package ru.hts.springwebdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes specific annotation
 * @author Ivan Sungurov
 */
public interface AnnotationHandler {
    RenderContext handle(AnnotationDesc annotationDoc);

    Class getSupportedAnnotation();
}
