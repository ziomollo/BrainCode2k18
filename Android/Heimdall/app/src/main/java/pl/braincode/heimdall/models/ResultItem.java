package pl.braincode.heimdall.models;

public class ResultItem {

    public String title;
    public String siteUrl;
    public String pictureUrl;
    public double price;

    public ResultItem(String title, String siteUrl, String pictureUrl, double price) {
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.siteUrl = siteUrl;
        this.price = price;
    }
}
