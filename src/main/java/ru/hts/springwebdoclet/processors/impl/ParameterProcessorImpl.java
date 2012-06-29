package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;
import ru.hts.springwebdoclet.JavadocUtils;
import ru.hts.springwebdoclet.processors.AnnotationProcessor;
import ru.hts.springwebdoclet.processors.ParameterProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processes method parameters
 * @author Ivan Sungurov
 */
public class ParameterProcessorImpl implements ParameterProcessor {

    private AnnotationProcessor annotationProcessor;

    @Override
    public List<RenderContext> process(MethodDoc methodDoc) {
        Map<String, String> descriptionMap = new HashMap<String, String>();
        for (Tag tag : methodDoc.tags("param")) {
            ParamTag paramTag = (ParamTag) tag;
            descriptionMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }

        List<RenderContext> result = new ArrayList<RenderContext>();

        for (Parameter paramDoc : methodDoc.parameters()) {
            RenderContext param = new RenderContext();

            param.putAll(annotationProcessor.process(paramDoc.annotations()));

            String name = (String) param.get("name");
            if (name == null) {
                continue;
            }

            param.put("description", descriptionMap.get(paramDoc.name()));
            param.put("type", JavadocUtils.formatTypeName(paramDoc.type()));

            result.add(param);
        }

        return result;
    }

    public void setAnnotationProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }
}
