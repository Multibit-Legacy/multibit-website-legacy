# Welcome to the MultiBit Website Repository

This repo contains the source for the main MultiBit website.

# Acknowledgements

Much of the groundwork for this website was put in place by Saivann Carigan - thanks from the MultiBit team!

# MultiBit Website

From a technical point of view this project uses

* Java - Primary language of the app
* [Maven](http://maven.apache.org/) - Build system
* [Dropwizard](http://dropwizard.codahale.com) - Self-contained web server
* HTML5 and CSS - All pages are simple HTML5

Why Dropwizard? We don't want the complexity that comes with an application server or an
external servlet container. Dropwizard gives us the simplicity we crave.

We looked at [Jekyll](https://github.com/mojombo/jekyll), and while it's a great technology, we
found it a bit limiting for what we needed from our website.

## Getting started

Since it is all just Java and Maven, it's pretty straightforward to get the site running. Just clone
from GitHub and note the following:

`<project root>` - The root directory of the project as checked out through git
`<version>` - The version as found in `pom.xml` (e.g. 3.0.0-SNAPSHOT)

All commands will work on *nix without modification, use \ instead of / for Windows.

From the console you can do the following
```
cd <project root>
mvn clean install
java -jar target/multibit-site-<version>.jar server site-config.yml
```
# Translation

Bitcoin is a global currency and so this site has many translations. If you'd like to contribute your own translation for the pages on offer please use this process:

1. Find the two letter [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) code for your language (fr, en, jp etc)
1. Copy existing html files from `src/main/resources/views/html/{lang}/` according to your source language (e.g. you're a French to Japanese expert so base on `fr` and copy to `jp`)
1. Paste new html files to `src/main/resources/views/html/{lang}/` according to your target language
1. Repeat for image files in `src/main/resources/assets/images/{lang}` this is for language-specific screenshots that are applicable
1. Translate all `.html` and image files as required then issue a pull request

## Advanced Usage

The following are for administrators of the site and don't affect most people.

### Alerts

Use `src/main/resources/views/ftl/base.ftl` to change static elements like the alert and download version.

## Where does the ASCII art come from?

The ASCII art for the startup banner was created using the online tool available at
[TAAG](http://patorjk.com/software/taag/#p=display&f=Standard&t=MultiBit%20Site)

