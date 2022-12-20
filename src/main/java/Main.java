import com.jobPortalScraper.scraper.BaseScraper;
import com.jobPortalScraper.scraper.RekruteScraper;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        System.out.println(BaseScraper.version());

        RekruteScraper sc = new RekruteScraper();
        sc.loadPagesNumber();
    }
}
