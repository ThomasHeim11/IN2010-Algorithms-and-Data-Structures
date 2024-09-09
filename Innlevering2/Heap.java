import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class BalancedBSTUsingHeap {

    public void printBalancedOrder(PriorityQueue<Integer> heap) {
        List<Integer> numbers = new ArrayList<>(heap);
        Collections.sort(numbers);
        int[] sortedArray = toIntArray(numbers, 0);
        printBalancedOrderHelper(sortedArray, 0, sortedArray.length - 1);
    }
    
    private int[] toIntArray(List<Integer> numbers, int index) {
        if (index == numbers.size()) {
            return new int[numbers.size()];
        }
        int[] array = toIntArray(numbers, index + 1);
        array[index] = numbers.get(index);
        return array;
    }
    
    private void printBalancedOrderHelper(int[] numbers, int start, int end) {
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;
        System.out.println(numbers[mid]);

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
        
        // Opprett en instans av BalancedBSTUsingHeap og kall algoritmen
        BalancedBSTUsingHeap bst = new BalancedBSTUsingHeap();
        bst.printBalancedOrder(heap);
    }
    
    public static PriorityQueue<Integer> lesOgSorterInput(String filename) {
        List<Integer> numberList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            numberList = readNumbers(br);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        Collections.sort(numberList);
        PriorityQueue<Integer> heap = new PriorityQueue<>(numberList);
        return heap;
    }

    private static List<Integer> readNumbers(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            return new ArrayList<>();
        }
        line = line.trim();
        List<Integer> list = readNumbers(br);
        if (!line.isEmpty()) {
            list.add(Integer.parseInt(line));
        }
        return list;
    }
}
