//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package perldoop.sintactico;



//#line 2 "parser.y"
import java.util.List;
import java.util.ArrayList;
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
import perldoop.modelo.arbol.escritura.*;
import perldoop.modelo.arbol.lectura.*;
import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.arbol.modulos.*;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
//#line 55 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:ParserVal
String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[] = new ParserVal[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
final void val_push(ParserVal val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    ParserVal[] newstack = new ParserVal[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final ParserVal val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final ParserVal val_peek(int relative)
{
  return valstk[valptr-relative];
}
final ParserVal dup_yyval(ParserVal val)
{
  return val;
}
//#### end semantic value section ####
public final static short PD_COL=257;
public final static short PD_REF=258;
public final static short PD_TIPO=259;
public final static short PD_NUM=260;
public final static short PD_VAR=261;
public final static short COMENTARIO=262;
public final static short DECLARACION_TIPO=263;
public final static short IMPORT_JAVA=264;
public final static short LINEA_JAVA=265;
public final static short VAR=266;
public final static short FILE=267;
public final static short ENTERO=268;
public final static short DECIMAL=269;
public final static short M_REX=270;
public final static short S_REX=271;
public final static short Y_REX=272;
public final static short TEXTO=273;
public final static short REX_MOD=274;
public final static short SEP=275;
public final static short STDIN=276;
public final static short STDOUT=277;
public final static short STDERR=278;
public final static short STDOUT_H=279;
public final static short STDERR_H=280;
public final static short MY=281;
public final static short SUB=282;
public final static short OUR=283;
public final static short PACKAGE=284;
public final static short WHILE=285;
public final static short DO=286;
public final static short FOR=287;
public final static short UNTIL=288;
public final static short USE=289;
public final static short IF=290;
public final static short ELSIF=291;
public final static short ELSE=292;
public final static short UNLESS=293;
public final static short LAST=294;
public final static short NEXT=295;
public final static short RETURN=296;
public final static short Q=297;
public final static short QQ=298;
public final static short QR=299;
public final static short QW=300;
public final static short QX=301;
public final static short LLOR=302;
public final static short LLXOR=303;
public final static short LLAND=304;
public final static short LLNOT=305;
public final static short ID=306;
public final static short MULTI_IGUAL=307;
public final static short DIV_IGUAL=308;
public final static short MOD_IGUAL=309;
public final static short SUMA_IGUAL=310;
public final static short MAS_IGUAL=311;
public final static short MENOS_IGUAL=312;
public final static short DESP_I_IGUAL=313;
public final static short DESP_D_IGUAL=314;
public final static short AND_IGUAL=315;
public final static short OR_IGUAL=316;
public final static short XOR_IGUAL=317;
public final static short POW_IGUAL=318;
public final static short LAND_IGUAL=319;
public final static short LOR_IGUAL=320;
public final static short DLOR_IGUAL=321;
public final static short CONCAT_IGUAL=322;
public final static short X_IGUAL=323;
public final static short DOS_PUNTOS=324;
public final static short LOR=325;
public final static short DLOR=326;
public final static short LAND=327;
public final static short NUM_EQ=328;
public final static short NUM_NE=329;
public final static short STR_EQ=330;
public final static short STR_NE=331;
public final static short CMP=332;
public final static short CMP_NUM=333;
public final static short SMART_EQ=334;
public final static short NUM_LE=335;
public final static short NUM_GE=336;
public final static short STR_LT=337;
public final static short STR_LE=338;
public final static short STR_GT=339;
public final static short STR_GE=340;
public final static short DESP_I=341;
public final static short DESP_D=342;
public final static short X=343;
public final static short STR_REX=344;
public final static short STR_NO_REX=345;
public final static short UNITARIO=346;
public final static short POW=347;
public final static short MAS_MAS=348;
public final static short MENOS_MENOS=349;
public final static short FLECHA=350;
public final static short ID_P=351;
public final static short AMBITO=352;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    4,    5,    6,    6,    8,    3,
    3,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,   13,   13,   13,   13,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,    9,   31,   31,   31,   10,   10,
   10,   10,   10,   10,   12,   12,   12,   12,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   20,   16,   16,   17,   17,   17,
   17,   17,   17,   17,   17,   32,   33,   33,   33,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   19,   19,   34,   34,   14,   14,   35,
   35,   36,   36,   36,   36,   25,   25,   26,   26,   26,
   26,   26,   26,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   37,   37,   37,   29,   29,   28,   28,   21,
   21,   21,   21,   21,   21,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   30,   30,   30,   30,   30,   30,   38,
   38,   39,   40,   40,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   41,   41,   41,
};
final static short yylen[] = {                            2,
    1,    2,    0,    2,    4,    2,    1,    2,    1,    0,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    1,
    2,    2,    2,    4,    3,    4,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    2,
    1,    1,    1,    3,    1,    3,    2,    1,    0,    2,
    2,    2,    2,    2,    2,    2,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    1,    3,    3,    3,
    4,    4,    4,    4,    4,    1,    0,    2,    2,    2,
    2,    2,    3,    3,    3,    3,    4,    3,    3,    3,
    3,    3,    3,    4,    4,    3,    2,    3,    2,    3,
    2,    3,    2,    3,    2,    1,    1,    2,    3,    2,
    2,    2,    2,    2,    2,    1,    3,    3,    2,    3,
    5,    5,    1,    1,    2,    1,    1,    3,    3,    3,
    3,    2,    3,    3,    3,    3,    3,    3,    2,    3,
    3,    2,    3,    5,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    2,    2,    2,
    2,    2,    2,    7,    6,    7,    6,    9,    9,    0,
    1,    0,    0,    1,    3,    8,    8,   10,   10,   12,
    7,    6,    9,    9,    0,    8,    4,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    4,    0,    0,   19,   20,   17,
   18,   76,   77,  136,  137,    0,    0,    0,  192,  192,
  192,  192,    0,  192,  192,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   13,    0,
   87,   87,    0,    0,    0,    2,    0,    7,   11,    0,
   14,   15,   16,    0,    0,   28,   29,   30,   31,   32,
   33,   34,   35,   36,   37,   38,   39,   41,   42,   43,
   45,  116,  117,    6,    0,   23,   22,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   56,   55,    0,   57,
    0,    0,   87,   87,    0,   87,    0,    0,  133,  134,
    0,  109,    0,    0,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  111,    0,  113,    0,
  115,    0,    0,    0,  125,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  182,  183,    0,  118,    0,  100,    0,   98,
   99,  103,    0,  101,  102,   27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,  135,
    0,    0,  138,    0,  107,    0,  110,  112,  195,  114,
    0,   78,   79,   89,   88,   80,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   12,  108,    0,  128,    0,
    0,    0,   46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,   87,    0,  119,
    5,  104,  105,   26,    0,    0,    0,    0,    0,    0,
    0,   24,    0,    0,   81,   83,   84,   82,   85,    0,
  106,    0,    0,    0,   87,   87,   87,    0,   87,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  131,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  194,    0,    0,  202,    0,    0,    0,    0,   87,   87,
  191,  185,    0,  187,    0,    0,    0,    0,  201,    0,
    0,    0,  184,    0,    0,  186,  196,    0,    0,    0,
  197,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  203,  204,  188,  189,  198,  199,    0,    0,    0,    0,
    0,    0,  200,    0,  207,    0,    0,    0,  206,
};
final static short yydgoto[] = {                          1,
    2,    3,   56,    5,    6,   57,   58,   59,   60,  164,
   61,   62,   63,   64,   65,   66,   67,   68,   69,   70,
   71,   72,   73,   74,   75,   76,   77,   78,   79,   80,
   81,  147,  148,  132,   82,   83,  124,  412,   99,  358,
  441,
};
final static short yysindex[] = {                         0,
    0, -239,  786, -249,    0,  -46,  -51,    0,    0,    0,
    0,    0,    0,    0,    0,   -6,   16, -227,    0,    0,
    0,    0, -223,    0,    0,   32,   34, 1106, -180, -178,
 -172, -166, -161, 1613,  430, -287, 1146, 1613, 1613, 1215,
 1613, 1613, 1613, 1613, 1254, 1505,  711,   75,    0, -153,
    0,    0,  895, 1466, 1613,    0,  786,    0,    0, -177,
    0,    0,    0, -286, 4065,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  786,    0,    0,    0, -142, 1613,
 -141, -139, -137, 1613, -136, -128,  -38, -164,  153,   72,
  161,  165,  -36,  -98,  171,  177,    0,    0, 1574,    0,
 4115,  -54,    0,    0,  -52,    0, 5587,  -44,    0,    0,
 1574,    0, 5587, 1613,    0,  164, 5173,  112,  112, -125,
  112,  -34,  112,  112,  -86,  -86,    0,  187,    0,  136,
    0,  105,  -81,  826,    0,  195,  203,   48,  142, -125,
  -26,  112,  -25, -125,  112,  -22,  112,    0, 1613, 1613,
 1613, 1613, 1613,  180,  491,  205, 1613, 1613, 1613, 1613,
 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613,
 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613,
 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613,
 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613,
 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613, 1613,  -57,
 -220, 1613,    0,    0,  -62,    0,  121,    0,  206,    0,
    0,    0,  208,    0,    0,    0,  -35, 1613,  786,    4,
    9,  -16, 1254,  -18,  -15,  205,  134, 1613,    0,  -27,
 1613, 1613,  133,    0,   61,   62,   64,   66,   73,    0,
  135, 5587,    0, 1801,    0,   -2,    0,    0,    0,    0,
 1613,    0,    0,    0,    0,    0, -125,   88,   -2,   -2,
 5487, 5487, 5487, 5487, 5487,    0,    0, 5587,    0, 5537,
 5537, 5587,    0, 5587, 5587, 5587, 5587, 5587, 5587, 5587,
 5587, 5587, 5587, 5587, 5587, 5587, 5587, 5587, 5587, 5587,
 4165, 1964, 5652, 5652, 5966, 5992, 5992, 2538,  558,  558,
  558,  558,  558,  558,  558,  987,  987,  987,  987,  987,
  987,  987,  987,  461,  461,  291,  291,  291,  108,  108,
  108,  108,   81,   82,   83,    0,   84,    0,  112,    0,
    0,    0,    0,    0, 4220,  239,  187,  306,  244,  786,
 4534,    0, 4589, 4644,    0,    0,    0,    0,    0, 1895,
    0, 4699,   -2, 1613,    0,    0,    0,   96,    0,   98,
  249, -246, 1613,  786,  251,  254,  255,  260,  -62,    0,
 5625,   99,  109,  116,  113,  117,  113,  786,  353,  354,
    0,  336,  271,    0,  786,  786,  786,  113,    0,    0,
    0,    0,  113,    0,  272, 1613, 1613, 1613,    0,  273,
  276,  277,    0,  128,  129,    0,    0, 5013, 5068,  364,
    0, -232, -232,  113,  113,  347,  348,  285,  369,  290,
    0,    0,    0,    0,    0,    0,  786, 1613,  786,  292,
 5123,  293,    0,  298,    0,  786,  300, -232,    0,
};
final static short yyrindex[] = {                         0,
    0,  431,   33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, 6501,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   67,    0,    0,  373,
    0,    0,    0,    0, 1074,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  309,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  286,    0,    0,    0,
    0,    0,  322,    0,    0,    0,    0, 6637, 6946, 1936,
 7014,    0, 7082, 7391, 6126, 6194,    0,    0,    0,    0,
    0,    0,  373,    0,    0,    0,    0,  -33,    0, 2074,
    0, 7459,    0, 2391, 7527,    0, 7836,    0,    0,    0,
    0,    0,    0,    0, 6569,    0,    0,    0,    0, 1171,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  309,    0,
    0,    0,  377,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  660,    0, 6058,    0, 2483,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, 2800,    0, 2892, 3209,
  378,  379,  380,  381,  382,    0,    0, 1175,    0,10308,
11570, 1652,    0, 1735, 2031, 5973, 6562,10943,11399,11440,
11444,11464,11468,11488,11495,11515,11519,11539,11543,11563,
    0,11116, 9858,11074, 3378,10988,11031,10147,10190,10235,
10525,10568,10613,10656,10701, 9240, 9291, 9349, 9646, 9697,
 9748, 9799,10096, 8877, 9182, 8703, 8761, 8819, 7972, 8279,
 8338, 8397,    0,    0,    0,    0,    0,    0, 7904,    0,
    0,    0,    0,    0,    0,    0,  384,    0,    0,  309,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 3683,
    0,    0, 3301,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  377,  309,    0,    0,    0,    0, 3751,    0,
11157,    0,    0,    0, 3615,    0, 3615,  309,    0,    0,
    0,    0,    0,    0,  309,  309,  309, 3615,    0,    0,
    0,    0, 3615,    0,    0,    0,    0,  404,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  390,  390, 3615, 3615,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  309,    0,  309,    0,
    0,    0,    0,    0,    0,  309,    0,  390,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -37,    0,    0,    0,  389,  401,  -43,    0,
    0,    0,    0,   63,  -28,    0,    0,  -79,    0,    0,
    0,    0,    0,    0,    0,    0,  415,    0,    0,    0,
  295,  -24,    0,  265,  -30,  -29,  327, -359,   97, -348,
 -371,
};
final static int YYTABLESIZE=11873;
static short yytable[][] = new short[2][];
static { yytable0(); yytable1();}
static void yytable0(){
yytable[0] = new short[] {
   111,    86,   138,   140,   143,    46,   117,   123,    88,
   127,   128,   129,   131,   133,   134,   135,   136,   151,
   145,    35,   165,   236,   246,   249,   354,   152,   155,
   157,   149,    46,    91,    89,   362,    10,    90,   402,
   226,   109,   414,   399,    91,    89,   400,     4,   270,
    95,    93,   229,   227,   423,   347,   233,    95,    93,
   426,   348,    94,    84,    92,   439,   440,   109,   442,
    86,    48,   166,   253,     9,    92,   275,   430,   247,
    86,    96,    87,   443,   444,    85,   261,    97,    96,
    98,   226,   103,   244,   242,   104,   459,   226,   256,
   257,   107,   259,   108,   226,   112,   262,   113,   226,
   226,   226,   138,   226,   114,   226,   226,   226,   226,
   159,   115,   160,   161,   245,   162,   116,   144,   163,
   100,   101,   102,   146,   105,   106,   226,   228,   230,
   226,   231,   226,   232,   234,   281,   282,   283,   284,
   285,   289,   288,   235,   290,   291,   292,   237,   294,
   295,   296,   297,   298,   299,   300,   301,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,     9,   238,   349,   239,   350,   244,
   242,    46,   357,   243,   356,    46,   159,   248,   160,
   161,   250,   162,   355,   251,   163,   343,   344,   345,
   359,   252,   346,   255,   361,   258,   260,   363,   364,
   245,   263,   265,   267,   268,   269,   109,   266,   226,
   272,   109,   328,   273,   276,   286,   277,   279,    86,
   372,   280,    45,   351,   352,   150,   353,   130,   154,
   226,   226,   226,   226,   226,   360,   270,   226,   370,
   226,   226,   226,   225,   226,   226,   226,   226,   226,
   226,   226,   226,   226,   226,   226,   226,   226,   226,
   226,   226,   226,   226,   226,   226,   226,   226,   226,
   226,   226,   226,   226,   226,   226,   226,   226,   226,
   226,   226,   226,   226,   226,   226,   226,   226,   226,
   226,   226,   226,   226,   226,   226,   226,   226,   122,
    10,   122,   287,   153,   156,   226,   274,   378,   385,
   380,   287,   226,   152,   218,   240,   152,   241,   226,
   216,   226,   226,   365,   366,   217,   367,   401,   368,
   389,   226,   152,   152,   391,   403,   369,     9,   371,
   392,   393,   394,   373,   396,   375,   376,   377,   379,
   226,   415,   226,   124,   382,   383,   124,   384,   420,
   421,   422,   395,   398,   397,   408,   401,   404,   405,
   406,   152,   124,   124,    46,   407,   409,   424,   425,
   411,   428,   429,   205,   410,   413,   416,   417,   418,
   419,   427,   431,   226,   226,   432,   433,   434,   435,
   438,   445,   446,   447,   448,   450,   152,   452,   449,
   109,   124,   278,   453,   455,   457,   451,   456,   226,
   205,   205,   458,   205,   205,   205,   205,   205,     1,
    49,   205,    10,   205,   193,    52,    54,    53,    50,
    51,   240,   194,   241,   193,   158,   124,   142,   205,
   205,   125,   220,   221,   205,   222,   223,   224,   225,
   222,   223,   224,   225,    41,    51,   293,    53,    40,
    36,    50,    45,   271,     0,    38,     0,    39,     0,
     0,     0,     0,     0,   205,   205,     0,     0,     0,
   205,     0,     0,     0,    37,     0,     0,     0,    54,
     0,     0,     0,   218,     0,     0,     0,     0,   216,
   213,     0,   214,   215,   217,   153,   156,     0,     0,
   205,     0,   205,   205,     0,     0,     0,     0,    46,
    55,     0,    41,    51,    52,    53,    40,    36,    50,
    45,     0,     0,    38,     0,    39,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    37,    46,   121,     0,    54,    42,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   152,     0,   152,   152,     0,
   152,     0,     0,   152,     0,     0,    46,    55,   109,
     0,     0,    52,   152,   152,   152,     0,     0,     0,
     0,   218,     0,     0,     0,     0,   216,   213,     0,
   214,   215,   217,     0,   124,     0,   124,   124,     0,
   124,     0,   109,   124,     0,    42,   203,     0,   205,
     0,     0,     0,   124,   124,   124,     0,     0,     0,
     0,     0,     0,     0,   219,   220,   221,     0,   222,
   223,   224,   225,     0,     0,     0,     0,   205,     0,
     0,    46,     0,     0,   205,   205,   205,   205,     0,
     0,   205,   205,     0,     0,     0,     0,     0,     0,
     0,   205,   205,     0,     0,   205,   205,   205,   205,
   205,   205,   205,   205,   205,   205,   109,     0,   205,
   205,   205,   205,   205,   205,   205,   205,   205,     0,
     0,     0,   205,   205,   118,    12,    13,     0,   130,
     0,     0,   130,     0,     0,    14,    15,   119,   120,
    16,     0,    17,     0,     0,     0,     0,   130,   130,
     0,     0,     0,     0,     0,     0,     0,    29,    30,
    31,    32,    33,     0,     0,     0,    34,    35,     0,
   205,   205,     0,   205,     0,     0,    41,    51,     0,
    53,    40,    36,    50,    45,     0,   130,    38,     0,
    39,     0,     0,    12,    13,     0,     0,     0,     0,
     0,     0,     0,    14,    15,    49,    37,    16,     0,
    17,    54,     0,     0,    43,    44,     0,    48,   122,
     0,     0,   130,     0,     0,    29,    30,    31,    32,
    33,     0,     0,     0,    34,    35,     0,     0,     0,
     0,    46,    55,   219,   220,   221,    52,   222,   223,
   224,   225,     0,     0,     0,     0,     0,     0,     0,
    41,    51,     0,    53,    40,    36,    50,    45,     0,
     0,    38,     0,    39,     0,     0,    47,     0,   141,
    42,     0,    43,    44,     0,    48,   287,     0,    49,
    37,     0,     0,     0,    54,     0,     0,     0,     0,
     0,     0,     0,     0,    41,    51,     0,    53,    40,
    36,    50,    45,   137,     0,    38,     0,    39,     0,
     0,     0,     0,     0,    46,    55,     0,     0,     0,
    52,     0,     0,     0,    37,     0,     0,     0,    54,
     0,     0,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
    47,     0,     0,    42,     0,     0,     0,     0,    46,
    55,     0,     0,     0,    52,     0,     0,     0,     0,
     0,    41,    51,   151,    53,    40,    36,    50,    45,
     0,     0,    38,     0,    39,     0,     0,     0,     0,
   130,     0,   130,   130,   109,   130,     0,    42,   130,
     0,    37,     0,     0,     0,    54,     0,     0,   130,
   130,   130,     0,     0,     7,     0,     0,     0,     0,
     0,     8,     9,    10,    11,     0,     0,    12,    13,
     0,     0,     0,     0,     0,    46,    55,    14,    15,
     0,    52,    16,     0,    17,    18,    19,    20,    21,
    22,    23,    24,     0,     0,    25,    26,    27,    28,
    29,    30,    31,    32,    33,     0,     0,     0,    34,
    35,   109,     0,     0,    42,     0,     0,   218,     0,
     0,     0,     0,   216,   213,     0,   214,   215,   217,
     0,     0,     0,     0,     0,     0,     0,     7,     0,
     0,     0,     0,     0,     8,     9,    10,    11,     0,
     0,    12,    13,     0,     0,     0,    43,    44,     0,
    48,    14,    15,     0,     0,    16,     0,    17,    18,
    19,    20,    21,    22,    23,    24,     0,    46,    25,
    26,    27,    28,    29,    30,    31,    32,    33,     0,
     0,     0,    34,    35,   118,    12,    13,     0,     0,
     0,     0,     0,     0,     0,    14,    15,   119,   120,
    16,     0,    17,   109,     0,     0,     0,     0,    48,
     0,     0,     0,     0,     0,     0,     0,    29,    30,
    31,    32,    33,     0,     0,     0,    34,    35,    48,
    43,    44,     0,    48,     0,    41,    51,     0,    53,
    40,    36,    50,    45,     0,     0,    38,     0,    39,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   150,     0,    12,    13,   110,    37,    48,     0,     0,
    54,     0,    14,    15,    43,    44,    16,    48,    17,
    41,    51,     0,    53,    40,    36,    50,    45,     0,
     0,    38,     0,    39,    29,    30,    31,    32,    33,
    46,    55,    48,    34,    35,    52,     0,     0,     0,
    37,     0,     0,     0,    54,     0,    47,     0,     0,
     0,   127,     0,     0,   127,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   109,    47,     0,    42,
   127,   127,     0,     0,    46,    55,     0,     0,     0,
    52,    43,    44,     0,    48,     0,    41,    51,     0,
    53,    40,    36,    50,    45,     0,     0,    38,     0,
    39,     0,     0,     0,    47,     0,     0,     0,   127,
   109,     0,     0,    42,     0,     0,    37,     0,     0,
     0,    54,     0,     0,     0,     0,     0,     0,     0,
    41,    51,     0,    53,    40,    36,    50,    45,   137,
    47,    38,     0,    39,   127,     0,     0,     0,     0,
     0,    46,    55,     0,     0,     0,    52,     0,     0,
    37,     0,     0,     0,    54,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   211,   212,   219,   220,
   221,     0,   222,   223,   224,   225,   109,     0,     0,
    42,     0,     0,     0,    46,    55,     0,     0,     0,
    52,     0,     0,     0,     0,     0,     0,     0,     0,
    48,     0,    48,    48,     0,    48,     0,     0,    48,
     0,     0,     0,     0,     0,     0,    12,    13,     0,
   109,     0,     0,    42,     0,     0,    14,    15,     0,
     0,    16,     0,    17,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    29,
    30,    31,    32,    33,     0,     0,     0,    34,    35,
     0,    12,    13,     0,     0,     0,     0,     0,     0,
   126,    14,    15,     0,     0,    16,     0,    17,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    29,    30,    31,    32,    33,     0,
     0,     0,    34,    35,     0,    43,    44,    47,    48,
    47,    47,   127,    47,   127,   127,    47,   127,     0,
     0,   127,     0,     0,     0,     0,     0,     0,     0,
     0,   127,   127,   127,     0,   130,     0,    12,    13,
     0,     0,     0,     0,     0,     0,     0,    14,    15,
    43,    44,    16,    48,    17,    41,    51,     0,    53,
    40,    36,    50,    45,     0,     0,    38,     0,    39,
    29,    30,    31,    32,    33,     0,     0,     0,    34,
    35,    12,    13,     0,     0,    37,     0,     0,     0,
    54,    14,    15,     0,     0,    16,     0,    17,    41,
    51,     0,    53,    40,    36,    50,    45,     0,     0,
    38,     0,    39,    29,    30,    31,    32,    33,     0,
    46,    55,    34,    35,     0,    52,    43,    44,    37,
    48,     0,     0,    54,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   109,     0,     0,    42,
     0,     0,     0,    46,    55,   139,     0,     0,    52,
    43,    44,     0,    48,     0,    41,    51,     0,    53,
    40,    36,    50,    45,     0,     0,    38,     0,    39,
     0,     0,     0,     0,     0,     0,     0,     0,   109,
     0,     0,    42,     0,     0,    37,     0,     0,     0,
    54,     0,     0,     0,     0,     0,     0,     0,    41,
    51,     0,    53,    40,    36,    50,    45,     0,     0,
    38,     0,    39,     0,     0,     0,     0,     0,     0,
    46,    55,     0,     0,     0,    52,     0,     0,    37,
     0,     0,     0,    54,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   151,     0,     0,   151,   109,     0,   141,    42,
     0,     0,     0,    46,    55,     0,     0,     0,    52,
   151,   151,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   154,     0,    12,    13,   109,
     0,     0,    42,     0,     0,     0,    14,    15,   151,
     0,    16,     0,    17,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    29,
    30,    31,    32,    33,     0,     0,     0,    34,    35,
    12,    13,     0,    59,   151,     0,    59,     0,     0,
    14,    15,     0,     0,    16,     0,    17,     0,     0,
     0,     0,    59,    59,     0,     0,     0,     0,     0,
     0,     0,    29,    30,    31,    32,    33,     0,     0,
     0,    34,    35,     0,     0,    43,    44,     0,    48,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    59,     0,     0,     0,     0,     0,    41,    51,
     0,    53,    40,     0,    50,    45,    12,    13,    38,
     0,    39,     0,     0,     0,     0,    14,    15,    43,
    44,    16,    48,    17,     0,     0,    59,     0,     0,
     0,     0,    54,     0,     0,     0,     0,     0,    29,
    30,    31,    32,    33,     0,     0,     0,    34,    35,
    12,    13,     0,     0,     0,     0,     0,     0,     0,
    14,    15,    46,    55,    16,     0,    17,    52,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    29,    30,    31,    32,    33,     0,     0,
     0,    34,    35,     0,     0,    43,    44,   109,    48,
     0,    42,    41,    51,     0,    53,     0,     0,    50,
    45,     0,   151,     0,   151,   151,     0,   151,     0,
     0,   151,     0,     0,     0,     0,     0,     0,     0,
     0,   151,   151,   151,     0,     0,    54,     0,    43,
    44,     0,    48,     0,     0,     0,     0,     0,    92,
     0,    92,    92,    92,     0,    92,    92,    92,    92,
    92,    92,    92,    92,     0,     0,    46,    55,     0,
     0,     0,    52,     0,     0,    92,    92,    92,    92,
    92,    92,    92,   218,   195,     0,     0,     0,   216,
   213,     0,   214,   215,   217,     0,     0,     0,     0,
     0,     0,   109,     0,    59,    42,    59,    59,   203,
    59,   205,    92,    59,    92,    92,     0,    92,     0,
     0,     0,     0,    59,    59,    59,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    46,     0,     0,   194,    92,    92,
    92,     0,     0,     0,     0,     0,     0,     0,    12,
    13,     0,    62,     0,     0,    62,     0,     0,    14,
    15,     0,     0,    16,     0,    17,     0,     0,   109,
   193,    62,    62,     0,     0,     0,     0,     0,     0,
     0,    29,    30,    31,    32,    33,     0,     0,     0,
    34,    35,    90,     0,    90,    90,    90,     0,    90,
    90,    90,    90,    90,    90,    90,    90,     0,     0,
    62,     0,     0,     0,     0,     0,     0,     0,    90,
    90,    90,    90,    90,    90,    90,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    43,    44,
     0,    48,     0,     0,     0,    62,     0,     0,     0,
     0,     0,     0,    12,    13,    90,     0,    90,    90,
     0,    90,     0,    14,    15,     0,     0,    16,     0,
    17,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    29,    30,    31,    32,
    33,    90,    90,    90,    34,    35,     0,     0,     0,
     0,     0,     0,     0,    92,     0,    92,     0,     0,
     0,     0,     0,    92,     0,    92,     0,    92,     0,
    92,    92,     0,    92,     0,     0,    92,     0,     0,
     0,     0,     0,     0,     0,     0,    92,    92,    92,
     0,     0,    92,    92,    92,    48,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,     0,    92,    92,    92,
    92,     0,     0,   190,   191,   192,   196,   197,   198,
   199,   200,   201,   202,   204,   206,   207,   208,   209,
   210,   211,   212,   219,   220,   221,     0,   222,   223,
   224,   225,     0,    62,     0,    62,    62,     0,    62,
     0,     0,    62,     0,     0,     0,     0,     0,     0,
     0,     0,    62,    62,    62,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    90,     0,
    90,     0,     0,     0,     0,     0,    90,     0,    90,
     0,    90,     0,    90,    90,     0,    90,     0,     0,
    90,     0,     0,     0,     0,     0,     0,     0,     0,
    90,    90,    90,     0,     0,    90,    90,    90,     0,
    90,    90,    90,    90,    90,    90,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    90,    90,     0,
    90,    90,    90,    90,    91,     0,    91,    91,    91,
     0,    91,    91,    91,    91,    91,    91,    91,    91,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    91,    91,    91,    91,    91,    91,    91,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    91,     0,
    91,    91,     0,    91,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    91,    91,    91,    95,     0,    95,
    95,    95,     0,    95,    95,    95,    95,    95,    95,
    95,    95,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    95,    95,    95,    95,    95,    95,
    95,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    95,   218,    95,    95,     0,    95,   216,   213,     0,
   214,   215,   217,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   203,     0,   205,
     0,     0,     0,     0,     0,    95,    95,    95,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    46,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   109,     0,     0,
    91,     0,    91,     0,     0,     0,     0,     0,    91,
     0,    91,     0,    91,     0,    91,    91,     0,    91,
     0,     0,    91,     0,     0,     0,     0,     0,     0,
     0,     0,    91,    91,    91,     0,     0,    91,    91,
    91,     0,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,     0,    91,    91,    91,    91,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    95,     0,    95,     0,     0,     0,     0,
     0,    95,     0,    95,     0,    95,     0,    95,    95,
     0,    95,     0,     0,    95,     0,     0,     0,     0,
     0,     0,     0,     0,    95,    95,    95,     0,     0,
    95,    95,    95,     0,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,     0,    95,    95,    95,    95,    96,
     0,    96,    96,    96,     0,    96,    96,    96,    96,
    96,    96,    96,    96,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    96,    96,    96,    96,
    96,    96,    96,     0,   196,   197,   198,   199,   200,
   201,   202,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
     0,     0,    96,     0,    96,    96,     0,    96,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    96,    96,
    96,    93,     0,    93,    93,    93,     0,    93,    93,
    93,    93,    93,    93,    93,    93,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    93,    93,
    93,    93,    93,    93,    93,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    93,     0,    93,    93,     0,
    93,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    93,    93,    93,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    96,     0,    96,     0,     0,
     0,     0,     0,    96,     0,    96,     0,    96,     0,
    96,    96,     0,    96,     0,     0,    96,     0,     0,
     0,     0,     0,     0,     0,     0,    96,    96,    96,
     0,     0,    96,    96,    96,     0,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,     0,    96,    96,    96,
    96,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    93,     0,    93,
     0,     0,     0,     0,     0,    93,     0,    93,     0,
    93,     0,    93,    93,     0,    93,     0,     0,    93,
     0,     0,     0,     0,     0,     0,     0,     0,    93,
    93,    93,     0,     0,    93,    93,    93,     0,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,     0,    93,
    93,    93,    93,    94,     0,    94,    94,    94,     0,
    94,    94,    94,    94,    94,    94,    94,    94,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    94,    94,    94,    94,    94,    94,    94,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    94,     0,    94,
    94,     0,    94,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    94,    94,    94,    97,     0,    97,    97,
    97,     0,    97,    97,    97,    97,    97,    97,    97,
    97,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    97,    97,    97,    97,    97,    97,    97,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    97,
     0,    97,    97,     0,    97,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   148,
     0,     0,   148,     0,    97,    97,    97,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   148,   148,
     0,   148,     0,   148,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   148,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    94,
     0,    94,     0,     0,     0,     0,     0,    94,     0,
    94,     0,    94,     0,    94,    94,     0,    94,     0,
     0,    94,   148,     0,     0,     0,     0,     0,     0,
     0,    94,    94,    94,     0,     0,    94,    94,    94,
     0,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
     0,    94,    94,    94,    94,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    97,     0,    97,     0,     0,     0,     0,     0,
    97,     0,    97,     0,    97,     0,    97,    97,     0,
    97,     0,     0,    97,     0,     0,     0,     0,     0,
     0,     0,     0,    97,    97,    97,     0,     0,    97,
    97,    97,     0,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,     0,    97,    97,    97,    97,   190,   190,
     0,     0,   190,   190,   190,   190,   190,   190,   190,
   148,     0,   148,   148,     0,   148,     0,     0,   148,
     0,   190,   190,   190,   190,   190,   190,     0,   148,
   148,   148,     0,     0,   148,   148,   148,     0,   148,
   148,   148,   148,   148,   148,   148,   148,   148,   148,
   148,   148,   148,   148,   148,   148,   148,   190,     0,
   190,   190,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   114,   114,     0,     0,   114,   114,
   114,   114,   114,   114,   114,     0,     0,     0,     0,
     0,     0,     0,   190,   190,   190,   114,   114,   114,
   114,   114,   114,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   114,   114,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   132,
   132,     0,     0,   132,   132,   132,   132,   132,   132,
   132,     0,     0,     0,     0,     0,     0,     0,     0,
   114,   114,   132,   132,   132,   132,   132,   132,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   132,   132,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   132,   132,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   190,     0,   190,   190,     0,   190,
     0,     0,   190,     0,     0,     0,     0,     0,     0,
     0,     0,   190,   190,   190,     0,     0,   190,   190,
   190,     0,   190,   190,   190,   190,   190,   190,   190,
   190,   190,   190,   190,   190,   190,   190,   190,   190,
   190,   190,   190,   190,   190,   190,   190,   190,   190,
   190,   190,   190,   190,   190,   190,   190,   190,   190,
   190,     0,   190,   190,   190,   190,     0,     0,   114,
     0,   114,   114,     0,   114,     0,     0,   114,     0,
     0,     0,     0,     0,     0,     0,     0,   114,   114,
   114,     0,     0,   114,   114,   114,     0,   114,   114,
   114,   114,   114,   114,   114,   114,   114,   114,   114,
   114,   114,   114,   114,   114,   114,   114,   114,   114,
   114,   114,   114,   114,   114,   114,   114,   114,   114,
   114,   114,   114,   114,   114,   114,     0,   114,   114,
   114,   114,     0,     0,   132,     0,   132,   132,     0,
   132,     0,     0,   132,     0,     0,     0,     0,     0,
     0,     0,     0,   132,   132,   132,     0,     0,   132,
   132,   132,     0,   132,   132,   132,   132,   132,   132,
   132,   132,   132,   132,   132,   132,   132,   132,   132,
   132,   132,   132,   132,   132,   132,   132,   132,   132,
   132,   132,   132,   132,   132,   132,   132,   132,   132,
   132,   132,     0,   132,   132,   132,   132,   218,   195,
     0,     0,     0,   216,   213,   170,   214,   215,   217,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   203,   171,   205,   188,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   218,   195,     0,     0,    46,   216,
   213,   194,   214,   215,   217,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   254,   203,
   171,   205,   188,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   109,   193,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   218,
   195,     0,     0,    46,   216,   213,   194,   214,   215,
   217,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   374,     0,   203,   171,   205,   188,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   109,
   193,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    46,
   218,   195,   194,     0,   381,   216,   213,     0,   214,
   215,   217,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   203,   171,   205,   188,
     0,     0,     0,     0,   109,   193,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    46,     0,     0,   194,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   109,   193,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   167,   168,   169,     0,     0,   172,   173,
   174,     0,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   189,   190,   191,
   192,   196,   197,   198,   199,   200,   201,   202,   204,
   206,   207,   208,   209,   210,   211,   212,   219,   220,
   221,     0,   222,   223,   224,   225,     0,   167,   168,
   169,     0,     0,   172,   173,   174,     0,   175,   176,
   177,   178,   179,   180,   181,   182,   183,   184,   185,
   186,   187,   189,   190,   191,   192,   196,   197,   198,
   199,   200,   201,   202,   204,   206,   207,   208,   209,
   210,   211,   212,   219,   220,   221,     0,   222,   223,
   224,   225,     0,   167,   168,   169,     0,     0,   172,
   173,   174,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   189,   190,
   191,   192,   196,   197,   198,   199,   200,   201,   202,
   204,   206,   207,   208,   209,   210,   211,   212,   219,
   220,   221,     0,   222,   223,   224,   225,     0,     0,
     0,     0,     0,     0,   167,   168,   169,     0,     0,
   172,   173,   174,     0,   175,   176,   177,   178,   179,
   180,   181,   182,   183,   184,   185,   186,   187,   189,
   190,   191,   192,   196,   197,   198,   199,   200,   201,
   202,   204,   206,   207,   208,   209,   210,   211,   212,
   219,   220,   221,     0,   222,   223,   224,   225,   218,
   195,     0,     0,   386,   216,   213,     0,   214,   215,
   217,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   203,   171,   205,   188,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    46,
   218,   195,   194,     0,   387,   216,   213,     0,   214,
   215,   217,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   203,   171,   205,   188,
     0,     0,     0,     0,   109,   193,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    46,   218,   195,   194,     0,   388,   216,   213,     0,
   214,   215,   217,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   203,   171,   205,
   188,     0,     0,     0,     0,   109,   193,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    46,   218,   195,   194,     0,   390,   216,   213,
     0,   214,   215,   217,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   203,   171,
   205,   188,     0,     0,     0,     0,   109,   193,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    46,     0,     0,   194,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   109,   193,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   167,   168,   169,     0,     0,   172,
   173,   174,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   189,   190,
   191,   192,   196,   197,   198,   199,   200,   201,   202,
   204,   206,   207,   208,   209,   210,   211,   212,   219,
   220,   221,     0,   222,   223,   224,   225,     0,     0,
     0,     0,     0,     0,   167,   168,   169,     0,     0,
   172,   173,   174,     0,   175,   176,   177,   178,   179,
   180,   181,   182,   183,   184,   185,   186,   187,   189,
   190,   191,   192,   196,   197,   198,   199,   200,   201,
   202,   204,   206,   207,   208,   209,   210,   211,   212,
   219,   220,   221,     0,   222,   223,   224,   225,     0,
     0,     0,     0,     0,     0,   167,   168,   169,     0,
     0,   172,   173,   174,     0,   175,   176,   177,   178,
   179,   180,   181,   182,   183,   184,   185,   186,   187,
   189,   190,   191,   192,   196,   197,   198,   199,   200,
   201,   202,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
     0,     0,     0,     0,     0,     0,   167,   168,   169,
     0,     0,   172,   173,   174,     0,   175,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   189,   190,   191,   192,   196,   197,   198,   199,
   200,   201,   202,   204,   206,   207,   208,   209,   210,
   211,   212,   219,   220,   221,     0,   222,   223,   224,
   225,   218,   195,     0,     0,   436,   216,   213,     0,
   214,   215,   217,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   203,   171,   205,
   188,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    46,   218,   195,   194,     0,   437,   216,   213,
     0,   214,   215,   217,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   203,   171,
   205,   188,     0,     0,     0,     0,   109,   193,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    46,   218,   195,   194,     0,   454,   216,
   213,     0,   214,   215,   217,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   203,
   171,   205,   188,     0,     0,     0,     0,   109,   193,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   218,
   195,     0,     0,    46,   216,   213,   194,   214,   215,
   217,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   203,   171,   264,   188,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   109,
   193,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    46,
     0,     0,   194,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   109,   193,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   167,   168,   169,     0,
     0,   172,   173,   174,     0,   175,   176,   177,   178,
   179,   180,   181,   182,   183,   184,   185,   186,   187,
   189,   190,   191,   192,   196,   197,   198,   199,   200,
   201,   202,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
     0,     0,     0,     0,     0,     0,   167,   168,   169,
     0,     0,   172,   173,   174,     0,   175,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   189,   190,   191,   192,   196,   197,   198,   199,
   200,   201,   202,   204,   206,   207,   208,   209,   210,
   211,   212,   219,   220,   221,     0,   222,   223,   224,
   225,     0,     0,     0,     0,     0,     0,   167,   168,
   169,     0,     0,   172,   173,   174,     0,   175,   176,
   177,   178,   179,   180,   181,   182,   183,   184,   185,
   186,   187,   189,   190,   191,   192,   196,   197,   198,
   199,   200,   201,   202,   204,   206,   207,   208,   209,
   210,   211,   212,   219,   220,   221,     0,   222,   223,
   224,   225,     0,   167,   168,   169,     0,     0,   172,
   173,   174,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   189,   190,
   191,   192,   196,   197,   198,   199,   200,   201,   202,
   204,   206,   207,   208,   209,   210,   211,   212,   219,
   220,   221,     0,   222,   223,   224,   225,   218,   195,
     0,     0,     0,   216,   213,     0,   214,   215,   217,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   203,   171,   205,   188,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   218,   195,     0,     0,    46,   216,
   213,   194,   214,   215,   217,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   203,
   171,   205,   188,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   109,   193,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   218,
   195,     0,     0,    46,   216,   213,   194,   214,   215,
   217,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   203,   171,   205,   188,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   109,
   193,   218,   195,     0,     0,     0,   216,   213,     0,
   214,   215,   217,     0,     0,     0,     0,     0,    46,
     0,     0,   194,     0,     0,     0,   203,     0,   205,
   188,   218,   195,     0,     0,     0,   216,   213,     0,
   214,   215,   217,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   109,   193,   203,     0,   205,
     0,    46,     0,     0,   194,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    46,     0,     0,   194,     0,   109,   193,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   109,   193,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   167,   168,   169,     0,     0,   172,   173,
   174,     0,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   189,   190,   191,
   192,   196,   197,   198,   199,   200,   201,   202,   204,
   206,   207,   208,   209,   210,   211,   212,   219,   220,
   221,     0,   222,   223,   224,   225,     0,     0,     0,
   169,     0,     0,   172,   173,   174,     0,   175,   176,
   177,   178,   179,   180,   181,   182,   183,   184,   185,
   186,   187,   189,   190,   191,   192,   196,   197,   198,
   199,   200,   201,   202,   204,   206,   207,   208,   209,
   210,   211,   212,   219,   220,   221,     0,   222,   223,
   224,   225,     0,     0,     0,     0,     0,     0,   172,
   173,   174,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   189,   190,
   191,   192,   196,   197,   198,   199,   200,   201,   202,
   204,   206,   207,   208,   209,   210,   211,   212,   219,
   220,   221,     0,   222,   223,   224,   225,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   189,   190,   191,   192,   196,   197,   198,   199,   200,
   201,   202,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
     0,     0,     0,   192,   196,   197,   198,   199,   200,
   201,   202,   204,   206,   207,   208,   209,   210,   211,
   212,   219,   220,   221,     0,   222,   223,   224,   225,
   218,   195,     0,     0,     0,   216,   213,     0,   214,
   215,   217,    63,     0,     0,    63,     0,     0,     0,
     0,     0,     0,     0,     0,   203,     0,   205,   218,
   195,    63,    63,     0,   216,   213,     0,   214,   215,
   217,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   203,     0,   205,     0,     0,
    46,     0,     0,   194,     0,     0,     0,     0,     0,
    63,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    46,
     0,     0,     0,     0,     0,   109,   193,     0,     0,
     0,     0,     0,   139,     0,    63,   139,   139,     0,
   139,     0,   139,   139,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   109,   139,   139,     0,   139,
   139,   139,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   139,   139,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   180,   180,
     0,     0,   180,   180,   180,   180,   180,   180,   180,
     0,     0,     0,     0,     0,     0,     0,     0,   139,
   139,   180,   180,   180,   180,   180,   180,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   180,   180,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   181,   181,     0,     0,   181,   181,
   181,   181,   181,   181,   181,     0,     0,     0,     0,
     0,     0,     0,     0,   180,   180,   181,   181,   181,
   181,   181,   181,    63,     0,    63,    63,     0,    63,
     0,     0,    63,     0,     0,     0,     0,     0,     0,
     0,     0,    63,    63,    63,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   181,   181,     0,     0,
     0,     0,     0,   196,   197,   198,   199,   200,   201,
   202,   204,   206,   207,   208,   209,   210,   211,   212,
   219,   220,   221,     0,   222,   223,   224,   225,     0,
   181,   181,   196,   197,   198,   199,   200,   201,   202,
   204,   206,   207,   208,   209,   210,   211,   212,   219,
   220,   221,     0,   222,   223,   224,   225,   139,     0,
   139,   139,     0,   139,     0,     0,   139,     0,     0,
     0,     0,     0,     0,     0,     0,   139,   139,   139,
     0,     0,   139,   139,   139,     0,   139,   139,   139,
   139,   139,   139,   139,   139,   139,   139,   139,   139,
   139,   139,   139,   139,   139,   139,   139,   139,   139,
   139,   139,   139,   139,   139,   139,   139,   139,   139,
   139,   139,   139,   139,   139,     0,   139,     0,     0,
   139,     0,     0,   180,     0,   180,   180,     0,   180,
     0,     0,   180,     0,     0,     0,     0,     0,     0,
     0,     0,   180,   180,   180,     0,     0,   180,   180,
   180,     0,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,     0,   180,     0,     0,     0,     0,     0,   181,
     0,   181,   181,     0,   181,     0,     0,   181,     0,
     0,     0,     0,     0,     0,     0,     0,   181,   181,
   181,     0,     0,   181,   181,   181,     0,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,     0,   181,   126,
   126,     0,   126,     0,   126,   126,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   126,   126,
     0,   126,   126,   126,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   126,   126,     0,
     0,     0,     0,     0,     0,     0,    64,     0,     0,
    64,     0,     0,     0,   129,   129,     0,   129,     0,
   129,   129,     0,     0,     0,    64,    64,     0,     0,
     0,   126,   126,   129,   129,     0,   129,   129,   129,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    64,     0,     0,     0,     0,
     0,     0,   129,   129,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   178,   178,     0,     0,
   178,   178,   178,   178,   178,   178,   178,     0,     0,
    64,     0,     0,     0,     0,     0,   129,   129,   178,
   178,   178,   178,   178,   178,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   178,   178,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   178,   178,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   126,     0,   126,   126,     0,   126,     0,     0,   126,
     0,     0,     0,     0,     0,     0,     0,     0,   126,
   126,   126,     0,     0,   126,   126,   126,     0,   126,
   126,   126,   126,   126,   126,   126,   126,   126,   126,
   126,   126,   126,   126,   126,   126,   126,   126,   126,
   126,   126,   126,   126,   126,   126,   126,   126,   126,
   126,   126,   126,   126,   126,   126,   126,    64,   126,
    64,    64,   126,    64,     0,   129,    64,   129,   129,
     0,   129,     0,     0,   129,     0,    64,    64,    64,
     0,     0,     0,     0,   129,   129,   129,     0,     0,
   129,   129,   129,     0,   129,   129,   129,   129,   129,
   129,   129,   129,   129,   129,   129,   129,   129,   129,
   129,   129,   129,   129,   129,   129,   129,   129,   129,
   129,   129,   129,   129,   129,   129,   129,   129,   129,
   129,   129,   129,     0,   129,     0,     0,   129,     0,
     0,   178,     0,   178,   178,     0,   178,     0,     0,
   178,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   178,   178,     0,     0,   178,   178,   178,     0,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   179,
   179,     0,     0,   179,   179,   179,   179,   179,   179,
   179,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   179,   179,   179,   179,   179,   179,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   179,   179,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   122,   122,     0,     0,   122,
   122,   122,   122,   122,   122,   122,     0,     0,     0,
     0,     0,     0,     0,     0,   179,   179,   122,   122,
   122,   122,   122,   122,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   122,   122,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   149,   149,     0,     0,   149,   149,   149,   149,   149,
   149,   149,     0,     0,     0,     0,     0,     0,     0,
     0,   122,   122,   149,   149,   149,   149,   149,   149,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   149,   149,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   149,   149,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   179,     0,   179,   179,     0,
   179,     0,     0,   179,     0,     0,     0,     0,     0,
     0,     0,     0,   179,   179,   179,     0,     0,   179,
   179,   179,     0,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,     0,     0,     0,     0,     0,     0,     0,
   122,     0,   122,   122,     0,   122,     0,     0,   122,
     0,     0,     0,     0,     0,     0,     0,     0,   122,
   122,   122,     0,     0,   122,   122,   122,     0,   122,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,     0,     0,
     0,     0,     0,     0,     0,   149,     0,   149,   149,
     0,   149,     0,     0,   149,     0,     0,     0,     0,
     0,     0,     0,     0,   149,   149,   149,     0,     0,
   149,   149,   149,     0,   149,   149,   149,   149,   149,
   149,   149,   149,   149,   149,   149,   149,   149,   149,
   149,   149,   149,   149,   149,   149,   149,   149,   149,
   149,   149,   149,   149,   149,   149,   149,   149,   149,
   149,   149,   149,   142,   142,     0,     0,   142,   142,
   142,   142,   142,   142,   142,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   142,   142,   142,
   142,   142,   142,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   142,   142,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   120,
   120,     0,     0,   120,   120,   120,   120,   120,   120,
   120,     0,     0,     0,     0,     0,     0,     0,     0,
   142,   142,   120,   120,   120,   120,   120,   120,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   120,   120,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   121,   121,     0,     0,   121,
   121,   121,   121,   121,   121,   121,     0,     0,     0,
     0,     0,     0,     0,     0,   120,   120,   121,   121,
   121,   121,   121,   121,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   121,   121,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   121,   121,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   142,
     0,   142,   142,     0,   142,     0,     0,   142,     0,
     0,     0,     0,     0,     0,     0,     0,   142,   142,
   142,     0,     0,   142,   142,   142,     0,   142,   142,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,   142,   142,   142,   142,   142,     0,     0,     0,
     0,     0,     0,     0,   120,     0,   120,   120,     0,
   120,     0,     0,   120,     0,     0,     0,     0,     0,
     0,     0,     0,   120,   120,   120,     0,     0,   120,
   120,   120,     0,   120,   120,   120,   120,   120,   120,
   120,   120,   120,   120,   120,   120,   120,   120,   120,
   120,   120,   120,   120,   120,   120,   120,   120,   120,
   120,   120,   120,   120,   120,   120,   120,   120,   120,
   120,   120,     0,     0,     0,     0,     0,     0,     0,
   121,     0,   121,   121,     0,   121,     0,     0,   121,
     0,     0,     0,     0,     0,     0,     0,     0,   121,
   121,   121,     0,     0,   121,   121,   121,     0,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,   121,   121,   121,   123,   123,
     0,     0,   123,   123,   123,   123,   123,   123,   123,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   123,   123,   123,   123,   123,   123,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   123,   123,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   174,   174,     0,     0,   174,   174,
   174,   174,   174,   174,   174,     0,     0,     0,     0,
     0,     0,     0,     0,   123,   123,   174,   174,   174,
   174,   174,   174,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   174,   174,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   172,
   172,     0,     0,   172,   172,   172,   172,   172,   172,
   172,     0,     0,     0,     0,     0,     0,     0,     0,
   174,   174,   172,   172,   172,   172,   172,   172,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   172,   172,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   172,   172,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   123,     0,   123,   123,     0,   123,
     0,     0,   123,     0,     0,     0,     0,     0,     0,
     0,     0,   123,   123,   123,     0,     0,   123,   123,
   123,     0,   123,   123,   123,   123,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,     0,     0,     0,     0,     0,     0,     0,   174,
     0,   174,   174,     0,   174,     0,     0,   174,     0,
     0,     0,     0,     0,     0,     0,     0,   174,   174,
   174,     0,     0,   174,   174,   174,     0,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,     0,     0,     0,
     0,     0,     0,     0,   172,     0,   172,   172,     0,
   172,     0,     0,   172,     0,     0,     0,     0,     0,
     0,     0,     0,   172,   172,   172,     0,     0,   172,
   172,   172,     0,   172,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   172,
   173,   173,     0,     0,   173,   173,   173,   173,   173,
   173,   173,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   173,   173,   173,   173,   173,   173,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   173,   173,     0,   177,   177,     0,     0,
   177,   177,   177,   177,   177,   177,   177,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   177,
   177,   177,   177,   177,   177,     0,   173,   173,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   177,   177,
     0,   175,   175,     0,     0,   175,   175,   175,   175,
   175,   175,   175,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   175,   175,   175,   175,   175,
   175,     0,   177,   177,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   175,   175,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   175,   175,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   173,     0,   173,   173,
     0,   173,     0,     0,   173,     0,     0,     0,     0,
     0,     0,     0,     0,   173,   173,   173,     0,     0,
   173,   173,   173,     0,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   177,     0,   177,   177,     0,   177,     0,     0,
   177,     0,     0,     0,     0,     0,     0,     0,     0,
   177,   177,   177,     0,     0,   177,   177,   177,     0,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   175,     0,   175,
   175,     0,   175,     0,     0,   175,     0,     0,     0,
     0,     0,     0,     0,     0,   175,   175,   175,     0,
     0,   175,   175,   175,     0,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   170,     0,     0,   170,     0,   170,   170,
   170,   170,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   170,   170,   170,   170,   170,
   170,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   170,   170,     0,   171,     0,     0,
   171,     0,   171,   171,   171,   171,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   171,
   171,   171,   171,   171,   171,     0,     0,   170,   170,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   171,   171,
     0,   176,     0,     0,   176,     0,   176,   176,   176,
   176,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   176,   176,   176,   176,   176,   176,
     0,     0,   171,   171,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   176,   176,     0,   144,     0,     0,   144,
     0,     0,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   144,   144,
   144,   144,   144,   144,     0,     0,   176,   176,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   144,   144,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   170,     0,   170,
   170,     0,   170,     0,     0,   170,     0,     0,     0,};
}
static void yytable1(){
yytable[1] = new short[] {
     0,   144,   144,     0,     0,   170,   170,   170,     0,
     0,   170,   170,   170,     0,   170,   170,   170,   170,
   170,   170,   170,   170,   170,   170,   170,   170,   170,
   170,   170,   170,   170,   170,   170,   170,   170,   170,
   170,   170,   170,   170,   170,   170,   170,   170,   170,
   170,   171,     0,   171,   171,     0,   171,     0,     0,
   171,     0,     0,     0,     0,     0,     0,     0,     0,
   171,   171,   171,     0,     0,   171,   171,   171,     0,
   171,   171,   171,   171,   171,   171,   171,   171,   171,
   171,   171,   171,   171,   171,   171,   171,   171,   171,
   171,   171,   171,   171,   171,   171,   171,   171,   171,
   171,   171,   171,   171,   171,   176,     0,   176,   176,
     0,   176,     0,     0,   176,     0,     0,     0,     0,
     0,     0,     0,     0,   176,   176,   176,     0,     0,
   176,   176,   176,     0,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   144,     0,   144,   144,     0,   144,     0,     0,   144,
     0,     0,     0,     0,     0,     0,     0,     0,   144,
   144,   144,     0,     0,   144,   144,   144,     0,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   145,     0,     0,   145,     0,
     0,   145,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   145,   145,   145,
   145,   145,   145,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   145,   145,     0,   157,
     0,     0,   157,     0,     0,   157,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   157,   157,     0,   157,     0,   157,     0,     0,
   145,   145,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   158,     0,     0,   158,
   157,   157,   158,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   158,   158,
     0,   158,     0,   158,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   157,   157,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   158,   158,     0,
   159,     0,     0,   159,     0,     0,   159,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   159,   159,     0,   159,     0,   159,     0,
     0,   158,   158,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   159,   159,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   145,
     0,   145,   145,     0,   145,   159,   159,   145,     0,
     0,     0,     0,     0,     0,     0,     0,   145,   145,
   145,     0,     0,   145,   145,   145,     0,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   157,     0,   157,   157,     0,   157,
     0,     0,   157,     0,     0,     0,     0,     0,     0,
     0,     0,   157,   157,   157,     0,     0,   157,   157,
   157,     0,   157,   157,   157,   157,   157,   157,   157,
   157,   157,   157,   157,   157,   157,   157,   157,   157,
   157,   157,   157,   157,   157,   157,   157,   157,     0,
   158,     0,   158,   158,     0,   158,     0,     0,   158,
     0,     0,     0,     0,     0,     0,     0,     0,   158,
   158,   158,     0,     0,   158,   158,   158,     0,   158,
   158,   158,   158,   158,   158,   158,   158,   158,   158,
   158,   158,   158,   158,   158,   158,   158,   158,   158,
   158,   158,   158,   158,   158,     0,     0,     0,     0,
     0,     0,     0,     0,   159,     0,   159,   159,     0,
   159,     0,     0,   159,     0,     0,     0,     0,     0,
     0,     0,     0,   159,   159,   159,     0,     0,   159,
   159,   159,     0,   159,   159,   159,   159,   159,   159,
   159,   159,   159,   159,   159,   159,   159,   159,   159,
   159,   159,   159,   159,   159,   159,   159,   159,   159,
   160,     0,     0,   160,     0,     0,   160,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   160,   160,     0,   160,     0,   160,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   164,     0,     0,
   164,   160,   160,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   164,
   164,     0,   164,     0,   164,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   160,   160,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   165,     0,     0,   165,   164,   164,
   165,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   165,   165,     0,   165,
     0,   165,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   164,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   166,     0,     0,   166,   165,   165,   166,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   166,   166,     0,   166,     0,   166,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   165,
   165,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   166,   166,     0,     0,     0,     0,     0,   146,
     0,     0,   146,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   146,   146,
     0,   146,     0,   146,     0,   166,   166,     0,     0,
     0,     0,     0,     0,   160,     0,   160,   160,     0,
   160,     0,     0,   160,     0,     0,     0,     0,     0,
     0,     0,     0,   160,   160,   160,   146,     0,   160,
   160,   160,     0,   160,   160,   160,   160,   160,   160,
   160,   160,   160,   160,   160,   160,   160,   160,   160,
   160,   160,   160,   160,   160,   160,   160,   160,   160,
     0,   164,   146,   164,   164,     0,   164,     0,     0,
   164,     0,     0,     0,     0,     0,     0,     0,     0,
   164,   164,   164,     0,     0,   164,   164,   164,     0,
   164,   164,   164,   164,   164,   164,   164,   164,   164,
   164,   164,   164,   164,   164,   164,   164,   164,   164,
   164,   164,   164,   164,   164,   164,     0,   165,     0,
   165,   165,     0,   165,     0,     0,   165,     0,     0,
     0,     0,     0,     0,     0,     0,   165,   165,   165,
     0,     0,   165,   165,   165,     0,   165,   165,   165,
   165,   165,   165,   165,   165,   165,   165,   165,   165,
   165,   165,   165,   165,   165,   165,   165,   165,   165,
   165,   165,   165,     0,   166,     0,   166,   166,     0,
   166,     0,     0,   166,     0,     0,     0,     0,     0,
     0,     0,     0,   166,   166,   166,     0,     0,   166,
   166,   166,     0,   166,   166,   166,   166,   166,   166,
   166,   166,   166,   166,   166,   166,   166,   166,   166,
   166,   166,   166,   166,   166,   166,   166,   166,   166,
   167,     0,     0,   167,     0,     0,   167,     0,     0,
   146,     0,   146,   146,     0,   146,     0,     0,   146,
     0,     0,   167,   167,     0,   167,     0,   167,   146,
   146,   146,     0,     0,   146,   146,   146,     0,   146,
   146,   146,   146,   146,   146,   146,   146,   146,   146,
   146,   146,   146,   146,   146,   146,   141,     0,     0,
   141,   167,   167,   141,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   141,
   141,     0,   141,     0,   141,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   167,   167,     0,     0,
     0,     0,     0,     0,   155,     0,     0,   155,     0,
     0,   155,     0,     0,     0,     0,     0,   141,   141,
     0,     0,     0,     0,     0,     0,   155,   155,     0,
   155,     0,   155,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   141,   141,   156,     0,     0,   156,     0,
     0,   156,     0,     0,     0,   155,   155,     0,     0,
     0,     0,     0,     0,     0,     0,   156,   156,     0,
   156,     0,   156,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   155,   155,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   156,   156,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   150,
     0,     0,   150,     0,     0,     0,     0,     0,     0,
   156,   156,     0,     0,     0,     0,     0,   150,   150,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   167,     0,   167,   167,     0,
   167,     0,     0,   167,     0,     0,     0,     0,     0,
     0,     0,     0,   167,   167,   167,   150,     0,   167,
   167,   167,     0,   167,   167,   167,   167,   167,   167,
   167,   167,   167,   167,   167,   167,   167,   167,   167,
   167,   167,   167,   167,   167,   167,   167,   167,   167,
     0,   141,   150,   141,   141,     0,   141,     0,     0,
   141,     0,     0,     0,     0,     0,     0,     0,     0,
   141,   141,   141,     0,     0,   141,   141,   141,     0,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
   141,   141,   141,   141,   141,   141,   141,   141,   155,
     0,   155,   155,     0,   155,     0,     0,   155,     0,
     0,     0,     0,     0,     0,     0,     0,   155,   155,
   155,     0,     0,   155,   155,   155,     0,   155,   155,
   155,   155,   155,   155,   155,   155,   155,   155,   155,
   155,   155,   155,   155,   155,   155,     0,     0,   156,
     0,   156,   156,     0,   156,     0,     0,   156,     0,
     0,     0,     0,     0,     0,     0,     0,   156,   156,
   156,     0,     0,   156,   156,   156,     0,   156,   156,
   156,   156,   156,   156,   156,   156,   156,   156,   156,
   156,   156,   156,   156,   156,   156,   162,     0,     0,
   162,     0,     0,   162,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   162,
   162,     0,   162,     0,   162,     0,     0,     0,     0,
   150,     0,   150,   150,     0,   150,     0,     0,   150,
     0,     0,     0,     0,   163,     0,     0,   163,   150,
   150,   163,     0,     0,     0,     0,     0,   162,   162,
     0,     0,     0,     0,     0,     0,   163,   163,     0,
   163,     0,   163,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   162,   162,   168,     0,     0,   168,     0,
     0,   168,     0,     0,     0,   163,   163,     0,     0,
     0,     0,     0,     0,     0,     0,   168,   168,     0,
   168,     0,   168,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   163,   163,   161,     0,     0,   161,     0,     0,   161,
     0,     0,     0,     0,     0,   168,   168,     0,     0,
     0,     0,     0,     0,   161,   161,     0,   161,     0,
   161,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   168,   168,   169,     0,     0,   169,     0,     0,   169,
     0,     0,     0,   161,   161,     0,     0,     0,     0,
     0,     0,     0,     0,   169,   169,     0,   169,     0,
   169,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   161,   161,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   169,   169,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   162,     0,   162,   162,     0,   162,     0,     0,
   162,     0,     0,     0,     0,     0,     0,   169,   169,
   162,   162,   162,     0,     0,   162,   162,   162,     0,
   162,   162,   162,   162,   162,   162,   162,   162,   162,
   162,   162,   162,   162,   162,   162,   162,   162,   163,
     0,   163,   163,     0,   163,     0,     0,   163,     0,
     0,     0,     0,     0,     0,     0,     0,   163,   163,
   163,     0,     0,   163,   163,   163,     0,   163,   163,
   163,   163,   163,   163,   163,   163,   163,   163,   163,
   163,   163,   163,   163,   163,   163,     0,     0,   168,
     0,   168,   168,     0,   168,     0,     0,   168,     0,
     0,     0,     0,     0,     0,     0,     0,   168,   168,
   168,     0,     0,   168,   168,   168,     0,   168,   168,
   168,   168,   168,   168,   168,   168,   168,   168,   168,
   168,   168,   168,   168,   168,   168,   161,     0,   161,
   161,     0,   161,     0,     0,   161,     0,     0,     0,
     0,     0,     0,     0,     0,   161,   161,   161,     0,
     0,   161,   161,   161,     0,   161,   161,   161,   161,
   161,   161,   161,   161,   161,   161,   161,   161,   161,
   161,   161,   161,   161,    60,     0,   169,    60,   169,
   169,     0,   169,     0,     0,   169,     0,     0,     0,
     0,     0,     0,    60,    60,   169,   169,   169,     0,
     0,   169,   169,   169,     0,   169,   169,   169,   169,
   169,   169,   169,   169,   169,   169,   169,   169,   169,
   169,   169,   169,   169,   140,     0,     0,   140,     0,
     0,     0,    60,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   140,   140,     0,   140,     0,   140,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    60,     0,
     0,     0,   143,     0,     0,   143,     0,     0,     0,
     0,     0,   140,   140,     0,     0,     0,     0,     0,
     0,   143,   143,     0,   143,     0,   143,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   140,   140,     0,
   147,     0,     0,   147,     0,     0,     0,     0,     0,
   143,   143,     0,     0,     0,     0,     0,     0,   147,
   147,     0,   147,     0,   147,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   143,   143,    44,     0,     0,
    44,     0,     0,     0,     0,     0,     0,   147,     0,
     0,     0,     0,     0,     0,    44,    44,     0,    44,
     0,    44,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   154,   147,     0,   154,     0,     0,     0,
     0,     0,     0,     0,    44,     0,     0,     0,     0,
     0,   154,   154,     0,   154,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    60,     0,    60,    60,
     0,    60,     0,     0,    60,     0,     0,     0,     0,
    44,     0,     0,     0,    60,    60,    60,     0,     0,
   154,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   140,     0,   140,   140,
     0,   140,     0,     0,   140,   154,     0,     0,     0,
     0,     0,     0,     0,   140,   140,   140,     0,     0,
   140,   140,   140,     0,   140,   140,   140,   140,   140,
   140,   140,   140,   140,   140,   140,   140,   140,   140,
   140,   140,   140,   143,     0,   143,   143,     0,   143,
     0,     0,   143,     0,     0,     0,     0,     0,     0,
     0,     0,   143,   143,   143,     0,     0,   143,   143,
   143,     0,   143,   143,   143,   143,   143,   143,   143,
   143,   143,   143,   143,   143,   143,   143,   143,   143,
   143,   147,     0,   147,   147,     0,   147,     0,     0,
   147,     0,     0,     0,     0,     0,     0,     0,     0,
   147,   147,   147,     0,     0,   147,   147,   147,     0,
   147,   147,   147,   147,   147,   147,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   147,    44,     0,
    44,    44,     0,    44,     0,     0,    44,     0,     0,
     0,     0,     0,     0,     0,     0,    44,    44,    44,
     0,     0,    44,    44,    44,     0,    44,    44,    44,
    44,    44,    44,    44,    44,    44,    44,    44,    44,
    44,    61,     0,   154,    61,   154,   154,     0,   154,
     0,     0,   154,     0,     0,     0,     0,     0,     0,
    61,    61,   154,   154,   154,     0,     0,   154,   154,
   154,     0,   154,   154,   154,   154,   154,   154,   154,
   154,   154,   154,   154,   154,   154,    70,     0,     0,
    70,    69,     0,     0,    69,     0,     0,     0,    61,
     0,     0,     0,     0,     0,    70,    70,     0,     0,
    69,    69,     0,    66,     0,     0,    66,    67,     0,
     0,    67,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    66,    66,    61,     0,    67,    67,     0,
    68,     0,     0,    68,    70,     0,     0,    65,    69,
     0,    65,     0,     0,     0,     0,     0,     0,    68,
    68,     0,     0,     0,     0,     0,    65,    65,     0,
    71,    66,     0,    71,    72,    67,     0,    72,     0,
    70,     0,     0,     0,    69,     0,     0,     0,    71,
    71,     0,     0,    72,    72,     0,    73,    68,     0,
    73,    75,     0,     0,    75,    65,    66,     0,     0,
     0,    67,     0,     0,     0,    73,    73,     0,     0,
    75,    75,     0,    74,     0,     0,    74,    71,     0,
     0,   153,    72,    68,   153,     0,     0,     0,     0,
     0,    65,    74,    74,     0,     0,     0,     0,     0,
   153,   153,     0,     0,    73,     0,     0,     0,    75,
     0,     0,     0,    71,     0,     0,     0,    72,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    74,     0,     0,     0,     0,     0,     0,   153,
    73,     0,     0,     0,    75,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    61,     0,    61,    61,    74,    61,     0,
     0,    61,     0,     0,   153,     0,     0,     0,     0,
     0,    61,    61,    61,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    70,     0,
    70,    70,    69,    70,    69,    69,    70,    69,     0,
     0,    69,     0,     0,     0,     0,    70,    70,    70,
     0,    69,    69,    69,    66,     0,    66,    66,    67,
    66,    67,    67,    66,    67,     0,     0,    67,     0,
     0,     0,     0,    66,    66,    66,     0,    67,    67,
    67,    68,     0,    68,    68,     0,    68,     0,    65,
    68,    65,    65,     0,    65,     0,     0,    65,     0,
    68,    68,    68,     0,     0,     0,     0,    65,    65,
    65,    71,     0,    71,    71,    72,    71,    72,    72,
    71,    72,     0,     0,    72,     0,     0,     0,     0,
    71,    71,    71,     0,    72,    72,    72,    73,     0,
    73,    73,    75,    73,    75,    75,    73,    75,     0,
     0,    75,     0,     0,     0,     0,    73,    73,    73,
     0,    75,    75,    75,    74,     0,    74,    74,     0,
    74,     0,   153,    74,   153,   153,     0,   153,     0,
     0,   153,     0,    74,    74,    74,     0,     0,     0,
     0,   153,   153};
}

 
static short yycheck[][] = new short[2][];
static { yycheck0(); yycheck1();}
static void yycheck0(){
yycheck[0] = new short[] {
    28,    34,    45,    46,    47,    91,    34,    35,    59,
    37,    38,    39,    40,    41,    42,    43,    44,    35,
    48,   306,   306,    59,   101,    59,    59,    53,    54,
    55,    52,    91,    36,    37,    59,     0,    40,   383,
    65,   123,   397,   285,    36,    37,   288,   282,   125,
    36,    37,    90,    85,   408,   270,    94,    36,    37,
   413,   275,    40,   306,    64,   291,   292,   123,   433,
    96,   351,   351,   109,     0,    64,   148,   418,   101,
   123,    64,   125,   434,   435,   123,   121,   306,    64,
    18,   111,   306,    36,    37,    23,   458,   117,   113,
   114,    59,   116,    59,   123,   275,   124,   275,   127,
   128,   129,   144,   131,   275,   133,   134,   135,   136,
   285,   275,   287,   288,    64,   290,   275,    40,   293,
    20,    21,    22,   273,    24,    25,   152,   266,   266,
   155,   266,   157,   266,   266,   159,   160,   161,   162,
   163,   166,   165,   266,   167,   168,   169,   306,   171,
   172,   173,   174,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   188,   189,
   190,   191,   192,   193,   194,   195,   196,   197,   198,
   199,   200,   201,   202,   203,   204,   205,   206,   207,
   208,   209,   210,   211,   212,   213,   214,   215,   216,
   217,   218,   219,   125,    40,   222,   123,   225,    36,
    37,    91,   243,    40,   239,    91,   285,    40,   287,
   288,   306,   290,   238,    40,   293,   270,   271,   272,
   246,    40,   275,   273,   248,   273,   266,   251,   252,
    64,    62,   352,    41,    93,   125,   123,   266,   262,
    39,   123,   264,    34,    96,    59,   266,   266,   275,
   271,   266,    40,   125,    41,   266,    41,   266,   266,
   281,   282,   283,   284,   285,   123,   125,   288,   125,
   290,   291,   292,   350,   294,   295,   296,   297,   298,
   299,   300,   301,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   352,
   282,   352,   352,    53,    54,   349,   273,   346,   360,
   348,   352,   355,    41,    37,   281,    44,   283,   361,
    42,   363,   364,   275,   275,    47,   275,   383,   275,
   370,   372,    58,    59,   374,   384,   275,   282,   352,
   375,   376,   377,   266,   379,   275,   275,   275,   275,
   389,   398,   391,    41,   125,    59,    44,   123,   405,
   406,   407,   275,   123,   275,   275,   418,   125,   123,
   123,    93,    58,    59,    91,   123,   275,   409,   410,
   274,   416,   417,     0,   275,   275,    40,    40,    59,
   125,   125,   125,   428,   429,   125,   125,   275,   275,
    41,    59,    59,   123,    40,   447,   125,   449,   123,
   123,    93,   151,   125,   125,   456,   448,   123,   451,
    33,    34,   125,    36,    37,    38,    39,    40,     0,
    59,    43,   125,    45,    59,    59,    59,    59,    59,
    59,   281,    59,   283,    41,    57,   125,    47,    59,
    60,    36,   344,   345,    64,   347,   348,   349,   350,
   347,   348,   349,   350,    33,    34,   170,    36,    37,
    38,    39,    40,   144,    -1,    43,    -1,    45,    -1,
    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,
    96,    -1,    -1,    -1,    60,    -1,    -1,    -1,    64,
    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,    42,
    43,    -1,    45,    46,    47,   244,   245,    -1,    -1,
   123,    -1,   125,   126,    -1,    -1,    -1,    -1,    91,
    92,    -1,    33,    34,    96,    36,    37,    38,    39,
    40,    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    60,    91,   123,    -1,    64,   126,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    91,    92,   123,
    -1,    -1,    96,   302,   303,   304,    -1,    -1,    -1,
    -1,    37,    -1,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,   123,   293,    -1,   126,    60,    -1,    62,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   343,   344,   345,    -1,   347,
   348,   349,   350,    -1,    -1,    -1,    -1,   256,    -1,
    -1,    91,    -1,    -1,   262,   263,   264,   265,    -1,
    -1,   268,   269,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   277,   278,    -1,    -1,   281,   282,   283,   284,
   285,   286,   287,   288,   289,   290,   123,    -1,   293,
   294,   295,   296,   297,   298,   299,   300,   301,    -1,
    -1,    -1,   305,   306,   267,   268,   269,    -1,    41,
    -1,    -1,    44,    -1,    -1,   277,   278,   279,   280,
   281,    -1,   283,    -1,    -1,    -1,    -1,    58,    59,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   297,   298,
   299,   300,   301,    -1,    -1,    -1,   305,   306,    -1,
   348,   349,    -1,   351,    -1,    -1,    33,    34,    -1,
    36,    37,    38,    39,    40,    -1,    93,    43,    -1,
    45,    -1,    -1,   268,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   277,   278,    59,    60,   281,    -1,
   283,    64,    -1,    -1,   348,   349,    -1,   351,   352,
    -1,    -1,   125,    -1,    -1,   297,   298,   299,   300,
   301,    -1,    -1,    -1,   305,   306,    -1,    -1,    -1,
    -1,    91,    92,   343,   344,   345,    96,   347,   348,
   349,   350,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    33,    34,    -1,    36,    37,    38,    39,    40,    -1,
    -1,    43,    -1,    45,    -1,    -1,   123,    -1,   125,
   126,    -1,   348,   349,    -1,   351,   352,    -1,    59,
    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    33,    34,    -1,    36,    37,
    38,    39,    40,    41,    -1,    43,    -1,    45,    -1,
    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,
    96,    -1,    -1,    -1,    60,    -1,    -1,    -1,    64,
    -1,    -1,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
   123,    -1,    -1,   126,    -1,    -1,    -1,    -1,    91,
    92,    -1,    -1,    -1,    96,    -1,    -1,    -1,    -1,
    -1,    33,    34,    35,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,   123,   290,    -1,   126,   293,
    -1,    60,    -1,    -1,    -1,    64,    -1,    -1,   302,
   303,   304,    -1,    -1,   256,    -1,    -1,    -1,    -1,
    -1,   262,   263,   264,   265,    -1,    -1,   268,   269,
    -1,    -1,    -1,    -1,    -1,    91,    92,   277,   278,
    -1,    96,   281,    -1,   283,   284,   285,   286,   287,
   288,   289,   290,    -1,    -1,   293,   294,   295,   296,
   297,   298,   299,   300,   301,    -1,    -1,    -1,   305,
   306,   123,    -1,    -1,   126,    -1,    -1,    37,    -1,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   256,    -1,
    -1,    -1,    -1,    -1,   262,   263,   264,   265,    -1,
    -1,   268,   269,    -1,    -1,    -1,   348,   349,    -1,
   351,   277,   278,    -1,    -1,   281,    -1,   283,   284,
   285,   286,   287,   288,   289,   290,    -1,    91,   293,
   294,   295,   296,   297,   298,   299,   300,   301,    -1,
    -1,    -1,   305,   306,   267,   268,   269,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   277,   278,   279,   280,
   281,    -1,   283,   123,    -1,    -1,    -1,    -1,    41,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   297,   298,
   299,   300,   301,    -1,    -1,    -1,   305,   306,    59,
   348,   349,    -1,   351,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   266,    -1,   268,   269,    59,    60,    93,    -1,    -1,
    64,    -1,   277,   278,   348,   349,   281,   351,   283,
    33,    34,    -1,    36,    37,    38,    39,    40,    -1,
    -1,    43,    -1,    45,   297,   298,   299,   300,   301,
    91,    92,   125,   305,   306,    96,    -1,    -1,    -1,
    60,    -1,    -1,    -1,    64,    -1,    41,    -1,    -1,
    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,    59,    -1,   126,
    58,    59,    -1,    -1,    91,    92,    -1,    -1,    -1,
    96,   348,   349,    -1,   351,    -1,    33,    34,    -1,
    36,    37,    38,    39,    40,    -1,    -1,    43,    -1,
    45,    -1,    -1,    -1,    93,    -1,    -1,    -1,    93,
   123,    -1,    -1,   126,    -1,    -1,    60,    -1,    -1,
    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    33,    34,    -1,    36,    37,    38,    39,    40,    41,
   125,    43,    -1,    45,   125,    -1,    -1,    -1,    -1,
    -1,    91,    92,    -1,    -1,    -1,    96,    -1,    -1,
    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   341,   342,   343,   344,
   345,    -1,   347,   348,   349,   350,   123,    -1,    -1,
   126,    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,
    96,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,   268,   269,    -1,
   123,    -1,    -1,   126,    -1,    -1,   277,   278,    -1,
    -1,   281,    -1,   283,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   297,
   298,   299,   300,   301,    -1,    -1,    -1,   305,   306,
    -1,   268,   269,    -1,    -1,    -1,    -1,    -1,    -1,
   276,   277,   278,    -1,    -1,   281,    -1,   283,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   297,   298,   299,   300,   301,    -1,
    -1,    -1,   305,   306,    -1,   348,   349,   285,   351,
   287,   288,   285,   290,   287,   288,   293,   290,    -1,
    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   302,   303,   304,    -1,   266,    -1,   268,   269,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   277,   278,
   348,   349,   281,   351,   283,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
   297,   298,   299,   300,   301,    -1,    -1,    -1,   305,
   306,   268,   269,    -1,    -1,    60,    -1,    -1,    -1,
    64,   277,   278,    -1,    -1,   281,    -1,   283,    33,
    34,    -1,    36,    37,    38,    39,    40,    -1,    -1,
    43,    -1,    45,   297,   298,   299,   300,   301,    -1,
    91,    92,   305,   306,    -1,    96,   348,   349,    60,
   351,    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,   126,
    -1,    -1,    -1,    91,    92,    93,    -1,    -1,    96,
   348,   349,    -1,   351,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
    -1,    -1,   126,    -1,    -1,    60,    -1,    -1,    -1,
    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    33,
    34,    -1,    36,    37,    38,    39,    40,    -1,    -1,
    43,    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    92,    -1,    -1,    -1,    96,    -1,    -1,    60,
    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    41,    -1,    -1,    44,   123,    -1,   125,   126,
    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,    96,
    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   266,    -1,   268,   269,   123,
    -1,    -1,   126,    -1,    -1,    -1,   277,   278,    93,
    -1,   281,    -1,   283,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   297,
   298,   299,   300,   301,    -1,    -1,    -1,   305,   306,
   268,   269,    -1,    41,   125,    -1,    44,    -1,    -1,
   277,   278,    -1,    -1,   281,    -1,   283,    -1,    -1,
    -1,    -1,    58,    59,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   297,   298,   299,   300,   301,    -1,    -1,
    -1,   305,   306,    -1,    -1,   348,   349,    -1,   351,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    -1,    -1,    -1,    -1,    -1,    33,    34,
    -1,    36,    37,    -1,    39,    40,   268,   269,    43,
    -1,    45,    -1,    -1,    -1,    -1,   277,   278,   348,
   349,   281,   351,   283,    -1,    -1,   125,    -1,    -1,
    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,   297,
   298,   299,   300,   301,    -1,    -1,    -1,   305,   306,
   268,   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   277,   278,    91,    92,   281,    -1,   283,    96,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   297,   298,   299,   300,   301,    -1,    -1,
    -1,   305,   306,    -1,    -1,   348,   349,   123,   351,
    -1,   126,    33,    34,    -1,    36,    -1,    -1,    39,
    40,    -1,   285,    -1,   287,   288,    -1,   290,    -1,
    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   302,   303,   304,    -1,    -1,    64,    -1,   348,
   349,    -1,   351,    -1,    -1,    -1,    -1,    -1,    34,
    -1,    36,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    91,    92,    -1,
    -1,    -1,    96,    -1,    -1,    58,    59,    60,    61,
    62,    63,    64,    37,    38,    -1,    -1,    -1,    42,
    43,    -1,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,   123,    -1,   285,   126,   287,   288,    60,
   290,    62,    91,   293,    93,    94,    -1,    96,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    -1,    -1,    94,   123,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   268,
   269,    -1,    41,    -1,    -1,    44,    -1,    -1,   277,
   278,    -1,    -1,   281,    -1,   283,    -1,    -1,   123,
   124,    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   297,   298,   299,   300,   301,    -1,    -1,    -1,
   305,   306,    34,    -1,    36,    37,    38,    -1,    40,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   348,   349,
    -1,   351,    -1,    -1,    -1,   125,    -1,    -1,    -1,
    -1,    -1,    -1,   268,   269,    91,    -1,    93,    94,
    -1,    96,    -1,   277,   278,    -1,    -1,   281,    -1,
   283,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   297,   298,   299,   300,
   301,   123,   124,   125,   305,   306,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   273,    -1,   275,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,    -1,   285,    -1,
   287,   288,    -1,   290,    -1,    -1,   293,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,   351,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,    -1,   347,   348,   349,
   350,    -1,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,   348,
   349,   350,    -1,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   273,    -1,
   275,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
    -1,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,    -1,
   347,   348,   349,   350,    34,    -1,    36,    37,    38,
    -1,    40,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    64,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    93,    94,    -1,    96,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,   125,    34,    -1,    36,
    37,    38,    -1,    40,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    37,    93,    94,    -1,    96,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,
    -1,    -1,    -1,    -1,    -1,   123,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,
   273,    -1,   275,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,    -1,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,   347,   348,   349,   350,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   273,    -1,   275,    -1,    -1,    -1,    -1,
    -1,   281,    -1,   283,    -1,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    -1,   347,   348,   349,   350,    34,
    -1,    36,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    64,    -1,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
    -1,    -1,    91,    -1,    93,    94,    -1,    96,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
   125,    34,    -1,    36,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    93,    94,    -1,
    96,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   273,    -1,   275,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,    -1,   285,    -1,
   287,   288,    -1,   290,    -1,    -1,   293,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,    -1,   347,   348,   349,
   350,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   273,    -1,   275,
    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,    -1,   347,
   348,   349,   350,    34,    -1,    36,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    64,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,
    94,    -1,    96,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   123,   124,   125,    34,    -1,    36,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    64,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    93,    94,    -1,    96,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    41,
    -1,    -1,    44,    -1,   123,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   273,
    -1,   275,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,    -1,   285,    -1,   287,   288,    -1,   290,    -1,
    -1,   293,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   302,   303,   304,    -1,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
    -1,   347,   348,   349,   350,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   273,    -1,   275,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    58,    59,    60,    61,    62,    63,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,    91,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,   125,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,   347,   348,   349,   350,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,   348,
   349,   350,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    37,    38,
    -1,    -1,    -1,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    91,    42,
    43,    94,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    91,    42,    43,    94,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    37,    38,    94,    -1,    41,    42,    43,    -1,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,   347,   348,   349,   350,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,   348,
   349,   350,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    -1,   347,   348,   349,   350,    37,
    38,    -1,    -1,    41,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    37,    38,    94,    -1,    41,    42,    43,    -1,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    37,    38,    94,    -1,    41,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    37,    38,    94,    -1,    41,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    -1,   347,   348,   349,   350,    -1,
    -1,    -1,    -1,    -1,    -1,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,    -1,   347,   348,   349,
   350,    37,    38,    -1,    -1,    41,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    37,    38,    94,    -1,    41,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    37,    38,    94,    -1,    41,    42,
    43,    -1,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,   123,   124,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    91,    42,    43,    94,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,    -1,   347,   348,   349,
   350,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,   348,
   349,   350,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    91,    42,
    43,    94,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    91,    42,    43,    94,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
   124,    37,    38,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,    -1,    -1,    -1,    60,    -1,    62,
    63,    37,    38,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,    60,    -1,    62,
    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    94,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,   347,   348,   349,   350,    -1,    -1,    -1,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,   348,
   349,   350,    -1,    -1,    -1,    -1,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
    -1,    -1,    -1,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,    -1,   347,   348,   349,   350,
    37,    38,    -1,    -1,    -1,    42,    43,    -1,    45,
    46,    47,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,    37,
    38,    58,    59,    -1,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    -1,    62,    -1,    -1,
    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    38,    -1,   125,    41,    42,    -1,
    44,    -1,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,    58,    59,    -1,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,
   125,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    58,    59,    60,
    61,    62,    63,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    -1,   347,   348,   349,   350,    -1,
   124,   125,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,   347,   348,   349,   350,   285,    -1,
   287,   288,    -1,   290,    -1,    -1,   293,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,    -1,   347,    -1,    -1,
   350,    -1,    -1,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,   347,    -1,    -1,    -1,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,   347,    41,
    42,    -1,    44,    -1,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    41,    -1,    -1,
    44,    -1,    -1,    -1,    41,    42,    -1,    44,    -1,
    46,    47,    -1,    -1,    -1,    58,    59,    -1,    -1,
    -1,   124,   125,    58,    59,    -1,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,   124,   125,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   285,   347,
   287,   288,   350,   290,    -1,   285,   293,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,   302,   303,   304,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    -1,   347,    -1,    -1,   350,    -1,
    -1,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    37,    38,    -1,    -1,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   124,   125,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,    37,    38,    -1,    -1,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
    37,    38,    -1,    -1,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    37,    38,    -1,    -1,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    37,    38,    -1,    -1,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   285,    -1,   287,
   288,    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,    38,    -1,    -1,    41,    -1,    43,    44,
    45,    46,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    38,    -1,    -1,
    41,    -1,    43,    44,    45,    46,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    38,    -1,    -1,    41,    -1,    43,    44,    45,
    46,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    38,    -1,    -1,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   285,    -1,   287,
   288,    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,};
}
static void yycheck1(){
yycheck[1] = new short[] {
    -1,   124,   125,    -1,    -1,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    38,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    38,    -1,    -1,    41,
    93,    94,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    38,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,   124,   125,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    38,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,    -1,
    41,    93,    94,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    38,    -1,    -1,    41,    93,    94,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    38,    -1,    -1,    41,    93,    94,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    93,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    -1,   285,   125,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,    -1,   285,    -1,
   287,   288,    -1,   290,    -1,    -1,   293,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    38,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    58,    59,    -1,    61,    -1,    63,   302,
   303,   304,    -1,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,   324,   325,   326,    38,    -1,    -1,
    41,    93,    94,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   285,    -1,   287,   288,    -1,
   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   302,   303,   304,    93,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    -1,   285,   125,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,    38,    -1,    -1,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
   285,    -1,   287,   288,    -1,   290,    -1,    -1,   293,
    -1,    -1,    -1,    -1,    38,    -1,    -1,    41,   302,
   303,    44,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    38,    -1,    -1,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    38,    -1,    -1,    41,    -1,    -1,    44,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,    -1,    -1,   285,
    -1,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,
   304,    -1,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   285,    -1,   287,
   288,    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,    41,    -1,   285,    44,   287,
   288,    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,   302,   303,   304,    -1,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,    41,    -1,    -1,    44,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    41,   125,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   285,    -1,   287,   288,
    -1,   290,    -1,    -1,   293,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   302,   303,   304,    -1,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   285,    -1,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   285,    -1,   287,   288,    -1,   290,    -1,    -1,
   293,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   285,    -1,
   287,   288,    -1,   290,    -1,    -1,   293,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    41,    -1,   285,    44,   287,   288,    -1,   290,
    -1,    -1,   293,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,   302,   303,   304,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    41,    -1,    -1,
    44,    41,    -1,    -1,    44,    -1,    -1,    -1,    93,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    -1,
    58,    59,    -1,    41,    -1,    -1,    44,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,   125,    -1,    58,    59,    -1,
    41,    -1,    -1,    44,    93,    -1,    -1,    41,    93,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    41,    93,    -1,    44,    41,    93,    -1,    44,    -1,
   125,    -1,    -1,    -1,   125,    -1,    -1,    -1,    58,
    59,    -1,    -1,    58,    59,    -1,    41,    93,    -1,
    44,    41,    -1,    -1,    44,    93,   125,    -1,    -1,
    -1,   125,    -1,    -1,    -1,    58,    59,    -1,    -1,
    58,    59,    -1,    41,    -1,    -1,    44,    93,    -1,
    -1,    41,    93,   125,    44,    -1,    -1,    -1,    -1,
    -1,   125,    58,    59,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    -1,    93,    -1,    -1,    -1,    93,
    -1,    -1,    -1,   125,    -1,    -1,    -1,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,    93,
   125,    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   285,    -1,   287,   288,   125,   290,    -1,
    -1,   293,    -1,    -1,   125,    -1,    -1,    -1,    -1,
    -1,   302,   303,   304,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   285,    -1,
   287,   288,   285,   290,   287,   288,   293,   290,    -1,
    -1,   293,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,   302,   303,   304,   285,    -1,   287,   288,   285,
   290,   287,   288,   293,   290,    -1,    -1,   293,    -1,
    -1,    -1,    -1,   302,   303,   304,    -1,   302,   303,
   304,   285,    -1,   287,   288,    -1,   290,    -1,   285,
   293,   287,   288,    -1,   290,    -1,    -1,   293,    -1,
   302,   303,   304,    -1,    -1,    -1,    -1,   302,   303,
   304,   285,    -1,   287,   288,   285,   290,   287,   288,
   293,   290,    -1,    -1,   293,    -1,    -1,    -1,    -1,
   302,   303,   304,    -1,   302,   303,   304,   285,    -1,
   287,   288,   285,   290,   287,   288,   293,   290,    -1,
    -1,   293,    -1,    -1,    -1,    -1,   302,   303,   304,
    -1,   302,   303,   304,   285,    -1,   287,   288,    -1,
   290,    -1,   285,   293,   287,   288,    -1,   290,    -1,
    -1,   293,    -1,   302,   303,   304,    -1,    -1,    -1,
    -1,   302,   303};
}

 
final static short YYFINAL=1;
final static short YYMAXTOKEN=352;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'","'\"'","'#'","'$'","'%'","'&'","'\\''","'('","')'","'*'",
"'+'","','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,
"':'","';'","'<'","'='","'>'","'?'","'@'",null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'['","'\\\\'","']'","'^'",null,"'`'",null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'{'","'|'","'}'","'~'",null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,"PD_COL","PD_REF",
"PD_TIPO","PD_NUM","PD_VAR","COMENTARIO","DECLARACION_TIPO","IMPORT_JAVA",
"LINEA_JAVA","VAR","FILE","ENTERO","DECIMAL","M_REX","S_REX","Y_REX","TEXTO",
"REX_MOD","SEP","STDIN","STDOUT","STDERR","STDOUT_H","STDERR_H","MY","SUB",
"OUR","PACKAGE","WHILE","DO","FOR","UNTIL","USE","IF","ELSIF","ELSE","UNLESS",
"LAST","NEXT","RETURN","Q","QQ","QR","QW","QX","LLOR","LLXOR","LLAND","LLNOT",
"ID","MULTI_IGUAL","DIV_IGUAL","MOD_IGUAL","SUMA_IGUAL","MAS_IGUAL",
"MENOS_IGUAL","DESP_I_IGUAL","DESP_D_IGUAL","AND_IGUAL","OR_IGUAL","XOR_IGUAL",
"POW_IGUAL","LAND_IGUAL","LOR_IGUAL","DLOR_IGUAL","CONCAT_IGUAL","X_IGUAL",
"DOS_PUNTOS","LOR","DLOR","LAND","NUM_EQ","NUM_NE","STR_EQ","STR_NE","CMP",
"CMP_NUM","SMART_EQ","NUM_LE","NUM_GE","STR_LT","STR_LE","STR_GT","STR_GE",
"DESP_I","DESP_D","X","STR_REX","STR_NO_REX","UNITARIO","POW","MAS_MAS",
"MENOS_MENOS","FLECHA","ID_P","AMBITO",
};
final static String yyrule[] = {
"$accept : raiz",
"raiz : fuente",
"fuente : masFuente cuerpo",
"masFuente :",
"masFuente : fuente funcionDef",
"funcionDef : funcionSub '{' cuerpo '}'",
"funcionSub : SUB ID",
"cuerpoR : sentencia",
"cuerpoR : cuerpoR sentencia",
"cuerpoNV : cuerpoR",
"cuerpo :",
"cuerpo : cuerpoNV",
"sentencia : lista modificador ';'",
"sentencia : ';'",
"sentencia : bloque",
"sentencia : flujo",
"sentencia : modulos",
"sentencia : IMPORT_JAVA",
"sentencia : LINEA_JAVA",
"sentencia : COMENTARIO",
"sentencia : DECLARACION_TIPO",
"sentencia : error ';'",
"sentencia : error '}'",
"sentencia : error '{'",
"modulos : USE paqueteID ID ';'",
"modulos : USE ID ';'",
"modulos : PACKAGE paqueteID ID ';'",
"modulos : PACKAGE ID ';'",
"expresion : numero",
"expresion : cadena",
"expresion : variable",
"expresion : varMulti",
"expresion : asignacion",
"expresion : binario",
"expresion : aritmetica",
"expresion : logico",
"expresion : comparacion",
"expresion : coleccion",
"expresion : acceso",
"expresion : funcion",
"expresion : '&' funcion",
"expresion : lectura",
"expresion : escritura",
"expresion : regulares",
"expresion : expresion DOS_PUNTOS expresion",
"lista : listaR",
"listaR : expresion ',' listaR",
"listaR : expresion ','",
"listaR : expresion",
"modificador :",
"modificador : IF expresion",
"modificador : UNLESS expresion",
"modificador : WHILE expresion",
"modificador : UNTIL expresion",
"modificador : FOR expresion",
"flujo : NEXT ';'",
"flujo : LAST ';'",
"flujo : RETURN ';'",
"flujo : RETURN expresion ';'",
"asignacion : expresion '=' expresion",
"asignacion : expresion MAS_IGUAL expresion",
"asignacion : expresion MENOS_IGUAL expresion",
"asignacion : expresion MULTI_IGUAL expresion",
"asignacion : expresion DIV_IGUAL expresion",
"asignacion : expresion MOD_IGUAL expresion",
"asignacion : expresion POW_IGUAL expresion",
"asignacion : expresion AND_IGUAL expresion",
"asignacion : expresion OR_IGUAL expresion",
"asignacion : expresion XOR_IGUAL expresion",
"asignacion : expresion DESP_D_IGUAL expresion",
"asignacion : expresion DESP_I_IGUAL expresion",
"asignacion : expresion LAND_IGUAL expresion",
"asignacion : expresion LOR_IGUAL expresion",
"asignacion : expresion DLOR_IGUAL expresion",
"asignacion : expresion X_IGUAL expresion",
"asignacion : expresion CONCAT_IGUAL expresion",
"numero : ENTERO",
"numero : DECIMAL",
"cadena : '\\'' TEXTO '\\''",
"cadena : '\"' cadenaTexto '\"'",
"cadena : '`' cadenaTexto '`'",
"cadena : Q SEP TEXTO SEP",
"cadena : QW SEP TEXTO SEP",
"cadena : QQ SEP cadenaTexto SEP",
"cadena : QR SEP cadenaTexto SEP",
"cadena : QX SEP cadenaTexto SEP",
"cadenaTexto : cadenaTextoR",
"cadenaTextoR :",
"cadenaTextoR : cadenaTextoR variable",
"cadenaTextoR : cadenaTextoR TEXTO",
"variable : '$' VAR",
"variable : '@' VAR",
"variable : '%' VAR",
"variable : '$' paqueteVar VAR",
"variable : '@' paqueteVar VAR",
"variable : '%' paqueteVar VAR",
"variable : '$' '#' VAR",
"variable : '$' '#' paqueteVar VAR",
"variable : MY '$' VAR",
"variable : MY '@' VAR",
"variable : MY '%' VAR",
"variable : OUR '$' VAR",
"variable : OUR '@' VAR",
"variable : OUR '%' VAR",
"varMulti : MY '(' lista ')'",
"varMulti : OUR '(' lista ')'",
"paqueteVar : paqueteVar VAR AMBITO",
"paqueteVar : VAR AMBITO",
"paqueteID : paqueteID ID AMBITO",
"paqueteID : ID AMBITO",
"colParen : '(' lista ')'",
"colParen : '(' ')'",
"colRef : '[' lista ']'",
"colRef : '[' ']'",
"colRef : '{' lista '}'",
"colRef : '{' '}'",
"coleccion : colParen",
"coleccion : colRef",
"acceso : expresion colRef",
"acceso : expresion FLECHA colRef",
"acceso : '$' expresion",
"acceso : '@' expresion",
"acceso : '%' expresion",
"acceso : '\\\\' expresion",
"funcion : ID expresion",
"funcion : ID_P colParen",
"funcion : ID",
"funcion : paqueteID ID expresion",
"funcion : paqueteID ID_P colParen",
"funcion : paqueteID ID",
"funcion : ID handle expresion",
"funcion : ID_P '(' handle expresion ')'",
"funcion : ID '{' lista '}' expresion",
"handle : STDOUT_H",
"handle : STDERR_H",
"handle : FILE VAR",
"escritura : STDOUT",
"escritura : STDERR",
"lectura : '<' STDIN '>'",
"lectura : '<' expresion '>'",
"binario : expresion '|' expresion",
"binario : expresion '&' expresion",
"binario : '~' expresion",
"binario : expresion '^' expresion",
"binario : expresion DESP_I expresion",
"binario : expresion DESP_D expresion",
"logico : expresion LOR expresion",
"logico : expresion DLOR expresion",
"logico : expresion LAND expresion",
"logico : '!' expresion",
"logico : expresion LLOR expresion",
"logico : expresion LLAND expresion",
"logico : LLNOT expresion",
"logico : expresion LLXOR expresion",
"logico : expresion '?' expresion ':' expresion",
"comparacion : expresion NUM_EQ expresion",
"comparacion : expresion NUM_NE expresion",
"comparacion : expresion '<' expresion",
"comparacion : expresion NUM_LE expresion",
"comparacion : expresion '>' expresion",
"comparacion : expresion NUM_GE expresion",
"comparacion : expresion CMP_NUM expresion",
"comparacion : expresion STR_EQ expresion",
"comparacion : expresion STR_NE expresion",
"comparacion : expresion STR_LT expresion",
"comparacion : expresion STR_LE expresion",
"comparacion : expresion STR_GT expresion",
"comparacion : expresion STR_GE expresion",
"comparacion : expresion CMP expresion",
"comparacion : expresion SMART_EQ expresion",
"aritmetica : expresion '+' expresion",
"aritmetica : expresion '-' expresion",
"aritmetica : expresion '*' expresion",
"aritmetica : expresion '/' expresion",
"aritmetica : expresion POW expresion",
"aritmetica : expresion X expresion",
"aritmetica : expresion '.' expresion",
"aritmetica : expresion '%' expresion",
"aritmetica : '+' expresion",
"aritmetica : '-' expresion",
"aritmetica : MAS_MAS expresion",
"aritmetica : MENOS_MENOS expresion",
"aritmetica : expresion MAS_MAS",
"aritmetica : expresion MENOS_MENOS",
"regulares : expresion STR_REX M_REX SEP cadenaTexto SEP rexMod",
"regulares : expresion STR_REX SEP cadenaTexto SEP rexMod",
"regulares : expresion STR_NO_REX M_REX SEP cadenaTexto SEP rexMod",
"regulares : expresion STR_NO_REX SEP cadenaTexto SEP rexMod",
"regulares : expresion STR_REX S_REX SEP cadenaTexto SEP cadenaTexto SEP rexMod",
"regulares : expresion STR_REX Y_REX SEP cadenaTexto SEP cadenaTexto SEP rexMod",
"rexMod :",
"rexMod : REX_MOD",
"abrirBloque :",
"listaFor :",
"listaFor : lista",
"bloque : '{' cuerpoNV '}'",
"bloque : WHILE abrirBloque '(' expresion ')' '{' cuerpo '}'",
"bloque : UNTIL abrirBloque '(' expresion ')' '{' cuerpo '}'",
"bloque : DO abrirBloque '{' cuerpo '}' WHILE '(' expresion ')' ';'",
"bloque : DO abrirBloque '{' cuerpo '}' UNTIL '(' expresion ')' ';'",
"bloque : FOR abrirBloque '(' listaFor ';' listaFor ';' listaFor ')' '{' cuerpo '}'",
"bloque : FOR abrirBloque variable colParen '{' cuerpo '}'",
"bloque : FOR abrirBloque colParen '{' cuerpo '}'",
"bloque : IF abrirBloque '(' expresion ')' '{' cuerpo '}' condicional",
"bloque : UNLESS abrirBloque '(' expresion ')' '{' cuerpo '}' condicional",
"condicional :",
"condicional : ELSIF '(' expresion ')' '{' cuerpo '}' condicional",
"condicional : ELSE '{' cuerpo '}'",
};

//#line 333 "parser.y"

	private List<Simbolo> simbolos;
	private PreParser preParser;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	
	/**
	 * Constructor del analizador sintactico
	 * @param terminales Terminales
	 * @param opciones Opciones
	 * @param gestorErrores Gestor de errores
	 */
	public Parser(List<Terminal> terminales, Opciones opciones,GestorErrores gestorErrores) {
		preParser = new PreParser(terminales);
		simbolos = new ArrayList<>(terminales.size()*10);
		this.opciones = opciones;
		this.gestorErrores = gestorErrores;
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
	 * Función invocada por el analizador cada vez que necesita un terminal.
	 * @return Tipo del terminal
	 */
	private int yylex (){
		yylval = new ParserVal();
		int tipo = preParser.next(yylval);
		set(yylval.get());
		return tipo;
	}

	/**
	 * Función invocada cuando el analizador encuentra un error sintactico.
	 * @param descripcion String con el mensaje "Syntax error"
	 */
	private void yyerror (String descripcion){
		List<Integer> tokens = new ArrayList<>(YYMAXTOKEN);
		int yychar, yyn;
		//Reducir
		for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ ){   
			yyn = yyrindex[yystate];  
			if ((yyn !=0 ) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == yychar){  
				tokens.add(yychar);
			}
		}
		//Desplazar
		for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ ){  
			yyn = yysindex[yystate];  
			if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == yychar){  
				tokens.add(yychar);
			}
		}
		ParserError.errorSintactico(this, tokens);
	}
