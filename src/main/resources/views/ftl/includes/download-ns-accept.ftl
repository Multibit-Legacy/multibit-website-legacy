<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Download buttons -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- TODO Remove this after verification -->
<#if model.acceptedTandC == true>
  Accepted T and C
<#else>
  Need to accept T and C
</#if>

<div class="row mb-downloads">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading">
      <h3 class="mb-download-heading">Get the latest version</h3>
    </div>
    <div class="panel-body">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-win32.exe" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Windows">
            <i class="mb-download-icon fa fa-windows fa-5x"></i>
            <br/>Windows</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-linux" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Linux">
            <i class="mb-download-icon fa fa-linux fa-5x"></i>
            <br/>Linux</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link text-center">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-macosx.dmg" class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for OS X">
            <i class="mb-download-icon fa fa-apple fa-5x"></i>
            <br/>OS X</a>
        </div>
      </div>

    </div>
  </div>
</div>

