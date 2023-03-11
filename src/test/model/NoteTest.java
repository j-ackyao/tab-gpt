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
        original.bend(Note.Bend.FULL);
        original.slideTo(Note.Slide.UP);
        Note clone = original.cloneNote();
        clone.edit(Note.MUTE);
        original.bend(Note.Bend.NONE);
        clone.bend(Note.Bend.HALF);
        clone.slideFrom(Note.Slide.DOWN);
        assertNotEquals(original.getFret(), clone.getFret());
        assertNotEquals(original.getBend(), clone.getBend());
        assertEquals(original.getSlideTo(), clone.getSlideTo());
        assertNotEquals(original.getSlideFrom(), clone.getSlideFrom());
        assertEquals(12, original.getFret());
        assertEquals(Note.MUTE, clone.getFret());
        assertEquals(Note.Bend.NONE, original.getBend());
        assertEquals(Note.Bend.HALF, clone.getBend());
    }

    @Test
    void testBend() {
        Note bending = new Note(12);
        assertEquals(Note.Bend.FULL, Note.Bend.getBend("FULL"));
        bending.bend(Note.Bend.FULL);
        assertEquals(bending.getFret() + Note.FULL_BEND_STRING, bending.toString());
        assertEquals(Note.Bend.HALF, Note.Bend.getBend("half"));
        bending.bend(Note.Bend.HALF);
        assertEquals(bending.getFret() + Note.HALF_BEND_STRING, bending.toString());
        assertEquals(Note.Bend.NONE, Note.Bend.getBend("none"));
        bending.bend(Note.Bend.NONE);
        assertEquals(Integer.toString(bending.getFret()), bending.toString());
    }

    @Test
    void testInvalidBend() {
        try {
            Note.Bend.getBend("notABend");
            fail("Exception expected");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testSlide() {
        Note sliding = new Note(12);
        assertEquals(Note.Slide.UP, Note.Slide.getSlide("UP"));
        sliding.slideTo(Note.Slide.UP);
        assertEquals(Note.SLIDE_UP_STRING + sliding.getFret(), sliding.toString());
        assertEquals(Note.Slide.DOWN, Note.Slide.getSlide("down"));
        sliding.slideFrom(Note.Slide.DOWN);
        assertEquals(Note.SLIDE_UP_STRING + sliding.getFret() + Note.SLIDE_DOWN_STRING, sliding.toString());
        assertEquals(Note.Slide.NONE, Note.Slide.getSlide("none"));
        sliding.slideTo(Note.Slide.NONE);
        sliding.slideFrom(Note.Slide.NONE);
        assertEquals(Integer.toString(sliding.getFret()), sliding.toString());
    }

    @Test
    void testInvalidSlide() {
        try {
            Note.Bend.getBend("notABend");
            fail("Exception expected");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
}
