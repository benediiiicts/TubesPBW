package com.tubes.pbw.Setlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tubes.Data.Artist;
import com.tubes.pbw.Artist.ArtistsService;

@Controller
public class SetlistController {

    @Autowired
    SetlistService setlistService;

    @Autowired
    ArtistsService artistsService;

    @GetMapping("/setlist")
    public String redirectToArtists() {
        return "setlist";
    }
    @GetMapping("/add-setlist")
    public String redirectToAddArtists() {
        return "add-setlist";
    }

    @ResponseBody
    @GetMapping("/api/setlist/artists/search")
    public List<Artist> searchArtists(@RequestParam String query) {
        try {
            return artistsService.searchArtists(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace();  // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching artists");
        }
    }

}

