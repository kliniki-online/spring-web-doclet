package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface ControllerProcessor {
    RenderContext process(ClassDoc classDoc);
}
