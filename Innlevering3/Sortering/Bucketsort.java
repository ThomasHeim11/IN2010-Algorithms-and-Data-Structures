import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Bucketsort {

    public static void bucketSort(int[] A) {
        int n = A.length;
        if (n <= 0) return; // Ingen sortering nødvendig for tom array

        // Finn minimums- og maksimumsverdien for å bestemme spennet til bøttene
        int minValue = A[0];
        int maxValue = A[0];
        for (int i = 1; i < n; i++) {
            if (A[i] < minValue) {
                minValue = A[i];
            } else if (A[i] > maxValue) {
                maxValue = A[i];
            }
        }

        // Oppretter bøtter
        ArrayList<Integer>[] B = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            B[i] = new ArrayList<>();
        }

        // Deler elementer i A til bøttene
        for (int i = 0; i < n; i++) {
            int k = (int) ((double) (A[i] - minValue) / (maxValue - minValue + 1) * n); // Skalering basert på spennet
            B[k].add(A[i]);
        }

        // Sorterer og flater ut bøttene tilbake til array A
        int j = 0;
        for (int k = 0; k < n; k++) {
            Collections.sort(B[k]);
            for (int x : B[k]) {
                A[j++] = x;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bruk: java Bucketsort <filnavn>");
            System.exit(1);
        }

        String filename = args[0];
        ArrayList<Integer> numbers = new ArrayList<>();

        // Leser tall fra filen
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Konverter ArrayList til en int-array
        int[] array = numbers.stream().mapToInt(i -> i).toArray();

        // Sorter med bucketSort
        bucketSort(array);

        // Skriv ut til fil
        try (FileWriter fw = new FileWriter(filename + "_bucket.out")) {
            for (int num : array) {
                fw.write(num + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
