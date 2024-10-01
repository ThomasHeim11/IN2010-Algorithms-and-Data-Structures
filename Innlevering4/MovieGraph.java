import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieGraph {
    protected  Map<String, ActorNode> actors;
    protected  Map<String, Movie> movies;
    protected  List<Edge> edges;

    // Constructor
    public MovieGraph() {
        actors = new HashMap<>();
        movies = new HashMap<>();
        edges = new ArrayList<>();
    }

    public ActorNode getActorNode(String actorID) {
        return actors.get(actorID);
    }

    public Collection<ActorNode> getAllActorNodes() {
        return actors.values();
    }

    public void addActor(String actorID, String actorName) {
        if (!actors.containsKey(actorID)) {
            actors.put(actorID, new ActorNode(actorID, actorName));
        }
    }

    public void addMovie(String movieID, String title, double rating) {
        if (!movies.containsKey(movieID)) {
            movies.put(movieID, new Movie(movieID, title, rating));
        }
    }

    public void addActorToMovie(String actorID, String movieID) {
        Movie movie = movies.get(movieID);
        ActorNode actor = actors.get(actorID);

        if (movie != null && actor != null) {
            actor.movies.add(movie);
            movie.actors.add(actor);

        // @audit Lager kanter mellom alle skuespillere i filmene.
        for(ActorNode coActor: movie.actors){
            if(!coActor.id.equals(actorID)){
                edges.add(new Edge(actor,coActor,movie.title,movie.rating));
            }
        }
    }
}

    public int countNodes() {
        return actors.size();
    }
    //@audit
    public int countEdges() {
        return edges.size();
    }
    
    public void buildGraph(String moviesFile, String actorsFile) throws IOException {
        // Read movies
        try (BufferedReader br = new BufferedReader(new FileReader(moviesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                String movieID = fields[0];
                String title = fields[1];
                double rating = Double.parseDouble(fields[2]);
                addMovie(movieID, title, rating);
            }
        }

        // Read actors
        try (BufferedReader br = new BufferedReader(new FileReader(actorsFile))) {
            String line;
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

    // Inner classes for Actor and Movie
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
    static  class Edge{
        ActorNode actor1;
        ActorNode actor2;
        String movieTitle;
        double rating;

        Edge(ActorNode actor1, ActorNode actor2, String moiveTitle,double rating){
            this.actor1 = actor1;
            this.actor2 = actor2;
            this.movieTitle = movieTitle;
            this.rating = rating;
        }
    }

    public static void main(String[] args) {
        MovieGraph movieGraph = new MovieGraph();

        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int nodeCount = movieGraph.countNodes();
        int edgeCount = movieGraph.countEdges();

        System.out.println("Antall noder: " + nodeCount);
        System.out.println("Antall kanter: " + edgeCount);
    }
}
