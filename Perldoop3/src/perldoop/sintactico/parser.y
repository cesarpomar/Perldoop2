%{
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import perldoop.modelo.Opciones;
import perldoop.error.GestorErrores;
import perldoop.modelo.sintactico.ParserVal;
import perldoop.modelo.arbol.*;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.funciondef.*;
import perldoop.modelo.arbol.funcionsub.*;
import perldoop.modelo.arbol.cuerpo.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.*;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.arbol.numero.*;
import perldoop.modelo.arbol.cadena.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.arbol.varmulti.*;
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.lectura.*;
import perldoop.modelo.arbol.modulos.*;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
%}

/*Tokens etiquetas del preprocesador, nunca deben llegar al analizador*/
%token PD_COL PD_REF PD_TIPO PD_NUM PD_VAR

/*Tokens sintacticos*/
%token COMENTARIO DECLARACION_TIPO IMPORT_JAVA LINEA_JAVA
%token ID VAR
%token ENTERO DECIMAL M_REX S_REX Y_REX TEXTO REX_MOD SEP STDIN STDOUT STDERR

/*Palabras reservadas*/
%token MY SUB OUR PACKAGE WHILE DO FOR UNTIL USE
%token IF ELSIF ELSE UNLESS LAST NEXT RETURN
%token Q QQ QR QW QX 

/*Prioridades*/
%left LLOR LLXOR
%left LLAND
%left LLNOT
%left ','
%left ID
%right '=' MULTI_IGUAL DIV_IGUAL MOD_IGUAL SUMA_IGUAL MAS_IGUAL MENOS_IGUAL DESP_I_IGUAL DESP_D_IGUAL AND_IGUAL OR_IGUAL XOR_IGUAL POW_IGUAL LAND_IGUAL LOR_IGUAL DLOR_IGUAL CONCAT_IGUAL X_IGUAL
%right ':' '?'
%nonassoc DOS_PUNTOS
%left LOR DLOR
%left LAND
%left '|' '^'
%left '&'
%nonassoc NUM_EQ NUM_NE STR_EQ STR_NE CMP CMP_NUM SMART_EQ
%nonassoc '<' NUM_LE '>' NUM_GE STR_LT STR_LE STR_GT STR_GE
%left DESP_I DESP_D
%left '+' '-' '.'
%left '*' '/' '%' X
%left STR_REX STR_NO_REX
%left '!' '~' UNITARIO 
%right POW
%nonassoc MAS_MAS MENOS_MENOS
%left FLECHA
%right '(' ')' '[' ']' '{' '}'
%left AMBITO


%%
raiz		:	fuente									{$$=set(new Raiz(add(s($1))));}

fuente		:	masFuente cuerpo						{$$=set(Fuente.add(s($1), s($2)), false);}

masFuente	:											{$$=set(new Fuente(), false);}
			|	fuente funcionDef						{$$=set(Fuente.add(s($1), s($2)), false);}

funcionDef	:	funcionSub '{' cuerpo '}'				{$$=set(new FuncionDef(s($1), s($2), s($3), s($4)));}

funcionSub	:	SUB ID									{$$=set(new FuncionSub(s($1), s($2)));}

cuerpoR		:	sentencia								{$$=set(Cuerpo.add(new Cuerpo(add(new AbrirBloque())), s($1)), false);}
			|	cuerpoR	sentencia				        {$$=set(Cuerpo.add(s($1), s($2)), false);}
			
cuerpoNV	:	cuerpoR									{$$=set(s($1));}

cuerpo		:											{$$=set(new Cuerpo(add(new AbrirBloque())));}
			|	cuerpoNV								{$$=$1;}

sentencia   :	lista modificador ';'					{$$=set(new StcLista(s($1), s($2), s($3)));}
			|	';'										{$$=set(new StcLista(new Lista(), add(new ModNada()), s($1)));}
			|	bloque									{$$=set(new StcBloque(s($1)));}
			|	flujo									{$$=set(new StcFlujo(s($1)));}
			|	modulos									{$$=set(new StcModulos(s($1)));}
			|	IMPORT_JAVA								{$$=set(new StcImport(s($1)));}
			|	LINEA_JAVA								{$$=set(new StcLineaJava(s($1)));}
			|	COMENTARIO								{$$=set(new StcComentario(s($1)));}
			|	DECLARACION_TIPO						{$$=set(new StcTipado(s($1)));}
			|	error	';'								{$$=set(new StcError());}
			|	error	'}'								{$$=set(new StcError());}
			
