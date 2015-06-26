<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Template for the MultiBit HD help pages (minimal extra formatting) -->

<#-- All templates include the base.ftl for variables -->
<#include "../includes/base.ftl">

<#-- Required for IE to render correctly -->
<!DOCTYPE HTML>
<html lang="en">
<head>

  <style>
    .alert-message {
      background-color: #FFF3DE;
      border: 1px solid #A78241;
      margin: 10px 0px;
      padding: 10px;
      color: #000;
    }

  </style>
</head>
<body>
<div id="centercontent" style="padding:10px;">
  <p>
  <center><a href="contents.html">Back to help contents</a></center>
  </p>

<#-- Pull in the HTML content from the model and replace non-FTL tokens -->
${model.content
?replace("downloadVersionHD","${downloadVersionHD}")
?replace("downloadVersionClassic","${downloadVersionClassic}")
?replace("buyTrezorLink","${buyTrezorLink}")
}

  <p>
  <center><a href="contents.html">Back to help contents</a></center>
  </p>
  <br>
</div>
</body>

</html>