//#line 3210 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn/9000][yyn%9000]);
        //#### NEXT STATE ####
        yystate = yytable[yyn/9000][yyn%9000];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn/9000][yyn%9000];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn/9000][yyn%9000]+" ");
            yystate = yytable[yyn/9000][yyn%9000];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 81 "parser.y"
{yyval=set(new Raiz(add(s(val_peek(0)))));}
break;
case 2:
//#line 83 "parser.y"
{yyval=set(Fuente.add(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 3:
//#line 85 "parser.y"
{yyval=set(new Fuente(), false);}
break;
case 4:
//#line 86 "parser.y"
{yyval=set(Fuente.add(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 5:
//#line 88 "parser.y"
{yyval=set(new FuncionDef(s(val_peek(3)), s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 6:
//#line 90 "parser.y"
{yyval=set(new FuncionSub(s(val_peek(1)), s(val_peek(0))));}
break;
case 7:
//#line 92 "parser.y"
{yyval=set(Cuerpo.add(new Cuerpo(add(new AbrirBloque())), s(val_peek(0))), false);}
break;
case 8:
//#line 93 "parser.y"
{yyval=set(Cuerpo.add(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 9:
//#line 95 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 10:
//#line 97 "parser.y"
{yyval=set(new Cuerpo(add(new AbrirBloque())));}
break;
case 11:
//#line 98 "parser.y"
{yyval=val_peek(0);}
break;
case 12:
//#line 100 "parser.y"
{yyval=set(new StcLista(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 13:
//#line 101 "parser.y"
{yyval=set(new StcLista(new Lista(), add(new ModNada()), s(val_peek(0))));}
break;
case 14:
//#line 102 "parser.y"
{yyval=set(new StcBloque(s(val_peek(0))));}
break;
case 15:
//#line 103 "parser.y"
{yyval=set(new StcFlujo(s(val_peek(0))));}
break;
case 16:
//#line 104 "parser.y"
{yyval=set(new StcModulos(s(val_peek(0))));}
break;
case 17:
//#line 105 "parser.y"
{yyval=set(new StcImport(s(val_peek(0))));}
break;
case 18:
//#line 106 "parser.y"
{yyval=set(new StcLineaJava(s(val_peek(0))));}
break;
case 19:
//#line 107 "parser.y"
{yyval=set(new StcComentario(s(val_peek(0))));}
break;
case 20:
//#line 108 "parser.y"
{yyval=set(new StcTipado(s(val_peek(0))));}
break;
case 21:
//#line 109 "parser.y"
{yyval=set(new StcError());}
break;
case 22:
//#line 110 "parser.y"
{yyval=set(new StcError());}
break;
case 23:
//#line 111 "parser.y"
{yyval=set(new StcError());}
break;
case 24:
//#line 113 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 25:
//#line 114 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 26:
//#line 115 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 27:
//#line 116 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 28:
//#line 118 "parser.y"
{yyval=set(new ExpNumero(s(val_peek(0))));}
break;
case 29:
//#line 119 "parser.y"
{yyval=set(new ExpCadena(s(val_peek(0))));}
break;
case 30:
//#line 120 "parser.y"
{yyval=set(new ExpVariable(s(val_peek(0))));}
break;
case 31:
//#line 121 "parser.y"
{yyval=set(new ExpVarMulti(s(val_peek(0))));}
break;
case 32:
//#line 122 "parser.y"
{yyval=set(new ExpAsignacion(s(val_peek(0))));}
break;
case 33:
//#line 123 "parser.y"
{yyval=set(new ExpBinario(s(val_peek(0))));}
break;
case 34:
//#line 124 "parser.y"
{yyval=set(new ExpAritmetica(s(val_peek(0))));}
break;
case 35:
//#line 125 "parser.y"
{yyval=set(new ExpLogico(s(val_peek(0))));}
break;
case 36:
//#line 126 "parser.y"
{yyval=set(new ExpComparacion(s(val_peek(0))));}
break;
case 37:
//#line 127 "parser.y"
{yyval=set(new ExpColeccion(s(val_peek(0))));}
break;
case 38:
//#line 128 "parser.y"
{yyval=set(new ExpAcceso(s(val_peek(0))));}
break;
case 39:
//#line 129 "parser.y"
{yyval=set(new ExpFuncion(s(val_peek(0))));}
break;
case 40:
//#line 130 "parser.y"
{yyval=set(new ExpFuncion5(s(val_peek(1)), s(val_peek(0))));}
break;
case 41:
//#line 131 "parser.y"
{yyval=set(new ExpLectura(s(val_peek(0))));}
break;
case 42:
//#line 132 "parser.y"
{}
break;
case 43:
//#line 133 "parser.y"
{yyval=set(new ExpRegulares(s(val_peek(0))));}
break;
case 44:
//#line 134 "parser.y"
{yyval=set(new ExpRango(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 45:
//#line 136 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 46:
//#line 138 "parser.y"
{yyval=set(Lista.add(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))), false);}
break;
case 47:
//#line 139 "parser.y"
{yyval=set(new Lista(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 48:
//#line 140 "parser.y"
{yyval=set(new Lista(s(val_peek(0))), false);}
break;
case 49:
//#line 142 "parser.y"
{yyval=set(new ModNada());}
break;
case 50:
//#line 143 "parser.y"
{yyval=set(new ModIf(s(val_peek(1)), s(val_peek(0))));}
break;
case 51:
//#line 144 "parser.y"
{yyval=set(new ModUnless(s(val_peek(1)), s(val_peek(0))));}
break;
case 52:
//#line 145 "parser.y"
{yyval=set(new ModWhile(s(val_peek(1)), s(val_peek(0))));}
break;
case 53:
//#line 146 "parser.y"
{yyval=set(new ModUntil(s(val_peek(1)), s(val_peek(0))));}
break;
case 54:
//#line 147 "parser.y"
{yyval=set(new ModFor(s(val_peek(1)), s(val_peek(0))));}
break;
case 55:
//#line 149 "parser.y"
{yyval=set(new Next(s(val_peek(1)), s(val_peek(0))));}
break;
case 56:
//#line 150 "parser.y"
{yyval=set(new Last(s(val_peek(1)), s(val_peek(0))));}
break;
case 57:
//#line 151 "parser.y"
{yyval=set(new Return(s(val_peek(1)), s(val_peek(0))));}
break;
case 58:
//#line 152 "parser.y"
{yyval=set(new Return(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 59:
//#line 154 "parser.y"
{yyval=set(new Igual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 60:
//#line 155 "parser.y"
{yyval=set(new MasIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 61:
//#line 156 "parser.y"
{yyval=set(new MenosIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 62:
//#line 157 "parser.y"
{yyval=set(new MultiIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 63:
//#line 158 "parser.y"
{yyval=set(new DivIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 64:
//#line 159 "parser.y"
{yyval=set(new ModIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 65:
//#line 160 "parser.y"
{yyval=set(new PowIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 66:
//#line 161 "parser.y"
{yyval=set(new AndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 67:
//#line 162 "parser.y"
{yyval=set(new OrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 68:
//#line 163 "parser.y"
{yyval=set(new XorIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 69:
//#line 164 "parser.y"
{yyval=set(new DespDIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 70:
//#line 165 "parser.y"
{yyval=set(new DespIIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 71:
//#line 166 "parser.y"
{yyval=set(new LAndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 72:
//#line 167 "parser.y"
{yyval=set(new LOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 73:
//#line 168 "parser.y"
{yyval=set(new DLOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 74:
//#line 169 "parser.y"
{yyval=set(new XIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 75:
//#line 170 "parser.y"
{yyval=set(new ConcatIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 76:
//#line 172 "parser.y"
{yyval=set(new Entero(s(val_peek(0))));}
break;
case 77:
//#line 173 "parser.y"
{yyval=set(new Decimal(s(val_peek(0))));}
break;
case 78:
//#line 175 "parser.y"
{yyval=set(new CadenaSimple(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 79:
//#line 176 "parser.y"
{yyval=set(new CadenaDoble(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 80:
//#line 177 "parser.y"
{yyval=set(new CadenaComando(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 81:
//#line 178 "parser.y"
{yyval=set(new CadenaQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 82:
//#line 179 "parser.y"
{yyval=set(new CadenaQW(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 83:
//#line 180 "parser.y"
{yyval=set(new CadenaQQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 84:
//#line 181 "parser.y"
{yyval=set(new CadenaQR(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 85:
//#line 182 "parser.y"
{yyval=set(new CadenaQX(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 86:
//#line 184 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 87:
//#line 186 "parser.y"
{yyval=set(new CadenaTexto(),false);}
break;
case 88:
//#line 187 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(1)),s(val_peek(0))),false);}
break;
case 89:
//#line 188 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(1)),s(val_peek(0))),false);}
break;
case 90:
//#line 190 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 91:
//#line 191 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 92:
//#line 192 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 93:
//#line 193 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 94:
//#line 194 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 95:
//#line 195 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 96:
//#line 196 "parser.y"
{yyval=set(new VarSigil(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 97:
//#line 197 "parser.y"
{yyval=set(new VarPaqueteSigil(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 98:
//#line 198 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 99:
//#line 199 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 100:
//#line 200 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 101:
//#line 201 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 102:
//#line 202 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 103:
//#line 203 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 104:
//#line 205 "parser.y"
{yyval=set(new VarMultiMy(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 105:
//#line 206 "parser.y"
{yyval=set(new VarMultiOur(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 106:
//#line 208 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 107:
//#line 209 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 108:
//#line 211 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 109:
//#line 212 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 110:
//#line 214 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 111:
//#line 215 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 112:
//#line 217 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 113:
//#line 218 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 114:
//#line 219 "parser.y"
{yyval=set(new ColLlave(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 115:
//#line 220 "parser.y"
{yyval=set(new ColLlave(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 116:
//#line 222 "parser.y"
{yyval=val_peek(0);}
break;
case 117:
//#line 223 "parser.y"
{yyval=val_peek(0);}
break;
case 118:
//#line 225 "parser.y"
{yyval=set(new AccesoCol(s(val_peek(1)),s(val_peek(0))));}
break;
case 119:
//#line 226 "parser.y"
{yyval=set(new AccesoColRef(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 120:
//#line 227 "parser.y"
{yyval=set(new AccesoRefEscalar(s(val_peek(1)),s(val_peek(0))));}
break;
case 121:
//#line 228 "parser.y"
{yyval=set(new AccesoRefArray(s(val_peek(1)),s(val_peek(0))));}
break;
case 122:
//#line 229 "parser.y"
{yyval=set(new AccesoRefMap(s(val_peek(1)),s(val_peek(0))));}
break;
case 123:
//#line 230 "parser.y"
{yyval=set(new AccesoRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 124:
//#line 232 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),add(new ColParentesis(add(new Lista(s(val_peek(0))))))));}
break;
case 125:
//#line 233 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),s(val_peek(0))));}
break;
case 126:
//#line 234 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 127:
//#line 235 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(new Lista(s(val_peek(0))))))));}
break;
case 128:
//#line 236 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 129:
//#line 237 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(1)),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 130:
//#line 238 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(new Lista(s(val_peek(0))))))));}
break;
case 131:
//#line 239 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(4)),s(val_peek(2)),add(new ColParentesis(s(val_peek(3)),add(new Lista(s(val_peek(1)))),s(val_peek(0))))));}
break;
case 132:
//#line 240 "parser.y"
{yyval=set(new FuncionBloque(add(new Paquetes()),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(new Lista(s(val_peek(0))))))));}
break;
case 133:
//#line 242 "parser.y"
{yyval=set(new HandleOut(s(val_peek(0))));}
break;
case 134:
//#line 243 "parser.y"
{yyval=set(new HandleErr(s(val_peek(0))));}
break;
case 135:
//#line 244 "parser.y"
{yyval=set(new HandleFile(s(val_peek(1)),s(val_peek(0))));}
break;
case 136:
//#line 246 "parser.y"
{yyval=set(new EscrituraOut(s(val_peek(0))));}
break;
case 137:
//#line 247 "parser.y"
{yyval=set(new EscrituraErr(s(val_peek(0))));}
break;
case 138:
//#line 249 "parser.y"
{yyval=set(new LecturaIn(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 139:
//#line 250 "parser.y"
{yyval=set(new LecturaFile(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 140:
//#line 252 "parser.y"
{yyval=set(new BinOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 141:
//#line 253 "parser.y"
{yyval=set(new BinAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 142:
//#line 254 "parser.y"
{yyval=set(new BinNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 143:
//#line 255 "parser.y"
{yyval=set(new BinXor(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 144:
//#line 256 "parser.y"
{yyval=set(new BinDespI(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 145:
//#line 257 "parser.y"
{yyval=set(new BinDespD(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 146:
//#line 259 "parser.y"
{yyval=set(new LogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 147:
//#line 260 "parser.y"
{yyval=set(new DLogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 148:
//#line 261 "parser.y"
{yyval=set(new LogAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 149:
//#line 262 "parser.y"
{yyval=set(new LogNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 150:
//#line 263 "parser.y"
{yyval=set(new LogOrBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 151:
//#line 264 "parser.y"
{yyval=set(new LogAndBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 152:
//#line 265 "parser.y"
{yyval=set(new LogNotBajo(s(val_peek(1)),s(val_peek(0))));}
break;
case 153:
//#line 266 "parser.y"
{yyval=set(new LogXorBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 154:
//#line 267 "parser.y"
{yyval=set(new LogTernario(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 155:
//#line 269 "parser.y"
{yyval=set(new CompNumEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 156:
//#line 270 "parser.y"
{yyval=set(new CompNumNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 157:
//#line 271 "parser.y"
{yyval=set(new CompNumLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 158:
//#line 272 "parser.y"
{yyval=set(new CompNumLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 159:
//#line 273 "parser.y"
{yyval=set(new CompNumGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 160:
//#line 274 "parser.y"
{yyval=set(new CompNumGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 161:
//#line 275 "parser.y"
{yyval=set(new CompNumCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 162:
//#line 276 "parser.y"
{yyval=set(new CompStrEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 163:
//#line 277 "parser.y"
{yyval=set(new CompStrNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 164:
//#line 278 "parser.y"
{yyval=set(new CompStrLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 165:
//#line 279 "parser.y"
{yyval=set(new CompStrLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 166:
//#line 280 "parser.y"
{yyval=set(new CompStrGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 167:
//#line 281 "parser.y"
{yyval=set(new CompStrGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 168:
//#line 282 "parser.y"
{yyval=set(new CompStrCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 169:
//#line 283 "parser.y"
{yyval=set(new CompSmart(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 170:
//#line 285 "parser.y"
{yyval=set(new AritSuma(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 171:
//#line 286 "parser.y"
{yyval=set(new AritResta(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 172:
//#line 287 "parser.y"
{yyval=set(new AritMulti(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 173:
//#line 288 "parser.y"
{yyval=set(new AritDiv(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 174:
//#line 289 "parser.y"
{yyval=set(new AritPow(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 175:
//#line 290 "parser.y"
{yyval=set(new AritX(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 176:
//#line 291 "parser.y"
{yyval=set(new AritConcat(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 177:
//#line 292 "parser.y"
{yyval=set(new AritMod(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 178:
//#line 293 "parser.y"
{yyval=set(new AritPositivo(s(val_peek(1)),s(val_peek(0))));}
break;
case 179:
//#line 294 "parser.y"
{yyval=set(new AritNegativo(s(val_peek(1)),s(val_peek(0))));}
break;
case 180:
//#line 295 "parser.y"
{yyval=set(new AritPreIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 181:
//#line 296 "parser.y"
{yyval=set(new AritPreDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 182:
//#line 297 "parser.y"
{yyval=set(new AritPostIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 183:
//#line 298 "parser.y"
{yyval=set(new AritPostDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 184:
//#line 300 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 185:
//#line 301 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 186:
//#line 302 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 187:
//#line 303 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 188:
//#line 304 "parser.y"
{yyval=set(new RegularSubs(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 189:
//#line 305 "parser.y"
{yyval=set(new RegularTrans(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 190:
//#line 307 "parser.y"
{yyval=set(null,false);}
break;
case 191:
//#line 308 "parser.y"
{yyval=val_peek(0);}
break;
case 192:
//#line 310 "parser.y"
{yyval=set(new AbrirBloque());}
break;
case 193:
//#line 312 "parser.y"
{yyval=set(new Lista());}
break;
case 194:
//#line 313 "parser.y"
{yyval=val_peek(0);}
break;
case 195:
//#line 315 "parser.y"
{yyval=set(new BloqueVacio(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 196:
//#line 316 "parser.y"
{yyval=set(new BloqueWhile(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 197:
//#line 317 "parser.y"
{yyval=set(new BloqueUntil(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 198:
//#line 318 "parser.y"
{yyval=set(new BloqueDoWhile(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 199:
//#line 319 "parser.y"
{yyval=set(new BloqueDoUntil(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 200:
//#line 320 "parser.y"
{yyval=set(new BloqueFor(s(val_peek(11)),s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 201:
//#line 321 "parser.y"
{yyval=set(new BloqueForeachVar(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 202:
//#line 322 "parser.y"
{yyval=set(new BloqueForeach(s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 203:
//#line 323 "parser.y"
{yyval=set(new BloqueIf(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 204:
//#line 324 "parser.y"
{yyval=set(new BloqueUnless(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 205:
//#line 326 "parser.y"
{yyval=set(new CondicionalNada());}
break;
case 206:
//#line 327 "parser.y"
{yyval=set(new CondicionalElsif(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 207:
//#line 328 "parser.y"
{yyval=set(new CondicionalElse(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
//#line 4187 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn/9000][yyn%9000] == yystate)
        yystate = yytable[yyn/9000][yyn%9000]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
