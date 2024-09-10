import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Oppgave b) Bygge balanserte søketrær
class BalancedBSTUsingHeap {

    // Metode for å skrive ut elementene i balansert rekkefølge
    public void printBalancedOrder(PriorityQueue<Integer> heap) {
        // Sorter elementene fra heap i en liste ved hjelp av poll()
        List<Integer> numbers = new ArrayList<>();
        while (!heap.isEmpty()) {
            numbers.add(heap.poll());
        }
        
        // Skriv ut elementene i en balansert rekkefølge
        printBalancedOrderHelper(numbers, 0, numbers.size() - 1);
    }
    
    // Rekursiv metode for å skrive ut elementer i balansert rekkefølge
    private void printBalancedOrderHelper(List<Integer> numbers, int start, int end) {
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;
        System.out.println(numbers.get(mid));

        printBalancedOrderHelper(numbers, start, mid - 1);
        printBalancedOrderHelper(numbers, mid + 1, end);
    }
}

public class Heap {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename>");
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
