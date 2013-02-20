package ru.hts.springwebdoclet.annotation.field;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;
import ru.hts.springwebdoclet.annotation.AnnotationHandler;
import ru.hts.springwebdoclet.render.RenderContext;

import javax.validation.constraints.NotNull;

/**
 * Processes <code>@NotNull</code> field annotation
 * @author Ivan Sungurov
 */
public class NotNullAnnotationHandler implements AnnotationHandler<FieldDoc> {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc, FieldDoc target) {
        RenderContext context = new RenderContext();
        context.put("required", true);
        return context;
    }

    @Override
    public Class getSupportedAnnotation() {
        return NotNull.class;
    }
}
