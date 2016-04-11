import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Program #3 - Hash Tables
 * @author Adam Beck
 * 
 * Pre-requisites:
 * Any derivative of words are distinct.
 * Words are defined as string of character between two delimiting characters.
 * split() built in method is allowed.
 * "Father" and "father" is one word, use toLowerCase() to help with this issue.
 */
public class FileWordCounter {
    
    /**
     * Reads an input text file and writes to an output file all the words
     * encountered in the document and their frequencies.
     * 
     * @param input_file The input file to scan
     * @param output_file The output file containing words and their frequencies
     * @return A String containing total words, hash table size, and 
     * average collision length. An error is returned if necessary.
     * @throws IOException 
     */
    public static String wordCount(String input_file, String output_file) throws IOException{
        
        /* Scan the input file and create the hash table*/
        BufferedReader br;
        try{
            File fileName = new File(input_file);
            br = new BufferedReader(new FileReader(fileName));
        }catch(Exception e){
            return "File reading error";
        }
        
        HashTable hashTable = new HashTable();
        
        /* If scanFile fails, return an error */
        if (!scanFile(br, hashTable)){
            return "Input file error: no input was found";
        }
        
        
        /* This is helpful to visualize my final structure of the hashTable,
         * but this is not needed in the context of this project. */
         //hashTable.printTable();
        
        /* Otherwise, return the formatted String */
        int wordTotal = hashTable.totalWords(); 
        int tableSize = hashTable.getTableSize();
        double avgCollisionLength = hashTable.avgCollisionLength();
        
        
        String returnString = new String();
        
        returnString += "OK; Total words " + wordTotal;
        returnString += ", Hash table size: " + tableSize;
        returnString += ", Average length of collision lists: " + avgCollisionLength;
        
        
        /* write to output file */
        PrintWriter writer = new PrintWriter(output_file);
        String outputString = hashTable.output();
        writer.write(outputString);
        writer.close();
        
        return returnString;
        
    }
    
    
    /**
     * Scans the input file
     * 
     * @param br The buffered reader for the input file.
     * @return Success or failure for scanning the file.
     * @throws IOException 
     */
    public static boolean scanFile(BufferedReader br, HashTable hashTable) throws IOException{
        
        String currentLine;
        boolean fileNotEmpty = false;
        
        /* If there's no input in the input file, return an error */
        while ( (currentLine = br.readLine()) != null){
            fileNotEmpty = true; // if there at least was something in the file, the file exists
            currentLine = currentLine.toLowerCase();
            
            // information on delimiters and regular expressions:
            // http://pages.cs.wisc.edu/~hasti/cs302/examples/Parsing/parseString.html
            /* Split according to project description */
            String[] splitLine = currentLine.split("[^a-zA-Z]");
            
            /* Search through the line, adds words to the hash table */    
            for (int i = 0; i < splitLine.length; i++){
                if (splitLine[i].length() == 0){
                    // ignore this case, this could reasonably happen
                }
                
                else{
                    hashTable.add(splitLine[i]); // add each "word" to the hash Table.                                 
                }
            }    
        }
       
        /* If the file was empty return false, this will be true if
         * the file contained information and the program ran. */
        return fileNotEmpty;      
    }
    
    /**
     * I'm assuming this program is run through the command line
     * 
     * @param args Two arguments, of input file name and output file name.
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException{

        /* Handle argument errors */
        if (args.length == 0){
            System.out.println("Please use two arguments, input filename and output filename");
        }
        else if (args.length != 2){
            System.out.println("Argument mismatch, you used " + args.length + " argument(s)");
        }
        
        /* Otherwise, run the program */
        else{
            String result = wordCount(args[0], args[1]);
            System.out.println(result);
        }       
        
    }
    
    
}
