/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> readWordList(BufferedReader input) throws IOException {
        ArrayList<String> wordList = new ArrayList<String>(75000);
        while (true) {
            String s = input.readLine();
            if (s.equals("#"))
                break;
            wordList.add(s);
        }
        return wordList;
    }

    public static void main(String args[]) throws IOException {
        // Säkrast att specificera att UTF-8 ska användas, för vissa system har annan
        // standardinställning för teckenkodningen.

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        List<String> wordList = readWordList(stdin);
        String word;
        while ((word = stdin.readLine()) != null) {
            ClosestWords closestWords = new ClosestWords(word, wordList);
            System.out.print(word + " (" + closestWords.getMinDistance() + ")");
            for (String w : closestWords.getClosestWords())
                System.out.print(" " + w);
            System.out.println();
        }
    }
}
