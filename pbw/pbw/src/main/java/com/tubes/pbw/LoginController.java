package com.tubes.pbw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String redirectToLogin(HttpSession session) {
        // Jika user sudah login, redirect ke dashboard
        if (session.getAttribute("loggedUser") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        // Panggil fungsi login di UserService
        User user = userService.login(email, password);
        if (user != null) {
            // Jika user valid, simpan ke session dan redirect ke dashboard
            session.setAttribute("loggedUser", user);
            return "redirect:/home";
        } else {
            // Jika user tidak ditemukan atau password salah, tampilkan error
            model.addAttribute("status", "failed");
            return "login";
        }
    }

    

}