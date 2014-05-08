<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<div class="navbar-wrapper">
  <div class="container">
	<div class="page-header">
	  <h1>MultiBit</h1>
	</div>
    <div class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/index.html">MultiBit</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          ${model.navBar}
          </ul>

          <div id="karmaAds" class="navbar-form navbar-right"><iframe scrolling="no" frameBorder="0" src="/ad"></iframe></div>

        </div>
      </div>
    </div>

  </div>
</div>
