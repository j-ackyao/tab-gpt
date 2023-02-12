package ui;

import model.Chord;
import model.Tab;

public class Main {
    public static void main(String[] args) {
        Tab tab = new Tab(6);
        Chord customChord = new Chord(new int[]{3, 3, 0, 0, 2, 3});
        tab.addChord(customChord);

        customChord.editNote(1, 2);

        tab.addChord(customChord);
        for (int i = 0; i < 10; i++) {
            tab.addChord(customChord);
        }

        System.out.println(tab.toString(true));
    }
}
