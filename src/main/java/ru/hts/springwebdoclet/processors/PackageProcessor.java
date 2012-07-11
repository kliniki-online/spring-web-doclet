package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface PackageProcessor {
    void init(RootDoc rootDoc);

    RenderContext process(PackageDoc packageDoc);
}
