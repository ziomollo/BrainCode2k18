package com.heimdall.bifrost.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.heimdall.bifrost.services.AllegroRequests;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class Test {

    @RequestMapping(value = "/addimage", method = RequestMethod.POST)
    public @ResponseBody
    String convertimagetopdf(@RequestBody String imagedat) {
        System.out.println(imagedat);
        String json = new JSONObject().put("obrazek",imagedat).toString();
        return json;
    }

    @GetMapping(value = "/tescik")
    public String ebin(){
        return "DZialczy";
    }
}
