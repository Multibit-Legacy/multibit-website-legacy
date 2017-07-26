
# Multibit is Deprecated - Do Not Use

Wednesday, July 26, 2017

Dear Bitcoin Community,

It is time for us to let Multibit go. 

KeepKey acquired Multibit a little over 1 year ago. At the time, the engineers who originally built and supported Multibit had announced that they would no longer be working on it or providing support. Multibit played an important role in the Bitcoin infrastructure. We felt that it was important for Multibit to continue and hoped that with our existing support and development teams, we would be able to keep Multibit alive.

The reality is that Multibit is in need of a lot of work. It has stubborn bugs that have caused us and Multibit users much grief. Additionally, Bitcoin has gone through a fundamental change in regards to the way fees work. The addition of SegWit in the coming weeks will mean the Multibit software has fallen still further behind.

Unfortunately, KeepKey simply does not have the resources to support the current issues, nor to rebuild Multibit to ensure ideal user experience. By focusing our attention on the KeepKey device, we will continue building and improving the best hardware wallet available.

Thus, KeepKey will discontinue support and maintenance of Multibit, effective immediately.

We recommend that all Multibit users discontinue using it and you move your keys to other wallet software of your choosing. 

## Next Steps for Multibit Users 
Videos that demonstrate how to move your wallet to Electrum are available on YouTube.

- Multibit HD: https://youtu.be/E-KcY6KUVnY
- Multibit Classic: https://youtu.be/LaijbTcxsv8

Please note that the version of Electrum available for download today (version 2.8.3) doesn’t fully support the importing Multibit HD wallet words. The version shown in the Multibit HD video is the soon-to-be-released next version.

Multibit was a fantastic piece of software in its time, and we want to thank the Multibit developers for such an important contribution to Bitcoin’s history.

Sincerely,

Ken Heutmaker

CTO, KeepKey 

------
## Welcome to the MultiBit Website Repository v4.0.0

Project status: Public beta. Expect minor bugs and API changes.

