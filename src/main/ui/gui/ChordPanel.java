package ui.gui;

import model.Chord;
import model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * JPanel representing all the information of a chord
 */
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

    /**
     * @EFFECTS: constructs ChordPanel with given chord and listener for right-click
     */
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
    }

    /**
     * @EFFECTS: action for right-click listener
     * @MODIFIES: this
     */
    private void promptChordOptions(MouseEvent e) {
        parent.promptChordOptions(this, e);
    }

    /**
     * @EFFECTS: helper constructor for each labels of each note's attributes
     * @MODIFIES: this
     */
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

    /**
     * @EFFECTS: helper constructor for each panel of note attributes
     * @MODIFIES: this
     */
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

        sltoPanel.setOpaque(false);
        fretPanel.setOpaque(false);
        bendPanel.setOpaque(false);
        slfrPanel.setOpaque(false);

        add(sltoPanel);
        add(fretPanel);
        add(bendPanel);
        add(slfrPanel);
    }

    public Chord getChord() {
        return this.chord;
    }

    /**
     * @EFFECTS: updates respective labels to current chord
     * @MODIFIES: this
     */
    public void updateLabels() {
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
            fret[i].setText(note.getFret() == Note.MUTE ? "x" : Integer.toString(note.getFret()));
            bend[i].setText(note.getBend().toString());
            slfr[i].setText(note.getSlideFrom().toString());
        }
    }

    /**
     * @EFFECTS: overrides paintComponent, calls super method and draws lines given parent settings
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        for (JLabel fretPanel : fret) {
            float lineThickness = parent.getLineThickness();
            float separatorThickness = parent.getSeparatorThickness();

            if (lineThickness != 0) {
                g2d.setStroke(new BasicStroke(lineThickness));
                int posY = fretPanel.getY() + (int) Math.round((double) fretPanel.getHeight() / 2);
                g2d.drawLine(0, posY, getWidth(), posY);
            }

            if (separatorThickness != 0) {
                g2d.setStroke(new BasicStroke(separatorThickness));
                g2d.drawLine(0, 0, 0, getHeight());
            }
        }
    }
}
