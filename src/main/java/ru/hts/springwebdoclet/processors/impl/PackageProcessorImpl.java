package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import org.springframework.stereotype.Controller;
import ru.hts.springwebdoclet.JavadocUtils;
import ru.hts.springwebdoclet.processors.ControllerProcessor;
import ru.hts.springwebdoclet.processors.PackageProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.List;

/** @author Ivan Sungurov */
public class PackageProcessorImpl implements PackageProcessor {

    private static final String CONTROLLER_CLASS = Controller.class.getCanonicalName();

    private ControllerProcessor controllerProcessor;

    @Override
    public void init(RootDoc rootDoc) {
        controllerProcessor.init(rootDoc);
    }

    @Override
    public RenderContext process(PackageDoc packageDoc) {
        RenderContext context = new RenderContext();

        List<RenderContext> classContextList = new ArrayList<RenderContext>();

        for (ClassDoc classDoc : packageDoc.ordinaryClasses()) {
            if (!JavadocUtils.hasAnnotation(classDoc, CONTROLLER_CLASS)) {
                continue;
            }

            classContextList.add(controllerProcessor.process(classDoc));
        }

        if (classContextList.isEmpty()) {
            return null;
        }

        context.put("controllers", classContextList);
        context.put("name", packageDoc.name());
        context.put("overview", packageDoc.commentText());

        return context;
    }

    public void setControllerProcessor(ControllerProcessor controllerProcessor) {
        this.controllerProcessor = controllerProcessor;
    }
}
