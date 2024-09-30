import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovieGraph {

    protected static Map<String, String> movieTitles = new HashMap<>();

    protected static class ActorNode {
        String id;
        String name;
        Set<Edge> edges = new HashSet<>();

        ActorNode(String id, String name) {
            this.id = id;
            this.name = name;
        }

        void addEdge(Edge edge) {
            edges.add(edge);
        }
    }

    protected static class Edge {
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
    }

    public static void main(String[] args) {
        String moviesFile = "./movies.tsv";
        String actorsFile = "./actors.tsv";

        Map<String, Double> movieRatings = loadMovieRatings(moviesFile);
        Map<String, ActorNode> actorGraph = loadActorGraph(actorsFile, movieRatings);

        int nodeCount = actorGraph.size();
        int edgeCount = countEdges(actorGraph);

        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + nodeCount);
        System.out.println("Edges: " + edgeCount);
    }

    protected static Map<String, Double> loadMovieRatings(String moviesFile) {
        Map<String, Double> movieRatings = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(moviesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 3) continue;
                String ttId = parts[0];
                String title = parts[1];
                double rating = Double.parseDouble(parts[2]);
                movieRatings.put(ttId, rating);
                movieTitles.put(ttId, title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieRatings;
    }

    protected static Map<String, ActorNode> loadActorGraph(String actorsFile, Map<String, Double> movieRatings) {
        Map<String, ActorNode> actorGraph = new HashMap<>();
        Map<String, Set<String>> movieActorsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(actorsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 2) continue;
                String nmId = parts[0];
                String name = parts[1];
                ActorNode actor = new ActorNode(nmId, name);
                actorGraph.put(nmId, actor);

                for (int i = 2; i < parts.length; i++) {
                    String ttId = parts[i];
                    if (!movieActorsMap.containsKey(ttId)) {
                        movieActorsMap.put(ttId, new HashSet<>());
                    }
                    movieActorsMap.get(ttId).add(nmId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Set<String>> entry : movieActorsMap.entrySet()) {
            String ttId = entry.getKey();
            Set<String> actorIds = entry.getValue();

            if (movieRatings.containsKey(ttId)) {
                double rating = movieRatings.get(ttId);
                String[] actorArray = actorIds.toArray(new String[0]);
                for (int i = 0; i < actorArray.length; i++) {
                    ActorNode actor1 = actorGraph.get(actorArray[i]);
                    if (actor1 == null) continue;
                    for (int j = i + 1; j < actorArray.length; j++) {
                        ActorNode actor2 = actorGraph.get(actorArray[j]);
                        if (actor2 == null) continue;

                        Edge edge = new Edge(ttId, rating, actor1, actor2);

                        actor1.addEdge(edge);
                        actor2.addEdge(edge);
                    }
                }
            }
        }

        return actorGraph;
    }

    protected static int countEdges(Map<String, ActorNode> actorGraph) {
        Set<Edge> edges = new HashSet<>();
        for (ActorNode actor : actorGraph.values()) {
            edges.addAll(actor.edges);
        }
        return edges.size();
    }

    public static String getMovieTitle(String ttId) {
        return movieTitles.getOrDefault(ttId, "Unknown Title");
    }
}
