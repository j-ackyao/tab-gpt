package ui.gui;

import model.Chord;
import model.Tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TabPanel extends JPanel {

    private final GraphicalEditor parent;
    private Tab tab;

    private ArrayList<ChordPanel> chordPanels;
    private Container tabContainer;
    private TabPopupMenu jpm;

    public TabPanel(GraphicalEditor parent) {
        super();
        this.parent = parent;
        this.chordPanels = new ArrayList<>();
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        jpm = new TabPopupMenu();
        this.add(jpm);

        //this.tabContainer = new JScrollPane(this,
        //        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //this.parent.add(tabContainer);
    }

    public void setTab(Tab tab) {
        this.tab = tab;
        chordPanels = new ArrayList<>(); // pray java garbage collects
        ArrayList<Chord> chords = tab.getChords();
        for (int i = 0; i < chords.size(); i++) {
            add(new ChordPanel(this, chords.get(i)),i);
        }
    }

    public void promptChordOptions(ChordPanel cp, MouseEvent e) {
        jpm.setSelectedChord(cp.getChord());

        jpm.show(this, e.getX() + cp.getX(), e.getY() + cp.getY());
    }
}
