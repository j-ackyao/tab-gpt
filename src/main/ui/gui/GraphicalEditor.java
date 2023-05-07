package ui.gui;

import model.Tab;
import persistence.JsonAndWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * GUI editor for tab, including menu to save and load
 */
public class GraphicalEditor extends JFrame {

    public static final Dimension START_UP_DIMENSION = new Dimension(400, 300);

    public static final String MENU_SCREEN_NAME = "menu";
    public static final String TAB_SCREEN_NAME = "tab";
    private final Container contentPane;
    private final CardLayout layout;
    private TabPanel tabPanel;
    private TabMenuBar tabMenuBar;

    /**
     * @EFFECTS: constructs GraphicalEditor with START_UP_DIMENSION and CardLayout, initializes menu and tab
     */
    public GraphicalEditor() {
        super("Tab GPT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(START_UP_DIMENSION);
        setVisible(true);
        setResizable(true);

        this.contentPane = getContentPane();
        this.layout = new CardLayout();
        this.contentPane.setLayout(layout);


        initMenuPanel();
        initTabPanel();
        initMenuBar();

        addComponentListeners();

        pack();
        showScreen(MENU_SCREEN_NAME);
    }

    /**
     * @EFFECTS: helper for the constructor to initialize and add menuPanel
     * @MODIFIES: this
     */
    private void initMenuPanel() {
        this.contentPane.add(MENU_SCREEN_NAME, new MenuPanel(this));
    }

    /**
     * @EFFECTS: helper for the constructor to initialize and add tabPanel
     * @MODIFIES: this
     */
    private void initTabPanel() {
        this.tabPanel = new TabPanel(this);
        this.tabPanel.setPreferredSize(getSize());
        JScrollPane scrollPane = new JScrollPane(tabPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.contentPane.add(TAB_SCREEN_NAME, scrollPane);
    }

    /**
     * @EFFECTS: helper for the constructor to initialize and add menuBar
     * @MODIFIES: this
     */
    private void initMenuBar() {
        this.tabMenuBar = new TabMenuBar(this, tabPanel);
        setJMenuBar(tabMenuBar);
    }

    /**
     * @EFFECTS: helper for the constructor to add component listeners
     * @MODIFIES: this
     */
    private void addComponentListeners() {
        // resizing due to broken flow layout
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                tabPanel.updateHeight();
            }
        });
        // prints all events at window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }

    /**
     * @EFFECTS: switches between screens (menu, tab) using CardLayout
     * @MODIFIES: this
     */
    public void showScreen(String name) {
        layout.show(contentPane, name);
        tabMenuBar.setVisible(name.equals(TAB_SCREEN_NAME));
        repaint();
        revalidate();
    }

    /**
     * @EFFECTS: action to prompt window for user when new tab was chosen
     * @MODIFIES: this
     */
    public void newTabAction() {
        String name = "";
        while (stringIsBlank(name)) {
            name = JOptionPane.showInputDialog(getContentPane(), "Name for new tab",
                    "New tab", JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                return;
            } else if (stringIsBlank(name)) {
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
        if (stringIsBlank(tuningInput) || tuning.length == 0) {
            tuning = Tab.STANDARD_TUNING;
        }
        loadTab(new Tab(name, tuning));
        showScreen(TAB_SCREEN_NAME);
    }

    /**
     * @EFFECTS: action to prompt window for user when load tab was chosen
     * @MODIFIES: this
     */
    public void loadTabAction() {
        Tab loadTab = null;

        while (loadTab == null) {
            String tabName = JOptionPane.showInputDialog(getContentPane(), "Name for existing tab",
                    "Load tab", JOptionPane.PLAIN_MESSAGE);
            if (tabName == null) {
                return;
            } else if (stringIsBlank(tabName)) {
                promptEmptyNameError();
                continue;
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


    /**
     * @EFFECTS: prompts window stating the user provided an empty input
     */
    public void promptEmptyNameError() {
        JOptionPane.showMessageDialog(getContentPane(),
                "No name was provided", "Empty input", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @EFFECTS: prompts window stating the user provided an invalid input
     */
    public void promptInvalidInputError() {
        JOptionPane.showMessageDialog(getContentPane(),
                "Invalid input given", "Invalid input", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @EFFECTS: updates tabPanel and switches to tab screen
     */
    public void loadTab(Tab tab) {
        tabPanel.setTab(tab);
        showScreen(TAB_SCREEN_NAME);
    }

    /**
     * @EFFECTS: determines if the string isBlank() to compensate for Java 8 bot
     */
    public boolean stringIsBlank(String string) {
        return string == null || string.trim().length() == 0;
    }
}
