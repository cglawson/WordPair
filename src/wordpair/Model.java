/*
This class stores and manipulates the dictionary of SourceDestination pairs.
 */
package wordpair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Model {

    private ArrayList<SourceDestination> sourceDestinations = new ArrayList<>();
    Map<Character,Integer> indicies = new HashMap<>(500);
    
    public void addSourceDestination(String source, String destination) {
        this.sourceDestinations.add(new SourceDestination(source, destination));
    }

    public void removeDuplicates() {
        ArrayList<SourceDestination> tmp = new ArrayList<>();

        for (int x = 0; x < sourceDestinations.size(); x++) {
            if ((!tmp.isEmpty()) && (tmp.get(tmp.size() - 1).isEqualTo(sourceDestinations.get(x)))) {
                tmp.get(tmp.size() - 1).iterateOccurence();
            } else {
                tmp.add(sourceDestinations.get(x));
            }

        }

        this.sourceDestinations = tmp;
    }

    public void addSourceDestination(SourceDestination sourceDestination) {
        this.sourceDestinations.add(sourceDestination);
    }

    public void calculateProbabilities() {
        //Get all the unique source Stringacters
        ArrayList<String> sources = this.listAllUniqueSources();

        for (int x = 0; x < sources.size(); x++) { //Iterate through each source
            ArrayList<SourceDestination> destinationResults = this.listDestinationsOfSource(sources.get(x));
            //Probability is based on total of occurences for all destinations of a particular source
            int totalOccurences = this.totalOccurrencesForSource(sources.get(x));

            for (int y = 0; y < destinationResults.size(); y++) { //Calculate for each SourceDestination
                destinationResults.get(y).calculateProbability(totalOccurences);
            }
        }
    }

    public SourceDestination getSourceDestination(int index) {
        SourceDestination result = this.sourceDestinations.get(index);
        return result;
    }

    public int getNumberOfSourceDestinations() {
        int number = this.sourceDestinations.size();
        return number;
    }

    public SourceDestination getRandomSourceDestination() {
        /*
        Used in beginning the generation of output, and also when there are no
        destinations for a particular source.
         */
        Random rand = new Random();
        return this.sourceDestinations.get(rand.nextInt(sourceDestinations.size()));
    }

    public boolean isModelEmpty() {
        return this.sourceDestinations.isEmpty();
    }

    public ArrayList listAllUniqueSources() {
        /*
        Used to calculate probability for each unique source.
         */
        ArrayList<String> sources = new ArrayList<>();

        for (int x = 0; x < this.sourceDestinations.size(); x++) {
            if (x > 0) {
                if (!this.sourceDestinations.get(x).getSource().equals(sources.get(sources.size()-1))) {
                    sources.add(this.sourceDestinations.get(x).getSource());
                }
            } else {
                sources.add(this.sourceDestinations.get(x).getSource());
            }
        }

        return sources;
    }

    public ArrayList listDestinationsOfSource(String source) {
        /*
        Used to list the different possibilities for a particular source.
         */
        ArrayList<SourceDestination> destinationsOfSource = new ArrayList<>();

        for (int x = 0; x < sourceDestinations.size(); x++) {
            if (source.equals(this.sourceDestinations.get(x).getSource())) {
                destinationsOfSource.add(this.sourceDestinations.get(x));
            }
        }

        return destinationsOfSource;
    }

    public void printSourceDestinations() {
        System.out.println();

        this.sortSourceDestinations();

        for (int x = 0; x < this.sourceDestinations.size(); x++) {
            System.out.println(this.sourceDestinations.get(x).toString());
        }

        System.out.println();
    }

    public void sortSourceDestinations() {
        Collections.sort(this.sourceDestinations);
    }

    public int sourceDestinationIndex(String source, String destination) {
        int index = -1; //If -1, does not exist

        for (int x = 0; x < this.sourceDestinations.size() && index == -1; x++) {
            if (source.equals(this.sourceDestinations.get(x).getSource()) && destination.equals(this.sourceDestinations.get(x).getDestination())) {
                index = x;
            }
        }

        return index;
    }

    public int totalOccurrencesForSource(String source) {
        int totalOccurences = 0;
        ArrayList<SourceDestination> sources = this.listDestinationsOfSource(source);

        for (int x = 0; x < sources.size(); x++) {
            totalOccurences += sources.get(x).getOccurences();
        }

        return totalOccurences;
    }

}
