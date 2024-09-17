import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Bucketsort {
    private static int comparisons = 0;
    private static int swaps = 0;

    @SuppressWarnings("unchecked")
    public static void bucketSort(int[] A) {
        comparisons = 0;
        swaps = 0;

        int n = A.length;
        if (n <= 0) {
            comparisons++; // for checking array length
            return; // Ingen sortering nødvendig for tom array
        }

        // Finn minimums- og maksimumsverdien for å bestemme spennet til bøttene
        int minValue = A[0];
        int maxValue = A[0];
        for (int i = 1; i < n; i++) {
            comparisons++;
            if (A[i] < minValue) {
                minValue = A[i];
            } else if (A[i] > maxValue) {
                maxValue = A[i];
            }
        }

        // Oppretter bøtter
        ArrayList<Integer>[] B = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            B[i] = new ArrayList<>();
        }

        // Deler elementer i A til bøttene
        for (int i = 0; i < n; i++) {
            comparisons++; // this could be considered as part of distribution logic
            int k = (int) ((double) (A[i] - minValue) / (maxValue - minValue + 1) * n); // Skalering basert på spennet
            B[k].add(A[i]);
            swaps++; // adding element to the bucket
        }

        // Sorterer og flater ut bøttene tilbake til array A
        int j = 0;
        for (int k = 0; k < n; k++) {
            Collections.sort(B[k]);
            for (int x : B[k]) {
                A[j++] = x;
                swaps++; // moving elements back to original array
            }
        }
    }

    public static void main(String[] args) throws IOException{
    if(args.length != 1){
        System.out.println("Bruk: java InsertionSort <filnavn>");
        System.exit(1);
    }
    String filnavn = args[0];
    ArrayList<Integer> tall = new ArrayList<>();
    // Leser tall fra fil. 
    try(BufferedReader br = new BufferedReader(new FileReader(filnavn))){
        String line;
        while((line = br.readLine()) != null){
            tall.add(Integer.parseInt(line.trim()));
        }
    } catch (IOException e){
        e.printStackTrace();
        System.exit(1);
    }
    // Konverterer ArrayList til int-array. 
    int[] array = tall.stream().mapToInt(i -> i).toArray();
    //Sorterer  
    bucketSort(array);

    // Skriv ut til fil
    try(FileWriter fw = new FileWriter(filnavn+ "_bucketsort.out")){
        for(int num : array){
            fw.write(num + "\n");
        }
    } catch (IOException e){
        e.printStackTrace();
    }
}
}