import java.io.IOException;
import java.util.*;
// Jeg bruker Dijkstra for å finne chilleste vei. 
public class Chillestevei{
    public static void finnChillesteVei(MovieGraph movieGraph, String startSkuespillerID,String målSkuespillerID){
        // Mål og startnode fra grafen. 
        MovieGraph.ActorNode startNode = movieGraph.getActorNode(startSkuespillerID);
        MovieGraph.ActorNode målNode = movieGraph.getActorNode(målSkuespillerID);

        if(startNode == null || målNode == null){
            System.out.println("Skespiller ikke funnet");
            return;
        }
        // En prioritetskø for å ta hånd av noder som sakl behandles basert på avstand. 
        PriorityQueue<SkuespillerNodeWrapper> prioritetskø = new PriorityQueue<>(new SkuespillerNodeComparator());
        //Holder styr på korteste avstand mellom nodene. 
        Map<MovieGraph.ActorNode,Double> avstander = new HashMap<>();
        // Veien fra tidligere noder. 
        Map<MovieGraph.ActorNode,MovieGraph.Edge> tidligere = new HashMap<>();

        // Initialiserer alle avstander til uendelig. 
        for(MovieGraph.ActorNode skuespiller: movieGraph.getAllActorNodes()){
            avstander.put(skuespiller,Double.MAX_VALUE);
        }
        // Avtanden til startnoden settes til 0. 
        avstander.put(startNode,0.0);
        // Legger startnoden i prioritetskøen. 
        prioritetskø.add(new SkuespillerNodeWrapper(startNode,0.0));

        // Så lenge prioritetskøa ikke er tom. 
        while(!prioritetskø.isEmpty()){
            // Behandler nodeen med den minste avstanden. 
            SkuespillerNodeWrapper nåværendeWrapper = prioritetskø.poll();
            MovieGraph.ActorNode nåværendeNode = nåværendeWrapper.node;

            if (nåværendeNode.equals(målNode)) break;

            for(MovieGraph.Movie film: nåværendeNode.movies){
                for(MovieGraph.ActorNode nabo : film.actors){
                    if(nåværendeNode.equals(nabo)) continue;

                    double nyAvstand = avstander.get(nåværendeNode) + (10-film.rating);
                    if(nyAvstand < avstander.get(nabo)){
                        avstander.put(nabo, nyAvstand);
                        tidligere.put(nabo, new MovieGraph.Edge(nåværendeNode,nabo,film.title,film.rating));
                        prioritetskø.add(new SkuespillerNodeWrapper(nabo,nyAvstand));
                    }
                }
            }
        }
        if(tidligere.containsKey(målNode)){
            double totalVekt = avstander.get(målNode);
            skrivUtChillesteVei(startNode,målNode,tidligere,totalVekt);
        } else{
            System.out.println("Ingen sti funnet mellom " + startNode.name + " og " + målNode.name);
        }
    }
    static class SkuespillerNodeWrapper{
        MovieGraph.ActorNode node;
        double avstand;

        SkuespillerNodeWrapper(MovieGraph.ActorNode node, double avstand){
            this.node = node;
            this.avstand = avstand;
        }
    }
    static  class SkuespillerNodeComparator implements Comparator<SkuespillerNodeWrapper>{
        @Override
        public int compare(SkuespillerNodeWrapper o1, SkuespillerNodeWrapper o2){
            return Double.compare(o1.avstand, o2.avstand);
        }
    }
        private static void skrivUtChillesteVei(MovieGraph.ActorNode startNode, MovieGraph.ActorNode målNode,
                                            Map<MovieGraph.ActorNode, MovieGraph.Edge> tidligere, double totalVekt) {
            List<String> sti = new ArrayList<>();
            MovieGraph.ActorNode nåværende = målNode;
            
            while(!nåværende.id.equals(startNode.id)){
                MovieGraph.Edge kant = tidligere.get(nåværende);
                sti.add("===[ " + kant.movieTitle + " (" + kant.rating + ") ] ===> " + nåværende.name);
                nåværende = kant.actor1;
            }
            sti.add(startNode.name);
            Collections.reverse(sti);

            for(String steg : sti){
                System.out.println(steg);
            }
            System.out.println("Total vekt: " + totalVekt);
}
    public static void main(String[] args) {
        MovieGraph movieGraph = new MovieGraph();
        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String[][] spørringer = {
            {"nm2255973", "nm0000460", "Donald Glover", "Jeremy Irons"},
            {"nm0424060", "nm8076281", "Scarlett Johansson", "Emma Mackey"},
            {"nm4689420", "nm0000365", "Carrie Coon", "Julie Delpy"},
            {"nm0000288", "nm2143282", "Christian Bale", "Lupita Nyong'o"},
            {"nm0637259", "nm0931324", "Tuva Novotny", "Michael K. Williams"}
        };

        System.out.println("Oppgave 3");
        for (String[] spørring : spørringer) {
            System.out.println(spørring[2] + " til " + spørring[3] + ":");
            finnChillesteVei(movieGraph, spørring[0], spørring[1]);
            System.out.println();
        }
    }
}