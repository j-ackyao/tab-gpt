package ui;

import model.Note;
import model.Tab;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The console editor for a tab
 */
public class ConsoleEditor {

    private Tab tab;
    Scanner input;

    public ConsoleEditor() {
        input = new Scanner(System.in);
        init();
        run();
    }

    private void init() {
        print("Input the tuning of the strings of tab in the proper form, or enter to use standard tuning");
        String tuning = readInput();
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

    @SuppressWarnings("methodlength")
    void run() {
        boolean exit = false;
        while (!exit) {
            String[] cmd = readInput().split(" ");
            String[] args = Arrays.copyOfRange(cmd, 1, cmd.length);
            switch (cmd[0]) {
                case "exit":
                    exit = true;
                    break;
                case "add":
                case "a":
                    addChord(args);
                    print(tab.toString(true));
                    break;
                case "insert":
                case "i":
                    insertChord(args);
                    print(tab.toString(true));
                    break;
                case "edit":
                case "e":
                    editChord(args);
                    print(tab.toString(true));
                    break;
                case "delete":
                case "d":
                    deleteChord(args);
                    print(tab.toString(true));
                    break;
                case "help":
                case "h":
                    help(args);
                    break;
                default:
                    print(tab);
            }
        }
    }
    // todo add cp (copy paste) cr (copy replace) xp (cut paste) xr (cut replace)

    void addChord(String[] args) {
        if (args.length == 0) {
            tab.addChord();
        } else {
            tab.addChord().editNotes(formatToFrets(args[0]));
        }
    }

    //todo index out of bounds
    void insertChord(String[] args) {
        switch (args.length) {
            case 0:
                print("Please input the position to insert chord (and chord in proper form");
                break;
            case 1:
                tab.insertChord(Integer.parseInt(args[0]));
                break;
            case 2:
                tab.insertChord(Integer.parseInt(args[0])).editNotes(formatToFrets(args[1]));
                break;
        }
    }

    // todo index out of bounds
    void editChord(String[] args) {
        if (args.length < 2) {
            print("Please provide the position of the chord and the frets in proper form");
        } else {
            tab.editChord(Integer.parseInt(args[0]), formatToFrets(args[1]));
        }
    }

    void deleteChord(String[] args) {
        if (args.length == 0) {
            tab.removeChord(tab.getLength() - 1);
        } else {
            tab.removeChord(Integer.parseInt(args[0]));
        }
    }

    void help(String[] args) {
        if (args.length == 0) {
            print("Proper form: X-X-X-.... 'e' for empty, 'x' for mute");
            print("For more additional info about command: help <command>");
            print("Available commands: (a)dd, (i)nsert, (e)dit, ");
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
                    print("Edits chord at given position: edit <position> [frets]");
                    break;
                default:
                    print("Command not found");
            }
        }
    }

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
                    try {
                        frets[i] = Integer.parseInt(strings[i]);
                    } catch (NumberFormatException e) {
                        frets[i] = Note.EMPTY;
                    }
            }
        }
        return frets;
    }

    // saves from typing print
    private void print(Object obj) {
        System.out.println(obj);
    }

    private String readInput() {
        return input.nextLine().toLowerCase();
    }
}
