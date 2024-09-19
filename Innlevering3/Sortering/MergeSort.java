class MergeSort extends Sorter {
    @Override
    void sort() {
        // Kaller mergeSort-metoden for å sortere arrayet A fra 0 til n-1
        mergeSort(A, 0, n - 1);
    }
    // Implementerer mergeSort-metoden som rekursivt deler arrayet og sorterer delene
    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // Finner midtpunktet for å dele arrayet
            int middle = (left + right) / 2;
            // Rekursivt sorterer den første halvdelen
            mergeSort(array, left, middle);
            // Rekursivt sorterer den andre halvdelen
            mergeSort(array, middle + 1, right);
            // Slår sammen de to sorterte halvdelene
            merge(array, left, middle, right);
        }
    }
    // Slår sammen to sorterte segmenter av et array
    private void merge(int[] array, int left, int middle, int right) {
        // Lengden til venstre subarray
        int n1 = middle - left + 1;
        // Lengden til høyre subarray
        int n2 = right - middle;
        // Oppretter midlertidig array for venstre subarray
        int[] L = new int[n1];
        // Oppretter midlertidig array for høyre subarray
        int[] R = new int[n2];

        // Kopierer dataene til de midlertidige arrayene L[] og R[]
        for (int i = 0; i < n1; i++)
            L[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = array[middle + 1 + j];
        // Initialiserer indekser for første, andre og sammenslått subarray
        int i = 0, j = 0, k = left;
        // Slår sammen de midlertidige arrayene tilbake i array[left..right]
        while (i < n1 && j < n2) {
            if (leq(L[i], R[j])) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
                swaps++;
            }
            k++;
        }
        // Kopierer de gjenværende elementene av L[], hvis det finnes noen
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        // Kopierer de gjenværende elementene av R[], hvis det finnes noen
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    @Override
    String algorithmName() {
        return "merge";
    }
}