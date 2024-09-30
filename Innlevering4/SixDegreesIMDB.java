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
        }
    }

    private static void BFSVisit(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                             Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                             Map<MovieGraph.ActorNode, MovieGraph.Movie> movieEdge) {
    Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
    Set<MovieGraph.ActorNode> visited = new HashSet<>();

    System.out.println("Starting BFS from: " + startNode.name);
    System.out.println("Goal node: " + goalNode.name);

    queue.add(startNode);
    visited.add(startNode);

    while (!queue.isEmpty()) {
        MovieGraph.ActorNode current = queue.poll();

        for (MovieGraph.Movie movie : current.movies) {
            for (MovieGraph.ActorNode neighbor : movie.actors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    previous.put(neighbor, current);
                    movieEdge.put(neighbor, movie);

                    // System.out.println("Added to queue: " + neighbor.name + " via movie: " + movie.title);

                    if (neighbor.equals(goalNode)) {
                        System.out.println("Goal node " + goalNode.name + " found!");
                        return;
                    }
                }
            }
        }
    }
}


private static void printPath(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                              Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                              Map<MovieGraph.ActorNode, MovieGraph.Movie> movieEdge) {
    List<MovieGraph.ActorNode> path = new ArrayList<>();
    List<MovieGraph.Movie> movies = new ArrayList<>();

    for (MovieGraph.ActorNode at = goalNode; at != null; at = previous.get(at)) {
        path.add(at);
        if (previous.get(at) != null) {
            movies.add(movieEdge.get(at));
        }
    }

    Collections.reverse(path);
    Collections.reverse(movies);

    System.out.println("Constructed path from " + startNode.name + " to " + goalNode.name + ":");
    if (!path.isEmpty()) {
        Iterator<MovieGraph.ActorNode> pathIterator = path.iterator();
        Iterator<MovieGraph.Movie> movieIterator = movies.iterator();

        MovieGraph.ActorNode actor = pathIterator.next();
        System.out.print(actor.name);

        while (pathIterator.hasNext() && movieIterator.hasNext()) {
            MovieGraph.Movie movie = movieIterator.next();
            actor = pathIterator.next();
            System.out.print(" ===[ " + movie.title + " (" + movie.rating + ") ]===> " + actor.name);
        }
        System.out.println();
    } else {
        System.out.println("No path found.");
    }
}


    public static void findShortestPath(MovieGraph movieGraph, String startActorID, String goalActorID) {
        MovieGraph.ActorNode startNode = movieGraph.getActorNode(startActorID);
        MovieGraph.ActorNode goalNode = movieGraph.getActorNode(goalActorID);

        if (startNode == null || goalNode == null) {
            System.out.println("Skuespiller ikke funnet");
            return;
        }

        Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous = new HashMap<>();
        Map<MovieGraph.ActorNode, MovieGraph.Movie> movieEdge = new HashMap<>();

        BFSVisit(startNode, goalNode, previous, movieEdge);

        if (previous.containsKey(goalNode)) {
            printPath(startNode, goalNode, previous, movieEdge);
        } else {
            System.out.println("Ingen sti funnet mellom " + startNode.name + " og " + goalNode.name);
        }
    }
}