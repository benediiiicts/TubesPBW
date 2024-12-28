package com.tubes.pbw.Artist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String genre,
            HttpSession session,
            Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
        }

        List<Artist> artists = artistsService.getFilteredArtists(page, size, search, country, genre);
        int totalPages = artistsService.getTotalPages(size);

        model.addAttribute("artists", artists);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "artists";
    }


    @GetMapping("/artist/{name}-{id}")
    public String redirectToArtistDetail(@PathVariable String name, @PathVariable Integer id, Model model) {
        Artist artist = artistsService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "artist-detail";
    }
}

