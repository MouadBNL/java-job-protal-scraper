package com.jobPortalScraper.scraper;

public interface ScraperListeners {
    public void updateTotalPages(int pages);
    public void updateTotalPosts(int posts);
    public void updateCurrentPostNumber(int posts);
    public void updateCurrentPostUrl(String url);
}
