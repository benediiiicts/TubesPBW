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
    public Artist getArtistById(Integer id){
        return artistRepository.findById(id);
    }

    public List<Artist> getFilteredArtists(int page, int size, String search, String country, String genre) {
        int offset = (page - 1) * size;
        StringBuilder sql = new StringBuilder("SELECT * FROM artist WHERE 1=1");
    
        if (search != null && !search.isEmpty()) {
            sql.append(" AND LOWER(name) LIKE LOWER('%").append(search).append("%')");
        }
        if (country != null && !country.isEmpty()) {
            sql.append(" AND LOWER(Country) = LOWER('").append(country).append("')");
        }
        if (genre != null && !genre.isEmpty()) {
            sql.append(" AND LOWER(genre) = LOWER('").append(genre).append("')");
        }
    
        sql.append(" LIMIT ").append(size).append(" OFFSET ").append(offset);
    
        return artistRepository.findAllArtists(sql.toString());
    }    
    
}


