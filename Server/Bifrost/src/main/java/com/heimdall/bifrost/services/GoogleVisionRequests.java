package com.heimdall.bifrost.services;

import com.heimdall.bifrost.models.SearchPhrase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleVisionRequests {

    Logger logger = LoggerFactory.getLogger(GoogleVisionRequests.class);

    private RestTemplate template;
    private String requestUrl;
    private GoogleTranslateRequests translateRequests;
    public GoogleVisionRequests() {
        template = new RestTemplate();
        requestUrl = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDjkHIGT-bFJemoNR7HtfnTYCgV9NviEis";
        translateRequests = new GoogleTranslateRequests();
    }

    public List<SearchPhrase> getSearchPhrases(byte[] bytes){
        ArrayList<SearchPhrase> searchPhrases = new ArrayList<>(10);
        JSONObject googleVisionResult = getImageDetails(bytes);

        googleVisionResult.optJSONArray("responses")
                .optJSONObject(0)
                .optJSONObject("webDetection")
                .optJSONArray("webEntities").iterator().forEachRemaining(o -> {
                    String res = ((JSONObject)o).optString("description");
                    if(!res.isEmpty()){
                        searchPhrases.add(new SearchPhrase(res));
                    }
        });

        ArrayList<String> a = searchPhrases.stream()
                .map(SearchPhrase::getPhrase)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> polishPhrases = translateRequests.translateStrings(a);

        return polishPhrases.stream().map(SearchPhrase::new).collect(Collectors.toList());

    }
    private JSONObject getImageDetails(byte[] bytes){
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
