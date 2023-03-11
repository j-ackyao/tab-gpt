package model;


/**
 * Note represents the one point on a string in the tab, whether it's a fret number or other guitar notation
 */
public class Note {

    public enum Bend {
        NONE(""), HALF(HALF_BEND), FULL(FULL_BEND);

        private final String stringRep;
        Bend(String string) {
            this.stringRep = string;
        }

        public static Bend getBend(String string) {
            return Bend.valueOf(string.toUpperCase());
        }

        @Override
        public String toString() {
            return this.stringRep;
        }
    }

    public enum Slide {
        NONE(""), DOWN(SLIDE_DOWN), UP(SLIDE_UP);

        private String stringRep;
        Slide(String string) {
            this.stringRep = string;
        }

        public static Slide getSlide(String string) {
            try {
                return Slide.valueOf(string.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return this.stringRep;
        }
    }

    // int representation of other guitar notations
    public static final int EMPTY = -1;
    public static final int MUTE = -2;

    // string representation of other guitar notations
    public static final String EMPTY_STRING = "-";
    public static final String MUTE_STRING = "x";

    // below are constants for bends and slides
    public static final String NONE = "";
    // string constants of bends
    public static final String FULL_BEND = "┘";
    public static final String HALF_BEND = "┘½";

    // string constants of slides
    public static final String SLIDE_DOWN = "\\";
    public static final String SLIDE_UP = "/";


    private int fret;
    private Bend bend;
    private Slide slideTo;
    private Slide slideFrom;

    /**
     * @REQUIRES: fret >= 0 or one of constants representing other guitar notations
     * @EFFECTS: constructor for Note given fret
     */
    public Note(int fret) {
        this.fret = fret;
        this.bend = Bend.NONE;
        this.slideTo = Slide.NONE;
        this.slideFrom = Slide.NONE;
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
     * @EFFECTS: changes bend of fret to given bend
     * @MODIFIES: this
     */
    public void bend(Bend bend) {
        this.bend = bend;
    }

    /**
     * @EFFECTS: changes slideTo of fret to given slide
     * @MODIFIES: this
     */
    public void slideTo(Slide slide) {
        this.slideTo = slide;
    }

    /**
     * @EFFECTS: changes slideFrom of fret to given bend
     * @MODIFIES: this
     */
    public void slideFrom(Slide slide) {
        this.slideFrom = slide;
    }

    /**
     * @EFFECTS: returns string representation of note, returns empty representation if somehow invalid fret
     */
    @Override
    public String toString() {
        switch (fret) {
            case EMPTY:
                return EMPTY_STRING;
            case MUTE:
                return MUTE_STRING;
            default:
                return fret >= 0 ? slideFrom + Integer.toString(fret) + bend + slideTo : EMPTY_STRING;
        }
    }

    public int getFret() {
        return fret;
    }

    /**
     * @EFFECTS: clones Note without implementing Cloneable
     */
    public Note cloneNote() {
        Note clone = new Note(fret);
        clone.bend(this.bend);
        clone.slideTo(this.slideTo);
        clone.slideFrom(this.slideFrom);
        return clone;
    }
}
