package com.codecool.enterprise.funfact.apihandling;

import com.codecool.enterprise.ai.apihandling.RemoteURLReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ChuckNorrisApiReader {

    private static final String CHUCK_NORRIS_API_PATH = "https://api.chucknorris.io/jokes/random";

    @Autowired
    RemoteURLReader quoteReader;

    public String getDataFromApi() throws IOException {
        String apiResponse = quoteReader.readFromUrl(CHUCK_NORRIS_API_PATH);
        JSONObject json = new JSONObject(apiResponse);
        String quote = json.get("value").toString();
        return quote;
    }
}
