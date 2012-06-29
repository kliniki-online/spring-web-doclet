package ru.hts.springwebdoclet;

import com.sun.javadoc.*;
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
        Class classType = ReflectionUtils.getOptionalClass(type.qualifiedTypeName());

        if (type.isPrimitive()) {
            typeName = ClassUtils.primitiveToWrapper(classType).getSimpleName();
        } else {
            typeName = type.simpleTypeName();
        }

        if ((classType != null) && Collection.class.isAssignableFrom(classType)) {
            ParameterizedType parameterizedType = type.asParameterizedType();
            typeName = parameterizedType.typeArguments()[0].simpleTypeName() + "[]";
        } else {
            typeName += type.dimension();
        }

        return typeName;
    }

    public static boolean hasAnnotation(ClassDoc classDoc, String qualifiedType) {
        try {
            for (AnnotationDesc annotationDesc : classDoc.annotations()) {
                if (annotationDesc.annotationType().qualifiedTypeName().equals(qualifiedType)) {
                    return true;
                }
            }
        } catch (ClassCastException e) {
            // happens when annotation class is not in classpath
            // since we have no access to compiled classes just ignore it
        }
        return false;
    }
}
