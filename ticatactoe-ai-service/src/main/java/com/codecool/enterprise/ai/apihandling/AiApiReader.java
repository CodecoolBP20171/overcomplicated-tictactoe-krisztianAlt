package com.codecool.enterprise.ai.apihandling;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AiApiReader {

    private static final String TTT_API_PATH = "http://tttapi.herokuapp.com/api/v1/";
    private static final String COMPUTER_SIGN = "X";

    @Autowired
    RemoteURLReader reader;

    public int getDataFromApi(String gamestate) throws IOException {
        String apiResponse = reader.readFromUrl(TTT_API_PATH + gamestate + "/" + COMPUTER_SIGN);
        JSONObject json = new JSONObject(apiResponse);
        String recommendation = json.get("recommendation").toString();
        return Integer.parseInt(recommendation);
    }

}
