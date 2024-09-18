import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Bucketsort extends Sorter {
    @Override
    void sort() {
        bucketSort(A, n);
    }
    
    private void bucketSort(int[] array, int n) {
        if (n <= 0) return;

        // Calculate the range of the input array
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;

        // Use a standard heuristic for number of buckets
        int bucketCount = (int) Math.sqrt(n);
        
        // Ensure bucketCount is at least 1
        if (bucketCount < 1) {
            bucketCount = 1;
        }

        // Initialize the buckets
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Distribute array elements into buckets
        for (int i = 0; i < n; i++) {
            comparisons++;
            int bucketIndex = (int) Math.floor(((double) (array[i] - min) / range) * bucketCount);
            // Ensure bucketIndex is in the valid range [0, bucketCount-1]
            if (bucketIndex >= bucketCount) {
                bucketIndex = bucketCount - 1;
            }
            System.out.println("Element: " + array[i] + " -> Bucket Index: " + bucketIndex); // Add logging here
            buckets.get(bucketIndex).add(array[i]);
        }

        // Print bucket contents for debugging
        for (int i = 0; i < bucketCount; i++) {
            System.out.println("Bucket " + i + ": " + buckets.get(i));
        }

        // Merge sorted buckets
        int currentIndex = 0;
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int val : bucket) {
                array[currentIndex++] = val;
                swaps++;
            }
        }

        // Print the final sorted array for verification
        System.out.println("Sorted Array: " + Arrays.toString(array));
    }

    @Override
    String algorithmName() {
        return "bucket";
    }
}