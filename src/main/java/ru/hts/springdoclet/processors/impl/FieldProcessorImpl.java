package ru.hts.springdoclet.processors.impl;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;
import ru.hts.springdoclet.JavadocUtils;
import ru.hts.springdoclet.ReflectionUtils;
import ru.hts.springdoclet.processors.FieldProcessor;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Processes class fields
 * @author Ivan Sungurov
 */
public class FieldProcessorImpl implements FieldProcessor {

    @Override
    public List<RenderContext> process(ClassDoc classDoc) {
        List<RenderContext> result = new ArrayList<RenderContext>();

        for (FieldDoc fieldDoc : classDoc.fields()) {
            RenderContext field = new RenderContext();

            field.put("name", fieldDoc.name());
            field.put("description", fieldDoc.commentText());
            field.put("type", JavadocUtils.formatTypeName(fieldDoc.type()));

            Class type = ReflectionUtils.getOptionalClass(fieldDoc.type().qualifiedTypeName());

            if (type != null) {
                if (Collection.class.isAssignableFrom(type)) {
                    Type[] collectionType = fieldDoc.type().asParameterizedType().typeArguments();
                    if (collectionType.length != 0) {
                        ClassDoc collectionClassDoc = classDoc.findClass(collectionType[0].qualifiedTypeName());
                        field.put("child", process(collectionClassDoc));
                        field.put("type", "List");
                    }

                } else {
                    String packageName = (type.getPackage() != null) ? type.getPackage().getName() : null;
                    if ((packageName != null) && !packageName.startsWith("java.")) {
                        ClassDoc fieldClassDoc = classDoc.findClass(fieldDoc.type().qualifiedTypeName());
                        field.put("child", process(fieldClassDoc));
                        field.put("type", "Object");

                    }
                }
            }

            result.add(field);
        }

        return result;
    }
}
