package ru.hts.springdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springdoclet.render.RenderContext;

/**
 * Processes specific annotation
 * @author Ivan Sungurov
 */
public interface AnnotationHandler {
    RenderContext handle(AnnotationDesc annotationDoc);

    Class getSupportedAnnotation();
}
