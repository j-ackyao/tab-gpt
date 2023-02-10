package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabTest {

    Tab tab;

    @BeforeEach
    void testInit() {
        tab = new Tab(6);
    }

    @Test
    void testConstructor() {

    }

    // TODO turn to working test
    @Test
    void testToString() {
        Tab tab = new Tab(6);
        // reference problem?
        Chord g9Chord = new Chord(tab.size);
        g9Chord.editNotes(new int[]{-1, 3, 3, 2, 3});
        tab.addChord(g9Chord);
        tab.addChord(g9Chord);
        g9Chord.editNote(3, 1); // becomes m9
        System.out.println(tab.toString());
    }


}