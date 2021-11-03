package datastructures.stack;

import java.util.ArrayList;

public class StackALR<E> {

    protected ArrayList<E> elements;

    public ArrayList<E> getElements() {
        return elements;
    }

    public void setElements(ArrayList<E> elements) {
        this.elements = elements;
    }

    public StackALR() {
        setElements(new ArrayList<>());
    }

    public static void main(String[] args) {
        StackALR<Integer> s = new StackALR<>();

        s.push(1);
        System.out.println(s.peek());
        s.push(2);
        System.out.println(s.peek());
        s.push(3);
        s.push(45);
        s.push(4);
        s.traverseStack();
        s.pop();
        s.pop();
        s.pop();
        System.out.println("Size of the stack: " + s.size());
        s.traverseStack();
        s.pop();
        s.pop();
        s.traverseStack();
    }

    public boolean empty() {
        return getElements().isEmpty();
    }

    public E push(E e) {
        elements.add(0, e);
        return e;
    }

    public E pop() {
        return elements.remove(0);
    }

    public E peek() {
        return elements.get(0);
    }

    public void traverseStack() {
        if (this.empty()) {
            System.out.println("No elements in stack. It is empty.");
            return;
        }
        System.out.println(getElements());
//        for (E e : getElements()) {
//            System.out.println(e);
//        }
    }

    public int size() {
        return elements.size();
    }


}
