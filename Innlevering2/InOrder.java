import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class InOrder {
    private InOrderNode rot = null;
    private int str = 0;

    protected class InOrderNode {
        protected int tall;
        protected InOrderNode venstre, høyre;

        public InOrderNode(int tall) {
            this.tall = tall;
        }

        @Override
        public String toString() {
            return String.valueOf(tall);
        }
    }

    public void leggTil(int tall) {
        settInn(rot, tall);
    }

    public void settInn(InOrderNode node, int tall) {
        if (rot == null) {
            rot = new InOrderNode(tall);
            return;
        }
        if (tall < node.tall) {
            if (node.venstre == null) {
                node.venstre = new InOrderNode(tall);
                return;
            }
            settInn(node.venstre, tall);

        } else if (tall > node.tall) {
            if (node.høyre == null) {
                node.høyre = new InOrderNode(tall);
                return;
            }
            settInn(node.høyre, tall);
        }
    }

    public void print() {
        System.out.println("InOrder: ");
        printInOrder(rot);
        System.out.println("Preorder: ");
        printPreOrder(rot);
        System.out.println("Postorder: ");
        printPostOrder(rot);
    }

    public void printInOrder(InOrderNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.venstre);
        System.out.println(node);
        printInOrder(node.høyre);
    }

    public void printPreOrder(InOrderNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        printPreOrder(node.venstre);
        printPreOrder(node.høyre);
    }

    public void printPostOrder(InOrderNode node) {
        if (node == null) {
            return;
        }
        printPostOrder(node.venstre);
        printPostOrder(node.høyre);
        System.out.println(node);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bruk: java InOrder <file path>");
            return;
        }

        String filePath = args[0];
        InOrder bst = new InOrder();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                try {
                    int number = Integer.parseInt(line.trim());
                    bst.leggTil(number);
                } catch (NumberFormatException e) {
                    // Handle the case where the line is not a valid integer
                    System.err.println("Invalid number in file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bst.print();
    }
}