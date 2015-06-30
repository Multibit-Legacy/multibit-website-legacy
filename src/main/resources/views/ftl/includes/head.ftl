<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<#-- Page <head> section -->

<#-- All templates include the base.ftl for variables -->
<#include "base.ftl">
<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<title>${model.msg("head.title")}</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="MultiBit HD Bitcoin wallet - Official Site" />
<meta name="keywords" content="multibit,multibit classic,multibit hd,multibithd,bitcoin,bitcoin wallet,bitcoin desktop wallet,bitcoin laptop wallet,bitcoin software,digital currency,trezor,xchange,bip32,bip39,bip44" />
<meta name="alexaVerifyID" content="7tCwJOsMCYO8h_sG800FoCMpMJc" />
<meta name="author" content="Gary Rowe" />

<link rel="shortcut icon" href="/images/favicon.png">

<#-- Latest compiled and minified jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" type="application/javascript"></script>

<#-- Use CDN Bootstrap CSS as the baseline -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css">

<!-- Optional theme -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" type="text/css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<#-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js" type="application/javascript"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js" type="application/javascript"></script>
<![endif]-->

<#-- Fonts -->
<#-- Corben works well in titles but has strange ligatures on the 'ck' combination -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Corben" type="text/css">
<#-- Roboto is good for sub-headers -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto" type="text/css">
<#-- Font Awesome provides the iconography -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" type="text/css">

<#-- Override selective entries with main.css  -->
<link rel="stylesheet" href="/css/main.css" type="text/css" />