import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

// Oppgave b) Bygge balanserte søketrær
class BalancedBSTUsingHeap {

    // Metode for å skrive ut elementene i balansert rekkefølge
    public void printBalancedOrder(PriorityQueue<Integer> heap) {
        // Skriv ut elementene i en balansert rekkefølge
        printBalancedOrderHelper(heap, heap.size());
    }
    
    // Rekursiv metode for å skrive ut elementer i balansert rekkefølge
    private void printBalancedOrderHelper(PriorityQueue<Integer> heap, int size) {
        if (size == 0 || heap.isEmpty()) {
            return;
        }

        int midIndex = size / 2;

        PriorityQueue<Integer> leftHeap = new PriorityQueue<>();
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>();
        
        // Flytt elementer til venstre heap, så midt og skriv det ut, og til slutt høyre heap
        for (int i = 0; i < size; i++) {
            int elem = heap.poll();
            if (i < midIndex) {
                leftHeap.offer(elem);
            } else if (i == midIndex) {
                // Midterste elementet
                System.out.println(elem);
            } else {
                rightHeap.offer(elem);
            }
        }

        // Rekursive anrop til venstre og høyre del
        printBalancedOrderHelper(leftHeap, leftHeap.size());
        printBalancedOrderHelper(rightHeap, rightHeap.size());
    }
}

public class Heap {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bruk: java Heap <filename>");
            return;
        }

        String filename = args[0];
        
        // Les tallene fra filen og sett dem i en PriorityQueue (heap)
        PriorityQueue<Integer> heap = lesOgSorterInput(filename);
        
        if (heap == null) {
            return;
        }

        // Opprett en instans av BalancedBSTUsingHeap og kall algoritmen.
        BalancedBSTUsingHeap bst = new BalancedBSTUsingHeap();
        bst.printBalancedOrder(heap);
    }
    
    public static PriorityQueue<Integer> lesOgSorterInput(String filename) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    heap.offer(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return heap;
    }
}