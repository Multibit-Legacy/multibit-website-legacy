# Summary of site transition redirects

## Site v3
Major push into a dynamic site with full internationalisation capabilities. Follows guidelines provided by W3C regarding language selection and identification.
Notably there will be no inference of language from the Accept-Language header. The user will be expected to select their language from a list of available
translations. The translation box will only be available once more than one language is fully in place.

Language codes are based on the two letter [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes#Partial_ISO_639_table) codes.

The language code is placed in the path either at the start (for main pages) or one level down (for images, help etc). This is to ensure a uniform structure
between image and textual resource provision. Dropwizard handles image assets internally and requires a specific path format to operate correctly.

### Automatic resolution of existing v2 page links
Site v3 will provide endpoints that will automatically resolve the v2 site links and issue redirects (301 Moved Permanently) into v3 links

### Main pages

These are the initial pages that the user finds on their visit to the site.

`/` -> English index.html
`/{page}.html` -> English main pages
`/{lang}/{page}.html` -> Language-specific page (e.g. en, fr, de, ru, but *not* de_AU, de_CH etc)

### Help pages

Help pages are specifically there to assist users and have a different layout to the main site

`/help` -> English help index.html for all versions
`/help/{lang}` -> Language-specific help index.html for all versions
`/help/{lang}/{version}/{page}.html` -> Language-specific help index.html for version

### Images

Images are currently cached and served internally by Dropwizard but can be handled by a Content Delivery Network (CDN) or a static web server

`/images/{version}/.../{image path}` -> English image path
`/images/{lang}/{version}/.../{image path}` -> Language-specific image path

### News and blog pages

News and blog pages are arbitrary articles with a timestamp and have a different layout to the main site. The prefix
of `blog` is chosen since it does not imply a recent event.

`/blog` -> English help index.html for recent articles
`/blog/{year}/{month}/{day}/{page}.html` -> English article
`/blog/{lang}/{year}/{month}/{day}/{page}.html` -> Language-specific article

## Site v2
Current as at April 2013 with many external references by linking sites
Superseded in June 2013 by v3

### Main pages
`/` -> English index.html
`/{page}.html` -> English main pages (includes v0.4 help pages)

### Help pages

`/helpImages/{image}.png` -> v0.4 help images

`/v0.5/{page}.html` -> v0.5 help pages
`/v0.5/helpImages/{image}.png` -> v0.5 help images

## Site v1
Entirely superseded

