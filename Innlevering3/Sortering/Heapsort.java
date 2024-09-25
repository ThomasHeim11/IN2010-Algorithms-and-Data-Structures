class Heap extends Sorter {
    @Override
    void sort() {
        heapsort(A, n);
    }

    private void heapsort(int[] array, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(0, i);
            heapify(array, i, 0);
        }
    }

    private void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && lt(array[largest], array[left])) {
            largest = left;
        }

        if (right < n && lt(array[largest], array[right])) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            heapify(array, n, largest);
        }
    }

    @Override
    String algorithmName() {
        return "heap";
    }
}