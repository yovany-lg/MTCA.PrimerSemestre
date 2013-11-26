 rem File: clj.bat
 rem Version: 1.0
 rem Date: 2013_11_22
 rem 
 rem Script based off of the Wikibooks Clojure Programming Getting Started.
 rem Source: http://en.wikibooks.org/wiki/Clojure_Programming/Getting_Started#Windows_2
 rem
 rem Usage:
 rem
 rem clj                           # Starts REPL
 rem clj my_script.clj             # Runs an external script
 rem clj my_script.clj arg1 arg2   # Runs the script passing it arguments
 
 @echo off
 
 :: Change the following to match your paths
 set CLOJURE_DIR=C:\clojure-1.5.1
 set CLOJURE_VERSION=1.5.1
 set CLOJURE_JAR="%CLOJURE_DIR%\clojure-%CLOJURE_VERSION%.jar"
 
 if (%1) == () (
     :: Start REPL
     java -server -cp .;%CLOJURE_JAR% clojure.main
 ) else (
     :: Start some_script.clj
     java -server -cp .;%CLOJURE_JAR% clojure.main %1 -- %*
 )