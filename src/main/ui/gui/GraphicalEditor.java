package ui.gui;

import model.Tab;
import persistence.JsonAndWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GraphicalEditor extends JFrame {

    public static final String MENU_SCREEN_NAME = "menu";
    public static final String TAB_SCREEN_NAME = "tab";
    private final Container contentPane;
    private final CardLayout layout;
    private TabPanel tabPanel;
    private TabMenuBar tabMenuBar;


    public GraphicalEditor() {
        super("Tab GPT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setVisible(true);
        setResizable(true);

        this.contentPane = getContentPane();
        this.layout = new CardLayout();
        this.contentPane.setLayout(layout);


        initMenuPanel();
        initTabPanel();
        initMenuBar();

        pack();
        showScreen(MENU_SCREEN_NAME);
    }

    private void initMenuPanel() {
        contentPane.add(MENU_SCREEN_NAME, new MenuPanel(this));
    }

    private void initTabPanel() {
        tabPanel = new TabPanel(this);
        contentPane.add(TAB_SCREEN_NAME, tabPanel);
    }

    private void initMenuBar() {
        tabMenuBar = new TabMenuBar(this, tabPanel);
        setJMenuBar(tabMenuBar);
    }

    public void showScreen(String name) {
        layout.show(contentPane, name);
        tabMenuBar.setVisible(name.equals(TAB_SCREEN_NAME));
        repaint();
        revalidate();
    }

    public void newTabAction() {
        String name = "";
        while (name.isBlank()) {
            name = JOptionPane.showInputDialog(getContentPane(), "Name for new tab",
                    "New tab", JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                return;
            } else if (name.isBlank()) {
                promptEmptyNameError();
            }
        }

        String tuningInput = (String) JOptionPane.showInputDialog(getContentPane(),
                "Tuning of tab in form X-X-X-...",
                "New tab", JOptionPane.PLAIN_MESSAGE, null,
                null, String.join("-", Tab.STANDARD_TUNING));
        if (tuningInput == null) {
            return;
        }
        String[] tuning = tuningInput.split("-");
        if (tuningInput.isBlank() || tuning.length == 0) {
            tuning = Tab.STANDARD_TUNING;
        }
        loadTab(new Tab(name, tuning));
        showScreen(TAB_SCREEN_NAME);
    }

    public void loadTabAction() {
        Tab loadTab = null;

        while (loadTab == null) {
            String tabName = JOptionPane.showInputDialog(getContentPane(), "Name for existing tab",
                    "Load tab", JOptionPane.PLAIN_MESSAGE);
            if (tabName == null) {
                return;
            } else if (tabName.isBlank()) {
                promptEmptyNameError();
                return;
            }
            try {
                loadTab = JsonAndWriter.load(tabName);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(getContentPane(), "Tab of name '" + tabName + "' was not found",
                        "Not found", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(getContentPane(), "Tab of name '" + tabName
                                + "' could not be loaded, may be corrupt.",
                        "Load fail", JOptionPane.ERROR_MESSAGE);
            }

        }


        loadTab(loadTab);
        showScreen(TAB_SCREEN_NAME);
    }

    private void promptEmptyNameError() {
        JOptionPane.showMessageDialog(getContentPane(),
                "No name was provided", "Empty input", JOptionPane.ERROR_MESSAGE);
    }

    public void loadTab(Tab tab) {
        tabPanel.setTab(tab);
        showScreen(TAB_SCREEN_NAME);
    }
}
