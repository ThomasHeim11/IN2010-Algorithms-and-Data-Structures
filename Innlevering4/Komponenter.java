import java.io.IOException;
import java.util.*;

// Hovedklasse for å finne størrelsen på komponenter i en urettet graf
public class Komponenter {

    public static void main(String[] args) {
        // Oppretter en ny instans av MovieGraph
        MovieGraph movieGraph = new MovieGraph();

        // Bygger grafen ved å lese data fra oppgitte filer
        try {
            movieGraph.buildGraph("movies.tsv", "actors.tsv");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Kaller metode for å finne komponentstørrelser
        finnKomponenter(movieGraph);
    }

    // Metoden for å finne og telle komponenter av ulike størrelser i grafen
    public static void finnKomponenter(MovieGraph movieGraph) {
        // Sett for å holde styr på besøkte noder
        Set<MovieGraph.ActorNode> besøkede = new HashSet<>();
        // Kart for å holde styr på antall komponenter av hver størrelse
        Map<Integer, Integer> komponentStørrelser = new HashMap<>();

        // Går gjennom alle skuespillernoder i grafen
        for (MovieGraph.ActorNode node : movieGraph.getAllActorNodes()) {
            // Hvis noden ikke er besøkt, utfører vi en bredde-først søk (BFS) fra denne noden
            if (!besøkede.contains(node)) {
                int størrelse = bfs(node, besøkede);
                // Oppdater kartet med størrelsen på komponenten
                if (komponentStørrelser.containsKey(størrelse)) {
                    komponentStørrelser.put(størrelse, komponentStørrelser.get(størrelse) + 1);
                } else {
                    komponentStørrelser.put(størrelse, 1);
                }
            }
        }

        // Skriver ut komponentstørrelser
        for (Map.Entry<Integer, Integer> entry : komponentStørrelser.entrySet()) {
            System.out.println("There are " + entry.getValue() + " components of size " + entry.getKey());
        }
    }

    // Bredde-først søk (BFS) for å finne størrelsen på en komponent
    public static int bfs(MovieGraph.ActorNode startNode, Set<MovieGraph.ActorNode> besøkede) {
        // Kø for BFS
        Queue<MovieGraph.ActorNode> queue = new LinkedList<>();
        // Legger startnoden til køen og markerer den som besøkt
        queue.add(startNode);
        besøkede.add(startNode);

        // Variabel for å holde styr på størrelsen på komponenten
        int størrelse = 0;

        // Utfører BFS
        while (!queue.isEmpty()) {
            MovieGraph.ActorNode node = queue.poll();
            størrelse++;

            // Går gjennom alle filmer skuespilleren er med i
            for (MovieGraph.Movie film : node.movies) {
                // Går gjennom alle med-skuespillere i filmen
                for (MovieGraph.ActorNode nabo : film.actors) {
                    // Hvis naboen ikke er besøkt, markerer vi den som besøkt og legger den til køen
                    if (!besøkede.contains(nabo)) {
                        besøkede.add(nabo);
                        queue.add(nabo);
                    }
                }
            }
        }

        // Returnerer størrelsen på komponenten funnet
        return størrelse;
    }
}