<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the home page with animation script -->

<#-- Template for the home page -->
<#include "base.ftl">
<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>
<#include "../includes/head.ftl">
</head>
<body>
<#include "../includes/header.ftl">

<div class="container marketing">

<#if alertText??>
  <div class="alert-message ${alertClass}">${alertText}</div>
</#if>
  <div id="content">

  <#-- Pull in the content from the model -->
  ${model.content}

  <#include "../includes/footer.ftl">

  </div>

</div>


<#include "../includes/cdn-scripts.ftl">

  <script type='text/javascript'>

    $(document).ready(function () {
      $('.carousel').carousel({
        interval: 10000
      })

    });

  </script>

</body>

</html>