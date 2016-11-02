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
import perldoop.modelo.arbol.contexto.*;
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
import perldoop.modelo.arbol.std.*;
import perldoop.modelo.arbol.lectura.*;
import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.arbol.modulos.*;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.rango.Rango;
//#line 56 "Parser.java"




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
public final static short EXP_SEP=274;
public final static short REX_MOD=275;
public final static short SEP=276;
public final static short STDIN=277;
public final static short STDOUT=278;
public final static short STDERR=279;
public final static short STDOUT_H=280;
public final static short STDERR_H=281;
public final static short MY=282;
public final static short SUB=283;
public final static short OUR=284;
public final static short PACKAGE=285;
public final static short WHILE=286;
public final static short DO=287;
public final static short FOR=288;
public final static short UNTIL=289;
public final static short USE=290;
public final static short IF=291;
public final static short ELSIF=292;
public final static short ELSE=293;
public final static short UNLESS=294;
public final static short LAST=295;
public final static short NEXT=296;
public final static short RETURN=297;
public final static short Q=298;
public final static short QQ=299;
public final static short QR=300;
public final static short QW=301;
public final static short QX=302;
public final static short LLOR=303;
public final static short LLXOR=304;
public final static short LLAND=305;
public final static short LLNOT=306;
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
public final static short ID=324;
public final static short DOS_PUNTOS=325;
public final static short LOR=326;
public final static short DLOR=327;
public final static short LAND=328;
public final static short NUM_EQ=329;
public final static short NUM_NE=330;
public final static short STR_EQ=331;
public final static short STR_NE=332;
public final static short CMP=333;
public final static short CMP_NUM=334;
public final static short SMART_EQ=335;
public final static short NUM_LE=336;
public final static short NUM_GE=337;
public final static short STR_LT=338;
public final static short STR_LE=339;
public final static short STR_GT=340;
public final static short STR_GE=341;
public final static short DESP_I=342;
public final static short DESP_D=343;
public final static short X=344;
public final static short STR_REX=345;
public final static short STR_NO_REX=346;
public final static short UNITARIO=347;
public final static short POW=348;
public final static short MAS_MAS=349;
public final static short MENOS_MENOS=350;
public final static short FLECHA=351;
public final static short ID_P=352;
public final static short AMBITO=353;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    4,    5,    6,    6,    8,    3,
    3,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,   13,   13,   13,   13,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   30,    9,    9,   31,   31,   10,   10,
   10,   10,   10,   10,   12,   12,   12,   12,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   16,   16,   17,   17,   17,
   17,   17,   17,   17,   17,   32,   33,   33,   33,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   34,   34,   14,   14,   35,   35,   36,
   36,   36,   36,   37,   37,   24,   24,   24,   25,   25,
   25,   25,   25,   25,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   38,   38,   38,   28,   28,   28,   27,
   27,   20,   20,   20,   20,   20,   20,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   29,   29,   29,   29,   29,
   29,   39,   39,   40,   41,   41,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   42,   42,   42,
};
final static short yylen[] = {                            2,
    1,    2,    0,    2,    4,    2,    1,    2,    1,    0,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    1,
    2,    2,    2,    4,    3,    4,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,    1,
    1,    1,    1,    3,    1,    2,    3,    1,    0,    2,
    2,    2,    2,    2,    2,    2,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    1,    3,    3,    3,
    4,    4,    4,    4,    4,    1,    0,    4,    2,    2,
    2,    2,    3,    3,    3,    3,    4,    3,    3,    3,
    3,    3,    3,    3,    2,    3,    2,    3,    2,    3,
    2,    3,    2,    4,    4,    1,    1,    1,    2,    3,
    2,    2,    2,    2,    2,    2,    1,    3,    3,    2,
    3,    5,    5,    1,    1,    2,    1,    1,    1,    3,
    2,    3,    3,    2,    3,    3,    3,    3,    3,    3,
    2,    3,    3,    2,    3,    5,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    2,    2,    2,    2,    7,    6,    7,    6,    9,
    9,    0,    1,    0,    0,    1,    3,    8,    8,   10,
   10,   12,    7,    6,    9,    9,    0,    8,    4,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    4,    0,    0,   19,   20,   17,
   18,   76,   77,  137,  138,  139,    0,    0,    0,  194,
  194,  194,  194,    0,  194,  194,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   13,
    0,   87,   87,    0,    0,    0,    2,    0,    7,   11,
    0,   14,   15,   16,    0,    0,   28,   29,   30,   31,
   32,   33,   34,   35,   36,   37,   38,   40,   41,   42,
   43,    0,  116,  117,  118,    6,    0,   23,   22,   21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   56,   55,
    0,   57,    0,    0,   87,   87,    0,   87,    0,    0,
  134,  135,    0,  107,    0,    0,   39,  141,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
  111,    0,  113,    0,    0,    0,  126,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  184,  185,    0,  119,    0,    0,  100,
    0,   98,   99,  103,    0,  101,  102,   27,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   25,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,  136,    0,    0,    0,  105,    0,  108,  110,  197,
  112,    0,   78,   79,   89,    0,   80,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   12,  106,    0,  129,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,   87,    0,  120,
    0,    5,  114,  115,   26,    0,    0,    0,    0,    0,
    0,    0,   24,    0,    0,   81,   83,   84,   82,   85,
    0,  104,    0,    0,    0,    0,   87,   87,   87,    0,
   87,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  132,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  196,    0,    0,  204,    0,    0,    0,
    0,   87,   87,  193,  187,    0,  189,    0,    0,    0,
    0,  203,    0,    0,    0,  186,    0,    0,  188,  198,
    0,    0,    0,  199,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  205,  206,  190,  191,  200,  201,    0,
    0,    0,    0,    0,    0,  202,    0,  209,    0,    0,
    0,  208,
};
final static short yydgoto[] = {                          1,
    2,    3,   57,    5,    6,   58,   59,   60,   61,  166,
   62,   63,   64,   65,   66,   67,   68,   69,   70,   71,
   72,   73,   74,   75,   76,   77,   78,   79,   80,   81,
   82,  149,  150,  134,   83,   84,   85,  126,  415,  101,
  359,  444,
};
final static short yysindex[] = {                         0,
    0, -239,  791, -277,    0,  -61,  -54,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,   13, -260,    0,
    0,    0,    0, -248,    0,    0,   25,   28, 1153, -186,
 -183, -180, -178, -172, 1959,  433, -289, 1192, 1959, 1959,
 1253, 1959, 1959, 1959, 1959, 1524, 1585,  715,   70,    0,
 -158,    0,    0, 1084, 1624, 1959,    0,  791,    0,    0,
 -175,    0,    0,    0, -287, 5256,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   73,    0,    0,    0,    0,  791,    0,    0,    0,
 -143, 1959, -141, -138, -127, 1959,  -74,  -71,  -40, -126,
  157,   83,   45,  168,  -39, -115,  172,  173,    0,    0,
 1688,    0, 3876,  -53,    0,    0,  -51,    0, 5406,  -43,
    0,    0, 1688,    0, 5721, 1959,    0,    0, 5306,  -11,
  -11, -125,  -11,  -36,  -11,  -11,   -5,   -5,    0,  185,
    0,  134,    0,  104,  -84,  832,    0,  192,  199, -199,
  141, -125,  -28,  -11,  -27, -125,  -11,  -25,  -11,    0,
 1959, 1959, 1959, 1959, 1959,  181,  494,  202, 1959, 1959,
 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
 -150, -225, 1959,    0,    0,  -67,    0, 1959,  119,    0,
  204,    0,    0,    0,  205,    0,    0,    0,  -38, 1959,
  791,   -6,   24,  -18, 1524,  -34,  -17,  202,  128, 1959,
    0,  -37, 1959, 1959,  132,    0,  -16,   49,   55,   60,
   76,    0,  133, 5721, 2020,    0,    3,    0,    0,    0,
    0, 1959,    0,    0,    0, 1959,    0, -125,   92,    3,
    3, 5256, 5256, 5256, 5256, 5256,    0,    0, 5721,    0,
 5356, 5356, 5406, 5406, 5406, 5406, 5406, 5406, 5406, 5406,
 5406, 5406, 5406, 5406, 5406, 5406, 5406, 5406, 5406, 5406,
 3926, 5748, 5776, 5776, 5813, 5841, 5841, 5868,  561,  561,
  561,  561,  561,  561,  561,  817,  817,  817,  817,  817,
  817,  817,  817,  453,  453,  281,  281,  281,  112,  112,
  112,  112,   86,   87,   89,    0,   90,    0,  -11,    0,
 5256,    0,    0,    0,    0, 3981,  234,  185,  311,  248,
  791, 4296,    0, 4351, 4406,    0,    0,    0,    0,    0,
 2056,    0, 4461, 4776,    3, 1959,    0,    0,    0,   97,
    0,   98,  252, -246, 1959,  791,  251,  255,  256,  257,
  -67,    0,    0, 5721,  105,  107,  108,  110,  111,  110,
  791,  346,  353,    0,  335,  270,    0,  791,  791,  791,
  110,    0,    0,    0,    0,  110,    0,  271, 1959, 1959,
 1959,    0,  273,  274,  278,    0,  129,  130,    0,    0,
 4831, 4886,  366,    0, -162, -162,  110,  110,  349,  351,
  290,  372,  293,    0,    0,    0,    0,    0,    0,  791,
 1959,  791,  292, 4941,  294,    0,  297,    0,  791,  296,
 -162,    0,
};
final static short yyrindex[] = {                         0,
    0,  423,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 6636,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   68,    0,    0,
  368,    0,    0,    0,    0,  289,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  644,    0,    0,    0,    0,  308,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  663,    0,
    0,    0,    0,    0,10844,    0,    0,    0,    0, 6782,
 7092, 2125, 7165,    0, 7238, 7548, 6255, 6328,    0,    0,
    0,    0,    0,    0,  368,    0,    0,    0,    0,  -26,
    0, 2440,    0, 7621,    0, 2513, 7694,    0, 8004,    0,
    0,    0,    0,    0,    0,    0, 6709,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  749,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  308,    0,    0,    0,  375,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,11989, 6182,    0, 2586,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 2901,    0, 2974,
 3047,  377,  379,  380,  381,  382,    0,    0,12039,    0,
12395,12406, 1113, 1223, 1555, 1717, 1741, 1836, 1990, 2101,
 9855, 9923,10289,10357,10723,12323,12327,12348,12369,12373,
    0, 5948,11889,11939,11834,11541,11597,10727,10784,11021,
11078,11135,11192,11429,11486, 9493, 9791, 9859, 9927,10225,
10293,10361,10659, 9353, 9423, 8907, 8977, 9047, 8150, 8458,
 8529, 8600,    0,    0,    0,    0,    0,    0, 8077,    0,
  356,    0,    0,    0,    0,    0,    0,  383,    0,    0,
  308,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 3508,    0,    0,    0, 3362,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  375,  308,    0,    0,    0,    0,
 3823,    0,    0,12273,    0,    0,    0, 3435,    0, 3435,
  308,    0,    0,    0,    0,    0,    0,  308,  308,  308,
 3435,    0,    0,    0,    0, 3435,    0,    0,    0,    0,
  402,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  392,  392, 3435, 3435,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  308,
    0,  308,    0,    0,    0,    0,    0,    0,  308,    0,
  392,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -41,    0,    0,    0,  386,  397,  -44,    0,
    0,    0,    0,   54,  -29,    0,    0,  343,    0,    0,
    0,    0,    0,    0,    0,  410,    0,    0,    0,    0,
    0,  -24,    0,  295,  -31,  -30,    0,  304, -383,  193,
 -362, -404,
};
final static int YYTABLESIZE=12710;
static short yytable[][] = new short[2][];
static { yytable0(); yytable1();}
static void yytable0(){
yytable[0] = new short[] {
   113,   153,   140,   142,   145,    90,   119,   125,    86,
   129,   130,   131,   133,   135,   136,   137,   138,   417,
   147,   238,   251,   355,   363,   405,    47,   154,   157,
   159,   426,   151,    93,    91,   445,   429,    10,    36,
   227,   167,    93,    91,   402,   271,    92,   403,     4,
   347,   229,    86,   231,    97,    95,   348,   235,    96,
   446,   447,   111,   462,    94,   433,    97,    95,    87,
    49,    99,   168,    94,   255,     9,    88,    86,    89,
   249,   100,   275,   276,   105,    98,   106,   263,    47,
   246,   244,   227,   109,   245,    47,   110,    98,   227,
   114,   258,   259,   115,   261,   227,   116,   264,   117,
   227,   227,   227,   140,   227,   118,   227,   227,   227,
   227,   247,   146,   161,   111,   162,   163,   148,   164,
   228,   111,   165,   343,   344,   345,   230,   227,   232,
   346,   227,   233,   227,   442,   443,   282,   283,   284,
   285,   286,   290,   289,   234,   291,   292,   293,   294,
   295,   296,   297,   298,   299,   300,   301,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   236,     9,   349,   237,   350,   240,
   239,   351,   357,   358,   161,    47,   162,   163,   241,
   164,   250,   252,   165,   356,   253,   254,   102,   103,
   104,   360,   107,   108,   257,   362,   260,   262,   364,
   365,   268,   269,   266,   270,   267,   273,   152,   274,
   227,   111,   328,   277,   278,   280,   287,   281,    46,
   373,   352,   353,   354,   374,   132,   156,    86,   361,
   227,   227,   227,   227,   227,   271,   371,   227,   366,
   227,   227,   227,   227,   227,   227,   227,   227,   227,
   227,   227,   227,   227,   227,   227,   227,   227,   227,
   227,   227,   227,   227,   227,   227,   227,   227,   227,
   227,   227,   227,   227,   227,   227,   227,   227,   227,
   227,   227,   227,   227,   227,   227,   227,   227,   227,
   227,   227,   227,   227,   227,   227,   227,   124,   124,
   288,   288,    10,   219,   227,   387,   227,   380,   217,
   382,   367,   227,   242,   218,   243,    48,   368,   227,
    48,   227,   227,   369,   223,   224,   225,   226,   404,
   391,   227,   227,   406,   226,   394,    48,   155,   158,
     9,   370,   395,   396,   397,   372,   399,   375,   384,
   418,   227,   377,   378,   227,   379,   381,   423,   424,
   425,   385,   386,    47,   398,   400,   401,   407,   404,
   408,   409,   410,   411,    48,   412,   413,   414,   419,
   416,   427,   428,   431,   432,   207,   420,   421,   422,
   430,    47,   434,   435,    47,   227,   227,   436,   111,
   437,   438,   441,   448,   453,   449,   455,   451,   450,
    48,    47,   452,   456,   460,   458,   459,   461,   454,
     1,   227,   207,   207,    49,   207,   207,   207,   207,
   207,    10,   195,   207,    52,   207,    54,    53,    50,
    51,   196,   195,   160,   144,   248,   127,   279,    47,
   272,   207,   207,     0,     0,     0,   207,   221,   222,
     0,   223,   224,   225,   226,     0,     0,    42,    52,
     0,    54,    41,    37,    51,    46,     0,     0,    39,
     0,    40,     0,     0,    47,     0,   207,   207,     0,
     0,     0,   207,     0,   219,     0,     0,    38,     0,
   217,   214,    55,   215,   216,   218,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   207,     0,   207,   207,     0,     0,     0,
     0,     0,    47,    56,     0,    42,    52,    53,    54,
    41,    37,    51,    46,     0,     0,    39,     0,    40,
     0,   155,   158,     0,    47,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    38,     0,   123,     0,
    55,    43,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    48,
   111,    48,    48,     0,    48,     0,     0,    48,     0,
    47,    56,     0,     0,     0,    53,     0,     0,     0,
     0,     0,     0,     0,   219,     0,     0,     0,     0,
   217,   214,     0,   215,   216,   218,     0,     0,     0,
     0,     0,     0,     0,     0,   111,     0,     0,    43,
   204,     0,   206,     0,   220,   221,   222,     0,   223,
   224,   225,   226,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    47,     0,    47,    47,     0,    47,
   207,     0,    47,     0,    47,     0,   207,   207,   207,
   207,     0,     0,   207,   207,     0,     0,     0,     0,
     0,     0,     0,   207,   207,   207,     0,     0,   207,
   207,   207,   207,   207,   207,   207,   207,   207,   207,
   111,    45,   207,   207,   207,   207,   207,   207,   207,
   207,   207,     0,     0,     0,   207,     0,   120,    12,
    13,    45,   154,     0,     0,   154,     0,     0,    14,
    15,    16,   121,   122,    17,   207,    18,     0,     0,
     0,   154,   154,     0,     0,     0,     0,     0,     0,
     0,     0,    30,    31,    32,    33,    34,     0,    45,
     0,    35,     0,   207,   207,     0,   207,     0,     0,
     0,    42,    52,     0,    54,    41,    37,    51,    46,
   154,    36,    39,     0,    40,     0,    12,    13,     0,
     0,     0,     0,     0,    45,     0,    14,    15,    16,
    50,    38,    17,     0,    18,    55,     0,     0,    44,
    45,     0,    49,   124,     0,   154,     0,    46,     0,
    30,    31,    32,    33,    34,   220,   221,   222,    35,
   223,   224,   225,   226,     0,    47,    56,    46,     0,
     0,    53,     0,     0,     0,     0,     0,     0,    36,
     0,     0,     0,     0,     0,    42,    52,     0,    54,
    41,    37,    51,    46,     0,     0,    39,     0,    40,
     0,    48,     0,   143,    43,    46,    44,    45,     0,
    49,   288,     0,     0,    50,    38,     0,     0,   219,
    55,     0,     0,     0,   217,   214,     0,   215,   216,
   218,    42,    52,     0,    54,    41,    37,    51,    46,
   139,    46,    39,     0,    40,     0,     0,     0,     0,
    47,    56,     0,     0,     0,    53,     0,     0,     0,
     0,    38,     0,     0,     0,    55,   205,   207,   208,
   209,   210,   211,   212,   213,   220,   221,   222,    47,
   223,   224,   225,   226,     0,    48,     0,     0,    43,
     0,     0,     0,     0,     0,    47,    56,     0,     0,
     0,    53,     0,    45,     0,    45,    45,     0,    45,
     0,   154,    45,     0,   111,     0,     0,     0,     0,
     0,     0,     0,     0,   154,     0,   154,   154,     0,
   154,   111,     0,   154,    43,     0,     0,     0,     0,
     0,     0,     0,   154,   154,   154,     0,     0,     7,
     0,     0,     0,     0,     0,     8,     9,    10,    11,
     0,     0,    12,    13,     0,     0,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,    19,    20,    21,    22,    23,    24,    25,     0,
     0,    26,    27,    28,    29,    30,    31,    32,    33,
    34,     0,     0,     0,    35,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    46,     0,    46,    46,    36,    46,     0,     0,    46,
     0,     0,     0,     7,     0,     0,     0,     0,     0,
     8,     9,    10,    11,     0,     0,    12,    13,     0,
     0,     0,    44,    45,     0,    49,    14,    15,    16,
     0,     0,    17,     0,    18,    19,    20,    21,    22,
    23,    24,    25,     0,     0,    26,    27,    28,    29,
    30,    31,    32,    33,    34,     0,     0,     0,    35,
     0,   120,    12,    13,     0,     0,     0,     0,     0,
     0,     0,    14,    15,    16,   121,   122,    17,    36,
    18,    42,    52,   153,    54,    41,    37,    51,    46,
     0,     0,    39,     0,    40,    30,    31,    32,    33,
    34,     0,     0,     0,    35,     0,    44,    45,     0,
    49,    38,     0,     0,     0,    55,     0,     0,     0,
     0,     0,   153,     0,    36,   153,     0,   212,   213,
   220,   221,   222,     0,   223,   224,   225,   226,     0,
     0,   153,   153,     0,     0,    47,    56,     0,     0,
     0,    53,    44,    45,     0,    49,     0,    42,    52,
     0,    54,    41,    37,    51,    46,     0,     0,    39,
     0,    40,     0,     0,     0,     0,     0,     0,     0,
   153,   111,     0,     0,    43,     0,   112,    38,     0,
     0,     0,    55,     0,     0,     0,     0,     0,     0,
     0,    42,    52,     0,    54,    41,    37,    51,    46,
     0,     0,    39,     0,    40,   153,     0,     0,     0,
     0,     0,    47,    56,     0,     0,     0,    53,     0,
     0,    38,     0,   128,     0,    55,     0,     0,     0,
     0,     0,     0,     0,    59,     0,     0,    59,     0,
     0,     0,     0,     0,     0,     0,     0,   111,     0,
     0,    43,     0,    59,    59,    47,    56,     0,    42,
    52,    53,    54,    41,    37,    51,    46,     0,     0,
    39,     0,    40,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    38,
     0,   111,    59,    55,    43,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    47,    56,     0,     0,    59,    53,
   152,     0,    12,    13,     0,     0,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,     0,     0,     0,     0,     0,     0,     0,   111,
     0,     0,    43,     0,     0,    30,    31,    32,    33,
    34,   153,     0,     0,    35,     0,     0,     0,     0,
     0,     0,     0,     0,   153,     0,   153,   153,     0,
   153,     0,     0,   153,    36,     0,     0,     0,     0,
     0,     0,     0,   153,   153,   153,     0,     0,    12,
    13,     0,     0,     0,     0,     0,     0,     0,    14,
    15,    16,    44,    45,    17,    49,    18,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    30,    31,    32,    33,    34,     0,     0,
     0,    35,    12,    13,     0,     0,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,    36,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    30,    31,    32,    33,
    34,     0,     0,    59,    35,     0,     0,     0,    44,
    45,     0,    49,     0,     0,     0,    59,     0,    59,
    59,     0,    59,     0,    36,    59,     0,   132,     0,
    12,    13,     0,     0,     0,    59,    59,    59,     0,
    14,    15,    16,     0,     0,    17,     0,    18,     0,
     0,     0,    44,    45,     0,    49,     0,     0,     0,
     0,     0,     0,    30,    31,    32,    33,    34,     0,
    42,    52,    35,    54,    41,    37,    51,    46,   139,
     0,    39,     0,    40,     0,     0,     0,     0,     0,
     0,     0,    36,     0,     0,     0,     0,     0,     0,
    38,     0,     0,     0,    55,     0,     0,     0,     0,
     0,     0,     0,    62,     0,     0,    62,     0,     0,
    44,    45,     0,    49,     0,     0,     0,     0,     0,
     0,     0,    62,    62,    47,    56,     0,    42,    52,
    53,    54,    41,    37,    51,    46,     0,     0,    39,
     0,    40,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    38,     0,
   111,    62,    55,    43,     0,     0,     0,     0,     0,
     0,    42,    52,     0,    54,    41,    37,    51,    46,
     0,     0,    39,     0,    40,     0,     0,     0,     0,
     0,     0,    47,    56,   141,     0,    62,    53,     0,
     0,    38,     0,     0,     0,    55,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   111,     0,
     0,    43,     0,     0,     0,    47,    56,     0,     0,
     0,    53,    42,    52,     0,    54,    41,    37,    51,
    46,     0,     0,    39,     0,    40,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   111,    38,     0,    43,     0,    55,     0,     0,
     0,     0,     0,    63,     0,     0,    63,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    63,    63,     0,     0,    47,    56,     0,
    64,     0,    53,    64,     0,     0,     0,     0,     0,
     0,    12,    13,     0,     0,     0,     0,     0,    64,
    64,    14,    15,    16,     0,     0,    17,     0,    18,
     0,    63,   111,     0,   143,    43,     0,     0,     0,
     0,     0,     0,     0,    30,    31,    32,    33,    34,
     0,     0,    62,    35,     0,     0,     0,    64,     0,
     0,     0,     0,     0,     0,    62,    63,    62,    62,
     0,    62,     0,    36,    62,     0,     0,     0,    12,
    13,     0,     0,     0,    62,    62,    62,     0,    14,
    15,    16,     0,    64,    17,     0,    18,     0,     0,
     0,    44,    45,     0,    49,    60,     0,     0,    60,
     0,     0,    30,    31,    32,    33,    34,     0,     0,
   156,    35,    12,    13,    60,    60,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,    36,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    30,    31,    32,    33,
    34,     0,     0,    60,    35,     0,     0,     0,    44,
    45,     0,    49,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    36,     0,     0,     0,     0,
     0,     0,     0,    12,    13,     0,     0,     0,    60,
     0,     0,     0,    14,    15,    16,     0,     0,    17,
     0,    18,    44,    45,     0,    49,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    30,    31,    32,
    33,    34,    63,    42,    52,    35,    54,    41,    37,
    51,    46,     0,     0,    39,    63,    40,    63,    63,
     0,    63,     0,     0,    63,    36,     0,     0,    64,
     0,     0,     0,    38,    63,    63,    63,    55,     0,
     0,     0,    64,     0,    64,    64,    61,    64,     0,
    61,    64,     0,    44,    45,     0,    49,     0,     0,
     0,    64,    64,    64,     0,    61,    61,    47,    56,
     0,    42,    52,    53,    54,    41,     0,    51,    46,
     0,     0,    39,     0,    40,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   111,    61,    55,    43,     0,     0,
     0,    42,    52,     0,    54,     0,     0,    51,    46,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    60,    47,    56,     0,     0,
    61,    53,     0,     0,     0,    55,     0,    60,     0,
    60,    60,     0,    60,     0,     0,    60,     0,     0,
     0,     0,     0,     0,     0,     0,    60,    60,    60,
    70,   111,     0,    70,    43,    47,    56,     0,     0,
     0,    53,     0,     0,     0,     0,     0,     0,    70,
    70,     0,    92,    92,     0,    92,    92,    92,    92,
    92,    92,    92,    92,     0,     0,     0,     0,     0,
     0,   111,     0,     0,    43,    92,    92,    92,    92,
    92,    92,     0,     0,     0,     0,     0,    70,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    92,     0,    92,    92,     0,     0,     0,
     0,     0,     0,    70,    12,    13,     0,     0,     0,
     0,     0,     0,     0,    14,    15,    16,     0,     0,
    17,     0,    18,     0,     0,     0,     0,    92,    92,
    92,     0,     0,     0,     0,     0,     0,    30,    31,
    32,    33,    34,     0,     0,    61,    35,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    61,
     0,    61,    61,     0,    61,     0,    36,    61,     0,
     0,     0,    12,    13,     0,     0,     0,    61,    61,
    61,     0,    14,    15,    16,     0,     0,    17,     0,
    18,     0,     0,     0,    44,    45,     0,    49,     0,
     0,     0,     0,     0,     0,    30,    31,    32,    33,
    34,     0,    12,    13,    35,     0,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,     0,     0,     0,    36,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    30,    31,    32,    33,
    34,     0,     0,     0,    35,     0,     0,     0,     0,
     0,     0,    44,    45,     0,    49,     0,     0,    70,
     0,     0,     0,     0,    36,     0,     0,     0,     0,
     0,     0,    70,     0,    70,    70,     0,    70,     0,
     0,    70,     0,     0,     0,    92,     0,     0,     0,
     0,    70,    70,    70,     0,    49,     0,     0,    92,
     0,    92,    92,     0,    92,     0,     0,    92,     0,
     0,     0,     0,     0,     0,     0,     0,    92,    92,
    92,     0,    92,    92,    92,     0,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,     0,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,     0,    92,    92,
    92,    92,    90,    90,     0,    90,    90,    90,    90,
    90,    90,    90,    90,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    90,    90,    90,    90,
    90,    90,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    90,     0,    90,    90,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    91,    91,     0,    91,    91,    91,
    91,    91,    91,    91,    91,     0,     0,    90,    90,
    90,     0,     0,     0,     0,     0,    91,    91,    91,
    91,    91,    91,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    91,     0,    91,    91,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    95,    95,     0,    95,    95,
    95,    95,    95,    95,    95,    95,     0,     0,    91,
    91,    91,     0,     0,     0,     0,     0,    95,    95,
    95,    95,    95,    95,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    95,     0,    95,    95,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    95,    95,    95,     0,     0,    90,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    90,
     0,    90,    90,     0,    90,     0,     0,    90,     0,
     0,     0,     0,     0,     0,     0,     0,    90,    90,
    90,     0,    90,    90,    90,     0,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    90,    90,    90,
    90,     0,    90,    90,    90,    90,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    90,    90,    90,
    90,    90,    90,    90,    90,    90,    91,    90,    90,
    90,    90,     0,     0,     0,     0,     0,     0,     0,
    91,     0,    91,    91,     0,    91,     0,     0,    91,
     0,     0,     0,     0,     0,     0,     0,     0,    91,
    91,    91,     0,    91,    91,    91,     0,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,     0,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    95,    91,
    91,    91,    91,     0,     0,     0,     0,     0,     0,
     0,    95,     0,    95,    95,     0,    95,     0,     0,
    95,     0,     0,     0,     0,     0,     0,     0,     0,
    95,    95,    95,     0,    95,    95,    95,     0,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,     0,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,     0,
    95,    95,    95,    95,    96,    96,     0,    96,    96,
    96,    96,    96,    96,    96,    96,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    96,    96,
    96,    96,    96,    96,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    96,     0,    96,    96,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    93,    93,     0,    93,
    93,    93,    93,    93,    93,    93,    93,     0,     0,
    96,    96,    96,     0,     0,     0,     0,     0,    93,
    93,    93,    93,    93,    93,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    93,     0,    93,    93,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    94,    94,     0,
    94,    94,    94,    94,    94,    94,    94,    94,     0,
     0,    93,    93,    93,     0,     0,     0,     0,     0,
    94,    94,    94,    94,    94,    94,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    94,     0,    94,
    94,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    94,    94,    94,     0,     0,    96,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    96,     0,    96,    96,     0,    96,     0,     0,
    96,     0,     0,     0,     0,     0,     0,     0,     0,
    96,    96,    96,     0,    96,    96,    96,     0,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,     0,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    93,
    96,    96,    96,    96,     0,     0,     0,     0,     0,
     0,     0,    93,     0,    93,    93,     0,    93,     0,
     0,    93,     0,     0,     0,     0,     0,     0,     0,
     0,    93,    93,    93,     0,    93,    93,    93,     0,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,     0,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    94,    93,    93,    93,    93,     0,     0,     0,     0,
     0,     0,     0,    94,     0,    94,    94,     0,    94,
     0,     0,    94,     0,     0,     0,     0,     0,     0,
     0,     0,    94,    94,    94,     0,    94,    94,    94,
     0,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,     0,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,     0,    94,    94,    94,    94,    97,    97,     0,
    97,    97,    97,    97,    97,    97,    97,    97,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    97,    97,    97,    97,    97,    97,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    97,     0,    97,
    97,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   192,   192,
     0,     0,   192,   192,   192,   192,   192,   192,   192,
     0,     0,    97,    97,    97,     0,     0,     0,     0,
     0,   192,   192,   192,   192,   192,   192,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   192,     0,
   192,   192,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   112,
   112,     0,     0,   112,   112,   112,   112,   112,   112,
   112,     0,     0,   192,   192,   192,     0,     0,     0,
     0,     0,   112,   112,   112,   112,   112,   112,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   112,   112,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   112,   112,     0,     0,
    97,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    97,     0,    97,    97,     0,    97,
     0,     0,    97,     0,     0,     0,     0,     0,     0,
     0,     0,    97,    97,    97,     0,    97,    97,    97,
     0,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,     0,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,   192,    97,    97,    97,    97,     0,     0,     0,
     0,     0,     0,     0,   192,     0,   192,   192,     0,
   192,     0,     0,   192,     0,     0,     0,     0,     0,
     0,     0,     0,   192,   192,   192,     0,   192,   192,
   192,     0,   192,   192,   192,   192,   192,   192,   192,
   192,   192,   192,   192,   192,   192,     0,   192,   192,
   192,   192,   192,   192,   192,   192,   192,   192,   192,
   192,   192,   192,   192,   192,   192,   192,   192,   192,
   192,   192,   112,   192,   192,   192,   192,     0,     0,
     0,     0,     0,     0,     0,   112,     0,   112,   112,
     0,   112,     0,     0,   112,     0,     0,     0,     0,
     0,     0,     0,     0,   112,   112,   112,     0,   112,
   112,   112,     0,   112,   112,   112,   112,   112,   112,
   112,   112,   112,   112,   112,   112,   112,     0,   112,
   112,   112,   112,   112,   112,   112,   112,   112,   112,
   112,   112,   112,   112,   112,   112,   112,   112,   112,
   112,   112,   112,     0,   112,   112,   112,   112,   133,
   133,     0,     0,   133,   133,   133,   133,   133,   133,
   133,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   133,   133,   133,   133,   133,   133,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   219,   196,
     0,   133,   133,   217,   214,     0,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   256,   204,   172,   206,   189,     0,     0,
     0,     0,     0,     0,     0,   133,   133,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   219,   196,     0,     0,    47,   217,
   214,   195,   215,   216,   218,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   376,     0,   204,
   172,   206,   189,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   111,   194,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    47,   219,   196,   195,     0,   383,
   217,   214,     0,   215,   216,   218,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   204,   172,   206,   189,     0,     0,     0,     0,   111,
   194,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    47,     0,     0,   195,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   133,     0,     0,     0,     0,     0,     0,
   111,   194,     0,     0,     0,   133,     0,   133,   133,
     0,   133,     0,     0,   133,     0,     0,     0,     0,
     0,     0,     0,     0,   133,   133,   133,     0,   133,
   133,   133,     0,   133,   133,   133,   133,   133,   133,
   133,   133,   133,   133,   133,   133,   133,     0,   133,
   133,   133,   133,   133,   133,   133,   133,   133,   133,
   133,   133,   133,   133,   133,   133,   133,   133,   133,
   133,   133,   133,     0,   133,   133,   133,   133,     0,
     0,     0,     0,   169,   170,   171,     0,   173,   174,
   175,     0,   176,   177,   178,   179,   180,   181,   182,
   183,   184,   185,   186,   187,   188,     0,   190,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,     0,   169,
   170,   171,     0,   173,   174,   175,     0,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   188,     0,   190,   191,   192,   193,   197,   198,
   199,   200,   201,   202,   203,   205,   207,   208,   209,
   210,   211,   212,   213,   220,   221,   222,     0,   223,
   224,   225,   226,     0,     0,     0,     0,     0,     0,
   169,   170,   171,     0,   173,   174,   175,     0,   176,
   177,   178,   179,   180,   181,   182,   183,   184,   185,
   186,   187,   188,     0,   190,   191,   192,   193,   197,
   198,   199,   200,   201,   202,   203,   205,   207,   208,
   209,   210,   211,   212,   213,   220,   221,   222,     0,
   223,   224,   225,   226,   219,   196,     0,     0,   388,
   217,   214,     0,   215,   216,   218,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   204,   172,   206,   189,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    47,   219,   196,   195,     0,
   389,   217,   214,     0,   215,   216,   218,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   204,   172,   206,   189,     0,     0,     0,     0,
   111,   194,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    47,   219,   196,   195,
     0,   390,   217,   214,     0,   215,   216,   218,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   204,   172,   206,   189,     0,     0,     0,
     0,   111,   194,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    47,   219,   196,
   195,     0,   392,   217,   214,     0,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   204,   172,   206,   189,     0,     0,
     0,     0,   111,   194,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,     0,
     0,   195,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   111,   194,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   169,   170,   171,     0,   173,   174,   175,     0,   176,
   177,   178,   179,   180,   181,   182,   183,   184,   185,
   186,   187,   188,     0,   190,   191,   192,   193,   197,
   198,   199,   200,   201,   202,   203,   205,   207,   208,
   209,   210,   211,   212,   213,   220,   221,   222,     0,
   223,   224,   225,   226,     0,     0,     0,     0,     0,
     0,   169,   170,   171,     0,   173,   174,   175,     0,
   176,   177,   178,   179,   180,   181,   182,   183,   184,
   185,   186,   187,   188,     0,   190,   191,   192,   193,
   197,   198,   199,   200,   201,   202,   203,   205,   207,
   208,   209,   210,   211,   212,   213,   220,   221,   222,
     0,   223,   224,   225,   226,     0,     0,     0,     0,
     0,     0,   169,   170,   171,     0,   173,   174,   175,
     0,   176,   177,   178,   179,   180,   181,   182,   183,
   184,   185,   186,   187,   188,     0,   190,   191,   192,
   193,   197,   198,   199,   200,   201,   202,   203,   205,
   207,   208,   209,   210,   211,   212,   213,   220,   221,
   222,     0,   223,   224,   225,   226,     0,     0,     0,
     0,     0,     0,   169,   170,   171,     0,   173,   174,
   175,     0,   176,   177,   178,   179,   180,   181,   182,
   183,   184,   185,   186,   187,   188,     0,   190,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,   219,   196,
     0,     0,     0,   217,   214,     0,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   204,   172,   206,   189,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,   219,
   196,   195,     0,   439,   217,   214,     0,   215,   216,
   218,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   204,   172,   206,   189,     0,
     0,     0,     0,   111,   194,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    47,
   219,   196,   195,     0,   440,   217,   214,     0,   215,
   216,   218,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   204,   172,   206,   189,
     0,     0,     0,     0,   111,   194,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    47,   219,   196,   195,     0,   457,   217,   214,     0,
   215,   216,   218,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   204,   172,   206,
   189,     0,     0,     0,     0,   111,   194,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,     0,   195,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   393,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   111,   194,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   169,   170,   171,     0,   173,   174,
   175,     0,   176,   177,   178,   179,   180,   181,   182,
   183,   184,   185,   186,   187,   188,     0,   190,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,     0,     0,
     0,     0,     0,     0,   169,   170,   171,     0,   173,
   174,   175,     0,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   188,     0,   190,
   191,   192,   193,   197,   198,   199,   200,   201,   202,
   203,   205,   207,   208,   209,   210,   211,   212,   213,
   220,   221,   222,     0,   223,   224,   225,   226,     0,
     0,     0,     0,     0,     0,   169,   170,   171,     0,
   173,   174,   175,     0,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   188,     0,
   190,   191,   192,   193,   197,   198,   199,   200,   201,
   202,   203,   205,   207,   208,   209,   210,   211,   212,
   213,   220,   221,   222,     0,   223,   224,   225,   226,
     0,     0,     0,     0,     0,     0,   169,   170,   171,
     0,   173,   174,   175,     0,   176,   177,   178,   179,
   180,   181,   182,   183,   184,   185,   186,   187,   188,
     0,   190,   191,   192,   193,   197,   198,   199,   200,
   201,   202,   203,   205,   207,   208,   209,   210,   211,
   212,   213,   220,   221,   222,     0,   223,   224,   225,
   226,   219,   196,     0,     0,     0,   217,   214,     0,
   215,   216,   218,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   204,   172,   206,
   189,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   219,   196,     0,
     0,    47,   217,   214,   195,   215,   216,   218,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   204,   172,   265,   189,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   111,   194,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   219,   196,     0,     0,    47,   217,   214,
   195,   215,   216,   218,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   204,   172,
   206,   189,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   111,   194,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   219,   196,
     0,     0,    47,   217,   214,   195,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   204,   172,   206,   189,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   111,   194,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,     0,
     0,   195,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   111,   194,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   169,   170,   171,
     0,   173,   174,   175,     0,   176,   177,   178,   179,
   180,   181,   182,   183,   184,   185,   186,   187,   188,
     0,   190,   191,   192,   193,   197,   198,   199,   200,
   201,   202,   203,   205,   207,   208,   209,   210,   211,
   212,   213,   220,   221,   222,     0,   223,   224,   225,
   226,     0,   169,   170,   171,     0,   173,   174,   175,
     0,   176,   177,   178,   179,   180,   181,   182,   183,
   184,   185,   186,   187,   188,     0,   190,   191,   192,
   193,   197,   198,   199,   200,   201,   202,   203,   205,
   207,   208,   209,   210,   211,   212,   213,   220,   221,
   222,     0,   223,   224,   225,   226,     0,     0,     0,
   171,     0,   173,   174,   175,     0,   176,   177,   178,
   179,   180,   181,   182,   183,   184,   185,   186,   187,
   188,     0,   190,   191,   192,   193,   197,   198,   199,
   200,   201,   202,   203,   205,   207,   208,   209,   210,
   211,   212,   213,   220,   221,   222,     0,   223,   224,
   225,   226,     0,     0,     0,     0,     0,   173,   174,
   175,     0,   176,   177,   178,   179,   180,   181,   182,
   183,   184,   185,   186,   187,   188,     0,   190,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,   219,   196,
     0,     0,     0,   217,   214,     0,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   204,     0,   206,   189,   219,   196,
     0,     0,     0,   217,   214,     0,   215,   216,   218,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   204,     0,   206,     0,    47,   219,
   196,   195,     0,     0,   217,   214,     0,   215,   216,
   218,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   204,     0,   206,    47,     0,
     0,   195,     0,   111,   194,     0,     0,     0,     0,
   219,   196,     0,     0,     0,   217,   214,     0,   215,
   216,   218,     0,     0,     0,     0,     0,     0,    47,
     0,     0,   195,   111,   194,   204,     0,   206,     0,
     0,   219,   196,     0,     0,     0,   217,   214,     0,
   215,   216,   218,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   111,   194,   204,     0,   206,
    47,   219,     0,   195,     0,     0,   217,   214,     0,
   215,   216,   218,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   204,     0,   206,
     0,    47,     0,     0,     0,   111,   194,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,     0,     0,     0,   111,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    44,     0,   111,    44,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    44,    44,     0,    44,     0,    44,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    44,     0,     0,     0,     0,   190,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,    44,   191,
   192,   193,   197,   198,   199,   200,   201,   202,   203,
   205,   207,   208,   209,   210,   211,   212,   213,   220,
   221,   222,     0,   223,   224,   225,   226,     0,     0,
     0,     0,   193,   197,   198,   199,   200,   201,   202,
   203,   205,   207,   208,   209,   210,   211,   212,   213,
   220,   221,   222,     0,   223,   224,   225,   226,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   197,   198,   199,   200,   201,
   202,   203,   205,   207,   208,   209,   210,   211,   212,
   213,   220,   221,   222,     0,   223,   224,   225,   226,
     0,     0,     0,     0,     0,   197,   198,   199,   200,
   201,   202,   203,   205,   207,   208,   209,   210,   211,
   212,   213,   220,   221,   222,     0,   223,   224,   225,
   226,     0,     0,     0,     0,   197,   198,   199,   200,
   201,   202,   203,   205,   207,   208,   209,   210,   211,
   212,   213,   220,   221,   222,     0,   223,   224,   225,
   226,   140,     0,    44,   140,   140,     0,   140,     0,
   140,   140,     0,     0,     0,     0,    44,     0,    44,
    44,     0,    44,   140,   140,    44,   140,   140,   140,
     0,     0,     0,     0,     0,    44,    44,    44,     0,
    44,    44,    44,     0,    44,    44,    44,    44,    44,
    44,    44,    44,    44,    44,    44,    44,    44,     0,
     0,     0,   140,   140,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   182,   182,     0,     0,   182,   182,   182,   182,
   182,   182,   182,     0,     0,     0,   140,   140,     0,
     0,     0,     0,     0,   182,   182,   182,   182,   182,
   182,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   182,   182,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   183,   183,     0,     0,   183,   183,   183,
   183,   183,   183,   183,     0,     0,     0,   182,   182,
     0,     0,     0,     0,     0,   183,   183,   183,   183,
   183,   183,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   183,   183,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   183,
   183,     0,     0,   140,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   140,     0,   140,
   140,     0,   140,     0,     0,   140,     0,     0,     0,
     0,     0,     0,     0,     0,   140,   140,   140,     0,
   140,   140,   140,     0,   140,   140,   140,   140,   140,
   140,   140,   140,   140,   140,   140,   140,   140,     0,
   140,   140,   140,   140,   140,   140,   140,   140,   140,
   140,   140,   140,   140,   140,   140,   140,   140,   140,
   140,   140,   140,   140,   182,   140,     0,     0,   140,
     0,     0,     0,     0,     0,     0,     0,   182,     0,
   182,   182,     0,   182,     0,     0,   182,     0,     0,
     0,     0,     0,     0,     0,     0,   182,   182,   182,
     0,   182,   182,   182,     0,   182,   182,   182,   182,
   182,   182,   182,   182,   182,   182,   182,   182,   182,
     0,   182,   182,   182,   182,   182,   182,   182,   182,
   182,   182,   182,   182,   182,   182,   182,   182,   182,
   182,   182,   182,   182,   182,   183,   182,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   183,
     0,   183,   183,     0,   183,     0,     0,   183,     0,
     0,     0,     0,     0,     0,     0,     0,   183,   183,
   183,     0,   183,   183,   183,     0,   183,   183,   183,
   183,   183,   183,   183,   183,   183,   183,   183,   183,
   183,     0,   183,   183,   183,   183,   183,   183,   183,
   183,   183,   183,   183,   183,   183,   183,   183,   183,
   183,   183,   183,   183,   183,   183,     0,   183,   127,
   127,     0,   127,     0,   127,   127,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   127,   127,
     0,   127,   127,   127,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   127,   127,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   130,   130,     0,   130,     0,   130,   130,     0,     0,
     0,   127,   127,     0,     0,     0,     0,     0,   130,
   130,     0,   130,   130,   130,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   130,   130,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   180,   180,     0,
     0,   180,   180,   180,   180,   180,   180,   180,     0,
     0,     0,   130,   130,     0,     0,     0,     0,     0,
   180,   180,   180,   180,   180,   180,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   180,
   180,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   180,   180,     0,     0,   127,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   127,     0,   127,   127,     0,   127,     0,     0,
   127,     0,     0,     0,     0,     0,     0,     0,     0,
   127,   127,   127,     0,   127,   127,   127,     0,   127,
   127,   127,   127,   127,   127,   127,   127,   127,   127,
   127,   127,   127,     0,   127,   127,   127,   127,   127,
   127,   127,   127,   127,   127,   127,   127,   127,   127,
   127,   127,   127,   127,   127,   127,   127,   127,   130,
   127,     0,     0,   127,     0,     0,     0,     0,     0,
     0,     0,   130,     0,   130,   130,     0,   130,     0,
     0,   130,     0,     0,     0,     0,     0,     0,     0,
     0,   130,   130,   130,     0,   130,   130,   130,     0,
   130,   130,   130,   130,   130,   130,   130,   130,   130,
   130,   130,   130,   130,     0,   130,   130,   130,   130,
   130,   130,   130,   130,   130,   130,   130,   130,   130,
   130,   130,   130,   130,   130,   130,   130,   130,   130,
   180,   130,     0,     0,   130,     0,     0,     0,     0,
     0,     0,     0,   180,     0,   180,   180,     0,   180,
     0,     0,   180,     0,     0,     0,     0,     0,     0,
     0,     0,   180,   180,   180,     0,   180,   180,   180,
     0,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,     0,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   181,   181,     0,     0,   181,   181,   181,   181,
   181,   181,   181,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   181,   181,   181,   181,   181,
   181,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   181,   181,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   123,   123,     0,     0,   123,   123,   123,
   123,   123,   123,   123,     0,     0,     0,   181,   181,
     0,     0,     0,     0,     0,   123,   123,   123,   123,
   123,   123,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   123,   123,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   151,   151,     0,     0,   151,   151,
   151,   151,   151,   151,   151,     0,     0,     0,   123,
   123,     0,     0,     0,     0,     0,   151,   151,   151,
   151,   151,   151,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   151,   151,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   151,   151,     0,     0,   181,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   181,     0,
   181,   181,     0,   181,     0,     0,   181,     0,     0,
     0,     0,     0,     0,     0,     0,   181,   181,   181,
     0,   181,   181,   181,     0,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
     0,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   123,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   123,
     0,   123,   123,     0,   123,     0,     0,   123,     0,
     0,     0,     0,     0,     0,     0,     0,   123,   123,
   123,     0,   123,   123,   123,     0,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,     0,   123,   123,   123,   123,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   151,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   151,     0,   151,   151,     0,   151,     0,     0,   151,
     0,     0,     0,     0,     0,     0,     0,     0,   151,
   151,   151,     0,   151,   151,   151,     0,   151,   151,
   151,   151,   151,   151,   151,   151,   151,   151,   151,
   151,   151,     0,   151,   151,   151,   151,   151,   151,
   151,   151,   151,   151,   151,   151,   151,   151,   151,
   151,   151,   151,   151,   151,   151,   151,   144,   144,
     0,     0,   144,   144,   144,   144,   144,   144,   144,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   144,   144,   144,   144,   144,   144,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   144,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   121,
   121,     0,     0,   121,   121,   121,   121,   121,   121,
   121,     0,     0,     0,   144,   144,     0,     0,     0,
     0,     0,   121,   121,   121,   121,   121,   121,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   121,   121,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   122,   122,     0,     0,   122,   122,   122,   122,   122,
   122,   122,     0,     0,     0,   121,   121,     0,     0,
     0,     0,     0,   122,   122,   122,   122,   122,   122,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   122,   122,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   122,   122,     0,
     0,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   144,     0,   144,   144,     0,
   144,     0,     0,   144,     0,     0,     0,     0,     0,
     0,     0,     0,   144,   144,   144,     0,   144,   144,
   144,     0,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,   144,     0,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   121,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   121,     0,   121,   121,
     0,   121,     0,     0,   121,     0,     0,     0,     0,
     0,     0,     0,     0,   121,   121,   121,     0,   121,
   121,   121,     0,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,   121,   121,   121,     0,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   122,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   122,     0,   122,
   122,     0,   122,     0,     0,   122,     0,     0,     0,
     0,     0,     0,     0,     0,   122,   122,   122,     0,
   122,   122,   122,     0,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,   122,     0,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,   122,   122,   122,   124,   124,     0,     0,   124,
   124,   124,   124,   124,   124,   124,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   124,   124,
   124,   124,   124,   124,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   124,   124,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   176,   176,     0,     0,
   176,   176,   176,   176,   176,   176,   176,     0,     0,
     0,   124,   124,     0,     0,     0,     0,     0,   176,
   176,   176,   176,   176,   176,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   176,   176,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   174,   174,     0,
     0,   174,   174,   174,   174,   174,   174,   174,     0,
     0,     0,   176,   176,     0,     0,     0,     0,     0,
   174,   174,   174,   174,   174,   174,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   174,
   174,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   174,   174,     0,     0,   124,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   124,     0,   124,   124,     0,   124,     0,     0,
   124,     0,     0,     0,     0,     0,     0,     0,     0,
   124,   124,   124,     0,   124,   124,   124,     0,   124,
   124,   124,   124,   124,   124,   124,   124,   124,   124,
   124,   124,   124,     0,   124,   124,   124,   124,   124,
   124,   124,   124,   124,   124,   124,   124,   124,   124,
   124,   124,   124,   124,   124,   124,   124,   124,   176,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   176,     0,   176,   176,     0,   176,     0,
     0,   176,     0,     0,     0,     0,     0,     0,     0,
     0,   176,   176,   176,     0,   176,   176,   176,     0,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,     0,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   174,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   174,     0,   174,   174,     0,   174,
     0,     0,   174,     0,     0,     0,     0,     0,     0,
     0,     0,   174,   174,   174,     0,   174,   174,   174,
     0,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,     0,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   175,
   175,     0,     0,   175,   175,   175,   175,   175,   175,
   175,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   175,   175,   175,   175,   175,   175,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   175,   175,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   179,   179,
     0,     0,   179,   179,   179,   179,   179,   179,   179,
     0,     0,     0,     0,     0,   175,   175,     0,     0,
     0,   179,   179,   179,   179,   179,   179,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   179,   179,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   177,   177,     0,
     0,   177,   177,   177,   177,   177,   177,   177,     0,
     0,     0,     0,     0,   179,   179,     0,     0,     0,
   177,   177,   177,   177,   177,   177,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   177,
   177,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,   177,     0,     0,     0,     0,
     0,     0,   175,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   175,     0,   175,   175,
     0,   175,     0,     0,   175,     0,     0,     0,     0,
     0,     0,     0,     0,   175,   175,   175,     0,   175,
   175,   175,     0,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,     0,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   179,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   179,     0,   179,   179,     0,
   179,     0,     0,   179,     0,     0,     0,     0,     0,
     0,     0,     0,   179,   179,   179,     0,   179,   179,
   179,     0,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,     0,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   177,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,     0,   177,   177,     0,   177,
     0,     0,   177,     0,     0,     0,     0,     0,     0,
     0,     0,   177,   177,   177,     0,   177,   177,   177,
     0,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,     0,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,   172,
     0,     0,   172,     0,   172,   172,   172,   172,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   172,   172,   172,   172,   172,   172,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,};
}
static void yytable1(){
yytable[1] = new short[] {
   172,   172,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   173,     0,     0,
   173,     0,   173,   173,   173,   173,     0,     0,     0,
     0,     0,     0,     0,   172,   172,     0,     0,   173,
   173,   173,   173,   173,   173,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   173,   173,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   178,     0,     0,   178,     0,
   178,   178,   178,   178,     0,     0,     0,     0,     0,
     0,     0,   173,   173,     0,     0,   178,   178,   178,
   178,   178,   178,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   178,   178,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   178,     0,     0,     0,     0,     0,     0,     0,
     0,   172,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   172,     0,   172,   172,     0,
   172,     0,     0,   172,     0,     0,     0,     0,     0,
     0,     0,     0,   172,   172,   172,     0,   172,   172,
   172,     0,   172,   172,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,     0,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   173,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   173,     0,   173,   173,     0,   173,     0,
     0,   173,     0,     0,     0,     0,     0,     0,     0,
     0,   173,   173,   173,     0,   173,   173,   173,     0,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,     0,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   178,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,     0,   178,   178,     0,   178,     0,     0,   178,
     0,     0,     0,     0,     0,     0,     0,     0,   178,
   178,   178,     0,   178,   178,   178,     0,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,     0,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   146,     0,     0,   146,     0,
     0,   146,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   146,   146,   146,
   146,   146,   146,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   146,   146,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   147,     0,     0,   147,     0,     0,   147,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   146,   146,     0,     0,   147,   147,   147,   147,   147,
   147,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   147,   147,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   159,     0,     0,   159,     0,     0,   159,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   147,   147,
     0,     0,   159,   159,     0,   159,     0,   159,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   159,   159,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   159,   159,     0,     0,
     0,     0,     0,     0,     0,     0,   146,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   146,     0,   146,   146,     0,   146,     0,     0,   146,
     0,     0,     0,     0,     0,     0,     0,     0,   146,
   146,   146,     0,   146,   146,   146,     0,   146,   146,
   146,   146,   146,   146,   146,   146,   146,   146,   146,
   146,   146,     0,   146,   146,   146,   146,   146,   146,
   146,   146,   146,   146,   146,   146,   146,   146,   146,
   146,   146,   146,   146,   147,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   147,     0,
   147,   147,     0,   147,     0,     0,   147,     0,     0,
     0,     0,     0,     0,     0,     0,   147,   147,   147,
     0,   147,   147,   147,     0,   147,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   147,   147,   147,
     0,   147,   147,   147,   147,   147,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   147,   147,   147,
   147,   147,   159,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   159,     0,   159,   159,
     0,   159,     0,     0,   159,     0,     0,     0,     0,
     0,     0,     0,     0,   159,   159,   159,     0,   159,
   159,   159,     0,   159,   159,   159,   159,   159,   159,
   159,   159,   159,   159,   159,   159,   159,     0,   159,
   159,   159,   159,   159,   159,   159,   159,   159,   159,
   159,   160,     0,     0,   160,     0,     0,   160,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   160,   160,     0,   160,     0,   160,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   160,   160,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    69,   161,     0,    69,
   161,     0,     0,   161,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    69,    69,   160,   160,   161,
   161,     0,   161,     0,   161,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    69,     0,     0,     0,   161,   161,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    66,   162,     0,    66,   162,     0,     0,   162,
     0,     0,     0,     0,     0,     0,     0,     0,    69,
    66,    66,   161,   161,   162,   162,     0,   162,     0,
   162,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    66,
     0,     0,     0,   162,   162,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    66,     0,     0,   162,   162,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   160,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   160,     0,   160,
   160,     0,   160,     0,     0,   160,     0,     0,     0,
     0,     0,     0,     0,     0,   160,   160,   160,     0,
   160,   160,   160,     0,   160,   160,   160,   160,   160,
   160,   160,   160,   160,   160,   160,   160,   160,     0,
   160,   160,   160,   160,   160,   160,   160,   160,   160,
   160,   160,     0,     0,    69,     0,     0,     0,   161,
     0,     0,     0,     0,     0,     0,     0,    69,     0,
    69,    69,   161,    69,   161,   161,    69,   161,     0,
     0,   161,     0,     0,     0,     0,    69,    69,    69,
     0,   161,   161,   161,     0,   161,   161,   161,     0,
   161,   161,   161,   161,   161,   161,   161,   161,   161,
   161,   161,   161,   161,     0,   161,   161,   161,   161,
   161,   161,   161,   161,   161,   161,   161,     0,     0,
    66,     0,     0,     0,   162,     0,     0,     0,     0,
     0,     0,     0,    66,     0,    66,    66,   162,    66,
   162,   162,    66,   162,     0,     0,   162,     0,     0,
     0,     0,    66,    66,    66,     0,   162,   162,   162,
     0,   162,   162,   162,     0,   162,   162,   162,   162,
   162,   162,   162,   162,   162,   162,   162,   162,   162,
     0,   162,   162,   162,   162,   162,   162,   162,   162,
   162,   162,   162,   166,     0,     0,   166,     0,     0,
   166,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   166,   166,     0,   166,
     0,   166,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   166,   166,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    67,   167,
     0,    67,   167,     0,     0,   167,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    67,    67,   166,
   166,   167,   167,     0,   167,     0,   167,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    67,     0,     0,     0,
   167,   167,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    68,   168,     0,    68,   168,     0,
     0,   168,     0,     0,     0,     0,     0,     0,     0,
     0,    67,    68,    68,   167,   167,   168,   168,     0,
   168,     0,   168,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    68,     0,     0,     0,   168,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    68,     0,     0,
   168,   168,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   166,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   166,
     0,   166,   166,     0,   166,     0,     0,   166,     0,
     0,     0,     0,     0,     0,     0,     0,   166,   166,
   166,     0,   166,   166,   166,     0,   166,   166,   166,
   166,   166,   166,   166,   166,   166,   166,   166,   166,
   166,     0,   166,   166,   166,   166,   166,   166,   166,
   166,   166,   166,   166,     0,     0,    67,     0,     0,
     0,   167,     0,     0,     0,     0,     0,     0,     0,
    67,     0,    67,    67,   167,    67,   167,   167,    67,
   167,     0,     0,   167,     0,     0,     0,     0,    67,
    67,    67,     0,   167,   167,   167,     0,   167,   167,
   167,     0,   167,   167,   167,   167,   167,   167,   167,
   167,   167,   167,   167,   167,   167,     0,   167,   167,
   167,   167,   167,   167,   167,   167,   167,   167,   167,
     0,     0,    68,     0,     0,     0,   168,     0,     0,
     0,     0,     0,     0,     0,    68,     0,    68,    68,
   168,    68,   168,   168,    68,   168,     0,     0,   168,
     0,     0,     0,     0,    68,    68,    68,     0,   168,
   168,   168,     0,   168,   168,   168,     0,   168,   168,
   168,   168,   168,   168,   168,   168,   168,   168,   168,
   168,   168,     0,   168,   168,   168,   168,   168,   168,
   168,   168,   168,   168,   168,   169,     0,     0,   169,
     0,     0,   169,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   169,   169,
     0,   169,     0,   169,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   169,   169,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    65,   143,     0,    65,   143,     0,     0,   143,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    65,
    65,   169,   169,   143,   143,     0,   143,     0,   143,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    65,     0,
     0,     0,   143,   143,   157,     0,     0,   157,     0,
     0,   157,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   157,   157,     0,
   157,     0,   157,    65,     0,     0,   143,   143,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   157,   157,     0,     0,
     0,     0,     0,     0,   125,     0,     0,   125,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   125,   125,     0,   125,     0,     0,
   157,   157,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   169,     0,
     0,     0,   125,     0,     0,     0,     0,     0,     0,
     0,   169,     0,   169,   169,     0,   169,     0,     0,
   169,     0,     0,     0,     0,     0,     0,     0,     0,
   169,   169,   169,     0,   169,   169,   169,   125,   169,
   169,   169,   169,   169,   169,   169,   169,   169,   169,
   169,   169,   169,     0,   169,   169,   169,   169,   169,
   169,   169,   169,   169,   169,   169,     0,     0,    65,
     0,     0,     0,   143,     0,     0,     0,     0,     0,
     0,     0,    65,     0,    65,    65,   143,    65,   143,
   143,    65,   143,     0,     0,   143,     0,     0,     0,
     0,    65,    65,    65,     0,   143,   143,   143,     0,
   143,   143,   143,     0,   143,   143,   143,   143,   143,
   143,   143,   143,   143,   143,   143,   143,   143,     0,
   143,   143,   143,   143,     0,     0,   157,   158,     0,
     0,   158,     0,     0,   158,     0,     0,     0,     0,
   157,     0,   157,   157,     0,   157,     0,     0,   157,
   158,   158,     0,   158,     0,   158,     0,     0,   157,
   157,   157,     0,   157,   157,   157,     0,   157,   157,
   157,   157,   157,   157,   157,   157,   157,   157,   157,
   157,   157,     0,   157,   157,   157,   157,     0,   158,
   158,   164,     0,   125,   164,     0,     0,   164,     0,
     0,     0,     0,     0,     0,     0,   125,     0,   125,
   125,     0,   125,   164,   164,   125,   164,     0,   164,
     0,     0,     0,   158,   158,   125,   125,   125,     0,
   125,   125,   125,     0,   125,   125,   125,   125,   125,
   125,   125,   125,   125,   125,   125,   125,   125,     0,
     0,     0,   164,   164,   165,     0,     0,   165,     0,
     0,   165,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   165,   165,     0,
   165,     0,   165,     0,     0,     0,   164,   164,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   165,   165,   170,     0,
     0,   170,     0,     0,   170,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   170,   170,     0,   170,     0,   170,     0,     0,     0,
   165,   165,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   170,
   170,     0,     0,     0,     0,     0,     0,     0,     0,
   158,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   158,     0,   158,   158,     0,   158,
     0,     0,   158,   170,   170,     0,     0,     0,     0,
     0,     0,   158,   158,   158,     0,   158,   158,   158,
     0,   158,   158,   158,   158,   158,   158,   158,   158,
   158,   158,   158,   158,   158,     0,   158,   158,   158,
   158,     0,     0,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   164,     0,   164,
   164,     0,   164,     0,     0,   164,     0,     0,     0,
     0,     0,     0,     0,     0,   164,   164,   164,     0,
   164,   164,   164,     0,   164,   164,   164,   164,   164,
   164,   164,   164,   164,   164,   164,   164,   164,     0,
   164,   164,   164,   164,     0,     0,   165,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   165,     0,   165,   165,     0,   165,     0,     0,   165,
     0,     0,     0,     0,     0,     0,     0,     0,   165,
   165,   165,     0,   165,   165,   165,     0,   165,   165,
   165,   165,   165,   165,   165,   165,   165,   165,   165,
   165,   165,     0,   165,   165,   165,   165,     0,     0,
   170,   163,     0,     0,   163,     0,     0,   163,     0,
     0,     0,     0,   170,     0,   170,   170,     0,   170,
     0,     0,   170,   163,   163,     0,   163,     0,   163,
     0,     0,   170,   170,   170,     0,   170,   170,   170,
     0,   170,   170,   170,   170,   170,   170,   170,   170,
   170,   170,   170,   170,   170,     0,   170,   170,   170,
   170,     0,   163,   163,   171,     0,     0,   171,     0,
     0,   171,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   171,   171,     0,
   171,     0,   171,     0,     0,     0,   163,   163,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   171,   171,     0,   142,
     0,     0,   142,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   142,   142,
     0,   142,     0,   142,     0,     0,     0,     0,     0,
   171,   171,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   142,   142,     0,
     0,   145,     0,     0,   145,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   145,   145,     0,   145,     0,   145,     0,     0,     0,
     0,   142,   142,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   145,
   145,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   163,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   163,     0,   163,
   163,     0,   163,   145,   145,   163,     0,     0,     0,
     0,     0,     0,     0,     0,   163,   163,   163,     0,
   163,   163,   163,     0,   163,   163,   163,   163,   163,
   163,   163,   163,   163,   163,   163,   163,   163,     0,
   163,   163,   163,   163,     0,     0,   171,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   171,     0,   171,   171,     0,   171,     0,     0,   171,
     0,     0,     0,     0,     0,     0,     0,     0,   171,
   171,   171,     0,   171,   171,   171,     0,   171,   171,
   171,   171,   171,   171,   171,   171,   171,   171,   171,
   171,   171,     0,   171,   171,   171,   171,   142,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   142,     0,   142,   142,     0,   142,     0,     0,
   142,     0,     0,     0,     0,     0,     0,     0,     0,
   142,   142,   142,     0,   142,   142,   142,     0,   142,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,   142,   142,     0,   142,   142,   142,   142,     0,
   145,     0,     0,     0,   150,     0,     0,   150,     0,
     0,     0,     0,   145,     0,   145,   145,     0,   145,
     0,     0,   145,   150,   150,     0,   150,     0,   150,
     0,     0,   145,   145,   145,     0,   145,   145,   145,
     0,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,     0,   145,   145,   145,
   145,     0,   150,     0,     0,   148,     0,     0,   148,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   148,   148,     0,   148,     0,
   148,     0,     0,     0,     0,     0,     0,   150,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   149,     0,   148,   149,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   149,   149,     0,   149,     0,   149,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   148,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   131,     0,   149,
   131,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   131,   131,     0,   131,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   149,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   128,     0,   131,   128,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   128,   128,     0,   128,     0,     0,     0,     0,
     0,     0,     0,   150,     0,     0,     0,     0,     0,
   131,     0,     0,     0,     0,     0,   150,     0,   150,
   150,     0,   150,     0,     0,   150,     0,     0,     0,
   128,     0,     0,     0,     0,   150,   150,   150,     0,
   150,   150,   150,     0,   150,   150,   150,   150,   150,
   150,   150,   150,   150,   150,   150,   150,   150,     0,
   150,   150,   150,   150,   148,   128,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   148,     0,
   148,   148,     0,   148,     0,     0,   148,     0,     0,
     0,     0,     0,     0,     0,     0,   148,   148,   148,
     0,   148,   148,   148,     0,   148,   148,   148,   148,
   148,   148,   148,   148,   148,   148,   148,   148,   148,
   149,   148,   148,   148,     0,     0,     0,     0,     0,
     0,     0,     0,   149,     0,   149,   149,     0,   149,
     0,     0,   149,     0,     0,     0,     0,     0,     0,
     0,     0,   149,   149,   149,     0,   149,   149,   149,
     0,   149,   149,   149,   149,   149,   149,   149,   149,
   149,   149,   149,   149,   149,   131,   149,   149,   149,
     0,     0,     0,     0,     0,     0,     0,     0,   131,
     0,   131,   131,     0,   131,     0,     0,   131,     0,
     0,     0,     0,     0,     0,     0,     0,   131,   131,
   131,     0,   131,   131,   131,     0,   131,   131,   131,
   131,   131,   131,   131,   131,   131,   131,   131,   131,
   131,   128,   156,     0,     0,   156,     0,     0,     0,
     0,     0,     0,     0,   128,     0,   128,   128,     0,
   128,   156,   156,   128,   156,     0,     0,     0,     0,
     0,     0,     0,   128,   128,   128,     0,   128,   128,
   128,     0,   128,   128,   128,   128,   128,   128,   128,
   128,   128,   128,   128,   128,   128,     0,    71,     0,
   156,    71,    72,     0,     0,    72,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    71,    71,     0,
     0,    72,    72,     0,     0,    73,     0,     0,    73,
     0,     0,     0,     0,     0,   156,     0,     0,     0,
     0,     0,     0,     0,    73,    73,     0,     0,    75,
     0,     0,    75,    74,     0,    71,    74,     0,     0,
    72,     0,     0,     0,     0,     0,     0,    75,    75,
     0,     0,    74,    74,     0,     0,     0,   152,     0,
     0,   152,     0,    73,     0,     0,     0,     0,     0,
   155,    71,     0,   155,     0,    72,   152,   152,     0,
     0,     0,     0,     0,     0,     0,    75,     0,   155,
   155,    74,     0,     0,     0,     0,     0,     0,    73,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   152,     0,     0,     0,
     0,     0,    75,     0,     0,     0,    74,   155,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   152,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   155,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   156,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   156,     0,   156,   156,     0,
   156,     0,     0,   156,     0,     0,     0,     0,     0,
     0,     0,     0,   156,   156,   156,     0,   156,   156,
   156,     0,   156,   156,   156,   156,   156,   156,   156,
   156,   156,   156,   156,   156,   156,    71,     0,     0,
     0,    72,     0,     0,     0,     0,     0,     0,     0,
    71,     0,    71,    71,    72,    71,    72,    72,    71,
    72,     0,     0,    72,    73,     0,     0,     0,    71,
    71,    71,     0,    72,    72,    72,     0,    73,     0,
    73,    73,     0,    73,     0,     0,    73,    75,     0,
     0,     0,    74,     0,     0,     0,    73,    73,    73,
     0,    75,     0,    75,    75,    74,    75,    74,    74,
    75,    74,     0,     0,    74,     0,   152,     0,     0,
    75,    75,    75,     0,    74,    74,    74,     0,   155,
   152,     0,   152,   152,     0,   152,     0,     0,   152,
     0,     0,   155,     0,   155,   155,     0,   155,   152,
   152,   155,     0,     0,     0,     0,     0,     0,     0,
     0,   155,   155};
}

 
static short yycheck[][] = new short[2][];
static { yycheck0(); yycheck1();}
static void yycheck0(){
yycheck[0] = new short[] {
    29,    35,    46,    47,    48,    59,    35,    36,    34,
    38,    39,    40,    41,    42,    43,    44,    45,   400,
    49,    59,    59,    59,    59,   385,    91,    54,    55,
    56,   411,    53,    36,    37,   436,   416,     0,   324,
    66,   324,    36,    37,   286,   125,    40,   289,   283,
   270,    87,   324,    92,    36,    37,   276,    96,    40,
   437,   438,   123,   461,    64,   421,    36,    37,   123,
   352,   324,   352,    64,   111,     0,   123,    96,   125,
   103,    19,   273,   274,   324,    64,    24,   123,    91,
    36,    37,   113,    59,    40,    91,    59,    64,   119,
   276,   115,   116,   276,   118,   125,   276,   126,   276,
   129,   130,   131,   146,   133,   276,   135,   136,   137,
   138,    64,    40,   286,   123,   288,   289,   273,   291,
    44,   123,   294,   270,   271,   272,   266,   154,   266,
   276,   157,   266,   159,   292,   293,   161,   162,   163,
   164,   165,   168,   167,   266,   169,   170,   171,   172,
   173,   174,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   188,   189,   190,
   191,   192,   193,   194,   195,   196,   197,   198,   199,
   200,   201,   202,   203,   204,   205,   206,   207,   208,
   209,   210,   211,   212,   213,   214,   215,   216,   217,
   218,   219,   220,   266,   125,   223,   266,   226,    40,
   324,   228,   241,   245,   286,    91,   288,   289,   123,
   291,    40,   324,   294,   240,    40,    40,    21,    22,
    23,   248,    25,    26,   273,   250,   273,   266,   253,
   254,    41,    93,   353,   125,   266,    39,   266,    34,
   264,   123,   265,    96,   266,   266,    59,   266,    40,
   272,   125,    41,    41,   276,   266,   266,   276,   123,
   282,   283,   284,   285,   286,   125,   125,   289,   276,
   291,   292,   293,   294,   295,   296,   297,   298,   299,
   300,   301,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   353,   353,
   353,   353,   283,    37,   349,   361,   351,   346,    42,
   348,   276,   356,   282,    47,   284,    41,   276,   362,
    44,   364,   365,   276,   348,   349,   350,   351,   385,
   371,   373,   374,   386,   351,   376,    59,    54,    55,
   283,   276,   377,   378,   379,   353,   381,   266,   125,
   401,   391,   276,   276,   394,   276,   276,   408,   409,
   410,    59,   123,    91,   276,   276,   123,   125,   421,
   123,   123,   123,   276,    93,   276,   276,   275,    40,
   276,   412,   413,   419,   420,     0,    40,    59,   125,
   125,    41,   125,   125,    44,   431,   432,   125,   123,
   276,   276,    41,    59,   450,    59,   452,    40,   123,
   125,    59,   123,   125,   459,   125,   123,   125,   451,
     0,   454,    33,    34,    59,    36,    37,    38,    39,
    40,   125,    59,    43,    59,    45,    59,    59,    59,
    59,    59,    41,    58,    48,   103,    37,   153,    93,
   146,    59,    60,    -1,    -1,    -1,    64,   345,   346,
    -1,   348,   349,   350,   351,    -1,    -1,    33,    34,
    -1,    36,    37,    38,    39,    40,    -1,    -1,    43,
    -1,    45,    -1,    -1,   125,    -1,    91,    92,    -1,
    -1,    -1,    96,    -1,    37,    -1,    -1,    60,    -1,
    42,    43,    64,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   123,    -1,   125,   126,    -1,    -1,    -1,
    -1,    -1,    91,    92,    -1,    33,    34,    96,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,   246,   247,    -1,    91,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    60,    -1,   123,    -1,
    64,   126,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
   123,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    91,    92,    -1,    -1,    -1,    96,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,   126,
    60,    -1,    62,    -1,   344,   345,   346,    -1,   348,
   349,   350,   351,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
   256,    -1,   294,    -1,    91,    -1,   262,   263,   264,
   265,    -1,    -1,   268,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   277,   278,   279,    -1,    -1,   282,
   283,   284,   285,   286,   287,   288,   289,   290,   291,
   123,    41,   294,   295,   296,   297,   298,   299,   300,
   301,   302,    -1,    -1,    -1,   306,    -1,   267,   268,
   269,    59,    41,    -1,    -1,    44,    -1,    -1,   277,
   278,   279,   280,   281,   282,   324,   284,    -1,    -1,
    -1,    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,   301,   302,    -1,    93,
    -1,   306,    -1,   349,   350,    -1,   352,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    93,   324,    43,    -1,    45,    -1,   268,   269,    -1,
    -1,    -1,    -1,    -1,   125,    -1,   277,   278,   279,
    59,    60,   282,    -1,   284,    64,    -1,    -1,   349,
   350,    -1,   352,   353,    -1,   125,    -1,    41,    -1,
   298,   299,   300,   301,   302,   344,   345,   346,   306,
   348,   349,   350,   351,    -1,    91,    92,    59,    -1,
    -1,    96,    -1,    -1,    -1,    -1,    -1,    -1,   324,
    -1,    -1,    -1,    -1,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,   123,    -1,   125,   126,    93,   349,   350,    -1,
   352,   353,    -1,    -1,    59,    60,    -1,    -1,    37,
    64,    -1,    -1,    -1,    42,    43,    -1,    45,    46,
    47,    33,    34,    -1,    36,    37,    38,    39,    40,
    41,   125,    43,    -1,    45,    -1,    -1,    -1,    -1,
    91,    92,    -1,    -1,    -1,    96,    -1,    -1,    -1,
    -1,    60,    -1,    -1,    -1,    64,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,    91,
   348,   349,   350,   351,    -1,   123,    -1,    -1,   126,
    -1,    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,   274,   294,    -1,   123,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,   123,    -1,   294,   126,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,    -1,   256,
    -1,    -1,    -1,    -1,    -1,   262,   263,   264,   265,
    -1,    -1,   268,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,   285,   286,   287,   288,   289,   290,   291,    -1,
    -1,   294,   295,   296,   297,   298,   299,   300,   301,
   302,    -1,    -1,    -1,   306,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,   324,   291,    -1,    -1,   294,
    -1,    -1,    -1,   256,    -1,    -1,    -1,    -1,    -1,
   262,   263,   264,   265,    -1,    -1,   268,   269,    -1,
    -1,    -1,   349,   350,    -1,   352,   277,   278,   279,
    -1,    -1,   282,    -1,   284,   285,   286,   287,   288,
   289,   290,   291,    -1,    -1,   294,   295,   296,   297,
   298,   299,   300,   301,   302,    -1,    -1,    -1,   306,
    -1,   267,   268,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,   280,   281,   282,   324,
   284,    33,    34,    35,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,   298,   299,   300,   301,
   302,    -1,    -1,    -1,   306,    -1,   349,   350,    -1,
   352,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    41,    -1,   324,    44,    -1,   342,   343,
   344,   345,   346,    -1,   348,   349,   350,   351,    -1,
    -1,    58,    59,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,   349,   350,    -1,   352,    -1,    33,    34,
    -1,    36,    37,    38,    39,    40,    -1,    -1,    43,
    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,   123,    -1,    -1,   126,    -1,    59,    60,    -1,
    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,   125,    -1,    -1,    -1,
    -1,    -1,    91,    92,    -1,    -1,    -1,    96,    -1,
    -1,    60,    -1,    62,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,
    -1,   126,    -1,    58,    59,    91,    92,    -1,    33,
    34,    96,    36,    37,    38,    39,    40,    -1,    -1,
    43,    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,
    -1,   123,    93,    64,   126,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    92,    -1,    -1,   125,    96,
   266,    -1,   268,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
    -1,    -1,   126,    -1,    -1,   298,   299,   300,   301,
   302,   274,    -1,    -1,   306,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,   324,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,    -1,   268,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   277,
   278,   279,   349,   350,   282,   352,   284,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,   301,   302,    -1,    -1,
    -1,   306,   268,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,   324,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,   301,
   302,    -1,    -1,   274,   306,    -1,    -1,    -1,   349,
   350,    -1,   352,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,   324,   294,    -1,   266,    -1,
   268,   269,    -1,    -1,    -1,   303,   304,   305,    -1,
   277,   278,   279,    -1,    -1,   282,    -1,   284,    -1,
    -1,    -1,   349,   350,    -1,   352,    -1,    -1,    -1,
    -1,    -1,    -1,   298,   299,   300,   301,   302,    -1,
    33,    34,   306,    36,    37,    38,    39,    40,    41,
    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   324,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
   349,   350,    -1,   352,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    91,    92,    -1,    33,    34,
    96,    36,    37,    38,    39,    40,    -1,    -1,    43,
    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,
   123,    93,    64,   126,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    92,    93,    -1,   125,    96,    -1,
    -1,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,
    -1,   126,    -1,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,    33,    34,    -1,    36,    37,    38,    39,
    40,    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    60,    -1,   126,    -1,    64,    -1,    -1,
    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    -1,    91,    92,    -1,
    41,    -1,    96,    44,    -1,    -1,    -1,    -1,    -1,
    -1,   268,   269,    -1,    -1,    -1,    -1,    -1,    58,
    59,   277,   278,   279,    -1,    -1,   282,    -1,   284,
    -1,    93,   123,    -1,   125,   126,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,   301,   302,
    -1,    -1,   274,   306,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,   286,   125,   288,   289,
    -1,   291,    -1,   324,   294,    -1,    -1,    -1,   268,
   269,    -1,    -1,    -1,   303,   304,   305,    -1,   277,
   278,   279,    -1,   125,   282,    -1,   284,    -1,    -1,
    -1,   349,   350,    -1,   352,    41,    -1,    -1,    44,
    -1,    -1,   298,   299,   300,   301,   302,    -1,    -1,
   266,   306,   268,   269,    58,    59,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,   324,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,   301,
   302,    -1,    -1,    93,   306,    -1,    -1,    -1,   349,
   350,    -1,   352,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   324,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   268,   269,    -1,    -1,    -1,   125,
    -1,    -1,    -1,   277,   278,   279,    -1,    -1,   282,
    -1,   284,   349,   350,    -1,   352,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
   301,   302,   274,    33,    34,   306,    36,    37,    38,
    39,    40,    -1,    -1,    43,   286,    45,   288,   289,
    -1,   291,    -1,    -1,   294,   324,    -1,    -1,   274,
    -1,    -1,    -1,    60,   303,   304,   305,    64,    -1,
    -1,    -1,   286,    -1,   288,   289,    41,   291,    -1,
    44,   294,    -1,   349,   350,    -1,   352,    -1,    -1,
    -1,   303,   304,   305,    -1,    58,    59,    91,    92,
    -1,    33,    34,    96,    36,    37,    -1,    39,    40,
    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,    93,    64,   126,    -1,    -1,
    -1,    33,    34,    -1,    36,    -1,    -1,    39,    40,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   274,    91,    92,    -1,    -1,
   125,    96,    -1,    -1,    -1,    64,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    41,   123,    -1,    44,   126,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    -1,    -1,   126,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,   125,   268,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   277,   278,   279,    -1,    -1,
   282,    -1,   284,    -1,    -1,    -1,    -1,   123,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,   301,   302,    -1,    -1,   274,   306,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,   324,   294,    -1,
    -1,    -1,   268,   269,    -1,    -1,    -1,   303,   304,
   305,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,    -1,    -1,    -1,   349,   350,    -1,   352,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,   301,
   302,    -1,   268,   269,   306,    -1,    -1,    -1,    -1,
    -1,    -1,   277,   278,   279,    -1,    -1,   282,    -1,
   284,    -1,    -1,    -1,   324,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,   301,
   302,    -1,    -1,    -1,   306,    -1,    -1,    -1,    -1,
    -1,    -1,   349,   350,    -1,   352,    -1,    -1,   274,
    -1,    -1,    -1,    -1,   324,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,    -1,
    -1,   294,    -1,    -1,    -1,   274,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   352,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,   346,    -1,   348,   349,
   350,   351,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    40,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,   123,   124,
   125,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,   123,
   124,   125,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,   125,    -1,    -1,   274,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,   346,   274,   348,   349,
   350,   351,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,   346,   274,   348,
   349,   350,   351,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,    -1,
   348,   349,   350,   351,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    40,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
   123,   124,   125,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,   123,   124,   125,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   123,   124,   125,    -1,    -1,   274,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,   274,
   348,   349,   350,   351,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,    -1,
    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,   346,
   274,   348,   349,   350,   351,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
   346,    -1,   348,   349,   350,   351,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,   123,   124,   125,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,   123,   124,   125,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
   346,   274,   348,   349,   350,   351,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,   274,   348,   349,   350,   351,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,   289,
    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   346,    -1,   348,   349,   350,   351,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    93,    94,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    91,    42,
    43,    94,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    -1,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    37,    38,    94,    -1,    41,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,   123,
   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,   286,    -1,   288,   289,
    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   346,    -1,   348,   349,   350,   351,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,   346,    -1,   348,
   349,   350,   351,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,    -1,
   348,   349,   350,   351,    37,    38,    -1,    -1,    41,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    37,    38,    94,    -1,
    41,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    37,    38,    94,
    -1,    41,    42,    43,    -1,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    91,    37,    38,
    94,    -1,    41,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,    -1,
   348,   349,   350,   351,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,   346,
    -1,   348,   349,   350,   351,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
   346,    -1,   348,   349,   350,   351,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    37,
    38,    94,    -1,    41,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
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
    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   346,    -1,   348,   349,   350,   351,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,   346,    -1,   348,   349,   350,   351,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,    -1,   348,   349,   350,
   351,    37,    38,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    91,    42,    43,    94,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    37,    38,    -1,    -1,    91,    42,    43,
    94,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    -1,    91,    42,    43,    94,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,    -1,   348,   349,   350,
   351,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
   346,    -1,   348,   349,   350,   351,    -1,    -1,    -1,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,   346,    -1,   348,   349,
   350,   351,    -1,    -1,    -1,    -1,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    -1,    62,    63,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    -1,    62,    -1,    91,    37,
    38,    94,    -1,    -1,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    -1,    62,    91,    -1,
    -1,    94,    -1,   123,   124,    -1,    -1,    -1,    -1,
    37,    38,    -1,    -1,    -1,    42,    43,    -1,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,   123,   124,    60,    -1,    62,    -1,
    -1,    37,    38,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,    60,    -1,    62,
    91,    37,    -1,    94,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,
    -1,    91,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    -1,    -1,   123,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,   123,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,   125,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,    -1,   348,   349,   350,   351,    -1,    -1,
    -1,    -1,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   346,    -1,   348,   349,   350,   351,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,   346,    -1,   348,   349,   350,   351,
    -1,    -1,    -1,    -1,    -1,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,    -1,   348,   349,   350,
   351,    -1,    -1,    -1,    -1,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,    -1,   348,   349,   350,
   351,    38,    -1,   274,    41,    42,    -1,    44,    -1,
    46,    47,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    58,    59,   294,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    37,    38,    -1,    -1,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    37,    38,    -1,    -1,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,
   125,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,   346,   274,   348,    -1,    -1,   351,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,   274,   348,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,   346,    -1,   348,    41,
    42,    -1,    44,    -1,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    41,    42,    -1,    44,    -1,    46,    47,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,   274,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,   274,
   348,    -1,    -1,   351,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,    -1,
    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,   346,
   274,   348,    -1,    -1,   351,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
   346,    37,    38,    -1,    -1,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    37,    38,    -1,    -1,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,   274,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   344,   345,   346,   274,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   341,   342,   343,   344,   345,   346,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   344,   345,   346,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    37,    38,    -1,    -1,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   345,   346,   274,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,   289,
    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   346,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   339,   340,   341,   342,
   343,   344,   345,   346,    37,    38,    -1,    -1,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,   274,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,   341,   342,   343,   344,   345,   346,   274,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,    -1,
    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   344,   345,   346,
   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,   289,
    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   344,
   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,    38,
    -1,    -1,    41,    -1,    43,    44,    45,    46,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,};
}
static void yycheck1(){
yycheck[1] = new short[] {
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,    -1,
    41,    -1,    43,    44,    45,    46,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    38,    -1,    -1,    41,    -1,
    43,    44,    45,    46,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,   341,   342,   343,   274,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,    -1,
    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,   341,   342,   343,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    38,    -1,    -1,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    38,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,
    -1,    -1,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,   337,   338,   339,
   340,   341,   342,   343,   274,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,   341,
   342,   343,   274,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,   289,
    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   307,
   308,   309,    -1,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,    -1,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,    38,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    41,    38,    -1,    44,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,   124,   125,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    41,    38,    -1,    44,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,
    58,    59,   124,   125,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   125,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,    -1,    -1,   274,    -1,    -1,    -1,   274,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,
   288,   289,   286,   291,   288,   289,   294,   291,    -1,
    -1,   294,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   303,   304,   305,    -1,   307,   308,   309,    -1,
   311,   312,   313,   314,   315,   316,   317,   318,   319,
   320,   321,   322,   323,    -1,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,    -1,    -1,
   274,    -1,    -1,    -1,   274,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,   286,   291,
   288,   289,   294,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
    -1,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,    38,    -1,    -1,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    41,    38,
    -1,    44,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,   124,
   125,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    41,    38,    -1,    44,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   125,    58,    59,   124,   125,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   274,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,    -1,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,    -1,    -1,   274,    -1,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,   286,   291,   288,   289,   294,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
    -1,    -1,   274,    -1,    -1,    -1,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,   289,
   286,   291,   288,   289,   294,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,   303,   304,   305,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,    38,    -1,    -1,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    41,    38,    -1,    44,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,   124,   125,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    93,    94,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,   125,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   274,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,   125,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,    -1,    -1,   274,
    -1,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   286,    -1,   288,   289,   286,   291,   288,
   289,   294,   291,    -1,    -1,   294,    -1,    -1,    -1,
    -1,   303,   304,   305,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,    -1,    -1,   274,    38,    -1,
    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    58,    59,    -1,    61,    -1,    63,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,    -1,    93,
    94,    38,    -1,   274,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    58,    59,   294,    61,    -1,    63,
    -1,    -1,    -1,   124,   125,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
    -1,    -1,    93,    94,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    38,    -1,
    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    -1,    63,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,    -1,    -1,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,    -1,    -1,
   274,    38,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,    -1,    93,    94,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    -1,    63,    -1,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,   124,   125,   294,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,    -1,    -1,   274,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,
   304,   305,    -1,   307,   308,   309,    -1,   311,   312,
   313,   314,   315,   316,   317,   318,   319,   320,   321,
   322,   323,    -1,   325,   326,   327,   328,   274,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   286,    -1,   288,   289,    -1,   291,    -1,    -1,
   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   303,   304,   305,    -1,   307,   308,   309,    -1,   311,
   312,   313,   314,   315,   316,   317,   318,   319,   320,
   321,   322,   323,    -1,   325,   326,   327,   328,    -1,
   274,    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,    -1,   325,   326,   327,
   328,    -1,    93,    -1,    -1,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    41,    -1,    93,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    -1,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    41,    -1,    93,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    41,    -1,    93,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   274,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,   286,    -1,   288,
   289,    -1,   291,    -1,    -1,   294,    -1,    -1,    -1,
    93,    -1,    -1,    -1,    -1,   303,   304,   305,    -1,
   307,   308,   309,    -1,   311,   312,   313,   314,   315,
   316,   317,   318,   319,   320,   321,   322,   323,    -1,
   325,   326,   327,   328,   274,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,   305,
    -1,   307,   308,   309,    -1,   311,   312,   313,   314,
   315,   316,   317,   318,   319,   320,   321,   322,   323,
   274,   325,   326,   327,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   286,    -1,   288,   289,    -1,   291,
    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   303,   304,   305,    -1,   307,   308,   309,
    -1,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,   274,   325,   326,   327,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   286,
    -1,   288,   289,    -1,   291,    -1,    -1,   294,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   303,   304,
   305,    -1,   307,   308,   309,    -1,   311,   312,   313,
   314,   315,   316,   317,   318,   319,   320,   321,   322,
   323,   274,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    58,    59,   294,    61,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,    -1,    41,    -1,
    93,    44,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    -1,    58,    59,    -1,    -1,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    -1,    41,
    -1,    -1,    44,    41,    -1,    93,    44,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    -1,    58,    59,    -1,    -1,    -1,    41,    -1,
    -1,    44,    -1,    93,    -1,    -1,    -1,    -1,    -1,
    41,   125,    -1,    44,    -1,   125,    58,    59,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,    58,
    59,    93,    -1,    -1,    -1,    -1,    -1,    -1,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,
    -1,    -1,   125,    -1,    -1,    -1,   125,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   286,    -1,   288,   289,    -1,
   291,    -1,    -1,   294,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   303,   304,   305,    -1,   307,   308,
   309,    -1,   311,   312,   313,   314,   315,   316,   317,
   318,   319,   320,   321,   322,   323,   274,    -1,    -1,
    -1,   274,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   286,    -1,   288,   289,   286,   291,   288,   289,   294,
   291,    -1,    -1,   294,   274,    -1,    -1,    -1,   303,
   304,   305,    -1,   303,   304,   305,    -1,   286,    -1,
   288,   289,    -1,   291,    -1,    -1,   294,   274,    -1,
    -1,    -1,   274,    -1,    -1,    -1,   303,   304,   305,
    -1,   286,    -1,   288,   289,   286,   291,   288,   289,
   294,   291,    -1,    -1,   294,    -1,   274,    -1,    -1,
   303,   304,   305,    -1,   303,   304,   305,    -1,   274,
   286,    -1,   288,   289,    -1,   291,    -1,    -1,   294,
    -1,    -1,   286,    -1,   288,   289,    -1,   291,   303,
   304,   294,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   303,   304};
}

 
final static short YYFINAL=1;
final static short YYMAXTOKEN=353;
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
"EXP_SEP","REX_MOD","SEP","STDIN","STDOUT","STDERR","STDOUT_H","STDERR_H","MY",
"SUB","OUR","PACKAGE","WHILE","DO","FOR","UNTIL","USE","IF","ELSIF","ELSE",
"UNLESS","LAST","NEXT","RETURN","Q","QQ","QR","QW","QX","LLOR","LLXOR","LLAND",
"LLNOT","MULTI_IGUAL","DIV_IGUAL","MOD_IGUAL","SUMA_IGUAL","MAS_IGUAL",
"MENOS_IGUAL","DESP_I_IGUAL","DESP_D_IGUAL","AND_IGUAL","OR_IGUAL","XOR_IGUAL",
"POW_IGUAL","LAND_IGUAL","LOR_IGUAL","DLOR_IGUAL","CONCAT_IGUAL","X_IGUAL","ID",
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
"expresion : std",
"expresion : regulares",
"expresion : rango",
"rango : expresion DOS_PUNTOS expresion",
"lista : listaR",
"lista : listaR ','",
"listaR : listaR ',' expresion",
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
"cadenaTextoR : cadenaTextoR EXP_SEP expresion EXP_SEP",
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
"colDec : MY '(' lista ')'",
"colDec : OUR '(' lista ')'",
"coleccion : colParen",
"coleccion : colRef",
"coleccion : colDec",
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
"std : STDIN",
"std : STDOUT",
"std : STDERR",
"lectura : '<' expresion '>'",
"lectura : '<' '>'",
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

