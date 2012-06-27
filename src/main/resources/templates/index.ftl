[#ftl]

[#assign title]Контроллеры[/#assign]

[#assign content]
<nav>
    <a href="${urlmapPath}">Карта URL'ов</a>
</nav>
    [#list packages as package]
    <h2 class="package">${package['overview']} (${package['name']})</h2>
    <ul class="controller-list">
        [#list package['controllers'] as controller]
            <li>
                <a href="${controller['link']}">${controller['title']} (${controller['name']})</a>
            </li>
        [/#list]
    </ul>
    [/#list]
[/#assign]

[#include template /]