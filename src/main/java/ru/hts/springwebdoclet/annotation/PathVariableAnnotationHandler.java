package ru.hts.springwebdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes <code>@PathVariable</code> annotation
 * @author Ivan Sungurov
 */
public class PathVariableAnnotationHandler implements AnnotationHandler {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc) {
        RenderContext result = new RenderContext();
        result.put("required", true);

        for (AnnotationDesc.ElementValuePair annotationParam : annotationDoc.elementValues()) {
            String annotationParamName = annotationParam.element().name();
            Object value = annotationParam.value().value();

            if ("value".equals(annotationParamName)) {
                result.put("name", value);

            }
        }

        return result;
    }

    @Override
    public Class getSupportedAnnotation() {
        return PathVariable.class;
    }
}
