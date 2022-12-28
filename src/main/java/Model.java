import weka.classifiers.Evaluation;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.*;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.instance.NonSparseToSparse;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

public class Model {
    protected Instances data;

    public Model(Instances data) {
        this.data = data;
    }

    public Model() {

    }

    public void loadDefaultData() throws Exception {
        DataSource src = new DataSource("data.arff");
        this.data = src.getDataSet();
    }

    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }

    public J48 startJ48() throws Exception {
        J48 j = new J48();
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = {"-U"};
        j.setOptions(options);
        j.buildClassifier(this.data);
        System.out.println(j.toSummaryString());
        Evaluation eval = new Evaluation(this.data);
        eval.evaluateModel(j, this.data);
        System.out.println(eval.toSummaryString());
        System.out.println("-------------------------------------------------------------------");
        System.out.println(eval.toClassDetailsString());
        System.out.println("-------------------------------------------------------------------");
        System.out.println(eval.toMatrixString());
        return j;
    }

    public ZeroR startZeroR() throws Exception {
        ZeroR z = new ZeroR();
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = {"-U"};
        z.setOptions(options);
        z.buildClassifier(this.data);
//        System.out.println(z.toString());
        Evaluation eval = new Evaluation(this.data);
        eval.evaluateModel(z, this.data);
        System.out.println(eval.toSummaryString());
        System.out.println("-------------------------------------------------------------------");
        System.out.println(eval.toClassDetailsString());
        System.out.println("-------------------------------------------------------------------");
        System.out.println(eval.toMatrixString());
        return z;
    }

    public DecisionTable startDecisionTable() throws Exception {
        DecisionTable d = new DecisionTable();
        data.setClassIndex(data.numAttributes() - 1);
//        String[] options = {"-U"};
        d.buildClassifier(this.data);
        System.out.println(d.toString());
        System.out.println(d.printFeatures());
        return d;
    }
}
