package persistence;


import model.Chord;
import model.Note;
import model.Tab;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 * Class that has static methods for reading and writing save files
 * Avoids creating objects with static methods
 */
public final class JsonAndWriter {

    // extension of file type
    public static final String EXTN = ".tab";
    public static final String EXPORT_EXTN = ".txt";

    private static final int SLTO_POS = 0;
    private static final int FRET_POS = 1;
    private static final int BEND_POS = 2;
    private static final int SLFR_POS = 3;

    // privatizes constructor, JsonAndWriter won't be instantiated as an object
    private JsonAndWriter() {

    }

    /**
     * @EFFECTS: exports tab to text as txt file
     */
    public static void export(Tab tab) throws IOException {
        PrintWriter writer = new PrintWriter(tab.getName() + EXPORT_EXTN);
        writer.write(tab.toString());
        writer.close();
    }

    /**
     * @EFFECTS: generates tab given tab name (without extension) and loads all json data
     */
    public static Tab load(String name) throws IOException {
        JSONObject jsonTab;
        jsonTab = new JSONObject(read(name));

        ArrayList<String> tuning = new ArrayList<>();
        jsonTab.getJSONArray("tuning").forEach(o -> tuning.add(o.toString()));

        Tab tab = new Tab(name, tuning.toArray(new String[0]));

        ArrayList<Chord> chords = new ArrayList<>();

        // for chord in chords
        for (Object chord : jsonTab.getJSONArray("chords")) {

            ArrayList<Note> notes = new ArrayList<>();

            // for notes in chord
            for (Object noteAttributes : (JSONArray) chord) {
                JSONArray attributes = (JSONArray) noteAttributes;
                Note newNote = new Note(attributes.getInt(FRET_POS));
                newNote.slideTo(attributes.getEnum(Note.Slide.class, SLTO_POS));
                newNote.bend(attributes.getEnum(Note.Bend.class, BEND_POS));
                newNote.slideFrom(attributes.getEnum(Note.Slide.class, SLFR_POS));
                notes.add(newNote);
            }

            chords.add(new Chord(notes.toArray(new Note[0])));
        }

        chords.forEach(tab::addChord);

        return tab;
    }

    /**
     * @EFFECTS: helper for load(), reads file and returns string
     */
    private static String read(String name) throws IOException {
        StringBuilder builder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(name + EXTN), StandardCharsets.UTF_8);
        stream.forEach(builder::append);
        stream.close();
        return builder.toString();
    }


    /**
     * @EFFECTS: saves given tab and returns whether it has been successfully saved
     */
    public static void save(Tab tab) throws FileNotFoundException {
        String name = tab.getName();
        String[] tuning = tab.getTuning();
        ArrayList<Chord> chords = tab.getChords();

        PrintWriter writer = new PrintWriter(name + EXTN);

        // name is not stored in file, as the name of the tab is whatever the file name is
        JSONObject json = new JSONObject();
        json.put("tuning", toJArray(tuning));
        ArrayList<Object[][]> chordsAsFretsAndAttributes = new ArrayList<>();
        chords.forEach(c -> {
            Object[][] fretsAndAttributes = new Object[c.size][4];
            for (int i = 0; i < c.size; i++) {
                Note note = c.getNote(i);
                fretsAndAttributes[i][FRET_POS] = note.getFret();
                fretsAndAttributes[i][SLTO_POS] = note.getSlideTo();
                fretsAndAttributes[i][SLFR_POS] = note.getSlideFrom();
                fretsAndAttributes[i][BEND_POS] = note.getBend();
            }
            chordsAsFretsAndAttributes.add(fretsAndAttributes);
        });
        json.put("chords", toJArray(chordsAsFretsAndAttributes.toArray()));

        writer.print(json);
        writer.close();
    }

    /**
     * @EFFECTS: converts a list of objects into a JSONArray
     */
    private static JSONArray toJArray(Object[] objects) {
        JSONArray jsonArray = new JSONArray();
        for (Object o : objects) {
            jsonArray.put(o);
        }
        return jsonArray;
    }
}
