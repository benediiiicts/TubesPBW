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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        @RequestParam(value = "query", required = false) String query,
        RedirectAttributes redirectAttributes
    ) {
        // Redirect to the base URL if query is empty or null
        if (query != null && query.trim().isEmpty()) {
            return "redirect:/discover";
        }

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
        }

        // Fetch popular setlists (terpengaruh pencarian)
        List<SetList> popularSetlists;
        if (query != null && !query.trim().isEmpty()) {
            popularSetlists = setlistService.searchSetlists(query);
        } else {
            popularSetlists = setlistService.getAllSetlists();
        }

        // Ambil hanya 10 setlist terpopuler
        popularSetlists = popularSetlists.stream()
                                        .limit(10)
                                        .collect(Collectors.toList());
        model.addAttribute("setlists", popularSetlists);

        // Fetch recomended setlists (tetap statis)
        List<SetList> allSetlists = setlistService.getAllSetlists();
        Collections.shuffle(allSetlists); // Acak list
        List<SetList> recomendedSetlists = allSetlists.stream()
                                                    .distinct()
                                                    .limit(5)
                                                    .collect(Collectors.toList());
        model.addAttribute("recomended_set", recomendedSetlists);

        return "discover";
    }


}
