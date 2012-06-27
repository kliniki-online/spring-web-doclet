package ru.hts.springdoclet.processors;

import com.sun.javadoc.PackageDoc;
import ru.hts.springdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface PackageProcessor {
    RenderContext process(PackageDoc packageDoc);
}
