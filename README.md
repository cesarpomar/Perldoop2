# What's Perldoop3 about? #

**Perldoop3** is a total reimplementation of "[Perldoop](https://github.com/citiususc/perldoop)" which was designed to automatically translates Hadoop-ready Perl scripts into its Java counterparts, these could be directly executed on Hadoop while improving their performance. This new version is designed to be able to translate any Perl script within a bounded syntax and without any human intervention but retaining all the features of its first version.
This tool takes as input a Perl script, which must comply with the normal syntax and tagging required by Perldoop, and generates an indented and normalized java code that can be read by a person and compiled with the java compiler.

# Release content #

The latest version can be downloaded in release section. The zip file contains:

* *perldoop3.jar* The main file of *Perldoop3* tool.
* *lib/* The folder contains all the dependencies of *Perldoop3* together with their respective licenses.
* *perldoop3* Script for executing *Perldoop3* on Unix systems
* *perldoop3.bat* Script for executing *Perldoop3* on Windows
* *manual.pdf* *Perldoop3* manual

# How to use #

To run Perldoop3 you need to run at least java 8.

The correct syntax to execute **Perldoop3** is:

`java perldoop3.jar [options] file [file ...]`

If you have java included in the path, you can also include Perldoop3 folder and invoke it as follows:

`perldoop3 [options] infile [infile ...]`

You can use Perldoop3 -h to see all available options.