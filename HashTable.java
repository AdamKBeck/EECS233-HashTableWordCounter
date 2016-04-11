import java.util.ArrayList;

import javax.swing.text.StyleContext.SmallAttributeSet;

/**
 * This class contains a hash table using separate chaining to 
 * keep track of word counts in an input file.
 * 
 * @author Adam Beck
 */
public class HashTable {
    
    /* Each element in the array contains an arrayList of type node */
    public static ArrayList<Node>[] hashTable;
    
    /**
     * Default constructor
     */
    public HashTable(){
        /* A hash table of size 2, increasing by powers of 2 to avoid multiples of 31 */
        hashTable = new ArrayList[2]; //TODO: type safety warning
        
        /* Initialize every arraylist in the hashtable */
        for (int i = 0; i < hashTable.length; i++){
            hashTable[i] = new ArrayList<Node>();
        }
    }
    
    /**
     *  Set the hashTable to a specific size.
     */
    public HashTable(int size){
       hashTable = new ArrayList[size]; //TODO: type safety warning
       
       /* Initialize every arraylist in the hashtable */
       for (int i = 0; i < size; i++){
           hashTable[i] = new ArrayList<Node>();
       }
    }
    
    /**
     * Gets the size (meaning array length) of the hash table
     * @return The size of the table.
     */
    public int getTableSize(){
        return hashTable.length;
    }
    
    public void add(String word){
        /* Generate hashing value, using table size as a mod
         * This is part of the project description */
        int hashValue = (word.hashCode() % getTableSize());
        
        /* Take the absolute value in case this is negative,
         * this may result in collisions, but since we are using
         * separate chaining this is fine */
         if (hashValue < 0){
             hashValue *= -1;
         }
         
         /* If the word hasn't been added yet, it's distinct and frequency is 1 */
         if (hashTable[hashValue].size() == 0){
             hashTable[hashValue].add(new Node(word, 1));
         }
         
         boolean found = false;
         /* If the arrayList is not null, the word may or may not be in there.
          * Iterate through the arrayList, if the word is there, do frequency++.
          * If not, chain it (by adding) it to the end of your arrayList. */
         for (int i = 0; i < hashTable[hashValue].size(); i++){
             if (hashTable[hashValue].get(i).getWord().equals(word)){
                 hashTable[hashValue].get(i).incrementFrequency();
                 found = true;
             }
         }
         
         /* Chain to the end of the list since the word is distinct and not in the list.
          * Checks every time if expanding and rehashing needs to be done */
         if (found == false){
             hashTable[hashValue].add(new Node(word, 1));  
             this.checkToRehash(); // Check when a new word is inserted to rehash or not
         }
         
         
    }
    
    /**
     * Calls expandAndRehash() if the table needs to be rehased.
     */
    public void checkToRehash(){
        
        double filledCount = 0; // Counts how many buckets are filled.
        
        /* EXPLANATION:
         * 
         * I figured you should check if hashTable[i] == null, but since I 
         * Initialized every list in my array of buckets, I need to check
         * if the size of the list is >0, which checks if the bucket is empty or not.
         */
        for (int i = 0; i < this.getTableSize(); i++){
            if (hashTable[i].size() > 0){
                filledCount++;
            }
        }
        
        /* Expand table at if at least 75% of buckets are filled */
        if (filledCount / (double)this.getTableSize() >= .75){
             expandAndRehash(); // Expand and rehash the table.
            // See javadoc on expandAndRehash() for explanation of why I'm rehashing at 75% 
        }
    }
    
    /**
     * This method prints out the table in a way to view which words are in
     * each bucket. This was just for testing purposes and is 
     * not called in the final version of this project.
     */
    public void printTable(){      
        /* Loop through the every hashTable bucket, print out every chain */       
        for (int i = 0; i < this.getTableSize(); i++){
            System.out.println("------------bucket------------");
            for (int k = 0; k < hashTable[i].size(); k++){
                System.out.println(hashTable[i].get(k).getWord() + " " + hashTable[i].get(k).getFrequency());
            }
        }
    }
    
    /** 
     * Expands and rehahses the table.
     * 
     * This is done by pointing the old table to a newly expanded, rehashed table.
     * 
     * I chose to rehash at 75% because I believe it gives the table a good balance between
     * avoiding "clustering" of buckets as well as making sure the table doesn't rehash too often, 
     * which would happen if the rehashing took place at a lower value, such as 50%. Rehashing time
     * complexity is expensive, which is why I'm trying to avoid it as much as possible by picking 75%.
     * 
     */
    public void expandAndRehash(){
        
        /* This is helpful to see how many times this method is called in the console */
        // System.out.println("rehashing...");
        
        /* Save a copy of every node in the old table */
        ArrayList<Node> everyNode = new ArrayList<Node>();
        
        /* Iterate through old table and add all nodes to the table */
        for (int i = 0; i < this.getTableSize(); i++){
            for (int k = 0; k < hashTable[i].size(); k++){
                everyNode.add(hashTable[i].get(k));
            }
        }
        
        /* Double the table size to avoid multiples of 31 */
        ArrayList<Node>[] newTable = new ArrayList[this.getTableSize() * 2]; //TODO: type safety warning
        
        /* Initialize every list in the new table */
        for (int i = 0; i < newTable.length; i++){
            newTable[i] = new ArrayList<Node>();
        }
        
        /* Rehash every Node in the list to this new hash table */
        int hashValue = 0;
        for (int i = 0; i < everyNode.size(); i++){
            hashValue = everyNode.get(i).getWord().hashCode() % newTable.length;
            
            /* Ensure that the hashValue is positive */
            if (hashValue < 0){
                hashValue *= -1;
            }
      
            newTable[hashValue].add(everyNode.get(i));
        }
        
        /*
         * Now, the old table points to the newly rehashed table. The old table is now
         * therefore expanded and rehashed accordingly. 
         */       
         hashTable = newTable; 
    }
    
    /**
     *  Returns the total words in the hash table.
     * 
     * The project description is ambiguous, so
     * I'm assuming this means total words is the number of
     * distinct words in the hash table.
     * 
     * @return total words in the hash table.
     */
    public int totalWords(){
        
        int wordCount = 0;
        
        /* Counts every distinct word in the table */
        for (int i = 0; i < this.getTableSize(); i++){
            for (int k = 0; k < hashTable[i].size(); k++){
                wordCount++;
            }
        }
        
        return wordCount;
    }
    
    /**
     * Returns the average length of collisions in the hashTable.
     * 
     * @return The average length of collisions in the hashTable.
     */
    public double avgCollisionLength(){
        
        
        double sumOfCollisions = 0;
        
        for (int i = 0; i < this.getTableSize(); i++){
            sumOfCollisions += hashTable[i].size();
        }
        
        
        /* Average collision length */
        return sumOfCollisions / this.getTableSize();
    }
    
    /**
     * Returns a String containing the correct output format for this assignment.
     * @return a String containing the correct output format for this assignment.
     */
    public String output(){
        StringBuilder s = new StringBuilder();
        
        for (int i = 0; i < this.getTableSize(); i++){
            for (int k = 0; k < hashTable[i].size(); k++){
                s.append("(");
                s.append(hashTable[i].get(k).getWord());
                s.append(" ");
                s.append(hashTable[i].get(k).getFrequency());
                s.append(") ");
            }
        }
        
        return s.toString();       
    }
}
