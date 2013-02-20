package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.MethodDoc;
import ru.hts.springwebdoclet.render.RenderContext;

/** @author Ivan Sungurov */
public interface ParameterProcessor {

    RenderContext process(MethodDoc methodDoc);
}
