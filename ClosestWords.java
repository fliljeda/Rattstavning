/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.ArrayList;
import java.util.List;

public class ClosestWords {
    List<String> closestWords = null;

    int closestDistance = -1;

    int partDist(String w1, String w2, int w1len, int w2len) {

        if (w1len == 0)
            return w2len;
        if (w2len == 0)
            return w1len;

        int res = partDist(w1, w2, w1len - 1, w2len - 1) + (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
        int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
        int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;

        if (addLetter < res)
            res = addLetter;
        if (deleteLetter < res)
            res = deleteLetter;

        return res;
    }

    int Distance(String w1, String w2) {
        return partDist(w1, w2, w1.length(), w2.length());
    }

    public ClosestWords(String w, List<String> wordList) {
        float val = 0;
        float counter = 0;
        float printCounter = 0;
        for (String s : wordList) {
            int dist = Distance(w, s);
            //System.out.println("d(" + w + "," + s + ")=" + dist);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new ArrayList<>();
                closestWords.add(s);
            } else if (dist == closestDistance)
                closestWords.add(s);
            val = counter++/wordList.size();
            printCounter++;
            if(printCounter%100 == 0)
                System.out.printf("%.3f%%\n", val);
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