modulos		:	USE paqueteID ID ';'					{$$=set(new ModuloUse(s($1),s($2),s($3),s($4)));}
			|	USE ID ';'								{$$=set(new ModuloUse(s($1),add(new Paquetes()),s($2),s($3)));}
			|	PACKAGE paqueteID ID ';'				{$$=set(new ModuloPackage(s($1),s($2),s($3),s($4)));}
			|	PACKAGE ID ';'							{$$=set(new ModuloPackage(s($1),add(new Paquetes()),s($2),s($3)));}

expresion	:	numero									{$$=set(new ExpNumero(s($1)));} 
			|	cadena									{$$=set(new ExpCadena(s($1)));} 
			|	variable								{$$=set(new ExpVariable(s($1)));} 
			|	varMulti								{$$=set(new ExpVarMulti(s($1)));}
			|	asignacion								{$$=set(new ExpAsignacion(s($1)));}  
			|	binario									{$$=set(new ExpBinario(s($1)));} 
			|	aritmetica								{$$=set(new ExpAritmetica(s($1)));} 
			|	logico									{$$=set(new ExpLogico(s($1)));} 
			|	comparacion								{$$=set(new ExpComparacion(s($1)));} 
			|	coleccion								{$$=set(new ExpColeccion(s($1)));} 
			|	acceso									{$$=set(new ExpAcceso(s($1)));} 
			|	funcion									{$$=set(new ExpFuncion(s($1)));} 
			|	'&' funcion %prec UNITARIO				{$$=set(new ExpFuncion5(s($1), s($2)));} 
			|	lectura									{$$=set(new ExpLectura(s($1)));} 
			|	regulares								{$$=set(new ExpRegulares(s($1)));}
			|	expresion DOS_PUNTOS expresion			{$$=set(new ExpRango(s($1),s($2),s($3)));}
			
lista		:	listaR									{$$=set(s($1));}

listaR		:	expresion ',' listaR					{$$=set(Lista.add(s($1), s($2), s($3)), false);}
			|	expresion ','							{$$=set(new Lista(s($1), s($2)), false);}
			|	expresion								{$$=set(new Lista(s($1)), false);}

modificador :											{$$=set(new ModNada());}
			|	IF expresion							{$$=set(new ModIf(s($1), s($2)));}
			|	UNLESS expresion						{$$=set(new ModUnless(s($1), s($2)));}
			|	WHILE expresion							{$$=set(new ModWhile(s($1), s($2)));}
			|	UNTIL expresion							{$$=set(new ModUntil(s($1), s($2)));}
			|	FOR expresion							{$$=set(new ModFor(s($1), s($2)));}

flujo		:	NEXT ';'								{$$=set(new Next(s($1), s($2)));}
			|	LAST ';'								{$$=set(new Last(s($1), s($2)));}
			|	RETURN ';'								{$$=set(new Return(s($1), s($2)));}
			|	RETURN expresion ';'					{$$=set(new Return(s($1), virtualCol(s($2)), s($3)));}

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
			|	expresion DLOR_IGUAL expresion			{$$=set(new DLOrIgual(s($1),s($2),s($3)));}
			|	expresion X_IGUAL expresion				{$$=set(new XIgual(s($1),s($2),s($3)));}
			|	expresion CONCAT_IGUAL expresion		{$$=set(new ConcatIgual(s($1),s($2),s($3)));}

numero		:	ENTERO									{$$=set(new Entero(s($1)));}
			|	DECIMAL									{$$=set(new Decimal(s($1)));}
			
