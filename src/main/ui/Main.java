package ui;

import model.Note;
import model.Chord;
import model.Tab;

public class Main {
    public static void main(String[] args) {
        Tab tab = new Tab(6);
        Chord gchord = new Chord(6);
        tab.addChord(gchord);

        gchord.editNote(0, 12);

        for (Chord s : tab.getChords()) {
            for (Note n : s.getNotes()) {
                System.out.println(n.getFret());
            }
        }
    }
}
