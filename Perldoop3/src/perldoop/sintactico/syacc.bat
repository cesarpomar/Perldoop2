echo off
cd %~dp0
rm Parser.java
echo ----------------------Analizador sintactico----------------------
yacc -Jnorun -Jnoconstruct -Jpackage=perldoop.sintactico -Jsemantic=ParserVal parser.y 
python split.py
echo -----------------------------------------------------------------