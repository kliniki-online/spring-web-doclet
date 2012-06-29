package ru.hts.springwebdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hts.springwebdoclet.render.RenderContext;

import static ru.hts.springwebdoclet.JavadocUtils.getAnnotationParamValue;

/**
 * Processes <code>@RequestMapping</code> annotation
 * @author Ivan Sungurov
 */
public class RequestMappingAnnotationHandler implements AnnotationHandler {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc) {
        RenderContext result = new RenderContext();

        for (AnnotationDesc.ElementValuePair param : annotationDoc.elementValues()) {
            String paramName = param.element().name();
            if ("value".equals(paramName)) {
                result.put("url", getAnnotationParamValue(param));

            } else if ("method".equals(paramName)) {
                FieldDoc requestMethod = (FieldDoc) getAnnotationParamValue(param);
                result.put("method", requestMethod.name());
            }
        }

        return result;
    }

    @Override
    public Class getSupportedAnnotation() {
        return RequestMapping.class;
    }
}
