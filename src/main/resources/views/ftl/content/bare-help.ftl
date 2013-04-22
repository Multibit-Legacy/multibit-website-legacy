<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the MultiBit client help pages (no extra formatting) -->
<#include "base.ftl">
<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
</head>
<body>

<#if alertText??>
  <div>${alertText}</div>
</#if>

<#-- Pull in the content from the model -->
${model.content}

<p><a href="help_contents.html">Back to contents</a></p>

<hr/>
<p>Insufficient? Please <a href="https://github.com/jim618/multibit-website/issues/new">raise an Issue</a> so that we can do better.</p>

</body>

</html>