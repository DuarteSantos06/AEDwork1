package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable(int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.

    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        for(int i=0;i<table.length;i++){
            int idx = Math.abs( key.hashCode() + i) % table.length;
            if (table[idx] != null && table[idx].key().equals(key)) {
                return idx;
            }
        }
        return NOT_FOUND; 
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);
        if(index!=NOT_FOUND) return table[index].value();

        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        int index = searchLinearProving(key);

        if(index!=NOT_FOUND){
            V oldValue = table[index].value();
            table[index] = new Entry<>(key, value);
            return oldValue;
        }
        int idxInsertion=-1;
        for(int i=0;i<table.length;i++){
            int idx = Math.abs( key.hashCode() + i) % table.length;
            if (table[idx] == REMOVED_CELL) {
                if (idxInsertion == -1) {
                    idxInsertion  = idx;
                }
            } else if (table[idx] == null) {
                if (idxInsertion  == -1) {
                    idxInsertion = idx;
                }
                break;
            }
        }
        table[idxInsertion]=new Entry<>(key,value);
        currentSize++;
        return null;
    }

    @SuppressWarnings("unchecked")
     private void rehash(){
        Entry<K,V>[] oldTable = table;
        int newSize= HashTable.nextPrime(2*oldTable.length);
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
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        int index =searchLinearProving(key);
        while(table[index] != null && table[index].key().equals(key)){
            if(table[index]!=REMOVED_CELL) {
                V oldValue = table[index].value();
                table[index] = (Entry<K, V>)REMOVED_CELL;
                currentSize--;
                return oldValue;
            }
            index = (index + 1) % table.length;
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(new ArrayIterator<>(table,table.length), e -> e != null && e != REMOVED_CELL);
    }

}
