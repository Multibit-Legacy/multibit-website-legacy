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

<div id="mainCarousel" class="carousel slide">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#mainCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#mainCarousel" data-slide-to="1"></li>
    <li data-target="#mainCarousel" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="item active">

      <img src="/images/common/escheresque.png" />

      <div class="container">

        <div class="carousel-caption">
          <div><i class="icon-bitcoin icon-hero"></i></div>

          <h1>Custom Bitcoin software</h1>

          <p>Integrate Bitcoin into your business today</p>

          <p><a class="btn btn-large btn-primary" href="/bespoke.html">Find out more</a></p>
        </div>
      </div>
    </div>
    <div class="item">

      <img src="/images/common/escheresque.png" />

      <div class="container">
        <div class="carousel-caption">

          <div><i class="icon-coffee icon-hero"></i></div>

          <h1>Face to face consultancy</h1>

          <p>Get professional advice so you can make Bitcoin part of your business</p>

          <p><a class="btn btn-large btn-primary" href="/consultancy.html">Find out more</a></p>
        </div>
      </div>
    </div>
    <div class="item">

      <img src="/images/common/escheresque.png" />

      <div class="container">

        <div class="carousel-caption">

          <div><i class="icon-heart icon-hero"></i></div>

          <h1>Check us out</h1>

          <p>We contribute to many open source Bitcoin projects</p>

          <p><a class="btn btn-large btn-primary" href="/foss.html">Find out more</a></p>
        </div>
      </div>
    </div>
  </div>
  <a class="left carousel-control" href="#mainCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
  <a class="right carousel-control" href="#mainCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
</div>
<#-- /.carousel -->

<div class="container marketing">

<#if alertText??>
  <div class="alert-message ${alertClass}">${alertText}</div>
</#if>
  <div id="content">

<#-- Pull in the content from the model -->
${model.content}

  <hr class="featurette-divider">

<#include "../includes/footer.ftl">

</div>

<#include "../includes/cdn-scripts.ftl">

<#-- Provide some animation for the screenshots -->
  <script src="/jquery/plugins/cycle/jquery.cycle.lite.js"></script>

  <script>

    $(function() {

      $('#slide').cycle({
        delay:   -3000
      });

    });

  </script>

</body>

</html>