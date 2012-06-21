[#ftl]

[#assign title]Контроллеры[/#assign]

[#assign content]
<nav>
    <a href="${urlmapPath}">Карта URL'ов</a>
</nav>
    [#list packages?keys as packageName]
    <h2>${packageName}</h2>
    <ul>
        [#list packages[packageName] as controller]
            <li>
                <a href="${controller['link']}">${controller['title']} (${controller['name']})</a>
            </li>
        [/#list]
    </ul>
    [/#list]
[/#assign]

[#include template /]