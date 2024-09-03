import java.util.HashSet;
import java.util.Set;

/**a) binært søketre på mengde */
public class SetTre{
    // Oppretter datastruktur
    private Set <String> mengder = new HashSet<>();
    
    // Contains: O(log(n))
    public void inneholder(set,x){
        if(mengder.contaons(x)){

        }  
    }
    // Insert: O(log(n))
    public void settInn(set,x){

    }

    // remove: O(log(n))
    public void fjern(set,x){

    }

    // size(set): O(log(n))
    public void storrelse(set){
        set.size();

    }
}


public static void main(String[] args){
    if (args.length != 1){
        System.out.println("Bruk: java Mengder <filnanv>");
    }
}