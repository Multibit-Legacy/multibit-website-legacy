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

<#-- Support SEO -->
<script type="application/ld+json">
{
  "@context": "http://schema.org/",
  "@type": "SoftwareApplication",
  "name": "MultiBit HD",
  "operatingSystem": "Windows,OSX,Linux",
  "applicationCategory": "http://schema.org/BitcoinApplication",
  "screenshot": "https://multibit.org/images/en/screenshots/mbhd-0.1/payments.png",
  "downloadUrl": "https://multibit.org/download.html",
  "featureList": "https://multibit.org/blog/2015/06/09/multibit-hd-release-0.1.html",
  "fileSize": "30MB",
  "memoryRequirements": "1GB",
  "releaseNotes": "https://multibit.org/download.html",
  "softwareHelp": "https://multibit.org/help.html",
  "softwareVersion": "${downloadVersionHD}",
  "storageRequirements": "50MB"
}
</script>