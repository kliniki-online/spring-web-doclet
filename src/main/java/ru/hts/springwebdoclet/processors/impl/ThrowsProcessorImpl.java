package ru.hts.springwebdoclet.processors.impl;

import com.sun.javadoc.*;
import org.apache.commons.lang3.StringUtils;
import ru.hts.springwebdoclet.processors.ThrowsProcessor;
import ru.hts.springwebdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes exceptions which method throws
 * @author Ivan Sungurov
 */
public class ThrowsProcessorImpl implements ThrowsProcessor {
    @Override
    public void init(RootDoc rootDoc) {
    }

    @Override
    public List<RenderContext> process(ClassDoc classDoc, MethodDoc methodDoc) {
        List<RenderContext> result = new ArrayList<RenderContext>();

        for (Tag tag : methodDoc.tags("throws")) {
            ThrowsTag throwsTag = (ThrowsTag) tag;
            RenderContext exception = new RenderContext();

            exception.put("type", throwsTag.exceptionName());

            ClassDoc exceptionClassDoc = classDoc.findClass(throwsTag.exceptionType().qualifiedTypeName());
            String description = StringUtils.isNotEmpty(throwsTag.exceptionComment()) ?
                    throwsTag.exceptionComment() :
                    exceptionClassDoc.commentText();
            exception.put("description", description);

            result.add(exception);
        }

        return result;
    }
}
