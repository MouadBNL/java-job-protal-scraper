package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public abstract class BaseScraper {

    protected String siteName;
    protected String baseUrl;
    protected String pagesUrlFormat;
    protected int pagesNumber;
    protected ArrayList<String> pagesUrl;
    protected  ArrayList<String> postsUrl;
    protected ArrayList<DataItem> posts;
    protected int maxPageToScrape = 2;
    protected int maxPostsToScrape = 20;

    public BaseScraper(String siteName, String baseUrl, String pagesUrlFormat) {
        this.siteName = siteName;
        this.baseUrl = baseUrl;
        this.pagesUrlFormat = pagesUrlFormat;
        this.pagesUrl = new ArrayList<>();
        this.postsUrl = new ArrayList<>();
        this.posts = new ArrayList<>();
        ScraperUtils.dd("Created new Scraper {baseUrl:  "+baseUrl+"}");
    }

    public static String version() {
        return "BaseScraper version 1.0-SNAPSHOT";
    }

    @Override
    public String toString() {
        return "BaseScraper {baseurl: "+ this.baseUrl +", pagesUrlFormat: "+this.pagesUrlFormat+"}";
    }

    public void fetchPageNumber() {
        int nbr = this.loadPagesNumber();
        this.pagesNumber = nbr;
        ScraperUtils.dd("Number of pages found: " + nbr);
    }

    public void fetchPagesUrls() {
        ScraperUtils.dd("Generating pages URls...");
        for (int i = 1; i <= this.pagesNumber; i++) {
            this.pagesUrl.add(this.pagesUrlFormat.replace("{X}", "" + i));
            if(i < 5) {
                ScraperUtils.dd(this.pagesUrlFormat.replace("{X}", "" + i));
            }
            if(i == 5) {
                ScraperUtils.dd("...");
            }
        }
    }

    public void fetchAllPostsUrl() {
        int maxPage = this.maxPageToScrape;
        for (String pageUrl : this.pagesUrl) {
            ScraperUtils.dd("Fetching data from page: " + pageUrl);
            this.fetchAllPostsFromPage(pageUrl);
            maxPage--;
            if(ScraperUtils.debug && maxPage == 0) {
                ScraperUtils.dd("...");
                break;
            }
        }
    }

    public void fetchAllPostsAttributes() {
        for(String postUrl: this.postsUrl) {
            ScraperUtils.dd("Loading data from post: " + postUrl);
            this.posts.add(fetchAttributesFromPost(postUrl));
        }
    }

    protected abstract int loadPagesNumber();

    protected abstract void fetchAllPostsFromPage(String pageUrl);
    protected DataItem fetchAttributesFromPost(String postUrl) {
        DataItem item = new DataItem(this.siteName, postUrl);
        Document doc;
        try {
            doc = Jsoup.connect(postUrl).get();
        } catch (Exception e) {
            ScraperUtils.dd(e.getMessage());
            ScraperUtils.dd("Error loading page. Skipping...");
            return null;
        }
        item.setTitle(this.fetchPostTitle(doc));
        item.setPublishDate(this.fetchPostPublishDate(doc));
        item.setApplyDate(this.fetchPostApplyDate(doc));
        item.setCompanyName(this.fetchPostCompanyName(doc));
        item.setCompanyAddress(this.fetchPostCompanyAddress(doc));
        item.setCompanyWebsite(this.fetchPostCompanyWebsite(doc));
        item.setCompanyDescription(this.fetchPostCompanyDescription(doc));
        item.setDescription(this.fetchPostDescription(doc));
        item.setCity(this.fetchPostCity(doc));
        item.setRegion(this.fetchPostRegion(doc));
        item.setSector(this.fetchPostSector(doc));
        item.setJob(this.fetchPostJob(doc));
        item.setContractType(this.fetchPostContractType(doc));
        item.setEducationLevel(this.fetchPostEducationLevel(doc));
        item.setDiploma(this.fetchPostDiploma(doc));
        item.setExperience(this.fetchPostExperience(doc));
        item.setProfileSearched(this.fetchPostProfileSearched(doc));
        item.setPersonalityTraits(this.fetchPostPersonalityTraits(doc));
        item.setHardSkills(this.fetchPostHardSkills(doc));
        item.setSoftSkills(this.fetchPostSoftSkills(doc));
        item.setRecommendedSkills(this.fetchPostRecommendedSkills(doc));
        item.setLanguage(this.fetchPostLanguage(doc));
        item.setLanguageLevel(this.fetchPostLanguageLevel(doc));
        item.setSalary(this.fetchPostSalary(doc));
        item.setSocialAdvantages(this.fetchPostSocialAdvantages(doc));
        item.setRemote(this.fetchPostRemote(doc));

        ScraperUtils.dd("Total Size: " + item.getFormatedSize());

        return item;
    };

    /**
     * Custom methods for fetching each attribute
     * @return
     */
    protected String fetchPostTitle(Document doc){
        return "";
    }
    protected String fetchPostPublishDate(Document doc){
        return "";
    }
    protected String fetchPostApplyDate(Document doc){
        return "";
    }
    protected String fetchPostCompanyName(Document doc){
        return "";
    }
    protected String fetchPostCompanyAddress(Document doc){
        return "";
    }
    protected String fetchPostCompanyWebsite(Document doc){
        return "";
    }
    protected String fetchPostCompanyDescription(Document doc){
        return "";
    }
    protected String fetchPostDescription(Document doc){
        return "";
    }
    protected String fetchPostCity(Document doc){
        return "";
    }
    protected String fetchPostRegion(Document doc){
        return "";
    }
    protected String fetchPostSector(Document doc){
        return "";
    }
    protected String fetchPostJob(Document doc){
        return "";
    }
    protected String fetchPostContractType(Document doc){
        return "";
    }
    protected String fetchPostEducationLevel(Document doc){
        return "";
    }
    protected String fetchPostDiploma(Document doc){
        return "";
    }
    protected String fetchPostExperience(Document doc){
        return "";
    }
    protected String fetchPostProfileSearched(Document doc){
        return "";
    }
    protected String fetchPostPersonalityTraits(Document doc){
        return "";
    }
    protected ArrayList<String> fetchPostHardSkills(Document doc){
        return new ArrayList<>();
    }
    protected ArrayList<String> fetchPostSoftSkills(Document doc){
        return new ArrayList<>();
    }
    protected ArrayList<String> fetchPostRecommendedSkills(Document doc){
        return new ArrayList<>();
    }
    protected String fetchPostLanguage(Document doc){
        return "";
    }
    protected String fetchPostLanguageLevel(Document doc){
        return "";
    }
    protected String fetchPostSalary(Document doc){
        return "";
    }
    protected String fetchPostSocialAdvantages(Document doc){
        return "";
    }
    protected String fetchPostRemote(Document doc){
        return "";
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPagesUrlFormat() {
        return pagesUrlFormat;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public ArrayList<String> getPagesUrl() {
        return pagesUrl;
    }

    public ArrayList<String> getPostsUrl() {
        return postsUrl;
    }

    public ArrayList<DataItem> getPosts() {
        return posts;
    }
}
