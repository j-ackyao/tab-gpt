package ui.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * JPanel representing the main menu, prompted when on start up
 */
public class MenuPanel extends JPanel {

    /**
     * @EFFECTS: constructs with visual component and three buttons: new, load, quit
     */
    public MenuPanel(GraphicalEditor parent) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // createRigidArea creates the padding for the buttons
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createGuitarLabel());

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton top = new JButton("New tab");
        top.addActionListener(e -> parent.newTabAction());
        add(top, BorderLayout.CENTER);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton middle = new JButton("Load tab");
        middle.addActionListener(e -> parent.loadTabAction());
        add(middle, BorderLayout.LINE_START);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton bottom = new JButton("Quit");
        bottom.addActionListener(e -> System.exit(0));
        add(bottom, BorderLayout.LINE_END);

        Arrays.asList(getComponents())
                .forEach(c -> ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT));

    }

    /**
     * helper constructor for JLabel for guitar logo
     */
    private JLabel createGuitarLabel() {
        JLabel guitar = new JLabel();

        try {
            BufferedImage guitarPNG = ImageIO.read(new File("./data/guitar.png"));
            Icon icon = new ImageIcon(guitarPNG);
            guitar.setIcon(icon);
        } catch (IOException ioe) {
            guitar.setText("Couldn't load logo");
            ioe.printStackTrace();
        }

        return guitar;
    }


}
