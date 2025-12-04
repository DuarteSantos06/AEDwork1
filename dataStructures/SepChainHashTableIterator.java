/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */


package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;
/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentIndex;
    private Iterator<Map.Entry<K,V>> currentBucketIterator;


    /**
     * Constructor initializes the iterator and positions to the first element.
     * Time Complexity: O(m) in worst-case to find the first non-empty bucket and O(1) on best case
     */
    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();
    }

    /**
     * Advances currentIndex to the next non-empty bucket.
     * Sets currentBucketIterator accordingly.
     * Time Complexity: O(m) in worst-case (all remaining buckets empty) and O(1) on best case
     */
    private void advanceToNextBucket() {
        currentIndex++;
        while (currentIndex < table.length) {
            if (table[currentIndex] != null && !table[currentIndex].isEmpty()) {
                currentBucketIterator = table[currentIndex].iterator();
                return;
            }
            currentIndex++;
        }
        currentBucketIterator = null;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *Time Complexity: O(1) amortized; may be O(m) if many empty buckets need to be skipped
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        if (currentBucketIterator == null)
            return false;

        if (currentBucketIterator.hasNext())
            return true;
        advanceToNextBucket();
        return currentBucketIterator != null && currentBucketIterator.hasNext();

    }

    /**
     * Returns the next element in the iteration.
     *Time Complexity: O(1)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return currentBucketIterator.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * Time Complexity: O(1) on best case and O(n) on worst case
     */
    public void rewind(){
        currentIndex = -1;
        advanceToNextBucket();
    }
}

