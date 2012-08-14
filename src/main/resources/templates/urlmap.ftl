[#ftl]

[#assign title]${i18n.getText('nav.urlmap')}[/#assign]

[#assign content]
<nav>
    <a href="${indexPath}">${i18n.getText('nav.controllers')}</a>
</nav>
<h2>${i18n.getText('nav.urlmap')}</h2>
<ul class="url-list">
    [#list methods as method]
        <li>
            <a href="${method['controllerLink']}#${method['anchor']}">
            ${method['url']} (${method['method']})</a> &ndash; ${method['description']}
        </li>
    [/#list]
</ul>
[/#assign]

[#include template/]