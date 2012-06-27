SpringDoclet
============
SpringDoclet is an extension to Javadoc utility (Doclet) which generates web API documentation for backends based on
Spring Web MVC framework: <http://static.springsource.org/spring/docs/current/spring-framework-reference/html/mvc.html>.

Output documentation structure
==============================

Index file structure
--------------------
Package title (package fully qualified name)
* Controller title (controller simple name)

Package title will be taken from standard package description file (package.html, package-info.html or package-info.java).
Controller title will be taken from controller class Javadoc comment

Controller file structure
-------------------------
Controller title (controller simple name)
* METHOD: /url/<br/>
  (from @RequestMapping annotation)

  Description<br/>
  (from method Javadoc comment)

  Access rights:<br/>
  (from @RolesAllowed annotation)
  * Role1
  * Role2

  Input parameters:<br/>
  | Parameter name | Type | Required? | Description |<br/>
  (from Javadoc comment and @param tag and from @RequestParam/@PathVariable annotations)

  Return object:<br/>
  | Field name | Type | Description |<br/>
  (from return class fields Javadoc comments)

  Errors:<br/>
  | Exception type | Description |<br/>
  (from Javadoc @throws tag. If tag description is empty description will be taken from exception class Javadoc)

Documentation example
---------------------
```java
/**
 * Articles
 */
@Controller
@RolesAllowed("ROLE_MANAGER")
public class ArticleController {

    /* ... */

    /**
     * Returns article list
     * @param page  page number
     * @param limit items per page
     * @throws InvalidPageNumberException if page number is invalid
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public GetArticlesResponse getArticles(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) throws InvalidPageNumberException {
        GetArticlesResponse response = new GetArticlesResponse();

        /* ... */

        return response;
    }

    public static class GetArticlesResponse {
        public static class Article {
            /** article ID */
            public Integer id;

            /** article title */
            public String title;

            /** article URI */
            public String uri;

            /** article content */
            public String text;
        }

        /** articles list */
        public List<Article> articles = new ArrayList<Article>();

        /** total articles count */
        public Long totalRecords;
    }

    /* ... */
}
```

Configuration
=============
See 'maven-javadoc-plugin' documentation: <http://maven.apache.org/plugins/maven-javadoc-plugin/javadoc-mojo.html>
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
<project ...>
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
                            <groupId>ru.kliniki-online</groupId>
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
</project>
```

License
=======
GNU General Public License, version 3 and better: <http://www.gnu.org/copyleft/gpl.html>