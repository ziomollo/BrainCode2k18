package com.heimdall.bifrost.services;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class GoogleTranslateRequests {

    private RestTemplate template;

    public GoogleTranslateRequests() {
        template = new RestTemplate();
    }

    public ArrayList<String> translateStrings(ArrayList<String> stringsToTranslate) {
        HttpEntity<String> entity = new HttpEntity<>("");

        RequestBuilder req = RequestBuilder.get()
                .setUri("https://translation.googleapis.com/language/translate/v2")
                .addParameter("target","pl")
                .addParameter("key","AIzaSyDjkHIGT-bFJemoNR7HtfnTYCgV9NviEis");
                stringsToTranslate.forEach(s -> req.addParameter("q",s));

        HttpRequest httpRequest = req.build();

        ResponseEntity<String> jsonResponse = template.exchange(((HttpUriRequest) httpRequest).getURI().toString(), HttpMethod.GET, entity, String.class);
        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

        ArrayList<String> translateStrings = new ArrayList<>(stringsToTranslate.size());

        jsonObject.optJSONObject("data").optJSONArray("translations").iterator().forEachRemaining( o -> {
            if(o instanceof JSONObject){
                translateStrings.add(((JSONObject) o).get("translatedText").toString());
            }
        });

        return translateStrings;
    }

}
