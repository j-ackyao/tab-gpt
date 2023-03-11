package ui;

import model.Note;
import model.Tab;
import persistence.Json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The console editor for tab
 */
public class ConsoleEditor {

    private final Scanner input;
    private Tab tab;

    /**
     * @EFFECTS: constructor for ConsoleEditor, prompts user to input tab tuning and calls run()
     */
    public ConsoleEditor() {
        this.input = new Scanner(System.in);
        init();
        print(tab);
        run();
    }

    /**
     * @EFFECTS: helper for constructor to handle user input and generate new Tab or open existing tab
     * @MODIFIES: this
     */
    private void init() {

        print("Specify name of tab to load or enter for new tab");
        while (true) {
            String tabName = input.nextLine();
            if ("".equals(tabName)) {
                newTab();
                return;
            }
            try {
                this.tab = Json.load(tabName);
                return;
            } catch (IOException io) {
                print("Tab not found try again");
            }
        }

    }

    /**
     * @EFFECTS: generates a new tab based on user input/specification
     * @MODIFIES: this
     */
    void newTab() {
        print("Give name to new tab");
        String name = input.nextLine();
        if ("".equals(name)) {
            print("No name given, default to 'tab'");
            name = "tab";
        }

        print("Input the tuning of the strings of tab in the form X-X-X-..., or enter to use standard tuning");
        // may include capitalized characters
        String tuning = input.nextLine();
        if ("".equals(tuning)) {
            print("Using standard tuning");
            tab = new Tab(name);
        } else if (tuning.split("-").length == 0) {
            print("Invalid tuning, using standard tuning");
            tab = new Tab(name);
        } else {
            tab = new Tab(name, tuning.split("-"));
        }
    }

    /**
     * @EFFECTS: handles all user inputs
     * @MODIFIES: this
     */
    // Suppressed, this handles all possible commands
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
                    case "bend":
                    case "b":
                        consoleBendChord(args);
                        break;
                    case "slide":
                    case "s":
                        consoleSlideChord(args);
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
                    case "save":
                        save();
                    case "":
                        print(tab.toString(true));
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
                print("Please input the position to insert chord [and chord in form X-X-X-...]");
                break;
            case 1:
                tab.insertChord(Integer.parseInt(args[0]));
                print(tab.toString(true));
                break;
            case 2:
                tab.insertChord(Integer.parseInt(args[0])).editNotes(formatToFrets(args[1]));
                print(tab.toString(true));
                break;
        }
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
            print(tab.toString(true));
        }
    }

    /**
     * @EFFECTS: bends chord to given args
     * @MODIFIES: this
     */
    void consoleBendChord(String[] args) {
        if (args.length < 2) {
            print("Please provide the position of the chord and type of bend (none|half|full)");
        } else {
            tab.bendChord(Integer.parseInt(args[0]), Note.Bend.getBend(args[1]));
            print(tab.toString(true));
        }
    }

    /**
     * @EFFECTS: bends chord to given args
     * @MODIFIES: this
     */
    void consoleSlideChord(String[] args) {
        if (args.length < 3) {
            print("Please provide the position of the chord, type of slide (none|up|down) and direction (to|from)");
        } else if (args[2].equals("to")) {
            tab.slideToChord(Integer.parseInt(args[0]), Note.Slide.getSlide(args[1]));
            print(tab.toString(true));
        } else if (args[2].equals("from")) {
            tab.slideFromChord(Integer.parseInt(args[0]), Note.Slide.getSlide(args[1]));
            print(tab.toString(true));
        } else {
            print("Please provide valid direction of slide");
        }
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
     * @EFFECTS: copies chord at position (first arg) and inserts at position (second arg)
     * @MODIFIES: this
     */
    void consoleCopyPaste(String[] args) {
        if (args.length < 2) {
            print("Please provide two positions to copy from and paste to");
        } else {
            tab.insertChord(Integer.parseInt(args[1]), tab.getChord(Integer.parseInt(args[0])));
            print(tab.toString(true));
        }
    }

    /**
     * @EFFECTS: saves tab using static method from Json
     */
    void save() {
        try {
            Json.save(tab);
            print("Save successful");
        } catch (FileNotFoundException fnf) {
            print("Unable to save to tab's name");
        }
    }

    /**
     * @EFFECTS: prints general help instructions or for specified commands
     */
    // Suppressed as this method is just a list of commands for help
    @SuppressWarnings("methodlength")
    void help(String[] args) {
        if (args.length == 0) {
            print("Form for fret input: X-X-X-... 'e' or '' (nothing) for empty, 'x' for mute");
            print("For more additional info about command: help <command>");
            print("Available commands: (a)dd, (i)nsert, (e)dit, (b)end, "
                    + "(s)lide, (d)elete, (c)opy(p)aste, (s)ave, exit");
            return;
        }
        switch (args[0]) {
            case "add":
            case "a":
                print("Adds chord to end of tab: add [frets]");
                break;
            case "insert":
            case "i":
                print("Inserts chord at given position: insert <position> [frets]");
                break;
            case "edit":
            case "e":
                print("Edits chord at given position: edit <position> <frets>");
                break;
            case "bend":
            case "b":
                print("Adds bend to chord at given position: bend <position> <none|half|full>");
                break;
            case "slide":
            case "s":
                print("Adds up or down slides to or from chord at given position:"
                        + " slide <position> <up|down> <to|from>");
                break;
            case "delete":
            case "d":
                print("Deletes chord at end or given position: delete [position]");
                break;
            case "copypaste":
            case "cp":
                print("Copies and pastes chord at given positions: copypaste <position1> <position2>");
                break;
            case "save":
                print("Saves tab to file as tab's name");
                break;
            case "exit":
                print("Exits program WITHOUT saving");
                break;
            default:
                print("Command not found");
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
                case "":
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

    // should reformat and handle exceptions respectively

    /**
     * @EFFECTS: handles exceptions arisen from user input with general behaviour
     */
    private void handleException(Exception e) {
        if (e instanceof IndexOutOfBoundsException) {
            print("Given position invalid/out of bounds, action failed");
        } else if (e instanceof NumberFormatException) {
            print("Given string/invalid number when expected integer, action failed");
        } else if (e instanceof IllegalArgumentException) {
            print("Given invalid argument");
        } else {
            e.printStackTrace();
            print("Unexpected exception encountered");
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
