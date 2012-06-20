package ru.hts.springdoclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import ru.hts.springdoclet.annotation.RequestMappingAnnotationHandler;
import ru.hts.springdoclet.annotation.RequestParamAnnotationHandler;
import ru.hts.springdoclet.annotation.RolesAllowedAnnotationHandler;
import ru.hts.springdoclet.processors.*;
import ru.hts.springdoclet.render.FreemarkerJavadocRenderer;
import ru.hts.springdoclet.render.RenderContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Javadoc Doclet which allows to collect information about Spring Web controllers
 * @author Ivan Sungurov
 */
public class SpringDoclet {

    private FreemarkerJavadocRenderer renderer;
    private ControllerProcessor controllerProcessor;

    public SpringDoclet(ControllerProcessor controllerProcessor, FreemarkerJavadocRenderer renderer) {
        this.renderer = renderer;
        this.controllerProcessor = controllerProcessor;
    }

    public void configure(String[][] options) {
        for (String[] opt : options) {
            String optName = opt[0];
            String optValue = opt.length > 1 ? opt[1] : null;

            if ("-d".equals(optName)) {
                renderer.setOutputDir(optValue);
            } else if ("-windowtitle".equals(optName)) {
                renderer.setWindowTitle(optValue);
            } else if ("-stylesheetfile".equals(optName)) {
                renderer.setStylesheetFile(optValue);
            }
        }
    }

    public boolean process(RootDoc root) {
        Map<String, List<RenderContext>> packageMap = new TreeMap<String, List<RenderContext>>();

        for (ClassDoc classDoc : root.classes()) {
            if (classDoc.containingClass() != null) {
                continue;
            }

            RenderContext context = controllerProcessor.process(classDoc);

            String packageName = context.get("package").toString();
            List<RenderContext> controllers = packageMap.get(packageName);
            if (controllers == null) {
                controllers = new ArrayList<RenderContext>();
                packageMap.put(packageName, controllers);
            }

            controllers.add(context);
        }

        try {
            renderer.render(packageMap);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean start(RootDoc root) {
        FreemarkerJavadocRenderer renderer = new FreemarkerJavadocRenderer();

        AnnotationProcessor methodAnnotationProcessor = new AnnotationProcessor();
        methodAnnotationProcessor.addAnnotationHandler(new RequestMappingAnnotationHandler());
        methodAnnotationProcessor.addAnnotationHandler(new RolesAllowedAnnotationHandler());

        AnnotationProcessor paramAnnotationProcessor = new AnnotationProcessor();
        paramAnnotationProcessor.addAnnotationHandler(new RequestParamAnnotationHandler());
        ParameterProcessor parameterProcessor = new ParameterProcessor(paramAnnotationProcessor);
        ReturnProcessor returnProcessor = new ReturnProcessor(new FieldProcessor());

        MethodProcessor methodProcessor = new MethodProcessor(parameterProcessor, methodAnnotationProcessor, returnProcessor, new ThrowsProcessor());
        ControllerProcessor controllerProcessor = new ControllerProcessor(methodProcessor);

        SpringDoclet doclet = new SpringDoclet(controllerProcessor, renderer);
        doclet.configure(root.options());
        return doclet.process(root);
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    public static int optionLength(String option) {
        if ("-d".equals(option) || "-windowtitle".equals(option) || "-stylesheetfile".equals(option) || "-author".equals(option)) {
            return 2;
        } else {
            return 0;
        }
    }
}
