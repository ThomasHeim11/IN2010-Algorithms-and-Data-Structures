import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Klasse som håndterer balanserte binære søketrær
class BalancedBST {

    // Metode for å skrive ut et balansert binært søketre i post-ordre rekkefølge
    public static void printBalancedOrder(int[] numbers) {
        printBalancedOrderHelper(numbers, 0, numbers.length - 1); // Kaller hjelpefunks med start og slutt indekser
    }

    // Hjelpefunksjon for å konstruere og skrive ut post-ordre rekkefølge rekursivt
    private static void printBalancedOrderHelper(int[] numbers, int start, int end) {
        if (start > end) { // Basis tilfelle for rekursjon, ingen elementer igjen
            return;
        }

        int mid = (start + end) / 2; // Finn midtpunktet i den nåværende delarrayen
    
         // Operere på midtpunktet til slutt
        System.out.println(numbers[mid]);

        // Rekursivt håndtere høyre sub-array
        printBalancedOrderHelper(numbers, mid + 1, end);

        // Rekursivt håndtere venstre sub-array først
        printBalancedOrderHelper(numbers, start, mid - 1);
    }
}

// Hovedklasse som inneholder main-metoden
public class InOrder {

    public static void main(String[] args) {
        if (args.length != 1) { // Sjekker om det er oppgitt en fil som argument
            System.out.println("Usage: java Main <filename>"); // Skriver ut riktig bruksmåte
            return; // Avslutter programmet
        }

        String filename = args[0]; // Leser filnavnet fra argumentene
        List<Integer> numberList = new ArrayList<>(); // Oppretter en liste for å lagre tallene

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { // Åpner filen for lesing
            String line;
            while ((line = br.readLine()) != null) { // Leser linje for linje fra filen
                line = line.trim(); // Fjerner eventuelle mellomrom fra starten og slutten av linjen
                if (!line.isEmpty()) { // Sjekker om linjen ikke er tom
                    numberList.add(Integer.parseInt(line)); // Konverterer linjen til et heltall og legger det til i listen
                }
            }
        } catch (IOException e) { // Fanger opp eventuelle IOExceptions som kan oppstå
            e.printStackTrace(); // Skriver ut stacktrace for feilsøking
            return; // Avslutter programmet
        }

        int[] numbers = numberList.stream().mapToInt(i -> i).toArray(); // Konverterer listen til en int-array

        BalancedBST.printBalancedOrder(numbers); // Kaller metoden for å skrive ut balansert BST
    }
}