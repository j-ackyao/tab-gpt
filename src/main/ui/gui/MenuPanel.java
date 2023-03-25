package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MenuPanel extends JPanel {

    public MenuPanel(GraphicalEditor parent) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // createRigidArea creates the padding for the buttons
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton top = new JButton("New tab");
        top.addActionListener(e -> parent.newTabAction());
        add(top, BorderLayout.CENTER);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton middle = new JButton("Load tab");
        middle.addActionListener(e -> parent.loadTabAction());
        add(middle, BorderLayout.LINE_START);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton bottom = new JButton("Quit");
        bottom.addActionListener(e -> System.exit(0));
        add(bottom, BorderLayout.LINE_END);

        Arrays.asList(getComponents())
                .forEach(c -> ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT));

    }


}
