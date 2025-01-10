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

import com.tubes.Data.User;
import com.tubes.pbw.RequiredRole;
import com.tubes.pbw.User.UserService;

@Controller
@RequestMapping("/members")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    // @RequiredRole("admin")
    public String listMembers(Model model, HttpSession session) {
        // Check if user is logged in
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        List<User> users = userService.findAll();

        // Sort users so that the newest user is at the bottom (ascending order by ID)
        users.sort((user1, user2) -> Integer.compare(user1.getId(), user2.getId())); // Sort by ID, ascending order

        model.addAttribute("user_list", users);
        model.addAttribute("user", loggedUser);
        return "data-member"; // HTML file for member list
    }
    //encode email untuk mapping
    @GetMapping("/edit-member/{email}")
    public String encodeEmail(@PathVariable String email){
        String encodedEmail =  URLEncoder.encode(email, StandardCharsets.UTF_8);
        return "redirect:/members/edit/"+encodedEmail;
    }
    // Form for editing a member
    @GetMapping("/edit/{encodedEmail}")
    // @RequiredRole("admin") // Only admin can access
    public String editMemberForm(@PathVariable String encodedEmail, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
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
    // @RequiredRole("admin") // Only admin can access
    public String updateMember(@ModelAttribute User user, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        userService.updateUserRole(user.getEmail(), user.getRole());
        return "redirect:/members"; // Redirect back to member list
    }

    @PostMapping("/delete/{email}")
    // @RequiredRole("admin") // Only admin can access
    public String deleteUser(@PathVariable String email, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        userService.deleteUser(email);
        return "redirect:/members"; // Redirect kembali ke daftar member
    }

    @PostMapping("/update-member")
    // @RequiredRole("admin") // Only admin can access
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
