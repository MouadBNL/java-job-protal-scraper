import javax.swing.*;

public class SelectAlgorithmPage extends JFrame {
    public SelectAlgorithmPage() {
        this.setBounds(100, 100, 736, 368);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JList list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(new AbstractListModel() {
            String[] values = new String[] {"J48", "ZeroR", "Tree"};
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
        list.setBounds(12, 41, 712, 135);
        this.getContentPane().add(list);

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Attr1", "Attr2", "Attr3", "Attr4", "Attr5", "Attr6"}));
        comboBox.setBounds(12, 228, 712, 26);
        this.getContentPane().add(comboBox);

        JLabel lblSelectAlgorithm = new JLabel("Select Algorithm");
        lblSelectAlgorithm.setBounds(12, 12, 712, 17);
        this.getContentPane().add(lblSelectAlgorithm);

        JLabel lblSelectField = new JLabel("Select Field");
        lblSelectField.setBounds(12, 199, 712, 17);
        this.getContentPane().add(lblSelectField);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(619, 300, 105, 27);
        this.getContentPane().add(btnStart);
        this.setVisible(true);
    }
}
