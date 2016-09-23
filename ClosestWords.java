/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.util.ArrayList;
import java.util.List;

public class ClosestWords {
    List<String> closestWords = null;

    int closestDistance = -1;

    int partDist(String w1, String w2) {

        int tempLen = Math.min(w1.length(), w2.length());
        int equalLetters = 0;

        for (int i = 0; i < tempLen; i++) {
            if ((w1.charAt(i) == w2.charAt(i))) {
                equalLetters++;
            } else {
                break;
            }
        }

        if (equalLetters > 0) {
            w1 = w1.substring(equalLetters);
            w2 = w2.substring(equalLetters);
        }

        int w1len = w1.length() + 1;
        int w2len = w2.length() + 1;

        // the array of distances
        int[] cost = new int[w1len];
        int[] newCost = new int[w1len];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < w1len; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < w2len; j++) {
            // initial cost of skipping prefix in String s1
            newCost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < w1len; i++) {
                // matching current letters in both strings
                int match = (w1.charAt(i - 1) == w2.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newCost[i - 1] + 1;

                // keep minimum cost
                newCost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newCost;
            newCost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[w1len - 1];
    }

//    int Distance(String w1, String w2) {
//        return partDist(w1, w2);
//    }

    public ClosestWords(String w, List<String> wordList) {

        for (String s : wordList) {

            if (closestDistance != -1 && Math.abs(s.length() - w.length()) > closestDistance)
                continue;

            int dist = partDist(w, s);
            //System.out.println("d(" + w + "," + s + ")=" + dist);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new ArrayList<String>();
                closestWords.add(s);
            } else if (dist == closestDistance)
                closestWords.add(s);
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
