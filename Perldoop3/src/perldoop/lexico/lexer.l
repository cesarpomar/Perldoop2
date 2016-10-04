package perldoop.lexico;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import perldoop.modelo.lexico.*;
import perldoop.sintactico.Parser;
import perldoop.error.GestorErrores;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.Opciones;
%%

%byaccj
%public
%class Lexer
%unicode
%line
%column
%char
%state VAR FUN PD

%{
	private StringBuilder buffer;
	private Token yyval;
	private Token comentario;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	private int errores;

	/**
	 * Constructor del analizador léxico
	 * @param in Sistema de entrada
	 * @param opciones Opciones
	 * @param gestorErrores Gestor de errores
	 */
	public Lexer(java.io.Reader in, Opciones opciones, GestorErrores gestorErrores){
		this(in);
		errores = 0;
		this.opciones = opciones;
		this.gestorErrores = gestorErrores;
		buffer = new StringBuilder(100);
	}

	/**
	 * Establece el gestor de errores
	 * @param gestorErrores Gestor de errores
	 */
	public void setGestorErrores(GestorErrores gestorErrores){
		this.gestorErrores = gestorErrores;
	}

	/**
	 * Obtiene el gestor de errores
	 * @return Gestor de errores
	 */
	public GestorErrores getGestorErrores(){
		return gestorErrores;
	}

	/**
	 * Obtiene las opciones
	 * @return Opciones
	 */
	public Opciones getOpciones() {
		return opciones;
	}

	/**
	 * Establece las opciones
	 * @param opciones Opciones
	 */
	public void setOpciones(Opciones opciones) {
		this.opciones = opciones;
	}

	/**
	 * Obtiene el número de errores léxicos, si no hay errores el analisis
	 * se ha realizado correctamente.
	 * @return Número de errores
	 */
	public int getErrores(){
		return errores;
	}

	/**
	 * Inicia el analisis lexico y la creacion de la lista de tokens. Una vez 
	 * terminado retorna una lista con todos los tokens del fichero.
	 * @return Lista de tokens
	 * @throws IOException Error durante la lectura del fichero
	 */
	public List<Token> getTokens() throws IOException{
		List<Token> tokens = new ArrayList<>(10000);
		while(yylex() > 0){
			tokens.add(yyval);
		}
		//Añadimos final de fichero
		token("EOF",0);
		tokens.add(yyval);
		return tokens;
	}

	/**
	 * Crea un token con token(yytext(), tipo, false)
	 * @param tipo Tipo
	 * @return tipo
	 */
	private int token(int tipo){
		return token(yytext(),tipo,false);
	}

	/**
	 * Crea un token con token(valor, tipo, false)
	 * @param valor Valor
	 * @param tipo Tipo
	 * @return tipo
	 */
	private int token(String valor, int tipo){
		return token(valor,tipo,false);
	}

	/**
	 * Crea un token, la posición del mismo se obtiene
	 * directamente del analizador.
	 * @param valor Valor del token
	 * @param tipo Tipo de token
	 * @param isEtiqueta El token es etiqueta
	 * @return tipo
	 */
	private int token(String valor, int tipo, boolean isEtiqueta){
		yyval = new Token();
		yyval.setEtiqueta(isEtiqueta);
		yyval.setValor(valor);
		yyval.setTipo(tipo);
		yyval.setLinea(yyline);
		yyval.setColumna(yycolumn);
		yyval.setPosicion(yychar);
		return tipo;
	}
%}
ID				=	[A-Za-z_][A-Za-z0-9_]*
NATURAL			=	[1-9][0-9]*
HEXADECIMAL		=	0[xX][0-9A-Fa-f]+
OCTAL			=	0[0-7]+
BINARIO			=	0[bB][01]+
ENTERO			=	({NATURAL} | {HEXADECIMAL} | {OCTAL} | {BINARIO} | 0)
FLOTANTE		=	(([0-9]*\.[0-9]+)|([0-9]+\.[0-9]*))
CIENTIFICO		=	({NATURAL}|{FLOTANTE})[eE](\+-)?{NATURAL}
DECIMAL			=	({FLOTANTE}|{CIENTIFICO})
CADENA_SIMPLE	=	\'([^\'\n]|(\\.))*\'
CADENA_DOBLE	=	\"([^\"\n]|(\\.))*\"
CADENA_COMANDO	=	`([^`\n]|(\\.))*`

REGEX_MOD		=	(i|s|m|x|o|p|d|a|u|l|g|cg|e)?
M_REGEX			=	m\/.*\/{REGEX_MOD}
S_REGEX			=	s\/.*\/.*\/{REGEX_MOD}
Y_REGEX			=	(y|tr)\/.*\/.*\/{REGEX_MOD}

IGNORAR_LINEA	=	.*\#<ignore-line>
IGNORAR_BLOQUE	=	\#<ignore-block>\n[^]*\#<ignore-block>
IMPORT_JAVA		=	\#<java-import>.*
LINEA_JAVA		=	\#<java-line>.*

PD_ID			=	<{ID}>
PD_VAR			=	<(\$|@|%){ID}>
PD_NUM			=	<{ENTERO}>


%%

	/*----Paquetes----*/
<VAR>{
	{ID}				{return token(Parser.VAR);}
	"::"				{return token(Parser.AMBITO);}
	"\#"				{return token('#');}
	.					{yypushback(1);yybegin(YYINITIAL);}
}

<FUN>{
	{ID}				{return token(Parser.ID);}
	"::"				{return token(Parser.AMBITO);}
	.					{yypushback(1);yybegin(YYINITIAL);}
}

<YYINITIAL>{
		/*----Identificador----*/
	"\$"				{yybegin(VAR);return token('$');}
	"@"					{yybegin(VAR);return token('@');}
	"%"					{yybegin(VAR);return token('%');}
	"&"					{yybegin(FUN);return token('&');} 
	{ID}				{String txt = yytext(); 
						int tipo = Reservados.buscarId(txt, Parser.ID);
						if(tipo==Parser.SUB) yybegin(VAR);
						return token(txt, tipo);}

		/*----Constantes----*/
	{ENTERO}			{return token(Parser.ENTERO);}
	{DECIMAL}			{return token(Parser.DECIMAL);}
	{CADENA_SIMPLE}		{return token(Parser.CADENA_SIMPLE);}
	{CADENA_DOBLE}		{return token(Parser.CADENA_DOBLE);}
	{CADENA_COMANDO}	{return token(Parser.CADENA_COMANDO);}

		/*----Operadores----*/
	"\+"				{return token('+');}
	"\+\+"				{return token(Parser.MAS_MAS);}
	"-"					{return token('-');}
	"--"				{return token(Parser.MENOS_MENOS);}
	"\*"				{return token('*');}
	"/"					{return token('/');}
	"\*\*"				{return token(Parser.POW);}
	"\."				{return token('.');} 
	"\|"				{return token('|');} 
	"~"					{return token('~');} //Not binario
	"\^"				{return token('^');} //Xor binario
	"<<"				{return token(Parser.DESP_I);}
	">>"				{return token(Parser.DESP_D);}
	"\|\|"				{return token(Parser.LOR);}
	"//"				{return token(Parser.DLOR);}
	"&&"				{return token(Parser.LAND);}
	"!"					{return token('!');}   
	"<"					{return token('<');}
	">"					{return token('>');}
	"<="				{return token(Parser.NUM_LE);}
	">="				{return token(Parser.NUM_GE);}
	"=="				{return token(Parser.NUM_EQ);}
	"!="				{return token(Parser.NUM_NE);}
	"=~"				{return token(Parser.STR_REX);}    //Cumple expresion regular
	"!~"				{return token(Parser.STR_NO_REX);} //No cumple expresion regular
	"<=>"				{return token(Parser.CMP);}        //Comparador
	"~~"				{return token(Parser.SMART_EQ);}   //Operador smart

		/*----Asignaciones----*/
	"="					{return token('=');}
	"*="				{return token(Parser.MULTI_IGUAL);}
	"/="				{return token(Parser.DIV_IGUAL);}
	"%="				{return token(Parser.MOD_IGUAL);}
	"\*\*="				{return token(Parser.POW_IGUAL);}
	"\+="				{return token(Parser.MAS_IGUAL);}
	"-="				{return token(Parser.MENOS_IGUAL);}
	"<<="				{return token(Parser.DESP_I_IGUAL);}
	">>="				{return token(Parser.DESP_D_IGUAL);}
	"&="				{return token(Parser.AND_IGUAL);}
	"\|="				{return token(Parser.OR_IGUAL);}
	"\^="				{return token(Parser.XOR_IGUAL);}
	"&&="				{return token(Parser.LAND_IGUAL);}
	"\|\|="				{return token(Parser.LOR_IGUAL);}
	"//="				{return token(Parser.DLOR_IGUAL);}
	"\.="				{return token(Parser.CONCAT_IGUAL);}
	"x="				{return token(Parser.X_IGUAL);}

		/*----Delimitadores----*/
	"\("				{return token('(');}
	"\)"				{return token(')');}
	"\["				{return token('[');}
	"\]"				{return token(']');}
	"\{"				{return token('{');}
	"\}"				{return token('}');}
	(","|"=>")			{return token(',');}
	"\.\."				{return token(Parser.DOS_PUNTOS);}
	"\?"				{return token('?');}
	";"					{return token(';');}
	":"					{return token(':');}
	"::"				{return token(Parser.AMBITO);}
	"\\"				{return token('\\');}
	"->"				{return token(Parser.FLECHA);}

		/*----Expresiones regualares----*/
	{M_REGEX}			{return token(Parser.M_REGEX);}
	{S_REGEX}			{return token(Parser.S_REGEX);}
	{Y_REGEX}			{return token(Parser.Y_REGEX);}

		/*----Entrada y Salida----*/
	"<STDIN>"			{return token(Parser.STDIN);}
	"<STDOUT>"			{return token(Parser.STDOUT);}
	"<STDERR>"			{return token(Parser.STDERR);}

		/*----Comentarios----*/

	"\#"				{yybegin(PD);token("", Parser.COMENTARIO, true);
						comentario=yyval;buffer.setLength(0);}
	{IGNORAR_LINEA}		{}
	{IGNORAR_BLOQUE}	{}
	{IMPORT_JAVA}		{return token(Parser.IMPORT_JAVA);}
	{LINEA_JAVA}		{return token(Parser.LINEA_JAVA);}
}

	/*----Etiquetas----*/
<PD> {
	{PD_ID}				{String t = yytext(); Integer tipo = Reservados.buscarEtiqueta(t);
						if (tipo == null){buffer.append(t);token(t, 0);
						gestorErrores.error(Errores.AVISO,Errores.ETIQUETA_IGNORADA, yyval, t);}
						else{return token(t, tipo, true);}}
	{PD_NUM}			{return token(yytext(), Parser.PD_NUM, true);}
	{PD_VAR}			{return token(yytext(), Parser.PD_VAR, true);}
	[^\n\r]				{buffer.append(yytext());}
	"\r"				{}
	"\n"				{yybegin(YYINITIAL);comentario.setValor(buffer.toString());
						if(!comentario.getValor().trim().isEmpty()){yyval=comentario;return comentario.getTipo();}}
}

<YYINITIAL>{
		/*----Caracteres ignorados----*/
	[ \t\r\n]			{}

		/*----Error----*/
	[^]					{errores++;token(0);gestorErrores.error(Errores.CARACTER_INVALIDO, yyval);}
}