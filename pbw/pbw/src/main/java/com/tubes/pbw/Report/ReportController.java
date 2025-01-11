package com.tubes.pbw.Report;

import com.tubes.Data.ArtistReport;
import com.tubes.Data.Song;
import com.tubes.Data.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.util.List;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReportController {

    @Autowired
    private JdbcReportRepository artistReportRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping("/artist-report")
    public String getArtistReport(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }else{
            return "redirect:/login";
        }
        // Ambil daftar artist reports
        List<ArtistReport> artistReports = reportService.getArtistReports();

        // Tambahkan data artistReports ke model
        model.addAttribute("artistReports", artistReports);
        model.addAttribute("selectedOption", "all");

        return "artist-report";
    }

    @GetMapping("/artist-songs")
    @ResponseBody
    public List<Song> getSongsByArtistName(@RequestParam Integer idArtist) {
        return artistReportRepository.getSongsByArtistName(idArtist);
    }
}
