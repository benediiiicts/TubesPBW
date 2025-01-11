package com.tubes.pbw.Report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.ArtistReport;
import com.tubes.Data.Song;

@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;

    public List<ArtistReport> getArtistReports(){
        return reportRepository.getArtistReports();
    }
    public List<Song> getSongsByArtistName(Integer idArtist){
        return reportRepository.getSongsByArtistName(idArtist);
    }
}
