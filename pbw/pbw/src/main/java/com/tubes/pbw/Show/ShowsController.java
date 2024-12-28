package com.tubes.pbw.Show;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import com.tubes.Data.User;
import org.springframework.ui.Model;

@Controller
public class ShowsController {
    @GetMapping("/shows")
    public String shows(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }
        return "shows"; // Mengarahkan ke halaman shows
    }

    @GetMapping("/add-show")
    public String redirectToAddShow() {
        return "add-show";
    }
}
