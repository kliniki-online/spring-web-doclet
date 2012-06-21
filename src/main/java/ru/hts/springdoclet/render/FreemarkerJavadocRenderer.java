package ru.hts.springdoclet.render;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Renders collected information about web API using FreeMarker templates
 * @author Ivan Sungurov
 */
@Component
public class FreemarkerJavadocRenderer implements JavadocRenderer {
    private static final String STYLESHEET_TARGET_FILE = "style.css";

    private String template = "base.ftl";
    private String outputDir = "webapi";
    private String windowTitle = "Web API";
    private String stylesheetFile;

    @Override
    public boolean render(Map<String, List<RenderContext>> packages) throws IOException {
        Configuration config = new Configuration();
        config.setDefaultEncoding("UTF-8");
        config.setClassForTemplateLoading(getClass(), "/templates/");

        RenderContext listContext = createBaseContext(".");
        listContext.put("packages", packages);

        if (stylesheetFile != null) {
            FileUtils.copyFile(new File(stylesheetFile), new File(outputDir + "/" + STYLESHEET_TARGET_FILE));
        }

        try {
            new File(outputDir).mkdir();

            for (String packageName : packages.keySet()) {
                String subDir = packageName.replaceAll("\\.", "/");
                String baseDir = outputDir + '/' + subDir;
                FileUtils.forceMkdir(new File(baseDir));

                StringBuilder basePath = new StringBuilder();
                int subDirLevel = StringUtils.countMatches(subDir, "/") + 1;
                for (int i = 0; i < subDirLevel; i++) {
                    basePath.append("../");
                }

                for (Map context : packages.get(packageName)) {
                    RenderContext controllerContext = createBaseContext(basePath.toString());
                    String name = context.get("name").toString();
                    controllerContext.put("controller", context);
                    controllerContext.put("template", template);
                    controllerContext.put("indexPath", basePath + "index.html");
                    context.put("path", subDir);
                    renderTemplate(config, "controller.ftl", controllerContext, baseDir + "/" + name + ".html");
                }
            }

            renderTemplate(config, "index.ftl", listContext, outputDir + '/' + "index.html");
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private RenderContext createBaseContext(String baseDir) {
        RenderContext context = new RenderContext();
        context.put("template", template);
        context.put("windowTitle", windowTitle);
        if (stylesheetFile != null) {
            context.put("stylesheet", baseDir + "/" + STYLESHEET_TARGET_FILE);
        }
        return context;
    }

    private void renderTemplate(Configuration config, String templateName, Map<String, Object> context, String outputFilename) throws IOException, TemplateException {
        Template listTemplate = config.getTemplate(templateName);

        Writer out = new FileWriter(outputFilename);
        try {
            listTemplate.process(context, out);
        } finally {
            out.close();
        }
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public void setStylesheetFile(String stylesheetFile) {
        this.stylesheetFile = stylesheetFile;
    }
}
