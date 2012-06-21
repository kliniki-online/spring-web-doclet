package ru.hts.springdoclet.processors;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface AnnotationProcessor {
    RenderContext process(AnnotationDesc[] annotations);
}
