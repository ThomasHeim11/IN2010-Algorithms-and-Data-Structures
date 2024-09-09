import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BalancedBSTRecursive {

    // Metode for å skrive ut et balansert binært søketre i post-ordre rekkefølge ved rekursjon
    public void printBalancedOrder(int[] numbers) {
        printBalancedOrderHelper(numbers, 0, numbers.length - 1);
    }

    // Hjelpefunksjon for rekursiv konstruksjon av balansert BST
    private void printBalancedOrderHelper(int[] numbers, int start, int end) {
        if (start > end) {
            return;  // Basis tilfelle for rekursjon, ingen elementer igjen
        }

        int mid = (start + end) / 2;  // Finn midtpunktet i den nåværende delarrayen

        // Operer på midtpunktet først
        System.out.println(numbers[mid]);

        // Rekursivt håndtere venstre sub-array først
        printBalancedOrderHelper(numbers, start, mid - 1);

        // Rekursivt håndtere høyre sub-array
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
        int[] numbers = numberList.stream().mapToInt(i -> i).toArray(); // Konverterer listen til en int-array

        BalancedBSTRecursive bst = new BalancedBSTRecursive();
        bst.printBalancedOrder(numbers); // Kaller metoden for å skrive ut balansert BST rekkefølge
    }
}
