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
      <h2>Oh, snap!</h2>

      <p>It looks as though something has broken on our system. Our internal error reporting system has picked this up
        and has sent a notification to the support engineers about it. They will sort it out as soon as possible.</p>

      <p>We are very sorry to cause you this inconvenience, your time is precious and we screwed up.</p>

      <p>Since there isn't much else to done, you should <a href="/">return to the home page</a>.</p>

      <h3>Technical</h3>

      <p>500 - Internal server error</p>

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