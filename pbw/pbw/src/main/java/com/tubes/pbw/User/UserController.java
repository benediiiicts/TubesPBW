package com.tubes.pbw.User;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private JdbcUserRepository jdbcUserRepository;

    @GetMapping("/login")
    public String loginView(HttpSession session, Model model) {
        // Jika user sudah login, redirect ke home
        if (session.getAttribute("loggedUser") != null) {
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        return "login"; // Mengarahkan ke halaman login
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
            model.addAttribute("user", user);
            return "redirect:/home";
        } else {
            // Jika user tidak ditemukan atau password salah, tampilkan error
            model.addAttribute("error_sign", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        // Menyediakan objek User kosong untuk form register
        model.addAttribute("user", new User());
        return "register"; // Mengarahkan ke halaman register
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
            BindingResult bindingResult, Model model, HttpSession session) {
        // Validasi kesamaan password dan konfirmasi password
        if (!user.getPassword().equals(user.getConfirmpassword())) {
            bindingResult.rejectValue("confirmpassword", "passwordMismatch",
                    "Password and confirm password do not match");
            model.addAttribute("error_reg", "password and confirm password mismatch");
        }
        // Cek apakah email sudah ada
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "emailExists", "Email already exists");
            model.addAttribute("error_reg", "Email already exists");
        }
        // Jika ada error dalam validasi, kembali ke form register
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("error_reg", errors);
            return "login"; // Return to the login page, keeping the signup panel active
        }
        // Enkripsi password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Simpan user ke database
        try {
            jdbcUserRepository.save(user);
            session.setAttribute("loggedUser", user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error_reg", "Failed to register. Please try again.");
            return "login"; // Return to login page with error
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate(); // hapus session
        return "redirect:/home"; // Redirect ke home page guest
    }
}
