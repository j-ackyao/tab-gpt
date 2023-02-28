package persistence;

import model.Chord;
import model.Note;
import model.Tab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonTest {

    Tab tab;
    Chord chord;
    Chord altChord;

    @BeforeEach
    void testInit() {
        tab = new Tab("test");
        chord = new Chord(new int[]{3, 0, 0, 0, 2, 3});
        altChord = new Chord(new int[]{Note.MUTE, 1, 2, 2, 0, Note.EMPTY});

    }

    @Test
    void testReadTestTab() {
        System.out.println(Json.load("test"));
    }

    @Test
    void testSaveEmptyTab() {
        tab.addChord(chord);
        tab.addChord(altChord);
        Json.save(tab);

    }

}
