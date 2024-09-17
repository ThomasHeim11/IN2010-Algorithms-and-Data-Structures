public class InsertionSort {
    public static int comparisons = 0;
    public static int swaps = 0;

    public static void insertionSort(int[] array) {
        comparisons = 0;
        swaps = 0;

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                comparisons++;
                array[j + 1] = array[j];
                swaps++;
                j = j - 1;
            }
            
            array[j + 1] = key;
            swaps++;
        }
    }
}