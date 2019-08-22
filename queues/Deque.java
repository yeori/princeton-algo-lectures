
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @param <Item>
 * @author yeori.seo@gmail.com
 */
public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private Node<Item> head;
    private Node<Item> tail;
    private int size;
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null && tail == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNotNullItem(item);
        Node<Item> newNode = new Node<>(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNotNullItem(item);
        Node<Item> newNode = new Node<>(item);
        if (isEmpty()) {
           head = newNode;
           tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    private void checkNotNullItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkNotNull() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    // remove and return the item from the front
    public Item removeFirst() {
        checkNotNull();
        Node<Item> nodeToDel = head;
        head = nodeToDel.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        nodeToDel.next = null;
        size--;
        return nodeToDel.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkNotNull();
        Node<Item> nodeToDel = tail;
        tail = nodeToDel.prev;
        if(tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        nodeToDel.prev = null;
        size--;
        return nodeToDel.item;
    }

    private void printFromHead() {
        Node<Item> ref = head;
        System.out.println("size : " + size);
        while (ref != null) {
            System.out.print(ref.item + " > ");
            ref = ref.next;
        }
        System.out.println();
    }
    private void printFromTail() {
        Node<Item> ref = tail;
        System.out.println("size : " + size);
        StringBuilder sb = new StringBuilder();
        while (ref != null) {
            sb.insert(0, ref.item + " < ");
            ref = ref.prev;
        }
        System.out.println(sb.toString());


    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new QueueItr<>(head);
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
        private Node(Item item) {
            this.item = item;
        }
    }

    private static class QueueItr<Item> implements Iterator<Item> {

        private Node<Item> ref;
        private QueueItr(Node<Item> head) {
            ref  = head;
        }

        @Override
        public boolean hasNext() {
            return ref != null;
        }

        @Override
        public Item next() {
            if (ref == null) {
                throw new NoSuchElementException();
            }
            Node<Item> cur = ref;
            ref = ref.next;
            return cur.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
