import com.jobPortalScraper.scraper.BaseScraper;
import com.jobPortalScraper.scraper.EmploiScraper;
import com.jobPortalScraper.scraper.ScraperUtils;
import com.sun.management.OperatingSystemMXBean;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Loading app...");
        System.out.println(BaseScraper.version());
        ScraperUtils.setDebug();
        setUIFont(new FontUIResource(new Font("Arial", 0, 12)));
//        SplashScreenPage sc = new SplashScreenPage();

        EmploiScraper sc = new EmploiScraper();

//        Main.printUsage();
        sc.fetchPageNumber();
        sc.fetchPagesUrls();
        sc.fetchAllPostsUrl();
        sc.fetchAllPostsAttributes();
//        sc.storeAllPosts();
    }
    public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
    public static void printUsage() {
        OperatingSystemMXBean ob = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        System.out.println("Cpu Load: "+ ob.getProcessCpuLoad());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cpu Load: "+ ob.getProcessCpuLoad());
    }

}
