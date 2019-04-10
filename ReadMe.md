BondJure
========

## Overview
An online bond calculator for people buying houses.

This project uses tools for deps and build and will require a minimum of clojure 1.9 to be installed.


To run you will need clojure install

And can be run in emacs using cider nREPL Plugin,

# How to run in dev

1. open a terminal
2. change directory to the web folder 
3. run: clojure

#r Check deps are uptodate
You can check if deps are up to date by running cljure with the "outdate" alias 

run clj -R:outdated

# Thoughts
-I am using 1.9 and setting the project up without templates or scaffolding to keep noise to a minimum
-Added olical/depot cause its not fun manual checking if deps are up to date.
-Added figwheel as I have a better workflow with it