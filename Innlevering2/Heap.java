import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

class HeapMetoder {

    // Metode for å skrive ut elementer i balansert postordre rekkefølge
    public static void printBalancedPostOrder(PriorityQueue<Integer> heap) {
        if (heap.isEmpty()) {
            return;
        }

        int size = heap.size();
        if (size == 1) {
            // Base-case: Hvis det kun er ett element, skriv ut og returner
            System.out.println(heap.poll());
            return;
        }

        // Beregn størrelsen til venstre og høyre sub-heap
        int leftSize = calculateLeftSize(size);
        PriorityQueue<Integer> leftHeap = new PriorityQueue<>();
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>();

        // Fyll venstre sub-heap med rekursiv metode
        fillHeap(heap, leftHeap, leftSize);

        // Resten til høyre sub-heap
        fillHeap(heap, rightHeap, size - leftSize);

        // Rekursiv kall for venstre sub-tre
        printBalancedPostOrder(leftHeap);
        // Rekursiv kall for høyre sub-tre
        printBalancedPostOrder(rightHeap);

        // Skriv ut roten
        System.out.println(heap.poll());
    }

    // Hjelpemetode for å beregne størrelsen på venstre sub-heap
    private static int calculateLeftSize(int totalSize) {
        // Ved et balansert tre, venstre sub-heap størrelse er antall noder
        // til venstre for roten i et nesten komplett binært tre.
        return ((totalSize - 1) / 2);
    }

    // Hjelpemetode for å fylle en sub-heap med count elementer rekursivt
    private static void fillHeap(PriorityQueue<Integer> srcHeap, PriorityQueue<Integer> destHeap, int count) {
        if (count <= 0 || srcHeap.isEmpty()) {
            return;
        }
        destHeap.offer(srcHeap.poll());
        fillHeap(srcHeap, destHeap, count - 1);
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

        // Skriv ut elementene i post-order for å oppnå balansert representasjon
        HeapMetoder.printBalancedPostOrder(minHeap);
    }
}