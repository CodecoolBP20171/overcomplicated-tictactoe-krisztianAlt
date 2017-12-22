package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"player", "game"})
public class GameController {

    @Autowired
    protected RestTemplate restTemplate;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(GameController.class);

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
                           @ModelAttribute("game") TictactoeGame tictactoeGame,
                           RedirectAttributes redirectAttributes) {
        System.out.println("Player moved " + move);

        if (tictactoeGame.isGameClosed()){
            redirectAttributes.addFlashAttribute("gameover", "Game over, you cannot select another field, please, start new game.");
        } else {
            boolean fieldIsReserved = tictactoeGame.checkFieldIsEmpty(move);

            if (fieldIsReserved){
                redirectAttributes.addFlashAttribute("error", "Field is 0reserved, please, click another one!");
            } else {
                tictactoeGame.setAGameField(move, "O");

                if (tictactoeGame.draw()){
                    redirectAttributes.addFlashAttribute("gameover", "Draw. Keep breath and try again.");
                } else {
                    if (tictactoeGame.playerWins()){
                        redirectAttributes.addFlashAttribute("gameover", "Glorious victory!");
                    } else {
                        int computerStep = restTemplate.getForObject("http://localhost:60001" + "/{gameStatus}", int.class, tictactoeGame.getGameFields());

                        if (computerStep != -1){
                            tictactoeGame.setAGameField(computerStep, "X");
                            if (tictactoeGame.computerWins()){
                                redirectAttributes.addFlashAttribute("gameover", "Defeat. Try again, we trust you.");
                            } else if (tictactoeGame.draw()){
                                redirectAttributes.addFlashAttribute("gameover", "Draw. Keep breath and try again.");
                            }
                        } else {
                            logger.error("DATA REQUEST FAILED. READ AI LOG MESSAGE!");
                        }
                    }
                }
            }
        }

        return "redirect:/game";
    }


}
