package org.JobPortalScraper.ScraperUI;

public class ScraperUIManager {
    public static void start() {
        System.out.println("Scraper UI: starter...");

        System.out.println("Starting splash screen...");
        SplashScreenPage screen = new SplashScreenPage();

        System.out.println("Starting dashboard...");
        DashboardPage dashboardPage = new DashboardPage();
    }
}
