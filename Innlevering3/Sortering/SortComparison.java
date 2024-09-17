import java.io.*;
import java.util.*;

public class SortComparison {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java SortComparison <filename>");
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
        
        String outputFilename = filename + "_results.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            // CSV header
            String header = String.join(",",
                "n",
                "bucket_cmp", "bucket_swaps", "bucket_time",
                "heap_cmp", "heap_swaps", "heap_time",
                "insertion_cmp", "insertion_swaps", "insertion_time",
                "merge_cmp", "merge_swaps", "merge_time"
            );
            writer.write(header + "\n");

            for (int i = 0; i <= array.length; i++) {
                int[] subArray = Arrays.copyOf(array, i);

                // BucketSort
                Bucketsort.comparisons = 0;
                Bucketsort.swaps = 0;
                long startTime = System.nanoTime();
                Bucketsort.bucketSort(subArray);
                long bucketDuration = (System.nanoTime() - startTime) / 1000;
                int bucketComparisons = Bucketsort.comparisons;
                int bucketSwaps = Bucketsort.swaps;

                // Heapsort
                subArray = Arrays.copyOf(array, i);
                Heapsort.comparisons = 0;
                Heapsort.swaps = 0;
                startTime = System.nanoTime();
                Heapsort.heapsort(subArray);
                long heapDuration = (System.nanoTime() - startTime) / 1000;
                int heapComparisons = Heapsort.comparisons;
                int heapSwaps = Heapsort.swaps;

                // InsertionSort
                subArray = Arrays.copyOf(array, i);
                InsertionSort.comparisons = 0;
                InsertionSort.swaps = 0;
                startTime = System.nanoTime();
                InsertionSort.insertionSort(subArray);
                long insertionDuration = (System.nanoTime() - startTime) / 1000;
                int insertionComparisons = InsertionSort.comparisons;
                int insertionSwaps = InsertionSort.swaps;
                
                // MergeSort
                subArray = Arrays.copyOf(array, i);
                MergeSort.comparisons = 0;
                MergeSort.swaps = 0;
                startTime = System.nanoTime();
                MergeSort.mergeSort(subArray);
                long mergeDuration = (System.nanoTime() - startTime) / 1000;
                int mergeComparisons = MergeSort.comparisons;
                int mergeSwaps = MergeSort.swaps;

                // Write to CSV file
                writer.write(String.join(",",
                    String.valueOf(i), 
                    String.valueOf(bucketComparisons), String.valueOf(bucketSwaps), String.valueOf(bucketDuration),
                    String.valueOf(heapComparisons), String.valueOf(heapSwaps), String.valueOf(heapDuration),
                    String.valueOf(insertionComparisons), String.valueOf(insertionSwaps), String.valueOf(insertionDuration),
                    String.valueOf(mergeComparisons), String.valueOf(mergeSwaps), String.valueOf(mergeDuration)
                ) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}