package ru.hts.springwebdoclet;

import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.hts.springwebdoclet.processors.PackageProcessor;
import ru.hts.springwebdoclet.render.FreemarkerJavadocRenderer;
import ru.hts.springwebdoclet.render.RenderContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Javadoc Doclet which allows to collect information about Spring Web controllers
 * @author Ivan Sungurov
 */
public class SpringWebDoclet {

    private FreemarkerJavadocRenderer renderer;

    private PackageProcessor packageProcessor;

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
            } else if ("-docencoding".equals(optName)) {
                renderer.setOutputEncoding(optValue);
            }
        }

        if (renderer.getStylesheetFile() == null) {
            renderer.setStylesheetFile(getDefaultStylesheetFile());
        }
    }

    private String getDefaultStylesheetFile() {
        try {
            File tmpSpreedsheet = File.createTempFile("spring-web-doclet", ".css");
            tmpSpreedsheet.deleteOnExit();

            InputStream is = getClass().getResourceAsStream("/style.css");
            OutputStream os = new FileOutputStream(tmpSpreedsheet);

            IOUtils.copy(is, os);

            return tmpSpreedsheet.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean process(RootDoc root) {
        List<RenderContext> packageContextList = new ArrayList<RenderContext>();

        packageProcessor.init(root);

        for (PackageDoc packageDoc : root.specifiedPackages()) {
            RenderContext packageContext = packageProcessor.process(packageDoc);
            if (packageContext != null) {
                packageContextList.add(packageContext);
            }
        }

        try {
            renderer.render(packageContextList);
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

        SpringWebDoclet doclet = (SpringWebDoclet) appContext.getBean("springWebDoclet");
        doclet.configure(root.options());
        return doclet.process(root);
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    public static int optionLength(String option) {
        if ("-d".equals(option) ||
                "-windowtitle".equals(option) ||
                "-stylesheetfile".equals(option) ||
                "-docencoding".equals(option)) {
            return 2;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Main.execute("javadoc", SpringWebDoclet.class.getCanonicalName(), args);
    }

    public void setRenderer(FreemarkerJavadocRenderer renderer) {
        this.renderer = renderer;
    }

    public void setPackageProcessor(PackageProcessor packageProcessor) {
        this.packageProcessor = packageProcessor;
    }
}
