package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class EmploiScraper extends BaseScraper {

    public EmploiScraper() {
        super("Emploi", "https://www.emploi.ma", "https://www.emploi.ma/recherche-jobs-maroc?page={X}");
    }

    @Override
    protected int loadPagesNumber() {
//        https://www.emploi.ma/recherche-jobs-maroc?page=1

        try {
            Document doc = Jsoup.connect("https://www.emploi.ma/recherche-jobs-maroc").get();
            String selector = "a[title=\"Aller à la dernière page\"]";

            Element page = doc.selectFirst(selector);
            assert page != null;
            String output = page.text();
            return Integer.parseInt(output);
        } catch (Exception e) {
            this.pagesNumber = 0;
            System.out.println(Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    @Override
    public void fetchPagesUrls() {
        ScraperUtils.dd("Generating pages URls...");
        for (int i = 0; i < this.pagesNumber; i++) {
            this.pagesUrl.add(this.pagesUrlFormat.replace("{X}", "" + i));
            if(i < 5) {
                ScraperUtils.dd(this.pagesUrlFormat.replace("{X}", "" + i));
            }
            if(i == 5) {
                ScraperUtils.dd("...");
            }
        }
    }

    @Override
    protected void fetchAllPostsFromPage(String pageUrl) {
        try {
            Document pageDoc = Jsoup.connect(pageUrl).get();
            String postTitleSelector = ".job-description-wrapper";
            Elements postsTitles = pageDoc.select(postTitleSelector);

            ScraperUtils.dd("Number of posts found: " + postsTitles.size());
            for(Element title: postsTitles) {

                this.postsUrl.add(title.attr("data-href"));
                ScraperUtils.dd("Post Url: " + title.attr("data-href"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected String fetchPostTitle(Document doc){
        try {
            String selector = "h1.title";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Post title: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching title");
//            e.printStackTrace();
            return "";
        }
    }


    protected String fetchPostPublishDate(Document doc){
        try {
            String selector = "div.job-ad-publication-date";
            String content = doc.selectFirst(selector)
                    .text()
                    .replace("Publiée le ", "")
                    .trim();
            ScraperUtils.dd("Publish date: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching publish date");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostCompanyName(Document doc){
        try {
            String selector = ".job-ad-company .company-title";
            String content = doc.selectFirst(selector).text();
            ScraperUtils.dd("Company name: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching company name");
            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostCompanyAddress(Document doc){
        try {
            String selector = "div.job-ad-company-description > a";
            String content = doc.selectFirst(selector).attr("href");
            ScraperUtils.dd("Company URL:" + this.baseUrl + content);
            doc = Jsoup.connect(this.baseUrl + content).get();
            Elements details = doc.select("#company-profile-details > tbody > tr");
            String country = "", city = "";
            for(Element d: details) {
                if(d.selectFirst("#company-profile-city") != null){
                    city = d.selectFirst("td:nth-child(3)").text();
                }
                if(d.selectFirst("#company-profile-country") != null){
                    country = d.selectFirst("td:nth-child(3)").text();
                }
            }
            content = country + ", " + city;
            ScraperUtils.dd("Company address: " + content);
            return content;
        }catch (Exception e) {
            ScraperUtils.dd("Error fetching company address");
            e.printStackTrace();
            return "";
        }
    }
    protected String fetchPostCompanyWebsite(Document doc) {
        try {
            String selector = ".website-url > a";
            String content = doc.selectFirst(selector).attr("href");
            ScraperUtils.dd("Company website: " + content);
            return content;
        }catch (Exception e) {
            ScraperUtils.dd("Error fetching company website");
            return "";
        }
    }
    protected String fetchPostCompanyDescription(Document doc){
        try {
            String selector = "div.job-ad-company-description > a";
            String content = doc.selectFirst(selector).attr("href");
            ScraperUtils.dd("Company URL:" + this.baseUrl + content);
            doc = Jsoup.connect(this.baseUrl + content).get();
            content = doc.selectFirst(".company-profile-description").text().substring(27).trim();
            ScraperUtils.dd("Company description: " + content.substring(0, 40) + "...");
            return content;
        }catch (Exception e) {
            ScraperUtils.dd("Error fetching company description");
            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostDescription(Document doc){
        try {
//
            String selector = ".jobs-ad-details > .inner > .content > div:nth-child(1)";

            String content = doc.select(selector).text().trim();
            ScraperUtils.dd("Post description: " + content.substring(0, 40) + "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post description.");
            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostCity(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Ville :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Post city: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post city.");
            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostRegion(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Région :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Post region: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post region.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostSector(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Secteur d´activité :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Post sector: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post sector.");
//            e.printStackTrace();
            return "";
        }
    }
    protected String fetchPostContractType(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Type de contrat :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Post contract: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post contract.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostEducationLevel(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Niveau d'études :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Education level: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post education level.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostDiploma(Document doc){
        try {
            return "";
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post diploma.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostExperience(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Niveau d'expérience :")) {
                    content = el.select("td").get(1).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Post Experiences: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post experience.");
//            e.printStackTrace();
            return "";
        }
    }


    protected String fetchPostProfileSearched(Document doc){
        try {
            String selector = ".ad-ss-title" ;


            String content = doc.select(selector).text();
            ScraperUtils.dd("Post searched profile: "  +content+ "...");
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post searched profile.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostLanguage(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Langues exigées :")) {
                    content = el.select("td").get(1).selectFirst("span").text().trim();
                    break;
                }
            }
            ScraperUtils.dd("langue: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post education level.");
//            e.printStackTrace();
            return "";
        }
    }
    protected String fetchPostLanguageLevel(Document doc){
        try {
            String selector = ".job-ad-criteria tr";
            Elements els = doc.select(selector);
            String content = "";
            for(Element el: els) {
                if(el.selectFirst("td").text().trim().equals("Langues exigées :")) {
                    content = el.select("td").get(1).select("span").get(2).text().trim();
                    break;
                }
            }
            ScraperUtils.dd("Niveau de langue: " + content);
            return content;
        } catch (Exception e) {
            ScraperUtils.dd("Error fetching post education level.");
//            e.printStackTrace();
            return "";
        }
    }

    protected String fetchPostRemote(Document doc){

        ScraperUtils.dd("Post Remote: Not specified");

        return "Non";
    }

    public static DataItem normalizeDataItem(DataItem item) {
        /**
         * Formating the sector
         */
        String sector = item.getSector();
        try {
            File f = new File("emploi_sectors.txt");
            Scanner r = new Scanner(f);
            while (r.hasNextLine()) {
                String l = r.nextLine();
                String[] sec_var = l.split(" => ");

                if(
                        sector.trim().toLowerCase().contains(sec_var[1].trim().toLowerCase()) ||
                        sec_var[1].trim().toLowerCase().contains(sector.trim().toLowerCase())
                ) {
                    sector = sec_var[0].trim().toLowerCase();
                    break;
                }
            }
        } catch (Exception e) {

        }
        ScraperUtils.dd("New Sector: " + sector);
        item.setSector(sector);

        return item;
    }


}
