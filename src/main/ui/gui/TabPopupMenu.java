package ui.gui;

import model.Chord;
import model.Note;
import ui.ConsoleEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Popup menu for editing interaction when a chord is right-clicked by user
 */
public class TabPopupMenu extends JPopupMenu {

    private final GraphicalEditor graphicalEditor;
    private final TabPanel tabPanel;
    private ChordPanel selectedChordPanel;
    private Chord clipboard;

    /**
     * @MODIFIES: constructs popup menu with null selectedChordPanel and clipboard
     */
    public TabPopupMenu(GraphicalEditor graphicalEditor, TabPanel tabPanel) {
        this.graphicalEditor = graphicalEditor;
        this.tabPanel = tabPanel;
        this.selectedChordPanel = null;
        this.clipboard = null;

        initEditMenuItem();
        add(new JSeparator());
        initBendMenu();
        initSlideMenu();
        add(new JSeparator());
        initInsertMenuItem();
        initCopyMenuItem();
        initPasteMenuItem();
        add(new JSeparator());
        initDeleteMenuItem();
    }

    /**
     * @EFFECTS: helper constructor for edit function and adds to this
     * @MODIFIES: this
     */
    private void initEditMenuItem() {
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chord selectedChord = selectedChordPanel.getChord();
                String current = fretsToString(selectedChord.getFrets());

                String input = (String) JOptionPane.showInputDialog(graphicalEditor,
                        "Edit frets in form X-X-X-...",
                        "Edit", JOptionPane.PLAIN_MESSAGE, null,
                        null, current);

                if (input == null || input.equals(current)) {
                    return;
                }
                try {
                    selectedChord.editNotes(ConsoleEditor.formatToFrets(input));
                } catch (NumberFormatException nfe) {
                    graphicalEditor.promptInvalidInputError();
                }
                selectedChordPanel.updateLabels();
            }
        });
        add(edit);
    }

    /**
     * @REQUIRES: frets.length > 0
     * @EFFECTS: helper for edit function, converts chord frets to string format
     */
    private String fretsToString(int[] frets) {
        StringBuilder builder = new StringBuilder();
        for (int fret : frets) {
            switch (fret) {
                case Note.EMPTY:
                    builder.append("e" + "-");
                    break;
                case Note.MUTE:
                    builder.append("x" + "-");
                    break;
                default:
                    builder.append(fret).append("-");
            }
        }
        // cut the last "-" off
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * @EFFECTS: helper constructor for bend function and adds to this
     * @MODIFIES: this
     */
    private void initBendMenu() {
        JMenu bend = new JMenu("Bend");
        JMenuItem none = new JMenuItem("None");
        JMenuItem half = new JMenuItem("Half");
        JMenuItem full = new JMenuItem("Full");
        bend.add(none);
        bend.add(half);
        bend.add(full);
        none.addActionListener(e -> bendChordUpdatePanel(Note.Bend.NONE));
        half.addActionListener(e -> bendChordUpdatePanel(Note.Bend.HALF));
        full.addActionListener(e -> bendChordUpdatePanel(Note.Bend.FULL));
        add(bend);
    }

    /**
     * @EFFECTS: helper for bend function, updates selected chord based on given bend
     * @MODIFIES: this
     */
    private void bendChordUpdatePanel(Note.Bend bend) {
        selectedChordPanel.getChord().bendNotes(bend);
        selectedChordPanel.updateLabels();
    }

    /**
     * @EFFECTS: helper constructor for slide function and adds to this
     * @MODIFIES: this
     */
    private void initSlideMenu() {
        JMenu slide = new JMenu("Slide");
        slide.add(createToSlideMenu());
        slide.add(createFromSlideMenu());
        add(slide);
    }

    /**
     * @EFFECTS: helper for slide constructor, implements slideTo
     */
    private JMenu createToSlideMenu() {
        JMenu to = new JMenu("To");
        JMenuItem toUp = new JMenuItem("Up");
        JMenuItem toDown = new JMenuItem("Down");
        JMenuItem toNone = new JMenuItem("None");
        toUp.addActionListener(e -> slideToChordUpdatePanel(Note.Slide.UP));
        toDown.addActionListener(e -> slideToChordUpdatePanel(Note.Slide.DOWN));
        toNone.addActionListener(e -> slideToChordUpdatePanel(Note.Slide.NONE));
        to.add(toUp);
        to.add(toDown);
        to.add(toNone);
        return to;
    }

    /**
     * @EFFECTS: helper for slide constructor, implements slideFrom
     */
    private JMenu createFromSlideMenu() {
        JMenu from = new JMenu("From");
        JMenuItem fromUp = new JMenuItem("Up");
        JMenuItem fromDown = new JMenuItem("Down");
        JMenuItem fromNone = new JMenuItem("None");
        fromUp.addActionListener(e -> slideFromChordUpdatePanel(Note.Slide.UP));
        fromDown.addActionListener(e -> slideFromChordUpdatePanel(Note.Slide.DOWN));
        fromNone.addActionListener(e -> slideFromChordUpdatePanel(Note.Slide.NONE));
        from.add(fromUp);
        from.add(fromDown);
        from.add(fromNone);
        return from;
    }

    /**
     * @EFFECTS: helper function for slideTo, updates selected chord with given slide
     * @MODIFIES: this
     */
    private void slideToChordUpdatePanel(Note.Slide slide) {
        selectedChordPanel.getChord().slideToNotes(slide);
        selectedChordPanel.updateLabels();
    }

    /**
     * @EFFECTS: helper function for slideFrom, updates selected chord with given slide
     * @MODIFIES: this
     */
    private void slideFromChordUpdatePanel(Note.Slide slide) {
        selectedChordPanel.getChord().slideFromNotes(slide);
        selectedChordPanel.updateLabels();
    }

    /**
     * @EFFECTS: helper constructor for insert function and adds to this
     * @MODIFIES: this
     */
    private void initInsertMenuItem() {
        JMenuItem insert = new JMenuItem("Insert");
        insert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = Arrays.asList(tabPanel.getComponents()).indexOf(selectedChordPanel) - 1;
                tabPanel.addChord(new Chord(selectedChordPanel.getChord().size), index);
            }
        });
        add(insert);
    }

    /**
     * @EFFECTS: helper constructor for copy function and adds to this
     * @MODIFIES: this
     */
    private void initCopyMenuItem() {
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clipboard = selectedChordPanel.getChord().cloneChord();
            }
        });
        add(copy);
    }

    /**
     * @EFFECTS: helper constructor for paste function and adds to this
     * @MODIFIES: this
     */
    private void initPasteMenuItem() {
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clipboard == null) {
                    return;
                }
                int index = Arrays.asList(tabPanel.getComponents()).indexOf(selectedChordPanel);
                tabPanel.addChord(clipboard.cloneChord(), index);
            }
        });
        add(paste);
    }

    /**
     * @EFFECTS: helper constructor for delete function and adds to this
     * @MODIFIES: this
     */
    private void initDeleteMenuItem() {
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabPanel.getTab().removeChord(
                        Arrays.asList(tabPanel.getComponents()).indexOf(selectedChordPanel) - 1);
                tabPanel.remove(selectedChordPanel);
                tabPanel.repaint();
                tabPanel.revalidate();
            }
        });
        add(delete);
    }

    /**
     * @EFFECTS: updates current selected chordPanel
     * @MODIFIES: this
     */
    public void setSelectedChordPanel(ChordPanel chordPanel) {
        selectedChordPanel = chordPanel;
    }
}
