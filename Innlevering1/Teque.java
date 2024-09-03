// Importere LinkedList for enkel tilgang til metoder som add, addFirst, addLast og get.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Teque {
    // Oppretter en lenkeliste
    private LinkedList<Integer> list = new LinkedList<>();

    public void push_back(int x) {
        // Legger til bakerst i lista.
        list.addLast(x);
    }

    public void push_front(int x) {
        // Legger til fremst i lista.
        list.addFirst(x);
    }

    public void push_middle(int x) {
        // Beregner midten i lista.
        int mid_index = (list.size() + 1) / 2;
        // Setter i midten av lista.
        list.add(mid_index, x);
    }

    public int get(int i) {
        // Finner element på indeks i.
        return list.get(i);
    }

    // Leser fra en tekstfil.
    /* Eksempel‐input 
        9
        push_back 9
        push_front 3
        push_middle 5
        get 0
        get 1
        get 2
        push_middle 1
        get 1
        get 2
     */

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bruk: java Teque <filnavn>");
            return;
        }

        String fileName = args[0];
        Teque teque = new Teque();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Leser antallet operasjoner
            int N = Integer.parseInt(reader.readLine().trim());

            for (int i = 0; i < N; i++) {
                String operationLine = reader.readLine().trim();
                String[] parts = operationLine.split(" ");
                String operation = parts[0];

                if (operation.equals("push_back")) {
                    int value = Integer.parseInt(parts[1]);
                    teque.push_back(value);
                } else if (operation.equals("push_front")) {
                    int value = Integer.parseInt(parts[1]);
                    teque.push_front(value);
                } else if (operation.equals("push_middle")) {
                    int value = Integer.parseInt(parts[1]);
                    teque.push_middle(value);
                } else if (operation.equals("get")) {
                    int index = Integer.parseInt(parts[1]);
                    System.out.println(teque.get(index));
                } else {
                    System.out.println("Ugyldig operasjon: " + operation);
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke lese filen: " + e.getMessage());
        }
    }
}
