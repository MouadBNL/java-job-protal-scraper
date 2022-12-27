package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class BaseScraper {

    protected DBManager dbManager;
    protected String siteName;
    protected String baseUrl;
    protected String pagesUrlFormat;
    protected int pagesNumber;
    protected ArrayList<String> pagesUrl;
    protected  ArrayList<String> postsUrl;
    protected ArrayList<DataItem> posts;
    protected int maxPageToScrape = 2;
    protected int maxPostsToScrape = 1000;
    protected ArrayList<ScraperListeners> listeners;

    public BaseScraper(String siteName, String baseUrl, String pagesUrlFormat) {
        this.siteName = siteName;
        this.baseUrl = baseUrl;
        this.pagesUrlFormat = pagesUrlFormat;
        this.pagesUrl = new ArrayList<>();
        this.postsUrl = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.listeners = new ArrayList<>();
        ScraperUtils.dd("Created new Scraper {baseUrl:  "+baseUrl+"}");
        this.dbManager = DBManager.getInstance();
    }

    public void registerListener(ScraperListeners listener) {
        this.listeners.add(listener);
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
        for(ScraperListeners l: this.listeners){
            l.updateTotalPages(nbr);
        }
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
        for(ScraperListeners l: this.listeners){
            l.updateCurrentPostNumber(this.pagesUrl.size());
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
        int max = this.maxPostsToScrape;
        for(String postUrl: this.postsUrl) {
            ScraperUtils.dd("Loading data from post: " + postUrl);
            for(ScraperListeners l: this.listeners){
                l.updateCurrentPostUrl(postUrl);
            }
            this.posts.add(fetchAttributesFromPost(postUrl));
            max--;
            if(max <= 0) break;
        }
    }

    protected abstract int loadPagesNumber();

    protected abstract void fetchAllPostsFromPage(String pageUrl);
    public DataItem fetchAttributesFromPost(String postUrl) {
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


    public void storeAllPosts() {
        ScraperUtils.dd("Connecting to database...");
        try {
            Connection conn = this.dbManager.makeConnection();
            int counter = 0;
            for (DataItem itm: this.posts) {
                counter++;
                try {
                    PreparedStatement st = itm.getInsertStatement(conn);
    //                ScraperUtils.dd("Starting insertion");
    //                ScraperUtils.dd(st.toString());
                    st.execute();
                    ScraperUtils.dd("Insertion success, num: "+counter+".");
                } catch (Exception e) {
                    ScraperUtils.dd("Insertion Error, num: "+counter+". " + e.getMessage());
                }
            }
            conn.close();
        } catch (Exception e) {
            ScraperUtils.dd("Connection failed.");
            e.printStackTrace();
        }
    }
}
