<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Navbar -->
<div class="navbar navbar-inverse" role="navigation">

  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a title="MultiBit HD home" class="navbar-brand hidden-md hidden-lg" href="/index.html">MultiBit HD</a>
  </div>

  <div class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
    ${model.navBar}
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li>
        <div id="karmaAds" class="hidden-sm navbar-right">
          <iframe scrolling="no" frameBorder="0" src="/ad"></iframe>
        </div>
      </li>
    </ul>
  </div>

</div>

<div class="page-header hidden-xs hidden-sm">
  <a title="MultiBit HD home" href="/index.html"><h1><img class="header-logo" src="/images/clients/MultiBitHD-logo-96x96-simple.png">MultiBit HD</h1></a>
</div>

<#-- Alert bar -->
<#if alertText??>
<div class="container-fluid container-alert">
  <div class="alert alert-${alertClass}">
  ${alertText}
  </div>
</div>
</#if>