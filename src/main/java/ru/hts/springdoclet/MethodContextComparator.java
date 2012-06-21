package ru.hts.springdoclet;

import ru.hts.springdoclet.render.RenderContext;

import java.util.Comparator;

/**
 * Compares method context using URL and HTTP method
 * @author Ivan Sungurov
 */
public class MethodContextComparator implements Comparator<RenderContext> {

    @Override
    public int compare(RenderContext o1, RenderContext o2) {
        int comp1 = o1.get("url").toString().compareTo(o2.get("url").toString());
        return comp1 != 0 ? comp1 : o1.get("method").toString().compareTo(o2.get("method").toString());
    }
}
