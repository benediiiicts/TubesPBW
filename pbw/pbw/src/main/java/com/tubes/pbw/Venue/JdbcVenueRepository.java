package com.tubes.pbw.Venue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Venue;

@Repository
public class JdbcVenueRepository implements VenueRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Venue> searchByName(String query) {
        String sql = "SELECT * FROM venue WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, this::mapRowToVenue, "%" + query.toLowerCase() + "%");
    }

    private Venue mapRowToVenue(ResultSet rs, int rowNum) throws SQLException {
        Venue venue = new Venue();
        venue.setId(rs.getLong("id")); // Ambil id
        venue.setName(rs.getString("name")); // Ambil nama venue
        venue.setAddress(rs.getString("address")); // Ambil alamat
        venue.setAddress2(rs.getString("address2")); // Ambil alamat tambahan (opsional)
        venue.setCity(rs.getString("city")); // Ambil kota
        venue.setState(rs.getString("state")); // Ambil negara bagian
        venue.setZip(rs.getString("zip")); // Ambil kode pos
        venue.setGeocodable(rs.getBoolean("geocodable")); // Ambil status geocodable
        venue.setLatitude(rs.getDouble("latitude")); // Ambil latitude
        venue.setLongitude(rs.getDouble("longitude")); // Ambil longitude
        venue.setWebsite(rs.getString("website")); // Ambil website
        venue.setPhone(rs.getString("phone")); // Ambil nomor telepon
        venue.setEmail(rs.getString("email")); // Ambil email
        return venue;
    }
    

    public List<Venue> findAllVenues(String sql) {
        return (List<Venue>) jdbcTemplate.query(sql, this::mapRowToVenue);
    }

    public Venue findById(Long id) {
        String sql = "SELECT * FROM venue WHERE id = ?";
        List<Venue> result = (List<Venue>) jdbcTemplate.query(sql, this::mapRowToVenue, id);
        if (result.isEmpty()) {
            return null; 
        }
        return result.get(0);
    }

}
