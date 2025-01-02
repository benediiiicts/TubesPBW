package com.tubes.pbw.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tubes.pbw.User.UserService;
import org.springframework.ui.Model;
import com.tubes.Data.User;

@Controller
@RequestMapping("/members")
public class memberController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listMembers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "data-member"; // Halaman HTML untuk menampilkan data
    }

    @GetMapping("/edit-member/{id}")
    public String editMemberForm(@PathVariable int id, Model model) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get()); // Ambil User jika ada
            return "edit-member"; // Halaman untuk edit role
        } else {
            // Jika User tidak ditemukan, redirect ke halaman daftar member
            return "redirect:/members";
        }
    }

    @PostMapping("/edit-member")
    public String updateMember(@ModelAttribute User user) {
        userService.updateUserRole(user.getId(), user.getRole());
        return "redirect:/members";
    }
}
