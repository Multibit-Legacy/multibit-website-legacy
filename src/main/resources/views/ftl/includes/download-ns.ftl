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
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-windows fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>Windows
          </div>
        </div>
      </div>

      <div class="col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-linux fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>Linux
          </div>
        </div>
      </div>

      <div class="md-download-btn-right col-xs-4">
        <div class="mb-download-link-disabled text-center">
          <div class="well" title="Clicking accept will enable the download buttons">
            <i class="mb-download-icon fa fa-apple fa-5x" title="Clicking accept will enable the download buttons"></i>
            <br/>OS X
          </div>
        </div>
      </div>

      <div class="alert alert-info col-xs-12">
        <form class="form-horizontal" action="${model.acceptAction}" method="post">
          <div class="form-group">
            <div class="col-sm-8 mb-download-terms-and-conditions-text">Read and accept the <a href="/tandc.html" target="_blank">terms and conditions</a> to enable the download
              buttons.</div>
            <div class="col-sm-4 text-right">
              <span class="glyphicon glyphicon-arrow-right"></span>
              <button type="submit" class="btn btn-info" onclick="showDownload()" title="Accept terms and conditions">Accept</button>
            </div>
          </div>
        </form>
      </div>

    </div>
  </div>
</div>