package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface ControllerProcessor {
    void init(RootDoc rootDoc);

    RenderContext process(ClassDoc classDoc);
}
