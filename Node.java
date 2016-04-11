/**
 * A node in the hash table contains the word and its frequency in the input file.
 * @author Adam Beck
 */
public class Node {
    
    /* A distinct word in the input file */
    private String word;
    
    /* The frequency of a distinct word */
    private int frequency;

    public Node(){
        // default, I guess set things to null and 0
        word = null;
        frequency = 0;
    }
    
    /**
     * Constructs a Node object
     * 
     * @param word A distinct word in the input file
     * @param frequency The number of occurences of the word in the input file.
     */
    public Node(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }
    
    /**
     * Gets the distinct word in the input file
     * @return The word
     */
    public String getWord(){
        return word;
    }
    
    /**
     * Gets the frequency of a distinct word in the input file
     * @return The frequency
     */
    public int getFrequency(){
        return frequency;
    }
    
    /**
     * Sets the frequency 
     * @param frequency
     */
    public void setFrequency(int frequency){
        this.frequency = frequency; 
    }
    
    /**
     * Increments the frequency of the word in this Node
     */
    public void incrementFrequency(){
        setFrequency(getFrequency() + 1);
    }
}
