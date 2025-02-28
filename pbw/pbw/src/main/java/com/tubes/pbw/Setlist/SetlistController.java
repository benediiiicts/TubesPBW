package com.tubes.pbw.Setlist;

import java.util.ArrayList;
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
import com.tubes.Data.Comment;
import com.tubes.Data.SetList;
import com.tubes.Data.SetlistHistory;
import com.tubes.Data.Song;
import com.tubes.Data.SongDetailView;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Comment.CommentService;
import com.tubes.pbw.Song.SongService;
import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class SetlistController {

    @Autowired
    SetlistService setlistService;

    @Autowired
    ArtistsService artistsService;

    @Autowired
    SongService SongService;

    @Autowired
    CommentService commentService;

    @GetMapping("/setlist")
    public String redirectToArtists(HttpSession session) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, arahkan ke halaman login
            return "redirect:/login";
        }

        // Jika login, arahkan ke halaman setlist
        return "setlist";
    }

    @GetMapping("/add-setlist")
    public String addSetlist(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, arahkan ke halaman login
            return "redirect:/login";
        }

        // Tambahkan atribut user ke model jika diperlukan
        model.addAttribute("user", loggedUser);
        return "add-setlist";
    }

    @PostMapping("/add-setlist")
    public String addSetlist(@RequestParam String setlistTitle, String showId, String artistId, HttpSession session) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, arahkan ke halaman login
            return "redirect:/login";
        }

        System.out.println("setlistTitle: " + setlistTitle);
        System.out.println("showId: " + showId);
        System.out.println("artistId: " + artistId);

        // Ubah showId dari string ke long
        Long showIdLong = Long.parseLong(showId);
        // Ubah artistId dari string ke long
        Long artistIdLong = Long.parseLong(artistId);

        // Handle the request
        Integer idSetlist = setlistService.addSetlist(setlistTitle, showIdLong, artistIdLong);
        return "redirect:/setlist/detail/" + idSetlist;
    }

    @GetMapping("/setlist/detail/{setlistId}")
    public String showSetlistDetail(@PathVariable int setlistId, HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");

        List<Comment> comments = commentService.getComments(setlistId);
        SetList setlist = setlistService.getSetList(setlistId);

        System.out.println("setlistTitle: " + setlist.getTitle());
        System.out.println("showId: " + setlist.getShow().getIdShow());
        System.out.println("artistId: " + setlist.getArtist().getId());

        List<Song> songList = SongService.getSongsBySetlistId(setlistId);

        model.addAttribute("comments", comments);
        model.addAttribute("user", loggedUser);
        model.addAttribute("setlist", setlist);
        model.addAttribute("songList", songList);
        return "setlist-detail";
    }

    @GetMapping("/setlist/add-song")
    public String addSongToSetlist(@RequestParam String setlistId, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, arahkan ke halaman login
            return "redirect:/login";
        }
        SetList setlist = setlistService.getSetList(Integer.valueOf(setlistId));
        model.addAttribute("user", loggedUser);
        model.addAttribute("setlist", setlist);
        return "add-song";
    }

    @PostMapping("/setlist/add-song")
    public String addSongToSetlist(@RequestParam int setlistId, int artistId, Model model) {
        SetList setlist = setlistService.getSetList(setlistId);
        model.addAttribute("setlist", setlist);
        return "add-song";
    }

    @ResponseBody
    @GetMapping("/api/setlist/artists/search")
    public List<Artist> searchArtists(@RequestParam String query, HttpSession session) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, lemparkan error
            throw new RuntimeException("User not logged in");
        }

        try {
            return artistsService.searchArtists(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace(); // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching artists");
        }
    }

    @ResponseBody
    @GetMapping("/api/setlist/songs/search")
    public List<SongDetailView> searchSongs(@RequestParam String query) {
        try {
            return SongService.searchSongsDetail(query); // Panggil service untuk pencarian artis
        } catch (Exception e) {
            e.printStackTrace();  // Untuk melihat jika ada error saat melakukan pencarian
            throw new RuntimeException("Error fetching songs");
        }
    }
    @GetMapping("/setlist/edit/{id}")
    public String showEditSetlist(@PathVariable int id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        SetList setlist = setlistService.getSetList(id);
        List<Song> songList = SongService.getSongsBySetlistId(id);

        model.addAttribute("setlist", setlist);
        model.addAttribute("songList", songList);
        model.addAttribute("user", loggedUser);
        
        return "edit-setlist";
    }

    @PostMapping("/setlist/edit/{id}")
    public String updateSetlist(
        @PathVariable int id,
        @RequestParam String title,
        @RequestParam String removedSongs,
        @RequestParam String addedSongs,
        @RequestParam String oldTitle,
        HttpSession session
    ) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        setlistService.updateSetlist(id, title, removedSongs, addedSongs, oldTitle, loggedUser.getEmail());
        
        return "redirect:/setlist/detail/" + id;
    }

    @GetMapping("/setlist/history/{id}")
    public String setlistHistory(@PathVariable int id, Model model, HttpSession session){
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<SetlistHistory> histories = setlistService.getSetlistHistories(id);
        SetList setlist = setlistService.getSetList(id);
        model.addAttribute("user", loggedUser);
        model.addAttribute("histories", histories);
        model.addAttribute("setlist", setlist);
        return "setlist-history";
    }
}
