package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface MethodProcessor {
    RenderContext process(ClassDoc classDoc, MethodDoc methodDoc);
}
