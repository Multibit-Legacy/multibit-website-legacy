<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Download buttons -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

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
      <h3 class="mb-download-heading">Get the latest version</h3>
    </div>
    <div class="panel-body">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-windows fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>Windows
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-win32.exe" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Windows">
            <i class="mb-download-icon fa fa-windows fa-5x"></i>
            <br/>Windows</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-linux fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>Linux
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-linux" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Linux">
            <i class="mb-download-icon fa fa-linux fa-5x"></i>
            <br/>Linux</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-apple fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>OS X
          </div>
        </div>
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-macosx.dmg" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for OS X">
            <i class="mb-download-icon fa fa-apple fa-5x"></i>
            <br/>OS X</a>
        </div>
      </div>

      <div class="alert alert-info col-xs-12">
        <form class="form-horizontal">
          <div class="form-group">
            <div class="col-sm-8 mb-download-terms-and-conditions-text">Read and accept the <a href="/tandc.html" target="_blank">terms and conditions</a> to enable the download
              buttons.
            </div>
            <div class="col-sm-4 text-right">
              <span class="glyphicon glyphicon-arrow-right"></span>
              <button type="submit" class="btn btn-info" onclick="acceptTandC()" title="Accept terms and conditions">Accept</button>
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
  <#include "download-ns-accept.ftl">
<#else>
  <#include "download-ns.ftl">
</#if>
</noscript>

<script type="text/javascript">

  $(document).ready(function () {

    // Show the "jsok" class since JavaScript is available
    $('.jsok').show();

    // If cookie is already set
    if ($.cookie("MBHD-Session")) {

      // Enable the download buttons since user has accepted
      $(".mb-downloads .panel.mb-download-panel .panel-heading").slideUp(function () {
        $(".mb-download-link-disabled").fadeOut(function () {
          $(".mb-download-link").fadeIn();
          $(".mb-downloads .panel.mb-download-panel .panel-body").addClass("mb-downloads-enabled");
        });
      });

    }

  }

  })

  <#-- Use JavaScript fade effect to show acceptance -->
  function acceptTandC() {

    // Get the session cookie through a post
    $.post("/index.html", function (data, status) {

      // Enable the download buttons since user has accepted
      $(".mb-downloads .panel.mb-download-panel .panel-heading").slideUp(function () {
        $(".mb-download-link-disabled").fadeOut(function () {
          $(".mb-download-link").fadeIn();
          $(".mb-downloads .panel.mb-download-panel .panel-body").addClass("mb-downloads-enabled");
        });
      });

    });

  }

</script>

