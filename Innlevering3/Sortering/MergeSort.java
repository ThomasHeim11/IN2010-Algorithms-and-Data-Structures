


public class MergeSort{
    public static int[] mergeSort(int[] A){
        if(A.length <= 1){
            return A;
        }
        int mid = A.length/2;
        int[]left = new int[mid];
        int[] right = new int [A.length-mid];
        // Kopiierer element til de to halvdelene.
        System.arraycopy(A,0,left,0,mid);
        System.arraycopy(A,mid,right,0,A.length-mid);
        //Rekursit sorterer begge halvdelene
        int[] sortedLeft = mergeSort(left);
        int[] sortedRigth = mergeSort(right);
        // Fletter de to sorterte halvdelene sammen
        return Merge(sortedLeft, sortedRigth);

    }
   
}
public class Merge{
    public static int[] merge(int[] A1, int[] A2){
        int i = 0;
        int j = 0;
        // Fortsetter så lenge begge inndekser er innen grenser for A1 og A2. 
        while (i < A1.length && j < A2.length) {
            //Setter element i A1[i] i den neste ledige stede i A(som er A[i+j])
            if (A1[i] <= A2[j]){
                A1[i+j] = A1[i];
            } else{
                // Setter element i A2[j] i den neste ledige stedet i A(somer er A[i+j])
                A1[i+j] = A2[j];
                j++;
            }
        }
        while (i < A1.length){
            A1[i+j] = A1[i];
            i++;
        }
        while(j < A2.length){
            A1[i+j] = A2[j];
            j++;
        }
        return A1;
    }
}