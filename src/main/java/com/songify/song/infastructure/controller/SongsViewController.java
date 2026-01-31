package com.songify.song.infastructure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongsViewController {

    private Map<Integer, String> database = new HashMap<>();

    @GetMapping("/")
    public String home() {
        return "home.html";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {
        database.put(1, "shawnmendes song1");
        database.put(2, "ariana grande song2");
        database.put(3, "jessy james song3");
        database.put(4, "paul morris song4");

        model.addAttribute("songsMap", database);

        return "songs";
    }
}
