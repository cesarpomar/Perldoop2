# What's Perldoop2 about? #

**Perldoop2** is a total reimplementation of [Perldoop](https://github.com/citiususc/perldoop) which was designed to automatically translates Hadoop-ready Perl scripts into its Java counterparts, these could be directly executed on Hadoop while improving their performance. This new version is designed to be able to translate any Perl script within a bounded syntax and without any human intervention but retaining all the features of its first version.
This tool takes as input a Perl script, which must comply with the normal syntax and tagging required by Perldoop, and generates an indented and normalized java code that can be read by a person and compiled with the java compiler.

# Release content #

The latest version can be downloaded in release section. The zip file contains:

* *perldoop2.jar* The main file of *Perldoop2* tool.
* *lib/* The folder contains all the dependencies of *Perldoop2* together with their respective licenses.
* *Perldoop2* Script for executing *Perldoop2* on Unix systems
* *Perldoop2.bat* Script for executing *Perldoop2* on Windows

# How to use #

To run Perldoop2 you need to run at least java 8.

The correct syntax to execute **Perldoop2** is:

`java perldoop2.jar [options] file [file ...]`

If you have java included in the path, you can also include Perldoop2 folder and invoke it as follows:

`perldoop2 [options] infile [infile ...]`

You can use perldoop2 -h to see all available options.
