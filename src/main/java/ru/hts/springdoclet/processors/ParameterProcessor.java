package ru.hts.springdoclet.processors;

import com.sun.javadoc.MethodDoc;
import ru.hts.springdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface ParameterProcessor {
    List<RenderContext> process(MethodDoc methodDoc);
}
