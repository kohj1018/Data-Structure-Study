package cse2010.table;

import cse2010.orderedmap.Entry;
import cse2010.orderedmap.OrderedMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchTable<K extends Comparable<K>,V> implements OrderedMap<K, V> {
    private final Entry<K,V>[] entries;
    private int size;

    /**
     * Create a search table with capacity of {@code capacity}.
     * @param capacity maximum number of entries that can be stored in the table
     */
    @SuppressWarnings("unchecked")
    public SearchTable(int capacity) {
        entries = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
    }

    // search method
    private int binarySearch(K key, int low, int high) {
        if (low > high) {
            return low;
        } else {
            int mid = (low + high) / 2;
            Entry<K, V> midEntry = entries[mid];

            if (key.compareTo(midEntry.getKey()) == 0) {
                return mid;
            } else if (key.compareTo(midEntry.getKey()) < 0) {
                return binarySearch(key, low, mid - 1);
            } else {
                return binarySearch(key, mid + 1, high);
            }
        }
    }

    /**
     * Check whether the table is empty or not.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check whether the table is full or not.
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        return size == entries.length;
    }

    /**
     * Returns the entry with smallest key value (or null, if the map is empty).
     * @return the entry with smallest key value (or null, if the map is empty)
     */
    @Override public Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return entries[0];
    }

    /**
     * Returns the entry with largest key value(or null if the map is empty)
     * @return the entry with largest key value (or null, if the map is empty)
     */
    @Override public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return entries[ size - 1 ];
    }

    /**
     * Returns the entry with the largest key value less than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value less than or equal to "key"
     * (or null, if no such entry exists)
     */
    @Override public Entry<K, V> floorEntry(final K key) {
        if (key.compareTo(firstEntry().getKey()) < 0) {
            return null;
        } else if (key.compareTo(lastEntry().getKey()) >= 0) {
            return lastEntry();
        }
        int i = binarySearch(key, 0, size - 1);
        if (key.compareTo(entries[i].getKey()) == 0) {
            return entries[i];
        } else {
            return entries[i - 1];
        }
    }

    /**
     * Returns the entry with the least key value greater than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the least key value greater than or equal to k (or null, if no such entry exists)
     */
    @Override public Entry<K, V> ceilingEntry(final K key) {
        if (key.compareTo(lastEntry().getKey()) > 0) {
            return null;
        } else if (key.compareTo(firstEntry().getKey()) <= 0) {
            return firstEntry();
        }
        int i = binarySearch(key, 0, size - 1);
        return entries[i];
    }

    /**
     * Returns the entry with the largest key value strictly less than "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value strictly less than k (or null, if no such entry exists).
     */
    @Override public Entry<K, V> lowerEntry(final K key) {
        if (key.compareTo(firstEntry().getKey()) <= 0) {
            return null;
        }
        int i = binarySearch(key, 0, size - 1);
        return entries[i - 1];
    }

    /**
     * Returns the entry with the least key value strictly greater than "key" (or null if no such entry exists).
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or null if no such entry exists)
     */
    @Override public Entry<K, V> higherEntry(final K key) {
        if (key.compareTo(lastEntry().getKey()) >= 0) {
            return null;
        }
        int i = binarySearch(key, 0, size - 1);
        if (key.compareTo(entries[i].getKey()) == 0) {
            return entries[i + 1];
        } else {
            return entries[i];
        }
    }

    /**
     * Returns the value associated with the specified key (or else null).
     * @param key search key
     * @return the value associated with the specified key (or else null)
     */
    @Override public V get(final K key) {
        if (isEmpty())
            throw new IllegalStateException("Empty table");

        if (key.compareTo(firstEntry().getKey()) < 0 || key.compareTo(lastEntry().getKey()) > 0) {
            return null;
        }

        int i = binarySearch(key, 0, size - 1);
        if (key.compareTo(entries[i].getKey()) == 0) {
            return entries[i].getValue();
        } else {
            return null;
        }
    }

    /**
     * Associates the given value with the given key, returning any overridden value.
     * @param key search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null otherwise
     * @throws IllegalStateException if full
     */
    @Override public V put(final K key, final V value) {
        if (isFull())
            throw new IllegalStateException("table full");
        if (isEmpty()) {
            entries[0] = new Entry<K, V>(key, value);
            size++;
            return null;
        } else if (size == 1) {
            if (key.compareTo(entries[0].getKey()) < 0) {
                entries[1] = entries[0];
                entries[0] = new Entry<K, V>(key, value);
            } else {
                entries[1] = new Entry<K, V>(key, value);
            }
            size++;
            return null;
        }
        int i = binarySearch(key, 0, size - 1);
        if (entries[i] == null) {
            entries[i] = new Entry<K, V>(key, value);

            size++;
            return null;
        } else if (key.compareTo(entries[i].getKey()) == 0) {  // 이미 들어있는 경우
            V oldValue = entries[i].getValue();

            //insert
            entries[i] = new Entry<K, V>(key, value);

            return oldValue;
        } else {
            // low index 뒤쪽 배열의 값들을 오른쪽으로 한칸씩 이동 (자리 만들기)
            for (int j = size - 1; j >= i; j--) {
                entries[j + 1] = entries[j];
            }

            // insert
            entries[i] = new Entry<K, V>(key, value);

            size++;
            return null;
        }
    }

    /**
     * Removes the entry having key "key" (if any) and returns its associated value.
     * @param key search key
     * @return value associated with the key
     */
    @Override public V remove(final K key) {
        if (isEmpty())
            throw new IllegalArgumentException("table empty");

        int i = binarySearch(key, 0, size - 1);
        if (key.compareTo(entries[i].getKey()) == 0) {  // 해당 key 값이 들어있는 경우
            V oldValue = entries[i].getValue();

            // i index 뒤쪽 배열 배열의 값들을 왼쪽으로 한칸씩 이동 (자리 매꾸기)
            for (int j = i; j < size; j++) {
                entries[j] = entries[j + 1];
            }
            size--;
            return oldValue;
        } else {
            return null;
        }
    }

    /**
     * Returns the number of entries in the map.
     * @return the number of entries in the map
     */
    @Override public int size() {
        return size;
    }

    /**
     * Returns the set of keys in the map sorted in ascending order.
     * @return the set of keys in the map sorted in ascending order
     */
    @Override public Set<K> keys() {
        return Arrays.stream(entries).limit(size).map(Entry::getKey).collect(Collectors.toSet());
    }

    /**
     * Returns the collection of values in the map.
     * @return the collection of values in the map
     */
    @Override public Collection<V> values() {
        return Arrays.stream(entries).limit(size).map(Entry::getValue).collect(Collectors.toList());
    }

    /**
     * Returns the collection of entries in the map.
     * @return the collection of entries in the map
     */
    @Override public Collection<Entry<K, V>> entries() {
        return Arrays.stream(entries).limit(size).collect(Collectors.toList());
    }

}
