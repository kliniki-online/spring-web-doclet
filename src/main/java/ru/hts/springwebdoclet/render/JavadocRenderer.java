package ru.hts.springwebdoclet.render;

import java.io.IOException;
import java.util.List;

/**
 * Renders collected information about web API
 * @author Ivan Sungurov
 */
public interface JavadocRenderer {
    boolean render(List<RenderContext> packages) throws IOException;

    void setOutputDir(String outputDir);
}
