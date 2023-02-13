package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tab represents the tab, hold an ArrayList of Chords in order
 * Handles the structure of the tab
 */
public class Tab {

    public static final String[] STANDARD_TUNING = {"E", "B", "G", "D", "A", "E"};

    // the following constants can be changed
    // the amount of EMPTY_SPACE to be placed in between chords
    public static final int CHORD_SPACING = 3;
    // the string to be used to space out chords
    public static final String CHORD_SPACER = Note.EMPTY_STRING;

    // private so that it can't be modified outside
    private final ArrayList<Chord> chords;
    private final String[] tuning;
    public final int size;

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

        for (int i = 0; i < tuning.length; i++) {
            // 2 represents the longest possible string length for tuning, eg. Bb.
            // If less than two, add space to align text
            output[i] = tuning[i] + (tuning[i].length() < 2 ? " " : "") + "|" + repeat(CHORD_SPACING, CHORD_SPACER);
        }

        if (chordPos){
            // 3 represents the string tuning and | line
            output[size] = repeat(3 + CHORD_SPACING, " ");
        }

        for (int i = 0; i < chords.size(); i++) {
            Chord c = chords.get(i);
            // getting the longest note's string in chord
            int noteMaxLength = Arrays.stream(c.getNotes())
                    .map(Note::toString)
                    .reduce((a, b) -> a.length() > b.length() ? a : b)
                    .orElse(" ").length();

            for (int j = 0; j < size; j++) {
                Note n = c.getNote(j);
                output[j] += n + repeat(CHORD_SPACING + noteMaxLength - n.toString().length(), CHORD_SPACER);

            }

            if(chordPos) {
                output[size] += i + repeat(CHORD_SPACING + noteMaxLength - Integer.toString(i).length(), " ");
            }
        }

        return String.join("\n", output);
    }

    /**
     * @EFFECTS: returns String s repeated n times
     */
    private String repeat(int n, String s) {
        return String.join("", Collections.nCopies(n, s));
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
     * @EFFECTS: adds given Chord to end of tab's list of Chord
     * @MODIFIES: this
     */
    public void addChord(Chord chord) {
        chords.add(chord.cloneChord());
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
     * @EFFECTS: inserts given Chord at given position of list of Chords
     * @MODIFIES: this
     */
    public void insertChord(int pos, Chord chord) {
        chords.add(pos, chord.cloneChord());
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
     * @EFFECTS: edits the Chord at given position to given chord
     * @MODIFIES: this
     */
    public void editChord(int pos, Chord chord) {
        chords.set(pos, chord.cloneChord());
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: removes a chord from pos given position and returns it
     * @MODIFIES: this
     */
    public Chord removeChord(int pos) {
        return chords.remove(pos);
    }

    public String[] getTuning() {
        return tuning.clone();
    }

    public ArrayList<Chord> getChords() {
        return new ArrayList<>(this.chords);
    }

}
