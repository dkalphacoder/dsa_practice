package datastructures.linkedlist;

public class LinkedList<T> {

    protected Node<T> head;

    /**
     * getters and setters
     */

    public Node<T> getHead() {
        return this.head;
    }

    public void setHead(Node<T> node) {
        this.head = node;
    }

    public static void main(String[] args){
        LinkedList<String> linkedList = new LinkedList<>();
        LinkedList<String> linkedList2 = new LinkedList<>();


        Node<String> n1 = new Node<>("homie");
        linkedList.addLast(n1);
        linkedList.add(new Node<>("10"),0);
        linkedList.add(new Node<>("20"),1);
        linkedList.add(new Node<>("30"),1);
        linkedList.add(new Node<>("40"),2);
        linkedList.remove(n1, true);

        Node<String> n2 = new Node<>("70");
        Node<String> n3 = new Node<>("150");
        linkedList2.addFirst(n2);
        linkedList2.remove(0);
        linkedList2.addFirst(new Node<>("140"));
        linkedList2.addFirst(n3);
        linkedList2.addFirst(new Node<>("50"));
        linkedList2.addLast(new Node<>("250"));
        linkedList2.remove(n3, true);
        linkedList2.remove(n2, false);

        linkedList.traverse();
        System.out.println(linkedList.size(false));
        System.out.println("ll2");
        linkedList2.traverse();

    }

    public void traverse() {

        if(getHead() == null) {
            System.out.println("LinkedList has no nodes.");
            return;
        }

        Node<T> current = getHead();
        while(current!= null) {
            System.out.println("data: " + current.getData() + ", next -> " + current.getNext());
            current = current.getNext();
        }

    }

    public boolean add(Node<T> node, long position) {

        Node<T> current = getHead();
        long currentPosition = 0;
        Node<T> prev = null;

        while(current != null && currentPosition < position) {
            prev = current;
            current = current.getNext();
            currentPosition++;
        }

        if (currentPosition == position) {

            if (prev == null) {
                node.setNext(getHead());
                setHead(node);
                System.out.println("Inserted node: " + node+ " at position: " + position);
                return true;
            }

            node.setNext(prev.getNext());
            prev.setNext(node);
            System.out.println("Inserted node: " + node+ " at position: " + position);
            return true;

        }

        System.out.println("Invalid position given");
        return false;
    }

    public boolean add(Node<T> node, Node<T> prevNode) {
        if (prevNode == null) {
            System.out.println("prev node cant be null");
            return false;
        } else {
            node.setNext(prevNode.getNext());
            prevNode.setNext(node);
            System.out.println("Inserted node: " + node+" after node: "+ prevNode);
            return true;
        }
    }

    public boolean addFirst(Node<T> node) {
        if (node != null) {
            node.setNext(getHead());
            setHead(node);
            System.out.println("Inserted node: "+ node + " at head");
            return true;
        } else {
            System.out.println("Node could not be inserted because it was null");
            return false;
        }
    }

    public boolean addLast(Node<T> node) {

        if (getHead() == null) {
            node.setNext(getHead());
            setHead(node);
            System.out.println("Appended node: "+ node + " at the head");
            return true;
        }

        Node<T> currentNode = getHead();
        Node<T> prevNode = null;
        while (currentNode!= null) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        node.setNext(null);
        prevNode.setNext(node);
        System.out.println("Appended node: "+ node + " at the end");

        return true;
    }

    public boolean remove(Node<T> n, boolean recursive) {

        if (recursive) {
            return deleteNodeRecursiveHelper(getHead(), n, null);
        }

        if (getHead() == null) {
            System.out.println("No head found, linked list is empty");
            return false;
        }

        Node<T> currentNode = getHead();
        Node<T> prevNode = null;
        while (currentNode!= null && !currentNode.equals(n)) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if (currentNode != null && currentNode.equals(n)) {

            if (prevNode == null) {
                setHead(currentNode.getNext());
            } else {
                prevNode.setNext(currentNode.getNext());
            }

            currentNode.setNext(null);
            System.out.println("Deleted node: "+ n);
            return true;

        }

        System.out.println("Could not find node: "+ n);
        return false;
    }

