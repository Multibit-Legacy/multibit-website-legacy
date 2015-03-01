<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->

<#-- Disabled download buttons with JavaScript and fallback to NoScript with accept button -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- JavaScript/NoScript with no acceptance -->

<#-- Ensure the "jsok" style is invisible by default -->
<style>
  .jsok {
    display: none;
  }
</style>

<#-- The "jsok" class ensures that only JavaScript users will see this -->
<div class="row mb-downloads jsok">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading">
      <h3 class="mb-download-heading">${model.msg("download.latest-version")}</h3>
    </div>
    <div class="panel-body">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <i class="mb-download-icon fa fa-windows fa-5x"
               title="${model.msg("download.accept-enables")}"></i>
            <br/>Windows
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a id="download-windows" href="${downloadWindows}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.windows")}">
            <i class="mb-download-icon fa fa-windows fa-5x"></i>
            <br/>Windows</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <i class="mb-download-icon fa fa-linux fa-5x" title="${model.msg("download.accept-enables")}"></i>
            <br/>Linux
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a id="download-linux" href="${downloadLinux}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.linux")}">
            <i class="mb-download-icon fa fa-linux fa-5x"></i>
            <br/>Linux</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <i class="mb-download-icon fa fa-apple fa-5x" title="${model.msg("download.accept-enables")}"></i>
            <br/>OS X
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a id="download-osx" href="${downloadOSX}"
             class="btn btn-default col-xs-12"
             title="${model.msg("download.osx")}">
            <i class="mb-download-icon fa fa-apple fa-5x"></i>
            <br/>OS X</a>
        </div>
      </div>
      <div class="alert alert-info col-xs-12 mb-download-link-disabled">
        <form id="acceptTandC" class="form-horizontal">
          <div class="form-group">
            <div class="col-sm-8 mb-download-terms-and-conditions-text">${model.msg("download.tandc")}</div>
            <div class="col-sm-4 text-right">
              <span class="glyphicon glyphicon-arrow-right"></span>
              <button type="submit" class="btn btn-info" onclick="acceptTandC()" title="${model.msg("download.tandc-title")}">
              ${model.msg("download.accept")}
              </button>
            </div>
          </div>
        </form>
      </div>

    </div>
  </div>
</div>

<#-- Switch to NoScript page views -->
<noscript>
<#if model.acceptedTandC == true>
    <#-- NoScript accepted terms (no buttons) -->
  <#include "download-ns-accept.ftl">
<#else>
<#-- NoScript with modified buttons -->
  <#include "download-ns.ftl">
</#if>
</noscript>

<script type="text/javascript">

  $(document).ready(function () {

    // Show the "jsok" class since JavaScript is available
    $('.jsok').show();

    $('#acceptTandC').bind('submit', function () {
      // Avoid a page refresh on submit
      return false;
    });

  });

  <#-- Use JavaScript fade effect to show acceptance -->
  function acceptTandC() {

    <#-- Get the session cookie through a post -->
    $.post("${model.acceptAction}", function (data, status) {


      <#-- Enable the download buttons since user has accepted -->
      $(".mb-downloads .panel.mb-download-panel .panel-heading").slideUp(function () {
        $(".mb-download-link-disabled").fadeOut(function () {
          $(".mb-download-link").fadeIn();
          $(".mb-downloads .panel.mb-download-panel .panel-body").addClass("mb-downloads-enabled");
        });
      });

      <#-- Use jQuery to transition to "how to install" after download has started -->
      $("#download-windows").click(
        function (e) {
          setTimeout(function () {
            document.location = "/en/help/hd0.1/how-to-install-windows.html";
          }, 5000);
        }
      );

      $("#download-linux").click(
        function (e) {
          setTimeout(function () {
            document.location = "/en/help/hd0.1/how-to-install-linux.html";
          }, 5000);
        }
      );

      $("#download-osx").click(
        function (e) {
          setTimeout(function () {
            document.location = "/en/help/hd0.1/how-to-install-osx.html";
          }, 5000);
        }
      );

    });

  }

</script>
