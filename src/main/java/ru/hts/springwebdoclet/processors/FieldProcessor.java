package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface FieldProcessor {
    void init(RootDoc rootDoc);

    List<RenderContext> process(ClassDoc classDoc);
}
