<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Footer text -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<footer class="container-fluid">
  <p class="text-muted">&copy;<a href="https://multibit.org/" target="_blank">${model.msg("app.title")}</a> 2011–2014.
    &nbsp;${model.msg("footer.powered")}
    &nbsp;${model.msg("footer.licence")}
    &nbsp;${model.msg("footer.trademarks")}
    &nbsp;<a href="privacy.html" target="_blank">${model.msg("footer.privacy")}</a>
    &nbsp;<a href="/tandc.html" target="_blank">${model.msg("footer.tandc")}</a>
  </p>
</footer>