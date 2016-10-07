echo off
cd %~dp0
rm Parser.java
printf "----------------------Analizador sintactico----------------------\n"
yacc -v -Jnorun -Jnoconstruct -Jpackage=perldoop.sintactico -Jsemantic=ParserVal parser.y 
python split.py
printf "-----------------------------------------------------------------\n"