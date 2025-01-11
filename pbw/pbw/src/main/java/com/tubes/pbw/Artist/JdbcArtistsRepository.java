package com.tubes.pbw.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;

@Repository
public class JdbcArtistsRepository implements ArtistsRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Artist addNewArtists(String name, String description, String genre, String year, String country,
            String pathURL) throws Exception{
        //simpan artis ke db                
        String sql = "INSERT INTO artist (PhotosURL, name, Description, genre, Year, Country) "+
                    "VALUES ( ? , ? , ? , ? , ? , ? )";
        jdbcTemplate.update(sql, pathURL, name, description, genre, year, country);
        //ambil artis yang telah disimpan
        //atribut yang akan digunakan adalah url foto, karena sudah pasti unik
        sql = "SELECT * FROM artist WHERE PhotosURL = ? ";
        List<Artist> result = jdbcTemplate.query(sql, this::mapRowToArtist, pathURL);
        return result.get(0);
    }

    @Override
    public void addArtist(Artist artist) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addArtist'");
    }

    @Override
    public Optional<Artist> findArtist(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findArtist'");
    }

    @Override
    public Artist findById(Integer id) {
        String sql = "SELECT * FROM artist WHERE idartist = ?";
        List<Artist> result = jdbcTemplate.query(sql, this::mapRowToArtist, id);
        Artist artist = result.get(0);
        return artist;
    }

    @Override
    public Optional<Artist> findByName(String name) {
        String sql = "SELECT * FROM artist WHERE LOWER(name) = LOWER(?)";
        List<Artist> results = jdbcTemplate.query(sql, this::mapRowToArtist, name);

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Artist> searchByName(String query) {
        String sql = "SELECT * FROM artist WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, this::mapRowToArtist, "%" + query.toLowerCase() + "%");
    }

    // Mapper untuk memetakan hasil query ke entitas Artist
    private Artist mapRowToArtist(ResultSet rs, int rowNum) throws SQLException {
        return new Artist(
                rs.getInt("idArtist"),
                rs.getString("PhotosURL"),
                rs.getString("name"),
                rs.getString("Description"),
                rs.getString("genre"),
                rs.getString("Year"),
                rs.getString("Country")
        );
    }

    @Override
    public List<Artist> findAllArtists(String query) {
        return jdbcTemplate.query(query, this::mapRowToArtist);
    }

    public List<Artist> findArtistsByPage(int limit, int offset) {
        String sql = "SELECT * FROM artist LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, this::mapRowToArtist, limit, offset);
    }

    public List<Artist> findToGetAllArtists() {
        String sql = "SELECT * FROM artist";
        return findAllArtists(sql);
    }

    @Override
    public int findTotalPages(int size, String search, String country, String genre) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM artist WHERE 1=1");
    
        if (search != null && !search.isEmpty()) {
            sql.append(" AND LOWER(name) LIKE LOWER('%").append(search).append("%')");
        }
        if (country != null && !country.isEmpty()) {
            sql.append(" AND LOWER(Country) = LOWER('").append(country).append("')");
        }
        if (genre != null && !genre.isEmpty()) {
            sql.append(" AND LOWER(genre) = LOWER('").append(genre).append("')");
        }
    
        int totalArtists = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        return (int) Math.ceil((double) totalArtists / size);
    }

    @Override
    public List<Artist> findFilteredArtists(int page, int size, String search, String country, String genre) {
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
    
        return findAllArtists(sql.toString());
    }
    
    @Override
    public List<String> findAllCountries() {
        String sql = "SELECT DISTINCT Country FROM artist";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> findAllGenres() {
        String sql = "SELECT DISTINCT genre FROM artist";
        return jdbcTemplate.queryForList(sql, String.class);
    } 
}