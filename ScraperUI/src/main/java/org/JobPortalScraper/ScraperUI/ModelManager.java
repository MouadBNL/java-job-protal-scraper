package org.JobPortalScraper.ScraperUI;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.clusterers.EM;
import weka.clusterers.MakeDensityBasedClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.*;

public class ModelManager {
    private static Instances data = null;
    private static ModelManager instance = null;
    public static ModelManager getInstance() {
        if(instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    private ModelManager() {
        this.loadData();
    }

    private void loadData() {
        DataSource src = null;
        try {
            src = new DataSource("data.arff");
            data = src.getDataSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getDataNbr(){
        return data.size();
    }

    public String[] getClassifiers() {
        return new String[] {"ZeroR", "J48", "Filtered classifier"};
    }

    public String[] getClusters() {
        return new String[] {"EM", "MakeDensityBasedClusterer", "Cobweb"};
    }

    public Evaluation j48Model() throws Exception {
        J48 j = new J48();
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = {"-U"};
        j.setOptions(options);
        j.buildClassifier(this.data);
        Evaluation eval = new Evaluation(this.data);
        eval.evaluateModel(j, this.data);
        return eval;
    }

    public Evaluation zeroRModel() throws Exception {
        ZeroR j = new ZeroR();
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = {"-U"};
        j.setOptions(options);
        j.buildClassifier(this.data);
        Evaluation eval = new Evaluation(this.data);
        eval.evaluateModel(j, this.data);
        return eval;
    }

    public Evaluation filteredModel() throws Exception {
        FilteredClassifier j = new FilteredClassifier();
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = {"-U"};
        j.setOptions(options);
        j.buildClassifier(this.data);
        Evaluation eval = new Evaluation(this.data);
        eval.evaluateModel(j, this.data);
        return eval;
    }


    /**
     * Clustering
     */
    public ClusterEvaluation makeDensityBasedClusterer() throws Exception {
        MakeDensityBasedClusterer j = new MakeDensityBasedClusterer();
//        data.setClassIndex(data.numAttributes() - 1);
        j.buildClusterer(this.data);
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(j);
        eval.evaluateClusterer(this.data);
        System.out.println(eval.clusterResultsToString());
        return eval;
    }
    public ClusterEvaluation emClusterer() throws Exception {
        EM j = new EM();
//        data.setClassIndex(data.numAttributes() - 1);
        j.buildClusterer(this.data);
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(j);
        eval.evaluateClusterer(this.data);
        System.out.println(eval.clusterResultsToString());
        return eval;
    }
    public ClusterEvaluation cobwebClusterer() throws Exception {
        Cobweb j = new Cobweb();
//        data.setClassIndex(data.numAttributes() - 1);
        j.buildClusterer(this.data);
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(j);
        eval.evaluateClusterer(this.data);
        System.out.println(eval.clusterResultsToString());
        return eval;
    }


}
