import com.jobPortalScraper.scraper.DBManager;
import com.jobPortalScraper.scraper.DataItem;
import com.jobPortalScraper.scraper.RekruteScraper;
import com.jobPortalScraper.scraper.ScraperUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DashboardPage extends JFrame {
    private final int WIDTH = 1280, HEIGHT = 720;
    protected DBManager dbManager;
    private RekruteScraper rekruteScraper;
    private JLabel lblScraping;
    private JProgressBar progressBar_2;
    private JButton btnSaveToMysql;
    private ArrayList<DataItem> items;

//    public DashboardPage() {
//        this.setTitle("Job Insight - Dashboard");
//        this.setSize(this.WIDTH,this.HEIGHT);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setBackground(Color.decode("#F4F4F4"));
//
//        /**
//         * Header panel
//         */
//        JPanel header = new JPanel();
//        header.setLayout(new FlowLayout(FlowLayout.LEADING));
//        header.setBackground(Color.decode("#1C1C1C"));
//
//        JLabel lbl = new JLabel("Job Insight");
//        lbl.setForeground(Color.white);
//        lbl.setFont(new Font(lbl.getFont().getFontName(), Font.BOLD, 20));
//        lbl.setIcon(new ImageIcon(new ImageIcon("logo_blue.png").getImage().getScaledInstance(42,42, Image.SCALE_SMOOTH)));
//        lbl.setIconTextGap(15);
//        header.setBorder(new EmptyBorder(10,30,10,30));
//        header.add(lbl);
//
//        /**
//         * Main Content
//         */
//        GridLayout mainLayout = new GridLayout(0,3);
//        mainLayout.setHgap(15);
//        JPanel main = new JPanel(mainLayout);
//        main.setBackground(Color.blue);
//        main.setBorder(new EmptyBorder(15,15,15,15));
//        main.setBounds(30, 100, this.getWidth()-60, this.HEIGHT - 160);
//
//        BorderedPanelComponent scraperPanel = new BorderedPanelComponent();
//        BorderedPanelComponent statsPanel = new BorderedPanelComponent();
//        main.add(scraperPanel);
//        main.add(statsPanel);
//        main.add(new JPanel());
//        main.add(new JPanel());
//        this.add(main);
//
//
//
//        this.setLayout(new BorderLayout());
//        this.add(header, BorderLayout.NORTH);
//
//
//        this.setVisible(true);
//    }


    /**
     * Create the application.
     */
    public DashboardPage() {
        this.dbManager = DBManager.getInstance();
        this.rekruteScraper = new RekruteScraper();
        items = new ArrayList<>();
        this.setTitle("Job Insight");
        this.setBounds(100, 100, 1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel header = new JPanel();
        header.setBackground(new Color(36, 31, 49));
        header.setForeground(new Color(255, 255, 255));
        header.setBorder(new EmptyBorder(15, 30, 15, 15));
        this.getContentPane().add(header, BorderLayout.NORTH);
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JLabel lblJobInsight = new JLabel("Job Insight");
        lblJobInsight.setIconTextGap(15);
        lblJobInsight.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblJobInsight.setForeground(new Color(255, 255, 255));
        lblJobInsight.setIcon(new ImageIcon("logo_blue.png"));
        header.add(lblJobInsight);

        JPanel main = new JPanel();
        main.setBackground(new Color(246, 245, 244));
        main.setBorder(new EmptyBorder(30, 30, 30, 30));
        this.getContentPane().add(main, BorderLayout.CENTER);
        GridBagLayout gbl_main = new GridBagLayout();
        gbl_main.columnWidths = new int[] {0, 0};
        gbl_main.rowHeights = new int[]{0, 0};
        gbl_main.columnWeights = new double[]{2.0, 1.0};
        gbl_main.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        main.setLayout(gbl_main);

        JPanel scraper = new JPanel();
        scraper.setBorder(new LineBorder(new Color(192, 191, 188), 1, true));
        scraper.setBackground(new Color(255, 255, 255));
        scraper.setLayout(null);
        GridBagConstraints gbc_scraper = new GridBagConstraints();
        gbc_scraper.fill = GridBagConstraints.BOTH;
        gbc_scraper.gridx = 0;
        gbc_scraper.gridy = 0;
        main.add(scraper, gbc_scraper);

        JLabel lblNewLabel = new JLabel("Select sources");
        lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblNewLabel.setBounds(30, 30, 140, 17);
        scraper.add(lblNewLabel);

        JCheckBox chckbxRekrutema = new JCheckBox("Rekrute.ma");
        chckbxRekrutema.setBackground(new Color(255, 255, 255));
        chckbxRekrutema.setBounds(30, 69, 114, 25);
        scraper.add(chckbxRekrutema);

        JCheckBox chckbxRekrutema_1 = new JCheckBox("Rekrute.ma");
        chckbxRekrutema_1.setBackground(Color.WHITE);
        chckbxRekrutema_1.setBounds(157, 69, 114, 25);
        scraper.add(chckbxRekrutema_1);

        JCheckBox chckbxRekrutema_1_1 = new JCheckBox("Rekrute.ma");
        chckbxRekrutema_1_1.setBackground(Color.WHITE);
        chckbxRekrutema_1_1.setBounds(285, 69, 114, 25);
        scraper.add(chckbxRekrutema_1_1);

        JCheckBox chckbxRekrutema_1_1_1 = new JCheckBox("Rekrute.ma");
        chckbxRekrutema_1_1_1.setBackground(Color.WHITE);
        chckbxRekrutema_1_1_1.setBounds(285, 105, 114, 25);
        scraper.add(chckbxRekrutema_1_1_1);

        JCheckBox chckbxRekrutema_1_2 = new JCheckBox("Rekrute.ma");
        chckbxRekrutema_1_2.setBackground(Color.WHITE);
        chckbxRekrutema_1_2.setBounds(157, 105, 114, 25);
        scraper.add(chckbxRekrutema_1_2);

        JCheckBox chckbxRekrutema_2 = new JCheckBox("Rekrute.ma");
        chckbxRekrutema_2.setBackground(Color.WHITE);
        chckbxRekrutema_2.setBounds(30, 105, 114, 25);
        scraper.add(chckbxRekrutema_2);

        JLabel lblSelectKeywords = new JLabel("Select keywords");
        lblSelectKeywords.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblSelectKeywords.setBounds(30, 173, 140, 17);
        scraper.add(lblSelectKeywords);

        JTextArea txtrTest = new JTextArea();
        txtrTest.setTabSize(4);
        txtrTest.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtrTest.setBorder(new LineBorder(new Color(192, 191, 188), 1, true));
        txtrTest.setBackground(new Color(246, 245, 244));
        txtrTest.setText("test");
        txtrTest.setBounds(30, 210, 615, 201);
        scraper.add(txtrTest);

        JButton btnStart = new JButton("Start");
        btnStart.setForeground(new Color(255, 255, 255));
        btnStart.setBorder(null);
        btnStart.setBackground(new Color(53, 132, 228));
        btnStart.setBounds(540, 423, 105, 27);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                start();
            }
        });
        scraper.add(btnStart);

        btnSaveToMysql = new JButton("Save to mysql");
        btnSaveToMysql.setEnabled(false);
        btnSaveToMysql.setBorder(new LineBorder(new Color(53, 132, 228), 1, true));
        btnSaveToMysql.setBackground(new Color(255, 255, 255));
        btnSaveToMysql.setForeground(new Color(53, 132, 228));
        btnSaveToMysql.setBounds(383, 423, 140, 27);
        btnSaveToMysql.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                store();
            }
        });
        scraper.add(btnSaveToMysql);

        progressBar_2 = new JProgressBar();
        progressBar_2.setBounds(30, 479, 615, 25);
        progressBar_2.setValue(0);
        scraper.add(progressBar_2);

        lblScraping = new JLabel("Scraping 0%");
        lblScraping.setBounds(30, 460, 600, 17);
        scraper.add(lblScraping);

        JPanel statcontainer = new JPanel();
        statcontainer.setBorder(new EmptyBorder(0, 15, 0, 0));
        statcontainer.setBackground(new Color(246, 245, 244));
        GridBagConstraints gbc_statcontainer = new GridBagConstraints();
        gbc_statcontainer.fill = GridBagConstraints.BOTH;
        gbc_statcontainer.gridx = 1;
        gbc_statcontainer.gridy = 0;
        main.add(statcontainer, gbc_statcontainer);
        statcontainer.setLayout(new BorderLayout(0, 0));

        JPanel stats = new JPanel();
        stats.setBorder(new LineBorder(new Color(192, 191, 188), 1, true));
        stats.setBackground(new Color(255, 255, 255));
        statcontainer.add(stats, BorderLayout.CENTER);
        stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(255, 255, 255));
        panel.setMaximumSize(new Dimension(3276, 70));
        stats.add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        panel.setLayout(gbl_panel);

        JLabel lblCpuUsage = new JLabel("CPU Usage");
        GridBagConstraints gbc_lblCpuUsage = new GridBagConstraints();
        gbc_lblCpuUsage.anchor = GridBagConstraints.LINE_START;
        gbc_lblCpuUsage.weightx = 0.5;
        gbc_lblCpuUsage.insets = new Insets(0, 0, 5, 5);
        gbc_lblCpuUsage.gridx = 0;
        gbc_lblCpuUsage.gridy = 0;
        panel.add(lblCpuUsage, gbc_lblCpuUsage);

        JLabel label = new JLabel("40%");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 0, 0);
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel.add(label, gbc_label);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 0, 0);
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        panel.add(panel_1, gbc_panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(null);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(246, 245, 244));
        panel_1.add(progressBar);
        progressBar.setValue(40);

        JPanel panel_2 = new JPanel();
        panel_2.setMaximumSize(new Dimension(3276, 70));
        panel_2.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel_2.setBackground(Color.WHITE);
        stats.add(panel_2);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        panel_2.setLayout(gbl_panel_2);

        JLabel lblCpuUsage_1 = new JLabel("RAM usage");
        GridBagConstraints gbc_lblCpuUsage_1 = new GridBagConstraints();
        gbc_lblCpuUsage_1.weightx = 0.5;
        gbc_lblCpuUsage_1.anchor = GridBagConstraints.LINE_START;
        gbc_lblCpuUsage_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblCpuUsage_1.gridx = 0;
        gbc_lblCpuUsage_1.gridy = 0;
        panel_2.add(lblCpuUsage_1, gbc_lblCpuUsage_1);

        JLabel label_1 = new JLabel("2.5 / 8 Gb - 81%");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 0, 5, 0);
        gbc_label_1.gridx = 1;
        gbc_label_1.gridy = 0;
        panel_2.add(label_1, gbc_label_1);

        JPanel panel_1_1 = new JPanel();
        GridBagConstraints gbc_panel_1_1 = new GridBagConstraints();
        gbc_panel_1_1.insets = new Insets(0, 0, 0, 0);
        gbc_panel_1_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1_1.gridwidth = 2;
        gbc_panel_1_1.gridx = 0;
        gbc_panel_1_1.gridy = 1;
        panel_2.add(panel_1_1, gbc_panel_1_1);
        panel_1_1.setLayout(new BorderLayout(0, 0));

        JProgressBar progressBar_1 = new JProgressBar();
        panel_1_1.add(progressBar_1, BorderLayout.NORTH);
        progressBar_1.setValue(81);
        progressBar_1.setBorderPainted(false);
        progressBar_1.setBorder(null);
        progressBar_1.setBackground(new Color(246, 245, 244));
        this.setVisible(true);

    }

    public void store() {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                lblScraping.setText("Mysql: starting...");
                ScraperUtils.dd("Connecting to database...");
                try {
                    Connection conn = dbManager.makeConnection();
                    int counter = 0;
                    int success = 0;
                    int failed = 0;
                    int total = items.size();
                    for (DataItem itm: items) {
                        counter++;
                        try {
                            PreparedStatement st = itm.getInsertStatement(conn);
                            //                ScraperUtils.dd("Starting insertion");
                            //                ScraperUtils.dd(st.toString());
                            st.execute();
                            ScraperUtils.dd("Insertion success, num: "+counter+".");
                            success++;
                        } catch (Exception e) {
                            ScraperUtils.dd("Insertion Error, num: "+counter+". " + e.getMessage());
                            failed++;
                        }
                        progressBar_2.setValue((counter*100)/total);
                        lblScraping.setText("Storing: " + ((counter*100)/total) + "%.");
                    }
                    conn.close();

                    JOptionPane.showMessageDialog(null, "Finished storing. Success: "+success+", Failed: "+failed+".");
                } catch (Exception e) {
                    ScraperUtils.dd("Connection failed.");
                    lblScraping.setText("Mysql Error: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };
        sw1.execute();
    }

    public void start() {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                lblScraping.setText("Scraping: starting...");

                System.out.println("Starting...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("fetchPageNumber...");
                rekruteScraper.fetchPageNumber();

                System.out.println("fetchPagesUrls...");
                rekruteScraper.fetchPagesUrls();

                System.out.println("fetchAllPostsUrl...");
                lblScraping.setText("Scraping : Fetching all posts url (may take a few minutes)...");
                rekruteScraper.fetchAllPostsUrl();

                System.out.println("getPostsUrl...");
                ArrayList<String> posts = rekruteScraper.getPostsUrl();
                int total = posts.size();
                int current = 0;
                lblScraping.setText("Scraping: found " + total + " posts");
                System.out.println("Scraping: found " + total + " posts");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for(String p: posts) {
                    current++;
                    progressBar_2.setValue((current*100)/total);
                    System.out.println(((current*100)/total) + "%");
                    lblScraping.setText("Scraping "+((current*100)/total)+"% : " + p);
                    items.add(rekruteScraper.fetchAttributesFromPost(p));
                }
                lblScraping.setText("Scraping: Finished.");
                btnSaveToMysql.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Finished scraping. Found " + current + " posts.");
                return null;
            }
        };
        sw1.execute();
//        this.lblScraping.setText("Scraping: starting...");
//
//        System.out.println("Starting...");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("fetchPageNumber...");
//        this.rekruteScraper.fetchPageNumber();
//
//        System.out.println("fetchPagesUrls...");
//        this.rekruteScraper.fetchPagesUrls();
//
//        System.out.println("fetchAllPostsUrl...");
//        this.lblScraping.setText("Scraping : Fetching all posts url (may take a few minutes)...");
//        this.rekruteScraper.fetchAllPostsUrl();
//
//        System.out.println("getPostsUrl...");
//        ArrayList<String> posts = this.rekruteScraper.getPostsUrl();
//        int total = posts.size();
//        int current = 0;
//        this.lblScraping.setText("Scraping: found " + total + " posts");
//        System.out.println("Scraping: found " + total + " posts");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        for(String p: posts) {
//            current++;
//            this.progressBar_2.setValue((current*100)/total);
//            System.out.println(((current*100)/total) + "%");
//            this.lblScraping.setText("Scraping "+((current*100)/total)+"% : " + p);
//            this.rekruteScraper.fetchAttributesFromPost(p);
//        }
//        this.lblScraping.setText("Scraping: Finished.");
    }
}
