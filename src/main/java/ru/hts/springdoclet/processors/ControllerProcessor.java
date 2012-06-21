package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import ru.hts.springdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface ControllerProcessor {
    RenderContext process(ClassDoc classDoc);
}
