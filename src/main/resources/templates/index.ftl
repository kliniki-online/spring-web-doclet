[#ftl]

[#assign title]Контроллеры[/#assign]

[#assign content]
<nav>
    <a href="${urlmapPath}">Карта URL'ов</a>
</nav>
    [#list packages?keys as packageName]
    <h2 class="package">${packageName}</h2>
    <ul class="controller-list">
        [#list packages[packageName] as controller]
            <li>
                <a href="${controller['link']}">${controller['title']} (${controller['name']})</a>
            </li>
        [/#list]
    </ul>
    [/#list]
[/#assign]

[#include template /]