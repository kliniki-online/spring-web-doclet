package ru.hts.springwebdoclet.processors;

import com.sun.javadoc.ClassDoc;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface FieldProcessor {
    List<RenderContext> process(ClassDoc classDoc);
}
