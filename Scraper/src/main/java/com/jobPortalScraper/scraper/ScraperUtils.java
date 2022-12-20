package com.jobPortalScraper.scraper;

public class ScraperUtils {
    public static boolean debug = false;

    public static void setDebug(boolean debug) {
        ScraperUtils.debug = debug;
    }

    public static  void setDebug() {
        ScraperUtils.debug = true;
    }

    public static void dd(String message) {
        if(! ScraperUtils.debug) return;
        System.out.println(message);
    }
}
