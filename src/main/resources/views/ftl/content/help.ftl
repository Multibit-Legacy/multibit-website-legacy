<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the help pages (minimal extra formatting) -->
<#include "base.ftl">
<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
<#include "../includes/head.ftl">
</head>
<body>
<#include "../includes/header.ftl">

<div id="container3">
  <div id="back1">
    <div id="back2">
      <div class="wrap">

      <#if alertText??>
        <div class="alert-message ${alertClass}">${alertText}</div>
      </#if>
        <div id="content-full">

        <#-- Pull in the content from the model -->
        ${model.content?replace("downloadVersion","${downloadVersion}")}

        <p><a href="help_contents.html">Back to contents</a></p>

        <hr/>
        <p>Insufficient? Please <a href="https://github.com/jim618/multibit-website/issues/new">raise an Issue</a> so that we can do better.</p>

        <#-- No sidebar for help -->
        </div>

      <#include "../includes/footer.ftl">

      </div>
    </div>
  </div>
</div>

<#include "../includes/cdn-scripts.ftl">

</body>

</html>
