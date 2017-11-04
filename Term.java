/******************************************************************************
  *  Name:    Greg Umali
  * 
  *  Description:  A datatype that models an autocomplete term with a specified
  *                query and weight. 
  * 
  ******************************************************************************/

import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;

public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;
    
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) {
            throw new NullPointerException();
        }
        
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        
        this.query = query;
        this.weight = weight;
    }
    
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ByReverse();
    }
    
    private static class ByReverse implements Comparator<Term> {
        public int compare(Term first, Term second) {
            return (int) (second.weight - first.weight);
        }
    }
    
    // Compares the two terms in lexicographic order but using only the 
    // first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) { 
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        
        return new ByPrefix(r);  
    }
    
    private static class ByPrefix implements Comparator<Term> {
        private int prefixLength;
        
        public ByPrefix(int r) {
            prefixLength = r;
        }
        
        public int compare(Term first, Term second) {
            
            int l1 = first.query.length();
            int l2 = second.query.length();
            
            // the "effective R" value will be used to limit how far the loop
            // runs. It is equal to either the shorter length of the two strings
            // or the given r, if it is shorter than both strings.
            int effR = Math.min(prefixLength, Math.min(l1, l2));
            
            char char1; // character from first.query
            char char2; // character from second.query
            
            for (int idx = 0; idx < effR; idx++) {
                char1 = first.query.charAt(idx);
                char2 = second.query.charAt(idx);
                
                // found the first difference, return accordingly
                if (char1 < char2) return -1;
                if (char2 < char1) return 1;
                
            }
            
            // characters match, but reached the end 
            // of one string, but not the other
            if ((effR < prefixLength) && (l1 != l2)) {
                // the shorter string is the smaller one
                if (l1 < l2) return -1;
                if (l2 < l1) return 1;
            }
            // go through all characters, all equal
            return 0;
        }
    }
    
    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }
    
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        Term test = new Term("greg", 100);
        Term test2 = new Term("gregory", 1);
        Term empty = new Term("", 0);
        
        Term[] array = {test, test2, empty};
        
        System.out.println("Print array");
        
        for (Term temp : array) {
            
            System.out.println(temp);
        }
        
        Comparator<Term> reverse = Term.byReverseWeightOrder();
        
        
        Arrays.sort(array, reverse);
        
        System.out.println("Print sorted array");
        
        for (Term temp : array) {
            
            System.out.println(temp);
        }
        
        
        System.out.println("greg compared to gregory = " + 
                           reverse.compare(test, test2));
        
        
        int num = StdIn.readInt();
        Term[] terms = new Term[num];
        int counter = 0;
        
        
        while (!StdIn.isEmpty()) {
            int weight = StdIn.readInt();
            String q = StdIn.readString();
            terms[counter] = new Term(q, weight);
            counter++; 
        }
        
        System.out.println("Print input text (normal)");
        
        for (Term temp : terms) {
            System.out.println(temp);
        }
        
        
        Arrays.sort(terms);
        System.out.println("Print sorted by lexicographic");
        for (Term temp : terms) {
            System.out.println(temp);
        }
        
        Arrays.sort(terms, Term.byReverseWeightOrder());
        System.out.println("Print sorted by reverse");
        for (Term temp : terms) {
            System.out.println(temp);
        }
        
        Arrays.sort(terms, Term.byPrefixOrder(2));
        System.out.println("Print sorted by prefix (2)");
        for (Term temp : terms) {
            System.out.println(temp);
        }
        
        Arrays.sort(terms, Term.byPrefixOrder(0));
        System.out.println("Print sorted by prefix (0)");
        for (Term temp : terms) {
            System.out.println(temp);
        }
        
        
        int testPrefComp = Term.byPrefixOrder(3).
            compare((new Term("jog", 5)), (new Term("jo", 6)));
        System.out.println("Comparing jo to ja: " + testPrefComp);
    }
}