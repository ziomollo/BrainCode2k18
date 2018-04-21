package com.heimdall.bifrost.controllers;


import com.heimdall.bifrost.models.ResultItem;
import com.heimdall.bifrost.models.SearchPhrase;
import com.heimdall.bifrost.services.AllegroRequests;
import com.heimdall.bifrost.services.GoogleTranslateRequests;
import com.heimdall.bifrost.services.GoogleVisionRequests;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageUploadController {
    private GoogleVisionRequests googleVisionRequests = new GoogleVisionRequests();
    private GoogleTranslateRequests googleTranslateRequests = new GoogleTranslateRequests();

    private final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    AllegroRequests allegroRequests;

    ImageUploadController(){
        allegroRequests = new AllegroRequests();
    }
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
            try{
                String value = googleVisionResult
                        .getJSONArray("responses")
                        .getJSONObject(0)
                        .getJSONObject("webDetection")
                        .getJSONArray("bestGuessLabels")
                        .getJSONObject(i)
                        .get("label")
                        .toString();
                searchPhrases.add(new SearchPhrase( value ));
            }catch(Exception e){

            }
        }

        ArrayList<String> a = searchPhrases.stream()
                .map(searchPhrase -> searchPhrase.phrase)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> polishPhrases = googleTranslateRequests.translateStrings(a);
        for (int i = 0; i < searchPhrases.size(); i++) {
            searchPhrases.get(i).phrase = polishPhrases.get(i);
        }

        return searchPhrases;
    }

    @GetMapping(value = "/search/{phrase}")
    public List<ResultItem> search(@PathVariable("phrase") String phrase){
       // ArrayList<ResultItem> resultItems = new ArrayList<>(10);

        return allegroRequests.ebin(phrase);

    }



    @PostMapping(value = "/imagetest")
    public String mock(@RequestBody byte[] bytes){
        return new JSONObject().put("title","czajnik").put("siteUrl","www.google.pl").put("pictureUrl","www.google.com/pictures").toString();
    }




}
