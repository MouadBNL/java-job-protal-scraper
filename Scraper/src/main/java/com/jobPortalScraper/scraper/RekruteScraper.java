package com.jobPortalScraper.scraper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
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
                    .replace("Publi??e", "")
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
            String content = doc.selectFirst(selector).text().substring(12).trim();
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

            String content = doc.selectFirst(selector).text().substring(7).trim();
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
            String selector = "li[title=\"R??gion\"]";
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
            String selector = "li[title=\"R??gion\"]";
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

    @Override
    protected String fetchPostEducationLevel(Document doc){
        try {
            String selector = "li[title=\"Niveau d'??tude et formation\"]";
            String content = doc.selectFirst(selector).text().split(" - ")[0].trim();
            ScraperUtils.dd("Post education level: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post education level.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostDiploma(Document doc){
        try {
            String selector = "li[title=\"Niveau d'??tude et formation\"]";
            String content = doc.selectFirst(selector).text().split(" - ")[1].trim();
            ScraperUtils.dd("Post diploma: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post diploma.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostExperience(Document doc){
        try {
            String selector = "li[title=\"Exp??rience requise\"]";
            String content = doc.selectFirst(selector).text().split(" - ")[1].trim();
            ScraperUtils.dd("Post experience: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post experience.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostProfileSearched(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(5)";
            if(! (
                    doc.selectFirst(selector) != null &&
                            doc.selectFirst(selector).selectFirst("h2") != null &&
//                    doc.selectFirst(selector).selectFirst("h2").text() != null &&
                            doc.selectFirst(selector).selectFirst("h2").text().equals("Profil recherch?? :")
            )) {
                selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(6)";
            }

            String content = doc.selectFirst(selector).text().substring(18).trim();
            ScraperUtils.dd("Post searched profile: " + content.substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post searched profile.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected String fetchPostPersonalityTraits(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(6)";
            if(! (
                    doc.selectFirst(selector) != null &&
                            doc.selectFirst(selector).selectFirst("h2") != null &&
//                    doc.selectFirst(selector).selectFirst("h2").text() != null &&
                            doc.selectFirst(selector).selectFirst("h2").text().equals("Traits de personnalit?? souhait??s :")
            )) {
                selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(7)";
            }
            Element persoTraits = doc.selectFirst(selector);
            String content = "";
            for(Element el: persoTraits.select(".tagSkills")){
                content = content + el.text().trim() + ", ";
            }
            ScraperUtils.dd("Post personality traits: " + content.substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post personality traits.");
//            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected ArrayList<String> fetchPostSoftSkills(Document doc){
        try {
            String selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(6)";
            if(! (
                    doc.selectFirst(selector) != null &&
                            doc.selectFirst(selector).selectFirst("h2") != null &&
//                    doc.selectFirst(selector).selectFirst("h2").text() != null &&
                            doc.selectFirst(selector).selectFirst("h2").text().equals("Traits de personnalit?? souhait??s :")
            )) {
                selector = "#fortopscroll > div.container.anno > div:nth-child(2) > div > div:nth-child(7)";
            }
            Element persoTraits = doc.selectFirst(selector);
            ArrayList<String> content = new ArrayList<>();
            for(Element el: persoTraits.select(".tagSkills")){
                content.add(el.text().trim());
            }
            ScraperUtils.dd("Post SoftSkills: " + content.toString().substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post SoftSkills.");
//            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected String fetchPostRemote(Document doc){
        try {
            String selector = "span[title=\"T??l??travail\"]";
            String content = doc.selectFirst(selector).text().replace("T??l??travail :", "").trim();
            ScraperUtils.dd("Post remote: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post remote.");
//            e.printStackTrace();
            return "";
        }
    }

    public static DataItem normalizeDataItem(DataItem item) {
        /**
         * Formating the sector
         */
        String sector = item.getSector();
        String[] secs = StringUtils.split(sector, "-/");
        for(String s: secs) {
            if(s.contains("Secteur")) {
                sector = s.replace("Secteur", "").trim();
                break;
            }
        }
        item.setSector(sector);

        return item;
    }


}
