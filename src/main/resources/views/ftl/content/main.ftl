<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the main pages direct from the nav bar -->

<#-- All templates include the base.ftl for variables -->
<#include "../includes/base.ftl">

<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
<#include "../includes/head.ftl">
</head>
<body>
<#include "../includes/header.ftl">

<#-- Wrap all page content here -->
<div class="container container-main-content">

<#if model.showDownload == true>
  <#include "../includes/download.ftl">
</#if>

  <#-- Pull in the HTML content from the model and replace non-FTL downloadVersion -->
  ${model.content?replace("downloadVersion","${downloadVersion}")}

</div>

<#include "../includes/footer.ftl">

</body>

</html>