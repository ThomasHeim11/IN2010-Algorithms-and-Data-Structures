public class InsertionSort{
    public static void InsertionSort(int[] A){
        // En yttre løkke for å fra 1 til lengden av array minus 1. 
        for(int i = 1;i< A.length; i++){
            int j = i;
        // Hvis det nåværende elementet er mindre enn det forrige
        //forsett å bytte. 
        while(j > 0 && A[j-1] > A[j]){
            // Bytter plass på A[j] og A[j-1]
            int temp = A[j];
            A[j] = A[j-1];
            A[j-1] = temp;
            j--;
        }
    }
}
}
