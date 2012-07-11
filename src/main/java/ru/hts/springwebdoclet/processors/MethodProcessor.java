package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface MethodProcessor {
    void init(RootDoc rootDoc);

    RenderContext process(ClassDoc classDoc, MethodDoc methodDoc);
}
