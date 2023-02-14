package ui;

import model.Note;
import model.Tab;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The console editor for tab
 */
public class ConsoleEditor {

    Scanner input;
    private Tab tab;

    public ConsoleEditor() {
        input = new Scanner(System.in);
        init();
        run();
    }

    /**
     * @EFFECTS: helper for constructor to handle user input and generate new Tab
     * @MODIFIES: this
     */
    private void init() {
        print("Input the tuning of the strings of tab in the form X-X-X-..., or enter to use standard tuning");
        // may include capitalized characters
        String tuning = input.nextLine();
        if ("".equals(tuning)) {
            print("Using standard tuning");
            tab = new Tab();
        } else if (tuning.split("-").length == 0) {
            print("Invalid tuning, using standard tuning");
            tab = new Tab();
        } else {
            tab = new Tab(tuning.split("-"));
        }
        print(tab);
    }


    /**
     * @EFFECTS: handles all user inputs
     * @MODIFIES: this
     */
    // Suppressed as this handles all possible commands
    @SuppressWarnings("methodlength")
    void run() {
        boolean exit = false;
        while (!exit) {
            String[] cmd = readInput().split(" ");
            String[] args = Arrays.copyOfRange(cmd, 1, cmd.length);
            // try catches around user input, just to prevent invalid input and program crashing
            // no specific behaviour for methods needed
            try {
                switch (cmd[0]) {
                    case "exit":
                        exit = true;
                        break;
                    case "add":
                    case "a":
                        consoleAddChord(args);
                        break;
                    case "insert":
                    case "i":
                        consoleInsertChord(args);
                        break;
                    case "edit":
                    case "e":
                        consoleEditChord(args);
                        break;
                    case "delete":
                    case "d":
                        consoleDeleteChord(args);
                        break;
                    case "copypaste":
                    case "cp":
                        consoleCopyPaste(args);
                        break;
                    case "help":
                    case "h":
                        help(args);
                        break;
                    case "":
                        print(tab);
                        break;
                    default:
                        print("Command not found");
                }
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    /**
     * @EFFECTS: adds empty chord/given frets to end of tab
     * @MODIFIES: this
     */
    void consoleAddChord(String[] args) {
        if (args.length == 0) {
            tab.addChord();
        } else {
            tab.addChord().editNotes(formatToFrets(args[0]));
        }
        print(tab.toString(true));
    }

    /**
     * @EFFECTS: inserts at pos (first arg) with empty chord/given frets (second arg)
     * @MODIFIES: this
     */
    void consoleInsertChord(String[] args) {
        switch (args.length) {
            case 0:
                print("Please input the position to insert chord (and chord in form X-X-X-...");
                break;
            case 1:
                tab.insertChord(Integer.parseInt(args[0]));
                break;
            case 2:
                tab.insertChord(Integer.parseInt(args[0])).editNotes(formatToFrets(args[1]));
                break;
        }
        print(tab.toString(true));
    }

    /**
     * @EFFECTS: edits chord at given pos (first arg) to given frets (second arg)
     * @MODIFIES: this
     */
    void consoleEditChord(String[] args) {
        if (args.length < 2) {
            print("Please provide the position of the chord and the frets in the form X-X-X-...");
        } else {
            tab.editChord(Integer.parseInt(args[0]), formatToFrets(args[1]));
        }
        print(tab.toString(true));
    }

    /**
     * @EFFECTS: deletes chord at end of tab or at specified position (first arg)
     * @MODIFIES: this
     */
    void consoleDeleteChord(String[] args) {
        if (args.length == 0) {
            tab.removeChord(tab.getLength() - 1);
        } else {
            tab.removeChord(Integer.parseInt(args[0]));
        }
        print(tab.toString(true));
    }

    /**
     * @EFFECTS: copies chord at position (first arg) and paste at position (second arg)
     * @MODIFIES: this
     */
    void consoleCopyPaste(String[] args) {
        if (args.length == 0) {
            tab.removeChord(tab.getLength() - 1);
        } else {
            tab.removeChord(Integer.parseInt(args[0]));
        }
        print(tab.toString(true));
    }

    /**
     * @EFFECTS: prints general help instructions or for specified commands
     */
    // Suppressed as this method is just a list of commands for help
    @SuppressWarnings("methodlength")
    void help(String[] args) {
        if (args.length == 0) {
            print("Form for fret input: X-X-X-... 'e' for empty, 'x' for mute");
            print("For more additional info about command: help <command>");
            print("Available commands: (a)dd, (i)nsert, (e)dit, (d)elete, (c)opypaste");
        } else {
            switch (args[0]) {
                case "a":
                case "add":
                    print("Adds chord to end of tab: add [frets]");
                    break;
                case "i":
                case "insert":
                    print("Inserts chord at given position: insert <position> [frets]");
                    break;
                case "e":
                case "edit":
                    print("Edits chord at given position: edit <position> <frets>");
                    break;
                case "d":
                case "delete":
                    print("Deletes chord at end or given position: delete [position]");
                    break;
                case "cp":
                case "copypaste":
                    print("Copies and pastes chord at given positions: copypaste <position1> <position2>");
                    break;
                default:
                    print("Command not found");
            }
        }
    }

    /**
     * @EFFECTS: converts given string form arg into valid frets, otherwise NumberFormatException is thrown
     */
    private int[] formatToFrets(String string) {
        String[] strings = string.split("-");
        int[] frets = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            switch (strings[i]) {
                case "e":
                    frets[i] = Note.EMPTY;
                    break;
                case "x":
                    frets[i] = Note.MUTE;
                    break;
                default:
                    frets[i] = Integer.parseInt(strings[i]);
            }
        }
        return frets;
    }

    /**
     * @EFFECTS: handles exceptions arisen from user input with general behaviour
     */
    private void handleException(Exception e) {
        if (e instanceof IndexOutOfBoundsException) {
            print("Given position invalid, action failed");
        } else if (e instanceof NumberFormatException) {
            print("Given string when expected number, action failed");
        }
    }

    /**
     * @EFFECTS: just to simplify the print to console
     */
    private void print(Object obj) {
        System.out.println(obj);
    }

    /**
     * @EFFECTS: reads input and converts to lower case
     */
    private String readInput() {
        return input.nextLine().toLowerCase();
    }
}
