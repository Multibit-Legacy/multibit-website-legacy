<#-- @ftlvariable name="model" type="org.multibit.site.views.PublicFreemarkerView" -->
<#assign downloadVersion = "1.2.3">

<#-- Alerts are configured here

# Place an alert using one of these classes:
# error (big problem!)
# success (we've fixed it!)
# info (something important is happening)
alertClass: success
# Add the text of the alert in here
alertText: Here is some alert text

-->

<#assign alertClass = "error">
<#assign alertText = "This is a test alert">