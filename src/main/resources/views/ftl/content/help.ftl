<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#include "base.ftl">
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

      <#if alert??>
        <div class="alert-message ${alertClass}">${alertText}</div>
      </#if>
        <div id="content">

        <#-- Pull in the content from the model -->
        ${model.content}

        <p>Insufficient? Please <a href="https://github.com/jim618/multibit-website/issues">raise an Issue</a> so we can do better. It takes but a moment.</p>

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