package com.tubes.pbw.Setlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tubes.Data.Artist;
import com.tubes.Data.SetList;
import com.tubes.Data.Song;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Song.SongService;

@Controller
public class SetlistController {

    @Autowired
    SetlistService setlistService;

    @Autowired
    ArtistsService artistsService;

    @Autowired
    SongService SongService;

    @GetMapping("/setlist")
    public String redirectToArtists() {
        return "setlist";
    }
    // @GetMapping("/add-setlist")
    // public String redirectToAddArtists() {
    //     return "add-setlist";
    // }

    @GetMapping("/add-setlist")
    public String addSetlist(@RequestParam Long idShow, Model model) {
        // Handle the request
        model.addAttribute("showId", idShow);
        return "add-setlist";
    }

    @PostMapping("/add-setlist")
    public String addSetlist(@RequestParam String setlistTitle, String showId ,String artistId) {

        System.out.println("setlistTitle: " + setlistTitle);
        System.out.println("showId: " + showId);
        System.out.println("artistId: " + artistId);

        //ubah showId dari string ke long
        Long showIdLong = Long.parseLong(showId);
        //ubah artistId dari string ke long
        Long artistIdLong = Long.parseLong(artistId);

        // Handle the request
        setlistService.addSetlist(setlistTitle, showIdLong, artistIdLong);
        return "redirect:/show/detail/" + showId;
    }

    @GetMapping("/setlist/detail/{setlistId}")
    public String showSetlistDetail(@PathVariable  int setlistId, Model model) {
        SetList setlist = setlistService.getSetList(setlistId);

        // Integer.parseInt(setlistId)

        System.out.println("setlistTitle: " + setlist.getTitle());
        System.out.println("showId: " + setlist.getShow().getIdShow());
        System.out.println("artistId: " + setlist.getArtist().getId());

        List<Song> songList = SongService.getSongsBySetlistId(setlistId);

        model.addAttribute("setlist", setlist);
        model.addAttribute("songList", songList);
        return "setlist-detail";
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

