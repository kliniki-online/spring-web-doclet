package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;
import org.apache.commons.lang3.ClassUtils;
import ru.hts.springdoclet.JavadocUtils;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Processes class fields
 * @author Ivan Sungurov
 */
public class FieldProcessor {

    public List<RenderContext> process(ClassDoc classDoc) {
        List<RenderContext> result = new ArrayList<RenderContext>();

        for (FieldDoc fieldDoc : classDoc.fields()) {
            RenderContext field = new RenderContext();

            field.put("name", fieldDoc.name());
            field.put("type", JavadocUtils.formatTypeName(fieldDoc.type()));
            field.put("description", fieldDoc.commentText());

            Class type;
            try {
                type = ClassUtils.getClass(fieldDoc.type().qualifiedTypeName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (Collection.class.isAssignableFrom(type)) {
                Type[] collectionType = fieldDoc.type().asParameterizedType().typeArguments();
                if (collectionType.length != 0) {
                    ClassDoc collectionClassDoc = classDoc.findClass(collectionType[0].qualifiedTypeName());
                    field.put("list", process(collectionClassDoc));
                }
            }

            result.add(field);
        }

        return result;
    }
}
