import com.jobPortalScraper.scraper.ScraperUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SplashScreenPage extends JFrame {
    private int WIDTH = 640, HEIGHT=400;
    public SplashScreenPage() {

        this.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((int) (screenSize.getWidth() - this.WIDTH)/2, (int) (screenSize.getHeight() - this.HEIGHT)/2 -100);
        this.setSize(this.WIDTH,this.HEIGHT);


//        JPanel pnl = new JPanel();
//        pnl.setBackground(Color.red);
//        pnl.setLayout(null);
//        pnl.setOpaque(false);
//        pnl.setBounds(50,50,WIDTH-100,HEIGHT-100);
//
//
//        JLabel title = new JLabel("Insight Job");
//        title.setFont(new Font("Arial", Font.BOLD, 40));
//        title.setForeground(Color.white);
//        title.setBounds(0,0,250,54);
//        pnl.add(title);
//
//        this.add(pnl);

        /**
         * Background image
         */
        BackgroundPanel bgPanel = new BackgroundPanel();
        bgPanel.setOpaque(true);
        bgPanel.setBounds(0,0,WIDTH,HEIGHT);
        this.add(bgPanel);
        this.setVisible(true);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.dispose();
        DashboardPage dashboard = new DashboardPage();
    }

    private static class BackgroundPanel extends JPanel {
        BackgroundPanel() {
            super();
            this.setPreferredSize(new Dimension(640, 400));
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2D = (Graphics2D) g;
            ScraperUtils.dd("Loading image...");
            String bgPath = "./splash screen_with_text.png";
            if(new File(bgPath).exists()){
                Image img = new ImageIcon(bgPath).getImage();
                g2D.drawImage(img, 0,0, null);
            } else {
                Color startColor = Color.decode("#a8c0ff");
                Color endColor = Color.decode("#3f2b96");

                int startX = 0, startY = 0, endX = 0, endY = 400;

                GradientPaint gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2D.setPaint(gradient);
                g2D.fillRect(0,0, 640,400);
            }
        }
    }

}
