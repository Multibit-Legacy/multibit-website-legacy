<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->

<#-- Enabled download buttons under NoScript with accepted terms and conditions -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- NoScript prevents CSS fonts so use basic images -->
<div class="row mb-downloads">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading" style="display:none;">
      <h3 class="mb-download-heading">${model.msg("download.latest-version")}</h3>
    </div>
    <div class="panel-body mb-downloads-enabled">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="${downloadWindows}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.windows")}">
            <img src="/images/common/dow-win.png"/><br/>Windows</a>
          <br/><a href="/en/help/hd0.3/how-to-install-windows.html">How to install</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="${downloadLinux}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.linux")}">
            <img src="/images/common/dow-linux.png"/><br/>Linux</a>
          <br/><a href="/en/help/hd0.3/how-to-install-linux.html">How to install</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="${downloadOSX}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.osx")}">
            <img src="/images/common/dow-osx-uni.png"/><br/>OS X</a>
          <br/><a href="/en/help/hd0.3/how-to-install-osx.html">How to install</a>
        </div>
      </div>

    </div>
  </div>
</div>
