package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;
import ru.hts.springwebdoclet.Config;
import ru.hts.springwebdoclet.JavadocUtils;
import ru.hts.springwebdoclet.ReflectionUtils;
import ru.hts.springwebdoclet.processors.AnnotationProcessor;
import ru.hts.springwebdoclet.processors.FieldProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Processes class fields
 * @author Ivan Sungurov
 */
public class FieldProcessorImpl implements FieldProcessor {

    private Config config;
    private AnnotationProcessor<FieldDoc> annotationProcessor;

    @Override
    public List<RenderContext> process(ClassDoc classDoc) {
        List<RenderContext> result = new ArrayList<RenderContext>();

        for (FieldDoc fieldDoc : classDoc.fields()) {
            RenderContext field = new RenderContext();

            field.put("name", fieldDoc.name());
            field.put("description", fieldDoc.commentText());
            field.put("type", JavadocUtils.formatTypeName(fieldDoc.type()));

            Class fieldClass = ReflectionUtils.getOptionalClass(fieldDoc.type().qualifiedTypeName());

            if ((fieldClass != null) && (Collection.class.isAssignableFrom(fieldClass))) {
                Type[] collectionTypes = fieldDoc.type().asParameterizedType().typeArguments();
                if (collectionTypes.length != 0) {
                    Type collectionType = collectionTypes[0];
                    if (shouldBeProcessed(collectionType)) {
                        ClassDoc collectionClassDoc = classDoc.findClass(collectionType.qualifiedTypeName());
                        if (!classDoc.equals(collectionClassDoc)) {
                            field.put("child", process(collectionClassDoc));
                            field.put("type", "List");
                        }
                    }
                }
            } else {
                if (shouldBeProcessed(fieldDoc.type())) {
                    ClassDoc fieldClassDoc = classDoc.findClass(fieldDoc.type().qualifiedTypeName());
                    if (!classDoc.equals(fieldClassDoc)) {
                        field.put("child", process(fieldClassDoc));
                        field.put("type", "Object");
                    }
                }
            }

            field.putAll(annotationProcessor.process(fieldDoc.annotations(), fieldDoc));

            result.add(field);
        }

        return result;
    }

    private boolean shouldBeProcessed(Type type) {
        int lastDotIndex = type.qualifiedTypeName().lastIndexOf('.');
        if (lastDotIndex == -1) {
            return false;
        }

        String packageName = type.qualifiedTypeName().substring(0, lastDotIndex);
        return isSpecifiedPackage(packageName);
    }

    private boolean isSpecifiedPackage(String packageName) {
        for (PackageDoc specifiedPackage : config.getSpecifiedPackages()) {
            if (packageName.startsWith(specifiedPackage.name())) {
                return true;
            }
        }
        return false;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setAnnotationProcessor(AnnotationProcessor<FieldDoc> annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }
}
