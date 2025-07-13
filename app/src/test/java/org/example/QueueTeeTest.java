package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QueueTeeTest {
    private QueueTee queue;
    private Cutie puppy;
    private Cutie kitty;
    private Cutie marmoset;

    @BeforeEach
    void setUp() {
        queue = new QueueTee();
        puppy = new Puppy();
        kitty = new Kitty();
        marmoset = new PygmyMarmoset();
    }

    @Test
    void size_initialSizeIsZero() {
        assertEquals(0, queue.size());
    }

    @Test
    void size_afterEnqueues() {
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        assertEquals(2, queue.size());
    }

    @Test
    void size_afterDequeues() {
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        queue.dequeue();
        assertEquals(1, queue.size());
    }

    @Test
    void enqueue_singleItem() {
        queue.enqueue(puppy);
        assertEquals(1, queue.size());
    }

    @Test
    void enqueue_fullQueueRejectsNewItems() {
        fillQueue(10);
        queue.enqueue(puppy);
        assertEquals(10, queue.size());
    }

    @Test
    void dequeue_returnsFirstItem() {
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        assertEquals(puppy, queue.dequeue());
    }

    @Test
    void dequeue_returnsNullWhenEmpty() {
        assertNull(queue.dequeue());
    }

    @Test
    void dequeue_maintainsFifoOrder() {
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        queue.enqueue(marmoset);
        
        assertEquals(puppy, queue.dequeue());
        assertEquals(kitty, queue.dequeue());
        assertEquals(marmoset, queue.dequeue());
    }

    @Test
    void dequeue_afterFullQueue() {
        fillQueue(10);
        queue.dequeue(); // Remove first item
        queue.enqueue(puppy); // Should succeed
        
        assertEquals(10, queue.size());
    }

    @Test
    void integration_fullWorkflow() {
        // Initial state
        assertEquals(0, queue.size());
        
        // Add items
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        assertEquals(2, queue.size());
        
        // Remove first item
        Cutie first = queue.dequeue();
        assertEquals(puppy, first);
        assertEquals(1, queue.size());
        
        // Add another item
        queue.enqueue(marmoset);
        assertEquals(2, queue.size());
        
        // Verify order
        assertEquals(kitty, queue.dequeue());
        assertEquals(marmoset, queue.dequeue());
        assertEquals(0, queue.size());
    }

    @Test
    void integration_circularBehavior() {
        // Fill the queue
        fillQueue(10);
        
        // Remove 3 items
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        
        // Add 3 new items
        queue.enqueue(puppy);
        queue.enqueue(kitty);
        queue.enqueue(marmoset);
        
        // Verify size
        assertEquals(10, queue.size());
        
        // Verify FIFO order of remaining items
        for (int i = 3; i < 10; i++) {
            Cutie c = queue.dequeue();
            assertEquals("Cutie " + i, c.description());
        }
        
        // Verify new items
        assertEquals(puppy, queue.dequeue());
        assertEquals(kitty, queue.dequeue());
        assertEquals(marmoset, queue.dequeue());
    }

    // Helper to fill queue with test objects
    private void fillQueue(int count) {
        for (int i = 0; i < count; i++) {
            queue.enqueue(new TestCutie("Cutie " + i));
        }
    }
    
    // Test implementation of Cutie interface
    private static class TestCutie implements Cutie {
        private final String desc;
        
        public TestCutie(String description) {
            this.desc = description;
        }
        
        @Override
        public String description() {
            return desc;
        }
        
        @Override
        public Integer cutenessRating() {
            return 5;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TestCutie)) return false;
            return this.desc.equals(((TestCutie) obj).desc);
        }
    }
}