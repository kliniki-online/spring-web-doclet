package ru.hts.springdoclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import ru.hts.springdoclet.processors.ControllerProcessor;
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

    private static final String CONTROLLER_CLASS = Controller.class.getCanonicalName();

    private FreemarkerJavadocRenderer renderer;

    private ControllerProcessor controllerProcessor;

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
            if (!JavadocUtils.hasAnnotation(classDoc, CONTROLLER_CLASS)) {
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
        return start(root, null);
    }

    public static boolean start(RootDoc root, String customContext) {
        GenericApplicationContext appContext = new GenericApplicationContext();
        XmlBeanDefinitionReader beanReader = new XmlBeanDefinitionReader(appContext);
        beanReader.loadBeanDefinitions(new ClassPathResource("application-context.xml"));

        if (customContext != null) {
            beanReader.loadBeanDefinitions(new ClassPathResource(customContext));
        }

        SpringDoclet doclet = (SpringDoclet) appContext.getBean("springDoclet");
        doclet.configure(root.options());
        return doclet.process(root);
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    public static int optionLength(String option) {
        if ("-d".equals(option) || "-windowtitle".equals(option) || "-stylesheetfile".equals(option)) {
            return 2;
        } else {
            return 0;
        }
    }


    public static void main(String[] args) {
        Main.execute("javadoc", "ru.hts.springdoclet.SpringDoclet", new String[]{
                "-public",
                "-sourcepath",
                "/home/ivan/projects/kliniki-online/src/main/java/",
                "-subpackages",
                "ru.hts.kliniki",
                "-windowtitle",
                "Kliniki Online Web API",
        });
    }

    public void setRenderer(FreemarkerJavadocRenderer renderer) {
        this.renderer = renderer;
    }

    public void setControllerProcessor(ControllerProcessor controllerProcessor) {
        this.controllerProcessor = controllerProcessor;
    }
}
