/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.util.ArrayList;
import java.util.List;

public class ClosestWords {

    private List<String> closestWords = null;
    private int[] wordLetters = new int[256];

    int closestDistance = Integer.MAX_VALUE;

    int partDist(String w1, String w2) {

        int w1len = w1.length() + 1;
        int w2len = w2.length() + 1;

        int[] cost = new int[w1len];
        int[] newCost = new int[w1len];
        int[] temp;
        int cost_modify;
        int cost_add;
        int cost_delete;

        for (int i = 0; i < w1len; i++) // initiate column
            cost[i] = i;
        for (int j = 1; j < w2len; j++) {
            newCost[0] = j; // initiate row

            /*
                calculate distance
             */
            for (int i = 1; i < w1len; i++) {
                if ((w1.charAt(i - 1) == w2.charAt(j - 1))) { // if two letters are equal then there no cost
                    newCost[i] = cost[i - 1];
                } else { // if the two letters are unequal there is a cost
                    cost_modify = cost[i - 1] + 1;
                    cost_add = cost[i] + 1;
                    cost_delete = newCost[i - 1] + 1;
                    newCost[i] = Math.min(Math.min(cost_add, cost_delete), cost_modify);
                }
            }

            // swap cost and newCost
            temp = cost;
            cost = newCost;
            newCost = temp;
        }
        return cost[w1len - 1];
    }

    public ClosestWords(String w, List<String> wordList) {

        int wLen = w.length();
        for (int i = 0; i < wLen; i++) {
            wordLetters[w.charAt(i)] = 1;
        }

        for (String s : wordList) {

            // if (the difference in length between the two words are greater then the current closest distance)
            // or if (the number of different characters are greater then the current closest distance) then continue
            if ((Math.abs(s.length() - w.length()) > closestDistance || getNumberOfDifferentCharacters(s) > closestDistance))
                continue;

            int dist = partDist(w, s);

            if (dist < closestDistance) { // if (we find a word with a shorter distance then make a new list and add word)
                closestDistance = dist;
                closestWords = new ArrayList<String>(100);
                closestWords.add(s);
            } else if (dist == closestDistance) // if (the word is equal in distance) then add to existing list
                closestWords.add(s);
        }
    }

    int getNumberOfDifferentCharacters(String s) {

        int counter = 0;
        int sLen = s.length();
        for (int i = 0; i < sLen; i++) {
            if (wordLetters[(int) s.charAt(i)] == 0)
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
