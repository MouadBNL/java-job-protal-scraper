package com.jobPortalScraper.scraper;

import java.util.ArrayList;

public abstract class BaseScraper {

    protected String baseUrl;
    protected String pagesUrlFormat;
    protected int pagesNumber;
    protected ArrayList<String> pagesUrl;
    protected  ArrayList<String> postsUrl;
    protected ArrayList<DataItem> posts;

    public BaseScraper(String baseUrl, String pagesUrlFormat) {
        this.baseUrl = baseUrl;
        this.pagesUrlFormat = pagesUrlFormat;
        this.pagesUrl = new ArrayList<>();
        this.postsUrl = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public static String version() {
        return "BaseScraper version 1.0-SNAPSHOT";
    }

    @Override
    public String toString() {
        return "BaseScraper {baseurl: "+ this.baseUrl +", pagesUrlFormat: "+this.pagesUrlFormat+"}";
    }

    public abstract void loadPagesNumber();

    public void fetchPagesUrls() {
        for (int i = 1; i <= this.pagesNumber; i++) {
            this.pagesUrl.add(this.pagesUrlFormat.replace("{X}", "" + i));
        }
    }

    public void fetchAllPosts() {
        for (String pageUrl : this.pagesUrl) {
            this.fetchAllPostsFromPage(pageUrl);
        }
    }

    public abstract void fetchAllPostsFromPage(String pageUrl);

    public void fetchAllPostsAttributes() {
        for(String postUrl: this.postsUrl) {
            this.posts.add(fetchAttributesFromPost(postUrl));
        }
    }
    public abstract DataItem fetchAttributesFromPost(String postUrl);



}
