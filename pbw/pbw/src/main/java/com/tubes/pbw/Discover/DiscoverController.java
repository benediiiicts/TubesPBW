package com.tubes.pbw.Discover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.SetList;
import com.tubes.Data.User;
import com.tubes.pbw.Setlist.SetlistService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DiscoverController {

    @Autowired
    private SetlistService setlistService;

    @GetMapping("/discover")
    public String discover(
        HttpSession session,
        Model model,
        @RequestParam(value = "query", required = false) String query
    ) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
        }

        List<SetList> setlists;
        if (query != null && !query.trim().isEmpty()) {
            setlists = setlistService.searchSetlists(query);
        } else {
            setlists = setlistService.getAllSetlists();
        }

        model.addAttribute("setlists", setlists);

        // setlist untuk slideshow recomended for you
        List<SetList> setlistsRecomended = new ArrayList<>();
        // random setlist
        Random random = new Random();
        // cek juga agar setlist yang sama tidak muncul 2 kali
        Set<Integer> checkSet = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            int temp = random.nextInt(20) + 1;
            if (checkSet.contains(temp))
                i--;
            else {
                setlistsRecomended.add(setlistService.getSetList(temp));
                checkSet.add(temp);
            }
        }
        model.addAttribute("recomended_set", setlistsRecomended);
        
        return "discover";
    }

}
