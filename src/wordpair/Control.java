/*
This class manipulates the Model and uses it to generate results.
 */
package wordpair;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control {

    Model model = new Model();
    Scanner console = new Scanner(System.in);
    String input = new String();
    String output = new String();

    public String cleanOutput() {
        if (!this.output.isEmpty()) {
            int count = 2;
            do {
                this.output = this.output.replaceAll("[^a-zA-Z\\.\\s\\,!?']", " "); //Remove any 'weird' punctuation
                this.output = this.output.replaceAll("(\\s[.,!?]\\s)", " "); //Remove lone punctuation  
                this.output = this.output.replaceAll("\\s+", " "); //Remove double or more spaces
            } while (count-- > 0);

            //Capitalize the beginning of a sentence
            for (int index = 0; index < this.output.length() - 2; index++) {
                if ((this.output.charAt(index) == '.' || this.output.charAt(index) == '!' || this.output.charAt(index) == '?') && this.output.charAt(index + 1) == ' ') {
                    this.output = this.output.substring(0, index + 1) + ' ' + Character.toUpperCase(this.output.charAt(index + 2)) + this.output.substring(index + 3);
                }
            }

            this.output = Character.toUpperCase(this.output.charAt(0)) + this.output.substring(1);

            if (Character.isAlphabetic(this.output.charAt(this.output.length() - 1))) {
                this.output = this.output + '.';
            }
        }
        return this.output;
    }

    public String generateOutput(int outputLength) {
        output = "";
        String currSource = this.model.getRandomSourceDestination().getSource(); //Start with a random
        ArrayList<SourceDestination> destinationsPossibleForSource;
        Random rand = new Random();

        while (output.length() < outputLength) { //String building loop
            output = output + " " + currSource;
            destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);

            while (destinationsPossibleForSource.isEmpty()) { //If a source has no destinations, pick random
                currSource = this.model.getRandomSourceDestination().getSource();
                destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);
            }

            boolean picked = false;
            while (!picked) { //Choose a random destination from the list, roll against its probability to accept
                SourceDestination candidate = destinationsPossibleForSource.get(rand.nextInt(destinationsPossibleForSource.size()));
                double roll = rand.nextDouble();

                if (roll <= candidate.getProbability()) {
                    picked = true;
                    currSource = candidate.getDestination();
                }
            }
        }

        return output;
    }

    public String generateOutput2(int outputLength) {
        output = "";
        String currSource = this.model.getRandomSourceDestination().getSource(); //Start with random
        ArrayList<SourceDestination> destinationsPossibleForSource;
        Random rand = new Random();

        while (output.length() < outputLength) { //String building loop
            output = output + " " + currSource; //Add onto string

            destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);

            while (destinationsPossibleForSource.isEmpty()) { //If list is empty choose random
                currSource = this.model.getRandomSourceDestination().getSource();
                destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);
            }

            boolean picked = false;
            while (!picked) {
                double roll = rand.nextDouble(); //Roll for a double
                double cumulativeProbability = 0.0;

                for (int x = 0; x < destinationsPossibleForSource.size() && !picked; x++) { //Find the destination that matches the roll
                    if (roll > cumulativeProbability && roll < (cumulativeProbability + destinationsPossibleForSource.get(x).getProbability())) {
                        picked = true;
                        currSource = destinationsPossibleForSource.get(x).getDestination();
                    }
                    cumulativeProbability += destinationsPossibleForSource.get(x).getProbability();
                }
            }
        }

        return output;
    }

    public String generateOutput3(int outputLength) { //Pure random, does not take into account occurences or calculated probability
        output = "";
        String currSource = this.model.getRandomSourceDestination().getSource(); //Start with random
        ArrayList<SourceDestination> destinationsPossibleForSource;
        Random rand = new Random();

        while (output.length() < outputLength) { //String building loop
            output = output + " " + currSource; //Add onto string

            destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);

            while (destinationsPossibleForSource.isEmpty()) { //If list is empty choose random
                currSource = this.model.getRandomSourceDestination().getSource();
                destinationsPossibleForSource = this.model.listDestinationsOfSource(currSource);
            }

            currSource = destinationsPossibleForSource.get(rand.nextInt(destinationsPossibleForSource.size())).getDestination();
        }

        return output;
    }

    public void getLineFromConsole() {
        this.input = this.console.nextLine();
    }

    public int getIntFromConsole() {
        int numChar = 0;
        if (this.console.hasNextInt()) {
            numChar = Math.abs(Integer.parseInt(this.console.nextLine()));
        } else {
            System.out.println("\n** Please enter a positive integer. **\n");
        }
        return numChar;
    }

    public void loadDictionaryFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(Paths.get(fileName));

            while (fileScanner.hasNext()) {
                SourceDestination sourceDestination = new SourceDestination(fileScanner.next(), fileScanner.next(), fileScanner.nextInt());

                this.model.addSourceDestination(sourceDestination);
            }

            this.model.calculateProbabilities();
        } catch (IOException ex) {
            System.out.print("\n** No such file. **\n");
        }
    }

    public void loadInputFromFile(String fileName) {
        input = "";

        try {
            Scanner fileScanner = new Scanner(Paths.get(fileName));

            while (fileScanner.hasNextLine()) {
                this.input = fileScanner.nextLine();
                this.loadInputToModel();
            }

            this.model.sortSourceDestinations();
            this.model.removeDuplicates();
            //this.model.applyThresholdToDictionary(2);
            this.model.calculateProbabilities();
            this.model.indexDictionary();
            this.model.printSourceDestinations();
        } catch (IOException ex) {
            System.out.print("\n** No such file. **\n");
        }
    }

    public void loadInputToModel() {
        Scanner in = new Scanner(this.input);
        String previous;
        if (in.hasNext()) {
            String current = in.next();

            while (in.hasNext()) {
                previous = current;
                current = in.next();

                this.model.addSourceDestination(previous, current);
            }
        }
    }

    public void outputDictionaryToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {

            for (int x = 0; x < this.model.getNumberOfSourceDestinations(); x++) {
                writer.append(this.model.getSourceDestination(x).toString());
                writer.newLine();
            }

            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeOutputToFile(String fileName) {
        if (!this.output.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"))) {

                writer.append(this.output);

                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.print("\n* Please generate output first. *\n");
        }
    }
}
