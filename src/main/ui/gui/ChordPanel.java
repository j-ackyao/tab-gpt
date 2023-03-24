package ui.gui;

import model.Chord;
import model.Note;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ChordPanel extends JPanel {

    private static final FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 0, 0);

    private final TabPanel parent;
    private final Chord chord;

    private JLabel[] slto;
    private JLabel[] fret;
    private JLabel[] bend;
    private JLabel[] slfr;

    private JPanel sltoPanel;
    private JPanel fretPanel;
    private JPanel bendPanel;
    private JPanel slfrPanel;

    public ChordPanel(TabPanel parent, Chord chord) {
        this.parent = parent;
        this.chord = chord;

        setLayout(fl);
        initPanels();
        initLabels();
        updateLabels();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    promptChordOptions(e);
                }
            }
        });

        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void initLabels() {
        slto = new JLabel[chord.size];
        fret = new JLabel[chord.size];
        bend = new JLabel[chord.size];
        slfr = new JLabel[chord.size];

        for (int i = 0; i < chord.size; i++) {
            slto[i] = new JLabel();
            fret[i] = new JLabel();
            bend[i] = new JLabel();
            slfr[i] = new JLabel();
            sltoPanel.add(slto[i]);
            fretPanel.add(fret[i]);
            bendPanel.add(bend[i]);
            slfrPanel.add(slfr[i]);
        }
    }

    private void initPanels() {
        sltoPanel = new JPanel();
        fretPanel = new JPanel();
        bendPanel = new JPanel();
        slfrPanel = new JPanel();

        // slide, note, bend, slide
        GridLayout gl = new GridLayout(chord.size, 4);
        gl.minimumLayoutSize(new JLabel(" "));
        sltoPanel.setLayout(gl);
        fretPanel.setLayout(gl);
        bendPanel.setLayout(gl);
        slfrPanel.setLayout(gl);

        add(sltoPanel);
        add(fretPanel);
        add(bendPanel);
        add(slfrPanel);
    }

    private void promptChordOptions(MouseEvent e) {
        parent.promptChordOptions(this, e);
    }

    public Chord getChord() {
        return this.chord;
    }

    private void updateLabels() {
        for (int i = 0; i < chord.size; i++) {
            Note note = chord.getNote(i);
            if (note.getFret() == Note.EMPTY) {
                slto[i].setText(" ");
                fret[i].setText(" ");
                bend[i].setText(" ");
                slfr[i].setText(" ");
                continue;
            }
            slto[i].setText(note.getSlideTo().toString());
            fret[i].setText(Integer.toString(note.getFret()));
            bend[i].setText(note.getBend().toString());
            slfr[i].setText(note.getSlideFrom().toString());
        }
    }
}