cadena		:	'\'' TEXTO '\''							{$$=set(new CadenaSimple(s($1),s($2),s($3)));}
			|	'"' cadenaTexto '"'						{$$=set(new CadenaDoble(s($1),s($2),s($3)));}
			|	'`' cadenaTexto '`'						{$$=set(new CadenaComando(s($1),s($2),s($3)));}	
			|	Q SEP TEXTO SEP							{$$=set(new CadenaQ(s($1),s($2),s($3),s($4)));}	 
			|	QW SEP TEXTO SEP						{$$=set(new CadenaQW(s($1),s($2),s($3),s($4)));}	  
			|	QQ SEP cadenaTexto SEP					{$$=set(new CadenaQQ(s($1),s($2),s($3),s($4)));}	  
			|	QR SEP cadenaTexto SEP					{$$=set(new CadenaQR(s($1),s($2),s($3),s($4)));}	  
			|	QX SEP cadenaTexto SEP					{$$=set(new CadenaQX(s($1),s($2),s($3),s($4)));}	  

cadenaTexto :	cadenaTextoR							{$$=set(s($1));}	
			
cadenaTextoR:											{$$=set(new CadenaTexto(),false);}
			|	cadenaTextoR variable 					{$$=set(CadenaTexto.add(s($1),s($2)),false);}
			|	cadenaTextoR TEXTO						{$$=set(CadenaTexto.add(s($1),s($2)),false);}			

variable	:	'$' VAR									{$$=set(new VarExistente(s($1),s($2)));} 
			|	'@' VAR									{$$=set(new VarExistente(s($1),s($2)));} 
			|	'%' VAR									{$$=set(new VarExistente(s($1),s($2)));} 
			|	'$' paqueteVar VAR						{$$=set(new VarPaquete(s($1),s($2),s($3)));} 
			|	'@' paqueteVar VAR						{$$=set(new VarPaquete(s($1),s($2),s($3)));} 
			|	'%' paqueteVar VAR						{$$=set(new VarPaquete(s($1),s($2),s($3)));}
			|	'$' '#' VAR 							{$$=set(new VarSigil(s($1),s($2),s($3)));}
			|	'$' '#' paqueteVar VAR 					{$$=set(new VarPaqueteSigil(s($1),s($2),s($3),s($4)));}			
			|	MY '$' VAR								{$$=set(new VarMy(s($1),s($2),s($3)));} 
			|	MY '@' VAR								{$$=set(new VarMy(s($1),s($2),s($3)));} 
			|	MY '%' VAR								{$$=set(new VarMy(s($1),s($2),s($3)));} 
			|	OUR '$' VAR								{$$=set(new VarOur(s($1),s($2),s($3)));} 
			|	OUR '@' VAR								{$$=set(new VarOur(s($1),s($2),s($3)));} 
			|	OUR '%' VAR								{$$=set(new VarOur(s($1),s($2),s($3)));} 
			
varMulti	:	MY '(' lista ')'						{$$=set(new VarMultiMy(s($1),s($2),s($3),s($4)));}
			|	OUR '(' lista ')'						{$$=set(new VarMultiOur(s($1),s($2),s($3),s($4)));}

paqueteVar	:	paqueteVar VAR AMBITO					{$$=set(Paquetes.add(s($1),s($2),s($3)));} 
			|	VAR AMBITO								{$$=set(new Paquetes(s($1),s($2)));} 

paqueteID	:	paqueteID ID AMBITO						{$$=set(Paquetes.add(s($1),s($2),s($3)));} 
			|	ID AMBITO								{$$=set(new Paquetes(s($1),s($2)));} 

colParen	:	'(' lista ')'							{$$=set(new ColParentesis(s($1),s($2),s($3)));}
			|	'('  ')'								{$$=set(new ColParentesis(s($1),new Lista(),s($2)));}
			
colRef		:	'[' lista ']'							{$$=set(new ColCorchete(s($1),s($2),s($3)));}
			|	'[' ']'									{$$=set(new ColCorchete(s($1),add(new Lista()),s($2)));}
			|	'{' lista '}'							{$$=set(new ColLlave(s($1),s($2),s($3)));}
			|	'{' '}'									{$$=set(new ColLlave(s($1),add(new Lista()),s($2)));}
			
coleccion	:	colParen								{$$=$1;}
			|	colRef									{$$=$1;}
			
