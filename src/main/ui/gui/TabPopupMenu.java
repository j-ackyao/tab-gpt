package ui.gui;

import model.Chord;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TabPopupMenu extends JPopupMenu {

    private Chord selected;

    public TabPopupMenu() {
        selected = null;
        // menuitem is the end
        // jmenu expands
        initEditMenuItem();
        initSlideMenuItem();
    }

    private void initEditMenuItem() {
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // joptionpane
            }
        });
        add(edit);
    }

    private void initSlideMenuItem() {
        JMenuItem slide = new JMenuItem("Slide");
        slide.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // joptionpane
            }
        });
        add(slide);
    }

    //todo add menu stuff and their actions here

    public void setSelectedChord(Chord c) {
        selected = c;
    }
}
