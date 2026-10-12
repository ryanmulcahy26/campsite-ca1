package models;

public class LinkedList<T> {
    private Node <T> head;
    private int size;

    public void addFirst(T Value){
        Node<T> node = new Node<>(Value);
        node.next = head;
        head = node;
        size ++;
    }

    public int size(){
        return size;
    }
}
