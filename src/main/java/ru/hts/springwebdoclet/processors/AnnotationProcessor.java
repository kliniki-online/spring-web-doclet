package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface AnnotationProcessor<T> {

    RenderContext process(AnnotationDesc[] annotations, T target);
}
