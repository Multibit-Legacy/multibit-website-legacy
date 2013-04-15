<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#include "../content/base.ftl">
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

          <h1>Access denied!</h1>

          <p>You need to <a href="/openid">sign in</a> to access this information</p>

        </div>

      <#include "../includes/footer.ftl">

      </div>
    </div>
  </div>
</div>

<#include "../includes/cdn-scripts.ftl">

</body>

</html>
