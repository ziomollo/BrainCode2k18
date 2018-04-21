package pl.braincode.heimdall.models;

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

}
