package com.tubes.pbw.Show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Artist;
import com.tubes.Data.Show;
import com.tubes.Data.Venue;

@Repository
public class jdbcShowsRepository implements ShowsRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Show> findByName(String showName) {
        String sql = "SELECT * FROM \"show\" WHERE LOWER(showName) = LOWER(?)";

        // Menggunakan JdbcTemplate untuk mengeksekusi query dan memetakan hasilnya ke objek Show
        List<Show> results = (List<Show>) jdbcTemplate.query(sql, this::mapRowToShow, showName);

        // Mengembalikan show pertama yang ditemukan dalam Optional
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void addShow(Show show) throws Exception {
       String sql = "INSERT INTO \"show\" (showName, date, venue, description) VALUES (?, ?, ?, ?)";
         jdbcTemplate.update(sql, 
                            show.getShowName(), 
                            show.getDate(), 
                            show.getVenue(), 
                            show.getDescription());
    }

    @Override
public Show findById(Long id) {
    String sql = "SELECT * FROM \"show\" WHERE idShow = ?";
    List<Show> result = (List<Show>) jdbcTemplate.query(sql, this::mapRowToShow, id);
    if (result.isEmpty()) {
        return null; 
    }
    return result.get(0);
}

    @Override
    public List<Artist> artistInShow(String showName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'artistInShow'");
    }

    private Venue mapRowToVenue(ResultSet rs) throws SQLException {
    return Venue.builder()
            .id(rs.getLong("id")) // Ambil id
            .name(rs.getString("name")) // Ambil nama venue
            .address(rs.getString("address")) // Ambil alamat
            .address2(rs.getString("address2")) // Ambil alamat tambahan (opsional)
            .city(rs.getString("city")) // Ambil kota
            .state(rs.getString("state")) // Ambil negara bagian
            .zip(rs.getString("zip")) // Ambil kode pos
            .geocodable(rs.getBoolean("geocodable")) // Ambil status geocodable
            .latitude(rs.getDouble("latitude")) // Ambil latitude
            .longitude(rs.getDouble("longitude")) // Ambil longitude
            .website(rs.getString("website")) // Ambil website
            .phone(rs.getString("phone")) // Ambil nomor telepon
            .email(rs.getString("email")) // Ambil email
            .build();
    }

    private Show mapRowToShow(ResultSet rs) throws SQLException {
        return Show.builder()
                .idShow(rs.getLong("idShow")) // Ambil idShow
                .showName(rs.getString("showName")) // Ambil nama show
                .date(rs.getDate("date")) // Ambil tanggal
                .venue(rs.getString("venue")) // Ambil venue
                .description(rs.getString("description")) // Ambil deskripsi
                .build();
    }
    

    
}
