package com.heimdall.bifrost.services;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.methods.RequestBuilder;
import java.util.ArrayList;

public class GoogleTranslateRequests {

    Logger logger = LoggerFactory.getLogger(GoogleTranslateRequests.class);

    private RestTemplate template;
    private String requestUrl;

    public GoogleTranslateRequests() {
        template = new RestTemplate();
        requestUrl = "https://translation.googleapis.com/language/translate/v2?target=pl&key=AIzaSyDjkHIGT-bFJemoNR7HtfnTYCgV9NviEis";
    }

    public ArrayList<String> translateStrings(ArrayList<String> stringsToTranslate) {
        HttpEntity<String> entity = new HttpEntity<>("");

        //String url =  String.copyValueOf(requestUrl.toCharArray(),0,requestUrl.length());
        String url = requestUrl;
        for (String stringToTranslate : stringsToTranslate) {
            url += "&q=" + stringToTranslate;
        }

        ResponseEntity<String> jsonResponse = template.exchange(url, HttpMethod.GET, entity, String.class);
        JSONObject jsonObject = new JSONObject(jsonResponse.getBody());

        ArrayList<String> translateStrings = new ArrayList<>(stringsToTranslate.size());
        for (int i = 0; i < stringsToTranslate.size(); i++) {
            translateStrings.add(jsonObject.optJSONObject("data").optJSONArray("translations").getJSONObject(i).get("translatedText").toString());
        }

        return translateStrings;
    }

}
