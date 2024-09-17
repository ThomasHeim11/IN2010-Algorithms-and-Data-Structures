import java.util.ArrayList; 
import java.util.Collections;

public class Bucketsort{
    public static void bucketSort(int[] A){
        int n = A.length;
        ArrayList<Integer>[] B = new ArrayList[n];
        for(int i = 0; i < n; i++){
            B[i] = new ArrayList<>();
        }
        // Dler elementer i A til bøttene i Bucketsort
        for(int i = 0; i < n; i++){
            int k = A[i] * n/(n*1);
            B[k].add(A[i]);
        }
        int j = 0;

        for(int k = 0; k <n; k++){
            Collections.sort(B[k]);

        // Går gjennom hvert element i listen B[k]
        for(int x: B[k]){
            A[j] = x;
            j++;
        }
        }
    }
}