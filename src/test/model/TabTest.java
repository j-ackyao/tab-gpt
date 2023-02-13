package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TabTest {

    Tab tab;
    Chord chord;
    Chord altChord;

    @BeforeEach
    void testInit() {
        tab = new Tab(6);
        chord = new Chord(new int[]{3, 12, 0, 0, 2, 3});
        altChord = new Chord(new int[]{0, 1, 2, 2, 0, Note.EMPTY});

        tab.addChord(chord);
        tab.addChord(chord);
    }

    @Test
    void testConstructorSize() {
        Tab tab = new Tab(10);
        assertEquals(10, tab.size);
        assertArrayEquals(Tab.STANDARD_TUNING, tab.getTuning());
        assertEquals(new ArrayList<Chord>(), tab.getChords());
    }

    @Test
    void testConstructorTuning() {
        String[] altTuning = new String[]{"C", "D", "A", "G"};
        Tab tab = new Tab(altTuning);
        assertEquals(altTuning.length, tab.size);
        assertArrayEquals(altTuning, tab.getTuning());
        assertEquals(new ArrayList<Chord>(), tab.getChords());
    }

    @Test
    void testToString() {
        // testing with Tab.CHORD_SPACING = 3
        // and Tab.CHORD_SPACER = Note.EMPTY_STRING
        String expected =
                "E |---3----3----\n" +
                "B |---12---12---\n" +
                "G |---0----0----\n" +
                "D |---0----0----\n" +
                "A |---2----2----\n" +
                "E |---3----3----";
        assertEquals(expected, tab.toString());
    }

    @Test
    void testToStringWithPos() {
        // testing with Tab.CHORD_SPACING = 3
        // and Tab.CHORD_SPACER = Note.EMPTY_STRING

        // adding chords to test the spacing of pos numbers
        for (int i = 0; i < 10; i++) {
            tab.addChord(altChord);
        }
        String expected =
                "E |---3----3----0---0---0---0---0---0---0---0---0---0---\n" +
                "B |---12---12---1---1---1---1---1---1---1---1---1---1---\n" +
                "G |---0----0----2---2---2---2---2---2---2---2---2---2---\n" +
                "D |---0----0----2---2---2---2---2---2---2---2---2---2---\n" +
                "A |---2----2----0---0---0---0---0---0---0---0---0---0---\n" +
                "E |---3----3--------------------------------------------\n" +
                "      0    1    2   3   4   5   6   7   8   9   10  11  ";
        assertEquals(expected, tab.toString(true));
    }

    @Test
    void testAddChord() {
        Chord newChord = tab.addChord();
        newChord.editNotes(chord.getFrets());
        assertEquals(chord.getFrets().length, newChord.size);
        assertArrayEquals(chord.getFrets(), newChord.getFrets());
    }

    @Test
    void testAddChordChord() {
        tab.addChord(chord);
        Chord reference = tab.getChords().get(0);
        assertArrayEquals(chord.getFrets(), reference.getFrets());
        chord.editNote(0, 12);
        assertEquals(chord.size, reference.size);
        assertFalse(Arrays.equals(chord.getFrets(), reference.getFrets()));
    }

    @Test
    void testInsertChord() {
        Chord insertedChord = tab.insertChord(0);
        insertedChord.editNotes(altChord.getFrets());
        assertArrayEquals(altChord.getFrets(), tab.getChords().get(0).getFrets());
        assertArrayEquals(chord.getFrets(), tab.getChords().get(1).getFrets());
    }

    @Test
    void testInsertChordChord() {
        tab.insertChord(1, altChord);
        ArrayList<Chord> chords = tab.getChords();
        assertArrayEquals(chord.getFrets(), chords.get(0).getFrets());
        assertArrayEquals(altChord.getFrets(), chords.get(1).getFrets());
        assertArrayEquals(chord.getFrets(), chords.get(2).getFrets());
    }

    @Test
    void testEditChord() {
        int[] newFrets = altChord.getFrets();
        tab.editChord(0, newFrets);
        assertArrayEquals(newFrets, tab.getChords().get(0).getFrets());
    }

    @Test
    void testEditChordChord() {
        tab.editChord(0, altChord);
        assertArrayEquals(altChord.getFrets(), tab.getChords().get(0).getFrets());
    }

    @Test
    void testRemoveChord() {
        tab.insertChord(1, altChord);
        Chord removed = tab.removeChord(0);
        assertEquals(2, tab.getChords().size());
        assertArrayEquals(chord.getFrets(), removed.getFrets());
        assertArrayEquals(altChord.getFrets(), tab.getChords().get(0).getFrets());
    }

}