package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.AnnotationDesc;
import ru.hts.springwebdoclet.annotation.AnnotationHandler;
import ru.hts.springwebdoclet.processors.AnnotationProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.hts.springwebdoclet.ReflectionUtils.getRequiredClass;


/**
 * Processes annotations
 * @author Ivan Sungurov
 */
public class AnnotationProcessorImpl<T> implements AnnotationProcessor<T> {
    private Map<Class, AnnotationHandler> handlerMap = new HashMap<Class, AnnotationHandler>();

    @Override
    public RenderContext process(AnnotationDesc[] annotations, T target) {
        RenderContext result = new RenderContext();

        for (AnnotationDesc annotationDoc : annotations) {
            Class annotationType;
            try {
                annotationType = getRequiredClass(annotationDoc.annotationType().qualifiedTypeName());
            } catch (ClassCastException e) {
                System.err.println("Skipped " + annotationDoc + "annotation");
                continue;
            }

            AnnotationHandler handler = handlerMap.get(annotationType);
            if (handler != null) {
                Map<String, Object> values = handler.handle(annotationDoc, target);
                result.putAll(values);
            }
        }

        return result;
    }

    public void setAnnotationHandlers(List<AnnotationHandler> handlerList) {
        for (AnnotationHandler handler : handlerList) {
            handlerMap.put(handler.getSupportedAnnotation(), handler);
        }
    }
}
