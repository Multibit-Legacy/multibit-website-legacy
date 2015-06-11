<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Navbar -->
<div class="navbar navbar-inverse" role="navigation">

  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
      <span class="sr-only">${model.msg("navbar.toggle")}</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a title="MultiBit HD home" class="navbar-brand hidden-md hidden-lg" href="/index.html">${model.msg("app.title")}</a>
  </div>

  <div class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
    ${model.navBar}
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li>
        <div id="trezorAd" class="hidden-sm navbar-right">
            <a title="Trezor" href="https://buytrezor.com/?a=4fbc03c60545"><h1><img src="images/banner/trezor1.png"></a>
        </div>
      </li>
    </ul>
  </div>

</div>

<div class="page-header hidden-xs hidden-sm">
    <a title="MultiBit HD home" href="/index.html"><h1><img class="header-logo" src="/images/clients/MultiBitHD-logo-96x96-simple.png">${model.msg("app.title")}</h1><h2>${model.msg("app.subtitle")}</h2></a>
</div>

<#-- Alert bar -->
<#if alertText??>
<div class="container-fluid container-alert">
  <div class="alert alert-${alertClass}">
  ${alertText}
  </div>
</div>
</#if>