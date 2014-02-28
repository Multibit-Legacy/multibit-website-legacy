## Welcome to the MultiBit Website Repository

This repo contains the source for the [MultiBit website](https://multibit.org).

## Acknowledgements

Much of the groundwork for this website was put in place by [Saivann Carigan](https://github.com/saivann) - thanks from the MultiBit team!

## MultiBit Website

From a technical point of view this project uses

* Java - Primary language of the app
* [Maven](http://maven.apache.org/) - Build system
* [Dropwizard](http://dropwizard.io) - Self-contained web server
* HTML5 and CSS - All pages are simple HTML5
* [LESS](http://lesscss.org/) - LESS is compiled into CSS to provide succinct stylesheets

Why Dropwizard? We don't want the complexity that comes with an application server or an
external servlet container. Dropwizard gives us the simplicity we crave.

We looked at [Jekyll](https://github.com/mojombo/jekyll), and while it's a great technology, we
found it a bit limiting for what we needed from our website.

## Branches

We follow the ["master-develop" branching strategy](http://nvie.com/posts/a-successful-git-branching-model/).

This means that the latest release is on the "master" branch (the default) and the latest release candidate is on the "develop" branch.
Any issues are addressed in feature branches from "develop" and merged in as required.

## Getting started

If you are already familiar with Java and Maven you can skip ahead to the Build and Preview section.

Some of the technology used when building the site may be unfamiliar at first so you'll need to install some supporting
code first. It should only take a few minutes and you'll be up and running.

1. Install or upgrade [Java](https://java.com/en/download/index.jsp)
1. Install or upgrade [Maven](https://maven.apache.org/download.cgi)

If you intend to do a lot of work involving the underlying Java code you'll need an IDE. We recommend [Intellij](https://www.jetbrains.com/idea/download/)
([best](http://programmers.stackexchange.com/a/24231/7167)) or [Eclipse](https://www.eclipse.org/downloads/). Both are available as free downloads.

If you just want to edit CSS and HTML you can do it all with a text editor and some console commands.

### Build and Preview

There are two ways to run up the project depending on whether you have access to a Java IDE or not.

#### Inside an IDE

Import the project as a Maven project in the usual manner.

To start the project you just need to execute `SiteService.main()` as a Java application. You'll need a runtime configuration
that passes in `server site-config.yml` as the Program Arguments.

Open a browser to [http://localhost:8080/](http://localhost:8080/) and you should see the site.

#### Outside of an IDE

Assuming that you've got Java and Maven installed you'll find it very straightforward to get the site running. Just clone
from GitHub and do the following:

```
cd <project root>
mvn clean install
java -jar target/site-<version>.jar server site-config.yml
```

where `<project root>` is the root directory of the project as checked out through git and `<version>` is the version
as found in `pom.xml` (e.g. "3.0.0") but you'll see a `.jar` in the `target` directory so it'll be obvious.

All commands will work on *nix without modification, use \ instead of / for Windows.

Open a browser to [http://localhost:8080/](http://localhost:8080/) and you should see the site.

## Workflows

Nobody wants to waste time getting stuff done, so here are some processes that we follow to make changes to the site
efficiently.

### Changing CSS/HTML

#### Inside an IDE

If you are running the application within an IDE do the following:

1. Create a runtime configuration for `mvn generate-resources` call it "Maven Resources"
1. Start the `SiteService` process and leave it running continuously
1. For CSS, use Firebug/Developer Tools to preview the effect you're after then locate the `main.less` file and edit to
accommodate your changes otherwise
1. For HTML just edit the appropriate file under `src/main/resources/views/html`
1. Run "Maven Resources" to compile `main.less` to `main.css` and copy all updated resources to `target`
1. Refresh your browser and verify that Dropwizard serves the resource as a 200 OK rather than 304 NOT MODIFIED if the
change is not apparent

#### Outside an IDE

If you're running via the command line your workflow is unfortunately a little less efficient.

1. Run up the site application as detailed in the earlier sections
1. Use Firebug/Developer Tools to preview the effect you're after
1. For CSS, use Firebug/Developer Tools to preview the effect you're after then locate the `main.less` file and edit to
accommodate your changes otherwise
1. For HTML just edit the appropriate file under `src/main/resources/views/html`
1. Stop the site application process (CTRL+C)
1. Run `mvn package` to rebuild with the newly generated `main.css`
1. Restart with `java -jar target/site-<version>.jar server site-config.yml`
1. Refresh your browser to observe the change

Clearly an IDE is the better way to go if you think you will have a lot of editing work that will need an incremental
approach.

### Translation

[Bitcoin is a global currency](http://bitcoin.org) and so this site has many translations. If you'd like to contribute your own translation for the pages on offer please use this process:

1. Find the two letter [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) code for your language (fr, en, jp etc)
1. Copy existing html files from `src/main/resources/views/html/{lang}/` according to your source language (e.g. you're a French to Japanese expert so base on `fr` and copy to `jp`)
1. Paste new html files to `src/main/resources/views/html/{lang}/` according to your target language
1. Repeat for image files in `src/main/resources/assets/images/{lang}` this is for language-specific screenshots that are applicable
1. Translate all `.html` and image files as required then issue a pull request

### Advanced Usage

The following are for administrators of the site and don't affect most people.

#### Alerts

Use `src/main/resources/views/ftl/base.ftl` to change static elements like the alert and download version.

#### Building the web support files (`site.xml` and `atom.xml`)

Before pushing to GitHub the `site.xml` and `atom.xml` files need to be regenerated. This is done by executing
the normal Maven build but adding a profile parameter as well:

```
mvn -DupdateWebSupport=true clean verify
```

### Where does the ASCII art come from?

The ASCII art for the startup banner was created using the online tool available at
[TAAG](http://patorjk.com/software/taag/#p=display&f=Standard&t=MultiBit%20Site)

### I like this approach to building a website can I copy it?

Yes. All the code in this repo is under the MIT license so you are welcome to take it and go.

However, this implementation serves the [MultiBit site](https://multibit.org) so don't just clone this and run a
mirror without first consulting us.

You may be interested in deploying [Dropwizard applications on Heroku](http://gary-rowe.com/agilestack/2012/10/09/how-to-deploy-a-dropwizard-project-to-heroku/)
since their free instances are sufficient for most low to medium traffic sites and they integrate very well with git.

### I've spotted a bug in the site what should I do?

[Raise an Issue](https://github.com/jim618/multibit-website/issues) through GitHub and we'll address it. Before diving
in make sure that you've checked to see that the issue has not already been raised by another.
