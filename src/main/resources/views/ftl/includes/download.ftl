<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Download buttons -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">

<#-- Start with the assumption that JavaScript is available then fallback to NoScript -->
<#if model.acceptedTandC == true>
  <#include "download-js-accept.ftl">
<#else>
  <#include "download-js.ftl">
</#if>

