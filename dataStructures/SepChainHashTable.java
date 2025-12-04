/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package dataStructures;
/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    /**
     * Time Complexity: O(p)
     */
    public SepChainHashTable( int capacity ){
       super(capacity);
        int primeCapacity = nextPrime(capacity);
        table = (Map<K,V>[]) new Map[primeCapacity];

        for (int i = 0; i < primeCapacity; i++) {
            table[i] = new MapSinglyList<>();
        }
        maxSize = (int)(primeCapacity * MAX_LOAD_FACTOR);
    }

    // Returns the hash value of the specified key.

    /** Time Complexity: O(p)
     *
     * @param key
     * @return the hash of a key
     */
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *Time Complexity: O(1)
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        if (key == null)
            return null;
        int index = hash(key);
        return table[index].get(key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *Time Complexity: O(1 + α) on average, O(n) worst-case if all keys collide
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        if (key == null)
            return null;
        if (isFull())
            rehash();
        int index = hash(key);
        V oldValue = table[index].put(key, value);

        if (oldValue == null)
            currentSize++;

        return oldValue;
    }


    @SuppressWarnings("unchecked")
    private void rehash() {
        Map<K, V>[] oldTable = table;

        int newCapacity = nextPrime(2 * table.length);

        table = (Map<K, V>[]) new Map[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new MapSinglyList<>();
        }
        maxSize = (int) (newCapacity * MAX_LOAD_FACTOR);
        currentSize = 0;

        for (Map<K, V> bucket : oldTable) {
            if (bucket != null) {
                Iterator<Entry<K, V>> it = bucket.iterator();
                while (it.hasNext()) {
                    Entry<K, V> entry = it.next();
                    put(entry.key(), entry.value());
                }
            }
        }
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *Time Complexity: O(1 + α) on average, O(n) worst-case if all keys collide
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        if(key==null){
            return null;
        }
        int index = hash(key);
        V oldValue = table[index].remove(key);
        if (oldValue != null){
            currentSize--;
        }
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *Time complexity: O(n)
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }


}
