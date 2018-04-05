# What's Perldoop2 about? #

Perl is one of the most important programming languages in many research areas. However, the most relevant Big Data frameworks, Apache Hadoop, Apache Spark and Apache Storm, do not support natively this language. To take advantage of these Big Data engines Perl programmers should port their applications to Java or Scala, which requires a huge effort, or use utilities as Hadoop Streaming with the corresponding degradation in the performance. For this reason we introduce **Perldoop2**, a Big Data-oriented Perl-Java source-to-source compiler. The compiler is able to generate Java code from Perl applications for sequential execution, but also for running on clusters taking advantage of Hadoop, Spark and Storm engines. Perl programmers only need to tag the source code in order to use the compiler.

If you use **Perldoop2**, please cite:

[*Perldoop2: a Big Data-oriented source-to-source Perl-Java compiler*]( https://doi.org/10.1109/DASC-PICom-DataCom-CyberSciTec.2017.156)  
César Piñeiro, José M. Abuín and Juan C. Pichel.<br/>IEEE Int. Conference on Big Data Intelligence and Computing (DataCom), pages 933-940, 2017.

# Release content #

The latest version can be downloaded from the release section. The zip file contains:

* *perldoop2.jar* -- Main file of *Perldoop2* tool.
* *LICENSE* -- Project license.
* *Perldoop2* -- Script for executing *Perldoop2* on Unix/Linux systems
* *Perldoop2.bat* -- Script for executing *Perldoop2* on Windows systems

# How to use #

To run Perldoop2 you need at least java 8.

The correct syntax to execute **Perldoop2** is:

`java perldoop2.jar [options] file [file ...]`

If you have java included in the path, you can also include Perldoop2 folder and invoke it as follows:

`perldoop2 [options] infile [infile ...]`

You can use perldoop2 -h to see all available options.
