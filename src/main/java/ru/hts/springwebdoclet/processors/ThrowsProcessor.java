package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface ThrowsProcessor {
    List<RenderContext> process(ClassDoc classDoc, MethodDoc methodDoc);
}
