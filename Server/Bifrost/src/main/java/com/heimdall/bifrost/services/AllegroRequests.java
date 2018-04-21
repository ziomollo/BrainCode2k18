package com.heimdall.bifrost.services;

import com.heimdall.bifrost.allegroapi.*;
import com.heimdall.bifrost.models.ResultItem;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllegroRequests {

    private ServiceService service;
    private ServicePort servicePort;
    private long currentVersion;
    private String apikey;
    private RestTemplate restTemplate;
    public AllegroRequests(){
        service = new ServiceService();
        servicePort = service.getServicePort();
        currentVersion = getCurrentVersion();
        apikey = "989db4ad";
        restTemplate = new RestTemplate();
    }
    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/vnd.allegro.public.v1+json");
        headers.add("Api-Key","eyJjbGllbnRJZCI6ImE0MWY1YjJhLThlODctNGI4Yi1iNmZlLTc0Y2M3NjM3MjBkNyJ9.ogVV_a9RUOMa1OWFZOTmgTkdk-U37vTliDCBUQ1YySU=");
        headers.add("User-Agent","hackaton2017 (Client-Id 656cbe47-b17d-46c2bae1-3222c8777d5b) Platform");

        return headers;
    }
    public List<ResultItem> ebin(String phrase){

        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());

        String url = String.format("%s=%s","https://allegroapi.io/offers?country.code=PL&limit=20\\&phrase",phrase);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
        ArrayList<ResultItem> lista = new ArrayList<>();
        new JSONObject(responseEntity.getBody()).getJSONArray("offers").iterator()
                .forEachRemaining(o -> lista.add(ResultItem.fromJson(o.toString())));

        return lista;
    }

    private long getCurrentVersion(){
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest();
        request.setCountryId(1);
        request.setWebapiKey("989db4ad");
        DoQueryAllSysStatusResponse response = servicePort.doQueryAllSysStatus(request);

        return response.getSysCountryStatus().getItem().get(0).getVerKey();
    }

    public List<String> getItems(){
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        request.setCountryId(1);
        request.setWebapiKey(apikey);


        FilterOptionsType filters = new FilterOptionsType();
        filters.setFilterId("search");
        ArrayOfString names = new ArrayOfString();
        //names.
        filters.setFilterValueId(new ArrayOfString());




        DoGetItemsListResponse doGetItemsListResponse = servicePort.doGetItemsList(request);

        DoGetItemsListResponse response = servicePort.doGetItemsList(request);
        ArrayList<String> arrayList = new ArrayList<>();
        response.getItemsList().getItem().stream().forEach(itemsListType -> arrayList.add(itemsListType.getItemTitle()));



        return arrayList;
    }




}
