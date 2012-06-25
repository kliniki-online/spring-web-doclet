package ru.hts.springdoclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;
import org.apache.commons.lang3.ClassUtils;

import java.util.Collection;

/** @author Ivan Sungurov */
public class JavadocUtils {
    public static Object getAnnotationParamValue(AnnotationDesc.ElementValuePair param) {
        AnnotationValue[] values = (AnnotationValue[]) param.value().value();
        return values[0].value();
    }

    public static String formatTypeName(Type type) {
        String typeName;
        Class classType = ReflectionUtils.getRequiredClass(type.qualifiedTypeName());

        if (type.isPrimitive()) {
            typeName = ClassUtils.primitiveToWrapper(classType).getSimpleName();
        } else {
            typeName = type.simpleTypeName();
        }

        if (Collection.class.isAssignableFrom(classType)) {
            ParameterizedType parameterizedType = type.asParameterizedType();
            typeName = parameterizedType.typeArguments()[0].simpleTypeName() + "[]";
        } else {
            typeName += type.dimension();
        }

        return typeName;
    }
}
