package com.codecool.enterprise.ai.apihandling;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class APIReader {

    private static final String tttApiPath = "http://tttapi.herokuapp.com/api/v1/";
    private static final String humanSign = "O";
    private static final String computerSign = "X";

    @Autowired
    RemoteURLReader reader;

    public int getDataFromApi(String gamestate) throws IOException {
        String apiResponse = reader.readFromUrl(tttApiPath + gamestate + "/" + humanSign);
        JSONObject json = new JSONObject(apiResponse);
        String recommendation = json.get("recommendation").toString();
        return Integer.parseInt(recommendation);
    }

}