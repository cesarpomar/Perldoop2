%{
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import perldoop.error.GestorErrores;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.sintactico.ParserVal;
import perldoop.modelo.arbol.*;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.masfuente.*;
import perldoop.modelo.arbol.funciondef.*;
import perldoop.modelo.arbol.funcionsub.*;
import perldoop.modelo.arbol.cuerpo.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.*;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.arbol.coleccion.*;
%}

/*Tokens etiquetas del preprocesador, nunca deben llegar al analizador*/
%token PD_TIPO PD_NUM PD_VAR PD_ARGS PD_RETURNS

/*Tokens sintacticos*/
%token COMENTARIO DECLARACION_TIPO IMPORT_JAVA LINEA_JAVA
%token ID VAR
%token ENTERO DECIMAL CADENA_SIMPLE CADENA_DOBLE CADENA_COMANDO M_REGEX S_REGEX Y_REGEX STDIN STDOUT STDERR

/*Palabras reservadas*/
%token MY SUB OUR PACKAGE WHILE DO FOR UNTIL
%token IF ELSIF ELSE UNLESS LAST NEXT RETURN

/*Prioridades*/
%left LLOR LLXOR
%left LLAND
%left LLNOT
%left ','
%left FUNCION
%right '=' MULTI_IGUAL DIV_IGUAL MOD_IGUAL SUMA_IGUAL MAS_IGUAL MENOS_IGUAL DESP_I_IGUAL DESP_D_IGUAL AND_IGUAL OR_IGUAL XOR_IGUAL POW_IGUAL LAND_IGUAL LOR_IGUAL CONCAT_IGUAL X_IGUAL
%right ':' '?'
%nonassoc DOS_PUNTOS
%left LOR SUELO
%left LAND
%left '|' '^'
%left '&'
%nonassoc NUM_EQ NUM_NE STR_EQ STR_NE CMP CMP_NUM SMART_EQ
%nonassoc '<' NUM_LE '>' NUM_GE STR_LT STR_LE STR_GT STR_GE
%left DESP_I DESP_D
%left '+' '-' '.'
%left '*' '/' '%' X
%left STR_REX STR_NO_REX
%left '!' '~' UNITARIO ID
%right POW
%nonassoc MAS_MAS MENOS_MENOS
%left FLECHA
%right '(' ')' '[' ']' '{' '}'
%left CONTEXTO


%%
raiz		:	fuente									{$$=set(new Raiz(s($1)));}

fuente		:	cuerpo masFuente						{$$=set(new Fuente(s($1), s($2)));}

masFuente	:											{$$=set(new MfNada());}
			|	funcionDef fuente						{$$=set(new MfFuente(s($1), s($2)));}

funcionDef	:	funcionSub '{' cuerpo '}'				{$$=set(new FuncionDef(s($1), s($2), s($3), s($4)));}

funcionSub	:	SUB ID									{$$=set(new FuncionSub(s($1), s($2)));}

cuerpo		:											{$$=set(new Cuerpo());}
			|	cuerpo sentencia						{$$=set(Cuerpo.add(s($1), s($2)));}

sentencia   :	lista modificador ';'					{$$=set(new StcLista(s($1), s($2), s($3)));}
			|	bloque									{$$=set(new StcBloque(s($1)));}
			|	flujo									{$$=set(new StcFlujo(s($1)));}
			|	PACKAGE ID ';'							{$$=set(new StcPaquete(s($1), s($2), s($3)));}
			|	COMENTARIO								{$$=set(new StcComentario(s($1)));}
			|	DECLARACION_TIPO						{$$=set(new StcDeclaracion(s($1)));}
			|	error	';'								{$$=set(new StcError());}

