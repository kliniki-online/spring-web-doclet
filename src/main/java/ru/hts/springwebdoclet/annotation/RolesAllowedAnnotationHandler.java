package ru.hts.springwebdoclet.annotation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import ru.hts.springwebdoclet.render.RenderContext;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes <code>@RolesAllowed</code> annotation
 * @author Ivan Sungurov
 */
public class RolesAllowedAnnotationHandler implements AnnotationHandler {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc) {
        RenderContext context = new RenderContext();

        List<String> roles = new ArrayList<String>();

        for (AnnotationDesc.ElementValuePair param : annotationDoc.elementValues()) {
            if ("value".equals(param.element().name())) {
                AnnotationValue[] values = (AnnotationValue[]) param.value().value();
                for (AnnotationValue value : values) {
                    roles.add(value.value().toString());
                }
            }
        }

        context.put("roles", roles);

        return context;
    }

    @Override
    public Class getSupportedAnnotation() {
        return RolesAllowed.class;
    }
}
