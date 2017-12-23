package com.codecool.enterprise.ai.apihandling;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class RemoteURLReader {

    public String readFromUrl(String endpoint) throws IOException {
        System.setProperty("http.agent", "Chrome");
        URL url = new URL(endpoint);
        String line;
        String result = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        reader.close();
        return result;
    }

}
