echo off
cd %~dp0
rm Lexer.java
printf "------------------------Analizador lexico------------------------\n"
jflex lexer.l &&^
printf "-----------------------------------------------------------------\n"
