package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    protected int maxPageToScrape = 4;
    protected int maxPostsToScrape = 1000;
    protected ScraperListener listener = null;

    public BaseScraper(String siteName, String baseUrl, String pagesUrlFormat) {
        this.siteName = siteName;
        this.baseUrl = baseUrl;
        this.pagesUrlFormat = pagesUrlFormat;
        this.pagesUrl = new ArrayList<>();
        this.postsUrl = new ArrayList<>();
        this.posts = new ArrayList<>();
        ScraperUtils.dd("Created new Scraper {baseUrl:  "+baseUrl+"}");
        this.dbManager = DBManager.getInstance();
    }

    public void setListener(ScraperListener listener){
        this.listener = listener;
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
        if(this.listener != null) {
            this.listener.updateTotalPages(nbr);
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
    }

    public void fetchAllPostsUrl() {
        int maxPage = this.maxPageToScrape;
        int nbr = 0;
        for (String pageUrl : this.pagesUrl) {
            nbr++;
            ScraperUtils.dd("Fetching data from page: " + pageUrl);
            this.fetchAllPostsFromPage(pageUrl);
            maxPage--;
            if(ScraperUtils.debug && maxPage == 0) {
                ScraperUtils.dd("...");
                break;
            }
        }
        if(this.listener != null) {
            this.listener.updateTotalPosts(this.postsUrl.size());
        }
    }

    public void fetchAllPostsAttributes() {
        int max = this.maxPostsToScrape;
        int nbr = 0;
        for(String postUrl: this.postsUrl) {
            ScraperUtils.dd("Loading data from post: " + postUrl);
            nbr++;
            if(this.listener != null) {
                this.listener.updateCurrentPost(nbr, postUrl);
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
        item.setTitle(this.fetchPostTitle(doc).toLowerCase());
        item.setPublishDate(this.fetchPostPublishDate(doc).toLowerCase());
        item.setApplyDate(this.fetchPostApplyDate(doc).toLowerCase());
        item.setCompanyName(this.fetchPostCompanyName(doc).toLowerCase());
        item.setCompanyAddress(this.fetchPostCompanyAddress(doc).toLowerCase());
        item.setCompanyWebsite(this.fetchPostCompanyWebsite(doc).toLowerCase());
        item.setCompanyDescription(this.fetchPostCompanyDescription(doc).toLowerCase());
        item.setDescription(this.fetchPostDescription(doc).toLowerCase());
        item.setCity(this.fetchPostCity(doc).toLowerCase());
        item.setRegion(this.fetchPostRegion(doc).toLowerCase());
        item.setSector(this.fetchPostSector(doc).toLowerCase());
        item.setJob(this.fetchPostJob(doc).toLowerCase());
        item.setContractType(this.fetchPostContractType(doc).toLowerCase());
        item.setEducationLevel(this.fetchPostEducationLevel(doc).toLowerCase());
        item.setDiploma(this.fetchPostDiploma(doc).toLowerCase());
        item.setExperience(this.fetchPostExperience(doc).toLowerCase());
        item.setProfileSearched(this.fetchPostProfileSearched(doc).toLowerCase());
        item.setPersonalityTraits(this.fetchPostPersonalityTraits(doc).toLowerCase());
        item.setHardSkills(this.fetchPostHardSkills(doc));
        item.setSoftSkills(this.fetchPostSoftSkills(doc));
        item.setRecommendedSkills(this.fetchPostRecommendedSkills(doc));
        item.setLanguage(this.fetchPostLanguage(doc).toLowerCase());
        item.setLanguageLevel(this.fetchPostLanguageLevel(doc).toLowerCase());
        item.setSalary(this.fetchPostSalary(doc).toLowerCase());
        item.setSocialAdvantages(this.fetchPostSocialAdvantages(doc).toLowerCase());
        item.setRemote(this.fetchPostRemote(doc).toLowerCase());

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
            int success = 0;
            int failed = 0;
            for (DataItem itm: this.posts) {
                counter++;
                try {
                    PreparedStatement st = itm.getInsertStatement(conn);
    //                ScraperUtils.dd("Starting insertion");
    //                ScraperUtils.dd(st.toString());
                    st.execute();
                    ScraperUtils.dd("Insertion success, num: "+counter+".");
                    success++;
                } catch (Exception e) {
                    failed++;
                    ScraperUtils.dd("Insertion Error, num: "+counter+". " + e.getMessage());
                }
                if(this.listener != null){
                    this.listener.updateCurrentStorage(counter);
                }
            }

            if(this.listener != null) {
                this.listener.finishedMysqlStorage(success, failed);
            }
            conn.close();
        } catch (Exception e) {
            ScraperUtils.dd("Connection failed.");
            e.printStackTrace();
        }
    }


    public static DataItem normalizeDataItem(DataItem item) {
        return item;
    }
}
