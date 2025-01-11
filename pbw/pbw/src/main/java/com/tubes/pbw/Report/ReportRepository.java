package com.tubes.pbw.Report;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.tubes.Data.ArtistReport;
import com.tubes.Data.Song;

public interface ReportRepository {
    List<ArtistReport> getArtistReports();
    List<Song> getSongsByArtistName(Integer idArtist);
}
