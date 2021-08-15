package cse2010.orderedmap;

import java.util.Collection;
import java.util.Set;

/**
 * A top level interface for a ordered map.
 *
 * @param <K> key type
 * @param <V> element type
 */
public interface OrderedMap<K extends Comparable<K>,V> {
    /**
     * Returns the entry with smallest key value (or null, if the map is empty).
     * @return the entry with smallest key value (or null, if the map is empty)
     */
    Entry<K,V> firstEntry();

    /**
     * Returns the entry with largest key value(or null if the map is empty)
     * @return the entry with largest key value (or null, if the map is empty)
     */
    Entry<K,V> lastEntry();

    /**
     * Returns the entry with the largest key value less than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value less than or equal to "key"
     * (or null, if no such entry exists)
     */
    Entry<K,V> floorEntry(K key);

    /**
     * Returns the entry with the least key value greater than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the least key value greater than or equal to k (or null, if no such entry exists)
     */
    Entry<K,V> ceilingEntry(K key);

    /**
     * Returns the entry with the largest key value strictly less than "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value strictly less than k (or null, if no such entry exists).
     */
    Entry<K,V> lowerEntry(K key); // predecessor

    /**
     * Returns the entry with the least key value strictly greater than "key" (or null if no such entry exists).
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or null if no such entry exists)
     */
    Entry<K,V> higherEntry(K key); // successor

    /**
     * Returns the value associated with the specified key (or else null).
     * @param key search key
     * @return the value associated with the specified key (or else null)
     */
    V get(K key);

    /**
     * Associates the given value with the given key, returning any overridden value.
     * @param key search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null otherwise
     */
    V put(K key, V value);

    /**
     * Removes the entry having key "key" (if any) and returns its associated value.
     * @param key search key
     * @return value associated with the key
     */
    V remove(K key);

    /**
     * Returns the number of entries in the map.
     * @return the number of entries in the map
     */
    int size();

    /**
     * Returns the set of keys in the map sorted in ascending order.
     * @return the set of keys in the map sorted in ascending order
     */
    Set<K> keys();

    /**
     * Returns the collection of values in the map.
     * @return the collection of values in the map
     */
    Collection<V> values();

    /**
     * Returns the collection of entries in the map.
     * @return the collection of entries in the map
     */
    Collection<Entry<K, V>> entries();
}
