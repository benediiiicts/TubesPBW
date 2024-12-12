package com.tubes.pbw.Artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArtistsController {

    @Autowired
    ArtistsService artistsService;

    @GetMapping("/artists")
    public String redirectToArtists() {
        return "artists";
    }
    @GetMapping("/add-artist")
    public String redirectToAddArtists() {
        return "add-artist";
    }

}
