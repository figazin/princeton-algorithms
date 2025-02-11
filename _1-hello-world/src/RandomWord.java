import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String champion = "";
        int wordCount = 0;
        while (!StdIn.isEmpty()) {
            wordCount++;
            String input = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / wordCount)) {
                champion = input;
            }
        }
        System.out.println(champion);
    }
}
