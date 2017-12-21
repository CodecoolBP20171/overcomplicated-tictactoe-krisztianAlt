package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@SessionAttributes({"player", "game"})
public class GameController {

    @Autowired
    protected RestTemplate restTemplate;


    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    @ModelAttribute("game")
    public TictactoeGame getGame() {
        return new TictactoeGame();
    }

    @ModelAttribute("avatar_uri")
    public String getAvatarUri() {
        return "https://robohash.org/codecool";
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player) {
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        return "redirect:/game";
    }

    @GetMapping(value = "/game")
    public String gameView(@ModelAttribute("player") Player player,
                           Model model) {
        model.addAttribute("funfact", "&quot;Chuck Norris knows the last digit of pi.&quot;");
        model.addAttribute("comic_uri", "https://imgs.xkcd.com/comics/bad_code.png");
        return "game";
    }

    @GetMapping(value = "/game-move")
    public String gameMove(@ModelAttribute("player") Player player,
                           @ModelAttribute("move") int move,
                           @ModelAttribute("game") TictactoeGame tictactoeGame) {
        System.out.println("Player moved " + move);
        tictactoeGame.setAGameField(move, "O");
        System.out.println("Game state: " + tictactoeGame.getGameFields());

        System.out.println("BEFORE");
        int computerStep = restTemplate.getForObject("http://localhost:60001" + "/{gameStatus}", int.class, tictactoeGame.getGameFields());
        System.out.println("AFTER");
        tictactoeGame.setAGameField(computerStep, "X");

        return "redirect:/game";
    }


}
