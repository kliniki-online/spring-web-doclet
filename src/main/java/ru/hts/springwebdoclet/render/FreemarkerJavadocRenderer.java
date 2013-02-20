package ru.hts.springwebdoclet.render;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import ru.hts.springwebdoclet.MethodContextComparator;
import ru.hts.springwebdoclet.util.MultiMap;

import java.io.*;
import java.util.*;

/**
 * Renders collected information about web API using FreeMarker templates
 * @author Ivan Sungurov
 */
public class FreemarkerJavadocRenderer implements JavadocRenderer {
    private static final String STYLESHEET_TARGET_FILE = "style.css";
    private static final String INDEX_FILE = "index.html";
    private static final String URLMAP_FILE = "urlmap.html";

    private MessageSource messageSource;

    private String template = "base.ftl";
    private String outputDir = "webapi";
    private String windowTitle = "Web API";
    private String stylesheetFile;
    private String outputEncoding = "UTF-8";
    private Locale locale = Locale.ENGLISH;

    @Override
    public boolean render(List<RenderContext> packages) throws IOException {
        Configuration config = new Configuration();
        config.setDefaultEncoding("UTF-8");
        config.setOutputEncoding(outputEncoding);
        config.setClassForTemplateLoading(getClass(), "/templates/");

        if (stylesheetFile != null) {
            FileUtils.copyFile(new File(stylesheetFile), new File(outputDir + "/" + STYLESHEET_TARGET_FILE));
        }

        try {
            new File(outputDir).mkdir();

            List<RenderContext> methodList = new ArrayList<RenderContext>();

            for (RenderContext packageContext : packages) {
                String subDir = packageContext.get("name").toString().replaceAll("\\.", "/");
                String baseDir = outputDir + '/' + subDir;
                FileUtils.forceMkdir(new File(baseDir));

                StringBuilder basePath = new StringBuilder();
                int subDirLevel = StringUtils.countMatches(subDir, "/") + 1;
                for (int i = 0; i < subDirLevel; i++) {
                    basePath.append("../");
                }

                for (RenderContext controllerContext : (List<RenderContext>) packageContext.get("controllers")) {
                    String name = controllerContext.get("name").toString();

                    String link = subDir + '/' + name + ".html";
                    controllerContext.put("link", link);

                    MultiMap<String, RenderContext> requestMethodMap = new MultiMap<String, RenderContext>();

                    for (RenderContext methodContext : (List<RenderContext>) controllerContext.get("methods")) {
                        methodContext.put("controllerLink", link);
                        methodContext.put("anchor", methodContext.get("method") + ":" + methodContext.get("url"));
                        methodList.add(methodContext);
                        requestMethodMap.putItem((String) methodContext.get("method"), methodContext);
                    }

                    controllerContext.put("requestMethods", requestMethodMap);

                    RenderContext controllerPageContext = createBaseContext(config, basePath.toString());
                    controllerPageContext.put("controller", controllerContext);
                    controllerPageContext.put("template", template);

                    renderTemplate(config, "controller.ftl", controllerPageContext, link);
                }
            }

            Collections.sort(methodList, new MethodContextComparator());
            RenderContext urlmapContext = createBaseContext(config, "./");
            urlmapContext.put("methods", methodList);
            renderTemplate(config, "urlmap.ftl", urlmapContext, URLMAP_FILE);

            RenderContext indexContext = createBaseContext(config, "./");
            indexContext.put("packages", packages);
            renderTemplate(config, "index.ftl", indexContext, INDEX_FILE);
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private RenderContext createBaseContext(Configuration config, String baseDir) {
        RenderContext context = new RenderContext();
        context.put("template", template);
        context.put("windowTitle", windowTitle);
        context.put("indexPath", baseDir + INDEX_FILE);
        context.put("urlmapPath", baseDir + URLMAP_FILE);
        context.put("charset", config.getOutputEncoding());
        context.put("i18n", new MessageProvider(messageSource, locale));
        if (stylesheetFile != null) {
            context.put("stylesheet", baseDir + STYLESHEET_TARGET_FILE);
        }
        return context;
    }

    private void renderTemplate(Configuration config, String templateName, Map<String, Object> context, String outputFilename) throws IOException, TemplateException {
        Template listTemplate = config.getTemplate(templateName);

        Writer out = new OutputStreamWriter(new FileOutputStream(outputDir + '/' + outputFilename), outputEncoding);
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

    public String getStylesheetFile() {
        return stylesheetFile;
    }

    public void setStylesheetFile(String stylesheetFile) {
        this.stylesheetFile = stylesheetFile;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
