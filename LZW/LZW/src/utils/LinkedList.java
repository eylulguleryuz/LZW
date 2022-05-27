package utils;

import java.util.Iterator;

/*
Implement all interface methods based on a linked list.
Do not use the java class LinkedList <E>, try to write all the logic yourself.
Additional methods and variables can be developed as needed.
*/
public class LinkedList<T> implements List<T> {
    private static class LinkedListNode<T> {

        private T data;

        private LinkedListNode next;

        public LinkedListNode(T data) {
            this.data = data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }

        public void setNext(LinkedListNode next) {
            this.next = next;
        }

        public LinkedListNode getNext() {
            return this.next;
        }
    }

    private int length = 0;

    private LinkedListNode head;
    private LinkedListNode tail;

    public LinkedList() {
        this.length = 0;
    }

    @Override
    public void add(T item) {
        LinkedListNode add = new LinkedListNode<T>(item);
        if (head != null)
        {
            tail.next = add;
        }
        else
        {
            head= add;
        }
        tail = add;
        length++;
    }

    public void insert(T data) {
        if (head == null) {
            head = new LinkedListNode(data);

        } else {
            LinkedListNode temp = new LinkedListNode(data);
            temp.next = head;
            head = temp;
        }
        length++;
    }


    /*boolean	retainAll(LinkedList<?> c)
	Retains only the elements in this list that are contained in the specified collection.
*/
    public boolean retainAll(LinkedList<?> c) {
        if(c.length == 0)
        {
            throw new NullPointerException("Linked list is empty");
        }
        Iterator iterator = iterator();

        while(iterator.hasNext())
        {
            var next = iterator.next();
            if(!c.contains(next))
            {
                remove((T) next);
                return true;
            }
        }
        return false;
    }
    private boolean contains(Object data)
    {
        for (int i = 0; i < this.length; i++) {
            if (data == get(i))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {

        LinkedListNode currentNode = this.head;
        int begin = 0;
        while (begin <= index) {
            if (begin == index)
                return (T) currentNode.getData();
            begin++;
            currentNode = currentNode.next;
        }
        return null;

    }
    public int size()
    {
        return length;
    }
    @Override
    public boolean remove(T item) {

        LinkedListNode temp = head, previous = null;
        if (temp != null && item.equals(temp.data))
        {
            head = temp.next;
            tail= temp.next;
            length--;
            return true;
        }
        while (temp != null && !item.equals(temp.data))
        {
            previous = temp;
            temp = temp.next;
        }
        if (temp == null) return false;
        previous.next = temp.next;
        length--;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return get(index++);
            }
        };
    }
}
