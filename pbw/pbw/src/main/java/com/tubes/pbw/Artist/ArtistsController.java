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

    // @GetMapping("/artists")
    // public String redirectToArtists() {
    //     return "artists";
    // }
    @GetMapping("/add-artist")
    public String redirectToAddArtists() {
        return "add-artist";
    }

    @GetMapping("/artists")
    public String showArtists(Model model) {
        List<Artist> artists = artistsService.getAllArtists();
        model.addAttribute("artists", artists);
        return "artists";
    }


}
