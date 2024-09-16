import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MergeSort {

    public static int[] mergeSort(int[] A) {
        // Basistilfellet. 
        if (A.length <= 1) {
            return A;
        }
        // Finner indeksen på midten av arrayen. 
        int mid = A.length / 2;
        // Venstre side av arrayen. 
        int[] left = new int[mid];
        // Høyre side av arrayen. 
        int[] right = new int[A.length - mid];
        // Kopierer element til de to halvdelene.
        System.arraycopy(A, 0, left, 0, mid);
        System.arraycopy(A, mid, right, 0, A.length - mid);
        // Rekursivt sorterer begge halvdelene
        int[] sortedLeft = mergeSort(left);
        int[] sortedRight = mergeSort(right);
        // Fletter de to sorterte halvdelene sammen
        return merge(sortedLeft, sortedRight);
    }

    public static int[] merge(int[] A1, int[] A2) {
        int[] result = new int[A1.length + A2.length];
        int i = 0, j = 0, k = 0;
        // Fortsetter så lenge begge inndekser er innen grenser for A1 og A2.
        while (i < A1.length && j < A2.length) {
            if (A1[i] <= A2[j]) {
                result[k++] = A1[i++];
            } else {
                result[k++] = A2[j++];
            }
        }
        while (i < A1.length) {
            result[k++] = A1[i++];
        }
        while (j < A2.length) {
            result[k++] = A2[j++];
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MergeSort <filename>");
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

        // Sorter med mergesort
        int[] sortedArray = mergeSort(array);

        // Skriv ut til fil
        try (FileWriter fw = new FileWriter(filename + "_merge.out")) {
            for (int num : sortedArray) {
                fw.write(num + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}