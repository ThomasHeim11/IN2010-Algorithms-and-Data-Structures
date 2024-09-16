import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Kattunge {
    public static void main(String[] args) {
        // Sjekk om filnavnet er gitt som argument
        if (args.length != 1) {
            System.out.println("Bruk: java Kattunge <filnavn>");
            return;
        }
        // Henter filnavn 
        String filnavn = args[0];
        
        int kattungePosisjon = 0; // Initiell plassering for kattungen.
        // HashMap for å lagre foreldre til barn
        Map<Integer, Integer> foreldremap = new HashMap<>();

        try {
            // Les alle linjene fra filen
            List<String> linjer = Files.readAllLines(Paths.get(filnavn));
            
            // Behandle første linje for å få startpunktet til kattungen
            kattungePosisjon = Integer.parseInt(linjer.get(0).trim());
            
            // Behandle resten av linjene for å bygge treet
            for (int i = 1; i < linjer.size(); i++) {
                // Trim linjen for å fjerne mellomrom
                String linje = linjer.get(i).trim();
                if (linje.equals("-1")) {
                    break;  // Slutt ved -1
                }
                
                // Pars inputlinjen
                String[] deler = linje.split(" ");
                // Forste element er forelder
                int forelder = Integer.parseInt(deler[0]);
                // Resterende elementer er barn. 
                for (int j = 1; j < deler.length; j++) {
                    int barn = Integer.parseInt(deler[j]);
                    // Legger inn forelder-barn-forhold i HashMap
                    foreldremap.put(barn, forelder);
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke lese filen: " + filnavn);
            e.printStackTrace();
            return;
        }

        // Spore stien fra kattungen til roten
        List<Integer> sti = new ArrayList<>();// Holder styr på stien. 
        // Så lenge posisjon til katten har en forelder. 
        while (foreldremap.containsKey(kattungePosisjon)) {
            // Legger til gjeldenede posisjon i stien. 
            sti.add(kattungePosisjon);
            // Flytt til forelderen
            kattungePosisjon = foreldremap.get(kattungePosisjon);
        }
        // Legger til roten i stien. 
        sti.add(kattungePosisjon);  // Legg til roten
        
        // Skriv ut stien
        for (int i = 0; i < sti.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(sti.get(i));
        }
        System.out.println();
    }
}