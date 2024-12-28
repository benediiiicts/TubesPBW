package com.tubes.pbw.Artist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;
import com.tubes.Data.Artist;

import jakarta.servlet.http.HttpSession;

@Controller
public class ArtistsController {

    @Autowired
    ArtistsService artistsService;

    @GetMapping("/add-artist")
    public String redirectToAddArtists() {
        return "add-artist";
    }

    @GetMapping("/artists")
    public String showArtists(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session,
            Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }

        // Ambil data artis berdasarkan halaman dan ukuran
        List<Artist> artists = artistsService.getArtistsByPage(page, size);
        int totalPages = artistsService.getTotalPages(size);

        model.addAttribute("artists", artists);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "artists"; // Mengarahkan ke halaman artists
    }

    @GetMapping("/artist-detail")
    public String redirectToArtistDetail() {
        return "artist-detail";
    }
}