Build status: [![Build Status](https://travis-ci.org/bitcoin-solutions/multibit-website.svg?branch=develop)](https://travis-ci.org/bitcoin-solutions/multibit-website)

This branch contains the source for the [MultiBit website](https://multibit.org).

## MultiBit Website

From a technical point of view this project uses

* Java 7+ - Primary language of the app
* [Maven](http://maven.apache.org/) - Build system
* [Dropwizard](http://dropwizard.io) - Self-contained web server
* HTML5 and CSS - All pages are simple HTML5 with Bootstrap and Font Awesome
* [LESS](http://lesscss.org/) - LESS is compiled into CSS to provide succinct stylesheets

Why Dropwizard? We don't want the complexity that comes with an application server or an
external servlet container. Dropwizard gives us the simplicity we crave.

We looked at [Jekyll](https://github.com/mojombo/jekyll), and while it's a great technology, we
found it a bit limiting for what we needed from our website.

## Branches

We follow the ["Git Flow" branching strategy](http://nvie.com/posts/a-successful-git-branching-model/).

This means that the latest release is on the `master` branch (the default) and the latest release candidate is on the `develop` branch.
Any issues are addressed in feature branches from `develop` and merged in as required.

## Getting started

The website is a standard Maven build from a GitHub repository.

Below are some basic instructions for developers.

### Verify you have Git

```
$ git --version
```

[Install git](https://help.github.com/articles/set-up-git/) if necessary.

Then, if this is your first time working with the website source code, clone the source code repository (over HTTPS) using:

```
$ git clone https://github.com/bitcoin-solutions/multibit-website.git
```
A sub-directory called `multibit-website` will be created which is your project root directory.

To update a previous clone of the website use a pull instead:

```
$ cd <project root>
$ git pull
```

### Verify you have Maven 3+

Most IDEs (such as [Intellij Community Edition](http://www.jetbrains.com/idea/download/)) come with support for Maven built in, 
but if not then you may need to [install it manually](http://maven.apache.org/download.cgi).

IDEs such as Eclipse may require the [m2eclipse plugin](http://www.sonatype.org/m2eclipse) to be configured.

To quickly check that you have Maven 3+ installed check on the command line:
```
$ mvn --version
```
Maven uses a file called `pom.xml` present in the MultiBit HD source code project directory to provide all the build information.

### Build and Preview

There are two ways to run up the project depending on whether you have access to a Java IDE or not.

#### Start the application (from an IDE)

To run the application within an IDE, simply execute `SiteService.main()`. You'll need a runtime configuration
that passes in `server site-config.yml` as the Program Arguments.

#### Start the application (from the command line)

To run the application from the command line, first build from the project root directory (pulling in all sources from upstream):
```
cd <project root>
mvn clean dependency:sources install
```
then start the application using the shaded JAR:
```
java -jar target/site-<version>.jar server site-config.yml
```

where `<project root>` is the root directory of the project as checked out through git and `<version>` is the version
as found in `pom.xml` (e.g. "4.0.0") but you'll see a `.jar` in the `target` directory so it'll be obvious.

All commands will work on *nix without modification, use `\` instead of `/` for Windows.

Open a browser to [http://localhost:8888/](http://localhost:8888/) and you should see the site (8888 is the develop branch port).

## Workflow inside an IDE (strongly recommended)

Nobody wants to waste time getting stuff done, so here are some processes that we follow to make changes to the site
efficiently.

### Changing FTL files (rare)

Changes to the Freemarker templates (`.ftl`) that wrap the HTML require a restart to be picked up. To optimise this
process you should set up a runtime configuration as follows:

1. Adjust the runtime configuration for `SiteService` so that it performs both a "make" then a `mvn generate-resources`
2. Start the `SiteService` process and only restart if you make a change to an `.ftl` file (should be rare)
3. The restart will automatically generate fresh CSS from the LESS so it may be a quicker workflow
4. Refresh your browser and verify that Dropwizard serves the resource as a 200 OK rather than 304 NOT MODIFIED if the
change is not apparent

### Changing only CSS/HTML

Changes to CSS are made by editing the `.less` files and compiling them through Maven using `mvn generate-resources`.
HTML files can be edited directly. There is no need to restart `SiteService` if the changes do not involve a `.ftl` file
which is the normal state of affairs.

1. Create a runtime configuration for `mvn generate-resources` (no `clean`) call it "Maven Resources"
2. Start the `SiteService` process and leave it running continuously
3. For CSS, use Firebug/Developer Tools to preview the effect you're after then locate the `main.less` file and edit to
accommodate your changes otherwise
4. For HTML just edit the appropriate file under `src/main/resources/views/html`
5. Run "Maven Resources" to compile `main.less` to `target/classes/assets/css/main.css`
6. Refresh your browser and verify that Dropwizard serves the resource as a 200 OK rather than 304 NOT MODIFIED if the
change is not apparent

## Workflow outside an IDE (really slow)

If you're running via the command line your workflow is unfortunately a little less efficient.

1. Run up the site application as detailed in the earlier sections
2. Use Firebug/Developer Tools to preview the effect you're after
3. For CSS, use Firebug/Developer Tools to preview the effect you're after then locate the `main.less` file and edit to
accommodate your changes otherwise
4. For HTML just edit the appropriate file under `src/main/resources/views/html`
5. Stop the site application process (CTRL+C)
6. Run `mvn package` to rebuild with the newly generated `main.css`
7. Restart with `java -jar target/site-<version>.jar server site-config.yml`
8. Refresh your browser to observe the change

Clearly an IDE is the better way to go if you think you will have a lot of editing work that will need an incremental
approach.

## Translation

[Bitcoin is a global currency](http://bitcoin.org) and so this site has many translations. If you'd like to contribute your own translation for the pages on offer please use this process:

1. Find the two letter [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) code for your language (fr, en, ja etc)
2. Copy existing html files from `src/main/resources/views/html/{lang}/` according to your source language (e.g. you're a French to Japanese expert so base on `fr` and copy to
`ja`)
3. Paste new html files to `src/main/resources/views/html/{lang}/` according to your target language
4. Repeat for image files in `src/main/resources/assets/images/{lang}` this is for language-specific screenshots that are applicable
5. Translate all `.html` and image files as required then issue a pull request

## Advanced Usage

The following are for administrators of the site and don't affect most people.

### Alerts

Use `src/main/resources/views/ftl/base.ftl` to change static elements like the alert and download version. 

### Building the web support files (`site.xml` and `atom.xml`)

Before pushing to GitHub the `site.xml` and `atom.xml` files need to be regenerated. This is done by executing
the normal Maven build but adding a profile parameter as well:

```
mvn -DupdateWebSupport=true clean verify
```

### Pushing changes

Push changes to the git repo using

```
git push production release-4.0.0:master
```

## Where does the ASCII art come from?

The ASCII art for the startup banner was created using the online tool available at
[TAAG](http://patorjk.com/software/taag/#p=display&f=Standard&t=MultiBit%20Site)

## I like this approach to building a website can I copy it?

Yes. All the code in this repo is under the MIT license so you are welcome to take it and go.

However, this implementation serves the [MultiBit site](https://multibit.org) so don't just clone this and run a
mirror without first consulting us.

You may be interested in deploying [Dropwizard applications on Heroku](http://gary-rowe.com/agilestack/2012/10/09/how-to-deploy-a-dropwizard-project-to-heroku/)
since their free instances are sufficient for most low to medium traffic sites and they integrate very well with git.

## I've spotted a bug in the site what should I do?

[Raise an Issue](https://github.com/bitcoin-solutions/multibit-website/issues) through GitHub and we'll address it. Before diving
in make sure that you've checked to see that the issue has not already been raised by another.
