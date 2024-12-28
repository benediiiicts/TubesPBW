package com.tubes.pbw.Discover;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class DiscoverController {
    @GetMapping("/discover")
    public String discover(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }
        return "discover"; // Mengarahkan ke halaman discover
    }
}
