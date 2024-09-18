import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));
        int[] A = in.lines().mapToInt(Integer::parseInt).toArray();
        in.close();

        SortRunner.runAlgsPart1(A, filename);
        SortRunner.runAlgsPart2(A, filename);
    }
}