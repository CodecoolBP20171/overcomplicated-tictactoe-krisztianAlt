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

import java.net.ConnectException;

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

    @ModelAttribute("avatar_uri")
    public String getAvatarUri() {
        try {
            String uri = restTemplate.getForObject("http://localhost:60002/", String.class);
            logger.info("URI from Avatar Service: " + uri);
            return uri;
        } catch (Exception e){
            logger.error("AVATAR SERVICE IS NOT AVAILABLE: " + e.getMessage());
        }

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

    @GetMapping(value = {"/game/{whoStart}"})
    public String gameStart(@ModelAttribute("player") Player player,
                            @PathVariable("whoStart") String whoStart,
                            RedirectAttributes redirectAttributes) {
        TictactoeGame newGame = new TictactoeGame();
        redirectAttributes.addFlashAttribute("game", newGame);

        if (whoStart.equals("comp")){
            int computerStep = getComputerStep(newGame);

            if (computerStep == -2){
                redirectAttributes.addFlashAttribute("error", "Server problem, please, try later.");
            } else if (computerStep == -1){
                logger.error("DATA REQUEST FAILED. READ AI LOG MESSAGE!");
            } else {
                newGame.setAGameField(computerStep, "X");
            }

        }

        return "redirect:/game";
    }

    @GetMapping(value = {"/game"})
    public String gameView(@ModelAttribute("player") Player player,
                           Model model) {
        model.addAttribute("funfact", getQuote());
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
            redirectAttributes.addFlashAttribute("gameover", "Game over, you cannot select another field. Please, go back to main page and start a new game.");
        } else {
            boolean fieldIsReserved = tictactoeGame.checkFieldIsEmpty(move);

            if (fieldIsReserved){
                redirectAttributes.addFlashAttribute("error", "Field is reserved, please, click another one!");
            } else {
                tictactoeGame.setAGameField(move, "O");

                if (tictactoeGame.draw()){
                    redirectAttributes.addFlashAttribute("gameover", "Draw. Keep breath and try again.");
                } else {
                    if (tictactoeGame.playerWins()){
                        redirectAttributes.addFlashAttribute("gameover", "Glorious victory!");
                    } else {
                        int computerStep = getComputerStep(tictactoeGame);

                        if (computerStep == -2){
                            redirectAttributes.addFlashAttribute("error", "Server problem, please, try later.");
                        } else if (computerStep == -1){
                            logger.error("DATA REQUEST FAILED. READ AI LOG MESSAGE!");
                        } else {
                            tictactoeGame.setAGameField(computerStep, "X");
                            if (tictactoeGame.computerWins()){
                                redirectAttributes.addFlashAttribute("gameover", "Defeat. Try again, we trust you.");
                            } else if (tictactoeGame.draw()){
                                redirectAttributes.addFlashAttribute("gameover", "Draw. Keep breath and try again.");
                            }
                        }
                    }
                }
            }
        }

        return "redirect:/game";
    }

    private int getComputerStep(TictactoeGame tictactoeGame){
        int computerStep = -2;

        try {
            computerStep = restTemplate.getForObject("http://localhost:60001" + "/{gameStatus}", int.class, tictactoeGame.getGameFields());
        } catch (Exception e){
            logger.error("AI SERVER IS NOT AVAILABLE: " + e.getMessage());
        }

        return computerStep;
    }

    private String getQuote(){
        try {
            String quote = restTemplate.getForObject("http://localhost:60003" + "/", String.class);
            return quote;
        } catch (Exception e){
            logger.error("FUNFACT SERVER IS NOT AVAILABLE: " + e.getMessage());
        }

        return "&quot;Chuck Norris knows the last digit of pi.&quot;";
    }
}
