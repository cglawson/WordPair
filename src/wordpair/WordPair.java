/*
This is the main class that the program runs.
 */
package wordpair;

import java.util.Scanner;

public class WordPair {

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
        System.out.println("    7. Clean output.");
        System.out.println("    8. Write dictionary to file.");
        System.out.println("    9. Write output to file.");
        System.out.println("    10. Exit.");
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

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    
                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:
                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:

                    this.exit = true;
            }
        }
    }

    public static void main(String[] args) {
        WordPair wordpair = new WordPair();
        wordpair.menuLoop();
    }

}
