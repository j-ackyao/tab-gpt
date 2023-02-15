package model;


/**
 * Note represents the one point on a string in the tab, whether it's a fret number or other guitar notation
 */
public class Note {

    // int representation of other guitar notations
    public static final int EMPTY = -1;
    public static final int MUTE = -2;

    // string representation of other guitar notations
    public static final String EMPTY_STRING = "-";
    public static final String MUTE_STRING = "x";

    private int fret;

    /**
     * @REQUIRES: fret >= 0 or one of constants representing other guitar notations
     * @EFFECTS: constructor for Note given fret
     */
    public Note(int fret) {
        this.fret = fret;
    }

    /**
     * @REQUIRES: fret >= 0 or one of constants
     * @EFFECTS: changes fret to provided fret
     * @MODIFIES: this
     */
    public void edit(int fret) {
        this.fret = fret;
    }

    /**
     * @EFFECTS: returns string representation of note, returns empty representation if somehow invalid fret
     */
    public String toString() {
        switch (fret) {
            case EMPTY:
                return EMPTY_STRING;
            case MUTE:
                return MUTE_STRING;
            default:
                return fret >= 0 ? Integer.toString(fret) : EMPTY_STRING;
        }
    }

    public int getFret() {
        return fret;
    }

    /**
     * @EFFECTS: clones Note without implementing Cloneable
     */
    public Note cloneNote() {
        return new Note(fret);
    }
}
