
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


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
    InsertionSort(array);

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
