package com.codecool.enterprise.comics.apihandling;

import com.codecool.enterprise.ai.apihandling.RemoteURLReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ComicsApiReader {

    private static final String comicsApiPath = "https://xkcd.com/%s/info.0.json";

    @Autowired
    RemoteURLReader comicsReader;

    public String getDataFromApi(int comicsNumber) throws IOException {
        String url = String.format(comicsApiPath, Integer.toString(comicsNumber));
        String apiResponse = comicsReader.readFromUrl(url);
        JSONObject json = new JSONObject(apiResponse);
        String imageUri = json.get("img").toString();
        return imageUri;
    }

}
