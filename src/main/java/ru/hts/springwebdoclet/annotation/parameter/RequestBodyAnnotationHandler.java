package ru.hts.springwebdoclet.annotation.parameter;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hts.springwebdoclet.annotation.AnnotationHandler;
import ru.hts.springwebdoclet.processors.FieldProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

/**
 * Processes <code>@RequestBody</code> annotation
 * @author Ivan Sungurov
 */
public class RequestBodyAnnotationHandler implements AnnotationHandler<Parameter> {

    private FieldProcessor fieldProcessor;

    @Override
    public RenderContext handle(AnnotationDesc annotationDoc, Parameter target) {
        RenderContext result = new RenderContext();
        result.put("body", fieldProcessor.process(target.type().asClassDoc()));
        return result;
    }

    @Override
    public Class getSupportedAnnotation() {
        return RequestBody.class;
    }

    public void setFieldProcessor(FieldProcessor fieldProcessor) {
        this.fieldProcessor = fieldProcessor;
    }
}
