package com.codecool.enterprise.ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AIController {

    @RequestMapping(value = "/{gameStatus}", method = RequestMethod.GET)
    public int renderPlanetsBySolarSystem(Model model,
                                             @PathVariable("gameStatus") String gameStatus) {

        System.out.println(gameStatus);
        return 5;
    }


}
