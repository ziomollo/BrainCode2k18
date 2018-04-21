package com.heimdall.bifrost.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heimdall.bifrost.models.Entity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleVisionRequests {

    Logger logger = LoggerFactory.getLogger(GoogleVisionRequests.class);

    private RestTemplate template;
    private String requestUrl;

    public GoogleVisionRequests() {
        template = new RestTemplate();
        requestUrl = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDjkHIGT-bFJemoNR7HtfnTYCgV9NviEis";
    }

    public JSONObject getImageDetails(byte[] bytes){
       String request = "{\n" +
               " \"requests\":[\n" +
               "   {\n" +
               "     \"image\":{\n" +
               "       \"content\": " + '"' + java.util.Base64.getEncoder().encodeToString(bytes) + '"' +
               "     },\n" +
               "     \"features\":[\n" +
               "       {\n" +
               "         \"type\":\"WEB_DETECTION\"\n" +
               "       }\n" +
               "     ]\n" +
               "   }\n" +
               " ]\n" +
               "}";
       HttpEntity<String> entity = new HttpEntity<>(request);
       ResponseEntity<String> jsonResponse = template.exchange(requestUrl,HttpMethod.POST,entity,String.class);
       return new JSONObject(jsonResponse.getBody());
    }



}
