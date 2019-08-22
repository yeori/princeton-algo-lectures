import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DequeTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void add_remove_at_first () {
        Deque<String> q = new Deque<>();
        q.addFirst("A");
        q.addFirst("B");
        q.addFirst("C");

        assertIter("CBA", q);
        assertEquals(3, q.size());
        assertEquals("C", q.removeFirst());
        assertEquals(2, q.size());

    }

    @Test
    void add_remove_at_last () {
        Deque<String> q = new Deque<>();
        // A > B > C
        q.addLast("A");
        q.addLast("B");
        q.addLast("C");

        assertEquals("C", q.removeLast());
        assertEquals(2, q.size());

        assertIter("AB", q);
    }

    @Test
    public void add_first_remove_last() {
        Deque<String> q = new Deque<>();
        // A > B > C
        q.addFirst("A");
        assertEquals("A", q.removeLast());
        assertTrue(q.isEmpty());
    }

    @Test
    public void addLast_removeFirst() {
        Deque<String> q = new Deque<>();
        // A > B > C
        q.addLast("A");
        assertEquals("A", q.removeFirst());
        assertTrue(q.isEmpty());
    }

    private void assertIter(String elems, Deque<String> q) {
        String [] expected = elems.split("");
        int i = 0;
        for(String actual : q) {
            assertEquals(expected[i], actual, "not equals at " + i);
            i++;
        }
    }
}