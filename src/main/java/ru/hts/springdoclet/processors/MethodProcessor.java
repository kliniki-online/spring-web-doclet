package ru.hts.springdoclet.processors;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Processes method
 * @author Ivan Sungurov
 */
public class MethodProcessor {
    private ParameterProcessor parameterProcessor;
    private AnnotationProcessor annotationProcessor;
    private ReturnProcessor returnProcessor;
    private ThrowsProcessor throwsProcessor;

    public MethodProcessor(ParameterProcessor parameterProcessor, AnnotationProcessor annotationProcessor,
                           ReturnProcessor returnProcessor, ThrowsProcessor throwsProcessor) {
        this.parameterProcessor = parameterProcessor;
        this.annotationProcessor = annotationProcessor;
        this.returnProcessor = returnProcessor;
        this.throwsProcessor = throwsProcessor;
    }

    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        RenderContext result = new RenderContext();

        result.put("description", methodDoc.commentText());

        ArrayList<AnnotationDesc> annotations = new ArrayList<AnnotationDesc>();
        annotations.addAll(Arrays.asList(methodDoc.annotations()));
        annotations.addAll(Arrays.asList(classDoc.annotations()));

        result.putAll(annotationProcessor.process(annotations.toArray(new AnnotationDesc[0])));
        result.putAll(returnProcessor.process(classDoc, methodDoc));
        result.put("parameters", parameterProcessor.process(methodDoc));
        result.put("exceptions", throwsProcessor.process(methodDoc));

        return result;
    }
}
