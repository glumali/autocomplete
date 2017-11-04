/******************************************************************************
 *  Name:    Greg Umali
 * 
 *  Description:  Datatype that takes an array of term objects and allows the
 *                client to retrieve an array of terms that include a "prefix"
 *                in reverse weight order.
 * 
 ******************************************************************************/

import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {
    
    private final Term[] finalTerms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        
        if (terms == null) {
             throw new NullPointerException();
        }
        
        // copies the array passed in (client-owned) into a new array 
        finalTerms = new Term[terms.length];
        
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new NullPointerException();
            }
            finalTerms[i] = terms[i];
        }
        
        // the array of terms is then sorted so it can be searched
        Arrays.sort(finalTerms); 
    }

    // Returns all terms that start with a prefix, by descending weight.
    public Term[] allMatches(String prefix) {
        // makes a dummy term from the prefix
        Term pref = new Term(prefix, 0);
        
        // used for the two different ways the array will be sorted
        Comparator<Term> prefComp = Term.byPrefixOrder(prefix.length());
        Comparator<Term> reverse = Term.byReverseWeightOrder();
        
        int first = BinarySearchDeluxe.firstIndexOf(finalTerms, pref, prefComp);
        int last = BinarySearchDeluxe.lastIndexOf(finalTerms, pref, prefComp);
        
        // element was not found, return empty array
        if (first == -1 || last == -1) {
            return new Term[0];
        }
        
        // elements were found, make a new array that will be returned
        Term[] toReturn = new Term[last - first + 1];
        
        // counter tracks the index of the array that we will return
        int counter = 0;
        // iterates over all matched elements
        for (int i = first; i <= last; i++) {
            toReturn[counter++] = finalTerms[i]; 
        }
        
        // sorts array to be returned by reverse order of weight
        Arrays.sort(toReturn, reverse);
        
        return toReturn;
        
        
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        // makes a dummy term from the prefix
        Term pref = new Term(prefix, 0);
        
        // searches for indexes of first and last hit
        Comparator<Term> prefComp = Term.byPrefixOrder(prefix.length());
        int first = BinarySearchDeluxe.firstIndexOf(finalTerms, pref, prefComp);
        int last = BinarySearchDeluxe.lastIndexOf(finalTerms, pref, prefComp);
        
        // if not found, returns 0
        if (first == -1 || last == -1) {
            return 0;
        }
        
        return last - first + 1;
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        int num = StdIn.readInt();
        Term[] terms = new Term[num];
        int counter = 0;
        while (!StdIn.isEmpty()) {
            int weight = StdIn.readInt();
            StdIn.readChar();
            String q = StdIn.readLine();
            terms[counter] = new Term(q, weight);
            counter++; 
        }
        
        String testString = "N";
        
        Autocomplete test = new Autocomplete(terms);
        
        Term[] testArray = test.allMatches(testString);
        
        /*
        StdOut.println("Printing original array...");
        for (Term temp : terms) {
            
            System.out.println(temp);
        }
        */
        
        StdOut.println("Printing matches...");
        for (Term temp2 : testArray) {
            
            System.out.println(temp2);
        }   
    }
}