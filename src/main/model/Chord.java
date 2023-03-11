package model;


/**
 * Chord represents a chord/a column of notes, holds an array of Note in order from top to bottom
 */
public class Chord {

    // number of strings in Chord
    public final int size;

    // ArrayList is not necessary for Chord as the list will always be fixed size
    private final Note[] notes;

    /**
     * @REQUIRES: size > 0
     * @EFFECTS: constructor for a Chord given number of strings (size), all strings are empty
     * recommended constructor in tab editing, as it can be initialized with consistent size
     */
    public Chord(int size) {
        this.size = size;
        this.notes = new Note[size];
        for (int i = 0; i < size; i++) {
            notes[i] = new Note(Note.EMPTY);
        }
    }

    /**
     * @REQUIRES: frets.length > 0, elements of frets must be valid fret
     * @EFFECTS: constructor for a Chord given list of int representing frets
     * NOT recommended for tab editing, possible future use, mainly for debugging
     */
    public Chord(int[] frets) {
        this.size = frets.length;
        this.notes = new Note[size];
        for (int i = 0; i < size; i++) {
            notes[i] = new Note(frets[i]);
        }
    }

    /**
     * @EFFECTS: private constructor for cloning Chord
     */
    private Chord(Note[] notes) {
        this.size = notes.length;
        this.notes = new Note[notes.length];
        for (int i = 0; i < notes.length; i++) {
            this.notes[i] = notes[i].cloneNote();
        }
    }

    /**
     * @REQUIRES: pos < size and fret must be valid fret (>=0 or Note constant)
     * @EFFECTS: modifies the note at given pos to fret (zero-based index)
     * @MODIFIES: this
     */
    public void editNote(int pos, int fret) {
        notes[pos].edit(fret);
    }

    /**
     * @REQUIRES: elements of notes must be valid fret
     * @EFFECTS: modifies the notes of this given list of frets
     * if given notes are less than lines, rest of this.notes are edited to Note.EMPTY
     * @MODIFIES: this
     */
    public void editNotes(int[] frets) {
        for (int i = 0; i < size; i++) {
            if (i < frets.length) {
                this.notes[i].edit(frets[i]);
            } else {
                this.notes[i].edit(Note.EMPTY);
            }
        }
    }

    public void bendNotes(Note.Bend bend) {
        for (Note note : notes) {
            note.bend(bend);
        }
    }

    public int getFret(int pos) {
        return notes[pos].getFret();
    }

    public int[] getFrets() {
        int[] frets = new int[size];
        for (int i = 0; i < size; i++) {
            frets[i] = getFret(i);
        }
        return frets;
    }

    public Note getNote(int pos) {
        return notes[pos].cloneNote();
    }

    public Note[] getNotes() {
        return notes.clone();
    }

    /**
     * @EFFECTS: clones Chord without implementing Cloneable
     */
    public Chord cloneChord() {
        return new Chord(notes);
    }
}
