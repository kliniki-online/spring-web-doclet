package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface ParameterProcessor {
    void init(RootDoc rootDoc);

    List<RenderContext> process(MethodDoc methodDoc);
}
