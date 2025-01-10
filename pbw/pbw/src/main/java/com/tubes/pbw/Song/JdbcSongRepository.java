package com.tubes.pbw.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;
import com.tubes.Data.Song;

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

    private Song mapRowToSong(ResultSet rs, int rowNum) throws SQLException {
        return new Song(
                rs.getInt("idsongs"),
                rs.getInt("idalbum"),
                rs.getString("title"),
                rs.getString("url"),
                rs.getLong("listener")
        );
    }
    
}
