class InsertionSort extends Sorter {
    @Override
    void sort() {
        // Går gjennom hver element i arrayet fra index 1 til n-1
        for (int i = 1; i < n; i++) {
            // Lagre det nåværende elementet i en variabel 'key'
            int key = A[i];
            // Sett 'j' til å være elementet før 'i'
            int j = i - 1;

            // Flytt elementer av A[0..i-1] som er større enn 'key' til ett sted foran deres nåværende posisjon
            while (j >= 0 && gt(A[j], key)) {
                // Flytt det større elementet ett steg til høyre
                A[j + 1] = A[j];
                j = j - 1;
                swaps++;
            }
            // Plasser 'key' på sin korrekte posisjon
            A[j + 1] = key;
        }
    }

    @Override
    String algorithmName() {
        return "insertion";
    }
}