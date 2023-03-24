package ui.gui;

import model.Tab;
import persistence.Json;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class MenuPanel extends JPanel {

    private final GraphicalEditor parent;

    public MenuPanel(GraphicalEditor parent) {
        super();

        this.parent = parent;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // createRigidArea creates the padding for the buttons
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton top = new JButton("New tab");
        top.addActionListener(e -> newTabAction());
        add(top, BorderLayout.CENTER);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton middle = new JButton("Load tab");
        middle.addActionListener(e -> loadTabAction());
        add(middle, BorderLayout.LINE_START);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton bottom = new JButton("Quit");
        bottom.addActionListener(e -> System.exit(0));
        add(bottom, BorderLayout.LINE_END);

        Arrays.asList(getComponents())
                .forEach(c -> ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT));

    }


    private void newTabAction() {
        String name = "";
        while (name.isBlank()) {
            name = JOptionPane.showInputDialog(parent.getContentPane(), "Name for new tab",
                    "New tab", JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                return;
            } else if (name.isBlank()) {
                promptEmptyNameError();
            }
        }

        String tuningInput = (String) JOptionPane.showInputDialog(parent.getContentPane(),
                "Tuning of tab in form X-X-X-...",
                "New tab", JOptionPane.PLAIN_MESSAGE, null,
                null, String.join("-", Tab.STANDARD_TUNING));
        String[] tuning = new String[0];
        if (tuningInput == null) {
            return;
        } else if (tuningInput.isBlank()) {
            tuning = Tab.STANDARD_TUNING;
        }

        parent.loadTab(new Tab(name, tuning));
        parent.showScreen(parent.TAB_SCREEN_NAME);
    }

    private void loadTabAction() {
        Tab loadTab = null;

        while (loadTab == null) {
            String tabName = JOptionPane.showInputDialog(parent.getContentPane(), "Name for existing tab",
                    "Load tab", JOptionPane.PLAIN_MESSAGE);
            if (tabName == null) {
                return;
            } else if (tabName.isBlank()) {
                promptEmptyNameError();
            } else {
                try {
                    loadTab = Json.load(tabName);
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(parent.getContentPane(),
                            "Tab of name '" + tabName
                                    + "' was not found", "Not found", JOptionPane.ERROR_MESSAGE);
                }
            }
        }


        parent.loadTab(loadTab);
        parent.showScreen(parent.TAB_SCREEN_NAME);
    }


    private void promptEmptyNameError() {
        JOptionPane.showMessageDialog(parent.getContentPane(),
                "No name was provided", "Empty input", JOptionPane.ERROR_MESSAGE);
    }
}
