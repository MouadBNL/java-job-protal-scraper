package org.JobPortalScraper.ScraperUI;

import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPage extends JFrame {

    protected String title;
    private String type;
    private JList selectAlgo;

    private ModelManager modelManager;
    public SelectionPage(String type, String title) {
        this.modelManager = ModelManager.getInstance();
        this.title = title;
        this.type = type;
        this.initialize();
        this.setVisible(true);
    }

    private void startResult() {
        try {
            String algo = selectAlgo.getSelectedValue().toString();
            if(type.equals("classification")) {
                if(algo.equals("ZeroR")){
                    Evaluation eval = modelManager.zeroRModel();
                    new ResultPage(eval);
                    return;
                } else if(algo.equals("J48")) {
                    Evaluation eval = modelManager.j48Model();
                    new ResultPage(eval);
                    return;
                } else if(algo.equals("Filtered classifier")) {
                    Evaluation eval = modelManager.filteredModel();
                    new ResultPage(eval);
                    return;
                }else {
                    JOptionPane.showMessageDialog(this, "Error loading the algorithm");
                }
            } else {
                if(algo.equals("EM")){
                    ClusterEvaluation eval = modelManager.emClusterer();
                    new ResultPage(eval);
                    return;
                }
                if(algo.equals("MakeDensityBasedClusterer")){
                    ClusterEvaluation eval = modelManager.makeDensityBasedClusterer();
                    new ResultPage(eval);
                    return;
                }
                if(algo.equals("Cobweb")){
                    ClusterEvaluation eval = modelManager.cobwebClusterer();
                    new ResultPage(eval);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Not implemented");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Make sure you selected an item.");
            e.printStackTrace();
        }
    }

    private void initialize() {
        this.setResizable(false);
        this.setTitle(this.title);
        this.setBounds(100, 100, 821, 480);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

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

        JLabel lblSelectSource = new JLabel("Select an algorithm. Dataset size: " + modelManager.getDataNbr());
        lblSelectSource.setBounds(12, 12, 274, 17);
        container.add(lblSelectSource);

        selectAlgo = new JList();
        selectAlgo.setModel(new AbstractListModel() {
            String[] values = type.equals("classification") ? modelManager.getClassifiers() : modelManager.getClusters();
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
        selectAlgo.setBackground(new Color(246, 245, 244));
        selectAlgo.setBounds(12, 41, 735, 224);
        container.add(selectAlgo);

//        JLabel lblSelectAttribute = new JLabel("Select Attribute");
//        lblSelectAttribute.setBounds(12, 277, 199, 17);
//        container.add(lblSelectAttribute);

//        JComboBox selectattr = new JComboBox();
//        selectattr.setModel(new DefaultComboBoxModel(new String[] {"Attr1", "Attr2", "Attr3"}));
//        selectattr.setBounds(12, 306, 735, 26);
//        container.add(selectattr);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(642, 350, 105, 27);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                startResult();
            }
        });
        container.add(btnStart);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(525, 350, 105, 27);
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        container.add(btnClose);
    }
}
