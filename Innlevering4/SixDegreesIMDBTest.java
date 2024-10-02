import java.io.IOException;

public class SixDegreesIMDBTest {
    public static void main(String[] args) {
        // Create a new MovieGraph instance
        MovieGraph movieGraph = new MovieGraph();

        // Build the graph using the provided data files
        try {
            movieGraph.buildGraph("marvel_movies.tsv", "marvel_actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Print the number of nodes and edges for validation
        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + movieGraph.actors.size());
        System.out.println("Edges: " + movieGraph.countEdges());

        // Predefined queries to be tested
        String[][] queries = {
            {"nm0424060", "nm0000375", "Scarlett Johansson", "Robert Downey Jr."}, // Expected to find a path
            {"nm0000164", "nm0000168", "Anthony Hopkins", "Samuel L. Jackson"}, // Expected to find a path
            {"nm0000602", "nm0000168", "Robert Redford", "Samuel L. Jackson"}, // Expected to find a path
            {"nm0000354", "nm0429363", "Matt Damon", "Toby Jones"}, // Expected to find a path
            {"nm1107001", "nm0038355", "Anthony Mackie", "Tadanobu Asano"} // Expected to find a path
        };

        System.out.println("Oppgave 2");
        for (String[] query : queries) {
            System.out.println(query[2] + " til " + query[3] + ":");
            SixDegreesIMDB.findShortestPath(movieGraph, query[0], query[1]);
            System.out.println();
        }
    }
}