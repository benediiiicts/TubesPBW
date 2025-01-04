package com.tubes.pbw.Home;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.tubes.Data.SetList;
import com.tubes.Data.User;
import com.tubes.pbw.Setlist.SetlistService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    SetlistService setlistService;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Ambil user dari session
        User loggedUser = (User) session.getAttribute("loggedUser");
        //setlist untuk slideshow recomended for you
        List<SetList> setlistsRecomended = new ArrayList<>();
        //random setlist
        Random random = new Random();
        //cek juga agar setlist yang sama tidak muncul 2 kali
        Set<Integer> checkSet = new HashSet<>();
        for(int i=0; i < 4; i++){
            int temp = random.nextInt(30)+1;
            if(checkSet.contains(temp)) i--;
            else{
                setlistsRecomended.add(setlistService.getSetList(temp));
                checkSet.add(temp);
            }
        }
        model.addAttribute("recomended_set", setlistsRecomended);
        //setlist untuk slideshow our top picks
        List<SetList> setlistsOTP = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            setlistsOTP.add(setlistService.getSetList(i+1));
        }
        model.addAttribute("otp_set", setlistsOTP);
        if (loggedUser != null) {
            // Jika user sudah login, tambahkan nama user ke model
            model.addAttribute("user", loggedUser);
        }
        return "home"; // Mengarahkan ke halaman home
    }

    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/home";
    }
}
