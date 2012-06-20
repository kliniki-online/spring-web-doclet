package ru.hts.springdoclet.processors;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;
import ru.hts.springdoclet.JavadocUtils;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processes method parameters
 * @author Ivan Sungurov
 */
public class ParameterProcessor {

    private AnnotationProcessor annotationProcessor;

    public ParameterProcessor(AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    public List<RenderContext> process(MethodDoc methodDoc) {
        Map<String, String> descriptionMap = new HashMap<String, String>();
        for (Tag tag : methodDoc.tags("param")) {
            ParamTag paramTag = (ParamTag) tag;
            descriptionMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }

        List<RenderContext> result = new ArrayList<RenderContext>();

        for (Parameter paramDoc : methodDoc.parameters()) {
            RenderContext param = new RenderContext();

            param.put("type", JavadocUtils.formatTypeName(paramDoc.type()));

            param.putAll(annotationProcessor.process(paramDoc.annotations()));

            param.put("description", descriptionMap.get(param.get("name")));

            result.add(param);
        }

        return result;
    }
}
