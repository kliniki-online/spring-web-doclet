[#ftl]

[#assign title]Карта URL'ов[/#assign]

[#assign content]
<nav>
    <a href="${indexPath}">Контроллеры</a>
</nav>
<h2>Карта URL'ов</h2>
<ul>
    [#list methods as method]
        <li>
            <a href="${method['controllerLink']}#${method['anchor']}">
            ${method['url']} (${method['method']})</a> &ndash; ${method['description']}
        </li>
    [/#list]
</ul>
[/#assign]

[#include template/]