package com.tubes.pbw.Artist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.Artist;

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
            Model model) {
        List<Artist> artists = artistsService.getArtistsByPage(page, size);
        int totalPages = artistsService.getTotalPages(size);
        
        model.addAttribute("artists", artists);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "artists";
    }



}
