<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the MultiBit HD help pages (minimal extra formatting) -->

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

<div class="container">

<#-- Pull in the content from the model -->
${model.content?replace("downloadVersion","${downloadVersion}")}

  <hr/>
  <p>Was this article detailed enough?<br/>If not please <a href="https://github.com/bitcoin-solutions/multibit-website/issues/new">raise a <strong>website
    documentation improvement Issue</strong></a> so that we can do better.</p>

</div>

<#include "../includes/footer.ftl">

</body>

</html>
