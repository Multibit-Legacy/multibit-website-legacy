## How the LESS files fit together

`main.less` provides the overall styling for the site.
 
`bootswatch.less` provides custom styling that overrides the standard Bootstrap CSS.

`variables.less` provides all the variables so there is a single point of reference.

The general idea is that Bootstrap CSS provides the foundation, then it is overridden by
the above files which are contained in a single CSS that is fast to download.

See `head.ftl` for the CDN links for CSS/JS.

## Workflow

Running `mvn generate-resources` will overwrite any existing `main.css` into the `target`
directory where it will be picked up by a running Dropwizard through a page refresh.

There is no need to restart the service for a LESS -> CSS change made in this way

