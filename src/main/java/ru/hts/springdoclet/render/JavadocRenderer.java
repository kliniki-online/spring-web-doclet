package ru.hts.springdoclet.render;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Renders collected information about web API
 * @author Ivan Sungurov
 */
public interface JavadocRenderer {
    boolean render(Map<String, List<RenderContext>> packages) throws IOException;

    void setOutputDir(String outputDir);
}
