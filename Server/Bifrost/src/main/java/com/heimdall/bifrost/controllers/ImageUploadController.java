package com.heimdall.bifrost.controllers;


import com.heimdall.bifrost.models.ResultItem;
import com.heimdall.bifrost.models.SearchPhrase;
import com.heimdall.bifrost.services.AllegroRequests;
import com.heimdall.bifrost.services.GoogleVisionRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageUploadController {

    private GoogleVisionRequests googleVisionRequests;

    private final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    private AllegroRequests allegroRequests;

    ImageUploadController(){
        allegroRequests = new AllegroRequests();
        googleVisionRequests = new GoogleVisionRequests();
    }

    @PostMapping(value = "/image")
    public List<SearchPhrase> takeFile(@RequestBody byte[] bytes){
        logger.info("Going to return list of search phrases");
       return googleVisionRequests.getSearchPhrases(bytes);
    }

    @PostMapping(value = "/search")
    public List<ResultItem> search(@RequestBody SearchPhrase phrase){
        logger.info("Going to return list of search product results");
        return allegroRequests.searchForProducts(phrase.getPhrase());

    }
}
