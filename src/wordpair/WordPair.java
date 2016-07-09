/*
This is the main class that the program runs.
 */
package wordpair;

import java.util.Scanner;

public class WordPair {

    Control control = new Control();
    Scanner input = new Scanner(System.in);
    int menuChoice;
    boolean exit = false;

    public void displayMenu() {
        System.out.println("Menu: ");
        System.out.println("    1. Console input.");
        System.out.println("    2. File input.");
        System.out.println("    3. Load dictionary from file.");
        System.out.println("    4. Console output (Choose random, then roll for acceptance).");
        System.out.println("    5. Console output (Roll, choose based on probability wheel).");
        System.out.println("    6. Console output (Pure random choice from model).");
        System.out.println("    7. Console output (User's choice).");
        System.out.println("    8. Clean output.");
        System.out.println("    9. Write dictionary to file.");
        System.out.println("    10. Write output to file.");
        System.out.println("    11. Exit.");
    }

    public void getMenuChoice() {
        if (this.input.hasNextInt()) {
            this.menuChoice = this.input.nextInt();
        } else {
            input.next();
            System.out.println("\n** Please enter an integer. **\n");
        }
    }

    public void menuLoop() {
        while (!this.exit) {
            this.displayMenu();
            this.getMenuChoice();

            switch (this.menuChoice) {
                case 1:
                    System.out.println("Paste text into the console, be sure to have removed any newlines or paragraph breaks, then press ENTER.");
                    this.control.getLineFromConsole();
                    this.control.loadInputToModel();
                    this.control.postLoadInput();
                    break;
                case 2:
                    System.out.println("Enter the filename, then press ENTER.");
                    this.control.getLineFromConsole();
                    this.control.loadInputFromFile(this.control.input);
                    this.control.postLoadInput();
                    break;
                case 3:
                    System.out.println("Enter the filename, then press ENTER.");
                    this.control.getLineFromConsole();
                    this.control.loadDictionaryFile(this.control.input);
                    break;
                case 4:
                    if (!this.control.model.isModelEmpty()) {
                        System.out.println("Enter the character length of the output, then press ENTER.");
                        System.out.println(this.control.generateOutput(this.control.getIntFromConsole()));
                    } else {
                        System.out.print("\n* Character Pair dictionary is empty, please load input. *\n");
                    }
                    break;
                case 5:
                    if (!this.control.model.isModelEmpty()) {
                        System.out.println("Enter the character length of the output, then press ENTER.");
                        System.out.println(this.control.generateOutput2(this.control.getIntFromConsole()));
                    } else {
                        System.out.print("\n* Character Pair dictionary is empty, please load input. *\n");
                    }
                    break;
                case 6:
                    if (!this.control.model.isModelEmpty()) {
                        System.out.println("Enter the character length of the output, then press ENTER.");
                        System.out.println(this.control.generateOutput3(this.control.getIntFromConsole()));
                    } else {
                        System.out.print("\n* Character Pair dictionary is empty, please load input. *\n");
                    }
                    break;
                case 7:
                    if (!this.control.model.isModelEmpty()) {
                        System.out.println("Enter the character length of the output, then press ENTER.");
                        System.out.println(this.control.generateOutput4());
                    } else {
                        System.out.print("\n* Character Pair dictionary is empty, please load input. *\n");
                    }
                    break;
                case 8:
                    System.out.println(this.control.cleanOutput());
                    break;
                case 9:
                    System.out.println("Enter the filename, then press ENTER.");
                    this.control.getLineFromConsole();
                    this.control.outputDictionaryToFile(this.control.input);
                    break;
                case 10:
                    System.out.println("Enter the filename, then press ENTER.");
                    this.control.getLineFromConsole();
                    this.control.writeOutputToFile(this.control.input);
                    break;
                case 11:
                    System.out.println("EXITED.");
                    this.exit = true;
            }
        }
    }

    public static void main(String[] args) {
        WordPair wordpair = new WordPair();
        wordpair.menuLoop();
    }

}
