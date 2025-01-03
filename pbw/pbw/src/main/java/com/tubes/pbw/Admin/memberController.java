package com.tubes.pbw.Admin;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tubes.Data.User;

@Controller
@RequestMapping("/members")
public class memberController {
    @Autowired
    private memberRepository userRepository;

    // Display all members
    @GetMapping
    public String listMembers(Model model, HttpSession session) {
        // Check if user is logged in
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        List<User> users = userRepository.findAll();

        // Sort users so that the newest user is at the bottom (ascending order by ID)
        users.sort((user1, user2) -> Integer.compare(user1.getId(), user2.getId())); // Sort by ID, ascending order

        model.addAttribute("users", users);
        model.addAttribute("user", loggedUser); // Pass logged user to view
        return "data-member"; // HTML file for member list
    }

    // Form for editing a member
    @GetMapping("/edit-member/{id}")
    public String editMemberForm(@PathVariable int id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("loggedUser", loggedUser); // Add logged user to model for header
            return "edit-member"; // HTML for editing a member
        } else {
            return "redirect:/members"; // Redirect if user not found
        }
    }

    @PostMapping("/edit-member")
    public String updateMember(@ModelAttribute User user, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        userRepository.updateUserRole(user.getId(), user.getRole());
        return "redirect:/members"; // Redirect back to member list
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        userRepository.deleteById(id);
        return "redirect:/members"; // Redirect kembali ke daftar member
    }

    @PostMapping("/update-member")
    public String updateMemberRole(@ModelAttribute User user, HttpSession session) {
        // Check if user is logged in
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        // Update role in database
        userRepository.updateUserRole(user.getId(), user.getRole());

        // Redirect back to the member list
        return "redirect:/members";
    }
}
