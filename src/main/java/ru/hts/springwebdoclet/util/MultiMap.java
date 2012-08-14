package ru.hts.springwebdoclet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Simple multi map implementation
 *
 * @author Ivan Sungurov
 */
public class MultiMap<K, V> extends HashMap<K, Collection<V>> {
    public void putItem(K key, V item) {
        Collection<V> list = get(key);

        if (list == null) {
            list = new ArrayList<V>();
            put(key, list);
        }

        list.add(item);
    }
}
