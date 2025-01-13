package com.tubes.pbw.Setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;
import com.tubes.Data.SetList;
import com.tubes.Data.SetlistHistory;
import com.tubes.Data.Show;
import com.tubes.Data.Song;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Show.ShowsService;
import com.tubes.pbw.Song.SongRepository;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private ShowsService showsService;

    @Autowired
    private ArtistsService artistsService;

    @Autowired
    private SongRepository songRepository; 

    @Override
    public void save(SetList setlist) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<SetList> findById(String id) {
        jdbcTemplate.query("SELECT * FROM setlist WHERE id = ?", this::mapRowToSetlist, id);
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public SetList findById(Integer id) {
        //ambil show
        String sql = "SELECT idshow FROM setlist WHERE idsetlist = ?";
        Long idShow = jdbcTemplate.queryForObject(sql, Long.class, id);
        Show show = showsService.getShow(idShow);
        //ambil artis
        sql = "SELECT idartist FROM setlist WHERE idsetlist = ?";
        Integer idArtist = jdbcTemplate.queryForObject(sql, Integer.class, id);
        Artist artist = artistsService.getArtistById(idArtist);
        //ambil lagu
        List<Song> songs = songRepository.findByArtist(idArtist);
        //ambil judul setlist
        sql = "SELECT title FROM setlist WHERE idsetlist = ?";
        String title = jdbcTemplate.queryForObject(sql, String.class, id);
        return new SetList(id, title, show, artist, songs);

    }

    @Override
    public List<SetList> findSetListByShowId(Long idShow) {
        String sql = "SELECT * FROM setlist WHERE idshow = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, idShow);
    }

    @Override
    public List<SetList> find5RandomSetlist() {
        String sql = "SELECT * FROM setlist ORDER BY RANDOM() LIMIT 5";
        return jdbcTemplate.query(sql, this::mapRowToSetlist);
    }

    @Override
    public Integer save(String setlistTitle, Long showId, Long artistId) {
        String sql = "INSERT INTO setlist (title, idshow, idartist) VALUES (?, ?, ?) RETURNING idsetlist";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, setlistTitle, showId, artistId);
        sql = "INSERT INTO artist_show (idartist, idshow) VALUES (?, ?)";
        jdbcTemplate.update(sql, showId, artistId);
        return id;
    }

    @Override
    public List<SetList> findByArtist(Integer id) {
        String sql = "SELECT idsetlist FROM setlist WHERE idartist = ?";
        List<Integer> idSetlists = jdbcTemplate.queryForList(sql, Integer.class, id);
        List<SetList> setlists = new ArrayList<>();
        for(int i : idSetlists){
            setlists.add(findById(i));
        }
        return setlists;
    }
    private SetList mapRowToSetlist(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = artistsService.getArtistById(rs.getInt("idartist")); // Fetch artist using service
        Show show = showsService.getShow(rs.getLong("idshow")); // Fetch show using service
        List<Song> songs = songRepository.findSongsBySetlistId(rs.getInt("idsetlist")); // Fetch songs associated with setlist

        return new SetList(
            rs.getInt("idsetlist"),    // ID of the setlist
            rs.getString("title"),    // Title of the setlist
            show,                     // Associated show object
            artist,                   // Associated artist object
            songs                     // List of songs in the setlist
        );
    }

    @Override
    public List<SetList> findAll() {
        String sql = "SELECT idsetlist FROM setlist";
        List<Integer> idSetlists = jdbcTemplate.queryForList(sql, Integer.class);
        List<SetList> setlists = new ArrayList<>();
        for (Integer id : idSetlists) {
            setlists.add(findById(id));
        }
        return setlists;
    }

    @Override
    public void updateSetlist(Integer id, String title, List<Integer> removedSongs, 
    List<Map<String, Object>> addedSongs, String oldTitle, String editorEmail) {
    
    StringBuilder activityLog = new StringBuilder();
    
    try {
        // Begin transaction
        jdbcTemplate.execute("BEGIN");
        
        // Update title if changed
        if (!title.equals(oldTitle)) {
            String sql = "UPDATE setlist SET title = ? WHERE idsetlist = ?";
            jdbcTemplate.update(sql, title, id);
            activityLog.append("Change the title from ").append(oldTitle)
                      .append(" to ").append(title).append("\n");
        }
        
        // Remove songs
        if (removedSongs != null && !removedSongs.isEmpty()) {
            String sql = "DELETE FROM song_setlist WHERE id_setlist = ? AND id_song = ?";
            for (Integer songId : removedSongs) {
                String songTitle = jdbcTemplate.queryForObject(
                    "SELECT title FROM songs WHERE idSongs = ?", 
                    String.class, songId);
                jdbcTemplate.update(sql, id, songId);
                activityLog.append("Remove ").append(songTitle)
                          .append(" from the setlist\n");
            }
        }
        
        // Add songs
        if (addedSongs != null && !addedSongs.isEmpty()) {
            for (Map<String, Object> song : addedSongs) {
                Integer songId;
                if ((Boolean) song.get("isNew")) {
                    // Get the artist ID from the setlist
                    Integer artistId = jdbcTemplate.queryForObject(
                        "SELECT idArtist FROM setlist WHERE idsetlist = ?",
                        Integer.class, id);
                    
                    // Create new song
                    String insertSongSql = """
                        INSERT INTO songs (title, listener, url, idAlbum)
                        VALUES (?, 0, '', (SELECT idAlbum FROM album WHERE IdArtist = ? LIMIT 1))
                        RETURNING idSongs
                    """;
                    songId = jdbcTemplate.queryForObject(insertSongSql, Integer.class, 
                        song.get("title"), artistId);
                    activityLog.append("Add new song ").append(song.get("title"))
                              .append(" to the setlist\n");
                } else {
                    songId = ((Number) song.get("id")).intValue();
                    String songTitle = jdbcTemplate.queryForObject(
                        "SELECT title FROM songs WHERE idSongs = ?", 
                        String.class, songId);
                    activityLog.append("Add ").append(songTitle)
                              .append(" to the setlist\n");
                }
                
                // Add song to setlist
                String sql = "INSERT INTO song_setlist (id_song, id_setlist) VALUES (?, ?)";
                jdbcTemplate.update(sql, songId, id);
            }
        }
        
        // Record changes
        if (activityLog.length() > 0) {
            String sql = "INSERT INTO setlist_changes (setlist_id, editor_email, activity) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, id, editorEmail, activityLog.toString());
        }
        
        // Commit transaction
        jdbcTemplate.execute("COMMIT");
    } catch (Exception e) {
        // Rollback transaction on error
        jdbcTemplate.execute("ROLLBACK");
        throw e;
    }
}

    @Override
    public List<SetList> searchSetlists(String query) {
        String sql = """
           SELECT 
                s.idSetlist, 
                s.title, 
                sh.showName AS showName, 
                a.name AS artistName, 
                a.PhotosURL
            FROM 
                setlist s
            JOIN 
                "show" sh ON s.idShow = sh.idShow
            JOIN 
                artist a ON s.idArtist = a.idArtist
            WHERE 
                LOWER(s.title) LIKE ? 
                OR LOWER(sh.showName) LIKE ? 
                OR LOWER(a.name) LIKE ?;
        """;

        return jdbcTemplate.query(sql, new Object[] {
            "%" + query + "%",
            "%" + query + "%",
            "%" + query + "%"
        }, (rs, rowNum) -> {
            SetList setlist = new SetList();
            setlist.setId(rs.getInt("idsetlist"));
            setlist.setTitle(rs.getString("title"));

            Show show = new Show();
            show.setShowName(rs.getString("showName"));
            setlist.setShow(show);

            Artist artist = new Artist();
            artist.setName(rs.getString("artistName"));
            artist.setPhotosURL(rs.getString("photosURL"));
            setlist.setArtist(artist);

            return setlist;
        });
    }

    @Override
    public List<SetlistHistory> getHistory(Integer idSetlist) {
        String sql = "SELECT * FROM setlist_changes WHERE setlist_id = ? ORDER BY change_date DESC";
        List<SetlistHistory> result = jdbcTemplate.query(sql, this::mapRowToSetlistHistory, idSetlist);
        return result;
    }

    private SetlistHistory mapRowToSetlistHistory(ResultSet rs, int rowNum) throws SQLException{
        return new SetlistHistory(
            rs.getInt("id"),
            rs.getInt("setlist_id"),
            rs.getString("editor_email"),
            rs.getTimestamp("change_date"),
            rs.getString("activity")
        );
    }
}
