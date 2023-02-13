package model;


/**
 * Chord represents a chord/a column of notes, holds an array of Note in order
 */
public class Chord {

    public final int size;

    // ArrayList is not necessary for Chord as the list will always be fixed size
    private final Note[] notes;

    /**
     * @REQUIRES: size > 0
     * @EFFECTS: constructor for a Chord given number of strings (size), all strings are empty
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
     * @REQUIRES: string < size and fret must be valid fret (>=0 or Note constant)
     * @EFFECTS: modifies the note at given string to fret (zero-based index)
     * @MODIFIES: this
     */
    public void editNote(int string, int fret) {
        notes[string].edit(fret);
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

    public int getFret(int string) {
        return notes[string].getFret();
    }

    public int[] getFrets() {
        int[] frets = new int[size];
        for (int i = 0; i < size; i++) {
            frets[i] = getFret(i);
        }
        return frets;
    }

    public Note getNote(int string) {
        return notes[string].cloneNote();
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
