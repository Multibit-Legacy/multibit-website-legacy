# Summary of site transition redirects

## Site v1
Entirely superseded

## Site v2
Current as at April 2013 with many external references by linking sites

### Main pages
`/` -> English index.html
`/{page}.html` -> English main pages (includes v0.4 help pages)

### Help pages

`/helpImages/{image}.png` -> v0.4 help images

`/v0.5/{page}.html` -> v0.5 help pages
`/v0.5/helpImages/{image}.png` -> v0.5 help images

## Site v3
Major push into a dynamic site with full internationalisation capabilities. Follows guidelines provided by W3C regarding language selection and identification.
Notably there will be no inference of language from the Accept-Language header. The user will be expected to select their language from a list of available
translations. The translation box will only be available once more than one language is fully in place.

### Automatic resolution of existing v2 page links
Site v3 will provide endpoints that will automatically resolve the v2 site links and issue redirects (301 Moved Permanently) into v3 links

### Main pages

These are the initial pages that the user finds on their visit to the site.

`/` -> Selected index.html based on Accept-Language
`/{page}.html` -> English main pages
`/{page}.{lang}.html` -> Language-specific page (e.g. en, fr, de, ru, but *not* de_AU, de_CH etc)

### Help pages

Help pages are specifically there to assist users and have a different layout to the main site

`/help` -> English help index.html for all versions (list)
`/help/{page}.html` -> English help index.html for all versions
`/help/{page}.{lang}.html` -> Language-specific help index.html for all versions

`/help/{version}` -> English help index.html for version
`/help/{version}/{page}.html` -> English help index.html for version
`/help/{version}/{page}.{lang}.html` -> Language-specific help index.html for version

### News and blog pages

News and blog pages are arbitrary articles with a timestamp and have a different layout to the main site. The prefix
of `blog` is chosen since it does not imply a recent event.

`/blog` -> English help index.html for recent articles
`/blog/{year}/{month}/{day}/{page}.html` -> English article
`/blog/{year}/{month}/{day}/{page}.{lang}.html` -> Language-specific article
