SpringDoclet
============
SpringDoclet is an extension to Javadoc utility (Doclet) which generates
web API documentation for backends based on
[Spring Web MVC]:http://static.springsource.org/spring/docs/current/spring-framework-reference/html/mvc.html
framework.

Usage
=====
See 'maven-javadoc-plugin' documentation: http://maven.apache.org/plugins/maven-javadoc-plugin/javadoc-mojo.html
Documentation can be built with javadoc:javadoc goal.

Additional parameters
---------------------
<table>
    <tr>
        <td>-d</td>
        <td>specifies output directory for generated documentation</td>
    </tr>
    <tr>
        <td>-stylesheetfile</td>
        <td>specifies stylesheet file which will be attached to generated documentation</td>
    </tr>
    <tr>
        <td>-windowtitle</td>
        <td>sets window base title</td>
    </tr>
</table>

Example
-------
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>2.8.1</version>
            <configuration>
                <subpackages>org.springframework.samples.petclinic.web</subpackages>
                <quiet>true</quiet>
                <show>public</show>
                <doclet>ru.hts.springdoclet.SpringDoclet</doclet>
                <docletArtifacts>
                    <docletArtifact>
                        <groupId>ru.hts</groupId>
                        <artifactId>spring-doclet</artifactId>
                        <version>0.1</version>
                    </docletArtifact>
                </docletArtifacts>

                <!-- useStandardDocletOptions=false is required -->
                <useStandardDocletOptions>false</useStandardDocletOptions>

                <additionalparam>
                    -stylesheetfile style.css
                    -windowtitle "Web API"
                </additionalparam>
            </configuration>
        </plugin>
    </plugins>
</build>
```

License
=======
[GNU General Public License v3 and greater]: http://www.gnu.org/copyleft/gpl.html