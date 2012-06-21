package ru.hts.springdoclet.processors;

import com.sun.javadoc.ClassDoc;
import ru.hts.springdoclet.render.RenderContext;

import java.util.List;

/** @author Ivan Sungurov */
public interface FieldProcessor {
    List<RenderContext> process(ClassDoc classDoc);
}
