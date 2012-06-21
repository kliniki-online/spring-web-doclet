[#ftl]

[#assign title]${controller['title']}[/#assign]

[#assign content]
<p>
    <a href="${indexPath}">← Контроллеры</a>
</p>
<h2>${controller['title']} (${controller['name']})</h2>
<ul>
    [#list controller['methods'] as method]
        <li>
            <h3>${method['method']}: ${method['url']}</h3>

            <p>${method['description']}</p>

            [#if method['roles']?? && (method['roles']?size != 0)]
                <h4>Права доступа</h4>
                <ul class="roles">
                    [#list method['roles'] as role]
                        <li>${role}</li>
                    [/#list]
                </ul>
            [/#if]

            [#if method['parameters']?size != 0]
                <h4>Вход</h4>
                <table class="parameters">
                    <tr>
                        <th>Параметр</th>
                        <th>Тип</th>
                        <th>Обязат.</th>
                        <th>Описание</th>
                    </tr>
                    [#list method['parameters'] as param]
                        <tr>
                            <td class="col-param">${param['name']!''}</td>
                            <td class="col-type">${param['type']}</td>
                            <td class="col-required">[#if param['required']!false]✓[/#if]</td>
                            <td class="col-description">${param['description']!''}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

            [#if method['return']??]
                <h4>Выход</h4>

                <p>${method['return']}</p>
            [/#if]

            [#if method['returnFields']?? && (method['returnFields']?size != 0)]
                <h4>Выход</h4>
                <table class="return">
                    <tr>
                        <th>Параметр</th>
                        <th>Тип</th>
                        <th>Описание</th>
                    </tr>
                    [@listparams method['returnFields']/]
                </table>
            [/#if]

            [#if method['exceptions']?size != 0]
                <h4>Ошибки</h4>
                <table class="exceptions">
                    <tr>
                        <th>Тип</th>
                        <th>Описание</th>
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
        <td class="col-type">${param['type']}</td>
        <td class="col-description">${param['description']!''}</td>
    </tr>
        [#if param['list']??]
            [@listparams param['list'] indentSize+1/]
        [/#if]
    [/#list]
[/#macro]

[#include template /]