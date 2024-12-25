package com.tubes.pbw.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            session.setAttribute("loggedUser ", user);
            return "redirect:/home";
        } else {
            // Jika user tidak ditemukan atau password salah, tampilkan error
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Kembali ke halaman login
        }
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        // Menyediakan objek User kosong untuk form
        model.addAttribute("user", new User());
        return "register"; // Mengarahkan ke halaman register
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model) {
        // Validasi kesamaan password dan konfirmasi password
        if (!user.getPassword().equals(user.getConfirmpassword())) {
            bindingResult.rejectValue("confirmpassword", "passwordMismatch",
                    "Password and confirm password do not match");
        }

        // Cek apakah email sudah ada
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "emailExists", "Email already exists");
        }

        // Jika ada error dalam validasi, kembali ke form register
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please fix the errors in the form");
            return "register";
        }

        // Enkripsi password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Simpan user ke database
        try {
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to register. Please try again.");
            return "register";
        }

        // Redirect ke halaman login setelah berhasil register
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/home"; // Redirect to home page
    }
}
