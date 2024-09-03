import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Oppretter en trestruktur for sett.
class TreeNode {
    int x;
    TreeNode left, right;
    
    public TreeNode(int item) {
        x = item;
        left = right = null;
    }
}
// Oppretter en rot og størrelse. 
class SetBST {
    private TreeNode root;
    private int size;
    
    public SetBST() {
        root = null;
        size = 0;
    }
    // Bruk
    public boolean contains(SetBST set, int x) {
        return contains(set.root, x);
    }
    // Hjelpemetode for å sjekke om et element er i settet.
    private boolean contains(TreeNode node, int x) {
        // Basistilfelle. 
        if (node == null) {
            return false;
        }
        // Hvis vi har funnet elementet.
        if (node.x == x) {
            return true;
        } 
        // Hvis elementet er mindre enn noden, gå til venstre.
        if (x < node.x) {
            return contains(node.left, x);
        } 
        // Hvis elementet er større enn noden, gå til høyre.
        return contains(node.right, x);
    }
    // Bruker den rekursive hjelpemetoden for å sette inn et element i settet.
    public void insert(SetBST set, int x) {
        set.root = insert(set.root, x);
    }
    // Rekursiv hjelpemetode for å sette inn et element i settet.
    private TreeNode insert(TreeNode node, int x) {
        // Basistilfelle.
        if (node == null) {
            size++;
            return new TreeNode(x);
        }
        // Hvis elementet er mindre enn noden, gå til venstre.
        if (x < node.x) {
            node.left = insert(node.left, x);
            // Hvis elementet er større enn noden, gå til høyre.
        } else if (x > node.x) {
            node.right = insert(node.right, x);
        }
        return node;
    }
    // Bruker den rekursive hjelpemetoden for å fjerne et element fra settet.
    public void remove(SetBST set, int x) {
        set.root = remove(set.root, x);
    }
    // Rekursiv hjelpemetode for å fjerne et element fra settet.
    private TreeNode remove(TreeNode node, int x) {
        // Basistilfelle.
        if (node == null) {
            return node;
        }
        // Hvis elementet er mindre enn noden, gå til venstre.
        if (x < node.x) {
            node.left = remove(node.left, x);
            return node;
        }
        // Hvsi elementet er større enn noden, gå til høyre.
        if (x > node.x) {
            node.right = remove(node.right, x);
            return node;
        }
        // Hvis noden har ett eller ingen barn.
        if (node.left == null) {
            size--;
            return node.right;
        }
        // Hvis noden har ett eller ingen barn.
        if (node.right == null) {
            size--;
            return node.left;
        }
        // Hvis noden har to barn.
        node.x = minValue(node.right);
        node.right = remove(node.right, node.x);
        return node;
    }
    // Hjelpemetode for å finne minste verdi i et tre.
    private int minValue(TreeNode node) {
        int minv = node.x;
        while (node.left != null) {
            minv = node.left.x;
            node = node.left;
        }
        return minv;
    }
    // Henter størrelsen på settet.
    public int size(SetBST set) {
        return size;
    }
}

// Leser inn fil og utfører operasjoner på settet.
public class SetTree {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Bruk: java SetTree <filnavn>");
            return;
        }
        String fileName = args[0];

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int N = Integer.parseInt(reader.readLine());

        SetBST setBST = new SetBST();

        for (int i = 0; i < N; i++) {
            String[] operation = reader.readLine().split(" ");
            String command = operation[0];
            int x;

            if (command.equals("contains")) {
                x = Integer.parseInt(operation[1]);
                System.out.println(setBST.contains(setBST, x) ? "true" : "false");
            } else if (command.equals("insert")) {
                x = Integer.parseInt(operation[1]);
                setBST.insert(setBST, x);
            } else if (command.equals("remove")) {
                x = Integer.parseInt(operation[1]);
                setBST.remove(setBST, x);
            } else if (command.equals("size")) {
                System.out.println(setBST.size(setBST));
            }
        }

        reader.close();
    }
}