package com.heimdall.bifrost.models;

import org.json.JSONObject;

import java.util.Optional;
import java.util.OptionalDouble;

public class ResultItem {

    public String title;
    public String siteUrl;
    public String pictureUrl;
    public double buyNowPrice;
    public double auctionPrice;

    public ResultItem(String title, String siteUrl, String pictureUrl, double buyNowPrice, double auctionPrice) {
        this.title = title;
        this.siteUrl = siteUrl;
        this.pictureUrl = pictureUrl;
        this.buyNowPrice = buyNowPrice;
        this.auctionPrice = auctionPrice;
    }

    public static ResultItem fromJson(String json){
        JSONObject object = new JSONObject(json);
        String pictureUrl = object.getJSONArray("images").getJSONObject(0).get("url").toString();
        String title = object.get("name").toString();
        String siteUrl = object.get("url").toString();
        double buyNowPrice = isBuyNow(object) ? object.getJSONObject("prices").getJSONObject("buyNow").getDouble("amount") : 0;
        double auctionPrice = isAuction(object) ? object.getJSONObject("prices").getJSONObject("current").getDouble("amount") : 0;
        return new ResultItem(title,siteUrl,pictureUrl,buyNowPrice,auctionPrice);
    }

    private static boolean isAuction(JSONObject object){
        return object.getBoolean("auction");
    }

    private static boolean isBuyNow(JSONObject object){
        return object.getBoolean("buyNow");
    }

}