import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class TreeNode {
    int x;
    TreeNode left, right;
    
    public TreeNode(int item) {
        x = item;
        left = right = null;
    }
}

class SetBST {
    private TreeNode root;
    private int size;
    
    public SetBST() {
        root = null;
        size = 0;
    }
    
    public boolean contains(SetBST set, int x) {
        return contains(set.root, x);
    }
    
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
    
    public void insert(SetBST set, int x) {
        set.root = insert(set.root, x);
    }
    
    private TreeNode insert(TreeNode node, int x) {
        if (node == null) {
            size++;
            return new TreeNode(x);
        }
        if (x < node.x) {
            node.left = insert(node.left, x);
        } else if (x > node.x) {
            node.right = insert(node.right, x);
        }
        return node;
    }
    
    public void remove(SetBST set, int x) {
        set.root = remove(set.root, x);
    }
    
    private TreeNode remove(TreeNode node, int x) {
        if (node == null) {
            return node;
        }
        if (x < node.x) {
            node.left = remove(node.left, x);
            return node;
        }
        if (x > node.x) {
            node.right = remove(node.right, x);
            return node;
        }
        if (node.left == null) {
            size--;
            return node.right;
        }
        if (node.right == null) {
            size--;
            return node.left;
        }
        
        node.x = minValue(node.right);
        node.right = remove(node.right, node.x);
        return node;
    }
    
    private int minValue(TreeNode node) {
        int minv = node.x;
        while (node.left != null) {
            minv = node.left.x;
            node = node.left;
        }
        return minv;
    }
    
    public int size(SetBST set) {
        return size;
    }
}

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