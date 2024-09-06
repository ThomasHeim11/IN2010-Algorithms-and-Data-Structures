import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Heap {

    // Metode for å finne venstre barnet til A[i]
    private static int leftOf(int i) {
        return 2 * i + 1;
    }

    // Metode for å finne høyre barnet til A[i]
    private static int rightOf(int i) {
        return 2 * i + 2;
    }

    // Metode som skriver ut elementer i postordre rekkefølge fra heap
    private static void printBalancedPostOrder(int[] heap, int index, int size) {
        if (index >= size) {
            return;
        }
        // Rot noden
        System.out.println(heap[index]);
        // Høyre sub-tree
        printBalancedPostOrder(heap, rightOf(index), size);
        // Venstre sub-tree
        printBalancedPostOrder(heap, leftOf(index), size);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Heap <filename>");
            return;
        }

        String filename = args[0];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Les fil og legg til elementer i minHeap
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

        // Konverter minHeap til array
        int size = minHeap.size();
        int[] heapArray = new int[size];
        for (int i = 0; i < size; i++) {
            heapArray[i] = minHeap.poll();
        }

        // Print elements in post-order
        printBalancedPostOrder(heapArray, 0, size);
    }
}