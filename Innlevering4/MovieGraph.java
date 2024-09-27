import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Klasse som representerer grafen for filmdata.
public class MovieGraph {

    // Node som representerer skuespillere.
    private static class ActorNode {
        String id;
        String name;
        Set<Edge> edges = new HashSet<>();

        ActorNode(String id, String name) {
            this.id = id;
            this.name = name;
        }
        // Legger til kant. 
        void addEdge(Edge edge) {
            edges.add(edge);
        }
    }
    // Egen klasse for kanter. 
    private static class Edge {
        String ttId;
        double rating;
        ActorNode actor1;
        ActorNode actor2;

        Edge(String ttId, double rating, ActorNode actor1, ActorNode actor2) {
            this.ttId = ttId;
            this.rating = rating;
            this.actor1 = actor1;
            this.actor2 = actor2;
        }
        /** 
         * Overstyrer equals-metoden for å definere logikken for likhet mellom to Edge-objekter. 
         * To Edge-objekter anses som like hvis de har samme film-ID (ttId) og samme par av skuespillere
         * (uavhengig av rekkefølgen på skuespillerne).
         */
        @Override
        public boolean equals(Object o) {
            // sjekker om referasen til objektet er den samme. 
            if (this == o) return true;
            // Sjekker om objektet er null eller om de er av forskjellige klasser
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            // Sjekker om film-ID ene er like og om skuespillerparene er like. 
            return ttId.equals(edge.ttId) && (
                    (actor1.equals(edge.actor1) && actor2.equals(edge.actor2)) ||
                    (actor1.equals(edge.actor2) && actor2.equals(edge.actor1)));
        }

        @Override
        public int hashCode() {
            // Kobinerer hashkoder av film-ID og skuespiller-ID er for å generere en konsistent hashkode. 
            return ttId.hashCode() + actor1.id.hashCode() + actor2.id.hashCode();
        }
    }

    public static void main(String[] args) {
        String moviesFile = "./movies.tsv";
        String actorsFile = "./actors.tsv";
    // Les filmene og deres retninger fra filen og lagre i en mappe. 
        Map<String, Double> movieRatings = loadMovieRatings(moviesFile);
        Map<String, ActorNode> actorGraph = loadActorGraph(actorsFile, movieRatings);

        int nodeCount = actorGraph.size();
        int edgeCount = countEdges(actorGraph);

        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + nodeCount);
        System.out.println("Edges: " + edgeCount);
    }
    // Leser fil med filmdata og returnerer en map med ttId og rating
    private static Map<String, Double> loadMovieRatings(String moviesFile) {
        Map<String, Double> movieRatings = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(moviesFile))) {
            String line;
            // Les linje for linje fra skuespilleren. 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                // Hopper over ufulsteningige linjer. 
                if (parts.length < 3) continue;
                String ttId = parts[0];
                double rating = Double.parseDouble(parts[2]);
                //Legger inn i map. 
                movieRatings.put(ttId, rating);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieRatings;
    }
    // Leser fil med skespillerdata og bygger opp en graf. 
    private static Map<String, ActorNode> loadActorGraph(String actorsFile, Map<String, Double> movieRatings) {
        Map<String, ActorNode> actorGraph = new HashMap<>();
        Map<String, Set<String>> movieActorsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(actorsFile))) {
            String line;
            // Leser linje for linje. 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 2) continue;
                String nmId = parts[0];
                String name = parts[1];
                ActorNode actor = new ActorNode(nmId, name);
                // Legger inn i grafen. 
                actorGraph.put(nmId, actor);

                for (int i = 2; i < parts.length; i++) {
                    String ttId = parts[i];
                    // Legger skuespiller til filmen. 
                    movieActorsMap.computeIfAbsent(ttId, k -> new HashSet<>()).add(nmId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Bygger grafen, kobler skuespillere med filmene de deltok i. 
        for (Map.Entry<String, Set<String>> entry : movieActorsMap.entrySet()) {
            // Henter filmens ID (ttId) og sett av skuespillere (actorIds) som deltok i filmen.
            String ttId = entry.getKey();
            Set<String> actorIds = entry.getValue();

            // Sjekker om filmen finnes i movieRatings mappen.
            if (movieRatings.containsKey(ttId)) {
                //Sjekker om filmen finnes i ratingmap. 
                double rating = movieRatings.get(ttId);
                // Konverterer settet med skuespiller ID-er til en array for enklere tilgang.
                String[] actorArray = actorIds.toArray(new String[0]);
                // Går gjennom alle kombinasjoner av par med skuespillere fra filmen. 
                for (int i = 0; i < actorArray.length; i++) {
                    // Henter første skuespiller i parret. 
                    ActorNode actor1 = actorGraph.get(actorArray[i]);
                    // sjekker at skuespilleren finnes i grafen 
                    if (actor1 == null) continue;
                    // nøstet-løkke for å kombinere skuespiller 1 med påfølgende skuespiller. 
                    for (int j = i + 1; j < actorArray.length; j++) {
                        // Henter andre skespiller i parret. 
                        ActorNode actor2 = actorGraph.get(actorArray[j]);
                        // Sjekker om skuespiller finnes i graf. 
                        if (actor2 == null) continue;
                        // Oppretter ny kant for filmen og kobler de to skuespillerne 
                        Edge edge = new Edge(ttId, rating, actor1, actor2);
                        actor1.addEdge(edge);
                        actor2.addEdge(edge);
                    }
                }
            }
        }

        return actorGraph;
    }
    // Teller antall kanter i grafen. 
    private static int countEdges(Map<String, ActorNode> actorGraph) {
        Set<Edge> edges = new HashSet<>();
        for (ActorNode actor : actorGraph.values()) {
            // legger til alle kanter fra hver skuespiller. 
            edges.addAll(actor.edges);
        }
        return edges.size();
    }
}