expresion	:	constante								{$$=set(new ExpConstante(s($1)));} 
			|	variable								{$$=set(new ExpVariable(s($1)));} 
			|	asignacion								{$$=set(new ExpAsignacion(s($1)));}  
			|	binario									{$$=set(new ExpBinario(s($1)));} 
			|	aritmetica								{$$=set(new ExpAritmetica(s($1)));} 
			|	logico									{$$=set(new ExpLogico(s($1)));} 
			|	comparacion								{$$=set(new ExpComparacion(s($1)));} 
			|	coleccion								{$$=set(new ExpColeccion(s($1)));} 
			|	acceso									{$$=set(new ExpAcceso(s($1)));} 
			|	funcion									{$$=set(new ExpFuncion(s($1)));} 
			|	regulares								{$$=set(new ExpRegulares(s($1)));} 

lista		:	expresion ',' lista						{$$=set(Lista.add(s($1), s($2), s($3)));}
			|	expresion ','							{$$=set(new Lista(s($1), s($2)));}
			|	expresion								{$$=set(new Lista(s($1)));}

modificador :											{$$=set(new ModNada());}
			|	IF expresion							{$$=set(new ModIf(s($1), s($2)));}
			|	UNLESS expresion						{$$=set(new ModUnless(s($1), s($2)));}
			|	WHILE expresion							{$$=set(new ModWhile(s($1), s($2)));}
			|	UNTIL expresion							{$$=set(new ModUntil(s($1), s($2)));}
			|	FOR expresion							{$$=set(new ModFor(s($1), s($2)));}

flujo		:	NEXT ';'								{$$=set(new Next(s($1), s($2)));}
			|	LAST ';'								{$$=set(new Last(s($1), s($2)));}
			|	RETURN ';'								{$$=set(new Return(s($1),new Lista(), s($2)));}
			|	RETURN lista ';'						{$$=set(new Return(s($1), s($2), s($3)));}

asignacion	:   expresion '=' expresion					{$$=set(new Igual(s($1),s($2),s($3)));}
			|	expresion MAS_IGUAL expresion			{$$=set(new MasIgual(s($1),s($2),s($3)));}
			|	expresion MENOS_IGUAL expresion			{$$=set(new MenosIgual(s($1),s($2),s($3)));}
			|	expresion MULTI_IGUAL expresion			{$$=set(new MultiIgual(s($1),s($2),s($3)));}
			|	expresion DIV_IGUAL expresion			{$$=set(new DivIgual(s($1),s($2),s($3)));}
			|	expresion MOD_IGUAL expresion			{$$=set(new ModIgual(s($1),s($2),s($3)));}
			|	expresion POW_IGUAL expresion			{$$=set(new PowIgual(s($1),s($2),s($3)));}
			|	expresion AND_IGUAL expresion			{$$=set(new AndIgual(s($1),s($2),s($3)));}
			|	expresion OR_IGUAL expresion			{$$=set(new OrIgual(s($1),s($2),s($3)));}
			|	expresion XOR_IGUAL expresion			{$$=set(new XorIgual(s($1),s($2),s($3)));}
			|	expresion DESP_D_IGUAL expresion		{$$=set(new DespDIgual(s($1),s($2),s($3)));}
			|	expresion DESP_I_IGUAL expresion		{$$=set(new DespIIgual(s($1),s($2),s($3)));}
			|	expresion LAND_IGUAL expresion			{$$=set(new LAndIgual(s($1),s($2),s($3)));}
			|	expresion LOR_IGUAL expresion			{$$=set(new LOrIgual(s($1),s($2),s($3)));}
			|	expresion X_IGUAL expresion				{$$=set(new XIgual(s($1),s($2),s($3)));}
			|	expresion CONCAT_IGUAL expresion		{$$=set(new ConcatIgual(s($1),s($2),s($3)));}

constante	:	ENTERO									{$$=set(new Entero(s($1)));}
			|	DECIMAL									{$$=set(new Decimal(s($1)));}
			|	CADENA_SIMPLE							{$$=set(new CadenaSimple(s($1)));}
			|	CADENA_DOBLE							{$$=set(new CadenaDoble(s($1)));}
			|	CADENA_COMANDO 							{$$=set(new CadenaComando(s($1)));}  

variable	:	VAR										{$$=set(new VarExistente(s($1)));} 
			|	paquete VAR								{$$=set(new VarPaquete(s($1),s($2)));} 
			|	MY VAR									{$$=set(new VarMy(s($1),s($2)));} 
			|	OUR VAR									{$$=set(new VarOur(s($1),s($2)));} 

