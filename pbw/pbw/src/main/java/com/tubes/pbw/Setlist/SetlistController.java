package com.tubes.pbw.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SetlistController {

    @Autowired
    SetlistService setlistService;

    @GetMapping("/setlist")
    public String redirectToArtists() {
        return "setlist";
    }
    @GetMapping("/add-setlist")
    public String redirectToAddArtists() {
        return "add-setlist";
    }

}

