package pl.braincode.heimdall.models;

public class ResultItem {

    public String title;
    public String siteUrl;
    public String pictureUrl;

    public ResultItem(String title, String siteUrl, String pictureUrl) {
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.siteUrl = siteUrl;
    }
}
