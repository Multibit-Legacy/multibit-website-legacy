# Welcome to the MultiBit Website Repository

This repo contains the source for the main MultiBit website.

# Acknowledgements

Much of the groundwork for this website was put in place by Saivann Carigan - thanks from the MultiBit team!

# OpenId Demo

This project demonstrates the following

* Dropwizard - Serves HTML
* OpenId - Provides integration with OpenId providers (Google, Facebook etc)
* Authorization - Security annotation supporting different levels of access
* Session cookies - Not very stateless, but this is only a demo - you'd use a database in real life

## Notation

```<project root>``` - The root directory of the project as checked out through git

All commands will work on *nix without modification, use \ instead of / for Windows.

## Getting started

From the console you can do the following
```
cd <project root>
mvn clean install
java -jar target/dropwizard-openid-1.0.0.jar server openid-demo.yml
```

## Where does the ASCII art come from?

The ASCII art for the startup banner was created using the online tool available at
[Webestools](http://www.webestools.com/ascii-text-generator-ascii-art-code-online-txt2ascii-text2ascii-maker-free-text-to-ascii-converter.html)
with a font of Tiza

# Making changes

Use the `site-config.yml` to change static elements like the alert and download version.

# Translation

Bitcoin is a global currency and so this site has many translations. If you'd like to contribute your own translation for the pages on offer please use this process:

1. Find the two letter ISO 639-1 code for your language (fr, en, jp)
1. Run `./_contrib/translate (language code) (language name)`
1. Make sure that languages are listed in alphabetical order in `_config.yml`
1. Rename html files in `(lang)/` according to your language. 
1. Update links in `_layouts/base-(lang).html` and `(lang)/*.html` to reflect your changes
1. Translate all `.html` and image files in `(lang)/` and `_layouts/base-(lang).html`

Tip: You can preview your work in a simple Google Chrome browser with no HTTP server. Just go to the existing English page, open the JavaScript console (`CTRL + SHIFT + J`) and use the following command to make the page editable : `document.body.contentEditable=true`

## Advanced Usage

The following are for administrators of the site and don't affect most people.

### Alerts

You can easily put a global alert on the website by changing the `ALERT` and `ALERT\_CLASS` variables in `_config.yml`.

You can also set an alert specific to a language by appending the language code to the `ALERT`.

Here is an example:

```
ALERT_CLASS: error
ALERT: <strong>Security alert:</strong> Please upgrade to 0.3.25 as soon as possible!
ALERT_fr: <strong>Alerte de sécurité:</strong> Mettez MultiBit à jour vers la version 0.5.0 sans délais!
```

The above will produce an English red alert box for all languages, and a translated red alert box for the French language. 

You may choose to change the alert colour, in which case possible classes are: error (red), info (blue), success (green) and warning (yellow)

### Release Notes

Release notes should be placed in `_posts/releases/YEAR-MONTH-DAY-SHORTTITLE.md` and adhere to this format:

```
---
layout: post
title: MultiBit version 0.4.18 released
src: https://groups.google.com/forum/?fromgroups=#!topic/bitcoin-multibit/CKYhvXQtCSs
category: releases
---

MultiBit v0.4.18 is now available for download at
<https://multibit.org/releases/multibit-0.4.18/>

... and some release notes here ...

In general pages follow this format

```
* `SHORTTITLE` is used to construct the URL. Something like `v0.4.23` will be fine
* `layout: post` important for Jekyll
* `title: ...` will be used as the title
* `src: ...` (optional) link to full announcement
* `category: ...` category of post
** `releases`
** `events`

### Common gotchas with Jekyll parsing

#### `parse': (<unknown>): did not find expected key while parsing a block mapping
You have formatted the front matter in the page and the `url` entry is probably out of alignment. Indent it and try again.

#### Liquid Exception: Tag '{%' was not properly terminated with regexp: /\%\}/
You have formatted a Liquid section over one or more lines. Put it all back on a single line and it'll be fine.

### Aliases for contributors

Aliases for contributors are defined in `_config.yml`.

```
aliases:
  garyrowe: Gary Rowe
  jimburton: Jim Burton
  saivanncarigan: Saivann Carigan
  timmolter: Tim Molter
```
