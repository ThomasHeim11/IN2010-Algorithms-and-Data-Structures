
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Værste kjøretid for heapsort O(n*log(n)).

public class Heapsort{
    public static void heapsort(int[]A){
        buildMaxHeap(A);
        
        // Går gjennom arraye fra ende til start
        int n = A.length;
        for(int i = n-1; i >= 1; i--){
            // Bytter det første elm(max) med det siste elementet. 
            swap(A,0,i);
            // Reduserer størrelsen med 1. 
            n--;
            // Oppretholder heap max. 
            heapify(A,0,n);
        }
    }
    public static void buildMaxHeap(int[] A){
        int n = A.length;

        //Går gjennom alle foreldrene fra midten til starter og
        //bygger max heap. 
        for(int i = n/2-1; i >= 0; i--){
            heapify(A,i,n);
        }
    }

        // Funksjonen for heapify
    private static void heapify(int[] A, int i, int n){
            int left = 2*i+1;
            int right = 2*i+2;
            int max = i;
        
        // Hvis venstre barn er større enn foreldrene
        if(left < n && A[left] > A[max]){
            max = left;
        }
        // Hvis høyre barn er større enn det største av forende og ve. barn
        if(right < n && A[right] > A[max]){
            max = right;
        }
        //Hvis max ikke er indeksen i, betyr det at ett av barna er større enn foreldrene.
        if(max != i){
            swap(A,i,max);
            heapify(A, i, n);
        }
        }
        // Swap funksjonen
        private static void swap(int[]A, int x, int y){
            int temp = A[x];
            A[x] = A[y];
            A[y] = temp;
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
    //Sorterer innsetingssortering. 
    heapsort(array);

    // Skriv ut til fil
    try(FileWriter fw = new FileWriter(filnavn+ "_insertion.out")){
        for(int num : array){
            fw.write(num + "\n");
        }
    } catch (IOException e){
        e.printStackTrace();
    }
}
}
