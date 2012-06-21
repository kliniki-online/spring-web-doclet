package ru.hts.springdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.processors.FieldProcessor;
import ru.hts.springdoclet.processors.ReturnProcessor;
import ru.hts.springdoclet.render.RenderContext;

/**
 * Processes return value
 * @author Ivan Sungurov
 */
public class ReturnProcessorImpl implements ReturnProcessor {

    private FieldProcessor fieldProcessor;

    @Override
    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        RenderContext result = new RenderContext();
        ClassDoc returnClassDoc = classDoc.findClass(methodDoc.returnType().qualifiedTypeName());
        result.put("return", fieldProcessor.process(returnClassDoc));
        return result;
    }

    public void setFieldProcessor(FieldProcessor fieldProcessor) {
        this.fieldProcessor = fieldProcessor;
    }
}
