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
            int left = 2*1+1;
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
    }
