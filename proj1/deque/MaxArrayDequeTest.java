package deque;

import jh61b.junit.In;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class MaxArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        MaxArrayDeque<String> lld1 = new MaxArrayDeque<String>(new StringComparator());

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<Integer>(new IntegerComparator());
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(new IntegerComparator());
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create MaxArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        MaxArrayDeque<String>  lld1 = new MaxArrayDeque<String>(new StringComparator());
        MaxArrayDeque<Double>  lld2 = new MaxArrayDeque<Double>(new DoubleComparator());
        MaxArrayDeque<Boolean> lld3 = new MaxArrayDeque<Boolean>(new BooleanCompartor());

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty MaxArrayDeque. */
    public void emptyNullReturnTest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<Integer>(new IntegerComparator());

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<Integer>(new IntegerComparator());
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    /* Test method get whether work well or not */
    public void getTest() {
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(new IntegerComparator());
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        int num = lld1.get(2);
        assertEquals(3, num);
    }


    @Test
    public void iteratorTest() {
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(new IntegerComparator());
        lld1.addLast(2);
        lld1.addLast(2);
        lld1.addLast(2);

        for (int item: lld1) {
            assertEquals(2, item);
        }
    }

    @Test
    public void maxTest() {
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(new IntegerComparator());
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);

        int maxItem = lld1.max();
        assertEquals(4, maxItem);

        MaxArrayDeque<String> lld2 = new MaxArrayDeque<>(new StringComparator());
        lld2.addLast("aaaa");
        lld2.addLast("bbbbb");
        lld2.addLast("ccc");

        String maxItem1 = lld2.max();
        String maxItem2 = lld2.max(new StringLengthComparator());
        assertEquals(4, maxItem);
        assertEquals("ccc", maxItem1);
        assertEquals("bbbbb", maxItem2);
    }

    private static class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2; // 正常升序排列
        }
    }

    private static class DoubleComparator implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            return (int) (o1 - o2);
        }
    }

    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length(); // 按字符串长度升序排序
        }
    }

    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2); // 按字符串长度升序排序
        }
    }

    private static class BooleanCompartor implements Comparator<Boolean>{

        @Override
        public int compare(Boolean o1, Boolean o2) {
            if (o1 && o2) {
                return 0;
            } else if (o1) {
                return 1;
            } else if (o2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
