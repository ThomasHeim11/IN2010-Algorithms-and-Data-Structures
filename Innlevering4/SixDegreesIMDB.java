import java.util.*;

// Bruker bredde først søk for å finne den korteste veien mellom skuespillerne. 
public class SixDegreesIMDB{
    String moviesFile = "./movies.tsv";
    String actorsFile = "./actors.tsv";
    // Laster fiulmvurderinger og lager en graf av skespillere. 
    Map<String,Double> movieRatings = MovieGraph.loadMovieRatings(moviesFile);
    Map<String, MovieGraph.ActorNode> actorGraph = MovieGraph.loadActorGraph(actorsFile,movieRatings);

     // Finn og skriv ut korteste vei mellom forskjellige par av skuespillere
        kortesteSti(actorGraph, "nm2255973", "nm0000460"); // Donald Glover til Jeremy Irons
        kortesteSti(actorGraph, "nm0424060", "nm8076281"); // Scarlett Johansson til Emma Mackey
        kortesteSti(actorGraph, "nm4689420", "nm0000365"); // Carrie Coon til Julie Delpy
        kortesteSti(actorGraph, "nm0000288", "nm2143282"); // Christian Bale til Lupita Nyong'o
        kortesteSti(actorGraph, "nm0637259", "nm0931324"); // Tuva Novotny til Michael K. Williams
}


public static void kortesteSti(Map<String, MovieGraph.ActorNode> actorGraph, String startId, String goalId){
    // Sjekker om skuespilleren finners i grafen
    if(!actorGraph.containsKey(startId) || !actorGraph.containsKey(goalId)){
        System.out.println("En eller begge skuespillere finnes ikke i grafen");
        return;
    }
    // Variabler for skulespilleren vi går fra og til. 
    MovieGraph.ActorNode startNode = actorGraph.get(startId);
    MovieGraph.ActorNode goalNode = actorGraph.get(goalId);

    // Data-struktur for bredde-først søk.
    Map<MovieGraph.ActorNode,Boolean> visited = new HashMap<>();
    Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
    Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous = new HashMap<>();
    Map<MovieGraph.ActorNode,MovieGraph.Edge> movieEdge = new HashMap<>();

    // Utfører BFS-SØK fra start.
    BFSVisit(actorGraph,startNode,visited,queue,previous,movieEdge,goalNode);
    // Sjekker om målskuespilleren er besøkt
    if(visited.getOrDefault(goalNode, false)){
        //Skriver ut stie hvis den er funnet
        printPath(previous,movieEdge,startNode,goalNode);
    } else{
        System.out.println("Ingen sti funnet mellom " + startId + " og " + goalId);
    } 
}

private static void BFSVisit(Map<String, MovieGraph.ActorNode> actorGraph,
                                 MovieGraph.ActorNode s,
                                 Map<MovieGraph.ActorNode, Boolean> visited,
                                 Queue<MovieGraph.ActorNode> queue,
                                 Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous,
                                 Map<MovieGraph.ActorNode, MovieGraph.Edge> movieEdge,
                                 MovieGraph.ActorNode goalNode) {
    // Legg til startnode i besøkte noder og køen
    visited.put(s,true);
    queue.add(s);

    // Utfør BFS
    while(!queue.isEmpty()){
        MovieGraph.ActorNode current = queue.poll();
        // Hvis vi når målskuspiller så avslutter vi.
        if(current.equals(goalNode)){
            return;
        }
        // Går gjennom alle naboebe(kanter)
        if(!visited.getOrDefault(neighbor, false)){
            visited.put(neighbor, true);
            queue.add(neighbor);
            previous.put(neighbor, current);
            movieEdge.put(neighbor, edge);
        }
    }
}

private static void printPath(Map<MovieGraph.ActorNode, MovieGraph.ActorNode> previous, Map<MovieGraph.ActorNode, MovieGraph.Edge> movieEdge, MovieGraph.ActorNode startNode, MovieGraph.ActorNode goalNode){
    List<MovieGraph.ActorNode> path = new LinkedList<>();
    List<MovieGraph.Edge> movies = new LinkedList<>();

    // Rekonstruerer stien ved å følge previous tilbake fra må til start.
    for(MovieGraph.ActorNode ved = goalNode; ved != null; ved = previous.get(ved)){
        path.add(ved);
        if(movieEdge.get(ved) != null){
            movies.add(movieEdge.get(ved));
        }
    }
    //Snur listen for å få korrekt rekkefølge fra start til målskuespilleren
    Collection.reverse(path);
    Collection.reverse(movies);

    // Skriv ut stien med skuespillernavn og film det er tilkoblet.
    System.out.println(path.get(0).name);
    for(int i = 1; i < path.size(); i++){
        MovieGraph.Edge edge = movies.get(i-1);
        MovieGraph.ActorNode actor = path.get(i);
        System.out.println("===[ " + edge.ttId + " (" + edge.rating + ") ] ===> " + actor.name);
    }

}