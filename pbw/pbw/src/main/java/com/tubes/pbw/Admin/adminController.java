package com.tubes.pbw.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class adminController {

    @GetMapping("/member")
    public String showMember() {
        return "data-member";
    }

    @GetMapping("/edit-member")
    public String editMember(@AuthenticationPrincipal User user, HttpSession session, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            // Jika @AuthenticationPrincipal kosong, cek user dari session
            User sessionUser = (User) session.getAttribute("loggedUser");
            if (sessionUser != null) {
                model.addAttribute("user", sessionUser);
            }
        }
        return "edit-member"; // Mengarahkan ke halaman admin
    }
}