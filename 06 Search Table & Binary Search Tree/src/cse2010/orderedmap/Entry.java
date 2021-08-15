package cse2010.orderedmap;

import java.util.Objects;

/**
 * Each entry represents a "key-value" pair.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Entry<K extends Comparable<K>,V> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
    }

    @Override
    public String toString() {
        return "Entry(key=" + key + ", value = " + value + ")";
    }
}
