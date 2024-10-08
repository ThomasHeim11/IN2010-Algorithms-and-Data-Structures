import java.io.*;
import java.util.ArrayList;
import java.util.List;
// Oppgave3
public class Oppgave3 {
    // Heapsort-algoritme
    private static void heapsort(List<Integer> list) {
        int n = list.size();

        // Bygg er maxheap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        // Trekker ut elementer fra heapen en etter en. 
        for (int i = n - 1; i > 0; i--) {
            // Flytt nåværende rot til enden. 
            int temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);

            // Retter opp i heapen. 
            heapify(list, i, 0);
        }
    }

    // Hjelpefunksjon for å opprettholde en heap. 
    private static void heapify(List<Integer> list, int n, int i) {
        int largest = i; // Initialiser største som roten
        int left = 2 * i + 1; // venstre barn = 2*i + 1
        int right = 2 * i + 2; // høyre barn = 2*i + 2

        // Hvis venstre barn er større enn rot
        if (left < n && list.get(left) > list.get(largest)) {
            largest = left;
        }

        // Hvis høyre barn er større enn største så langt
        if (right < n && list.get(right) > list.get(largest)) {
            largest = right;
        }
        // Hvis største ikke er rot
        if (largest != i) {
            int swap = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, swap);
            // Kaller på seg selv for å oppretholde heapen. 
            heapify(list, n, largest);
        }
    }
    // Hovedprogram. 
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Bruk: java Heapsort <filnavn>");
            System.exit(1);
        }

        String inputFilename = args[0];
        List<Integer> numbers = readNumbersFromFile(inputFilename);
        if (numbers == null) {
            System.exit(1);
        }
        heapsort(numbers);
        String outputFilename = inputFilename + "_out";
        writeNumbersToFile(outputFilename, numbers);
    }

    // Leser tall fra fil
    private static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Les linje for linje
            while ((line = br.readLine()) != null) {
                // Legg til tallet i listen etter fjerning av blanktegn
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Feil ved lesing fra fil: " + e.getMessage());
            return null;
        }
        return numbers;
    }

    // Skriver sorterte tall til fil
    private static void writeNumbersToFile(String filename, List<Integer> numbers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Integer number : numbers) {
                bw.write(number.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Feil ved skriving til fil: " + e.getMessage());
        }
    }
}