//#line 338 "parser.y"

	private List<Simbolo> simbolos;
	private PreParser preParser;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	private Lista args;
	
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
	
	
//#line 3386 "Parser.java"
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
//#line 83 "parser.y"
{yyval=set(new Raiz(add(s(val_peek(0)))));}
break;
case 2:
//#line 85 "parser.y"
{yyval=set(Fuente.addCuerpo(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 3:
//#line 87 "parser.y"
{yyval=set(new Fuente(), false);}
break;
case 4:
//#line 88 "parser.y"
{yyval=set(Fuente.addFuncion(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 5:
//#line 90 "parser.y"
{yyval=set(new FuncionDef(s(val_peek(3)), new Contexto(s(val_peek(2)), s(val_peek(1)), s(val_peek(0)))));}
break;
case 6:
//#line 92 "parser.y"
{yyval=set(new FuncionSub(s(val_peek(1)), s(val_peek(0))));}
break;
case 7:
//#line 94 "parser.y"
{yyval=set(new Cuerpo(add(new AbrirBloque()), s(val_peek(0))), false);}
break;
case 8:
//#line 95 "parser.y"
{yyval=set(Cuerpo.add(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 9:
//#line 97 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 10:
//#line 99 "parser.y"
{yyval=set(new Cuerpo(add(new AbrirBloque())));}
break;
case 11:
//#line 100 "parser.y"
{yyval=val_peek(0);}
break;
case 12:
//#line 102 "parser.y"
{yyval=set(new StcLista(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 13:
//#line 103 "parser.y"
{yyval=set(new StcLista(new Lista(), add(new ModNada()), s(val_peek(0))));}
break;
case 14:
//#line 104 "parser.y"
{yyval=set(new StcBloque(s(val_peek(0))));}
break;
case 15:
//#line 105 "parser.y"
{yyval=set(new StcFlujo(s(val_peek(0))));}
break;
case 16:
//#line 106 "parser.y"
{yyval=set(new StcModulos(s(val_peek(0))));}
break;
case 17:
//#line 107 "parser.y"
{yyval=set(new StcImport(s(val_peek(0))));}
break;
case 18:
//#line 108 "parser.y"
{yyval=set(new StcLineaJava(s(val_peek(0))));}
break;
case 19:
//#line 109 "parser.y"
{yyval=set(new StcComentario(s(val_peek(0))));}
break;
case 20:
//#line 110 "parser.y"
{yyval=set(new StcTipado(s(val_peek(0))));}
break;
case 21:
//#line 111 "parser.y"
{yyval=set(new StcError());}
break;
case 22:
//#line 112 "parser.y"
{yyval=set(new StcError());}
break;
case 23:
//#line 113 "parser.y"
{yyval=set(new StcError());}
break;
case 24:
//#line 115 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 25:
//#line 116 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 26:
//#line 117 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 27:
//#line 118 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 28:
//#line 120 "parser.y"
{yyval=set(new ExpNumero(s(val_peek(0))));}
break;
case 29:
//#line 121 "parser.y"
{yyval=set(new ExpCadena(s(val_peek(0))));}
break;
case 30:
//#line 122 "parser.y"
{yyval=set(new ExpVariable(s(val_peek(0))));}
break;
case 31:
//#line 123 "parser.y"
{yyval=set(new ExpAsignacion(s(val_peek(0))));}
break;
case 32:
//#line 124 "parser.y"
{yyval=set(new ExpBinario(s(val_peek(0))));}
break;
case 33:
//#line 125 "parser.y"
{yyval=set(new ExpAritmetica(s(val_peek(0))));}
break;
case 34:
//#line 126 "parser.y"
{yyval=set(new ExpLogico(s(val_peek(0))));}
break;
case 35:
//#line 127 "parser.y"
{yyval=set(new ExpComparacion(s(val_peek(0))));}
break;
case 36:
//#line 128 "parser.y"
{yyval=set(new ExpColeccion(s(val_peek(0))));}
break;
case 37:
//#line 129 "parser.y"
{yyval=set(new ExpAcceso(s(val_peek(0))));}
break;
case 38:
//#line 130 "parser.y"
{yyval=set(new ExpFuncion(s(val_peek(0))));}
break;
case 39:
//#line 131 "parser.y"
{yyval=set(new ExpFuncion5(s(val_peek(1)), s(val_peek(0))));}
break;
case 40:
//#line 132 "parser.y"
{yyval=set(new ExpLectura(s(val_peek(0))));}
break;
case 41:
//#line 133 "parser.y"
{yyval=set(new ExpStd(s(val_peek(0))));}
break;
case 42:
//#line 134 "parser.y"
{yyval=set(new ExpRegulares(s(val_peek(0))));}
break;
case 43:
//#line 135 "parser.y"
{yyval=set(new ExpRango(s(val_peek(0))));}
break;
case 44:
//#line 137 "parser.y"
{yyval=set(new Rango(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 45:
//#line 139 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 46:
//#line 140 "parser.y"
{yyval=set(s(ParseValLista.add(val_peek(1), s(val_peek(0)))));}
break;
case 47:
//#line 142 "parser.y"
{yyval=ParseValLista.add(val_peek(2), s(val_peek(1)), s(val_peek(0)), args);args=null;}
break;
case 48:
//#line 143 "parser.y"
{yyval=new ParseValLista(new Lista(), s(val_peek(0)), args, simbolos);args=null;}
break;
case 49:
//#line 145 "parser.y"
{yyval=set(new ModNada());}
break;
case 50:
//#line 146 "parser.y"
{yyval=set(new ModIf(s(val_peek(1)), s(val_peek(0))));}
break;
case 51:
//#line 147 "parser.y"
{yyval=set(new ModUnless(s(val_peek(1)), s(val_peek(0))));}
break;
case 52:
//#line 148 "parser.y"
{yyval=set(new ModWhile(s(val_peek(1)), s(val_peek(0))));}
break;
case 53:
//#line 149 "parser.y"
{yyval=set(new ModUntil(s(val_peek(1)), s(val_peek(0))));}
break;
case 54:
//#line 150 "parser.y"
{yyval=set(new ModFor(s(val_peek(1)), s(val_peek(0))));}
break;
case 55:
//#line 152 "parser.y"
{yyval=set(new Next(s(val_peek(1)), s(val_peek(0))));}
break;
case 56:
//#line 153 "parser.y"
{yyval=set(new Last(s(val_peek(1)), s(val_peek(0))));}
break;
case 57:
//#line 154 "parser.y"
{yyval=set(new Return(s(val_peek(1)), s(val_peek(0))));}
break;
case 58:
//#line 155 "parser.y"
{yyval=set(new Return(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 59:
//#line 157 "parser.y"
{yyval=set(new Igual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 60:
//#line 158 "parser.y"
{yyval=set(new MasIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 61:
//#line 159 "parser.y"
{yyval=set(new MenosIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 62:
//#line 160 "parser.y"
{yyval=set(new MultiIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 63:
//#line 161 "parser.y"
{yyval=set(new DivIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 64:
//#line 162 "parser.y"
{yyval=set(new ModIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 65:
//#line 163 "parser.y"
{yyval=set(new PowIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 66:
//#line 164 "parser.y"
{yyval=set(new AndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 67:
//#line 165 "parser.y"
{yyval=set(new OrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 68:
//#line 166 "parser.y"
{yyval=set(new XorIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 69:
//#line 167 "parser.y"
{yyval=set(new DespDIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 70:
//#line 168 "parser.y"
{yyval=set(new DespIIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 71:
//#line 169 "parser.y"
{yyval=set(new LAndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 72:
//#line 170 "parser.y"
{yyval=set(new LOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 73:
//#line 171 "parser.y"
{yyval=set(new DLOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 74:
//#line 172 "parser.y"
{yyval=set(new XIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 75:
//#line 173 "parser.y"
{yyval=set(new ConcatIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 76:
//#line 175 "parser.y"
{yyval=set(new Entero(s(val_peek(0))));}
break;
case 77:
//#line 176 "parser.y"
{yyval=set(new Decimal(s(val_peek(0))));}
break;
case 78:
//#line 178 "parser.y"
{yyval=set(new CadenaSimple(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 79:
//#line 179 "parser.y"
{yyval=set(new CadenaDoble(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 80:
//#line 180 "parser.y"
{yyval=set(new CadenaComando(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 81:
//#line 181 "parser.y"
{yyval=set(new CadenaQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 82:
//#line 182 "parser.y"
{yyval=set(new CadenaQW(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 83:
//#line 183 "parser.y"
{yyval=set(new CadenaQQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 84:
//#line 184 "parser.y"
{yyval=set(new CadenaQR(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 85:
//#line 185 "parser.y"
{yyval=set(new CadenaQX(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 86:
//#line 187 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 87:
//#line 189 "parser.y"
{yyval=set(new CadenaTexto(),false);}
break;
case 88:
//#line 190 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(3)),s(val_peek(1))),false);}
break;
case 89:
//#line 191 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(1)),s(val_peek(0))),false);}
break;
case 90:
//#line 193 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 91:
//#line 194 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 92:
//#line 195 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 93:
//#line 196 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 94:
//#line 197 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 95:
//#line 198 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 96:
//#line 199 "parser.y"
{yyval=set(new VarSigil(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 97:
//#line 200 "parser.y"
{yyval=set(new VarPaqueteSigil(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 98:
//#line 201 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 99:
//#line 202 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 100:
//#line 203 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 101:
//#line 204 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 102:
//#line 205 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 103:
//#line 206 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 104:
//#line 208 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 105:
//#line 209 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 106:
//#line 211 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 107:
//#line 212 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 108:
//#line 214 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 109:
//#line 215 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 110:
//#line 217 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 111:
//#line 218 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 112:
//#line 219 "parser.y"
{yyval=set(new ColLlave(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 113:
//#line 220 "parser.y"
{yyval=set(new ColLlave(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 114:
//#line 222 "parser.y"
{yyval=set(new ColDecMy(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 115:
//#line 223 "parser.y"
{yyval=set(new ColDecOur(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 116:
//#line 225 "parser.y"
{yyval=val_peek(0);}
break;
case 117:
//#line 226 "parser.y"
{yyval=val_peek(0);}
break;
case 118:
//#line 227 "parser.y"
{yyval=val_peek(0);}
break;
case 119:
//#line 229 "parser.y"
{yyval=set(new AccesoCol(s(val_peek(1)),s(val_peek(0))));}
break;
case 120:
//#line 230 "parser.y"
{yyval=set(new AccesoColRef(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 121:
//#line 231 "parser.y"
{yyval=set(new AccesoRefEscalar(s(val_peek(1)),s(val_peek(0))));}
break;
case 122:
//#line 232 "parser.y"
{yyval=set(new AccesoRefArray(s(val_peek(1)),s(val_peek(0))));}
break;
case 123:
//#line 233 "parser.y"
{yyval=set(new AccesoRefMap(s(val_peek(1)),s(val_peek(0))));}
break;
case 124:
//#line 234 "parser.y"
{yyval=set(new AccesoRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 125:
//#line 236 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),add(new ColParentesis(args=add(new Lista(s(val_peek(0))))))));}
break;
case 126:
//#line 237 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),s(val_peek(0))));}
break;
case 127:
//#line 238 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 128:
//#line 239 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(args=new Lista(s(val_peek(0))))))));}
break;
case 129:
//#line 240 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 130:
//#line 241 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(1)),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 131:
//#line 242 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(args=new Lista(s(val_peek(0))))))));}
break;
case 132:
//#line 243 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(4)),s(val_peek(2)),add(new ColParentesis(s(val_peek(3)),add(new Lista(s(val_peek(1)))),s(val_peek(0))))));}
break;
case 133:
//#line 244 "parser.y"
{yyval=set(new FuncionBloque(add(new Paquetes()),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(add(args=new Lista(s(val_peek(0))))))));}
break;
case 134:
//#line 246 "parser.y"
{yyval=set(new HandleOut(s(val_peek(0))));}
break;
case 135:
//#line 247 "parser.y"
{yyval=set(new HandleErr(s(val_peek(0))));}
break;
case 136:
//#line 248 "parser.y"
{yyval=set(new HandleFile(s(val_peek(1)),s(val_peek(0))));}
break;
case 137:
//#line 250 "parser.y"
{yyval=set(new StdIn(s(val_peek(0))));}
break;
case 138:
//#line 251 "parser.y"
{yyval=set(new StdOut(s(val_peek(0))));}
break;
case 139:
//#line 252 "parser.y"
{yyval=set(new StdErr(s(val_peek(0))));}
break;
case 140:
//#line 254 "parser.y"
{yyval=set(new LecturaFile(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 141:
//#line 255 "parser.y"
{yyval=set(new LecturaIn(s(val_peek(1)),s(val_peek(0))));}
break;
case 142:
//#line 257 "parser.y"
{yyval=set(new BinOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 143:
//#line 258 "parser.y"
{yyval=set(new BinAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 144:
//#line 259 "parser.y"
{yyval=set(new BinNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 145:
//#line 260 "parser.y"
{yyval=set(new BinXor(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 146:
//#line 261 "parser.y"
{yyval=set(new BinDespI(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 147:
//#line 262 "parser.y"
{yyval=set(new BinDespD(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 148:
//#line 264 "parser.y"
{yyval=set(new LogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 149:
//#line 265 "parser.y"
{yyval=set(new DLogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 150:
//#line 266 "parser.y"
{yyval=set(new LogAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 151:
//#line 267 "parser.y"
{yyval=set(new LogNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 152:
//#line 268 "parser.y"
{yyval=set(new LogOrBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 153:
//#line 269 "parser.y"
{yyval=set(new LogAndBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 154:
//#line 270 "parser.y"
{yyval=set(new LogNotBajo(s(val_peek(1)),s(val_peek(0))));}
break;
case 155:
//#line 271 "parser.y"
{yyval=set(new LogXorBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 156:
//#line 272 "parser.y"
{yyval=set(new LogTernario(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 157:
//#line 274 "parser.y"
{yyval=set(new CompNumEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 158:
//#line 275 "parser.y"
{yyval=set(new CompNumNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 159:
//#line 276 "parser.y"
{yyval=set(new CompNumLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 160:
//#line 277 "parser.y"
{yyval=set(new CompNumLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 161:
//#line 278 "parser.y"
{yyval=set(new CompNumGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 162:
//#line 279 "parser.y"
{yyval=set(new CompNumGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 163:
//#line 280 "parser.y"
{yyval=set(new CompNumCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 164:
//#line 281 "parser.y"
{yyval=set(new CompStrEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 165:
//#line 282 "parser.y"
{yyval=set(new CompStrNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 166:
//#line 283 "parser.y"
{yyval=set(new CompStrLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 167:
//#line 284 "parser.y"
{yyval=set(new CompStrLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 168:
//#line 285 "parser.y"
{yyval=set(new CompStrGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 169:
//#line 286 "parser.y"
{yyval=set(new CompStrGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 170:
//#line 287 "parser.y"
{yyval=set(new CompStrCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 171:
//#line 288 "parser.y"
{yyval=set(new CompSmart(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 172:
//#line 290 "parser.y"
{yyval=set(new AritSuma(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 173:
//#line 291 "parser.y"
{yyval=set(new AritResta(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 174:
//#line 292 "parser.y"
{yyval=set(new AritMulti(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 175:
//#line 293 "parser.y"
{yyval=set(new AritDiv(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 176:
//#line 294 "parser.y"
{yyval=set(new AritPow(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 177:
//#line 295 "parser.y"
{yyval=set(new AritX(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 178:
//#line 296 "parser.y"
{yyval=set(new AritConcat(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 179:
//#line 297 "parser.y"
{yyval=set(new AritMod(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 180:
//#line 298 "parser.y"
{yyval=set(new AritPositivo(s(val_peek(1)),s(val_peek(0))));}
break;
case 181:
//#line 299 "parser.y"
{yyval=set(new AritNegativo(s(val_peek(1)),s(val_peek(0))));}
break;
case 182:
//#line 300 "parser.y"
{yyval=set(new AritPreIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 183:
//#line 301 "parser.y"
{yyval=set(new AritPreDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 184:
//#line 302 "parser.y"
{yyval=set(new AritPostIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 185:
//#line 303 "parser.y"
{yyval=set(new AritPostDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 186:
//#line 305 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 187:
//#line 306 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 188:
//#line 307 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 189:
//#line 308 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 190:
//#line 309 "parser.y"
{yyval=set(new RegularSubs(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 191:
//#line 310 "parser.y"
{yyval=set(new RegularTrans(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 192:
//#line 312 "parser.y"
{yyval=set(null,false);}
break;
case 193:
//#line 313 "parser.y"
{yyval=val_peek(0);}
break;
case 194:
//#line 315 "parser.y"
{yyval=set(new AbrirBloque());}
break;
case 195:
//#line 317 "parser.y"
{yyval=set(new Lista());}
break;
case 196:
//#line 318 "parser.y"
{yyval=val_peek(0);}
break;
case 197:
//#line 320 "parser.y"
{yyval=set(new BloqueVacio(new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 198:
//#line 321 "parser.y"
{yyval=set(new BloqueWhile(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 199:
//#line 322 "parser.y"
{yyval=set(new BloqueUntil(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 200:
//#line 323 "parser.y"
{yyval=set(new BloqueDoWhile(s(val_peek(9)),s(val_peek(8)),new Contexto(s(val_peek(7)),s(val_peek(6)),s(val_peek(5))),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 201:
//#line 324 "parser.y"
{yyval=set(new BloqueDoUntil(s(val_peek(9)),s(val_peek(8)),new Contexto(s(val_peek(7)),s(val_peek(6)),s(val_peek(5))),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 202:
//#line 325 "parser.y"
{yyval=set(new BloqueFor(s(val_peek(11)),s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 203:
//#line 326 "parser.y"
{yyval=set(new BloqueForeachVar(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 204:
//#line 327 "parser.y"
{yyval=set(new BloqueForeach(s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
case 205:
//#line 328 "parser.y"
{yyval=set(new BloqueIf(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),new Contexto(s(val_peek(3)),s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 206:
//#line 329 "parser.y"
{yyval=set(new BloqueUnless(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),new Contexto(s(val_peek(3)),s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 207:
//#line 331 "parser.y"
{yyval=set(new CondicionalNada());}
break;
case 208:
//#line 332 "parser.y"
{yyval=set(new CondicionalElsif(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),new Contexto(s(val_peek(3)),s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 209:
//#line 333 "parser.y"
{yyval=set(new CondicionalElse(s(val_peek(3)),new Contexto(s(val_peek(2)),s(val_peek(1)),s(val_peek(0)))));}
break;
//#line 4371 "Parser.java"
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
