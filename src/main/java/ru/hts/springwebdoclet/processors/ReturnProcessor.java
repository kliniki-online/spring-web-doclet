package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface ReturnProcessor {
    RenderContext process(ClassDoc classDoc, MethodDoc methodDoc);
}
