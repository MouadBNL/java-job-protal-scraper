package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;

public class RekruteScraper extends BaseScraper{

    public RekruteScraper() {
        super("Rekrute", "https://www.rekrute.com", "https://www.rekrute.com/offres.html?p={X}&s=1&o=1");
    }

    @Override
    public int loadPagesNumber() {
        try {
            Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?p=2&s=1&o=1").get();
            String selector = "#fortopscroll > div.main.alt-main > div > div > div > div.col-md-9 > div > div.slide-block > div:nth-child(1) > div.section > span";
            Element page = doc.selectFirst(selector);
            assert page != null;
            String output = page.text();
            String[] strPage = output.split(" ");
            String pg = strPage[strPage.length -1];
            return Integer.parseInt(pg);
        } catch (Exception e) {
            this.pagesNumber = 0;
            System.out.println(Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    @Override
    public void fetchAllPostsFromPage(String pageUrl) {
        try {
            Document pageDoc = Jsoup.connect(pageUrl).get();
            String postTitleSelector = "div > div.col-sm-10.col-xs-12 > div > h2 > a";
            Elements postsTitles = pageDoc.select(postTitleSelector);
            ScraperUtils.dd("Number of posts found: " + postsTitles.size());
            for(Element title: postsTitles) {
                this.postsUrl.add(this.baseUrl + title.attr("href"));
                ScraperUtils.dd("Post Url: " + title.attr("href"));
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    protected String fetchPostTitle(Document doc){
        try {
            String selector = "h1";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Post title: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching title");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostPublishDate(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div.col-md-12.info.blc.noback > div > div > div.col-md-12.col-sm-12.col-xs-12 > span";
            String content = doc.selectFirst(selector)
                    .text()
                    .split("-")[0]
                    .replace("Publiée", "")
                    .replace(" sur ReKrute.com", "")
                    .trim();
            ScraperUtils.dd("Publish date: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching publish date");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostApplyDate(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div.col-md-12.info.blc.noback > div > div > div.col-md-12.col-sm-12.col-xs-12 > span";
            String content = doc.selectFirst(selector)
                    .text()
                    .split("-")[1]
                    .replace("Postulez avant le ", "")
                    .trim();
            ScraperUtils.dd("Apply date: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching apply date");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostCompanyName(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div.col-md-12.info.blc.noback > div > div > div.col-md-2.col-sm-12.col-xs-12 > div > a";
            String profileUrl = doc.selectFirst(selector).attr("href");
            Document docPorfile = Jsoup.connect(this.baseUrl + "/" + profileUrl).get();
            selector = "#fortopscroll > div.main.alt-main > div > div > div > div.col-md-9 > div > div.content-holder > div > div:nth-child(2) > p:nth-child(1) > span > a";
            String content = docPorfile.selectFirst(selector).text();
            ScraperUtils.dd("Company name: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching company name");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostCompanyAddress(Document doc){
        try {
            String selector = "#address";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Company address: " + content);
            return content;
        }catch (Exception e) {
            ScraperUtils.dd("Error fetching company name");
            return "";
        }
    }

    @Override
    protected String fetchPostCompanyDescription(Document doc){
        try {
            String selector = "#recruiterDescription";
            String content = doc.selectFirst(selector).text().substring(11).trim();
            ScraperUtils.dd("Company description: " + content.substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching company description.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostDescription(Document doc){
        try {
//                             #fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(5)
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(4)";
            if(! (
                    doc.selectFirst(selector) != null &&
                    doc.selectFirst(selector).selectFirst("h2") != null &&
//                    doc.selectFirst(selector).selectFirst("h2").text() != null &&
                    doc.selectFirst(selector).selectFirst("h2").text().equals("Poste :")
            )) {
                selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(5)";
            }

            String content = doc.selectFirst(selector).text().substring(6).trim();
            ScraperUtils.dd("Post description: " + content.substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post description.");
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostCity(Document doc){
        try {
            String selector = "li[title=\"Région\"]";
            String[] contents = doc.selectFirst(selector).text().split(" - ")[0].split(" ");
            String content = contents[contents.length - 2].trim();
            if(content.equals("et")){
                content = contents[contents.length - 3];
            }
            ScraperUtils.dd("Post city: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post city.");
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostRegion(Document doc){
        try {
            String selector = "li[title=\"Région\"]";
            String content = doc.selectFirst(selector).text().split(" - ")[1].trim();
            ScraperUtils.dd("Post region: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post region.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostSector(Document doc){
        try {
            String selector = "h2.h2italic";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Post sector: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post sector.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostContractType(Document doc){
        try {
            String selector = "span[title=\"Type de contrat\"]";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Post contract: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post contract.");
//            e.printStackTrace();
            return "";
        }
    }


}
