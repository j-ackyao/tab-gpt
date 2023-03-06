package persistence;


import model.Chord;
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
public final class Json {

    // location of where data is saved to
    private static final String PATH = "./data/";
    // extension of file type
    private static final String EXTN = ".tab";

    // privatizes constructor, Json won't be instantiated as an object
    private Json() {

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

        for (Object o : jsonTab.getJSONArray("chords")) {
            ArrayList<Integer> frets = new ArrayList<>();
            ((JSONArray) o).forEach(fret -> frets.add((int) fret));
            chords.add(new Chord(frets.stream().mapToInt(Integer::intValue).toArray()));
        }

        chords.forEach(tab::addChord);

        return tab;
    }

    /**
     * @EFFECTS: helper for load(), reads file and returns string
     */
    private static String read(String name) throws IOException {
        StringBuilder builder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(PATH + name + EXTN), StandardCharsets.UTF_8);
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

        PrintWriter writer;
        writer = new PrintWriter(PATH + name + EXTN);

        // name is not stored in file, as the name of the tab is whatever the file name is
        JSONObject json = new JSONObject();
        json.put("tuning", toJArray(tuning));
        ArrayList<int[]> chordsAsFrets = new ArrayList<>();
        chords.forEach(c -> chordsAsFrets.add(c.getFrets()));
        json.put("chords", toJArray(chordsAsFrets.toArray()));

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
