package model;


/**
 * Note represents the one point on a string in the tab, whether it's a fret number or other guitar notation
 */
public class Note {

    public static final int EMPTY = -1;
    public static final int MUTE = -2;

    public static final String EMPTY_STRING = "-";
    public static final String MUTE_STRING = "x";

    private int fret;

    /**
     * @REQUIRES: fret >= 0
     * @EFFECTS: constructor for Note given fret number, sets type to fret
     */
    public Note(int fret) {
        this.fret = fret;
    }

    /**
     * @REQUIRES: fret >= 0
     * @MODIFIES: this
     * @EFFECTS: changes the type to FRET, and changes the fret number
     */
    public void edit(int fret) {
        this.fret = fret;
    }

    /**
     * @EFFECTS: returns string representation of note, returns empty representation if invalid fret
     */
    public String toString() {
        switch (fret) {
            case EMPTY:
                return "-";
            case MUTE:
                return "x";
            default:
                return fret >= 0 ? Integer.toString(fret) : EMPTY_STRING;
        }
    }

    public int getFret() {
        return fret;
    }

    /**
     * @EFFECTS: clones Note, does not implement Cloneable
     */
    public Note cloneNote() {
        return new Note(fret);
    }
}
