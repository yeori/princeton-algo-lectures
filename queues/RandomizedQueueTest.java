import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {

    @Test
    void arrayCopy() {
        String[] vs = "abcdefghi".split("");
        Arrays.fill(vs,0, 3, null);
        // [null, null, null, d, e, f, g, h, i]
        //  HEAD           2
        //  TAIL                                9
        System.out.println(Arrays.toString(vs));
        System.arraycopy(vs, 3, vs, 0, vs.length-3);
        System.out.println(Arrays.toString(vs));

    }

    @Test
    public void test_enlarge() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        System.out.println(rq);
        // rq.enqueue("2");
        push(rq, "2 3 5 7 11 13 17 19");
        System.out.println(rq);
        push(rq, "23");
        System.out.println(rq);
        new Tester(rq)
                .capacity(16)
                .size(9)
                .tail(9)
                .test();
    }

    @Test
    public  void test_shrink() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        System.out.println(rq);
        push(rq, "2 3 5 7"); // 4/8
        pop(rq, 2);
        new Tester(rq)
                .capacity(4)
                .size(2)
                .tail(2)
                .test();
    }

    @Test
    public void test_overflow() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        // rq.enqueue("2");
        push(rq, "2 3 5 7 11 13 17 19");
        pop(rq, 2);
        System.out.println(rq);
        Tester t = new Tester(rq)
                .capacity(8)
                .size(6)
                .tail(6)
                .test();
        push(rq, "A B");
        t.capacity(8).size(8).tail(8).test();
    }

    @Test
    public void test_iterator() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        Tester t = new Tester(rq);
        t.iter("");
        push(rq,"A B C D E");
        t.iter("A B C D E");
    }

    class Tester {
        RandomizedQueue<String> q;
        Integer capacity;
        Integer size;
        // Integer head;
        Integer tail;

        Tester(RandomizedQueue<String> q) {
            this.q = q;
        }
        Tester capacity(int v) {
            capacity = v;
            return this;
        }
        Tester size(int v) {
            size = v;
            return this;
        }
        /*
        Tester head(int v) {
            head = v;
            return this;
        }*/
        Tester tail(int v) {
            tail = v;
            return this;
        }

        Tester test() {
            if (capacity != null) {
                assertEquals(capacity.intValue(), q.capacity(), "capacity failed");
            }
            /*
            if(head != null) {
                assertEquals(head.intValue(), q.head(), "head failed");
            }
             */
            if (tail != null) {
                assertEquals(tail.intValue(), q.tail(), "tail failed");
            }
            if (size != null) {
                assertEquals(size.intValue(), q.size(), "size failed");
            }
            return this;
        }

        Tester iter(String vs) {
            String [] data = vs.split(" ");
            Set<String> set = Arrays.stream(data).collect(Collectors.toSet());
            set.remove("");

            assertEquals(set.size(), q.size(), "different size!");
            for (String s : q) {
                if(!set.contains(s)) {
                    fail(String.format("unexpected value \"%s\" found in queue", s));
                } else {
                    set.remove(s);
                }
            }
            if (set.size() >0) {
                fail("no such elements in queue: " + set);
            }
            return this;
        }
    }

    /**
     * dequeue cnt elements from the queue
     * @param q queue
     * @param cnt the number of elems to remove
     */
    void pop(RandomizedQueue<String> q, int cnt) {
        for (int i =0 ; i < cnt ; i++) {
            q.dequeue();
        }
    }

    void push(RandomizedQueue<String> q, String value) {
        String [] elems = value.split(" ");
        for (String each : elems) {
            q.enqueue(each);
        }


    }
}