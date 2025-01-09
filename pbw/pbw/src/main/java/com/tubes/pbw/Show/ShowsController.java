package com.tubes.pbw.Show;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

import com.tubes.Data.Artist;
import com.tubes.Data.SetList;
import com.tubes.Data.Show;
import com.tubes.Data.User;
import com.tubes.Data.Venue;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Setlist.SetlistService;
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
    @Autowired
    private SetlistService setlistService;

    // Menampilkan halaman Shows
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

    @GetMapping("/shows-detail")
    public String showDetail() {
        return "show-detail";
    }

    // Halaman untuk menambah show
    @GetMapping("/add-show")
    public String redirectToAddShow() {
        return "add-show";
    }

    @PostMapping("/add-show")
    public String addShow(@RequestParam("showName") String showName,
                          @RequestParam("date") String date,
                          @RequestParam("description") String description,
                          @RequestParam("venueId") Long venue,
                          Model model) {
        try {
            showsService.addNewShow(showName, date, description, venue);
            model.addAttribute("success", "Show added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error adding show");
        }
        return "add-show";
    }

    // API untuk mencari artis
    @ResponseBody
    @GetMapping("/api/show/artists/search")
    public List<Artist> searchArtists(@RequestParam String query) {
        try {
            return artistsService.searchArtists(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace(); // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching artists");
        }
    }

    // API untuk mencari venue
    @ResponseBody
    @GetMapping("/api/show/venue/search")
    public List<Venue> searchVenue(@RequestParam String query) {
        try {
            return venuesService.getVenueByName(query); // Panggil service untuk pencarian venue
        } catch (Exception e) {
            e.printStackTrace(); // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching Venue");
        }
    }

    // API untuk mendapatkan detail show
    // @ResponseBody
    @GetMapping("/show/detail/{id}")
    public String getShowDetail(@PathVariable Long id, Model model) {
        Show show = showsService.getShow(id);
        //ambil id venue untuk mendapatkan objek venue
        Long idvenue = show.getVenue();
        //ambil venue
        Venue venue = venuesService.getVenueById(idvenue);
        //cari setlist-setlist yang ada di show dengan id di parameter
        // List<Setlist> setlist = setlistService.getSetlistByShowId(id);
        List<SetList> setlist = setlistService.getSetlistByShowId(id);
        System.out.println(setlist.toString());
        model.addAttribute("show", show);
        model.addAttribute("venue", venue);
        model.addAttribute("setlist_list", setlist);
        return "show-detail";
    }
}
