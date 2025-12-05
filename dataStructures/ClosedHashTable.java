/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */
package dataStructures;

/**
 * Closed Hash Table (Open Addressing with Linear Probing)
 * Generic key-value dictionary.
 *
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    // Load factors
    static final float IDEAL_LOAD_FACTOR = 0.5f; // used to calculate initial array size
    static final float MAX_LOAD_FACTOR = 0.8f;   // triggers rehash
    static final int NOT_FOUND = -1;             // sentinel for unsuccessful searches

    // Marker for removed entries
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // Array storing the entries
    private Entry<K,V>[] table;

    /**
     * Default constructor
     * Creates hash table with default capacity
     */
    public ClosedHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor with specific capacity
     * @param capacity approximate number of elements
     */
    @SuppressWarnings("unchecked")
    public ClosedHashTable(int capacity) {
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (Entry<K,V>[]) new Entry[arraySize];
        for (int i = 0; i < arraySize; i++) table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    /**
     * Searches for a key using linear probing
     * @param key the key to search
     * @return index of the entry or NOT_FOUND if missing
     *
     * Time Complexity: O(n) in worst case (full table), O(1) average if load factor is low
     */
    int searchLinearProving(K key) {
        for(int i=0;i<table.length;i++){
            int idx = Math.abs(key.hashCode() + i) % table.length;
            if (table[idx] != null && table[idx].key().equals(key)) {
                return idx;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Returns value associated with the key
     * @param key
     * @return value or null if key not found
     *
     * Time Complexity: O(n) worst case, O(1) on best
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);
        if(index != NOT_FOUND) return table[index].value();
        return null;
    }

    /**
     * Inserts or updates a key-value pair
     * @param key
     * @param value
     * @return old value if key existed, null otherwise
     *
     * Time Complexity: O(n) worst case (linear probing or rehash), O(1) on best
     */
    @Override
    public V put(K key, V value) {
        if (isFull()) rehash();

        int index = searchLinearProving(key);
        if(index != NOT_FOUND){
            V oldValue = table[index].value();
            table[index] = new Entry<>(key, value);
            return oldValue;
        }

        // Find slot for insertion
        int idxInsertion = -1;
        for(int i=0;i<table.length;i++){
            int idx = Math.abs(key.hashCode() + i) % table.length;
            if (table[idx] == REMOVED_CELL && idxInsertion == -1) {
                idxInsertion  = idx;
            } else if (table[idx] == null) {
                if (idxInsertion == -1) idxInsertion = idx;
                break;
            }
        }

        table[idxInsertion] = new Entry<>(key, value);
        currentSize++;
        return null;
    }

    /**
     * Resizes the table and rehashes all entries
     * Time Complexity: O(n) because all entries are reinserted
     */
    @SuppressWarnings("unchecked")
    private void rehash(){
        Entry<K,V>[] oldTable = table;
        int newSize = HashTable.nextPrime(2 * oldTable.length);
        table = (Entry<K,V>[]) new Entry[newSize];
        currentSize = 0;
        maxSize = (int) (newSize * MAX_LOAD_FACTOR);
        for (Entry<K,V> e : oldTable) {
            if (e != null && e != REMOVED_CELL) {
                put(e.key(), e.value());
            }
        }
    }

    /**
     * Removes the entry with the given key
     * @param key
     * @return previous value if key existed, null otherwise
     *
     * Time Complexity: O(n) worst case (linear probing), O(1) on best case
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        int index = searchLinearProving(key);
        while(table[index] != null && table[index].key().equals(key)){
            if(table[index] != REMOVED_CELL) {
                V oldValue = table[index].value();
                table[index] = (Entry<K,V>)REMOVED_CELL;
                currentSize--;
                return oldValue;
            }
            index = (index + 1) % table.length;
        }
        return null;
    }

    /**
     * Returns iterator of non-null entries
     * Time Complexity: O(n) to iterate all table slots
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(new ArrayIterator<>(table, table.length),
                e -> e != null && e != REMOVED_CELL);
    }
}
