package com.tubes.pbw.Setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;
import com.tubes.Data.SetList;
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
        String sql = "SELECT * FROM setlist WHERE idsetlist = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, idShow);
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
        
}
