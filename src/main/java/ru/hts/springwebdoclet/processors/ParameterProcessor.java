package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.MethodDoc;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface ParameterProcessor {
    List<RenderContext> process(MethodDoc methodDoc);
}
