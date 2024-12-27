package com.tubes.pbw.Artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;

@Service
public class ArtistsService {

    @Autowired
    private JdbcArtistsRepository artistRepository;

     @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Artist> searchArtists(String query) {
        return artistRepository.searchByName(query);
    }

    public List<Artist> getAllArtists() {
        String sql = "SELECT * FROM artist";
        return artistRepository.findAllArtists(sql);
    }

    public List<Artist> getArtistsByPage(int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM artist LIMIT ? OFFSET ?";
        return artistRepository.findArtistsByPage(sql, size, offset);
    }
    
    public int getTotalPages(int size) {
        String sql = "SELECT COUNT(*) FROM artist";
        int totalArtists = jdbcTemplate.queryForObject(sql, Integer.class);
        return (int) Math.ceil((double) totalArtists / size);
    }
    
    
}

