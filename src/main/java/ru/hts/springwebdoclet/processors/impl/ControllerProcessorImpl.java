package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.MethodContextComparator;
import ru.hts.springwebdoclet.processors.ControllerProcessor;
import ru.hts.springwebdoclet.processors.MethodProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Processes controller class
 * @author Ivan Sungurov
 */
public class ControllerProcessorImpl implements ControllerProcessor {
    private MethodProcessor methodProcessor;

    @Override
    public void init(RootDoc rootDoc) {
        methodProcessor.init(rootDoc);
    }

    @Override
    public RenderContext process(ClassDoc classDoc) {
        RenderContext result = new RenderContext();

        result.put("package", classDoc.containingPackage().name());
        result.put("name", classDoc.typeName());
        result.put("title", classDoc.commentText());

        List<RenderContext> methods = new ArrayList<RenderContext>();
        for (MethodDoc methodDoc : classDoc.methods()) {
            RenderContext context = methodProcessor.process(classDoc, methodDoc);
            if (context != null) {
                methods.add(context);
            }
        }

        Collections.sort(methods, new MethodContextComparator());

        result.put("methods", methods);

        return result;
    }

    public void setMethodProcessor(MethodProcessor methodProcessor) {
        this.methodProcessor = methodProcessor;
    }
}
