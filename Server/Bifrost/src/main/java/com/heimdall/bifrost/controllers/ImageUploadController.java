package com.heimdall.bifrost.controllers;


import com.heimdall.bifrost.models.SearchPhrase;
import com.heimdall.bifrost.services.GoogleVisionRequests;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
public class ImageUploadController {
    private GoogleVisionRequests googleVisionRequests = new GoogleVisionRequests();
    private final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile){
        logger.debug("File upload!");

        if (multipartFile.isEmpty()) {
            return "Daj mi plik";
        }

        return "Dzieki za plik";
    }

    @PostMapping(value = "/image")
    public ArrayList<SearchPhrase> takeFile(@RequestBody byte[] bytes){
        ArrayList<SearchPhrase> searchPhrases = new ArrayList<>(10);
        JSONObject googleVisionResult = googleVisionRequests.getImageDetails(bytes);
        for( int i = 0 ; i < 10 ; i++ ){
            searchPhrases.add(new SearchPhrase( googleVisionResult.getJSONObject("responses").getJSONArray("labelAnnotations").getJSONObject(i).get("description").toString() ));
        }
        return searchPhrases;
    }

    @PostMapping(value = "/imagetest")
    public String mock(@RequestBody byte[] bytes){
        return new JSONObject().put("title","czajnik").put("siteUrl","www.google.pl").put("pictureUrl","www.google.com/pictures").toString();
    }




}