coleccion	:	'(' lista ')'							{$$=set(new ColParentesis(s($1),s($2),s($3)));}
			|	'('  ')'								{$$=set(new ColParentesis(s($1),new Lista(),s($2)));}
			|	'[' lista ']'							{$$=set(new ColCorchete(s($1),s($2),s($3)));}
			|	'[' ']'									{$$=set(new ColCorchete(s($1),new Lista(),s($2)));}
			|	'{' lista '}'							{$$=set(new ColLlave(s($1),s($2),s($3)));}
			|	'{' '}'									{$$=set(new ColLlave(s($1),new Lista(),s($2)));}
			|	expresion DOS_PUNTOS expresion			{$$=set(new ColGenerador(s($1),s($2),s($3)));}
			|	MY '(' lista ')'						{$$=set(new ColMy(s($1),s($2),s($3),s($4)));}
			|	OUR '(' lista ')'						{$$=set(new ColOur(s($1),s($2),s($3),s($4)));}

acceso		:	expresion '{' lista '}'					{}
			|	expresion '[' lista ']'					{}
			|	expresion FLECHA '{' lista '}'			{}
			|	expresion FLECHA '[' lista ']'			{}
			|	'$' expresion %prec UNITARIO			{}
			|	'@' expresion %prec UNITARIO			{}
			|	'%' expresion %prec UNITARIO			{}

funcion		:	paquete ID expresion					{}
			|	paquete ID								{}
			|	ID expresion							{}
			|	ID										{}

paquete		:	ID CONTEXTO								{}

regulares	:	expresion STR_NO_REX M_REGEX			{}
			|	expresion STR_REX M_REGEX				{}
			|	expresion STR_REX S_REGEX				{}
			|	expresion STR_REX Y_REGEX				{}

binario		:	expresion '|' expresion					{}
			|	expresion '&' expresion					{}
			|	expresion '~' expresion					{}
			|	expresion '^' expresion					{}
			|	expresion DESP_I expresion				{}
			|	expresion DESP_D expresion				{}

logico		:	expresion LOR expresion					{}
			|	expresion LAND expresion				{}
			|	'!' expresion							{}
			|	expresion LLOR expresion				{}
			|	expresion LLAND expresion				{}
			|	LLNOT expresion							{}
			|	expresion LLXOR expresion				{}
			|	expresion '?' expresion ':' expresion	{}

comparacion	:	expresion NUM_EQ expresion				{}
			|	expresion NUM_NE expresion				{}
			|	expresion '<' expresion					{}
			|	expresion NUM_LE expresion				{}
			|	expresion '>' expresion					{}
			|	expresion NUM_GE expresion				{}
			|	expresion CMP_NUM expresion				{}
			|	expresion STR_EQ expresion				{}
			|	expresion STR_NE expresion				{}
			|	expresion STR_LT expresion				{}
			|	expresion STR_LE expresion				{}
			|	expresion STR_GT expresion				{}
			|	expresion STR_GE expresion				{}
			|	expresion CMP expresion					{}
			|	expresion SMART_EQ expresion			{}

aritmetica	:	expresion '+' expresion					{}
			|	expresion '-' expresion					{}
			|	expresion '*' expresion					{}
			|	expresion '/' expresion					{}
			|	expresion POW expresion					{}
			|	expresion X expresion					{}
			|	expresion '.' expresion					{}
			|	expresion '%' expresion					{}
			|	'+' expresion %prec UNITARIO			{}
			|	'-' expresion %prec UNITARIO			{}
			|	MAS_MAS expresion						{}
			|	MENOS_MENOS expresion					{}
			|	expresion MAS_MAS						{}
			|	expresion MENOS_MENOS					{}

abrirBloque :											{}

