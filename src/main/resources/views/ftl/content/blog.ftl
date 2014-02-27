<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the blog pages with no download promotion -->
<#include "base.ftl">
<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
  <#include "../includes/head.ftl">
</head>
<body>
<#include "../includes/header.ftl">

<div id="container3">
      <div class="wrap">

        <#if alertText??>
          <div class="alert-message ${alertClass}">${alertText}</div>
        </#if>
          <div id="content-full">

          <#-- Pull in the content from the model -->
          ${model.content?replace("downloadVersion","${downloadVersion}")}

            <p><a href="/atom.xml"><img src="/images/common/rssicon.png" alt="Subscribe via Atom">&nbsp;Subscribe via Atom</a> | <a href="/blog.html">Back to blog contents</a> </p>

            <hr/>
            <p>Great read ! How can I reward you ?<br/>MultiBit is "donationware" and all <a href="bitcoin:1AhN6rPdrMuKBGFDKR1k9A8SCLYaNgXhty?amount=0.01&label=Please%20donate%20to%20multibit.org">donations</a> go towards development and server costs.</p>
            <p>Was this article accurate ?<br/>If not please <a href="https://github.com/jim618/multibit-website/issues/new">raise a website improvement Issue</a> so that we can do better.</p>

          <#-- No sidebar for help -->
          </div>

        <#include "../includes/footer.ftl">

  </div>
</div>

<#include "../includes/cdn-scripts.ftl">

</body>

</html>
