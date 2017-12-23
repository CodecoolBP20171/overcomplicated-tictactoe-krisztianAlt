package com.codecool.enterprise.funfact.controller;

import com.codecool.enterprise.funfact.apihandling.ChuckNorrisApiReader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunFactController {

    @Autowired
    ChuckNorrisApiReader chuckNorrisApiReader;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(FunFactController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getDataFromChuckNorrisApi() {

        String quote = "&quot;Chuck Norris knows the last digit of pi.&quot;";
        try {
            quote = "&quot;" + chuckNorrisApiReader.getDataFromApi() + "&quot;";
            logger.info("Data request from API succeeded: " + quote);
        } catch (Exception e){
            logger.error("DATA REQUEST FROM API FAILED: " + e.getMessage());
        }
        return quote;

    }

}
