<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the main navigation and information pages with no animation -->
<#include "base.ftl">
<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
<#include "../includes/head.ftl">
</head>
<body>
<#include "../includes/header.ftl">

<div class="container">

<#-- Pull in the content from the model -->
${model.content}

  <hr class="featurette-divider">

<#include "../includes/footer.ftl">

</div>

<#include "../includes/cdn-scripts.ftl">

</body>

</html>