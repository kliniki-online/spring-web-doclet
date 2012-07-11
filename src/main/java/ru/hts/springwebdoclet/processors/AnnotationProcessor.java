package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface AnnotationProcessor {
    void init(RootDoc rootDoc);

    RenderContext process(AnnotationDesc[] annotations);
}
