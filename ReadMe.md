BondJure
========

## Overview
An online bond calculator for people buying houses.

This project uses tools for dependency management and building and will require a minimum of clojure 1.9 to be installed.
Must have Java 1.2 - 1.8 (Jave 1.9 has issue with some of the dependencies for the clojurescript build)

To run you will need clojure install

The project can be run in emacs using cider nREPL Plugin,

# How to run in dev

1. open a terminal
2. make sure your running java 1.8 export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
3. change directory to "server" folder
4. run clojure -A:repl build.clj figwheel 63481
5. A repl session can connect to nrep on port 63481 
6. The web server will be running on localhost:3000
 

#r Check deps are uptodate
You can check if deps are up to date by running cljure with the "outdate" alias 

run clj -R:outdated

# Thoughts
-I am using 1.9 and setting the project up without templates or scaffolding to keep noise to a minimum
-Added olical/depot cause its not fun manual checking if deps are up to date.
-Added figwheel as I have a better workflow with it
-Added the form layout first because I like to work "Front to back"