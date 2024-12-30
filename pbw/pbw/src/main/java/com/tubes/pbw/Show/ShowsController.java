package com.tubes.pbw.Show;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

import com.tubes.Data.Artist;
import com.tubes.Data.User;
import com.tubes.Data.Venue;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Venue.VenueService;

import org.springframework.ui.Model;

@Controller
public class ShowsController {

    @Autowired
    private ShowsService showsService;
    @Autowired
    private ArtistsService artistsService;
    @Autowired
    private VenueService venuesService;

    @GetMapping("/shows")
    public String shows(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }
        return "shows"; // Mengarahkan ke halaman shows
    }

    @GetMapping("/add-show")
    public String redirectToAddShow() {
        return "add-show";
    }

    @ResponseBody
    @GetMapping("/api/show/artists/search")
    public List<Artist> searchArtists(@RequestParam String query) {
        try {
            return artistsService.searchArtists(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace();  // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching artists");
        }
    }

    @ResponseBody
    @GetMapping("/api/show/venue/search")
    public List<Venue> searchVenue(@RequestParam String query) {
        try {
            return venuesService.getVenueByName(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace();  // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching Venue");
        }
    }
}
