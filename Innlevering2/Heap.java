import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

class HeapMetoder {

    // Metode som skriver ut elementer i balansert postordre rekkefølge ved bruk av heaps
    public static void printBalancedPostOrder(PriorityQueue<Integer> heap) {
        // Base-case: Hvis heap er tom
        if (heap.isEmpty()) {
            return;
        }

        // Del elementene i heapen i to heaps
        PriorityQueue<Integer> leftHeap = new PriorityQueue<>();
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>();

        // Antall elementer i heapen
        int originalSize = heap.size();
        // Flytt halvparten av elementene til venstreheapen
        for (int i = 0; i < originalSize / 2; i++) {
            leftHeap.offer(heap.poll());
        }
        // Flytt resten av elementene til høyreheapen
        while (!heap.isEmpty()) {
            rightHeap.offer(heap.poll());
        }

        // Rekursiv kall for venstre sub-tre
        printBalancedPostOrder(leftHeap);
        // Rekursiv kall for høyre sub-tre
        printBalancedPostOrder(rightHeap);
        // Skriv ut roten
        System.out.println(heap.poll());
    }
}

public class Heap {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Heap <filename>");
            return;
        }

        String filename = args[0];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Les inn elementer fra filen til minHeap
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    minHeap.offer(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Print elements in post-order for å oppnå balansert representasjon
        HeapMetoder.printBalancedPostOrder(minHeap);
    }
}