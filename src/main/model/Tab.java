package model;

import model.eventlogger.Event;
import model.eventlogger.EventLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tab represents the tab, hold an ArrayList of Chords in order
 * Handles the structure of the tab
 */
public class Tab {

    public static final String[] STANDARD_TUNING = {"E", "B", "G", "D", "A", "E"};

    // the amount of CHORD_SPACER to be placed in between chords
    // should be greater than 1
    public static final int CHORD_SPACING = 3;
    // the string to be used draw the lines
    public static final String LINE_CHAR = "-";

    // the number of guitar strings of Tab
    public final int size;
    // private so that it can't be modified outside
    private final ArrayList<Chord> chords;
    private final String name;
    private final String[] tuning;

    /**
     * @EFFECTS: constructs tab by instantiating a new arraylist of chord with standard tuning
     */
    public Tab(String name) {
        this.chords = new ArrayList<>();
        this.size = STANDARD_TUNING.length;
        this.name = name;
        this.tuning = STANDARD_TUNING;
    }

    /**
     * @REQUIRES: tuning.length > 0
     * @EFFECTS: constructs tab by instantiating a new arraylist of segments
     */
    public Tab(String name, String[] tuning) {
        this.chords = new ArrayList<>();
        this.size = tuning.length;
        this.name = name;
        this.tuning = tuning;
    }

    /**
     * @EFFECTS: returns the Tab as string without chord number pos (default toString)
     */
    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * @EFFECTS: returns the Tab as string represented in tab notation with or without chord number pos
     */
    public String toString(boolean chordPos) {
        String[] output = new String[size + (chordPos ? 1 : 0)];

        // finding the longest string in tuning
        // 1 is the space between vertical line and tuning notes
        int tuningMaxLength = 1 + Arrays.stream(tuning)
                .map(String::length).reduce((a, b) -> a > b ? a : b).orElse(1);

        for (int i = 0; i < tuning.length; i++) {
            output[i] = tuning[i] + repeat(tuningMaxLength - tuning[i].length(), " ")
                    + "|" + repeat(CHORD_SPACING, LINE_CHAR);
        }

        if (chordPos) {
            // 1 accounts for the | in aligning pos num with respective chords
            output[size] = repeat(tuningMaxLength + 1 + CHORD_SPACING, " ");
        }

        for (int i = 0; i < chords.size(); i++) {
            Chord c = chords.get(i);
            // getting the longest note's string in chord
            // in case of error, default to 1 (shouldn't happen)
            int noteMaxLength = Arrays.stream(c.getNotes())
                    .map(a -> a.toString().length()).reduce((a, b) -> a > b ? a : b).orElse(1);

            // accounts for chords of different sizes
            for (int j = 0; j < size; j++) {
                output[j] += (j < c.size ? c.getNote(j) : LINE_CHAR) + repeat(CHORD_SPACING + noteMaxLength
                        - (j < c.size ? c.getNote(j).toString().length() : 1), LINE_CHAR);
            }
            if (chordPos) {
                output[size] += i + repeat(CHORD_SPACING + noteMaxLength - Integer.toString(i).length(), " ");
            }
        }

        return String.join("\n", output);
    }

    /**
     * @EFFECTS: returns String s repeated n times, helper function for toString
     */
    private String repeat(int n, String s) {
        return String.join("", Collections.nCopies(n, s));
    }

    /**
     * @EFFECTS: adds a new Chord to the end of the tab's list of Chords and returns it
     * @MODIFIES: this
     */
    public Chord addChord() {
        EventLog.getInstance().logEvent(new Event("Added chord to end of tab"));

        Chord newChord = new Chord(this.size);
        chords.add(newChord);
        return newChord;
    }

    /**
     * @REQUIRES: chord.size == this.size
     * @EFFECTS: adds given Chord to end of tab's list of Chord
     * @MODIFIES: this
     */
    public void addChord(Chord chord) {
        EventLog.getInstance().logEvent(new Event("Added " + Arrays.toString(chord.getNotes())
                + " to end of tab"));

        chords.add(chord);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: inserts a new Chord at given position and returns it
     * @MODIFIES: this
     */
    public Chord insertChord(int pos) {
        EventLog.getInstance().logEvent(new Event("Inserted chord at position " + pos));

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
        EventLog.getInstance().logEvent(new Event("Inserted " + Arrays.toString(chord.getNotes())
                + " at position " + pos));

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
     * @EFFECTS: edits the Chord at given position to given chord
     * @MODIFIES: this
     */
    public void editChord(int pos, Chord chord) {
        chords.set(pos, chord);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: adds bending to the Chord at given position with given Bend
     * @MODIFIES: this
     */
    public void bendChord(int pos, Note.Bend bend) {
        chords.get(pos).bendNotes(bend);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: adds slideTo to the Chord at given position with given slide
     * @MODIFIES: this
     */
    public void slideToChord(int pos, Note.Slide slide) {
        chords.get(pos).slideToNotes(slide);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: adds slideFrom to the Chord at given position with given slide
     * @MODIFIES: this
     */
    public void slideFromChord(int pos, Note.Slide slide) {
        chords.get(pos).slideFromNotes(slide);
    }

    /**
     * @REQUIRES: pos >= 0
     * @EFFECTS: removes a Chord from pos given position and returns it
     * @MODIFIES: this
     */
    public Chord removeChord(int pos) {
        EventLog.getInstance().logEvent(new Event("Removed chord from tab at position " + pos));

        return chords.remove(pos);
    }

    /**
     * @REQUIRES: pos >= 0, this.chords.size > 0
     * @EFFECTS: returns the Chord at given position with
     */
    public Chord getChord(int pos) {
        return chords.get(pos);
    }

    /**
     * @EFFECTS: returns the number of chords in tabs
     */
    public int getLength() {
        return chords.size();
    }

    public String getName() {
        return this.name;
    }

    public String[] getTuning() {
        return tuning;
    }

    public ArrayList<Chord> getChords() {
        return this.chords;
    }
}
