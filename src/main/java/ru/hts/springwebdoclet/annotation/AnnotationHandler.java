package ru.hts.springwebdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes specific annotation
 * @author Ivan Sungurov
 */
public interface AnnotationHandler<T> {
    RenderContext handle(AnnotationDesc annotationDoc, T target);

    Class getSupportedAnnotation();
}
