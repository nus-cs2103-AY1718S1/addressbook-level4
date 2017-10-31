# ZY-Ang
###### \NavigationMenu.adoc
``` adoc
// Create the menu as an inline list, only if it's to be displayed in a browser
ifndef::env-github[]
[.nav-headbar#navbar]
- https://cs2103aug2017-w11-b2.github.io/main/[Home]
- https://cs2103aug2017-w11-b2.github.io/main/UserGuide.html[User Guide]
- https://cs2103aug2017-w11-b2.github.io/main/DeveloperGuide.html[Developer Guide]
- https://cs2103aug2017-w11-b2.github.io/main/AboutUs.html[About]
- https://cs2103aug2017-w11-b2.github.io/main/ContactUs.html[Contact]
- https://github.com/CS2103AUG2017-W11-B2/main[Github]
endif::[]
```
###### \NavigationMenu.adoc
``` adoc

```
###### \stylesheets\gh-pages.css
``` css
#content #navbar {
    width: 100%;
    max-width: 60.8em;
    background-color: white;
    z-index:1;
    overflow: hidden;
    position:fixed !important;
    top: 0;
}

div.nav-headbar ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: white;
    border-bottom: solid #7a2518;
}

div.nav-headbar li {
    float: left;
}

div.nav-headbar li:last-child {
    float: right;
}

div.nav-headbar li:last-child a {
    padding-right: 20px;
}

div.nav-headbar li p {
    margin: 0;
}

div.nav-headbar li a {
    -webkit-transition-duration: 0.5s; /* Safari */
    transition-duration: 0.5s;
    padding: 10px 10px;
    display: block;
    color: black;
    text-align: center;
    text-decoration: none;
    white-space: nowrap;
    font-family:"Open Sans","DejaVu Sans",sans-serif;
    font-style: normal;
}

div.nav-headbar li a:hover {
    -webkit-transition-duration: 0.5s; /* Safari */
    transition-duration: 0.5s;
    color: white;
    background-color: #a53221;
}
```