    private boolean deleteNodeRecursiveHelper (Node<T> head, Node<T> n, Node<T> prev) {
        if (head == null) {
            System.out.println("Node n: " + n + "not found");
            return false;
        }

        if (head.equals(n)) {

            if (prev == null) {
                setHead(head.getNext());
            } else {
                prev.setNext(head.getNext());
            }
            head.setNext(null);
            System.out.println("Deleted node: "+ n);
            return true;
        }

        return  deleteNodeRecursiveHelper(head.getNext(), n, head);
    }

    public boolean remove(long position) {

        if(getHead() == null) {
            System.out.println("No head found, linked list is empty");
            return false;
        }

        long currentPosition = 0;
        Node<T> currentNode = getHead();
        Node<T> prevNode = null;
        while (currentNode != null && currentPosition != position) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();

            currentPosition++;
        }

        // if currentNode!= null then currentPosition will be equals to position

        if (currentNode != null &&  currentPosition == position) {

            if (prevNode == null) {
                setHead(getHead().getNext());
            } else {
                prevNode.setNext(currentNode.getNext());
            }

            currentNode.setNext(null);
            System.out.println("Deleted node: "+ currentNode);
            return true;
        }

        System.out.println("Invalid position given, no node found");
        return false;
    }

    public boolean deleteLinkedList() {
        if(getHead() == null) {
            System.out.println("Linked List is already empty");
            return false;
        } else {
            setHead(null);
            System.out.println("Head of the linked list has been set to null");
            return true;
        }
    }

    public long size(boolean recursive) {
        if (recursive) {
            return lengthRecursiveHelper(getHead());
        }

        Node<T> currentNode = getHead();
        long length = 0;

        while (currentNode!=null) {
            currentNode = currentNode.getNext();
            length++;
        }
        return length;

    }

    public long lengthRecursiveHelper(Node<T> head) {

//        if (head == null) {
//            return length;
//        }
//        return lengthRecursiveHelper(head.getNext(), length +1);

        if (head == null) {
            return 0;
        }
        return lengthRecursiveHelper(head.getNext()) + 1;
    }

    public boolean searchElement(T element, boolean recursive) {

        if (recursive) {
            return searchElementRecursiveHelper(element, getHead());
        }

        if (getHead() == null) {
            System.out.println("Linked List is empty. Element not found");
            return false;
        }

        Node<T> currentNode = getHead();
        while (currentNode != null && !currentNode.getData().equals(element)) {
            currentNode = currentNode.getNext();
        }

        if (currentNode != null && currentNode.getData().equals(element)) {
            System.out.println("Found the element: " + element+ " at node: " + currentNode);
            return true;
        }

        System.out.println("Element: " + element + " not found");
        return false;

    }

    public boolean searchElementRecursiveHelper(T element, Node<T> head) {

        if (head == null) {
            System.out.println("Element: " + element + " not found");
            return false;
        }

        if (head.getData().equals(element)) {
            System.out.println("Found the element: " + element+ " at node: " + head);
            return true;
        } else {
            return searchElementRecursiveHelper(element, head.getNext());
        }
    }

    public Node<T> getNth(long position, boolean recursive) {
        if (recursive) {
            return getNthRecursiveHelper(position, getHead());
//            return getNthRecursive(position, 0, getHead());
        }

        if(getHead() == null) {
            System.out.println("Node not found at given position because Linked List is empty");
            assert(false);
            return new Node<T>();
        }

        long currentPosition = 0;
        Node<T> currentNode = getHead();

        while (currentNode != null && currentPosition != position) {
            currentNode = currentNode.getNext();
            currentPosition++;
        }

        // if currentNode!= null then currentPosition will be equals to position

        if (currentNode != null && currentPosition == position) {
            System.out.println("Node was found at given position: " + position);
            return currentNode;
        }

        System.out.println("Node not found at given position: " + position);
        assert(false);
        return new Node<T>();
    }

    public Node<T> getNthRecursiveHelper(long position, Node<T> head) {

//        if (head != null) {
//            if (position == currentPosition) {
//                return head;
//            } else {
//                return getNthRecursive(position, currentPosition + 1, head.getNext());
//            }
//        }

        if (head != null) {
            if (position == 0) {
                return head;
            } else {
                return getNthRecursiveHelper(position - 1, head.getNext());
            }
        }

        System.out.println("Node not found at given position");

        assert(false);
        return new Node<T>();
    }

    @Override
    public String toString() {
        return head.toString();
    }

}