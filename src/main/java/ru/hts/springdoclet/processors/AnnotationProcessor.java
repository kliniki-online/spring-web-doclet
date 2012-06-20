package ru.hts.springdoclet.processors;

import com.sun.javadoc.AnnotationDesc;
import org.apache.commons.lang3.ClassUtils;
import ru.hts.springdoclet.annotation.AnnotationHandler;
import ru.hts.springdoclet.render.RenderContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Processes annotations
 * @author Ivan Sungurov
 */
public class AnnotationProcessor {
    private Map<Class, AnnotationHandler> handlerMap = new HashMap<Class, AnnotationHandler>();

    public RenderContext process(AnnotationDesc[] annotations) {
        RenderContext result = new RenderContext();

        for (AnnotationDesc annotationDoc : annotations) {
            Class annotationType;
            try {
                annotationType = ClassUtils.getClass(annotationDoc.annotationType().qualifiedTypeName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            AnnotationHandler handler = handlerMap.get(annotationType);
            if (handler != null) {
                Map<String, Object> values = handler.handle(annotationDoc);
                result.putAll(values);
            }
        }

        return result;
    }

    public void addAnnotationHandler(AnnotationHandler handler) {
        handlerMap.put(handler.getSupportedAnnotation(), handler);
    }
}
