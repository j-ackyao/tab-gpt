package ui.gui;

import model.Chord;
import model.Tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A JPanel representing the tab editing screen, shows the tab and handles editing interactions
 */
public class TabPanel extends JPanel {

    private final GraphicalEditor parent;
    private final TabPopupMenu popup;
    private final FlowLayout fl;
    private Tab tab;

    /**
     * @EFFECTS: initially instantiates as empty with null tab
     */
    public TabPanel(GraphicalEditor parent) {
        super();
        this.parent = parent;
        this.tab = null;

        this.fl = new FlowLayout(FlowLayout.LEFT);
        this.fl.setHgap(0);
        setLayout(fl);

        // assigning to local param instead of adding to avoid loss when removeAll()
        this.popup = new TabPopupMenu(parent, this);
    }

    /**
     * @EFFECTS: updates the currently selected chordPanel of popup and shows popup on screen
     * @MODIFIES: this
     */
    public void promptChordOptions(ChordPanel chordPanel, MouseEvent e) {
        popup.setSelectedChordPanel(chordPanel);
        popup.show(this, e.getX() + chordPanel.getX(), e.getY() + chordPanel.getY());
    }

    /**
     * @REQUIRES: index >= 0
     * @EFFECTS: adds chord at index for both tab and tabPanel, accounting for 1 based index of tabPanel
     * @MODIFIES: this
     */
    public void addChord(Chord chord, int index) {
        // tab is 0 based index, chordpanels are 1 based index
        tab.insertChord(index, chord);
        add(new ChordPanel(this, chord), index + 1);
        repaint();
        revalidate();
    }

    /**
     * @EFFECTS: replaces current tab information with new tab and updates labels
     * @MODIFIES: this
     */
    public void setTab(Tab tab) {
        this.tab = tab;
        removeAll(); // pray java garbage collects properly

        JPanel tuningPanel = new JPanel();

        tuningPanel.setLayout(new GridLayout(tab.size, 1));
        for (String t : tab.getTuning()) {
            tuningPanel.add(new JLabel(t));
        }
        add(tuningPanel, 0);

        ArrayList<Chord> chords = tab.getChords();
        for (int i = 0; i < chords.size(); i++) {
            // + 1 to account for tuning panel
            add(new ChordPanel(this, chords.get(i)), i + 1);
        }
        repaint();
        revalidate();
    }

    public Tab getTab() {
        return tab;
    }

    /**
     * @EFFECTS: updates the height on window change to compensate for FlowLayout's implementation
     * @MODIFIES: this
     */
    public void updateHeight() {
        if (tab == null) {
            return;
        }

        // calculates the number of rows
        int rows = 1;
        int maxWidth = parent.getWidth();
        int widthCounter = 0;
        for (Component c : getComponents()) {
            widthCounter += c.getWidth();
            if (widthCounter > maxWidth) {
                widthCounter = c.getWidth();
                rows++;
            }
        }

        // under the assumption that all components are same size (should be)
        int componentHeight = fl.getVgap() + getComponent(0).getHeight();
        int height = (componentHeight * rows) + fl.getVgap() * 2;
        setPreferredSize(new Dimension(parent.getWidth() - 5, height));
    }
}
