<#-- @ftlvariable name="view" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the home page with animation script -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

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

<#if view.model.acceptedTandC == true>
  Accepted T and C
<#else>
  Not accepted T and C
</#if>

<#-- Pull in the content from the model -->
  ${view.model.content?replace("downloadVersion","${downloadVersion}")}

</div>

<#include "../includes/footer.ftl">

</body>

</html>