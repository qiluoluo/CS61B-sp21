package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    private Comparator<T> comparator;

    /**
     * Creates a MaxArrayDeque with the given Comparator.
     * @param c the comparator.
     */
    public MaxArrayDeque(Comparator<T> c){
        super();
        comparator = c;
    }

    /**
     * Returns the maximum element in the deque as governed
     * by the previously given Comparator. If the MaxArrayDeque
     * is empty, simply return null.
     * @return the maximum element in the deque.If the MaxArrayDeque
     * is empty, simply return null.
     */
    public T max() {
        return max(comparator);
    }

    /**
     * Returns the maximum element in the deque as governed by the
     * parameter Comparator c. If the MaxArrayDeque is empty, simply
     * return null.
     * @param c the comparator.
     * @return the maximum element in the deque as governed by the
     * parameter Comparator c. If the MaxArrayDeque is empty, simply
     * return null.
     */
    public T max(Comparator<T> c) {
        T maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            T ithItem = get(i);
            if (c.compare(maxItem, ithItem) < 0) {
                maxItem = ithItem;
            }
        }
        return maxItem;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof MaxArrayDeque)) {
            return false;
        }
        MaxArrayDeque<?> toEquals = (MaxArrayDeque<?>) o;
        if (toEquals.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (toEquals.get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }

}
