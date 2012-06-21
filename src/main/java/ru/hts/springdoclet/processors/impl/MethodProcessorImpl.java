package ru.hts.springdoclet.processors.impl;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.processors.*;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Processes method
 * @author Ivan Sungurov
 */
public class MethodProcessorImpl implements MethodProcessor {
    private ParameterProcessor parameterProcessor;
    private AnnotationProcessor annotationProcessor;
    private ReturnProcessor returnProcessor;
    private ThrowsProcessor throwsProcessor;

    @Override
    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        RenderContext result = new RenderContext();

        result.put("description", methodDoc.commentText());

        ArrayList<AnnotationDesc> annotations = new ArrayList<AnnotationDesc>();
        annotations.addAll(Arrays.asList(methodDoc.annotations()));
        annotations.addAll(Arrays.asList(classDoc.annotations()));

        result.putAll(annotationProcessor.process(annotations.toArray(new AnnotationDesc[0])));
        result.putAll(returnProcessor.process(classDoc, methodDoc));
        result.put("parameters", parameterProcessor.process(methodDoc));
        result.put("exceptions", throwsProcessor.process(classDoc, methodDoc));

        return result;
    }

    public void setParameterProcessor(ParameterProcessor parameterProcessor) {
        this.parameterProcessor = parameterProcessor;
    }

    public void setAnnotationProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    public void setReturnProcessor(ReturnProcessor returnProcessor) {
        this.returnProcessor = returnProcessor;
    }

    public void setThrowsProcessor(ThrowsProcessor throwsProcessor) {
        this.throwsProcessor = throwsProcessor;
    }
}
