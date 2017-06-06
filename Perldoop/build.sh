echo "------------------------Analizador lexico------------------------" 1>&2
(cd src/perldoop/lexico && jflex lexer.l -nobak)
echo "-----------------------------------------------------------------" 1>&2

echo "----------------------Analizador sintactico----------------------" 1>&2
(cd src/perldoop/sintactico && \
byaccj -Jnorun -Jnoconstruct -Jpackage=perldoop.sintactico -Jsemantic=ParserVal parser.y && \
python3 split.py)

echo "-----------------------------------------------------------------" 1>&2
