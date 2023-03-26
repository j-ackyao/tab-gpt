package ui.gui;

import persistence.JsonAndWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Tab menu bar that holds the data persistence interaction and new chord button
 */
public class TabMenuBar extends JMenuBar {

    private final GraphicalEditor graphicalEditor;
    private final TabPanel tabPanel;

    /**
     * @EFFECTS: constructor for menu bar with two items: file and add
     */
    public TabMenuBar(GraphicalEditor graphicalEditor, TabPanel tabPanel) {
        super();
        this.graphicalEditor = graphicalEditor;
        this.tabPanel = tabPanel;
        setAlignmentX(LEFT_ALIGNMENT);

        initFileMenu();
        initAddMenuItem();
    }

    /**
     * @EFFECTS: helper constructor for file item that handles the data persistence, adds to this
     * @MODIFIES: this
     */
    private void initFileMenu() {
        JMenu file = new JMenu("File");

        file.add(createNewTabMenuItem());
        file.add(createOpenMenuItem());
        file.add(new JSeparator());
        file.add(createSaveMenuItem());
        file.add(createExportMenuItem());
        file.add(new JSeparator());
        file.add(createCloseMenuItem());

        add(file);
    }

    /**
     * @EFFECTS: helper constructor for adding chord, adds to this
     * @MODIFIES: this
     */
    private void initAddMenuItem() {
        JMenuItem addChord = new JMenuItem("Add new chord");
        addChord.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabPanel.add(new ChordPanel(tabPanel, tabPanel.getTab().addChord()), tabPanel.getComponentCount());
                tabPanel.repaint();
                tabPanel.revalidate();
            }
        });
        add(addChord);
    }

    /**
     * @EFFECTS: helper constructor for new in file
     */
    private JMenuItem createNewTabMenuItem() {
        JMenuItem newTab = new JMenuItem("New");
        newTab.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphicalEditor.newTabAction();
            }
        });
        newTab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        return newTab;
    }

    /**
     * @EFFECTS: helper constructor for open in file
     */
    private JMenuItem createOpenMenuItem() {
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphicalEditor.loadTabAction();
            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        return open;
    }

    /**
     * @EFFECTS: helper constructor for save in file
     */
    private JMenuItem createSaveMenuItem() {
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JsonAndWriter.save(tabPanel.getTab());
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(tabPanel,
                            "Unable to save tab to '" + tabPanel.getTab().getName() + "'",
                            "Save failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        return save;
    }

    /**
     * @EFFECTS: helper constructor for export in file
     */
    private JMenuItem createExportMenuItem() {
        JMenuItem saveAs = new JMenuItem("Export to text");
        saveAs.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JsonAndWriter.export(tabPanel.getTab());
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(tabPanel,
                            "Unable to export tab to '" + tabPanel.getTab().getName() + "'",
                            "Export failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        return saveAs;
    }

    /**
     * @EFFECTS: helper constructor for close in file
     */
    private JMenuItem createCloseMenuItem() {
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo prompt confirmation to save
                graphicalEditor.showScreen(GraphicalEditor.MENU_SCREEN_NAME);
            }
        });
        return close;
    }

}
