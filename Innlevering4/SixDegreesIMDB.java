import java.util.*;

public class SixDegreesIMDB {

    public static void main(String[] args) {
        // Filstier til film- og skuespillerdatafiler
        String moviesFile = "./movies.tsv";
        String actorsFile = "./actors.tsv";

        // Laster filmvurderinger og lager en graf av skuespillere
        Map<String, Double> movieRatings = MovieGraph.loadMovieRatings(moviesFile);
        Map<String, MovieGraph.ActorNode> actorGraph = MovieGraph.loadActorGraph(actorsFile, movieRatings);

        // Finn og skriv ut korteste vei mellom forskjellige par av skuespillere
        findShortestPath(actorGraph, "nm2255973", "nm0000460"); // Donald Glover til Jeremy Irons
        findShortestPath(actorGraph, "nm0424060", "nm8076281"); // Scarlett Johansson til Emma Mackey
        findShortestPath(actorGraph, "nm4689420", "nm0000365"); // Carrie Coon til Julie Delpy
        findShortestPath(actorGraph, "nm0000288", "nm2143282"); // Christian Bale til Lupita Nyong'o
        findShortestPath(actorGraph, "nm0637259", "nm0931324"); // Tuva Novotny til Michael K. Williams
    }

    public static void findShortestPath(Map<String, MovieGraph.ActorNode> actorGraph, String startId, String goalId) {
        // Sjekk om start og målskuespiller finnes i grafen
        if (!actorGraph.containsKey(startId) || !actorGraph.containsKey(goalId)) {
            System.out.println("En eller begge skuespillere finnes ikke i grafen");
            System.out.println(); // Legger til linjeskift etter meldingen
            return;
        }

        // Initialiserer start- og målskuespillernoder
        MovieGraph.ActorNode startNode = actorGraph.get(startId);
        MovieGraph.ActorNode goalNode = actorGraph.get(goalId);

        // Data-strukturer for BFS
        Map<MovieGraph.ActorNode, Boolean> visited = new HashMap<>();
        Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
        Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous = new HashMap<>();
        Map<MovieGraph.ActorNode, MovieGraph.Edge> movieEdge = new HashMap<>();

        // Utfør BFS fra start
        BFSVisit(startNode, visited, queue, previous, movieEdge, goalNode);

        // Sjekk om målskuespilleren er besøkt
        if (visited.containsKey(goalNode) && visited.get(goalNode)) {
            // Skriv ut sti hvis den er funnet
            printPath(previous, movieEdge, startNode, goalNode);
        } else {
            System.out.println("Ingen sti funnet mellom " + startNode + " og " + goalNode);
        }
        
        System.out.println(); // Legger til linjeskift etter utskriften av en komplett sti
    }

    private static void BFSVisit(MovieGraph.ActorNode s,
                                 Map<MovieGraph.ActorNode, Boolean> visited,
                                 Queue<MovieGraph.ActorNode> queue,
                                 Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                                 Map<MovieGraph.ActorNode, MovieGraph.Edge> movieEdge,
                                 MovieGraph.ActorNode goalNode) {
        // Legg til startnode i besøkte noder og køen
        visited.put(s, true);
        queue.add(s);

        // Utfør bredde-først søk
        while (!queue.isEmpty()) {
            MovieGraph.ActorNode current = queue.poll();

            // Hvis vi har nådd målskuespilleren, avslutt
            if (current.equals(goalNode)) {
                return;
            }

            // Iterer gjennom alle naboene (kanter)
            for (MovieGraph.Edge edge : current.edges) {
                MovieGraph.ActorNode neighbor = edge.actor1.equals(current) ? edge.actor2 : edge.actor1;

                // Hvis naboskuespilleren ikke er besøkt, legg til i besøkt, kø, og oppdater sti
                if (!visited.containsKey(neighbor) || !visited.get(neighbor)) {
                    visited.put(neighbor, true);
                    queue.add(neighbor);
                    previous.put(neighbor, current);
                    movieEdge.put(neighbor, edge);
                }
            }
        }

        // Hvis målskuespilleren ikke ble funnet
        if (!visited.containsKey(goalNode) || !visited.get(goalNode)) {
            System.out.println("Målskuespilleren " + goalNode + " ble ikke funnet fra " + s);
        }
    }

    private static void printPath(Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                                  Map<MovieGraph.ActorNode, MovieGraph.Edge> movieEdge,
                                  MovieGraph.ActorNode startNode,
                                  MovieGraph.ActorNode goalNode) {
        if (!previous.containsKey(goalNode)) {
            System.out.println("Ingen sti funnet mellom " + startNode + " og " + goalNode);
            return;
        }

        List<MovieGraph.ActorNode> path = new LinkedList<>();
        List<MovieGraph.Edge> movies = new LinkedList<>();

        // Rekonstruer stien ved å følge 'previous' tilbake fra mål til start
        for (MovieGraph.ActorNode at = goalNode; at != null; at = previous.get(at)) {
            path.add(at);
            if (movieEdge.get(at) != null) {
                movies.add(movieEdge.get(at));
            }
        }

        // Snur listen for å få korrekt rekkefølge fra start til mål
        Collections.reverse(path);
        Collections.reverse(movies);

        if (path.isEmpty() || !path.get(0).equals(startNode)) {
            System.out.println("Noe gikk galt ved rekonstruksjonen av stien.");
            return;
        }

        // Skriv ut stien med skuespillernavn og film det er tilkoblet
        System.out.println(path.get(0).name);

        for (int i = 1; i < path.size(); i++) {
            MovieGraph.Edge edge = movies.get(i - 1);
            MovieGraph.ActorNode actor = path.get(i);
            String movieTitle = MovieGraph.getMovieTitle(edge.ttId);
            System.out.println("===[ " + movieTitle + " (" + edge.rating + ") ] ===> " + actor.name);
        }
    }
}
