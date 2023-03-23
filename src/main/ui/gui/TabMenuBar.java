package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TabMenuBar extends JMenuBar {

    public TabMenuBar() {
        super();

        JMenu file = new JMenu("file");
        JMenuItem save = new JMenuItem("save");
        save.addActionListener(e -> saveAction());
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        file.add(save);
        add(file);
        JMenuItem menuButtonTwo = new JMenuItem(":3");
        menuButtonTwo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("meow");
            }
        });
        add(menuButtonTwo);
    }

    private void saveAction() {
        System.out.println("lol");
    }
}
