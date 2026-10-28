package models;

public class LinkedList<T> {

    public static class Node<T>{
        private T data;
        private Node<T> next;

        private Node(T data){
            this.data = data;
        }

        public T getData(){
            return data;
        }

        public Node<T> getNext(){
            return next;
        }

    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // add value to the end of the linkedlist
    public void add(T value){
        Node<T> node = new Node<>(value);
        if(head==null){
            head = node;
            tail = node;
        }
        else{
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // pass a value to the head of the linkedlist
    public void addFirst(T value){
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if(tail == null){
            tail = node;
        }
        size ++;
    }
    
