class Heapsort extends Sorter {
    @Override
    void sort() {
        // Kaller på heapsort-metoden for å sortere arrayet A
        heapsort(A, n);
    }
    // Implementerer heapsort-metoden som sorterer et array ved hjelp av heap-sorteringsalgoritmen. 
    private void heapsort(int[] array, int n) {
        // Bygger en maks-heap fra det usorterte arrayet
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        // Tar elementer fra heapen én etter én og plasserer dem på riktig posisjon
        for (int i = n - 1; i >= 0; i--) {
            swap(0, i);
            heapify(array, i, 0);
        }
    }
    // Bidrar til å opprettholde heap-egenskapen for et gitt tre med rot på node i
    private void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        // Sjekker om venstre barn er større enn roten
        if (left < n && lt(array[largest], array[left])) {
            largest = left;
        }
         // Sjekker om høyre barn er større enn den største så langt
        if (right < n && lt(array[largest], array[right])) {
            largest = right;
        }
        // Bytter om roten hvis nødvendig for å opprettholde heap-egenskapen
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