acceso		:	expresion colRef						{$$=set(new AccesoCol(s($1),s($2)));}
			|	expresion FLECHA colRef					{$$=set(new AccesoColRef(s($1),s($2),s($3)));}
			|	'$' expresion %prec UNITARIO			{$$=set(new AccesoRefEscalar(s($1),s($2)));} 
			|	'@' expresion %prec UNITARIO			{$$=set(new AccesoRefArray(s($1),s($2)));} 
			|	'%' expresion %prec UNITARIO			{$$=set(new AccesoRefMap(s($1),s($2)));} 			
			|	'\\' expresion %prec UNITARIO			{$$=set(new AccesoRef(s($1),s($2)));} 

funcion		:	paqueteID ID expresion					{$$=set(new FuncionPaqueteArgs(s($1),s($2),virtualCol(s($3))));}
			|	paqueteID ID							{$$=set(new FuncionPaqueteNoArgs(s($1),s($2)));}
			|	ID expresion							{$$=set(new FuncionArgs(s($1),virtualCol(s($2))));}
			|	ID 										{$$=set(new FuncionNoArgs(s($1)));}
			|	ID '{' lista '}' expresion				{$$=set(new FuncionEspecial(s($1),s($2),s($3),s($4),virtualCol(s($5))));}
			|	ID '{' STDOUT '}' expresion				{$$=set(new FuncionEspecial(s($1),s($2),add(new Lista(s($3))),s($4),virtualCol(s($5))));}
			|	ID '{' STDERR '}' expresion				{$$=set(new FuncionEspecial(s($1),s($2),add(new Lista(s($3))),s($4),virtualCol(s($5))));}
			
lectura		:	'<' STDIN '>'							{$$=set(new LecturaIn(s($1),s($2),s($3)));}
			|	'<' expresion '>'						{$$=set(new LecturaFile(s($1),s($2),s($3)));}

binario		:	expresion '|' expresion					{$$=set(new BinOr(s($1),s($2),s($3)));}
			|	expresion '&' expresion					{$$=set(new BinAnd(s($1),s($2),s($3)));}
			|	'~' expresion							{$$=set(new BinNot(s($1),s($2)));}
			|	expresion '^' expresion					{$$=set(new BinXor(s($1),s($2),s($3)));}
			|	expresion DESP_I expresion				{$$=set(new BinDespI(s($1),s($2),s($3)));}
			|	expresion DESP_D expresion				{$$=set(new BinDespD(s($1),s($2),s($3)));}

logico		:	expresion LOR expresion					{$$=set(new LogOr(s($1),s($2),s($3)));}
			|	expresion DLOR expresion				{$$=set(new DLogOr(s($1),s($2),s($3)));}
			|	expresion LAND expresion				{$$=set(new LogAnd(s($1),s($2),s($3)));}
			|	'!' expresion							{$$=set(new LogNot(s($1),s($2)));}
			|	expresion LLOR expresion				{$$=set(new LogOrBajo(s($1),s($2),s($3)));}
			|	expresion LLAND expresion				{$$=set(new LogAndBajo(s($1),s($2),s($3)));}
			|	LLNOT expresion							{$$=set(new LogNotBajo(s($1),s($2)));}
			|	expresion LLXOR expresion				{$$=set(new LogXorBajo(s($1),s($2),s($3)));}
			|	expresion '?' expresion ':' expresion	{$$=set(new LogTernario(s($1),s($2),s($3),s($4),s($5)));}

