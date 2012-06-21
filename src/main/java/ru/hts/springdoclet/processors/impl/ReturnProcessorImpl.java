package ru.hts.springdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import ru.hts.springdoclet.processors.FieldProcessor;
import ru.hts.springdoclet.processors.ReturnProcessor;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.List;

import static ru.hts.springdoclet.ReflectionUtils.getRequiredClass;

/**
 * Processes return value
 * @author Ivan Sungurov
 */
public class ReturnProcessorImpl implements ReturnProcessor {

    private List<Class> ignoreTypes = new ArrayList<Class>();
    private FieldProcessor fieldProcessor;

    @Override
    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        boolean ignoreReturn = false;
        String returnType = methodDoc.returnType().qualifiedTypeName();
        Class returnClass = getRequiredClass(returnType);
        for (Class ignoreClass : ignoreTypes) {
            if (ignoreClass.isAssignableFrom(returnClass)) {
                ignoreReturn = true;
                break;
            }
        }

        Tag[] returnTags = methodDoc.tags("return");

        RenderContext result = new RenderContext();
        if (returnTags.length > 0) {
            result.put("return", returnTags[0].text());

        } else if (!ignoreReturn) {
            ClassDoc returnClassDoc = classDoc.findClass(returnType);
            result.put("returnFields", fieldProcessor.process(returnClassDoc));
        }

        return result;
    }

    public void setIgnoreTypes(List<String> ignoreTypes) {
        for (String ignoreType : ignoreTypes) {
            this.ignoreTypes.add(getRequiredClass(ignoreType));
        }
    }

    public void setFieldProcessor(FieldProcessor fieldProcessor) {
        this.fieldProcessor = fieldProcessor;
    }
}
