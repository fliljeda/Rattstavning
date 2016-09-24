/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.util.ArrayList;
import java.util.List;

public class ClosestWords {
    private List<String> closestWords = null;
    private int[] wordLetters = new int[256];

    int closestDistance = -1;

    int partDist(String w1, String w2) {

        int w1len = w1.length() + 1;
        int w2len = w2.length() + 1;

        // the array of distances
        int[] cost = new int[w1len];
        int[] newCost = new int[w1len];
        int[] swap;
        int cost_replace;
        int cost_insert;
        int cost_delete;

        // initial cost for the first word (base case)
        // used as 'previous row' in first case
        for (int i = 0; i < w1len; i++) cost[i] = i;

        // dynamically computing the array of distances
        // using two arrays one of which holds previous row
        // and the other holds the current row
        for (int j = 1; j < w2len; j++) {
            // initial cost for the second word (base case)
            newCost[0] = j;

            // filling the current row with the help of
            // base case and previous row
            for (int i = 1; i < w1len; i++) {

                // matching current letters in both strings
                if((w1.charAt(i-1) == w2.charAt(j-1))) {
                    newCost[i] = cost[i - 1];
                } else {
                    cost_replace = cost[i - 1] + 1;
                    cost_insert = cost[i] + 1;
                    cost_delete = newCost[i - 1] + 1;
                    newCost[i] = Math.min(Math.min(cost_insert, cost_delete),cost_replace);
                }

                // computing cost for each transformation
                //cost_replace = cost[i - 1] + match;
                // keep minimum cost
            }

            // swap cost/newcost arrays
            swap = cost;
            cost = newCost;
            newCost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[w1len - 1];
    }

    public ClosestWords(String w, List<String> wordList) {

        int wLen = w.length();
        for (int i = 0; i < wLen; i++) {
            wordLetters[w.charAt(i)] = 1;
        }

        for (String s : wordList) {

            if (closestDistance != -1 && (Math.abs(s.length() - w.length()) > closestDistance
                    || getAmountOfDiffChars(s) > closestDistance))
                continue;

            int dist = partDist(w, s);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new ArrayList<String>();
                closestWords.add(s);
            } else if (dist == closestDistance)
                closestWords.add(s);
        }
    }

    int getAmountOfDiffChars(String s) {

        int counter = 0;
        int sLen = s.length();
        for (int i = 0; i < sLen; i++) {
            if(wordLetters[(int) s.charAt(i)] == 0)
                counter++;
        }
        return counter;
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
