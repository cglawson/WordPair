/*
This class is the base unit that the Model unit uses to store data about
SourceDestination word pairs, the total number of occurences encountered,
and the calculated probability of a particular SourceDestination verses all
other destination choices for a Source.
 */
package wordpair;

public class SourceDestination implements Comparable<SourceDestination> {

    private final String source;
    private final String destination;
    private int occurences = 1;
    private double probability;

    public SourceDestination(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public SourceDestination(String source, String destination, int occurences) {
        this.source = source;
        this.destination = destination;
        this.occurences = occurences;
    }

    public void calculateProbability(int numSources) {
        /*
        Probability is calculated from how many times the particular 
        SourceDestination has occured versus the total number of occurences
        of a particular source String.
        
        For instance, source String A has two SourceDestinations: (A,B) which
        has occurences of 2, and (A,C) which has occurences of 1. The source
        A has total occurences of 3.  Therefore (A,B) has a probability of .66
        and (A,C) has a probability of .33.
         */
        this.probability = (double) this.occurences / (double) numSources;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getSource() {
        return this.source;
    }

    public int getOccurences() {
        return this.occurences;
    }

    public double getProbability() {
        return this.probability;
    }

    public void iterateOccurence() {
        this.occurences++;
    }
    
    public boolean isEqualTo(SourceDestination compare){
        boolean result = false;
        
        if(this.source.equals(compare.getSource()) && this.destination.equals(compare.getDestination())){
            result = true;
        }
        
        return result;
    }

    @Override
    public String toString() {
        //Converting String to int so that spaces are not lost in translation
        String result = this.getSource() + " " + this.getDestination() + " " + this.getOccurences() + " " + this.probability;

        return result;
    }

    @Override
    public int compareTo(SourceDestination compare) {
        //int score = (int) this.getSource() * 1000 + this.getDestination();
        //int compareScore = (int) compare.getSource() * 1000 + (int) compare.getDestination();
        //return score - compareScore;
        
        int sources = this.getSource().compareTo(compare.getSource()) * 1000;
        int destinations = this.getDestination().compareTo(compare.getDestination());
        
        return sources + destinations;
    }
}
