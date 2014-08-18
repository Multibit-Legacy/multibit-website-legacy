<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the main navigation and information pages with no animation -->

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

<#-- Pull in the content from the model -->
  ${model.content?replace("downloadVersion","${downloadVersion}")}

</div>

<#include "../includes/footer.ftl">

</body>

</html>