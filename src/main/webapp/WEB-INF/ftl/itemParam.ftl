

<link rel="stylesheet" type="text/css" href="/css/pshow.css" media="all"/>

<div class="Ptable1">

<#list itemParamMap?keys as key>
    <div class="Ptable-item">
        <h3>${key} </h3>
        <dl>
            <#assign  itemParamList=itemParamMap[key]/>
            <#list itemParamList?keys as key>
            <dt>${key}</dt><dd>${itemParamList[key]}</dd>
            </#list>
        </dl>
    </div>
</#list>

</div>