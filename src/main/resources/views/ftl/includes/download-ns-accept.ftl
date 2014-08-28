<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->

<#-- Enabled download buttons under NoScript with accepted terms and conditions -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- NoScript prevents CSS fonts so use basic images -->
<div class="row mb-downloads">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading" style="display:none;">
      <h3 class="mb-download-heading">Get the latest version</h3>
    </div>
    <div class="panel-body mb-downloads-enabled">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-win32.exe"
             class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Windows">
            <img src="/images/common/dow-win.png" /><br/>Windows</a>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-linux"
             class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for Linux">
            <img src="/images/common/dow-linux.png" /><br/>Linux</a>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-ns text-center" style="display:block;">
          <a href="https://multibit.org/releases/multibit-hd-${downloadVersion}/mbhd-${downloadVersion}-macosx.dmg"
             class="btn btn-default col-xs-12"
             title="Click to download MultiBit HD for OS X">
            <img src="/images/common/dow-osx-uni.png"/><br/>OS X</a>
        </div>
      </div>

    </div>
  </div>
</div>
