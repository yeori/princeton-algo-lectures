/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    final private static int DEFAULT_SIZE = 8;
    private Object[] elems;
    // head++, and then remove an element from head
    // private int head;
    // add an element to tail, then tail++;
    private int tail;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue() {
        elems = new Object[DEFAULT_SIZE];
        // head = 0;
        tail = 0;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        tryEnlarge();
        // int tailIdx = tail % elems.length;
        elems[tail] = item;
        // shuffleOnce(head, tail, tail);
        // tail = (tail+1)% elems.length;
        tail++;
        size++;
        shuffleOnce(tail);
    }

    private void tryEnlarge() {
        if(size < elems.length) {
            return;
        }
        // now tail is out of range
        Object [] cloned = new Object[elems.length*2];
        System.arraycopy(elems, 0, cloned, 0, elems.length);

        tail = elems.length;
        elems = cloned;
    }

    private void shuffleOnce(int limit) {
        int randIdx = StdRandom.uniform(limit);
        Item tmp = (Item) elems[randIdx];
        elems[randIdx] = elems[limit-1];
        elems[limit-1] = tmp;
    }

    // remove and return a random item
    public Item dequeue() {
        checkNonEmpty(this);
        //head++;
        // shuffleOnce(tail);
        tail--;
        Item itemToDel = (Item) elems[tail];
        elems[tail] = null;
        size--;

        tryShrink();

        return itemToDel;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkNonEmpty(this);
        shuffleOnce(tail);
        // shuffleOnce(head+1, tail-1, tail-1);
        return (Item) elems[tail-1];
    }
    private static void checkNonEmpty(RandomizedQueue q) {
        if(q.isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    private void tryShrink() {
        if (size == 0 || size*4 > elems.length) {
            return;
        }

        Object [] cloned = new Object[elems.length/2];
        System.arraycopy(elems, 0, cloned, 0, size);
        elems = cloned;
    }


    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomItr<>(elems, size);
    }

    private static class RandomItr<Item> implements Iterator<Item> {
        Object[] ref;
        int size;
        int pos;
        RandomItr(Object[] elems, int size) {
            ref = new Object[size];
            System.arraycopy(elems, 0, ref, 0, size);
            StdRandom.shuffle(ref);
            this.size = size;
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public Item next() {
            if (pos >= size) {
               throw new NoSuchElementException();
            }
            return (Item)ref[pos++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capacity: " + elems.length + "\n");
        sb.append(String.format("TAIL: %d, SIZE: %d\n", tail, size));
        for (int i = 0; i < elems.length; i++) {
            if(elems[i] == null) {
                sb.append("_ ");
            } else {
                sb.append(elems[i] + " ");
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    int capacity() {
        return elems.length;
    }
    int head() {
        return 0;

    }
    int tail() {
        return tail;
    }*/

}
