package ru.hts.springdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hts.springdoclet.render.RenderContext;

/**
 * Processes <code>@RequestParam</code> annotation
 * @author Ivan Sungurov
 */
public class RequestParamAnnotationHandler implements AnnotationHandler {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc) {
        RenderContext result = new RenderContext();
        result.put("required", true);

        for (AnnotationDesc.ElementValuePair annotationParam : annotationDoc.elementValues()) {
            String annotationParamName = annotationParam.element().name();
            Object value = annotationParam.value().value();

            if ("value".equals(annotationParamName)) {
                result.put("name", value);

            } else if ("required".equals(annotationParamName)) {
                result.put("required", value);
            }
        }

        return result;
    }

    @Override
    public Class getSupportedAnnotation() {
        return RequestParam.class;
    }
}
