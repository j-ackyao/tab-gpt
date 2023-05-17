package persistence;

import model.Chord;
import model.Note;
import model.Tab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JsonAndWriterTest {

    String[] tuning;
    Tab tab;
    Chord chord;
    Chord altChord;

    @BeforeEach
    void testInit() {
        tuning = new String[]{"A", "Bb", "C", "D", "F", "Fake tuning"};
        chord = new Chord(new int[]{3, 0, 0, 0, 2, 3}).slideFromNotes(Note.Slide.DOWN);
        altChord = new Chord(new int[]{Note.MUTE, 1, 2, 2, 0, Note.EMPTY})
                .bendNotes(Note.Bend.FULL).slideToNotes(Note.Slide.UP);

    }


    @Test
    void testLoadTestTab() {
        Tab load = null;
        try {
            load = JsonAndWriter.load("./data/testTabs/loadTest");
        } catch (IOException io) {
            fail("loadTest.tab not found or unable to load");
        }
        assertEquals("./data/testTabs/loadTest", load.getName());
        assertEquals(6, load.size);
        assertEquals(2, load.getLength());
        assertArrayEquals(tuning, load.getTuning());
        assertArrayEquals(altChord.getFrets(), load.getChord(0).getFrets());
        Arrays.asList(load.getChord(0).getNotes()).forEach(n -> assertEquals(Note.Bend.HALF, n.getBend()));
        Arrays.asList(load.getChord(0).getNotes()).forEach(n -> assertEquals(Note.Slide.DOWN, n.getSlideFrom()));
        Arrays.asList(load.getChord(0).getNotes()).forEach(n -> assertEquals(Note.Slide.NONE, n.getSlideTo()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Bend.FULL, n.getBend()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Slide.UP, n.getSlideTo()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Slide.NONE, n.getSlideFrom()));
        assertArrayEquals(chord.getFrets(), load.getChord(1).getFrets());
    }

    @Test
    void testSaveTab() {
        tab = new Tab("./data/testTabs/saveTest", tuning);
        tab.addChord(chord);
        tab.addChord(altChord);


        Tab load = null;
        try {
            JsonAndWriter.save(tab);
            load = JsonAndWriter.load("./data/testTabs/saveTest");
        } catch (FileNotFoundException fnf) {
            fail("File not found exception");
        } catch (IOException io) {
            fail("Unable to find saveTest");
        }

        assertEquals("./data/testTabs/saveTest", load.getName());
        assertEquals(6, load.size);
        assertEquals(2, load.getLength());
        assertArrayEquals(tuning, load.getTuning());
        assertArrayEquals(chord.getFrets(), tab.getChord(0).getFrets());
        assertArrayEquals(altChord.getFrets(), tab.getChord(1).getFrets());
        Arrays.asList(load.getChord(0).getNotes()).forEach(n -> assertEquals(Note.Bend.NONE, n.getBend()));
        Arrays.asList(load.getChord(0).getNotes()).forEach(n -> assertEquals(Note.Slide.DOWN, n.getSlideFrom()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Bend.FULL, n.getBend()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Slide.NONE, n.getSlideFrom()));
        Arrays.asList(load.getChord(1).getNotes()).forEach(n -> assertEquals(Note.Slide.UP, n.getSlideTo()));
    }

    @Test
    void testInvalidLoad() {
        try {
            JsonAndWriter.load("\\");
            fail("Loaded invalid tab");
        } catch (IOException io) {
            // Expected behaviour
        }
    }

    @Test
    void testExport() {
        tab = new Tab("./data/testTabs/exportText");
        tab.addChord(chord);
        tab.addChord(altChord);
        try {
            JsonAndWriter.export(tab);
        } catch (IOException ioe) {
            fail("Unexpected IOException when exporting");
        }
        try {
            StringBuilder builder = new StringBuilder();
            Stream<String> stream = Files.lines(
                    Paths.get("./data/testTabs/exportText" + JsonAndWriter.EXPORT_EXTN),
                    StandardCharsets.UTF_8);
            stream.forEach(s -> builder.append(s).append("\n"));
            stream.close();
            assertEquals(tab.toString(), builder.toString().trim());
        } catch (IOException ioe) {
            fail("Unexpected IOException when reading");
        }
    }

}
