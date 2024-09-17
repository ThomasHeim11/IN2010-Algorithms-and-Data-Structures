import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bucketsort {
    public static int comparisons = 0;
    public static int swaps = 0;

    public static void bucketSort(int[] array) {
        // Initialisering
        comparisons = 0;
        swaps = 0;

        if (array == null || array.length == 0) {
            comparisons++; // for checking array length
            return;
        }

        int minValue = array[0];
        int maxValue = array[0];
        
        // Finn maksimum og minimum verdier i arrayen
        for (int value : array) {
            if (value < minValue) {
                minValue = value;
                swaps++;
            } else if (value > maxValue) {
                maxValue = value;
                swaps++;
            }
            comparisons += 2; // for each comparison
        }

        // Initialiser bøttene
        int bucketCount = maxValue - minValue + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribuer inputverdiene til bøttene
        for (int value : array) {
            int bucketIndex = value - minValue;
            buckets.get(bucketIndex).add(value);
        }

        // Sorter hver bøtte og sett sammen resultatet
        int arrayIndex = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int value : bucket) {
                array[arrayIndex++] = value;
                swaps++;
            }
        }
    }
}