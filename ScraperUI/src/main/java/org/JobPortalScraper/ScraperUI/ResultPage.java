package org.JobPortalScraper.ScraperUI;

import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultPage extends JFrame {
    
    private String title;
    private Evaluation eval;
    private ClusterEvaluation clusteringEval;
    public ResultPage(Evaluation eval) {
        this.title = "Job Insight - Result";
        this.eval = eval;
        this.clusteringEval = null;
        this.initialize();
        this.setVisible(true);
    }
    public ResultPage(ClusterEvaluation eval) {
        this.title = "Job Insight - Result";
        this.clusteringEval = eval;
        this.eval = null;
        this.initialize();
        this.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setResizable(false);
        this.setBounds(100, 100, 783, 666);
        this.getContentPane().setLayout(null);

        JLabel lblSummary = new JLabel("Summary");
        lblSummary.setBounds(12, 12, 712, 17);
        this.getContentPane().add(lblSummary);

        JLabel lblDetails = new JLabel("Details");
        lblDetails.setBounds(12, 201, 712, 17);
        this.getContentPane().add(lblDetails);

        JLabel lblMatrix = new JLabel("Matrix");
        lblMatrix.setBounds(12, 390, 712, 17);
        this.getContentPane().add(lblMatrix);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(900, 598, 105, 27);
        this.getContentPane().add(btnClose);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 41, 759, 148);
        if(eval == null) {
            scrollPane.setBounds(12, 41, 759, 548);
        }
        this.getContentPane().add(scrollPane);

        JTextPane txtSummary = new JTextPane();
        if(eval == null) {
            txtSummary.setText(clusteringEval.clusterResultsToString());
        } else {
            txtSummary.setText(eval.toSummaryString());
        }
        scrollPane.setViewportView(txtSummary);

        if(eval != null) {

            JScrollPane scrollPane_1 = new JScrollPane();
            scrollPane_1.setBounds(12, 230, 759, 148);
            this.getContentPane().add(scrollPane_1);

            JTextPane txtDetails = new JTextPane();
            try {
                txtDetails.setText(eval.toClassDetailsString());
            } catch (Exception e) {
                txtDetails.setText("Error loading...");
            }
            scrollPane_1.setViewportView(txtDetails);

            JScrollPane scrollPane_2 = new JScrollPane();
            scrollPane_2.setBounds(12, 419, 759, 148);
            this.getContentPane().add(scrollPane_2);

            JTextPane txtpnMatrix = new JTextPane();
            try {
                txtpnMatrix.setText(eval.toMatrixString());
            } catch (Exception e) {
                txtpnMatrix.setText("Error loading...");
            }
            scrollPane_2.setViewportView(txtpnMatrix);
        }

        JButton btnClose_1 = new JButton("Close");
        btnClose_1.setBounds(666, 598, 105, 27);
        btnClose_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        this.getContentPane().add(btnClose_1);
    }
}
