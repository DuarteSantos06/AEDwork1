package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentIndex;
    private Iterator<Map.Entry<K,V>> currentBucketIterator;


    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();
    }

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
     *
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
     *
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
     */
    public void rewind(){
        currentIndex = -1;
        advanceToNextBucket();
    }
}

