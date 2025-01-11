package com.tubes.pbw.Show;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.tubes.Data.SetlistView;
import com.tubes.Data.Show;
import com.tubes.Data.ShowView;
import com.tubes.Data.Song;
import com.tubes.Data.SongView;
import com.tubes.Data.User;
import com.tubes.Data.Venue;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Setlist.SetlistService;
import com.tubes.pbw.Song.SongService;
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
    @Autowired
    private SongService songService;

    private List<ShowView> buildShowWrapper(List<Show> showList) {
        List<ShowView> showViews = new ArrayList<>();

        // building ShowView wrapper
        showList.forEach(show -> {
            // Ambil artist
            List<Artist> artistInShow = showsService.artistInShow(show.getIdShow());
            Artist artist = artistInShow.get(0);

            // Ambil venue
            Venue venue = venuesService.getVenueById(show.getVenue());

            // Tambahkan ke wrapper view
            showViews.add(new ShowView(show, venue, artist));

            System.out.println("-------Show: " + show.getShowName() + " --------");
            System.out.println("Artist Name: " + artist.getName());
            System.out.println("Venue Name: " + venue.getName());
        });
        return showViews;
    }

    // Menampilkan halaman Shows
    @GetMapping("/shows")
    public String shows(HttpSession session, Model model) {
        List<Show> shows = showsService.get5RandomShows(); // Ambil 5 random show dari database
        // Karena th:each tidak bisa mengiterasi 2 list secara pararel, maka kita perlu
        // membuat wrapper view
        List<ShowView> showViews = buildShowWrapper(shows);

        List<Song> top5Songs = songService.getTop5Songs(); // Ambil 5 lagu teratas dari database
        List<SongView> top5songViews = new ArrayList<>();

        // building SongView wrapper
        for (Song song : top5Songs) {
            Long Listener = song.getListener();
            String result;
            // if (Listener >= 1_000_000_000) {
            // result = String.format("%.0fB", Listener / 1_000_000_000.0);
            // }else if (Listener >= 1_000_000){
            // result = String.format("%.0fM", Listener / 1_000_000.0);
            // }
            // else if (Listener >= 1_000) {
            // result = String.format("%.0fK", Listener / 1_000.0);
            // } else {
            // result = String.valueOf(Listener);
            // }
            result = NumberFormat.getNumberInstance(Locale.US).format(Listener);
            top5songViews.add(new SongView(song, result));
            System.out.println("Song Name: " + song.getTitle());
            System.out.println("Listener: " + result);
        }

        List<SetList> setList5Random = setlistService.get5RandomSetlist(); // Ambil 5 random setlist dari database
        List<SetlistView> setlistViews = new ArrayList<>();

        // building SetlistView wrapper
        setList5Random.forEach(setlist -> {
            // Ambil show
            Show show = showsService.getShow(setlist.getShow().getIdShow());

            setlistViews.add(new SetlistView(setlist, show));
        });

        List<Show> upcomingShows = showsService.getUpcomingShows(); // Ambil show yang akan datang dari database
        // Menambahkan list upcomingShows ke model
        if (upcomingShows != null && !upcomingShows.isEmpty()) {
            List<ShowView> upcomingShowViews = buildShowWrapper(upcomingShows);
            model.addAttribute("upcomingShowViewList", upcomingShowViews);
        } else {
            // Explicitly set an empty list or null to ensure consistent rendering
            model.addAttribute("upcomingShowViewList", null);
        }
        // List<ShowView> upcomingShowViews = buildShowWrapper(upcomingShows);

        // debug purpose
        System.out.println("-------Upcoming Shows--------");
        for (Show show : upcomingShows) {
            System.out.println("Show Name: " + show.getShowName());
            System.out.println("Date: " + show.getDate());
            System.out.println("Description: " + show.getDescription());
            System.out.println("Venue: " + show.getVenue());
            System.out.println("------------------------");
        }
        
        // if(!upcomingShows.isEmpty()) model.addAttribute("upcomingShowViewList", upcomingShowViews); 
        model.addAttribute("showViewList", showViews); // Menambahkan list showViews ke model
        model.addAttribute("top5SongsView", top5songViews); // Menambahkan list top5Songs ke model
        model.addAttribute("top5Setlistsview", setlistViews); // Menambahkan list setlistViews ke model

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
    public String redirectToAddShow(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // Jika pengguna belum login, arahkan ke halaman login
            return "redirect:/login";
        }

        // Tambahkan atribut user ke model jika diperlukan
        model.addAttribute("user", loggedUser);
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
        return "redirect:/shows";
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
    public String getShowDetail(@PathVariable String id, Model model) {
        Long idShow = Long.parseLong(id);
        Show show = showsService.getShow(idShow);
        //ambil id venue untuk mendapatkan objek venue
        Long idvenue = show.getVenue();
        // ambil venue
        Venue venue = venuesService.getVenueById(idvenue);
        //cari setlist-setlist yang ada di show dengan id di parameter
        List<SetList> setlist = setlistService.getSetlistByShowId(idShow);
        // System.out.println(setlist.toString());
        System.out.println("-------SetList In show: " + show.getShowName() + " --------");
        for (SetList set : setlist) {
            System.out.println("Setlist ID: " + set.getId());
            System.out.println("Setlist Title: " + set.getTitle());
            System.out.println("Artist Name: " + set.getArtist().getName()); // Assuming 'getArtist()' returns an Artist
                                                                             // object
            // Add any other properties you want to print
            System.out.println("------------------------");
        }

        model.addAttribute("show", show);
        model.addAttribute("venue", venue);
        model.addAttribute("setlist_list", setlist);
        return "show-detail";
    }
}
