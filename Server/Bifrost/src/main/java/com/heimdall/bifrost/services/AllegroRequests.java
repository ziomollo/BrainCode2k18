package com.heimdall.bifrost.services;

import com.heimdall.bifrost.allegroapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AllegroRequests {

    private ServiceService service;
    private ServicePort servicePort;
    private long currentVersion;
    private String apikey;

    AllegroRequests(){
        service = new ServiceService();
        servicePort = service.getServicePort();
        currentVersion = getCurrentVersion();
        apikey = "989db4ad";
    }

    private long getCurrentVersion(){
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest();
        request.setCountryId(1);
        request.setWebapiKey("989db4ad");
        DoQueryAllSysStatusResponse response = servicePort.doQueryAllSysStatus(request);

        return response.getSysCountryStatus().getItem().get(0).getVerKey();
    }

    public List<ItemsListType> getItems(){
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        request.setCountryId(1);
        request.setWebapiKey(apikey);

        return Collections.emptyList();
    }



}
