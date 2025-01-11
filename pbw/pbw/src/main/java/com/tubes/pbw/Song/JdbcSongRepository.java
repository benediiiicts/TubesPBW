package com.tubes.pbw.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;
import com.tubes.Data.Song;
import com.tubes.Data.SongDetailView;

@Repository
public class JdbcSongRepository implements SongRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Song findById(Integer id) {
        String sql = "SELECT * FROM songs WHERE idsongs = ?";
        List<Song> songs = jdbcTemplate.query(sql, this::mapRowToSong, id);
        return songs.get(0);
    }

    @Override
    public List<Song> findByArtist(Integer idArtist) {
        String sql = "SELECT idsongs FROM song_artist WHERE idartist = ?";
        List<Integer> idSongs = jdbcTemplate.queryForList(sql, Integer.class, idArtist);
        List<Song> songs = new ArrayList<>();
        for(Integer id : idSongs){
            songs.add(findById(id));
        }
        return songs;
    }

    @Override
    public List<Song> findSongsBySetlistId(Integer idSetlist){
        String sql = "SELECT id_song FROM song_setlist WHERE id_setlist = ?";
        List<Integer> idSongs = jdbcTemplate.queryForList(sql, Integer.class, idSetlist);
        List<Song> songs = new ArrayList<>();
        for(Integer idSong : idSongs){
            songs.add(findById(idSong));
        }
        return songs;
    }

    public String findAlbumName(Integer id){
        String sql = "SELECT album_name FROM album WHERE idalbum = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    @Override
    public List<Song> findTop5Songs() {
        String sql = "SELECT * FROM songs ORDER BY listener DESC LIMIT 5";
        return jdbcTemplate.query(sql, this::mapRowToSong);
    }

    @Override
    public List<Song> searchSongs(String query) {
        String sql = "SELECT * FROM songs WHERE title LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToSong, "%" + query + "%");
    }

    @Override
    public List<SongDetailView> searchSongsDetail(String query) {
        String sql = "SELECT songs.idsongs AS songId, songs.title AS songTitle, artist.name AS artistName, " +
                 "artist.idartist AS artistId, album.title AS albumTitle " +
                 "FROM songs " +
                 "JOIN song_artist ON songs.idSongs = song_artist.idSongs " +
                 "JOIN artist ON song_artist.idArtist = artist.idArtist " +
                 "JOIN album ON songs.idalbum = album.idalbum " +
                 "WHERE songs.title LIKE ?";

        return jdbcTemplate.query(sql, this::mapRowToSongDetailView, "%" + query + "%");
    }

    private Song mapRowToSong(ResultSet rs, int rowNum) throws SQLException {
        return new Song(
                rs.getInt("idsongs"),
                rs.getInt("idalbum"),
                rs.getString("title"),
                rs.getString("url"),
                rs.getLong("listener")
        );
    }

    private SongDetailView mapRowToSongDetailView(ResultSet rs, int rowNum) throws SQLException {
        return new SongDetailView(
                rs.getInt("songId"),
                rs.getString("songTitle"),
                rs.getString("artistName"),
                rs.getInt("artistId"),
                rs.getString("albumTitle")
        );
    }
    
}
