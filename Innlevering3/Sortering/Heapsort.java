public class Heapsort {
    public static int comparisons = 0;
    public static int swaps = 0;

    public static void heapsort(int[] array) {
        comparisons = 0;
        swaps = 0;

        int n = array.length;
        
        // Bygg max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        
        // Ekstraher elementer fra heapen
        for (int i = n - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            swaps++;

            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) {
            largest = left;
            comparisons++;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
            comparisons++;
        }

        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            swaps++;

            heapify(array, n, largest);
        }
    }
}