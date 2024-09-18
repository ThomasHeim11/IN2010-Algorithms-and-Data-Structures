class InsertionSort extends Sorter {
    @Override
    void sort() {
        for (int i = 1; i < n; i++) {
            int key = A[i];
            int j = i - 1;
            while (j >= 0 && gt(A[j], key)) {
                A[j + 1] = A[j];
                j = j - 1;
                swaps++;
            }
            A[j + 1] = key;
        }
    }

    @Override
    String algorithmName() {
        return "insertion";
    }
}