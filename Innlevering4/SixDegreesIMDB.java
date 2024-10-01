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
                                 Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                                 Map<MovieGraph.ActorNode, String> movieEdge,
                                 Map<MovieGraph.ActorNode, Double> movieRating, MovieGraph graph) {
        Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
        Set<MovieGraph.ActorNode> visited = new HashSet<>();

        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            MovieGraph.ActorNode currentNode = queue.poll();

            for (MovieGraph.Edge edge : graph.getEdgesForActor(currentNode)) {
                MovieGraph.ActorNode neighbor = edge.actor1.equals(currentNode) ? edge.actor2 : edge.actor1;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    previous.put(neighbor, currentNode);
                    movieEdge.put(neighbor, edge.movieTitle);
                    movieRating.put(neighbor, edge.rating);

                    if (neighbor.equals(goalNode)) {
                        return;
                    }

                    queue.add(neighbor);
                }
            }
        }
    }

    private static void printPath(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                                  Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                                  Map<MovieGraph.ActorNode, String> movieEdge,
                                  Map<MovieGraph.ActorNode, Double> movieRating) {
        List<MovieGraph.ActorNode> path = new ArrayList<>();
        List<String> movies = new ArrayList<>();
        List<Double> ratings = new ArrayList<>();

        MovieGraph.ActorNode node = goalNode;
        while (node != null && node != startNode) {
            path.add(node);
            movies.add(movieEdge.get(node));
            ratings.add(movieRating.get(node));
            node = previous.get(node);
        }

        if (node == null) {
            System.out.println("Ingen sti funnet.");
            return;
        }

        path.add(startNode);
        Collections.reverse(path);
        Collections.reverse(movies);
        Collections.reverse(ratings);

        Iterator<MovieGraph.ActorNode> pathIterator = path.iterator();
        Iterator<String> movieIterator = movies.iterator();
        Iterator<Double> ratingIterator = ratings.iterator();

        MovieGraph.ActorNode actor = pathIterator.next();
        System.out.print(actor.name);

        while (pathIterator.hasNext()) {
            String movie = movieIterator.next();
            double rating = ratingIterator.next();
            actor = pathIterator.next();
            System.out.println();
            System.out.print("===[ " + movie + " (" + rating + ") ]===> " + actor.name);
        }
        System.out.println();
    }

    public static void findShortestPath(MovieGraph movieGraph, String startActorID, String goalActorID) {
        MovieGraph.ActorNode startNode = movieGraph.getActorNode(startActorID);
        MovieGraph.ActorNode goalNode = movieGraph.getActorNode(goalActorID);

        if (startNode == null || goalNode == null) {
            System.out.println("Skuespiller ikke funnet");
            return;
        }

        Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous = new HashMap<>();
        Map<MovieGraph.ActorNode, String> movieEdge = new HashMap<>();
        Map<MovieGraph.ActorNode, Double> movieRating = new HashMap<>();

        BFSVisit(startNode, goalNode, previous, movieEdge, movieRating, movieGraph);

        if (previous.containsKey(goalNode)) {
            printPath(startNode, goalNode, previous, movieEdge, movieRating);
        } else {
            System.out.println("Ingen sti funnet mellom " + startNode.name + " og " + goalNode.name);
        }
    }
}