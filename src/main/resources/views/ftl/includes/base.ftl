<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template providing a single point of configuration for common variables -->
<#assign downloadVersionHD = "0.1.1">
<#assign downloadVersionClassic = "0.5.18">

<#-- Create download links for latest version -->
<#assign downloadWindows = "https://multibit.org/releases/multibit-hd/multibit-hd-${downloadVersionHD}/multibit-hd-windows-x64-${downloadVersionHD}.exe">
<#assign downloadWindows32 = "https://multibit.org/releases/multibit-hd/multibit-hd-${downloadVersionHD}/multibit-hd-windows-${downloadVersionHD}.exe">
<#assign downloadLinux = "https://multibit.org/releases/multibit-hd/multibit-hd-${downloadVersionHD}/multibit-hd-unix-${downloadVersionHD}.sh">
<#assign downloadOSX = "https://multibit.org/releases/multibit-hd/multibit-hd-${downloadVersionHD}/multibit-hd-macos-${downloadVersionHD}.dmg">

<#assign signatureWindows = "${downloadWindows}.asc">
<#assign signatureWindows32 = "${downloadWindows32}.asc">
<#assign signatureLinux = "${downloadLinux}.asc">
<#assign signatureOSX = "${downloadOSX}.asc">

<#-- Create affiliate links for recommended products and services -->
<#assign buyTrezorLink = "https://buytrezor.com/?a=multibit.org">

<#-- Documentation for alerts

# Place an alert using one of these classes:
# danger (big problem!)
# success (we've fixed it!)
# info (something important is happening)
# Example of a beta test alert:

<#assign alertClass = "success">
<#assign alertText = "Beta testers: <a href='https://multibit.org/releases/multibit-classic/multibit-0.5.9rc1/'>MultiBit v0.5.9 Release Candidate 1</a> is now available for trials.">

-->

<#--&lt;#&ndash; Uncomment when ready to present -->
<#--<#assign alertClass = "info">-->
<#--<#assign alertText = "Prepare for MultiBit HD...">-->

<#--&ndash;&gt;-->
