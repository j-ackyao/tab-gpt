package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChordTest {

    Chord chord;

    @BeforeEach
    void testInit() {
        chord = new Chord(6);
    }

    @Test
    void testConstructorSize() {
        Chord chord = new Chord(3);
        assertEquals(3, chord.getNotes().length);
        for (int i = 0; i < chord.size; i++) {
            assertEquals(Note.EMPTY, chord.getNotes()[i].getFret());
        }
    }

    @Test
    void testConstructorFrets() {
        int[] frets = new int[]{1,2,4,5,3,7};
        Chord chord = new Chord(frets);
        assertEquals(6, chord.getNotes().length);
        for (int i = 0; i < frets.length; i++) {
            assertEquals(frets[i], chord.getFret(i));
        }
    }

    @Test
    void testEditNote() {
        chord.editNote(0, 0);
        Note[] notes = chord.getNotes();
        assertEquals(0, notes[0].getFret());
        assertEquals(Note.EMPTY, notes[1].getFret());
    }

    @Test
    void testEditNotesUnder() {
        chord.editNotes(new int[]{1, 0});
        Note[] notes = chord.getNotes();
        assertEquals(1, notes[0].getFret());
        assertEquals(0, notes[1].getFret());
        assertEquals(Note.EMPTY, notes[3].getFret());
    }


    @Test
    void testEditNotesOver() {
        chord.editNotes(new int[]{3, 2, 1, 4, 0, 5});
        Note[] notes = chord.getNotes();
        assertEquals(6, chord.getNotes().length);
        for (int i = 0; i < chord.size; i++) {
            assertEquals(notes[i].getFret(), chord.getFret(i));
        }
    }

    @Test
    void testCloneChord() {
        chord.editNote(0, 12);
        chord.editNote(1, 13);
        chord.editNote(2, 14);
        chord.editNote(3, 15);
        Chord clone = chord.cloneChord();
        clone.editNote(2, 4);
        clone.editNote(3, 5);
        assertEquals(chord.getNote(0).getFret(), clone.getNote(0).getFret());
        assertEquals(chord.getNote(1).getFret(), clone.getNote(1).getFret());
        assertEquals(14, chord.getNote(2).getFret());
        assertEquals(4, clone.getNote(2).getFret());
    }

    @Test
    void testBend() {
        chord.bendNotes(Note.Bend.FULL);
        for (Note note : chord.getNotes()) {
            assertEquals(note.getBend(), Note.Bend.FULL);
        }
    }

    @Test
    void testSlide() {
        chord.slideToNotes(Note.Slide.UP);
        chord.slideFromNotes(Note.Slide.DOWN);
        for (Note note : chord.getNotes()) {
            assertEquals(note.getSlideTo(), Note.Slide.UP);
            assertEquals(note.getSlideFrom(), Note.Slide.DOWN);
        }
    }
}
