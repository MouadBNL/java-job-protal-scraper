import javax.swing.*;
import java.awt.*;

public class BorderedPanelComponent extends JPanel {
    public BorderedPanelComponent() {
        super();

        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#DEDEDE"), 1));
    }
}
