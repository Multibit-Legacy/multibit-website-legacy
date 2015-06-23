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

    <ul class="nav navbar-nav mb-banner-text hidden-md hidden-lg navbar-right">
      <li>
          <a title="TREZOR for $99 promo" href="https://buytrezor.com/?a=multibit.org" target="_blank"><strong>TREZOR&nbsp;$99&nbsp;Code: multibit.org</strong></a>
      </li>
    </ul>

  </div>

</div>

<div class="page-header hidden-xs hidden-sm">
  <a title="MultiBit HD home" href="/index.html"><h1><img class="header-logo" src="/images/clients/MultiBitHD-logo-96x96-simple.png">${model.msg("app.title")}</h1>

    <h2>${model.msg("app.subtitle")}</h2></a>
  <span class="mb-banner-large pull-right">
    <a title="TREZOR for $99 promo" href="https://buytrezor.com/?a=multibit.org" target="_blank"><img src="/images/banner/trezor-99-banner-512x72.png"></a>
  </span>
  <span class="mb-banner-medium pull-right">
    <a title="TREZOR for $99 promo" href="https://buytrezor.com/?a=multibit.org" target="_blank"><img src="/images/banner/trezor-99-banner-280x72.png"></a>
  </span>
</div>

<#-- Alert bar -->
<#if alertText??>
<div class="container-fluid container-alert">
  <div class="alert alert-${alertClass}">
  ${alertText}
  </div>
</div>
</#if>