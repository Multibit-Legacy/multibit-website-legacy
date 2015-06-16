<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
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

  <div class="row">

    <div class="col-lg-12">
      <h2>${model.msg("404.title")}</h2>


      <p>${model.msg("404.explain")}</p>

      <p>${model.msg("404.return-to-home")}</p>

      <h3>${model.msg("404.technical-title")}</h3>

      <p>${model.msg("404.technical-explain")}</p>

    </div>

  </div>

<#include "../includes/footer.ftl">

</div>

</body>