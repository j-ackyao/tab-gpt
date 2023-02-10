package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Tab {

    private final ArrayList<Chord> chords;
    public final int size;

    /**
     * REQUIRES: size  > 0
     * EFFECTS: constructs tab by instantiating a new arraylist of segments
     */
    public Tab(int size) {
        this.chords = new ArrayList<>();
        this.size = size;
    }

    /**
     * EFFECTS: returns the Tab as string represented in tab notation
     */
    public String toString() {
        String[] output = new String[size];
        Arrays.fill(output, "");

        for (Chord s : chords) {
            for (int i = 0; i < size; i++) {
                output[i] += Note.EMPTY_STRING + s.getNote(i);
            }
        }

        return String.join(Note.EMPTY_STRING + "\n", output) + Note.EMPTY_STRING;
    }

    /**
     * EFFECTS: adds given Chord to end of tab's list of Chord WITH reference
     * REQUIRES: chord.size == this.size
     * MODIFIES: this
     */
    // TODO: this is for keeping reference?
    public void addChord(Chord chord) {
        chords.add(chord);
    }

    /**
     * EFFECTS: adds a new Chord to the end of the tab's list of Chords and returns it
     * MODIFIES: this
     */
    public Chord addChord() {
        Chord newChord = new Chord(this.size);
        this.chords.add(newChord);
        return newChord;
    }

    /**
     * EFFECTS: inserts given Chord at given position of list of Chords WITH reference
     * MODIFIES: this
     */
    // TODO
    public void insertChord(int stringPos, Chord chord) {

    }


    public ArrayList<Chord> getChords() {
        return new ArrayList<>(this.chords);
    }

}