bloque		:	bloqueIf elsif																	{}
			|	WHILE abrirBloque '(' expresion ')' '{' cuerpo '}'								{}
			|	UNTIL abrirBloque '(' expresion ')' '{' cuerpo '}'								{}
			|	DO abrirBloque '{' cuerpo '}' WHILE '(' expresion ')' ';'						{}
			|	DO abrirBloque '{' cuerpo '}' UNTIL '(' expresion ')' ';'						{}
			|	FOR abrirBloque '(' expresion ';' expresion ';' expresion ')' '{' cuerpo '}'	{}
			|	FOR abrirBloque expresion '(' expresion ')' '{' cuerpo '}'						{}
			|	FOR abrirBloque '(' lista ')' '{' cuerpo '}'									{}

bloqueIf	:	IF abrirBloque '(' expresion ')' '{' cuerpo '}'									{}
			|	UNLESS abrirBloque '(' expresion ')' '{' cuerpo '}'								{}
	
elsif		:
			|	bloqueElsif elsif																{}
			|	ELSE abrirBloque '{' cuerpo '}'													{}

bloqueElsif :	ELSIF abrirBloque '(' expresion ')' '{' cuerpo '}'								{}


%%

	private List<Simbolo> simbolos;
	private List<Token> tokens;
	private Iterator<Token> iterator;
	private GestorErrores gestorErrores;
	private int errores;
	
	/**
	 * Constructor del analizador sintactico
	 * @param tokens Tokens lexicos
	 * @param gestorErrores Gestor de errores
	 */
	public Parser(List<Token> tokens, GestorErrores gestorErrores) {
		this.tokens = tokens;
		simbolos = new ArrayList<>(tokens.size()*10);
		iterator = tokens.iterator();
		this.gestorErrores = gestorErrores;
		errores = 0;
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
	 * Obtiene el número de errores sintacticos, si no hay errores el analisis
	 * se ha realizado correctamente.
	 * @return Número de errores
	 */
	public int getErrores(){
		return errores;
	}
	
	/**
	 * Activa el depurador nativo del analizador
	 * @param debugMe Estado
	 */
	public void debug(boolean debugMe){
		yydebug = debugMe;
	}

	/**
	 * Inicia el analisis y la creación del arbol de simbolos. Una vez terminado
	 * se obtiene una lista ordenada de los símbolos segun fueron analizados y
	 * unidos entre si en forma de arbol.
	 * @return Lista de Símbolos
	 */
	public List<Simbolo> parsear(){
		yyparse();
		return simbolos;
	}

	/**
	 * Función interna auxiliar que extraer el símbolo del ParseVal y luego lo 
	 * retorna casteado al tipo que requiere su padre.
	 * @param <T> Tipo requerido por el constructor del padre
	 * @param pv ParserVal del analizador
	 * @return Simbolo castadeado al subtipo requerido
	 */
	private <T> T s(ParserVal pv){
		return (T)pv.get();
	}

	/**
	 * Función interna auxiliar que añade el simbolo a la lista de analizador
	 * y luego lo retorno encapsulado en un ParseVal del analizador.
	 * @param s Simbolo
	 * @return ParseVal
	 */
	private ParserVal set(Simbolo s){
		simbolos.add(s);
		return new ParserVal(s);
	}

	/**
	 * Función invocada por el analizador cada vez que necesita un token.
	 * @return Tipo del token
	 */
	private int yylex (){
		if(iterator.hasNext()){
			Token token = iterator.next();
			yylval = new ParserVal(new Terminal(token));
			return token.getTipo();
		}
		return 0;
	}

	/**
	 * Función invocada cuando el analizador encuentra un error sintactico.
	 * @param descripcion String con el mensaje "Syntax error"
	 */
	private void yyerror (String descripcion){
		errores++;
		List<Integer> tokens = new ArrayList<>(YYMAXTOKEN);
		int yychar, yyn;
		//Reducir
		for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ ){   
			yyn = yyrindex[yystate];  
			if ((yyn !=0 ) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == yychar){  
				tokens.add(yychar);
			}
		}
		//Desplazar
		for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ ){  
			yyn = yysindex[yystate];  
			if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == yychar){  
				tokens.add(yychar);
			}
		}
		ParserError.errorSintactico(this, tokens);
	}