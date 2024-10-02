import java.io.IOException;
import java.util.*;

public class SixDegreesIMDB {
    public static void main(String[] args) {
        MovieGraph movieGraph = new MovieGraph();

        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Predefined queries to be tested
        String[][] queries = {
            {"nm2255973", "nm0000460", "Donald Glover", "Jeremy Irons"},
            {"nm0424060", "nm8076281", "Scarlett Johansson", "Emma Mackey"},
            {"nm4689420", "nm0000365", "Carrie Coon", "Julie Delpy"},
            {"nm0000288", "nm2143282", "Christian Bale", "Lupita Nyong'o"},
            {"nm0637259", "nm0931324", "Tuva Novotny", "Michael K. Williams"}
        };

        System.out.println("Oppgave 2");
        for (String[] query : queries) {
            System.out.println(query[2] + " til " + query[3] + ":");
            findShortestPath(movieGraph, query[0], query[1]);
            System.out.println();
        }
    }

    private static void BFSVisit(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                                 Map<MovieGraph.ActorNode, MovieGraph.Edge> previous,
                                 MovieGraph graph) {
        Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
        Set<MovieGraph.ActorNode> visited = new HashSet<>();

        System.out.println("Starter BFS fra: " + startNode.name);
        if(startNode.equals(goalNode)) {
            System.out.println("Startnoden er måletoden.");
            return;
        }

        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            MovieGraph.ActorNode currentNode = queue.poll();

            for (MovieGraph.Movie movie : currentNode.movies) {
                for (MovieGraph.ActorNode neighbor : movie.actors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        previous.put(neighbor, new MovieGraph.Edge(currentNode, neighbor, movie.title, movie.rating));

                        if (neighbor.equals(goalNode)) {
                            System.out.println("Målnode " + goalNode.name + " funnet.");
                            return;
                        }

                        queue.add(neighbor);
                    }
                }
            }
        }

        System.out.println("Ingen sti funnet fra " + startNode.name + " til " + goalNode.name);
    }

    private static void printPath(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                                  Map<MovieGraph.ActorNode, MovieGraph.Edge> previous) {
        List<String> path = new ArrayList<>();
        MovieGraph.ActorNode current = goalNode;

        while (!current.equals(startNode)) {
            MovieGraph.Edge edge = previous.get(current);
            path.add("===[ " + edge.movieTitle + " (" + edge.rating + ") ] ===> " + current.name);
            current = edge.actor1;
        }
        path.add(startNode.name);

        Collections.reverse(path);

        System.out.println("Korteste sti fra " + startNode.name + " til " + goalNode.name + ":");
        for (String step : path) {
            System.out.println(step);
        }
        System.out.println(); // Add extra newline here for separation
    }

    public static void findShortestPath(MovieGraph movieGraph, String startActorID, String goalActorID) {
        MovieGraph.ActorNode startNode = movieGraph.getActorNode(startActorID);
        MovieGraph.ActorNode goalNode = movieGraph.getActorNode(goalActorID);

        if (startNode == null || goalNode == null) {
            System.out.println("Skuespiller ikke funnet");
            return;
        }

        Map<MovieGraph.ActorNode, MovieGraph.Edge> previous = new HashMap<>();

        BFSVisit(startNode, goalNode, previous, movieGraph);

        if (previous.containsKey(goalNode)) {
            System.out.println("Korteste sti funnet!");
            printPath(startNode, goalNode, previous);
        } else {
            System.out.println("Ingen sti funnet mellom " + startNode.name + " og " + goalNode.name);
        }
    }
}