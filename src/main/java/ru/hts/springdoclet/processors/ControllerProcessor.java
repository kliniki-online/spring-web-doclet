package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.render.RenderContext;

import java.util.*;

/**
 * Processes controller class
 * @author Ivan Sungurov
 */
public class ControllerProcessor {
    private MethodProcessor methodProcessor;

    public ControllerProcessor(MethodProcessor methodProcessor) {
        this.methodProcessor = methodProcessor;
    }

    public RenderContext process(ClassDoc classDoc) {
        RenderContext result = new RenderContext();

        result.put("package", classDoc.containingPackage().name());
        result.put("name", classDoc.typeName());
        result.put("title", classDoc.commentText());

        List<Map<String, Object>> methods = new ArrayList<Map<String, Object>>();
        for (MethodDoc methodDoc : classDoc.methods()) {
            methods.add(methodProcessor.process(classDoc, methodDoc));
        }

        Collections.sort(methods, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int comp1 = o1.get("url").toString().compareTo(o2.get("url").toString());
                return comp1 != 0 ? comp1 : o1.get("method").toString().compareTo(o2.get("method").toString());
            }
        });

        result.put("methods", methods);

        return result;
    }
}
