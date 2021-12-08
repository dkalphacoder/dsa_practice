package datastructures.disjointset;

import java.util.HashMap;

public class DisjointSet<E> {
    protected HashMap<E, Node<E>> elements;

    public HashMap<E, Node<E>> getElements() {
        return elements;
    }

    public void setElements(HashMap<E, Node<E>> elements) {
        this.elements = elements;
    }


    public DisjointSet() {
        setElements(new HashMap<>());
    }

    public DisjointSet(HashMap<E, Node<E>> elements) {
        setElements(elements);
    }

    public static void main(String[] args) {
        DisjointSet<Integer> djs = new DisjointSet<>();
        for (int i = 1; i < 8; i++) {
            djs.getElements().put(i, djs.makeSet(i));
        }

//        System.out.println(djs.getElements());

        djs.unionByRank(1,2);
        djs.unionByRank(2,3);
        djs.unionByRank(4,5);
        djs.unionByRank(6,7);
        djs.unionByRank(5,6);
        djs.unionByRank(3,7);

        System.out.println(djs.getElements());

        djs.getElements().clear();

        for (int i = 1; i < 8; i++) {
            djs.getElements().put(i, djs.makeSet(i));
        }

//        System.out.println(djs.getElements());

        djs.unionBySize(1,2);
        djs.unionBySize(2,3);
        djs.unionBySize(4,5);
        djs.unionBySize(6,7);
        djs.unionBySize(5,6);
        djs.unionBySize(3,7);

        System.out.println(djs.getElements());

    }

    public Node<E> makeSet(E elem) {
        return new Node<>(elem);
    }

    public E find (E element) {
        return find(elements.get(element)).getData();
    }

    public Node<E> find (Node<E> node) {
        if (node.getParent().equals(node.getData())) {
            return node;
        }

        return find(elements.get(node.getParent()));
    }

    public Node<E> findPathCompression (Node<E> node) {
        if (node.getParent().equals(node.getData())) {
            return node;
        }

        Node<E> result = findPathCompression(elements.get(node.getParent()));
        node.setParent(result.getData());
        return result;
    }

    public void unionByRank(E elem1, E elem2) {
        Node<E> n1 = elements.get(elem1);
        Node<E> n2 = elements.get(elem2);

        Node<E> rep1 = findPathCompression(n1);
        Node<E> rep2 = findPathCompression(n2);

        if (rep1.equals(rep2)) {
            return;
        }

        if (rep1.getRank() < rep2.getRank()) {

            rep1.setParent(rep2.getData());       //set parent here
            rep1.setRank(0);                      //set loser's rank

            rep2.setSize(rep1.getSize() + rep2.getSize());          //set sizes for parent and loser
            rep1.setSize(1);
        } else if (rep1.getRank() > rep2.getRank()) {

            rep2.setParent(rep1.getData());
            rep2.setRank(0);

            rep1.setSize(rep1.getSize() + rep2.getSize());
            rep2.setSize(1);
        } else {

            rep2.setParent(rep1.getData());
            rep2.setRank(0);
            int rep1Rank = rep1.getRank();
            rep1.setRank(rep1Rank + 1);                 //increment parent's rank

            rep1.setSize(rep1.getSize() + rep2.getSize());
            rep2.setSize(1);
        }
    }

    public void unionBySize(E elem1, E elem2) {
        Node<E> n1 = elements.get(elem1);
        Node<E> n2 = elements.get(elem2);

        Node<E> rep1 = findPathCompression(n1);
        Node<E> rep2 = findPathCompression(n2);

        if (rep1.equals(rep2)) {
            return;
        }

        if (rep1.getSize() < rep2.getSize()) {

            rep1.setParent(rep2.getData());                         //set parent here
            rep2.setSize(rep1.getSize() + rep2.getSize());          //set sizes for parent and loser
            rep1.setSize(1);
        } else {

            rep2.setParent(rep1.getData());
            rep1.setSize(rep1.getSize() + rep2.getSize());
            rep2.setSize(1);
        }
    }
}
