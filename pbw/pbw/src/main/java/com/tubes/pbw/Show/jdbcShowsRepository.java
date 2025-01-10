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
import com.tubes.pbw.Venue.VenueService;
@Repository
public class jdbcShowsRepository implements ShowsRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VenueService venueService;

    @Override
    public Optional<Show> findByName(String showName) {
        String sql = "SELECT * FROM \"show\" WHERE LOWER(showName) = LOWER(?)";

        // Menggunakan JdbcTemplate untuk mengeksekusi query dan memetakan hasilnya ke objek Show
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, showName);

        // Mengembalikan show pertama yang ditemukan dalam Optional
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void addShow(Show show) throws Exception {
       String sql = "INSERT INTO \"show\" (showName, date, description ,idVenue ) VALUES (?, ?, ?, ?)";
         jdbcTemplate.update(sql, 
                            show.getShowName(), 
                            show.getDate(), 
                            show.getDescription(),
                            show.getVenue());
    }

    @Override
    public Show findById(Long id) {
        String sql = "SELECT idvenue FROM \"show\" WHERE idshow = ?";
        //ambil nama venue pada show
        Long idVenue = jdbcTemplate.queryForObject(sql, Long.class, id);
        Venue venue = venueService.getVenueById(idVenue);
        sql = "SELECT * FROM \"show\" WHERE idshow = ?";
        List<Show> result = jdbcTemplate.query(sql, 
            (rs, rowNum) -> new Show(
                rs.getLong("idshow"),
                rs.getString("showname"),
                rs.getDate("date"),
                rs.getLong("idvenue"),
                rs.getString("description"))
            , id);
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

    private Show mapRowToShow(ResultSet rs, int intRow) throws SQLException {
        Show show = new Show(
            rs.getLong("idShow"),        // Ambil idShow
            rs.getString("showName"),   // Ambil nama show
            rs.getDate("date"),         // Ambil tanggal
            rs.getLong("idvenue"),      // Ambil venue
            rs.getString("description") // Ambil deskripsi
        );
        // untuk debug atau log.
        // System.out.println("Processing row: " + intRow);
        return show;
    }
    

    
}
