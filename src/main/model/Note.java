package model;


/**
 * Note represents the one point on a string in the tab, whether it's a fret number or other guitar notation
 */
public class Note {

    // represents bending
    public enum Bend {
        // constants
        NONE(""), HALF(HALF_BEND_STRING), FULL(FULL_BEND_STRING);

        // string representation of bends
        private final String stringRep;
        // private constructor of enum
        Bend(String string) {
            this.stringRep = string;
        }

        // for ui, get bend given name of bend
        public static Bend getBend(String string) {
            return Bend.valueOf(string.toUpperCase());
        }

        @Override
        public String toString() {
            return this.stringRep;
        }
    }

    // represents slides
    public enum Slide {
        // constants
        NONE(""), DOWN(SLIDE_DOWN_STRING), UP(SLIDE_UP_STRING);

        // string representation of slides
        private final String stringRep;
        // private constructor of enum
        Slide(String string) {
            this.stringRep = string;
        }

        // for ui, gets slide given name of slide
        public static Slide getSlide(String string) {
            return Slide.valueOf(string.toUpperCase());
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
    // string constants of bends
    public static final String FULL_BEND_STRING = "┘";
    public static final String HALF_BEND_STRING = "┘½";

    // string constants of slides
    public static final String SLIDE_DOWN_STRING = "\\";
    public static final String SLIDE_UP_STRING = "/";


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
                return fret >= 0 ? slideTo + Integer.toString(fret) + bend + slideFrom : EMPTY_STRING;
        }
    }

    public int getFret() {
        return fret;
    }

    public Bend getBend() {
        return this.bend;
    }

    public Slide getSlideTo() {
        return this.slideTo;
    }

    public Slide getSlideFrom() {
        return this.slideFrom;
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
