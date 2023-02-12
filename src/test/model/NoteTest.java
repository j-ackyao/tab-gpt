package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {


    @Test
    void testFretConstructor() {
        Note note = new Note(12);
        assertEquals(12, note.getFret());
        assertEquals("12", note.toString());
    }

    @Test
    void testMuteConstructor() {
        Note note = new Note(Note.MUTE);
        assertEquals(Note.MUTE, note.getFret());
        assertEquals(Note.MUTE_STRING, note.toString());
    }

    @Test
    void testEmptyConstructor() {
        Note note = new Note(Note.EMPTY);
        assertEquals(Note.EMPTY, note.getFret());
        assertEquals(Note.EMPTY_STRING, note.toString());
    }

    @Test
    void testToString() {
        Note mute = new Note(Note.MUTE);
        Note empty = new Note(Note.EMPTY);
        Note fret = new Note(0);
        Note invalidFret = new Note(-100);
        assertEquals(Note.MUTE_STRING, mute.toString());
        assertEquals(Note.EMPTY_STRING, empty.toString());
        assertEquals("0", fret.toString());
        assertEquals(Note.EMPTY_STRING, invalidFret.toString());
    }

    @Test
    void testMuteToFretEdit() {
        Note note = new Note(Note.MUTE);
        note.edit(15);
        assertEquals(15, note.getFret());
        assertEquals("15", note.toString());
    }


    @Test
    void testClone() {
        Note original = new Note(12);
        Note clone = original.cloneNote();
        clone.edit(Note.MUTE);
        assertNotEquals(original.getFret(), clone.getFret());
        assertEquals(12, original.getFret());
        assertEquals(Note.MUTE, clone.getFret());
    }
}
