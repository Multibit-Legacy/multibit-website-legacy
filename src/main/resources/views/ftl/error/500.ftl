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
      <h2>${model.msg("500.title")}</h2>

      <p>${model.msg("500.explain")}</p>

      <p>${model.msg("500.apology")}</p>

      <p>${model.msg("500.return-to-home")}</p>

      <h3>${model.msg("500.technical-title")}</h3>

      <p>${model.msg("500.technical-explain")}</p>

    </div>
  </div>

  <div class="row">

    <div class="span12">
      <a href="/"><img src="/images/common/error.jpg"></a>
    </div>

  </div>

<#include "../includes/footer.ftl">

</div>

</body>