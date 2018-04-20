package com.heimdall.bifrost.controllers;


import com.heimdall.bifrost.exceptions.ImageVisionException;
import com.heimdall.bifrost.services.GoogleVisionRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageUploadController {
    GoogleVisionRequests googleVisionRequests = new GoogleVisionRequests();
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
    public List<String> takeFile(@RequestBody byte[] bytes){
        return googleVisionRequests.getTagsFromAnImage(bytes).orElseThrow(() -> new ImagingOpException("Oops"));
    }




}
