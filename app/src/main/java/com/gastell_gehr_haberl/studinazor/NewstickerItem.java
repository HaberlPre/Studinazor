package com.gastell_gehr_haberl.studinazor;

/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerItem {

    private String author;
    private String title;
    private String description;
    private String  url;
    private String urlToImage;
    private String publishedAt;

    public NewstickerItem (String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

}
