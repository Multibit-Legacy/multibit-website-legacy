<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Navbar -->
<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/index.html">MultiBit HD</a>
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
</nav>
<#-- Alert bar -->
<#if alertText??>
<div class="container">
  <div class="alert alert-${alertClass}">
  ${alertText}
  </div>
</div>
</#if>