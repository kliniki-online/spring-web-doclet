package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.*;
import ru.hts.springwebdoclet.JavadocUtils;
import ru.hts.springwebdoclet.ReflectionUtils;
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

    private List<String> specifiedPackages = new ArrayList<String>();

    @Override
    public void init(RootDoc rootDoc) {
        for (PackageDoc packageDoc : rootDoc.specifiedPackages()) {
            specifiedPackages.add(packageDoc.name());
        }
    }

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
                    if ((packageName != null) && isSpecifiedPackage(packageName)) {
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

    private boolean isSpecifiedPackage(String packageName) {
        for (String specifiedPackage : specifiedPackages) {
            if (packageName.startsWith(specifiedPackage)) {
                return true;
            }
        }
        return false;
    }
}
