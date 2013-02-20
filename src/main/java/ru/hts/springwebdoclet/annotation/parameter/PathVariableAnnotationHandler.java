package ru.hts.springwebdoclet.annotation.parameter;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hts.springwebdoclet.annotation.AnnotationHandler;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes <code>@PathVariable</code> annotation
 * @author Ivan Sungurov
 */
public class PathVariableAnnotationHandler implements AnnotationHandler<Parameter> {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc, Parameter target) {
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
