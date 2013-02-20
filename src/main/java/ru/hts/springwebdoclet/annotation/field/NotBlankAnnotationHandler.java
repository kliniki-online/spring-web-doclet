package ru.hts.springwebdoclet.annotation.field;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;
import org.hibernate.validator.constraints.NotBlank;
import ru.hts.springwebdoclet.annotation.AnnotationHandler;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes <code>@NotBlank</code> field annotation
 * @author Ivan Sungurov
 */
public class NotBlankAnnotationHandler implements AnnotationHandler<FieldDoc> {
    @Override
    public RenderContext handle(AnnotationDesc annotationDoc, FieldDoc target) {
        RenderContext context = new RenderContext();
        context.put("required", true);
        return context;
    }

    @Override
    public Class getSupportedAnnotation() {
        return NotBlank.class;
    }
}
