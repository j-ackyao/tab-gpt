package ui.gui;

import javax.swing.*;

public class TabPanel extends JPanel {

    private final GraphicalEditor parent;

    JLabel tabLabel;

    public TabPanel(GraphicalEditor parent) {
        super();
        this.parent = parent;
        this.tabLabel = new JLabel("hi :3");
        add(tabLabel);
    }

    public void updateTabLabel() {
        tabLabel.setText("lol no cant update");
    }
}
