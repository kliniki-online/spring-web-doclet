package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hts.springwebdoclet.processors.*;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Processes method
 * @author Ivan Sungurov
 */
public class MethodProcessorImpl implements MethodProcessor {
    private static final String REQUEST_MAPPING_CLASS_NAME = RequestMapping.class.getCanonicalName();

    private ParameterProcessor parameterProcessor;
    private AnnotationProcessor annotationProcessor;
    private ReturnProcessor returnProcessor;
    private ThrowsProcessor throwsProcessor;

    @Override
    public void init(RootDoc rootDoc) {
        returnProcessor.init(rootDoc);
    }

    @Override
    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        RenderContext result = new RenderContext();

        result.put("description", methodDoc.commentText());

        ArrayList<AnnotationDesc> annotations = new ArrayList<AnnotationDesc>();
        annotations.addAll(Arrays.asList(classDoc.annotations()));
        annotations.addAll(Arrays.asList(methodDoc.annotations()));

        boolean mapped = false;
        for (AnnotationDesc annotationDoc : annotations) {
            if (REQUEST_MAPPING_CLASS_NAME.equals(annotationDoc.annotationType().qualifiedTypeName())) {
                mapped = true;
                break;
            }
        }

        if (!mapped) {
            return null;
        }

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
