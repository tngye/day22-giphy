package sg.edu.nus.iss.day22giphy.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GiphyService {

    // API_KEY
    @Value("${api.key}")
    private String apiKey;

    public List<String> getSearchResults(String q, Integer limit, String rating) throws IOException {

        List<String> imgUrls = new ArrayList<String>();
        
        String url = UriComponentsBuilder
                .fromUriString("https://api.giphy.com/v1/gifs/search")
                .queryParam("api_key", apiKey)
                .queryParam("q", q)
                .queryParam("limit", limit)
                .queryParam("offset", 0)
                .queryParam("rating", rating)
                .queryParam("lang", "en")
                .toUriString();

        System.out.println(">>>> url: " + url);

        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return imgUrls;
        }

        JsonObject data = null;

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            data = reader.readObject();
        }

        JsonArray dataArr = data.getJsonArray("data");

        for (int i = 0; i < dataArr.size(); i++) {
            JsonObject obj = dataArr.getJsonObject(i);
            JsonObject img = obj.getJsonObject("images");
            JsonObject fixedwidth = img.getJsonObject("fixed_width");
            String imgUrl = fixedwidth.getString("url");
            imgUrls.add(imgUrl);
        }
        System.out.println(">>>> imgUrls: " + imgUrls);
        return imgUrls;
    }

}
