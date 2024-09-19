import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Bucketsort extends Sorter {
    
    @Override
    void sort() {
        bucketSort(A, n);
    }
    // Implementerer bucketSort-metoden som sorterer et array ved hjelp av bøttesortering
    private void bucketSort(int[] array, int n) {
        // Sjekker om array er tom eller holder 0 elementer. 
        if (n <= 0) return;

        // Beregner range av input-arrayet. 
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;

        // Bestemmer antall bøtter. 
        int bucketCount = (int) Math.sqrt(n);
        
        // Sikrer at antall bøtter er minst 1. 
        if (bucketCount < 1) {
            bucketCount = 1;
        }

        // Initialiserer bøttene
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Fordeler elementene fra arrayet inn i passende bøtter
        for (int i = 0; i < n; i++) {
            comparisons++;
            int bucketIndex = (int) Math.floor(((double) (array[i] - min) / range) * bucketCount);
            // Sikrer at bucketIndex er innenfor gyldig område[0, bucketCount-1]
            if (bucketIndex >= bucketCount) {
                bucketIndex = bucketCount - 1;
            }
            System.out.println("Element: " + array[i] + " -> Bucket Index: " + bucketIndex); // Add logging here
            buckets.get(bucketIndex).add(array[i]);
        }

        // Skriv ut bøttenes innhold for feilsøking
        for (int i = 0; i < bucketCount; i++) {
            System.out.println("Bucket " + i + ": " + buckets.get(i));
        }

        // Slår sammen sorterte bøtter tilbake til det opprinnelige arrayet
        int currentIndex = 0;
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int val : bucket) {
                array[currentIndex++] = val;
                swaps++;
            }
        }
        System.out.println("Sorted Array: " + Arrays.toString(array));
    }
    @Override
    String algorithmName() {
        return "bucket";
    }
}