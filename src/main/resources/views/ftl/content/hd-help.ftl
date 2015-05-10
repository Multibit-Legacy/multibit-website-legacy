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

  <p><a href="contents.html">Back to help contents</a></p>
  <hr/>

<#-- Pull in the HTML content from the model and replace non-FTL tokens -->
${model.content?replace("downloadVersion","${downloadVersion}")?replace("buyTrezorLink","${buyTrezorLink}")}

  <hr/>
  <p><a href="contents.html">Back to help contents</a></p>

  <p>Was this article detailed enough?<br/>If not please <a href="https://github.com/bitcoin-solutions/multibit-website/issues/new">raise a website content improvement Issue</a>
    so that we can do better.</p>

</div>

<#include "../includes/footer.ftl">

</body>

</html>
