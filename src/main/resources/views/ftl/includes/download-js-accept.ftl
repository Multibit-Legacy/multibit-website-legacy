<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->

<#-- Enabled download buttons under JavaScript with accepted terms and conditions -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- Ensure the "jsok" style is invisible by default -->
<style>
  .jsok {
    display: none;
  }
</style>

<div class="row mb-downloads jsok">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading" style="display:none;">
      <h3 class="mb-download-heading">${model.msg("download.latest-version")}</h3>
    </div>
    <div class="panel-body mb-downloads-enabled">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-disabled text-center" style="display:none;">
          <div class="well" title="${model.msg("download.windows")}">
            <i class="mb-download-icon fa fa-windows fa-5x" title="${model.msg("download.windows")}"></i>
            <br/>Windows
          </div>
        </div>
        <div class="mb-download-link text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-win32.exe" class="btn btn-default col-xs-12"
             title="${model.msg("download.windows")}">
            <i class="mb-download-icon fa fa-windows fa-5x"></i>
            <br/>Windows</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-disabled text-center" style="display:none;">
          <div class="well" title="${model.msg("download.linux")}">
            <i class="mb-download-icon fa fa-linux fa-5x" title="${model.msg("download.linux")}"></i>
            <br/>Linux
          </div>
        </div>
        <div class="mb-download-link text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-linux" class="btn btn-default col-xs-12"
             title="${model.msg("download.linux")}">
            <i class="mb-download-icon fa fa-linux fa-5x"></i>
            <br/>Linux</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-disabled text-center" style="display:none;">
          <div class="well" title="${model.msg("download.osx")}">
            <i class="mb-download-icon fa fa-apple fa-5x" title="${model.msg("download.osx")}"></i>
            <br/>OS X
          </div>
        </div>
        <div class="mb-download-link text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-macosx.dmg" class="btn btn-default col-xs-12"
             title="${model.msg("download.osx")}">
            <i class="mb-download-icon fa fa-apple fa-5x"></i>
            <br/>OS X</a>
        </div>
      </div>

    </div>
  </div>
</div>

<#-- Switch to NoScript page views -->
<noscript>
  <#-- NoScript accepted terms -->
  <#include "download-ns-accept.ftl">
</noscript>

<script type="text/javascript">

  $(document).ready(function () {

    // Show the "jsok" class since JavaScript is available
    $('.jsok').show();

  });

</script>