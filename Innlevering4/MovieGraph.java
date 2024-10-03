import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieGraph {
    protected Map<String, ActorNode> actors;
    protected Map<String, Movie> movies;

    // Konstruktør 
    public MovieGraph() {
        actors = new HashMap<>();
        movies = new HashMap<>();
    }
    // Henter skuespillenode. 
    public ActorNode getActorNode(String actorID) {
        return actors.get(actorID);
    }
    // Henter en sammling av alle nodene av skuespillere. 
    public Collection<ActorNode> getAllActorNodes() {
        return actors.values();
    }
    // Legger til skuspillere. 
    public void addActor(String actorID, String actorName) {
        if (!actors.containsKey(actorID)) {
            actors.put(actorID, new ActorNode(actorID, actorName));
        }
    }
    // Legger til filmer. 
    public void addMovie(String movieID, String title, double rating) {
        if (!movies.containsKey(movieID)) {
            movies.put(movieID, new Movie(movieID, title, rating));
        }
    }
    // Legger til skuespiller til film. 
    public void addActorToMovie(String actorID, String movieID) {
        // Henter film med gitt id. 
        Movie movie = movies.get(movieID);
        // Hentr skuespiller basert på id. 
        ActorNode actor = actors.get(actorID);
        // Hvis vi har en film og en skespiller. 
        if (movie != null && actor != null) {
            actor.movies.add(movie);
            movie.actors.add(actor);
        }
    }
    // Bygger grafen. 
    public void buildGraph(String moviesFile, String actorsFile) throws IOException {
        // Les filmer
        try (BufferedReader br = new BufferedReader(new FileReader(moviesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                String movieID = fields[0];
                String title = fields[1];
                // Henter rating. 
                double rating = Double.parseDouble(fields[2]);
                addMovie(movieID, title, rating);
            }
        }

        // Les skuespillere
        try (BufferedReader br = new BufferedReader(new FileReader(actorsFile))) {
            String line;
            // Leser hver linje og henter skuespiller. 
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                String actorID = fields[0];
                String actorName = fields[1];
                addActor(actorID, actorName);

                for (int i = 2; i < fields.length; i++) {
                    String movieID = fields[i];
                    if (movies.containsKey(movieID)) {
                        addActorToMovie(actorID, movieID);
                    }
                }
            }
        }
    }
    // Teller antall kanter. 
    public int countEdges() {
        int edges = 0;
        // Går gjennom alle filmene. 
        for (Movie movie : movies.values()) {
            // Antall skespillere i en film. 
            int actorCount = movie.actors.size();
            // For hver skiespiller, beregn potensielle kanter.
            edges += actorCount * (actorCount - 1); 
        }
        // Deler med 2 siden hver kant telles to ganger. 
        return edges / 2; 
    }

    // Indre klasser for skulespiller
    static class ActorNode {
        String id;
        String name;
        List<Movie> movies;

        ActorNode(String id, String name) {
            this.id = id;
            this.name = name;
            this.movies = new ArrayList<>();
        }
    }
    // Inger node for film. 
    static class Movie {
        String id;
        String title;
        double rating;
        List<ActorNode> actors;

        Movie(String id, String title, double rating) {
            this.id = id;
            this.title = title;
            this.rating = rating;
            this.actors = new ArrayList<>();
        }
    }
    // Indre klasse av kant
    static class Edge {
        ActorNode actor1;
        ActorNode actor2;
        String movieTitle;
        double rating;

        Edge(ActorNode actor1, ActorNode actor2, String movieTitle, double rating) {
            this.actor1 = actor1;
            this.actor2 = actor2;
            this.movieTitle = movieTitle;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return actor1.name + " <===[ " + movieTitle + " (" + rating + ") ]===> " + actor2.name;
        }
    }
    // Hovedprogram som skriver ut antall noder og kanter. 
    public static void main(String[] args) {
        // Oppretter en graf og leser inn filene. 
        MovieGraph movieGraph = new MovieGraph();

        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Oppgave 1");
        System.out.println("Nodes: " + movieGraph.actors.size());
        System.out.println("Edges: " + movieGraph.countEdges());
    }
}