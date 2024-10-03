import java.io.IOException;
import java.util.*;

public class SixDegreesIMDB {
    public static void main(String[] args) {
        MovieGraph movieGraph = new MovieGraph();
        // Bygger opp grafen fra filen med filmer og skespillere. 
        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Streger som som skal testes. Samme som fra eksmpelet i oppgaven. 
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
// BFS-algoritme for å finne den korteste stien fra startnoden til målnoden. 
    private static void BFSVisit(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                                 Map<MovieGraph.ActorNode, MovieGraph.Edge> previous,
                                 MovieGraph graph) {
        Queue<MovieGraph.ActorNode> queue = new LinkedList<>(); // Kø for BFS. 
        Set<MovieGraph.ActorNode> visited = new HashSet<>(); //besøkte noder. 

        System.out.println("Starter BFS fra: " + startNode.name);
        // Sjekker om startnoden er målnoden. 
        if(startNode.equals(goalNode)) {
            System.out.println("Startnoden er måletoden.");
            return;
        }
        // Marker startnoden som besøkt. 
        visited.add(startNode);
        // legg en startnode i køen. 
        queue.add(startNode);
        // så lenge køen ikke er tom. 
        while (!queue.isEmpty()) {
            MovieGraph.ActorNode currentNode = queue.poll();
        // Utforske alle naboene til den nåværende noden. 
            for (MovieGraph.Movie movie : currentNode.movies) {
                for (MovieGraph.ActorNode neighbor : movie.actors) {
                    // Hvis naboen ikke er besøkt. 
                    if (!visited.contains(neighbor)) {
                        // Marker naboeb som besøkt. 
                        visited.add(neighbor);
                        // Oppdaterer veien til naboen. 
                        previous.put(neighbor, new MovieGraph.Edge(currentNode, neighbor, movie.title, movie.rating));
                        // Sjekker om naboeben er målnoden. 
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
    // Metode for å skrive ut stien fra startnode til målnode. 
    private static void printPath(MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode,
                                  Map<MovieGraph.ActorNode, MovieGraph.Edge> previous) {
        List<String> path = new ArrayList<>();
        MovieGraph.ActorNode current = goalNode;
        // Rekonstruere stien ved å følge `previous` kartet.
        while (!current.equals(startNode)) {
            MovieGraph.Edge edge = previous.get(current);
            path.add("===[ " + edge.movieTitle + " (" + edge.rating + ") ] ===> " + current.name);
            current = edge.actor1;
        }
        // Legg til startnode. 
        path.add(startNode.name);
        // Reverserer stien for rikitg rekkefølge. 
        Collections.reverse(path);

        System.out.println("Korteste sti fra " + startNode.name + " til " + goalNode.name + ":");
        for (String step : path) {
            System.out.println(step);
        }
        System.out.println();
    }

    public static void findShortestPath(MovieGraph movieGraph, String startActorID, String goalActorID) {
        MovieGraph.ActorNode startNode = movieGraph.getActorNode(startActorID);
        MovieGraph.ActorNode goalNode = movieGraph.getActorNode(goalActorID);
        // Sjekk om begge skespillern finnes i grafen. 
        if (startNode == null || goalNode == null) {
            System.out.println("Skuespiller ikke funnet");
            return;
        }

        Map<MovieGraph.ActorNode, MovieGraph.Edge> previous = new HashMap<>();
        // Utfør BFS for å finne den korteste stien. 
        BFSVisit(startNode, goalNode, previous, movieGraph);
        // Skriv ut resultatene om en sti ble funnet. 
        if (previous.containsKey(goalNode)) {
            System.out.println("Korteste sti funnet!");
            printPath(startNode, goalNode, previous);
        } else {
            System.out.println("Ingen sti funnet mellom " + startNode.name + " og " + goalNode.name);
        }
    }
}