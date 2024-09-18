
import java.util.Arrays;

class Bucketsort extends Sorter {
    @Override
    void sort() {
        bucketSort(A, n);
    }

    private void bucketSort(int[] array, int n) {
        if (n <= 0) return;
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int bucketCount = max - min + 1;
        int[][] buckets = new int[bucketCount][];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new int[n]; // initialize with maximum possible length
        }

        int[] indexArray = new int[bucketCount];

        for (int i = 0; i < n; i++) {
            comparisons++;
            int bucketIndex = (array[i] - min) / bucketCount;
            buckets[bucketIndex][indexArray[bucketIndex]++] = array[i];
        }

        int currentIndex = 0;

        for (int i = 0; i < bucketCount; i++) {
            if (indexArray[i] != 0) {
                Arrays.sort(buckets[i], 0, indexArray[i]);
                for (int j = 0; j < indexArray[i]; j++) {
                    array[currentIndex++] = buckets[i][j];
                    swaps++;
                }
            }
        }
    }

    @Override
    String algorithmName() {
        return "bucket";
    }
}