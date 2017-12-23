package com.codecool.enterprise.avatar.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvatarController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(AvatarController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getDataFromRoboApi() {
        String uri = "https://robohash.org/freddie";
        logger.info("URI: " + uri);
        return uri;
    }
}