comparacion	:	expresion NUM_EQ expresion				{$$=set(new CompNumEq(s($1),s($2),s($3)));}
			|	expresion NUM_NE expresion				{$$=set(new CompNumNe(s($1),s($2),s($3)));}
			|	expresion '<' expresion					{$$=set(new CompNumLt(s($1),s($2),s($3)));}
			|	expresion NUM_LE expresion				{$$=set(new CompNumLe(s($1),s($2),s($3)));}
			|	expresion '>' expresion					{$$=set(new CompNumGt(s($1),s($2),s($3)));}
			|	expresion NUM_GE expresion				{$$=set(new CompNumGe(s($1),s($2),s($3)));}
			|	expresion CMP_NUM expresion				{$$=set(new CompNumCmp(s($1),s($2),s($3)));}
			|	expresion STR_EQ expresion				{$$=set(new CompStrEq(s($1),s($2),s($3)));}
			|	expresion STR_NE expresion				{$$=set(new CompStrNe(s($1),s($2),s($3)));}
			|	expresion STR_LT expresion				{$$=set(new CompStrLt(s($1),s($2),s($3)));}
			|	expresion STR_LE expresion				{$$=set(new CompStrLe(s($1),s($2),s($3)));}
			|	expresion STR_GT expresion				{$$=set(new CompStrGt(s($1),s($2),s($3)));}
			|	expresion STR_GE expresion				{$$=set(new CompStrGe(s($1),s($2),s($3)));}
			|	expresion CMP expresion					{$$=set(new CompStrCmp(s($1),s($2),s($3)));}
			|	expresion SMART_EQ expresion			{$$=set(new CompSmart(s($1),s($2),s($3)));}

aritmetica	:	expresion '+' expresion					{$$=set(new AritSuma(s($1),s($2),s($3)));}
			|	expresion '-' expresion					{$$=set(new AritResta(s($1),s($2),s($3)));}
			|	expresion '*' expresion					{$$=set(new AritMulti(s($1),s($2),s($3)));}
			|	expresion '/' expresion					{$$=set(new AritDiv(s($1),s($2),s($3)));}
			|	expresion POW expresion					{$$=set(new AritPow(s($1),s($2),s($3)));}
			|	expresion X expresion					{$$=set(new AritX(s($1),s($2),s($3)));}
			|	expresion '.' expresion					{$$=set(new AritConcat(s($1),s($2),s($3)));}
			|	expresion '%' expresion					{$$=set(new AritMod(s($1),s($2),s($3)));}
			|	'+' expresion %prec UNITARIO			{$$=set(new AritPositivo(s($1),s($2)));}
			|	'-' expresion %prec UNITARIO			{$$=set(new AritNegativo(s($1),s($2)));}
			|	MAS_MAS expresion						{$$=set(new AritPreIncremento(s($1),s($2)));}
			|	MENOS_MENOS expresion					{$$=set(new AritPreDecremento(s($1),s($2)));}
			|	expresion MAS_MAS						{$$=set(new AritPostIncremento(s($1),s($2)));}
			|	expresion MENOS_MENOS					{$$=set(new AritPostDecremento(s($1),s($2)));}
			
regulares	:	expresion STR_REX M_REX SEP cadenaTexto SEP rexMod								{$$=set(new RegularMatch(s($1),s($2),s($3),s($4),s($5),s($6),s($7)));}
			|	expresion STR_REX SEP cadenaTexto SEP rexMod									{$$=set(new RegularMatch(s($1),s($2),null ,s($3),s($4),s($5),s($6)));}
			|	expresion STR_NO_REX M_REX SEP cadenaTexto SEP rexMod							{$$=set(new RegularNoMatch(s($1),s($2),s($3),s($4),s($5),s($6),s($7)));}
			|	expresion STR_NO_REX SEP cadenaTexto SEP rexMod									{$$=set(new RegularNoMatch(s($1),s($2),null ,s($3),s($4),s($5),s($6)));}
			|	expresion STR_REX S_REX SEP cadenaTexto SEP cadenaTexto SEP rexMod				{$$=set(new RegularSubs(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9)));}
			|	expresion STR_REX Y_REX SEP cadenaTexto SEP cadenaTexto SEP rexMod				{$$=set(new RegularTrans(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9)));}
			
rexMod		:																					{$$=set(null,false);}
			|	REX_MOD																			{$$=$1;}

abrirBloque :																					{$$=set(new AbrirBloque());}
			
listaFor	:																					{$$=set(new Lista());}
			|	lista																			{$$=$1;}
			
