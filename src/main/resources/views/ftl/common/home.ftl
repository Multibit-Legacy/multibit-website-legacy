<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#assign downloadVersion = "1.2.3">

<#-- Alerts are configured here

# Place an alert using one of these classes:
# error (big problem!)
# success (we've fixed it!)
# info (something important is happening)
alertClass: success
# Add the text of the alert in here
alertText: Here is some alert text

-->

<#assign alertClass = "error">
<#assign alertText = "This is a test alert">

<#-- The main page template starts here -->
<html lang="en">
<head>
  <#include "../includes/common/head.ftl">
</head>
<body>
<div id="container1">
  <div class="wrap">
    <div id="logo"><a href="/"><span>MultiBit</span></a></div>
    <div id="logotext">MultiBit</div>
  </div>
</div>
<div id="container2">
  <ul id="menu" class="wrap">
    <li>TODO extract HTML from jekyll</li>
  </ul>
</div>

<div id="container3">
  <div id="back1">
    <div id="back2">
      <div class="wrap">

        <#if alert??>
          <div class="alert-message ${alertClass}">${alertText}</div>
        </#if>
        <div id="content">

          <#-- Pull in the content from Markdown -->
          ${model.content}

          <#include "../includes/common/right-sidebar.ftl">

        </div>

        <#include "../includes/common/footer.ftl">

      </div>
    </div>
  </div>
</div>

<#include "../includes/common/cdn-scripts.ftl">

</body>

</html>