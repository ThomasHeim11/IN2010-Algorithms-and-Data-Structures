import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Opggave b) Effektive mengder

// Oppretter en trestruktur for sett.
class TreeNode {
    int x;
    TreeNode left, right;
    int height;  // Høyde av noden            <@ ny kode @>

    public TreeNode(int item) {
        x = item;
        left = right = null;
        height = 1;  // Ny node er i starten et blad (høyde 1)  <@ ny kode @>
    }
}

// Oppretter en rot og størrelse.
class AVLTree {
    private TreeNode root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    // Hjelpemetode for å sjekke høyden til en node.   <@ ny kode @>
    private int height(TreeNode N) {
        if (N == null) {
            return 0;
        } else {
            return N.height;
        }
    }

    // Hjelpemetode for å finne balansefaktoren til en node.  <@ ny kode @>
    private int getBalance(TreeNode N) {
        if (N == null) {
            return 0;
        } else {
            return height(N.left) - height(N.right);
        }
    }

    // Oppdaterer høyden til en node.   <@ ny kode @>
    private void setHeight(TreeNode N) {
        N.height = 1 + Math.max(height(N.left), height(N.right));
    }

    // Venstrerotasjon.   <@ ny kode @>
    private TreeNode leftRotate(TreeNode z) {
        TreeNode y = z.right;
        TreeNode T1 = y.left;

        y.left = z;
        z.right = T1;

        setHeight(z);
        setHeight(y);

        return y;
    }

    // Høyre-rotasjon.   <@ ny kode @>
    private TreeNode rightRotate(TreeNode z) {
        TreeNode y = z.left;
        TreeNode T2 = y.right;

        y.right = z;
        z.left = T2;

        setHeight(z);
        setHeight(y);

        return y;
    }

    // Balanserer en node hvis den er ubalansert.   <@ ny kode @>
    private TreeNode balance(TreeNode v) {
        if (getBalance(v) < -1) {
            if (getBalance(v.right) > 0) {
                v.right = rightRotate(v.right);
            }
            return leftRotate(v);
        }

        if (getBalance(v) > 1) {
            if (getBalance(v.left) < 0) {
                v.left = leftRotate(v.left);
            }
            return rightRotate(v);
        }

        return v;
    }

    // Bruker den rekursive hjelpemetoden for å sette inn et element i settet.
    public void insert(AVLTree set, int x) {
        set.root = insert(set.root, x);
    }

    // Rekursiv hjelpemetode for å sette inn et element i settet.
    private TreeNode insert(TreeNode node, int x) {
        if (node == null) {
            size++;
            return new TreeNode(x);
        }

        if (x < node.x) {
            node.left = insert(node.left, x);
        } else if (x > node.x) {
            node.right = insert(node.right, x);
        } else {
            return node;  // Ingen duplikater
        }

        setHeight(node);      // <@ ny kode @>
        return balance(node); // <@ ny kode @>
    }

    // Bruker den rekursive hjelpemetoden for å fjerne et element fra settet.
    public void remove(AVLTree set, int x) {
        set.root = remove(set.root, x);
    }

    // Rekursiv hjelpemetode for å fjerne et element fra settet.
    private TreeNode remove(TreeNode node, int x) {
        if (node == null) {
            return node;
        }

        if (x < node.x) {
            node.left = remove(node.left, x);
        } else if (x > node.x) {
            node.right = remove(node.right, x);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            }

            node.x = minValue(node.right);
            node.right = remove(node.right, node.x);
        }

        setHeight(node);       // <@ ny kode @>
        return balance(node);  // <@ ny kode @>
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
    public int size(AVLTree set) {
        return size;
    }

    // Bruker den rekursive hjelpemetoden for å sjekke om et element er i settet.
    public boolean contains(AVLTree set, int x) {
        return contains(set.root, x);
    }

    // Hjelpemetode for å sjekke om et element er i settet.
    private boolean contains(TreeNode node, int x) {
        if (node == null) {
            return false;
        }
        if (node.x == x) {
            return true;
        }
        if (x < node.x) {
            return contains(node.left, x);
        }
        return contains(node.right, x);
    }
}

// Leser inn fil og utfører operasjoner på settet.
public class AVLTreeLes {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Bruk: java AVLSetTester <filnavn>");
            return;
        }
        String fileName = args[0];

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int N = Integer.parseInt(reader.readLine());

        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < N; i++) {
            String[] operation = reader.readLine().split(" ");
            String command = operation[0];
            int x;

            if (command.equals("contains")) {
                x = Integer.parseInt(operation[1]);
                System.out.println(avlTree.contains(avlTree, x) ? "true" : "false");
            } else if (command.equals("insert")) {
                x = Integer.parseInt(operation[1]);
                avlTree.insert(avlTree, x);
            } else if (command.equals("remove")) {
                x = Integer.parseInt(operation[1]);
                avlTree.remove(avlTree, x);
            } else if (command.equals("size")) {
                System.out.println(avlTree.size(avlTree));
            }
        }

        reader.close();
    }
}