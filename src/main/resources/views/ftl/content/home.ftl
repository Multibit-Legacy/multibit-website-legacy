<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the home page with animation script -->
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
  <div id="back1">
    <div id="back2">
      <div class="wrap">

        <#if alertText??>
          <div class="alert-message ${alertClass}">${alertText}</div>
        </#if>
        <div id="content">

          <#-- Pull in the content from the model -->
          ${model.content}

          <#include "../includes/right-sidebar.ftl">

        </div>

        <#include "../includes/footer.ftl">

      </div>
    </div>
  </div>
</div>

<#include "../includes/cdn-scripts.ftl">

<#-- Provide some animation for the screenshots -->
<script src="/jquery/plugins/cycle/jquery.cycle.lite.js"></script>

<script>

  $(function() {

    $('#slide').cycle({
      delay:   -2000
    });

  });

</script>

</body>

</html>
