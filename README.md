# Welcome to the MultiBit Website Repository

This repo contains the source for the main MultiBit website.

# Acknowledgements

Much of the groundwork for this website was put in place by Saivann Carigan - thanks from the MultiBit team!

# MultiBit Website

This project demonstrates the following

* Dropwizard - Serves HTML and images

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

# Translation

Bitcoin is a global currency and so this site has many translations. If you'd like to contribute your own translation for the pages on offer please use this process:

1. Find the two letter ISO 639-1 code for your language (fr, en, jp)
1. Rename html files in `src/main/resources/views/html/(lang)/` according to your language
1. Update image files in `src/main/resources/assets/images/(lang)` to reflect your changes
1. Translate all `.html` and image files as required then issue a pull request

Tip: You can preview your work in a simple Google Chrome browser with no HTTP server. Just go to the existing English page, open the JavaScript console (`CTRL + SHIFT + J`) and use the following command to make the page editable : `document.body.contentEditable=true`

## Advanced Usage

The following are for administrators of the site and don't affect most people.

### Alerts

Use `src/main/resources/views/ftl/base.ftl` to change static elements like the alert and download version.

## Where does the ASCII art come from?

The ASCII art for the startup banner was created using the online tool available at
[TAAG](http://patorjk.com/software/taag/#p=display&f=Standard&t=MultiBit%20Site)

