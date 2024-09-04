class InOrder{
    private InOrderNode rot = null;
    private int str = 0;


    protected class InOrderNode{
        protected  int tall;
        protected InOrderNode venstre, høyre;


        public InOrderNode(int tall){
            this.tall = tall;
        }
    }

    public void leggTil(int tall){
        settInn(rot, tall);
    }

    public void settInn(InOrderNode node, int tall){
        if(rot == null){
            rot = new InOrderNode(tall);
            return;
        }
        if(tall < node.tall){
            if(node.venstre == null){
                node.venstre = new InOrderNode(tall);
                return;
            }
            settInn(node.venstre,tall);
        }else if(tall > node.tall){
            if(node.høyre == null){
                node.høyre = new InOrderNode(tall);
                return;
            }
            settInn(node.høyre,tall);
        }
    }
    
    public void print(){
        System.out.println("InOrder: ");
        printInOrder(rot);
        System.out.println("Preorder: ");
        printPreOrder(rot);
        System.out.println("Postorder: ");
        printPostOrder(rot);

    }

    public void printInOrder(InOrderNode node){
        if(node == null){
            return;
        }
        printInOrder(node.venstre);
        System.out.println(node);
        printInOrder(node.høyre);
    }

    public void printPreOrder(InOrderNode node){
        if(node == null){
            return;
        }
        System.out.println(node);
        printPreOrder(node.venstre);
        printPreOrder(node.høyre);
    }

    public void printPostOrder(InOrderNode node){
        if(node == null){
            return;
        }
        printPostOrder(node.venstre);
        printPostOrder(node.høyre);
        System.out.println(node);
    }

}