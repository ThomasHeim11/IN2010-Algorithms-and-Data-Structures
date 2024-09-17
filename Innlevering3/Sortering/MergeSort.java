import java.io.*;
import java.util.ArrayList;

public class MergeSort {
    public static int comparisons = 0;
    public static int swaps = 0;

    public static int[] mergeSort(int[] A) {
        if (A.length <= 1) {
            comparisons++;
            return A;
        }

        int mid = A.length / 2;
        int[] left = new int[mid];
        int[] right = new int[A.length - mid];
        System.arraycopy(A, 0, left, 0, mid);
        System.arraycopy(A, mid, right, 0, A.length - mid);

        int[] sortedLeft = mergeSort(left);
        int[] sortedRight = mergeSort(right);

        return merge(sortedLeft, sortedRight);
    }

    public static int[] merge(int[] A1, int[] A2) {
        int[] result = new int[A1.length + A2.length];
        int i = 0, j = 0, k = 0;
        while (i < A1.length && j < A2.length) {
            comparisons++;
            if (A1[i] <= A2[j]) {
                result[k++] = A1[i++];
                swaps++;
            } else {
                result[k++] = A2[j++];
                swaps++;
            }
        }
        while (i < A1.length) {
            result[k++] = A1[i++];
            swaps++;
        }
        while (j < A2.length) {
            result[k++] = A2[j++];
            swaps++;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java MergeSort <filename>");
            System.exit(1);
        }

        String filename = args[0];
        ArrayList<Integer> numbers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        long startTime = System.nanoTime();
        int[] sortedArray = mergeSort(array);
        long duration = (System.nanoTime() - startTime) / 1000;

        try (FileWriter fw = new FileWriter(filename + "_merge.out")) {
            for (int num : sortedArray) {
                fw.write(num + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println("Time (microseconds): " + duration);
    }
}