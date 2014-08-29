<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->

<#-- Disabled download buttons under NoScript with accept button -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- NoScript prevents CSS fonts so perform replacements -->
<div class="row mb-downloads">

  <div class="panel panel-default mb-download-panel">

    <div class="panel-heading">
      <h3 class="mb-download-heading">${model.msg("download.latest-version")}</h3>
    </div>
    <div class="panel-body">

      <div class="md-download-btn-left col-xs-4">
        <div class="mb-download-link-disabled-ns text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <img src="/images/common/dow-win.png" />&nbsp;Windows
          </div>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-disabled-ns text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <img src="/images/common/dow-linux.png" />&nbsp;Linux
          </div>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-disabled-ns text-center">
          <div class="well" title="${model.msg("download.accept-enables")}">
            <img src="/images/common/dow-osx-uni.png" />&nbsp;OS X
          </div>
        </div>
      </div>
      <div id="acceptTandC" class="alert alert-info col-xs-12">
        <form class="form-horizontal" method="post" action="${model.acceptAction}">
          <div class="form-group">
            <div class="col-sm-8 mb-download-terms-and-conditions-text">${model.msg("download.tandc")}</div>
            <div class="col-sm-4 text-right">
              <button type="submit" class="btn btn-info" title="${model.msg("download.tandc-title")}">${model.msg("download.accept")}</button>
            </div>
          </div>
        </form>
      </div>

    </div>
  </div>
</div>
