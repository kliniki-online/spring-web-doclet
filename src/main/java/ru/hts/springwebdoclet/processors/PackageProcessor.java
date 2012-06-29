package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.PackageDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface PackageProcessor {
    RenderContext process(PackageDoc packageDoc);
}
