package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import ru.hts.springwebdoclet.ReflectionUtils;
import ru.hts.springwebdoclet.processors.FieldProcessor;
import ru.hts.springwebdoclet.processors.ReturnProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.List;

import static ru.hts.springwebdoclet.ReflectionUtils.getRequiredClass;

/**
 * Processes return value
 * @author Ivan Sungurov
 */
public class ReturnProcessorImpl implements ReturnProcessor {

    private List<Class> ignoreTypes = new ArrayList<Class>();
    private FieldProcessor fieldProcessor;

    @Override
    public void init(RootDoc rootDoc) {
        fieldProcessor.init(rootDoc);
    }

    @Override
    public RenderContext process(ClassDoc classDoc, MethodDoc methodDoc) {
        boolean ignoreReturn = false;

        String returnType = methodDoc.returnType().qualifiedTypeName();
        Class returnClass = ReflectionUtils.getOptionalClass(returnType);

        if (returnClass != null) {
            for (Class ignoreClass : ignoreTypes) {
                if (ignoreClass.isAssignableFrom(returnClass)) {
                    ignoreReturn = true;
                    break;
                }
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
