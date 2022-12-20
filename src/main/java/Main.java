import com.jobPortalScraper.scraper.BaseScraper;
import com.jobPortalScraper.scraper.RekruteScraper;
import com.jobPortalScraper.scraper.ScraperUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        System.out.println(BaseScraper.version());
        ScraperUtils.setDebug();

        RekruteScraper sc = new RekruteScraper();
        sc.fetchPageNumber();
        sc.fetchPagesUrls();
        sc.fetchAllPostsUrl();
        sc.fetchAllPostsAttributes();
    }
}
