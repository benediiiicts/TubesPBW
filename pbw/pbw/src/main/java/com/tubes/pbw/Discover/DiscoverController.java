package com.tubes.pbw.Discover;

import java.util.List;

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
        return "discover";
    }
}
