import java.util.HashSet;
import java.util.Map;
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
        ActorNode actor1;
        ActorNode actor2;

        Edge(String ttId, double rating, ActorNode actor1, ActorNode actor2){
            this.ttId = ttId;
            this.rating = rating;
            this.actor1 = actor1;
            this.actor2 = actor2;

        }

    }
    public static void main(String[] args) {
        // Stien til filene som tar utgangspunkt i at testfilene er i samme mappe. 
        String moviesFile = "./movies.tsv";
        String actorsFile = "./actors.tsv";

        // Laster inn fil ratingen fra movies.tsv
        Map<String,Double> movieRatings = loadMovieRatings(moviesFile);
        // Laster inn skuespillergrafen
        Map<String, ActorNode> actorGraph = loadActorGraph(actorsFile,movieRatings);

        // Teller antall skuespillereint 
        int nodeCount = actorGraph.size();
        // Teller antall filemer
        int edgeCount = countEdges(actorGraph);

        // Skriver ut resultatene
        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + nodeCount);
        System.out.println("Edges: " + edgeCount);


        

    }
}