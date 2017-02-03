package perldoop.lexico;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import perldoop.modelo.lexico.Token;
import perldoop.sintactico.Parser;
import perldoop.preprocesador.Preprocesador;
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
%state VAR FUN PD IGNORAR DELIMITADOR DELIMITADOR_DOBLE ANALISIS ANALISIS_VAR ANALISIS_ACCESO ANALISIS_ACCESO_INDEX ANALISIS_ACCESO_INDEX_STR REX_T REX_MOD

%{
	private Token yyval;
	private Token tokenAux;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	
	/*Atributos para interpolar*/
	private StringBuilder buffer;
	private Character sepBlock;
	private char sep;
	private int stack;
	private int sepN;
	private boolean interpola;
	private int estadoReturn;
	private int analisisTipo;
	private int flag;
	private boolean isVar;

	/**
	 * Constructor del analizador léxico
	 * @param in Sistema de entrada
	 * @param opciones Opciones
	 * @param gestorErrores Gestor de errores
	 */
	public Lexer(java.io.Reader in, Opciones opciones, GestorErrores gestorErrores){
		this(in);
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
	 * Inicia el analisis lexico y la creacion de la lista de tokens. Una vez 
	 * terminado retorna una lista con todos los tokens del fichero.
	 * @return Lista de tokens
	 * @throws IOException Error durante la lectura del fichero
	 */
	public List<Token> getTokens() throws IOException{
		List<Token> tokens = new ArrayList<>(10000);
		while(yylex() != 0){
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

	/**
	 * Función invocada en caso de error
	 */	
	private void error(){
		token(0);
		gestorErrores.error(Errores.CARACTER_INVALIDO, yyval, yytext());	
		yybegin(YYINITIAL);
	}

	/**
	 * Analiza el interior de una expresion delimitada
	 *
	 * @param analisisTipo Tipo de tokens a retornar
	 * @param estadoReturn Estado de cambio al terminar
	 * @param sepN Numero de delimitadores
	 * @param interpola Interpolar las variables
	 */	
	private void analizar(int analisisTipo, int estadoReturn, int sepN, Character sep, boolean interpola){
		stack=0;
		tokenAux=null;
		this.analisisTipo = analisisTipo;
		this.estadoReturn = estadoReturn;
		this.sepN = sepN;
		this.interpola = interpola;	
		if(sep==null){
			yybegin(DELIMITADOR);	
		}else{
			this.sep=sep;
			yybegin(ANALISIS);			
		}
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

IGNORAR_LINEA	=	<ignore-line>
IGNORAR_BLOQUE	=	<ignore-block>
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
	[^]					{yypushback(1);yybegin(YYINITIAL);}
}

<FUN>{
	{ID}				{return token(Parser.ID);}
	"::"				{return token(Parser.AMBITO);}
	[^]					{yypushback(1);yybegin(YYINITIAL);}
}

<YYINITIAL>{

		/*----Quote-Like Operators----*/
	"'"					{analizar('\'', YYINITIAL, 1, '\'', false);return token('\'');}
	"\""				{analizar('"',  YYINITIAL, 1, '"',  true); return token('"');}
	"`"					{analizar('`',  YYINITIAL, 1, '`',  true); return token('`');}
	"q"					{analizar(Parser.SEP, YYINITIAL, 2, null, false);return token(Parser.Q);}
	"qw"				{analizar(Parser.SEP, YYINITIAL, 2, null, false);return token(Parser.QW);}
	"qq"				{analizar(Parser.SEP, YYINITIAL, 2, null, true);return token(Parser.QQ);}
	"qr"				{analizar(Parser.SEP, YYINITIAL, 2, null, true);return token(Parser.QR);}
	"qx"				{analizar(Parser.SEP, YYINITIAL, 2, null, true);return token(Parser.QX);}

		/*----Identificador----*/
	"\$"				{yybegin(VAR);return token('$');}
	"@"					{yybegin(VAR);return token('@');}
	"%"					{yybegin(VAR);return token('%');}
	"&"					{yybegin(FUN);return token('&');} 
	{ID}				{String txt = yytext(); 
						int tipo = PalabrasReservadas.buscarId(txt, Parser.ID);
						if(tipo==Parser.SUB) yybegin(VAR);
						return token(txt, tipo);}

		/*----Numeros----*/
	{ENTERO}			{return token(Parser.ENTERO);}
	{DECIMAL}			{return token(Parser.DECIMAL);}

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
	"<=>"				{return token(Parser.NUM_CMP);}    //Comparador
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
	"=~"				{yybegin(REX_T);return token(Parser.STR_REX);}
	"!~"				{yybegin(REX_T);return token(Parser.STR_NO_REX);}	

		/*----Comentarios----*/

	"\#!".*					{}
	^.*\#{IGNORAR_LINEA}.*	{}
	\#{IGNORAR_BLOQUE}.*		{yybegin(IGNORAR);}
	{IMPORT_JAVA}			{return token(Parser.IMPORT_JAVA);}
	{LINEA_JAVA}			{return token(Parser.LINEA_JAVA);}
	"\#"					{yybegin(PD);token("", Parser.COMENTARIO, true);
							tokenAux=yyval;buffer.setLength(0);}
						
}

<IGNORAR>{
	[^]					{}
	{IGNORAR_BLOQUE}.*	{yybegin(YYINITIAL);}
}


<REX_T>{				
	"m"					{analizar(Parser.SEP, REX_MOD, 2, null, true);return token(Parser.M_REX);}
	"/"					{analizar(Parser.SEP, REX_MOD, 1, '/', true);return token(Parser.SEP);}
	"\""				{analizar(Parser.SEP, REX_MOD, 1, '"', true);return token(Parser.SEP);}
	"s"					{analizar(Parser.SEP, REX_MOD, 3, null, true);return token(Parser.S_REX);}
	"y"					{analizar(Parser.SEP, REX_MOD, 3, null, false);return token(Parser.Y_REX);}
	"tr"				{analizar(Parser.SEP, REX_MOD, 3, null, false);return token(Parser.Y_REX);}
	[\s]				{}
	[^]					{error();}
}

<REX_MOD>{
	[a-z]+				{yybegin(YYINITIAL);return token(Parser.REX_MOD);}
	[^]					{yybegin(YYINITIAL);yypushback(1);}
}

<DELIMITADOR>{
	[\s]				{}
	.					{
							yybegin(ANALISIS);
							sepN--;
							sep=yycharat(0);
							switch(sep){
								case '(': sepBlock='('; sep=')'; break;
								case '{': sepBlock='{'; sep='}'; break;
								case '[': sepBlock='['; sep=']'; break;
								case '\'': interpola=false; break;
								default: sepBlock=null;
							}
							return token(analisisTipo);}
}

<DELIMITADOR_DOBLE>{
	[^]					{
							if(yycharat(0) == sepBlock){
								yybegin(ANALISIS);
								yyval.setValor(""+sep+sepBlock); 
								return yyval.getTipo();}else{error();
							}
						}
}

<ANALISIS>{
	\\[^].?				{
							if(yylength()==3) yypushback(1);
							if(tokenAux==null){
								token(Parser.TEXTO);
								tokenAux=yyval;buffer.setLength(0);
							}
							buffer.append(yytext());
						}
	[^].?.?		{
							char c = yycharat(0);
							boolean c2 = false;
							if(yylength()>1){ 
								char cc=yycharat(1);
								if(cc=='{' && yylength()>2){
									cc=yycharat(2);
								}
								if(interpola && (c == '$' || c == '@')){
									if(PalabrasReservadas.isPerlSpecialVar(cc) && sep!=cc){
										token(0);
										gestorErrores.error(Errores.AVISO,Errores.VARIABLE_IGNORADA, yyval, yytext().substring(0, 2));
									}
								}
								yypushback(yylength()-1);	
								c2 = (cc >= 'A' && cc <= 'Z') || (cc >= 'a' && cc <= 'z') || cc=='_';								
							}	
							if((c == sep && stack==0) || (interpola && c2 && (c == '$' || c == '@'))){
								if(tokenAux!=null){
									yyval=tokenAux;
									tokenAux=null;
									yypushback(1);
									yyval.setValor(buffer.toString());
									return yyval.getTipo();								
								}else if(c == sep){
									sepN--;
									token(analisisTipo);
									if(sepN>0 && sepBlock!=null){
										yybegin(DELIMITADOR_DOBLE);
									}else {
										if(sepN==0){
											yybegin(estadoReturn);
										}
										return yyval.getTipo();
									}
									
								}else{
									if(flag==0){
										flag++;
										yypushback(1);return token(Parser.EXP_SEP);
									}else{
										flag=0;
									}
									isVar=false;
									yybegin(ANALISIS_VAR);return token(c);
								}
							}else{
								if(sepBlock!=null){
									if(c == sepBlock){
										stack++;
									}else if(c == sep && stack!=0){
										stack--;							
									}								
								}
								if(tokenAux==null){
									token(Parser.TEXTO);
									tokenAux=yyval;
									buffer.setLength(0);
								}
								buffer.append(yytext());						
							}
						}
}

<ANALISIS_VAR>{
	{ID}				{isVar=true;return token(Parser.VAR);}
	"::"				{return token(Parser.AMBITO);}
	"\#"				{return token('#');}
	[\{\[]				{yypushback(1);yybegin(ANALISIS_ACCESO);}
	[^]					{yypushback(1);yybegin(ANALISIS);return token(Parser.EXP_SEP);}
}

<ANALISIS_ACCESO>{
	[\{\[]				{yybegin(ANALISIS_ACCESO_INDEX);if(isVar)return token(yycharat(0));}
	[^]					{yypushback(1);yybegin(ANALISIS);return token(Parser.EXP_SEP);}
}

<ANALISIS_ACCESO_INDEX>{
	"$"					{return token('$');}
	"@"					{return token('@');}
	"::"				{return token(Parser.AMBITO);}
	"\#"				{return token('#');}
	{ID}				{return token(Parser.VAR);}
	{ENTERO}			{return token(Parser.ENTERO);}
	"'"					{yybegin(ANALISIS_ACCESO_INDEX_STR);return token(yycharat(0));}
	[\}\]]				{yybegin(ANALISIS_ACCESO);if(isVar)return token(yycharat(0));}
	[\s]				{}
	[^]					{yybegin(ANALISIS);yypushback(1);error();}
}

<ANALISIS_ACCESO_INDEX_STR>{
	([^']|\\')*'		{if(yylength()>1){yypushback(1);return token(Parser.TEXTO);}yybegin(ANALISIS_ACCESO_INDEX);return token(yycharat(0));}
}



	/*----Etiquetas----*/
<PD> {
	{PD_ID}				{
							String t = yytext(); 
							Integer tipo = PalabrasReservadas.buscarEtiqueta(t);
							if (tipo == null){
								buffer.append(t);
								token(t, 0);
								gestorErrores.error(Errores.AVISO,Errores.ETIQUETA_IGNORADA, yyval, t);
							}else{
								return token(t, tipo, true);
							}
						}
	{PD_NUM}			{return token(yytext(), Preprocesador.PD_NUM, true);}
	{PD_VAR}			{return token(yytext(), Preprocesador.PD_VAR, true);}
	[^\n\r]				{buffer.append(yytext());}
	"\r"				{}
	"\n"				{
							yybegin(YYINITIAL);
							tokenAux.setValor(buffer.toString());
							if(!tokenAux.getValor().trim().isEmpty()){
								yyval=tokenAux;
								return tokenAux.getTipo();
							}
						}
}

<YYINITIAL>{
		/*----Caracteres ignorados----*/
	[\s]				{}

		/*----Error----*/
	[^]					{error();}
}