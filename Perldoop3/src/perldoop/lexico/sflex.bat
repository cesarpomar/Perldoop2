echo off
cd %~dp0
rm Lexer.java
echo ------------------------Analizador lexico------------------------
jflex lexer.l ^
&& echo -----------------------------------------------------------------
