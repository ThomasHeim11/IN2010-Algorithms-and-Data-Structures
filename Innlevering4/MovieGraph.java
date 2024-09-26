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
        // Set med kanter for skuespiller (filmer)
        Set<Edge> edges = new HashSet<>();

        ActorNode(String id, String name) {
            this.id = id;
            this.name = name;
        }

        // Legger film til skuespiller.
        void addEdge(Edge edge) {
            edges.add(edge);
        }
    }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return actor1.equals(edge.actor1) && actor2.equals(edge.actor2) && ttId.equals(edge.ttId);
        }

        @Override
        public int hashCode() {
            return actor1.id.hashCode() + actor2.id.hashCode() + ttId.hashCode();
        }
    }

    public static void main(String[] args) {
        // Stien til filene som tar utgangspunkt i at testfilene er i samme mappe.
        String moviesFile = "./movies.tsv";
        String actorsFile = "./actors.tsv";

        // Laster inn filmratingene fra movies.tsv
        Map<String, Double> movieRatings = loadMovieRatings(moviesFile);
        // Laster inn skuespillergrafen
        Map<String, ActorNode> actorGraph = loadActorGraph(actorsFile, movieRatings);

        // Teller antall skuespillere
        int nodeCount = actorGraph.size();
        // Teller antall filmer
        int edgeCount = countEdges(actorGraph);

        // Skriver ut resultatene
        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + nodeCount);
        System.out.println("Edges: " + edgeCount);
    }

    // Metode for å laste inn filmratings
    private static Map<String, Double> loadMovieRatings(String moviesFile) {
        Map<String, Double> movieRatings = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(moviesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Deler linjen
                if (parts.length < 3) continue; // Hopper over linjer med færre enn tre kolonner
                String ttId = parts[0]; // Filmens tt-ID
                double rating = Double.parseDouble(parts[2]); // Filmens rating
                movieRatings.put(ttId, rating); // Legger tt-ID og rating til i movieRatings
            }
        } catch (IOException e) {
            e.printStackTrace(); // Håndterer feil ved fillesing
        }
        return movieRatings; // Returnerer kartet med filmratings
    }

    // Metode for å laste inn skuespillergrafen fra actors.tsv
    private static Map<String, ActorNode> loadActorGraph(String actorsFile, Map<String, Double> movieRatings) {
        Map<String, ActorNode> actorGraph = new HashMap<>();
        // Map for å gruppere skuespillere etter filmer
        Map<String, Set<String>> movieActorsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(actorsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Deler linjen opp
                if (parts.length < 2) continue; // Hopper over linjer med færre enn to kolonner
                String nmId = parts[0]; // Skuespillerens nm-ID
                String name = parts[1]; // Skuespillerens navn
                ActorNode actor = new ActorNode(nmId, name); // Oppretter en node for skuespilleren

                // Legger skuespilleren til i grafen
                actorGraph.put(nmId, actor);

                // For hver film skuespilleren har vært med i
                for (int i = 2; i < parts.length; i++) {
                    String ttId = parts[i];
                    movieActorsMap.computeIfAbsent(ttId, k -> new HashSet<>()).add(nmId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Håndterer feil ved fillesing
        }

        // Nå som vi har gruppert skuespillerne etter filmer, kan vi opprette kanter
        for (Map.Entry<String, Set<String>> entry : movieActorsMap.entrySet()) {
            String ttId = entry.getKey();
            Set<String> actorIds = entry.getValue();
            if (movieRatings.containsKey(ttId)) {
                double rating = movieRatings.get(ttId);
                // Opprett kanter mellom alle par av skuespillere i den samme filmen
                for (String actorId1 : actorIds) {
                    for (String actorId2 : actorIds) {
                        if (!actorId1.equals(actorId2)) {
                            if (actorId1.compareTo(actorId2) < 0) {
                                ActorNode actor1 = actorGraph.get(actorId1);
                                ActorNode actor2 = actorGraph.get(actorId2);
                                if (actor1 != null && actor2 != null) {
                                    Edge edge = new Edge(ttId, rating, actor1, actor2);
                                    actor1.addEdge(edge);
                                    actor2.addEdge(edge);
                                }
                            }
                        }
                    }
                }
            }
        }

        return actorGraph; // Returnerer skuespillergrafen
    }

    // Metode for å telle antall kanter i grafen
    private static int countEdges(Map<String, ActorNode> actorGraph) {
        // Bruker et Set for å unngå duplikate kanter
        Set<Edge> edges = new HashSet<>();
        for (ActorNode actor : actorGraph.values()) {
            edges.addAll(actor.edges); // Legger til alle kanter for hver skuespiller
        }
        return edges.size(); // Returnerer antall kanter
    }
}