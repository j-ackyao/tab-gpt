package ui.gui;

import model.Tab;

import javax.swing.*;
import java.awt.*;

public class GraphicalEditor extends JFrame {

    public static final String MENU_SCREEN_NAME = "menu";
    public static final String TAB_SCREEN_NAME = "tab";
    private final Container contentPane;
    private final CardLayout layout;
    private MenuPanel menuPanel;
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

    public static void main(String[] args) {
        GraphicalEditor ge = new GraphicalEditor();
    }

    private void initMenuPanel() {
        menuPanel = new MenuPanel(this);
        contentPane.add(MENU_SCREEN_NAME, menuPanel);
    }

    private void initTabPanel() {
        tabPanel = new TabPanel(this);
        contentPane.add(TAB_SCREEN_NAME, tabPanel);
    }

    private void initMenuBar() {
        tabMenuBar = new TabMenuBar();
        setJMenuBar(tabMenuBar);
    }

    //todo
    /*
    private void updateTabPanel() {
        JTextArea tuning = new JTextArea();
        for (String t : testTab.getTuning()) {
            tuning.append(t + "\n");
        }
        tabPanel.add(tuning);

        for (Chord c : testTab.getChords()) {
            JTextArea sltoText = new JTextArea();
            JTextArea fretText = new JTextArea();
            JTextArea bendText = new JTextArea();
            JTextArea slfrText = new JTextArea();
            for (Note n : c.getNotes()) {
                sltoText.append(n.getSlideTo() + "\n");
                fretText.append((n.getFret() == -1 ? "" : n.getFret() == -2 ? "x" : n.getFret()) + "\n");
                bendText.append(n.getBend() + "\n");
                slfrText.append(n.getSlideFrom() + "\n");
            }
            tabPanel.add(sltoText);
            tabPanel.add(fretText);
            tabPanel.add(bendText);
            tabPanel.add(slfrText);
        }

        Arrays.asList(tabPanel.getComponents()).forEach(c -> ((JComponent) c).setOpaque(false));

    }*/

    public void showScreen(String name) {
        switch (name) {
            case MENU_SCREEN_NAME:
                layout.show(contentPane, name);
                tabMenuBar.setVisible(false);
                break;
            case TAB_SCREEN_NAME:
                layout.show(contentPane, name);
                tabMenuBar.setVisible(true);
                break;

        }
    }

    public void loadTab(Tab tab) {
        tabPanel.setTab(tab);
        showScreen(TAB_SCREEN_NAME);
    }
}
