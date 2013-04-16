<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the blog pages with no download promotion -->
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

        <#if alertText??>
          <div class="alert-message ${alertClass}">${alertText}</div>
        </#if>
        <div id="content-full">

          <#-- Pull in the content from the model -->
          ${model.content}

        </div>

        <#include "../includes/footer.ftl">

      </div>
    </div>
  </div>
</div>

<#include "../includes/cdn-scripts.ftl">

</body>

</html>
