package com.tubes.pbw.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;
import com.tubes.pbw.User.JdbcUserRepository;
import com.tubes.pbw.User.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcUserRepository jdbcUserRepository;
    
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

    @GetMapping("/register")
    public String registerView(User user){
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult ){
        if(!user.getPassword().equals(user.getConfirmpassword())){
            bindingResult.rejectValue("confirmpassword", "passwordMissmatch", "password do not match");
        }

        if(!userService.register(user)){
            bindingResult.rejectValue("username", "usernameAlreadyExists", "username already exists" );
        }
       
        if(bindingResult.hasErrors()){
            return "/login";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            jdbcUserRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    

}