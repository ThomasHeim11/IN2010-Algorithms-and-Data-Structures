import java.time.chrono.ThaiBuddhistChronology;
import java.util.HashSet;
import java.util.Set;

// Klasse som representerer grafen for filmdata.
public class MovieGraph {
    // Node skom representerer skuespillere. 
    private static class ActorNode {
        String id;
        String name;
        // Set med kanter for skuespiller(filmer)
        Set<Edge> edges = new HashSet<>();

        ActorNode(String id, String name){
            this.id = id;
            this.name = name;
        }
        // Legger  filem til skuespiller. 
        void addEdge(Edge edge){
            edges.add(edge);
        }
    }

    private static class Edge{
        String ttId;
        double rating;
        this.actor1 = actor1;
        this.actor2 = actor2;

    }
}