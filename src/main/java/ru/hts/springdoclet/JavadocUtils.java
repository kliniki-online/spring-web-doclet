package ru.hts.springdoclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.Type;
import org.apache.commons.lang3.ClassUtils;

/** @author Ivan Sungurov */
public class JavadocUtils {
    public static Object getAnnotationParamValue(AnnotationDesc.ElementValuePair param) {
        AnnotationValue[] values = (AnnotationValue[]) param.value().value();
        return values[0].value();
    }

    public static String formatTypeName(Type type) {
        String typeName = type.simpleTypeName();
        if (type.isPrimitive()) {
            try {
                typeName = ClassUtils.primitiveToWrapper(ClassUtils.getClass(typeName)).getSimpleName();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return typeName + type.dimension();
    }
}
