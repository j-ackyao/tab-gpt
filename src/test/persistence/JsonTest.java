package persistence;

import model.Chord;
import model.Note;
import model.Tab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    String[] tuning;
    Tab tab;
    Chord chord;
    Chord altChord;

    @BeforeEach
    void testInit() {
        tuning = new String[]{"A", "Bb", "C", "D", "F", "Test Tune"};
        chord = new Chord(new int[]{3, 0, 0, 0, 2, 3});
        altChord = new Chord(new int[]{Note.MUTE, 1, 2, 2, 0, Note.EMPTY});

    }


    @Test
    void testLoadTestTab() {
        Tab load = null;
        try {
            load = Json.load("testTabs/loadTest");
        } catch (IOException io) {
            fail("loadTest.tab not found or unable to load");
        }
        assertEquals("testTabs/loadTest", load.getName());
        assertEquals(6, load.size);
        assertEquals(2, load.getLength());
        assertArrayEquals(tuning, load.getTuning());
        assertArrayEquals(altChord.getFrets(), load.getChord(0).getFrets());
        assertArrayEquals(chord.getFrets(), load.getChord(1).getFrets());
    }

    @Test
    void testSaveTab() {
        tab = new Tab("testTabs/saveTest", tuning);
        tab.addChord(chord);
        tab.addChord(altChord);
        tab.addChord(chord);

        Tab load = null;
        try {
            Json.save(tab);
            load = Json.load("testTabs/saveTest");
        } catch (FileNotFoundException fnf) {
            fail("File not found exception");
        } catch (IOException io) {
            fail("Unable to find saveTest");
        }

        assertEquals("testTabs/saveTest", load.getName());
        assertEquals(6, load.size);
        assertEquals(3, load.getLength());
        assertArrayEquals(tuning, load.getTuning());
        assertArrayEquals(chord.getFrets(), tab.getChord(0).getFrets());
        assertArrayEquals(altChord.getFrets(), tab.getChord(1).getFrets());
        assertArrayEquals(chord.getFrets(), tab.getChord(2).getFrets());
    }

    @Test
    void testInvalidLoad() {
        try {
            Json.load("\\");
            fail("Loaded invalid tab");
        } catch (IOException io) {
            // Expected behaviour
        }
    }

}
