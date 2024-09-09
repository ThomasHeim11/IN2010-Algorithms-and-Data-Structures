import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class BalancedBSTHeap {

    // Hjelpefunksjon for å konstruere balansert rekkefølge ved hjelp av heap
    public static void printBalancedOrder(List<Integer> input) {
        PriorityQueue<Integer[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        heap.offer(new Integer[]{0, input.size() - 1});

        // List for å lagre resultatene
        List<Integer> result = new ArrayList<>();
        
        while (!heap.isEmpty()) {
            Integer[] range = heap.poll();
            int start = range[0];
            int end = range[1];
            
            if (start > end) continue;
            
            int mid = (start + end) / 2;
            result.add(input.get(mid));
            
            // Legg venstre og høyre sub-arrays på heap
            heap.offer(new Integer[]{start, mid - 1});
            heap.offer(new Integer[]{mid + 1, end});
        }
        
        for (int num : result) {
            System.out.println(num);
        }
    }
}

public class Heap {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        List<Integer> numberList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    numberList.add(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Collections.sort(numberList); // Sørg for at listen er sortert
        BalancedBSTHeap.printBalancedOrder(numberList); // Kaller metoden for å skrive ut balansert BST rekkefølge ved hjelp av heap
    }
}
