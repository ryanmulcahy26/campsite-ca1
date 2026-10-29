package datastructure;

public class LinkedList<T> {

    public static class Node<T> {
        private T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // add value to the end of the linkedlist
    public void add(T val) {
        Node<T> node = new Node<>(val);
        if (head == null) {   // if linkedlist is empty
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // pass a value to the head of the linkedlist
    public void addFirst(T val) {
        Node<T> node = new Node<>(val);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    public Node<T> getHead(){
        return head;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }

    // check if index is valid, throw an exception if not
    private Node<T> nodeAt(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException(
                    "This index: " + index + "is not valid for this list with the size" + size
            );
        }
        //follow next pointers (index) times over list
        Node<T> current = head;
        for(int i = 0; i<index; i++){
            current = current.next;
        }
        return current;
    }
    // return value in the node
    public T get(int index){
        return nodeAt(index).data;
    }
    // make the list walkable, keep returning true while the value is equal to the one given
    public boolean contains(T val){
        Node<T> current = head;
        while(current !=null){
            if(current.data.equals(val)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //remove method; walks through list while keeping track of prev node
    //changes pointers so lists skips the node you wanted removed
    public boolean remove(T val){
        Node<T> prev = null;
        Node<T> current = head;
        while(current!= null){
            if(current.data.equals(val)){
                if(prev == null){
                    head = current.next; // move head forward if no prev
                }
                else{
                    prev.next = current.next; // skip node
                }
                if(current == tail){
                    tail = prev; // tail moves back to previous
                }
                size --;
                return true; // let user know if removed
            }
            prev = current;
            current = current.next;
        }
        return false; // let user know if removed
    }

    // remove node at a position in the linkedist, return value
    // throw exception if index isn't valid
    public T removeAt(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException(
                    "The index: " + index + "is not valid for a list of size" + size
            );
        }
        if(index == 0){
            T val = head.data;
            head = head.next;
            if(head == null){
                tail = null;
            }
            size --;
            return val;
        }
        Node<T> prev = nodeAt(index - 1);
        Node<T> goal = prev.next;
        prev.next = goal.next;
        if(goal == tail){
            tail = prev; // removed last node
        }
        size --;
        return goal.data;
    }

}
