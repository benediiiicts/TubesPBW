package com.tubes.pbw.Admin;

import java.util.List;
import java.util.Optional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;
import com.tubes.pbw.RequiredRole;
import com.tubes.pbw.User.UserService;

@Controller
@RequestMapping("/members")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    @RequiredRole("admin") // Only admin can access
    public String listMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String search,
            Model model,
            HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<User> users = userService.findByEmailContaining(search, page, size);
        if(users.get(0).getEmail().equals("admin@gmail.com"))
            users.remove(0);
        long totalUsers = userService.countByEmailContaining(search);

        int startNumber = page * size + 1; // Mulai nomor sesuai halaman
        int totalPages = (int) Math.ceil((double) totalUsers / size);

        model.addAttribute("user_list", users);
        model.addAttribute("user", loggedUser);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startNumber", startNumber); // Kirim nomor awal
        model.addAttribute("searchQuery", search);

        return "data-member";
    }

    // encode email untuk mapping
    @GetMapping("/edit-member/{email}")
    @RequiredRole("admin") // Only admin can access
    public String encodeEmail(@PathVariable String email) {
        if (email.equals("admin@gmail.com"))
            return "redirect:/members";
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        return "redirect:/members/edit/" + encodedEmail;
    }

    // Form for editing a member
    @GetMapping("/edit/{encodedEmail}")
    @RequiredRole("admin") // Only admin can access
    public String editMemberForm(@PathVariable String encodedEmail, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        String checkEmail = URLEncoder.encode("admin@gmail.com", StandardCharsets.UTF_8);
        if (encodedEmail.equals(checkEmail))
            return "redirect:/members";
        String decodedEmail = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        Optional<User> user = userService.findByEmail(decodedEmail);
        if (user.isPresent()) {
            model.addAttribute("edit_user", user.get());
            model.addAttribute("user", loggedUser);
            return "edit-member"; // HTML for editing a member
        } else {
            return "redirect:/members"; // Redirect if user not found
        }
    }

    @PostMapping("/edit-member")
    @RequiredRole("admin") // Only admin can access
    public String updateMember(@ModelAttribute User user, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        userService.updateUserRole(user.getEmail(), user.getRole());
        return "redirect:/members"; // Redirect back to member list
    }

    @PostMapping("/delete/{email}")
    @RequiredRole("admin") // Only admin can access
    public String deleteUser(@PathVariable String email, HttpSession session) {
        if (email.equals("admin@gmail.com"))
            return "redirect:/members";
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        userService.deleteUser(email);
        return "redirect:/members"; // Redirect kembali ke daftar member
    }

    @PostMapping("/update-member")
    @RequiredRole("admin") // Only admin can access
    public String updateMemberRole(@ModelAttribute User user, HttpSession session) {
        // Check if user is logged in
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        // Update role in database
        userService.updateUserRole(user.getEmail(), user.getRole());
        // Redirect back to the member list
        return "redirect:/members";
    }
}
