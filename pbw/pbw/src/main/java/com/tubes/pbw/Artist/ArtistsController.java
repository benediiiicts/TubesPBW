package com.tubes.pbw.Artist;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tubes.Data.User;
import com.tubes.pbw.Country.CountryService;
import com.tubes.Data.Artist;
import com.tubes.Data.Country;

import jakarta.servlet.http.HttpSession;

@Controller
public class ArtistsController {

    @Autowired
    ArtistsService artistsService;

    @Autowired
    CountryService countryService;

    // untuk upload foto artis
    private static final String UPLOAD_DIR = "src/main/resources/static/assets";

    @GetMapping("/add-artist")
    public String redirectToAddArtists(Model model, HttpSession session) {
        // Mengambil user yang sedang login
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Jika tidak ada user yang login, redirect ke halaman login
        }

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("user", loggedUser); // Pastikan ini ada
        return "add-artist";
    }

    @PostMapping("/add-artist")
    public String addNewArtists(@RequestParam("artist_name") String name,
            @RequestParam("artist_description") String description,
            @RequestParam("artist_genre") String genre,
            @RequestParam("artist_year") String year,
            @RequestParam("artist_country") String country,
            @RequestParam("artist_picture") MultipartFile picture,
            Model model,
            HttpSession session) {
        // Mengambil user yang sedang login
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Jika tidak ada user yang login, redirect ke halaman login
        }

        try {
            // Cek jika tidak ada foto
            if (picture == null || picture.isEmpty()) {
                model.addAttribute("image_error", "Please select an image file");
                model.addAttribute("countries", countryService.getAllCountries());
                model.addAttribute("artist_name", name);
                model.addAttribute("artist_description", description);
                model.addAttribute("artist_genre", genre);
                model.addAttribute("artist_year", year);
                model.addAttribute("selected_country", country);
                return "add-artist";
            }

            // Check tipe file
            String contentType = picture.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute("image_error", "Please upload an image file (jpg, png, etc)");
                model.addAttribute("countries", countryService.getAllCountries());
                return "add-artist";
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = picture.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = name + "-" + UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(picture.getInputStream(), filePath);

            String imageUrl = "/assets/" + newFilename;
            model.addAttribute("imageUrl", imageUrl);

            // Tambahkan artis ke database
            Artist artist = artistsService.addNewArtists(name, description, genre, year, country, imageUrl);
            return "redirect:/artist/" + artist.getName() + "-" + artist.getId();
        } catch (Exception e) {
            model.addAttribute("error_submit", "Failed to upload file: " + e.getMessage());
            model.addAttribute("countries", countryService.getAllCountries());
            model.addAttribute("artist_name", name);
            model.addAttribute("artist_description", description);
            model.addAttribute("artist_genre", genre);
            model.addAttribute("artist_year", year);
            model.addAttribute("selected_country", country);
            return "add-artist";
        }
    }

    @GetMapping("/artists")
    public String showArtists(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String genre,
            HttpSession session, // Menambahkan parameter HttpSession
            Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser); // Pastikan user ada di model
        }

        List<Artist> artists = artistsService.getFilteredArtists(page, size, search, country, genre);
        int totalPages = artistsService.getTotalPages(size, search, country, genre);

        // Fetch genres and countries dynamically
        List<String> countries = artistsService.getAllCountries();
        List<String> genres = artistsService.getAllGenres();

        model.addAttribute("artists", artists);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countryFilter", country);
        model.addAttribute("genreFilter", genre);
        model.addAttribute("searchQuery", search);
        model.addAttribute("countries", countries);
        model.addAttribute("genres", genres);

        return "artists";
    }

    @GetMapping("/artist/{name}-{id}")
    public String redirectToArtistDetail(@PathVariable String name, @PathVariable Integer id, Model model,
            HttpSession session) {
        // Mengambil user yang sedang login
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser); // Menambahkan user ke model jika sudah login
        }

        Artist artist = artistsService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "artist-detail";
    }
}
