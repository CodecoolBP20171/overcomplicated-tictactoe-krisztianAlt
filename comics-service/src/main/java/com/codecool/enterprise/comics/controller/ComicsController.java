package com.codecool.enterprise.comics.controller;

import com.codecool.enterprise.comics.apihandling.ComicsApiReader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ComicsController {

    @Autowired
    ComicsApiReader comicsApiReader;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ComicsController.class);

    private Random randomGenerator = new Random();
    private static final int MIN_COMICS_NUMBER = 1;
    private static final int MAX_COMICS_NUMBER = 1929;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getDataFromComicsApi() {

        try {
            int comicsNumber = randomGenerator.nextInt(MAX_COMICS_NUMBER - MIN_COMICS_NUMBER + 1) + MIN_COMICS_NUMBER;
            String uri = comicsApiReader.getDataFromApi(comicsNumber);
            logger.info("Data request from API succeeded. The URI: " + uri);
            return uri;
        } catch (Exception e){
            logger.error("DATA REQUEST FROM API FAILED: " + e.getMessage());
        }

        return "https://imgs.xkcd.com/comics/bad_code.png";

    }

}
