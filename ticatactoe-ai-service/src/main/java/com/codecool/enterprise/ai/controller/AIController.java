package com.codecool.enterprise.ai.controller;

import com.codecool.enterprise.ai.apihandling.APIReader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AIController {

    @Autowired
    APIReader apiReader;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(AIController.class);

    @RequestMapping(value = "/{gameStatus}", method = RequestMethod.GET)
    public int renderPlanetsBySolarSystem(@PathVariable("gameStatus") String gameStatus) {

        int apiRecommendation = -1;
        try {
            apiRecommendation = apiReader.getDataFromApi(gameStatus);
            logger.info("DATA REQUEST FROM API SUCCEEDED. RECOMMENDED STEP: " + apiRecommendation);
        } catch (Exception e){
            logger.error("DATA REQUEST FROM API FAILED: " + e.getMessage());
        }
        return apiRecommendation;

    }


}
