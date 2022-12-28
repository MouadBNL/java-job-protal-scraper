package org.JobPortalScraper.ScraperUI;

import com.jobPortalScraper.scraper.RekruteScraper;
import com.jobPortalScraper.scraper.ScraperListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DashboardPage extends JFrame {
    protected JLabel lbl_logs;
    protected JProgressBar action_progress;
    protected JButton mysql_start;
    protected JButton scraping_mysql;
    protected JTextPane txt_logs;
    protected int total = 1;
    protected RekruteScraper rekruteScraper;

    public DashboardPage() {
        initialize();
        this.setVisible(true);

        rekruteScraper = new RekruteScraper();
        rekruteScraper.setListener(new ScraperListener() {
            @Override
            public void updateTotalPages(int pages) {
                lbl_logs.setText("Number of pages found: " + pages);
                System.out.println("Number of pages found: " + pages);
                txt_logs.setText(txt_logs.getText() + "Number of pages found: " + pages + "\n");
            }

            @Override
            public void updateTotalPosts(int posts) {
                lbl_logs.setText("Number of posts found: " + posts);
                System.out.println("Number of posts found: " + posts);
                txt_logs.setText(txt_logs.getText() + "Number of posts found: " + posts + "\n");
                total = posts;
            }

            @Override
            public void updateCurrentPost(int post, String url) {
                int progress = (post * 100) / total;
                lbl_logs.setText("Scraping "+progress+"% : " + url);
                action_progress.setValue(progress);
                System.out.println("Scraping "+progress+"% : " + url);
                txt_logs.setText(txt_logs.getText() + "Scraping "+progress+"% : " + url + "\n");

            }

            @Override
            public void updateCurrentStorage(int current) {
                int progress = (current * 100) / total;
                lbl_logs.setText("Storing "+progress+"%.");
                action_progress.setValue(progress);
                System.out.println("Storing "+progress+"%.");
                txt_logs.setText(txt_logs.getText() + "Storing "+progress+"%." + "\n");
            }

            @Override
            public void finishedMysqlStorage(int success, int failed) {
                JOptionPane.showMessageDialog(null, "Finished storing: "+success+" success, "+failed+" failed.");
                txt_logs.setText(txt_logs.getText() + "Finished storing: "+success+" success, "+failed+" failed." + "\n");
            }
        });
    }


    private void startSelection(String type, String title) {
        SelectionPage select = new SelectionPage(type,title);
    }

    private void initialize() {
        this.setResizable(false);
        this.setTitle("Job Insight");
        this.setBounds(100, 100, 821, 544);
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
        main.setLayout(new BorderLayout(0, 0));

        JPanel scraper = new JPanel();
        scraper.setBorder(new LineBorder(new Color(192, 191, 188), 1, true));
        scraper.setBackground(new Color(255, 255, 255));
        main.add(scraper);
        scraper.setLayout(new BorderLayout(0, 0));

        JPanel container = new JPanel();
        container.setBorder(new EmptyBorder(30, 30, 30, 30));
        container.setBackground(new Color(255, 255, 255));
        scraper.add(container, BorderLayout.CENTER);
        container.setLayout(null);

        JLabel lblSelectSource = new JLabel("Select source");
        lblSelectSource.setBounds(12, 12, 174, 17);
        container.add(lblSelectSource);

        JComboBox select_src = new JComboBox();
        select_src.setModel(new DefaultComboBoxModel(new String[] {"Rekrute.ma", "Emploi.ma"}));
        select_src.setBounds(12, 41, 735, 26);
        container.add(select_src);

        JLabel lblScraping = new JLabel("Scraping");
        lblScraping.setBounds(12, 79, 60, 17);
        container.add(lblScraping);

        scraping_mysql = new JButton("Start");
        scraping_mysql.setBounds(642, 137, 105, 27);
        scraping_mysql.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                start();
            }
        });
        container.add(scraping_mysql);

        action_progress = new JProgressBar();
        action_progress.setBounds(12, 108, 735, 17);
        container.add(action_progress);

        mysql_start = new JButton("Save to mysql");
        mysql_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                store();
            }
        });
        mysql_start.setBounds(502, 137, 128, 27);
        container.add(mysql_start);

        lbl_logs = new JLabel("scraping: 0%");
        lbl_logs.setBounds(12, 142, 472, 17);
        container.add(lbl_logs);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 191, 188)), "Machine learning", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setOpaque(false);
        panel.setBounds(12, 308, 735, 61);
        container.add(panel);

        JButton open_classification = new JButton("Start classification");
        open_classification.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startSelection("classification", "Job Insight - Select Classification algorithm");
            }
        });
        panel.add(open_classification);

        JButton open_clustering = new JButton("Start clustering");
        open_clustering.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startSelection("clustering", "Job Insight - Select Clustering algorithm");
            }
        });
        panel.add(open_clustering);

        JScrollPane txt_logs_pane = new JScrollPane();
        txt_logs_pane.setBackground(new Color(246, 245, 244));
        txt_logs_pane.setBounds(12, 176, 735, 120);
        txt_logs = new JTextPane();
        txt_logs.setBackground(new Color(246, 245, 244));
        txt_logs.setText("Dashboard started.\n");
        container.add(txt_logs);
        txt_logs_pane.setViewportView(txt_logs);
        container.add(txt_logs_pane);
    }

    public void store() {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                scraping_mysql.setEnabled(false);
                mysql_start.setEnabled(false);
                action_progress.setValue(0);
                lbl_logs.setText("Mysql storage: starting...");
                rekruteScraper.storeAllPosts();
                scraping_mysql.setEnabled(true);
                mysql_start.setEnabled(true);
                return null;
            }
        };
        sw1.execute();
    }

    public void start() {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                scraping_mysql.setEnabled(false);
                mysql_start.setEnabled(false);
                action_progress.setValue(0);

                lbl_logs.setText("Scraping: starting...");
                txt_logs.setText(txt_logs.getText() + "Scraping: starting...\n");
                rekruteScraper.fetchPageNumber();
                rekruteScraper.fetchPagesUrls();
                rekruteScraper.fetchAllPostsUrl();
                rekruteScraper.fetchAllPostsAttributes();
                scraping_mysql.setEnabled(true);
                mysql_start.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Finished scraping. Found " + total + " posts.");
                txt_logs.setText(txt_logs.getText() + "Finished scraping. Found " + total + " posts." + "\n");
                return null;
            }
        };
        sw1.execute();
    }
}