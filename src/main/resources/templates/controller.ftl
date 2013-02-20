[#ftl]

[#assign title]${controller['title']}[/#assign]

[#assign content]
<nav>
    <a href="${indexPath}">${i18n.getText('nav.controllers')}</a>
    <a href="${urlmapPath}">${i18n.getText('nav.urlmap')}</a>
</nav>

<h2>${controller['title']} (${controller['name']})</h2>

<div class="toc">
    [#list controller['requestMethods']?keys?sort as requestMethod]
        <h3>${requestMethod}</h3>
        <ul>
            [#list controller['requestMethods'][requestMethod] as method]
                <li>
                    <a href="#${method['anchor']}">${method['url']}</a>
                </li>
            [/#list]
        </ul>
    [/#list]
</div>
<div class="clear"></div>

<ul>
    [#list controller['methods'] as method]
        <li>
            <h3><a class="anchor" name="${method['anchor']}">${method['method']}: ${method['url']}</a></h3>

            <p>${method['description']}</p>

            [#if method['roles']?? && (method['roles']?size != 0)]
                <h4>${i18n.getText('controller.allowed.roles')}</h4>
                <ul class="roles">
                    [#list method['roles'] as role]
                        <li>${role}</li>
                    [/#list]
                </ul>
            [/#if]

            [#if method['parameters']['list']?size != 0]
                [#if method['method'] == 'POST']
                    <h4>${i18n.getText('controller.input.type')}</h4>

                    <p>${method['parameters']['type']}</p>
                [/#if]
                <h4>${i18n.getText('controller.input')}</h4>
                <table class="parameters">
                    <tr>
                        <th>${i18n.getText('common.parameter')}</th>
                        <th>${i18n.getText('common.type')}</th>
                        <th>${i18n.getText('common.required')}</th>
                        <th>${i18n.getText('common.description')}</th>
                    </tr>
                    [#list method['parameters']['list'] as param]
                        <tr>
                            <td class="col-param">${param['name']!''}</td>
                            <td class="col-type">${param['type']?html}</td>
                            <td class="col-required">[#if param['required']!false]&#10003;[/#if]</td>
                            <td class="col-description">${param['description']!''}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

            [#if method['return']??]
                <h4>${i18n.getText('controller.output')}</h4>

                <p>${method['return']}</p>
            [/#if]

            [#if method['returnFields']?? && (method['returnFields']?size != 0)]
                <h4>${i18n.getText('controller.output')}</h4>
                <table class="return">
                    <tr>
                        <th>${i18n.getText('common.parameter')}</th>
                        <th>${i18n.getText('common.type')}</th>
                        <th>${i18n.getText('common.description')}</th>
                    </tr>
                    [@listparams method['returnFields']/]
                </table>
            [/#if]

            [#if method['exceptions']?size != 0]
                <h4>${i18n.getText('controller.exceptions')}</h4>
                <table class="exceptions">
                    <tr>
                        <th>${i18n.getText('common.type')}</th>
                        <th>${i18n.getText('common.description')}</th>
                    </tr>
                    [#list method['exceptions'] as ex]
                        <tr>
                            <td>${ex['type']}</td>
                            <td>${ex['description']!''}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]
        </li>
    [/#list]
</ul>
[/#assign]


[#macro indent char count]
    [#if count != 0][#list 0..count as x]${char}[/#list][/#if]
[/#macro]

[#macro listparams params indentSize=0]
    [#list params as param]
    <tr>
        <td class="col-name">[@indent '&nbsp;&nbsp;' indentSize/]${param['name']!''}</td>
        <td class="col-type">${param['type']?html}</td>
        <td class="col-description">${param['description']!''}</td>
    </tr>
        [#if param['child']??]
            [@listparams param['child'] indentSize+1/]
        [/#if]
    [/#list]
[/#macro]

[#include template /]