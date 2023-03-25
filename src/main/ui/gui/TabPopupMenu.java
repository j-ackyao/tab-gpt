package ui.gui;

import model.Chord;
import model.Note;
import ui.ConsoleEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class TabPopupMenu extends JPopupMenu {

    private final GraphicalEditor graphicalEditor;
    private final TabPanel tabPanel;
    private ChordPanel selectedChordPanel;
    private Chord clipboard;

    public TabPopupMenu(GraphicalEditor graphicalEditor, TabPanel tabPanel) {
        this.graphicalEditor = graphicalEditor;
        this.tabPanel = tabPanel;
        this.selectedChordPanel = null;
        this.clipboard = null;

        // menuitem is the end
        // jmenu expands
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
                    JOptionPane.showMessageDialog(graphicalEditor,
                            "Invalid input given", "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
                selectedChordPanel.updateLabels();
            }
        });
        add(edit);
    }

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

    private void bendChordUpdatePanel(Note.Bend bend) {
        selectedChordPanel.getChord().bendNotes(bend);
        selectedChordPanel.updateLabels();
    }

    private void initSlideMenu() {
        JMenu slide = new JMenu("Slide");
        slide.add(initToSlideMenu());
        slide.add(initFromSlideMenu());
        add(slide);
    }

    private JMenu initToSlideMenu() {
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

    private JMenu initFromSlideMenu() {
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

    private void slideToChordUpdatePanel(Note.Slide slide) {
        selectedChordPanel.getChord().slideToNotes(slide);
        selectedChordPanel.updateLabels();
    }

    private void slideFromChordUpdatePanel(Note.Slide slide) {
        selectedChordPanel.getChord().slideFromNotes(slide);
        selectedChordPanel.updateLabels();
    }

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

    private void initCopyMenuItem() {
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clipboard = selectedChordPanel.getChord();
            }
        });
        add(copy);
    }

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

    //todo add menu stuff and their actions here

    public void setSelectedChordPanel(ChordPanel cp) {
        selectedChordPanel = cp;
    }
}
