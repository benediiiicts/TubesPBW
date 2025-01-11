package com.tubes.pbw.Report;

import com.tubes.Data.ArtistReport;
import com.tubes.Data.Song;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class JdbcReportRepository implements ReportRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ArtistReport> getArtistReports() {
        String sql = """
                    SELECT a.idArtist AS idArtist, a.year AS debutYear, a.name AS artistName, SUM(s.listener) AS totalListeners
                    FROM artist a
                    JOIN album al ON a.idArtist = al.idArtist
                    JOIN songs s ON al.idAlbum = s.idAlbum
                    GROUP BY a.idArtist
                    ORDER BY totalListeners DESC;
                """;

        return jdbcTemplate.query(sql, artistReportRowMapper());
    }

    private RowMapper<ArtistReport> artistReportRowMapper() {
        return (rs, rowNum) -> {
            ArtistReport report = new ArtistReport();
            report.setIdArtist(rs.getInt("idArtist"));
            report.setYear(rs.getString("debutYear"));
            report.setArtistName(rs.getString("artistName"));
            report.setTotalListeners(rs.getLong("totalListeners"));
            return report;
        };
    }

    public List<Song> getSongsByArtistName(Integer idArtist) {
        String sql = """
                    SELECT s.title AS songTitle, s.listener AS totalListeners, s.url AS songUrl
                    FROM artist a
                    JOIN album al ON a.idArtist = al.idArtist
                    JOIN songs s ON al.idAlbum = s.idAlbum
                    WHERE a.idartist = ?
                    ORDER BY s.listener DESC;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Song song = new Song();
            song.setTitle(rs.getString("songTitle"));
            song.setListener(rs.getLong("totalListeners"));
            song.setUrl(rs.getString("songUrl"));
            return song;
        }, idArtist);
    }
}
