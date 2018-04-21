package com.heimdall.bifrost.services;

import com.heimdall.bifrost.models.ResultItem;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AllegroRequests {

    private RestTemplate restTemplate;
    public AllegroRequests(){
        restTemplate = new RestTemplate();
    }
    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/vnd.allegro.public.v1+json");
        headers.add("Api-Key","eyJjbGllbnRJZCI6ImE0MWY1YjJhLThlODctNGI4Yi1iNmZlLTc0Y2M3NjM3MjBkNyJ9.ogVV_a9RUOMa1OWFZOTmgTkdk-U37vTliDCBUQ1YySU=");
        headers.add("User-Agent","hackaton2017 (Client-Id 656cbe47-b17d-46c2bae1-3222c8777d5b) Platform");

        return headers;
    }
    public List<ResultItem> searchForProducts(String phrase){

        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());
        HttpRequest request = RequestBuilder.get()
                .setUri("https://allegroapi.io/offers")
                .addParameter("country.code","PL")
                .addParameter("limit","100")
                //.addParameter("phrase",phrase)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(((HttpUriRequest) request).getURI().toString()+String.format("&%s=%s","phrase",phrase), HttpMethod.GET,entity,String.class);
        ArrayList<ResultItem> lista = new ArrayList<>();
        new JSONObject(responseEntity.getBody()).getJSONArray("offers").iterator()
                .forEachRemaining(o -> lista.add(ResultItem.fromJson(o.toString())));

        return lista;
    }

}
