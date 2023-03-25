package ui.gui;

import model.Chord;
import model.Tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TabPanel extends JPanel {

    private final TabPopupMenu tpm;
    private Tab tab;

    public TabPanel(GraphicalEditor parent) {
        super();
        this.tab = null;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        // assigning to local param instead of adding to avoid loss when removeAll()
        this.tpm = new TabPopupMenu(parent, this);

        //this.tabContainer = new JScrollPane(this,
        //        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //this.parent.add(tabContainer);
    }

    public void promptChordOptions(ChordPanel cp, MouseEvent e) {
        tpm.setSelectedChordPanel(cp);
        tpm.show(this, e.getX() + cp.getX(), e.getY() + cp.getY());
    }

    public void addChord(Chord c, int i) {
        // tab is 0 based index, chordpanels are 1 based index
        tab.insertChord(i, c);
        add(new ChordPanel(this, c), i + 1);
        repaint();
        revalidate();
    }

    public Tab getTab() {
        return tab;
    }

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
}