bloque		:	'{' cuerpoNV '}'																{$$=set(new BloqueVacio(s($1),s($2),s($3)));}
			|	WHILE abrirBloque '(' expresion ')' '{' cuerpo '}'								{$$=set(new BloqueWhile(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8)));}
			|	UNTIL abrirBloque '(' expresion ')' '{' cuerpo '}'								{$$=set(new BloqueUntil(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8)));}
			|	DO abrirBloque '{' cuerpo '}' WHILE '(' expresion ')' ';'						{$$=set(new BloqueDoWhile(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9),s($10)));}
			|	DO abrirBloque '{' cuerpo '}' UNTIL '(' expresion ')' ';'						{$$=set(new BloqueDoUntil(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9),s($10)));}
			|	FOR abrirBloque '(' listaFor ';' listaFor ';' listaFor ')' '{' cuerpo '}'		{$$=set(new BloqueFor(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9),s($10),s($11),s($12)));}
			|	FOR abrirBloque variable colParen '{' cuerpo '}'								{$$=set(new BloqueForeachVar(s($1),s($2),s($3),s($4),s($5),s($6),s($7)));}
			|	FOR abrirBloque colParen '{' cuerpo '}'											{$$=set(new BloqueForeach(s($1),s($2),s($3),s($4),s($5),s($6)));}
			|	IF abrirBloque '(' expresion ')' '{' cuerpo '}'	condicional						{$$=set(new BloqueIf(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9)));}
			|	UNLESS abrirBloque '(' expresion ')' '{' cuerpo '}'	condicional					{$$=set(new BloqueUnless(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8),s($9)));}
			
condicional	:																					{$$=set(new CondicionalNada());}
			|	ELSIF '(' expresion ')' '{' cuerpo '}' condicional								{$$=set(new CondicionalElsif(s($1),s($2),s($3),s($4),s($5),s($6),s($7),s($8)));}
			|	ELSE '{' cuerpo '}'																{$$=set(new CondicionalElse(s($1),s($2),s($3),s($4)));}			
			


%%

	private List<Simbolo> simbolos;
	private List<Terminal> terminales;
	private Iterator<Terminal> iterator;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	private int errores;
	
	/**
	 * Constructor del analizador sintactico
	 * @param terminales Terminales
	 * @param opciones Opciones
	 * @param gestorErrores Gestor de errores
	 */
	public Parser(List<Terminal> terminales, Opciones opciones,GestorErrores gestorErrores) {
		this.terminales = terminales;
		simbolos = new ArrayList<>(terminales.size()*10);
		iterator = terminales.iterator();
		this.opciones = opciones;
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
	 * y luego lo retorna encapsulado en un ParseVal del analizador.
	 * @param s Simbolo
	 * @return ParseVal
	 */
	private ParserVal set(Simbolo s){
		simbolos.add(s);
		return new ParserVal(s);
	}
	
	/**
	 * Función interna auxiliar que añade el simbolo a la lista de analizador
	 * y luego lo retorna encapsulado en un ParseVal del analizador.
	 * @param s Simbolo
	 * @param add Añadir a la lista
	 * @return ParseVal
	 */
	private ParserVal set(Simbolo s, boolean add){
		if(add){
			simbolos.add(s);
		}
		return new ParserVal(s);
	}	
	
	/**
	 * Función interna auxiliar que añade el simbolo a la lista de analizador
	 * y luego lo retorna.
	 * @param s Simbolo
	 * @return Simbolo s
	 */
	private <T extends Simbolo>  T add(T s){
		simbolos.add(s);
		return s;
	}
	
	/**
	 * Función interna auxiliar que simula que las expresiones estan dentro de parentesis
	 * aunque no sea asi en el codigo fuente.
	 * @param s Simbolo
	 * @return Simbolo s
	 */
	private ExpColeccion virtualCol(Expresion s){
		if(!(s instanceof ExpColeccion && ((ExpColeccion)s).getColeccion() instanceof ColParentesis)){
            Lista l = new Lista((Expresion)s);
            Coleccion col = new ColParentesis(l);
			simbolos.add(col);
			ExpColeccion expcol= new ExpColeccion(col);
			simbolos.add(expcol);		
			return expcol;
		}
		return (ExpColeccion)s;
	}

	/**
	 * Función invocada por el analizador cada vez que necesita un terminal.
	 * @return Tipo del terminal
	 */
	private int yylex (){
		if(iterator.hasNext()){
			Terminal t = iterator.next();
			yylval = set(t);
			return t.getToken().getTipo();
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