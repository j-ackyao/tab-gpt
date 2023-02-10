package model;

public class Chord {

    public final int size;

    private final Note[] notes;

    /**
     * REQUIRES: size > 0
     * EFFECTS: constructor for a Chord given number of strings (size), all strings are empty
     */
    public Chord(int size) {
        this.size = size;
        this.notes = new Note[size];
        for (int i = 0; i < size; i++) {
            notes[i] = new Note(Note.EMPTY);
        }
    }

    /**
     * EFFECTS: private constructor for cloning Chord
     */
    private Chord(int size, Note[] notes) {
        this.size = size;
        this.notes = new Note[size];
        for (int i = 0; i < size; i++) {
            this.notes[i] = notes[i].cloneNote();
        }
    }

    /**
     * REQUIRES: string < size and fret must be valid fret (>=0 or Note constant)
     * EFFECTS: modifies the note at given string to fret (zero-based index)
     * MODIFIES: this
     */
    public void editNote(int string, int fret) {
        notes[string].edit(fret);
    }

    /**
     * REQUIRES: elements of notes must be valid fret
     * EFFECTS: modifies the notes of this given list of frets
     *          if given notes are less than lines, rest of this.notes are edited to Note.EMPTY
     * MODIFIES: this
     */
    public void editNotes(int[] notes) {
        for (int i = 0; i < size; i++) {
            if (i < notes.length) {
                this.notes[i].edit(notes[i]);
            } else {
                this.notes[i].edit(Note.EMPTY);
            }
        }
    }

    public Note getNote(int string) {
        return notes[string].cloneNote();
    }

    public Note[] getNotes() {
        return notes.clone();
    }

    /**
     * EFFECTS: clones Chord without implementing Cloneable
     */
    public Chord cloneChord() {
        return new Chord(size, notes);
    }
}
