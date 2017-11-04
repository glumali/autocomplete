/******************************************************************************
 *  Name:    Greg Umali
 * 
 *  Description:  Searches for the first or last index of a specified prefix
 *                within a given term array.
 * 
 ******************************************************************************/


import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;

public class BinarySearchDeluxe {

    // Returns the index of the first key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {
        
        if (a.length == 0) {
            return -1; 
        }
        
        int low = 0;
        int high = a.length-1;
        int mid = low + (high - low) / 2;
        int compare = comparator.compare(a[mid], key);
        
        // continues until low is no longer less than high (signals that the 
        // bounds of the unscanned area only encompasses one or no elements)
        while (low < high) {
            // if there is a hit, adjust the upper bound, keep scanning
            if (compare == 0) {
                high = mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            
            mid = low + (high - low) / 2;
            compare = comparator.compare(a[mid], key);
        }
        
        // after breaking out of the loop, either we found the right element
        // or we didn't find any at all
        if (compare == 0) {
            return mid;
        } else {
            return -1;
        }
      
    }

    // Returns the index of the last key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, 
                                        Comparator<Key> comparator) {
        if (a.length == 0) {
            return -1; 
        }
        
        int low = 0;
        int high = a.length - 1;
        int mid = low + (high - low) / 2;
        // will track the "best matched" element skipped by low = mid+1
        int found = -1;
        int compare;

        while (low <= high) {
            // compares the value pointed to by mid and the key
            compare = comparator.compare(a[mid], key);
            
            if (compare < 0) {
                low = mid + 1;
            } else if (compare > 0) {
                high = mid - 1;
            } else {
                // if we have a hit, adjust low, but it might skip the element
                // that caused the initial hit (thus the need for found)
                low = mid + 1;
                // update the "best match" element
                found = mid;
            }
            mid = low + (high - low) / 2;
        }

        return found;
        /*
        if (compare == 0) {
            return mid;
        } else {
            return -1;
        }
        */
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
            
            String targetString = "ja";
            // sort based on length of target string?
            Comparator<Term> prefix = Term.byPrefixOrder(targetString.length());
            Arrays.sort(terms, prefix);
            
            Term target = new Term(targetString, 0);
            
            int index = BinarySearchDeluxe.firstIndexOf(terms, target, prefix);
            
            System.out.println("first index of " + targetString + " = " + index);
            
            Term target2 = new Term(targetString, 0);
            int index2 = BinarySearchDeluxe.lastIndexOf(terms, target2, prefix);
            System.out.println("last index of " + targetString + " = " + index2);
            
            System.out.println("by prefix order");
            for (Term temp : terms) {
                
                System.out.println(temp);
            }
            
            /* System.out.println("\nsorting by reverse weight");
            Comparator<Term> reverse = Term.byReverseWeightOrder();
            Arrays.sort(terms, reverse);
            for (Term temp : terms) {
                
                System.out.println(temp);
            }
            */
    }
}