package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tab represents the tab, hold an ArrayList of Chords in order
 * Handles the structure of the tab
 */
public class Tab {

    public static final String[] STANDARD_TUNING = {"E", "B", "G", "D", "A", "E"};

    private final ArrayList<Chord> chords;
    public final int size;
    public final String[] tuning;
    //TODO ADD TAB SPACING AS CONSTANT AND REFORMAT TOSTRING

    /**
     * @REQUIRES: size  > 0
     * @EFFECTS: constructs tab by instantiating a new arraylist of segments with standard tuning
     */
    public Tab(int size) {
        this.chords = new ArrayList<>();
        this.size = size;
        this.tuning = STANDARD_TUNING;
    }

    /**
     * @REQUIRES: tuning.length > 0
     * @EFFECTS: constructs tab by instantiating a new arraylist of segments
     */
    public Tab(String[] tuning) {
        this.chords = new ArrayList<>();
        this.size = tuning.length;
        this.tuning = tuning;
    }

    /**
     * @EFFECTS: returns the Tab as string without chord number pos (default toString)
     */
    public String toString() {
        return toString(false);
    }

    /**
     * @EFFECTS: returns the Tab as string represented in tab notation with or without chord number pos
     */
    public String toString(boolean chordPos) {
        String[] output = new String[size + (chordPos? 1 : 0)];
        Arrays.fill(output, "");

        if (chordPos){
            output[size] += "    ";
        }

        for (int i = 0; i < tuning.length; i++) {
            output[i] += tuning[i] + "|";
        }

        for (int i = 0; i < chords.size(); i++) {
            Chord c = chords.get(i);
            boolean wide = c.containsWideFret();
            for (int j = 0; j < size; j++) {
                Note n = c.getNote(j);
                output[j] += Note.EMPTY_STRING + Note.EMPTY_STRING + n;

                if (wide) {
                    output[j] += n.getFret() < 10 ? Note.EMPTY_STRING : "";
                }


            }

            if(chordPos) {
                output[size] += i + (wide && i < 10? "   " : "  ");
            }
        }

        return String.join(Note.EMPTY_STRING + "\n", output) + (chordPos? "" : Note.EMPTY_STRING);
    }

    /**
     * @EFFECTS: adds a new Chord to the end of the tab's list of Chords and returns it
     * @MODIFIES: this
     */
    public Chord addChord() {
        Chord newChord = new Chord(this.size);
        this.chords.add(newChord);
        return newChord;
    }

    /**
     * @REQUIRES: chord.size == this.size
     * @EFFECTS: adds given Chord to end of tab's list of Chord WITH reference
     * @MODIFIES: this
     */
    public void addChord(Chord chord) {
        chords.add(chord);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: inserts a new Chord at given position and returns it
     * @MODIFIES: this
     */
    public Chord insertChord(int pos) {
        Chord chord = new Chord(size);
        chords.add(pos, chord);
        return chord;
    }

    /**
     * @REQUIRES: pos >= 0, chord.size == this.size
     * @EFFECTS: inserts given Chord at given position of list of Chords WITH reference
     * @MODIFIES: this
     */
    public void insertChord(int pos, Chord chord) {
        chords.add(pos, chord);
    }

    /**
     * @REQUIRES: pos >= 0 and elements of notes must be valid fret (or Note constants)
     * @EFFECTS: edits the Chord at given position using given notes
     * @MODIFIES: this
     */
    public void editChord(int pos, int[] notes) {
        chords.get(pos).editNotes(notes);
    }

    /**
     * @REQUIRES: pos >= 0, chord.size == this.size
     * @EFFECTS: edits the Chord at given position to given chord WITH reference
     * @MODIFIES: this
     */
    public void editChord(int pos, Chord chord) {
        chords.set(pos, chord);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: removes a chord from pos given position and returns it
     * @MODIFIES: this
     */
    public Chord removeChord(int pos) {
        return chords.remove(pos);
    }

    public ArrayList<Chord> getChords() {
        return new ArrayList<>(this.chords);
    }

}
