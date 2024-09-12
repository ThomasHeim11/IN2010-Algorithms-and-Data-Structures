
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Kattunge{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Les startpunktet til kattungen
        int kattPosisjon = sc.nextInt();
        sc.nextLine();

        // En map for å holde overikt over foreldre
        Map<Integer, Integer> forelderMap = new HashMap<>();


        //Leser reseten av input for å bygge treet
        while (true) { 
            String linje = sc.nextLine();
            if(linje.equals("-1")){
                break;
            }
            String[] deler = linje.split(" ");
            int forelder = Integer.parseInt(deler[0]);
            for(int i = 1; i < deler.length; i++){
                int barn = Integer.parseInt(deler[i]);
                forelderMap.put(barn,forelder);
            } 
        }
        // Spor stien fra kattungen til roten
        List<Integer> sti = new ArrayList<>();
        while (forelderMap.containsKey(kattPosisjon)){
            sti.add(kattPosisjon);
            kattPosisjon = forelderMap.get(kattPosisjon);
        }
        sti.add(kattPosisjon);

        // Skriv ut stien
        for(int i = 0; i < sti.size(); i++){
            if(i > 0){
                System.out.print(" ");
            }
            System.out.print(sti.get(i));
        }
        System.out.println();
        sc.close();

    }
}
