package com.tubes.pbw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArtistsController {
    @GetMapping("/artists")
    public String redirectToArtists() {
        return "artists";
    }
    @GetMapping("/add-artist")
    public String redirectToAddArtists() {
        return "add-artist";
    }
}
