[#ftl]

[#assign title]Контроллеры[/#assign]

[#assign content]
    [#list packages?keys as packageName]
    <h2>${packageName}</h2>
    <ul>
        [#list packages[packageName] as controller]
            <li>
                <a href="${controller['path']}/${controller['name']}.html">
                ${controller['title']} (${controller['name']})
                </a>
            </li>
        [/#list]
    </ul>
    [/#list]
[/#assign]

[#include template /]