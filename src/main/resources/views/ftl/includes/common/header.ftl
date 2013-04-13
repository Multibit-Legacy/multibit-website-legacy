<#-- @ftlvariable name="" type="org.multibit.site.views.PublicFreemarkerView" -->
<div id="container1">
  <div class="wrap">
    <div id="logo"><a href="/{{ page.langcode }}/"><span>MultiBit</span></a></div>
    <div id="logotext">MultiBit</div>
  </div>
</div>
<div id="container2">
  <ul id="menu" class="wrap">
    {% for link in page.menu %}{% assign active = nil %}{% capture fullurl %}{{ link.url | append:'.html' }}{% endcapture %}{% if page.url == fullurl %}{% assign active = 'active' %}{% endif %}
    <li{% if active %} class="{{ active }}"{% endif %}><a href="{{ link.url }}">{{ link.text }}</a></li>
    {% endfor %}
  </ul>
</div>