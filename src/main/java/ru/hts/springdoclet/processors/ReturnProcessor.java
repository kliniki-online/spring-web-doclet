package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.render.RenderContext;

/**
 * Processes return value
 * @author Ivan Sungurov
 */
public class ReturnProcessor {

    private FieldProcessor fieldProcessor;

    public ReturnProcessor(FieldProcessor fieldProcessor) {
        this.fieldProcessor = fieldProcessor;
    }

    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        RenderContext result = new RenderContext();
        ClassDoc returnClassDoc = classDoc.findClass(methodDoc.returnType().qualifiedTypeName());
        result.put("return", fieldProcessor.process(returnClassDoc));
        return result;
    }
}
