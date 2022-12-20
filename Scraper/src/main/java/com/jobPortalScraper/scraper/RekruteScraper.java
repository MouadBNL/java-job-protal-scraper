package com.jobPortalScraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class RekruteScraper extends BaseScraper{

    public RekruteScraper() {
        super("https://www.rekrute.com/", "https://www.rekrute.com/offres.html?p={X}&s=1&o=1");
    }

    @Override
    public void loadPagesNumber() {
        try {
            Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?p=2&s=1&o=1").get();
            String selector = "#fortopscroll > div.main.alt-main > div > div > div > div.col-md-9 > div > div.slide-block > div:nth-child(1) > div.section > span";
            Element page = doc.selectFirst(selector);
            String output = page.text();
            String[] strPage = output.split(" ");
            String pg = strPage[strPage.length -1];
            this.pagesNumber = Integer.parseInt(pg);

        } catch (Exception e) {
            this.pagesNumber = 0;
            System.out.println(e.getStackTrace());
        }
        System.out.println("Found " + this.pagesNumber + " pages.");
    }

    @Override
    public void fetchAllPostsFromPage(String pageUrl) {

    }

    @Override
    public DataItem fetchAttributesFromPost(String postUrl) {
        return null;
    }
}
