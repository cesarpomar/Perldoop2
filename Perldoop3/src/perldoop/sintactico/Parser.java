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
import java.util.Iterator;
import perldoop.modelo.Opciones;
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
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.arbol.elsif.*;
import perldoop.modelo.arbol.bloqueelsif.*;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.aritmetica.*;
//#line 53 "Parser.java"




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
public final static short PD_TIPO=257;
public final static short PD_NUM=258;
public final static short PD_VAR=259;
public final static short PD_ARGS=260;
public final static short PD_RETURNS=261;
public final static short COMENTARIO=262;
public final static short DECLARACION_TIPO=263;
public final static short IMPORT_JAVA=264;
public final static short LINEA_JAVA=265;
public final static short ID=266;
public final static short VAR=267;
public final static short SIGIL=268;
public final static short ENTERO=269;
public final static short DECIMAL=270;
public final static short CADENA_SIMPLE=271;
public final static short CADENA_DOBLE=272;
public final static short CADENA_COMANDO=273;
public final static short M_REGEX=274;
public final static short S_REGEX=275;
public final static short Y_REGEX=276;
public final static short STDIN=277;
public final static short STDOUT=278;
public final static short STDERR=279;
public final static short MY=280;
public final static short SUB=281;
public final static short OUR=282;
public final static short PACKAGE=283;
public final static short WHILE=284;
public final static short DO=285;
public final static short FOR=286;
public final static short UNTIL=287;
public final static short IF=288;
public final static short ELSIF=289;
public final static short ELSE=290;
public final static short UNLESS=291;
public final static short LAST=292;
public final static short NEXT=293;
public final static short RETURN=294;
public final static short LLOR=295;
public final static short LLXOR=296;
public final static short LLAND=297;
public final static short LLNOT=298;
public final static short MULTI_IGUAL=299;
public final static short DIV_IGUAL=300;
public final static short MOD_IGUAL=301;
public final static short SUMA_IGUAL=302;
public final static short MAS_IGUAL=303;
public final static short MENOS_IGUAL=304;
public final static short DESP_I_IGUAL=305;
public final static short DESP_D_IGUAL=306;
public final static short AND_IGUAL=307;
public final static short OR_IGUAL=308;
public final static short XOR_IGUAL=309;
public final static short POW_IGUAL=310;
public final static short LAND_IGUAL=311;
public final static short LOR_IGUAL=312;
public final static short CONCAT_IGUAL=313;
public final static short X_IGUAL=314;
public final static short DOS_PUNTOS=315;
public final static short LOR=316;
public final static short SUELO=317;
public final static short LAND=318;
public final static short NUM_EQ=319;
public final static short NUM_NE=320;
public final static short STR_EQ=321;
public final static short STR_NE=322;
public final static short CMP=323;
public final static short CMP_NUM=324;
public final static short SMART_EQ=325;
public final static short NUM_LE=326;
public final static short NUM_GE=327;
public final static short STR_LT=328;
public final static short STR_LE=329;
public final static short STR_GT=330;
public final static short STR_GE=331;
public final static short DESP_I=332;
public final static short DESP_D=333;
public final static short X=334;
public final static short STR_REX=335;
public final static short STR_NO_REX=336;
public final static short UNITARIO=337;
public final static short POW=338;
public final static short MAS_MAS=339;
public final static short MENOS_MENOS=340;
public final static short FLECHA=341;
public final static short AMBITO=342;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    3,    3,    4,    5,    2,    2,    6,    6,
    6,    6,    6,    6,    6,    6,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,    7,    7,
    7,    8,    8,    8,    8,    8,    8,   10,   10,   10,
   10,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   13,   13,   13,
   13,   13,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   24,   24,   11,   11,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   22,   22,   22,   22,
   23,   23,   23,   23,   16,   16,   16,   16,   16,   16,
   18,   18,   18,   18,   18,   18,   18,   18,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   25,    9,    9,
    9,    9,    9,    9,    9,    9,   26,   26,   27,   27,
   27,   28,
};
final static short yylen[] = {                            2,
    1,    2,    0,    2,    4,    2,    0,    2,    3,    1,
    1,    4,    3,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    1,    3,    2,
    1,    0,    2,    2,    2,    2,    2,    2,    2,    2,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    2,    2,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    2,    3,    2,    3,    2,
    3,    2,    3,    2,    3,    4,    4,    4,    4,    5,
    5,    2,    2,    2,    3,    2,    3,    2,    2,    1,
    3,    3,    3,    3,    3,    3,    2,    3,    3,    3,
    3,    3,    2,    3,    3,    2,    3,    5,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    2,    2,    2,    2,    0,    2,    8,
    8,   10,   10,   12,    9,    8,    8,    8,    0,    2,
    5,    8,
};
final static short yydefred[] = {                         7,
    0,    1,    0,    0,   14,   15,    0,   58,   59,   60,
   61,   62,    0,    0,    0,    0,  148,  148,  148,  148,
  148,  148,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,    7,    0,    8,    0,   10,   11,    0,    0,   17,
   18,   19,   20,   21,   22,   23,   24,   25,   26,   28,
    0,   16,   78,    0,    0,    0,    0,    0,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   39,   38,   40,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   80,    0,   82,    0,
   84,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    7,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  146,  147,    0,    0,    0,  148,  148,
  149,    0,   71,    0,   69,   70,   74,    0,   72,   73,
   13,    0,    0,    7,    0,    0,    0,    0,    0,   41,
   76,    0,   79,   81,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,   77,    0,    0,    0,    0,
   29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  102,  103,
  104,  101,    0,    0,    0,    0,    0,    0,    0,  160,
   86,   87,   12,    0,    0,    0,    0,    0,    0,    0,
    0,   75,    5,    0,    0,    0,   89,   88,    0,    7,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   91,
   90,    0,    0,    7,    0,    0,    7,    0,    0,    7,
    7,    7,    0,  161,    0,    0,    0,    0,    0,    7,
    0,    0,    0,    7,  150,    0,    0,  156,    0,    0,
  151,  157,  158,    0,    0,    0,    0,  155,  162,  152,
  153,    7,    0,  154,
};
final static short yydgoto[] = {                          1,
    2,    3,   41,   42,   43,   44,   45,  118,   46,   47,
   48,   49,   50,   51,   52,   53,   54,   55,   56,   57,
   58,   59,   60,   92,   76,   61,  181,  182,
};
final static short yysindex[] = {                         0,
    0,    0,  309,  -49,    0,    0,  211,    0,    0,    0,
    0,    0,  -24, -239,  -19, -238,    0,    0,    0,    0,
    0,    0,  -30,  -29, 1508, 1904, -230, 1904, 1904, 1551,
 1904, 1904, 1904, 1904, 1587, 1630, 1828, 1488, 1871, 1904,
    0,    0,  -81,    0, -282,    0,    0, -223, 3090,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -270,    0,    0, 4933, -220, 1904, -219, -216,    0, -208,
 1904, -207, -206,  -52, -200,   28,  -54, 1943,   30,   31,
   32,    0,    0,    0, 3140, 4933,    0,   19,   19, -269,
   19, -193,   19,   19,  -91,  -91,    0,   39,    0,  -12,
    0,  -43, -269, 1904,   19, -184, -269,   19, -183,   19,
    0,    0, 1904, 1904, 1904, 1904, 1904,   26,  272, 1904,
 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904,
 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904,
 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904,
 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904,
 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904, 1904,
 -252, -188, 1904,    0,    0,  -88, 1904, 1904,    0,    0,
    0, -270,    0,   46,    0,    0,    0,   47,    0,    0,
    0,  -51, 1904,    0, 1587, 3190, 1904, 1904, 1904,    0,
    0, -253,    0,    0,    0,   19, -253, -253,  372, 4535,
 4535, 4535, 4535, 4535,    0,    0, 4933, 4890, 4890, 4933,
    0, 4933, 4933, 4933, 4933, 4933, 4933, 4933, 4933, 4933,
 4933, 4933, 4933, 4933, 4933, 4933, 4933, 3495, 4988, 5025,
 5053, 2000, 2000, 2362,  867,  867,  867,  867,  867,  867,
  867,  188,  188,  188,  188,  188,  188,  188,  188,  284,
  284,  396,  396,  396,  175,  175,  175,  175,    0,    0,
    0,    0,   19, 1904, 1904,   -3,  -34,   54,  -28,    0,
    0,    0,    0, 3550,  411,   55, 3035, 1904, 3605, 3660,
 3965,    0,    0, 1904,    4,  -27,    0,    0, 1904,    0,
  -23, -273,  -22, 1904,   62,  -18,  -17,  -16, 4960,    0,
    0, 4020,  677,    0,   64,   68,    0, 4070,  -14,    0,
    0,    0,  -11,    0,  716, 1904, 1904,  756, 1904,    0,
  795, 1061, 1100,    0,    0, 4125, 4430,    0, 4485, 1140,
    0,    0,    0, 1179,   52,   56,  -10,    0,    0,    0,
    0,    0, 1445,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  114,    0,    0,    0, 5471,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,    0,    0,  -26,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,  826,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 1210,    0, 5829, 5887, 2193,
 5945,    0, 6245, 6303, 5358, 5416,    0,    0,    0,    0,
    0,    0, 2251,    0, 6361,    0, 2309, 6661,    0, 6719,
    0,    0,    0,    0,    0,    0,    0,    0, 5527,    0,
    0,    0,  437,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, 2614,    0,    0,    0, 6777, 2672, 2730,    0,   59,
   60,   61,   63,   66,    0,    0, 1516, 1933, 9404, 1981,
    0, 7683, 8540, 8958,10172,10188,10209,10229,10259,10341,
10470,10490,10517,10533,10553,10576,10603,    0,10110,10062,
 9831, 9737, 9774, 9199, 9236, 9273, 9310, 9347, 9626, 9663,
 9700, 8363, 8405, 8451, 8493, 8781, 8823, 8869, 8911, 8010,
 8067, 7606, 7902, 7959, 7135, 7193, 7491, 7549,    0,    0,
    0,    0, 7077,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   75,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 4585,    0,    0,    0,    0,    0,10141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   79,  443,    0,    0,    0,    0,  746,    0,    0,    0,
  107,   24,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  102,    0,  -13,   57,    0,  -50,    0,
};
final static int YYTABLESIZE=10900;
static short yytable[][] = new short[2][];
static { yytable0(); yytable1();}
static void yytable0(){
yytable[0] = new short[] {
   177,   159,   113,   274,   114,   115,   116,   191,   283,
   117,    62,   315,    67,    65,   316,    31,    66,    72,
    70,   179,   180,    71,   269,   270,   271,   106,   109,
    69,    74,    82,    83,    64,   178,    31,   159,   275,
     7,   159,   159,   159,    68,   159,   112,   119,   159,
    73,   159,   183,   185,    85,    86,   186,    88,    89,
    91,    93,    94,    95,    96,   187,   189,   190,   105,
   108,   110,   159,   192,    31,   193,   194,   197,   198,
   199,   201,   202,    77,    78,    79,    80,    81,   203,
   204,   205,   207,   208,   215,   272,   281,   282,   292,
   297,   298,   159,   159,   299,   300,   303,   310,   311,
    31,   314,   317,   196,   319,   326,   320,   321,   322,
   327,   330,   177,   350,   334,   352,     3,   351,    31,
    32,    35,    37,    36,   111,    33,    75,   159,    34,
   159,   159,   206,    87,     0,     0,   280,     0,     0,
     0,     0,   210,   211,   212,   213,   214,   178,   217,
   218,   219,   220,     0,   222,   223,   224,   225,   226,
   227,   228,   229,   230,   231,   232,   233,   234,   235,
   236,   237,   238,   239,   240,   241,   242,   243,   244,
   245,   246,   247,   248,   249,   250,   251,   252,   253,
   254,   255,   256,   257,   258,   259,   260,   261,   262,
   263,   264,   265,   266,   267,   268,     0,     0,   273,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   284,     0,   287,     0,   289,   290,   291,     0,
   169,     0,     0,     0,     0,   167,   164,     0,   165,
   166,   168,   278,   279,     0,     0,     0,     0,     0,
     0,    31,     0,     0,    38,    30,    27,   176,    35,
     0,     0,    28,     0,    29,   159,    31,     0,    31,
    31,    31,   159,   159,    31,   177,   159,     0,     0,
   159,   159,   159,   159,   159,    39,     0,     0,     0,
   177,     0,   159,   159,   159,   159,   159,   159,   159,
   159,   159,    63,   216,   159,   159,   159,   159,     0,
     0,   178,   159,     0,     0,    36,    40,     0,    31,
     0,     0,    38,    30,    27,   178,    35,     0,     0,
    28,     0,    29,   309,     0,     0,   169,     0,   312,
     0,     0,   167,   164,   318,   165,   166,   168,     0,
     0,    37,     0,    39,    32,     0,     0,   159,   159,
    31,     0,     0,    38,    30,    27,     0,    35,   336,
   337,    28,   339,    29,     0,     0,   173,   174,   175,
   176,     0,     0,    36,    40,     0,     0,     0,     0,
     0,     0,     0,     0,    39,     0,   177,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    37,
     0,     0,    32,     0,    36,    40,     0,     0,     0,
    31,     0,   178,    38,    30,    27,     0,    35,     0,
     0,    28,     0,    29,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    37,   169,     0,    32,    39,     0,   167,     0,     0,
     0,     0,   168,    31,     0,     0,    38,    30,    27,
     0,    35,     0,     0,    28,     0,    29,     0,     0,
     0,     0,     0,     0,    36,    40,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    39,     0,
     7,    30,     0,     8,     9,    10,    11,    12,     0,
     0,   177,     0,     0,     0,    13,     0,    15,     0,
    37,    30,   293,    32,     0,     0,     0,    36,    40,
     0,     0,     0,     0,     0,    26,   171,   172,     0,
   173,   174,   175,   176,     0,     0,   178,   162,   163,
   170,   171,   172,     0,   173,   174,   175,   176,    30,
     0,     0,     0,    37,     0,   302,    32,     7,     0,
     0,     8,     9,    10,    11,    12,     0,     0,     0,
     0,    33,    34,    13,    63,    15,   209,     0,     0,
     0,     0,     0,     0,    30,     0,     0,     4,     0,
     0,     0,     0,    26,     5,     6,     0,     0,     7,
     0,     0,     8,     9,    10,    11,    12,     0,     0,
     0,     0,     0,     0,    13,    14,    15,    16,    17,
    18,    19,    20,    21,     0,     0,    22,    23,    24,
    25,     0,     0,     0,    26,     0,     0,     0,    33,
    34,     0,   216,     0,     0,     0,   170,   171,   172,
     0,   173,   174,   175,   176,     0,     0,     4,     0,
     0,     0,     0,     0,     5,     6,     0,   285,     7,
     0,     0,     8,     9,    10,    11,    12,     0,     0,
    33,    34,     0,     0,    13,     0,    15,    16,    17,
    18,    19,    20,    21,     0,     0,    22,    23,    24,
    25,     4,     0,     0,    26,     0,     0,     5,     6,
     0,     0,     7,     0,     0,     8,     9,    10,    11,
    12,     0,     0,     0,     0,     0,     0,    13,     0,
    15,    16,    17,    18,    19,    20,    21,     0,     0,
    22,    23,    24,    25,     0,     0,     0,    26,    31,
    33,    34,    38,    30,    27,     0,    35,     0,     0,
    28,    30,    29,    30,    30,    30,     0,     0,    30,
     0,   170,   171,   172,     0,   173,   174,   175,   176,
     0,     0,     0,    39,     0,   313,     0,     0,     0,
     0,     0,    31,    33,    34,    38,    30,    27,     0,
    35,   325,     0,    28,   328,    29,     0,   331,   332,
   333,     0,     0,    36,    40,     0,     0,     0,   340,
     0,     0,     0,   344,     0,     0,    39,    98,   100,
   102,     0,     0,     0,     0,     0,    31,     0,     0,
    38,    30,    27,   353,    35,     0,     0,    28,    37,
    29,   324,    32,     0,     0,     0,    36,    40,     0,
     0,     0,   184,     0,     0,     0,     0,   188,     0,
     0,    39,     0,     0,     0,     0,     0,     0,     0,
    31,     0,     0,    38,    30,    27,     0,    35,     0,
     0,    28,    37,    29,   335,    32,     0,     0,     0,
     0,    36,    40,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    39,     0,     0,     0,     0,
     0,     0,    99,    99,     0,   221,    99,     0,     0,
     0,     0,     0,     0,     0,     0,    37,     0,   338,
    32,     0,    99,    99,    36,    40,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   169,     0,     0,     0,     0,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
    37,    99,   341,    32,     0,   276,   277,     0,     0,
   154,     0,   156,     0,     0,     0,     4,     0,     0,
     0,     0,     0,     5,     6,   286,     0,     7,     0,
     0,     8,     9,    10,    11,    12,    99,     0,     0,
     0,     0,     0,    13,   177,    15,    16,    17,    18,
    19,    20,    21,     0,     0,    22,    23,    24,    25,
     4,     0,     0,    26,     0,     0,     5,     6,     0,
     0,     7,     0,     0,     8,     9,    10,    11,    12,
   178,     0,     0,     0,     0,     0,    13,     0,    15,
    16,    17,    18,    19,    20,    21,     0,     0,    22,
    23,    24,    25,     0,     4,     0,    26,     0,    33,
    34,     5,     6,   295,   296,     7,     0,     0,     8,
     9,    10,    11,    12,     0,     0,     0,     0,   305,
     0,    13,     0,    15,    16,    17,    18,    19,    20,
    21,     0,     0,    22,    23,    24,    25,     4,     0,
     0,    26,    33,    34,     5,     6,     0,     0,     7,
     0,     0,     8,     9,    10,    11,    12,     0,     0,
     0,     0,     0,     0,    13,     0,    15,    16,    17,
    18,    19,    20,    21,     0,     0,    22,    23,    24,
    25,     0,     0,     0,    26,    31,    33,    34,    38,
    30,    27,     0,    35,     0,     0,    28,     0,    29,
     0,     0,     0,    99,     0,    99,    99,    99,     0,
     0,    99,     0,     0,     0,    99,    99,    99,     0,
    39,     0,     0,     0,     0,     0,     0,     0,    31,
    33,    34,    38,    30,    27,     0,    35,     0,     0,
    28,     0,    29,     0,     0,     0,     0,     0,     0,
    36,    40,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    39,     0,     0,     0,     0,     0,
     0,     0,     0,    31,     0,     0,    38,    30,    27,
     0,    35,     0,     0,    28,    37,    29,   342,    32,
     0,     0,     0,    36,    40,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,    39,   173,
   174,   175,   176,     0,     0,     0,    31,     0,     0,
    38,    30,    27,     0,    35,     0,     0,    28,    37,
    29,   343,    32,     0,     0,     0,     0,    36,    40,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    39,     0,     0,     0,     0,     0,     0,   116,
   116,     0,     0,   116,     0,     0,     0,     0,     0,
     0,     0,     0,    37,     0,   348,    32,     0,   116,
   116,    36,    40,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    37,   116,   349,
    32,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     4,     0,     0,     0,     0,     0,
     5,     6,     0,     0,     7,     0,     0,     8,     9,
    10,    11,    12,   116,     0,     0,     0,     0,     0,
    13,     0,    15,    16,    17,    18,    19,    20,    21,
     0,     0,    22,    23,    24,    25,     4,     0,     0,
    26,     0,     0,     5,     6,     0,     0,     7,     0,
     0,     8,     9,    10,    11,    12,     0,     0,     0,
     0,     0,     0,    13,     0,    15,    16,    17,    18,
    19,    20,    21,     0,     0,    22,    23,    24,    25,
     0,     4,     0,    26,     0,    33,    34,     5,     6,
     0,     0,     7,     0,     0,     8,     9,    10,    11,
    12,     0,     0,     0,     0,     0,     0,    13,     0,
    15,    16,    17,    18,    19,    20,    21,     0,     0,
    22,    23,    24,    25,     4,     0,     0,    26,    33,
    34,     5,     6,     0,     0,     7,     0,     0,     8,
     9,    10,    11,    12,     0,     0,     0,     0,     0,
     0,    13,     0,    15,    16,    17,    18,    19,    20,
    21,     0,     0,    22,    23,    24,    25,     0,     0,
     0,    26,    31,    33,    34,    38,    30,    27,     0,
    35,     0,     0,    28,     0,    29,     0,     0,     0,
   116,     0,   116,   116,   116,     0,     0,   116,     0,
     0,     0,   116,   116,   116,     0,    39,     0,     0,
     0,     0,     0,     0,     0,     0,    33,    34,     0,
    31,     0,   104,    38,    30,    27,     0,    35,     0,
     0,    28,     0,    29,     0,     0,    36,    40,     0,
     0,     0,    31,     0,     0,    38,    30,    27,     0,
    35,     0,     0,    28,    39,    29,     0,     0,    97,
    97,     0,     0,    97,     0,     0,     0,     0,     0,
     0,    84,    37,     0,   354,    32,    39,     0,    97,
    97,     0,     0,     0,    36,    40,     0,     0,     0,
    31,     0,     0,    38,    30,    27,     0,    35,     0,
     0,    28,     0,    29,     0,     0,    36,    40,     0,
     0,     0,     0,     0,     0,     0,     0,    97,     0,
    37,     0,     0,    32,    39,     0,     0,     0,     0,
    31,     0,     0,    38,    30,    27,     0,    35,    97,
     0,    28,    37,    29,     0,    32,     0,     0,     0,
     0,     0,     0,    97,    36,    40,     0,     0,     0,
     0,     0,     0,     0,    39,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    31,     0,
     0,    38,    30,    27,     0,    35,     0,     0,    28,
    37,    29,     0,    32,    36,    40,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    39,     0,     0,     0,     0,     0,     0,
     4,     0,     0,     0,     0,     0,     5,     6,     0,
    37,     7,     0,    32,     8,     9,    10,    11,    12,
     0,     0,    36,    40,    99,     0,    13,     0,    15,
    16,    17,    18,    19,    20,    21,     0,     0,    22,
    23,    24,    25,     0,     0,     0,    26,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    37,     7,
   103,    32,     8,     9,    10,    11,    12,     0,     0,
     0,     0,     0,     0,    13,     0,    15,     0,     0,
     0,     7,     0,     0,     8,     9,    10,    11,    12,
     0,     0,    33,    34,    26,     0,    13,     0,    15,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    97,     0,    97,    97,    97,     0,    26,    97,     0,
     0,     0,    97,    97,    97,     0,     0,     0,     7,
    90,     0,     8,     9,    10,    11,    12,     0,     0,
    33,    34,     0,     0,    13,     0,    15,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    33,    34,    26,     0,     0,     0,     7,
     0,     0,     8,     9,    10,    11,    12,    31,     0,
     0,    38,    30,    27,    13,    35,    15,     0,    28,
     0,    29,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    26,     0,     0,     0,     0,
    33,    34,    39,     0,     0,     0,     7,     0,     0,
     8,     9,    10,    11,    12,    31,     0,     0,    38,
    30,    27,    13,    35,    15,     0,    28,     0,    29,
     0,     0,    36,    40,     0,     0,     0,     0,     0,
    33,    34,    26,     0,     0,     0,     0,     0,     0,
    39,     0,    31,     0,     0,    38,    30,    27,     0,
    35,     0,     0,    28,     0,    29,     0,    37,     0,
   101,    32,     0,     0,     0,     0,     0,     0,     0,
    36,    40,     0,     0,     0,     0,    39,    33,    34,
     0,     0,   114,   114,     0,    31,   114,     0,    38,
    30,    27,     0,   195,     0,     0,    28,     0,    29,
     0,     0,   114,   114,     0,    37,    36,    40,    32,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    39,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   115,   115,     0,     0,
   115,   114,    37,     0,     0,    32,     0,     0,     0,
    36,    40,     0,   169,   146,   115,   115,     0,   167,
   164,     0,   165,   166,   168,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   114,     0,   154,
     0,   156,     0,     0,     0,    37,     0,     0,    32,
     0,     0,     0,     0,   115,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,     0,     0,     7,     0,     0,
     8,     9,    10,    11,    12,     0,     0,     0,     0,
   115,     0,    13,     0,    15,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   178,
     0,     0,    26,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     7,   107,     0,     8,     9,
    10,    11,    12,     0,     0,     0,     0,     0,     0,
    13,     0,    15,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    33,    34,
    26,     7,     0,     0,     8,     9,    10,    11,    12,
     0,     0,     0,     0,     0,     0,    13,     0,    15,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    26,     0,     0,
     0,     0,     0,     0,     7,    33,    34,     8,     9,
    10,    11,    12,   114,     0,   114,   114,   114,     0,
    13,   114,    15,     0,     0,   114,   114,    65,    65,
     0,    65,    65,    65,    65,    65,    65,    65,    65,
    26,     0,    33,    34,     0,     0,     0,     0,     0,
     0,    65,    65,    65,    65,    65,    65,     0,     0,
     0,     0,     0,     0,     0,     0,   115,     0,   115,
   115,   115,     0,     0,   115,     0,     0,     0,   115,
   115,   115,     0,     0,     0,    33,    34,    65,     0,
    65,    65,    63,    63,     0,    63,    63,    63,    63,
    63,    63,    63,    63,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    63,    63,    63,    63,
    63,    63,     0,    65,    65,    65,   147,   148,   149,
   150,   151,   152,   153,   155,   157,   158,   159,   160,
   161,   162,   163,   170,   171,   172,     0,   173,   174,
   175,   176,    63,     0,    63,    63,    64,    64,     0,
    64,    64,    64,    64,    64,    64,    64,    64,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    64,    64,    64,    64,    64,    64,     0,    63,    63,
    63,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   169,    64,     0,    64,
    64,   167,   164,     0,   165,   166,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   154,     0,   156,     0,     0,     0,     0,     0,
     0,     0,    64,    64,    64,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   177,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    65,     0,    65,    65,    65,     0,     0,
    65,   178,     0,     0,    65,    65,    65,     0,    65,
    65,    65,     0,    65,    65,    65,    65,    65,    65,
    65,    65,    65,    65,    65,    65,    65,    65,     0,
    65,    65,    65,    65,    65,    65,    65,    65,    65,
    65,    65,    65,    65,    65,    65,    65,    65,    65,
    65,     0,    65,    65,    65,    65,    63,     0,    63,
    63,    63,     0,     0,    63,     0,     0,     0,    63,
    63,    63,     0,    63,    63,    63,     0,    63,    63,
    63,    63,    63,    63,    63,    63,    63,    63,    63,
    63,    63,    63,     0,    63,    63,    63,    63,    63,
    63,    63,    63,    63,    63,    63,    63,    63,    63,
    63,    63,    63,    63,    63,     0,    63,    63,    63,
    63,    64,     0,    64,    64,    64,     0,     0,    64,
     0,     0,     0,    64,    64,    64,     0,    64,    64,
    64,     0,    64,    64,    64,    64,    64,    64,    64,
    64,    64,    64,    64,    64,    64,    64,     0,    64,
    64,    64,    64,    64,    64,    64,    64,    64,    64,
    64,    64,    64,    64,    64,    64,    64,    64,    64,
     0,    64,    64,    64,    64,    68,    68,     0,    68,
    68,    68,    68,    68,    68,    68,    68,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    68,
    68,    68,    68,    68,    68,     0,     0,     0,   147,
   148,   149,   150,   151,   152,   153,   155,   157,   158,
   159,   160,   161,   162,   163,   170,   171,   172,     0,
   173,   174,   175,   176,     0,    68,     0,    68,    68,
    66,    66,     0,    66,    66,    66,    66,    66,    66,
    66,    66,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    66,    66,    66,    66,    66,    66,
     0,    68,    68,    68,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    66,     0,    66,    66,    67,    67,     0,    67,    67,
    67,    67,    67,    67,    67,    67,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    67,    67,
    67,    67,    67,    67,     0,    66,    66,    66,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    67,     0,    67,    67,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    67,    67,    67,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    68,     0,    68,    68,    68,     0,     0,    68,     0,
     0,     0,    68,    68,    68,     0,    68,    68,    68,
     0,    68,    68,    68,    68,    68,    68,    68,    68,
    68,    68,    68,    68,    68,    68,     0,    68,    68,
    68,    68,    68,    68,    68,    68,    68,    68,    68,
    68,    68,    68,    68,    68,    68,    68,    68,     0,
    68,    68,    68,    68,    66,     0,    66,    66,    66,
     0,     0,    66,     0,     0,     0,    66,    66,    66,
     0,    66,    66,    66,     0,    66,    66,    66,    66,
    66,    66,    66,    66,    66,    66,    66,    66,    66,
    66,     0,    66,    66,    66,    66,    66,    66,    66,
    66,    66,    66,    66,    66,    66,    66,    66,    66,
    66,    66,    66,     0,    66,    66,    66,    66,    67,
     0,    67,    67,    67,     0,     0,    67,     0,     0,
     0,    67,    67,    67,     0,    67,    67,    67,     0,
    67,    67,    67,    67,    67,    67,    67,    67,    67,
    67,    67,    67,    67,    67,     0,    67,    67,    67,
    67,    67,    67,    67,    67,    67,    67,    67,    67,
    67,    67,    67,    67,    67,    67,    67,     0,    67,
    67,    67,    67,   169,   146,     0,     0,     0,   167,
   164,   123,   165,   166,   168,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   304,   154,
   124,   156,   140,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,   169,   146,   145,     0,     0,
   167,   164,   123,   165,   166,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   154,   124,   156,   140,     0,     0,     0,     0,   178,
   144,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   169,   146,     0,     0,   177,   167,   164,   145,   165,
   166,   168,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   200,   154,   124,   156,   140,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   169,   146,     0,   288,
   177,   167,   164,   145,   165,   166,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   154,   124,   156,   140,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   178,   144,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   177,     0,     0,   145,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   178,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   120,   121,   122,     0,   125,   126,   127,     0,   128,
   129,   130,   131,   132,   133,   134,   135,   136,   137,
   138,   139,   141,   142,     0,   143,   147,   148,   149,
   150,   151,   152,   153,   155,   157,   158,   159,   160,
   161,   162,   163,   170,   171,   172,     0,   173,   174,
   175,   176,     0,     0,     0,     0,     0,     0,     0,
     0,   120,   121,   122,     0,   125,   126,   127,     0,
   128,   129,   130,   131,   132,   133,   134,   135,   136,
   137,   138,   139,   141,   142,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,     0,     0,     0,   120,   121,   122,
     0,   125,   126,   127,     0,   128,   129,   130,   131,
   132,   133,   134,   135,   136,   137,   138,   139,   141,
   142,     0,   143,   147,   148,   149,   150,   151,   152,
   153,   155,   157,   158,   159,   160,   161,   162,   163,
   170,   171,   172,     0,   173,   174,   175,   176,     0,
     0,     0,   120,   121,   122,     0,   125,   126,   127,
     0,   128,   129,   130,   131,   132,   133,   134,   135,
   136,   137,   138,   139,   141,   142,     0,   143,   147,
   148,   149,   150,   151,   152,   153,   155,   157,   158,
   159,   160,   161,   162,   163,   170,   171,   172,     0,
   173,   174,   175,   176,   169,   146,     0,     0,     0,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   294,     0,
   154,   124,   156,   140,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   177,   169,   146,   145,     0,
   301,   167,   164,     0,   165,   166,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   154,   124,   156,   140,     0,     0,     0,     0,
   178,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   177,   169,   146,   145,
     0,   306,   167,   164,     0,   165,   166,   168,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   154,   124,   156,   140,     0,     0,     0,
     0,   178,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   177,   169,   146,
   145,     0,   307,   167,   164,     0,   165,   166,   168,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   154,   124,   156,   140,     0,     0,
     0,     0,   178,   144,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   177,     0,
     0,   145,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   178,   144,     0,     0,     0,     0,
     0,   120,   121,   122,     0,   125,   126,   127,     0,
   128,   129,   130,   131,   132,   133,   134,   135,   136,
   137,   138,   139,   141,   142,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,     0,     0,     0,     0,     0,     0,
     0,     0,   120,   121,   122,     0,   125,   126,   127,
     0,   128,   129,   130,   131,   132,   133,   134,   135,
   136,   137,   138,   139,   141,   142,     0,   143,   147,
   148,   149,   150,   151,   152,   153,   155,   157,   158,
   159,   160,   161,   162,   163,   170,   171,   172,     0,
   173,   174,   175,   176,     0,     0,     0,     0,     0,
     0,     0,     0,   120,   121,   122,     0,   125,   126,
   127,     0,   128,   129,   130,   131,   132,   133,   134,
   135,   136,   137,   138,   139,   141,   142,     0,   143,
   147,   148,   149,   150,   151,   152,   153,   155,   157,
   158,   159,   160,   161,   162,   163,   170,   171,   172,
     0,   173,   174,   175,   176,     0,     0,     0,     0,
     0,     0,     0,     0,   120,   121,   122,     0,   125,
   126,   127,     0,   128,   129,   130,   131,   132,   133,
   134,   135,   136,   137,   138,   139,   141,   142,     0,
   143,   147,   148,   149,   150,   151,   152,   153,   155,
   157,   158,   159,   160,   161,   162,   163,   170,   171,
   172,     0,   173,   174,   175,   176,   169,   146,     0,
     0,   308,   167,   164,     0,   165,   166,   168,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   154,   124,   156,   140,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   177,   169,   146,
   145,     0,   323,   167,   164,     0,   165,   166,   168,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   154,   124,   156,   140,     0,     0,
     0,     0,   178,   144,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   169,   146,     0,     0,   177,   167,
   164,   145,   165,   166,   168,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   329,   154,
   124,   156,   140,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   178,   144,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,   169,   146,   145,     0,   345,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   154,   124,   156,   140,     0,     0,     0,     0,   178,
   144,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   177,     0,     0,   145,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   120,   121,   122,     0,   125,   126,
   127,     0,   128,   129,   130,   131,   132,   133,   134,
   135,   136,   137,   138,   139,   141,   142,     0,   143,
   147,   148,   149,   150,   151,   152,   153,   155,   157,
   158,   159,   160,   161,   162,   163,   170,   171,   172,
     0,   173,   174,   175,   176,     0,     0,     0,     0,
     0,     0,     0,     0,   120,   121,   122,     0,   125,
   126,   127,     0,   128,   129,   130,   131,   132,   133,
   134,   135,   136,   137,   138,   139,   141,   142,     0,
   143,   147,   148,   149,   150,   151,   152,   153,   155,
   157,   158,   159,   160,   161,   162,   163,   170,   171,
   172,     0,   173,   174,   175,   176,     0,     0,     0,
   120,   121,   122,     0,   125,   126,   127,     0,   128,
   129,   130,   131,   132,   133,   134,   135,   136,   137,
   138,   139,   141,   142,     0,   143,   147,   148,   149,
   150,   151,   152,   153,   155,   157,   158,   159,   160,
   161,   162,   163,   170,   171,   172,     0,   173,   174,
   175,   176,     0,     0,     0,     0,     0,     0,     0,
     0,   120,   121,   122,     0,   125,   126,   127,     0,
   128,   129,   130,   131,   132,   133,   134,   135,   136,
   137,   138,   139,   141,   142,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,   169,   146,     0,     0,   346,   167,
   164,     0,   165,   166,   168,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   154,
   124,   156,   140,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   177,   169,   146,   145,     0,   347,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   154,   124,   156,   140,     0,     0,     0,     0,   178,
   144,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   169,   146,     0,     0,   177,   167,   164,   145,   165,
   166,   168,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   154,   124,   156,   140,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    79,    79,     0,    79,
   177,    79,    79,   145,    79,    79,    79,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    79,    79,    79,    79,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   178,   144,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    79,     0,     0,    79,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    79,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   120,   121,   122,     0,   125,   126,   127,     0,   128,
   129,   130,   131,   132,   133,   134,   135,   136,   137,
   138,   139,   141,   142,     0,   143,   147,   148,   149,
   150,   151,   152,   153,   155,   157,   158,   159,   160,
   161,   162,   163,   170,   171,   172,     0,   173,   174,
   175,   176,     0,     0,     0,     0,     0,     0,     0,
     0,   120,   121,   122,     0,   125,   126,   127,     0,
   128,   129,   130,   131,   132,   133,   134,   135,   136,
   137,   138,   139,   141,   142,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,     0,     0,     0,   120,   121,   122,
     0,   125,   126,   127,     0,   128,   129,   130,   131,
   132,   133,   134,   135,   136,   137,   138,   139,   141,
   142,     0,   143,   147,   148,   149,   150,   151,   152,
   153,   155,   157,   158,   159,   160,   161,   162,   163,
   170,   171,   172,     0,   173,   174,   175,   176,     0,
     0,     0,    79,    79,    79,     0,    79,    79,    79,
     0,    79,    79,    79,    79,    79,    79,    79,    79,
    79,    79,    79,    79,    79,    79,     0,    79,    79,
    79,    79,    79,    79,    79,    79,    79,    79,    79,
    79,    79,    79,    79,    79,    79,    79,    79,     0,
    79,    79,    79,    79,   169,   146,     0,     0,     0,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   154,   124,   156,   140,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   169,   146,     0,     0,     0,   167,   164,
     0,   165,   166,   168,   177,     0,     0,   145,     0,
     0,     0,     0,     0,     0,     0,     0,   154,   124,
   156,   140,   169,   146,     0,     0,     0,   167,   164,
     0,   165,   166,   168,     0,     0,     0,     0,     0,
   178,   144,     0,     0,     0,     0,     0,   154,     0,
   156,   140,   177,   169,   146,   145,     0,     0,   167,
   164,     0,   165,   166,   168,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   154,
     0,   156,   177,     0,     0,   145,     0,   178,   144,
     0,     0,     0,     0,   169,   146,     0,     0,     0,
   167,   164,     0,   165,   166,   168,     0,     0,     0,
     0,     0,     0,   177,     0,     0,   145,   178,   144,
   154,     0,   156,     0,     0,   169,   146,     0,     0,
     0,   167,   164,     0,   165,   166,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   178,
   144,   154,     0,   156,   177,     0,     0,   145,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   177,     0,     0,   145,
   178,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   178,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   122,     0,   125,   126,   127,     0,
   128,   129,   130,   131,   132,   133,   134,   135,   136,
   137,   138,   139,   141,   142,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,   125,   126,   127,     0,   128,   129,
   130,   131,   132,   133,   134,   135,   136,   137,   138,
   139,   141,   142,     0,   143,   147,   148,   149,   150,
   151,   152,   153,   155,   157,   158,   159,   160,   161,
   162,   163,   170,   171,   172,     0,   173,   174,   175,
   176,   141,   142,     0,   143,   147,   148,   149,   150,
   151,   152,   153,   155,   157,   158,   159,   160,   161,
   162,   163,   170,   171,   172,     0,   173,   174,   175,
   176,     0,     0,   142,     0,   143,   147,   148,   149,
   150,   151,   152,   153,   155,   157,   158,   159,   160,
   161,   162,   163,   170,   171,   172,     0,   173,   174,
   175,   176,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   143,   147,   148,
   149,   150,   151,   152,   153,   155,   157,   158,   159,
   160,   161,   162,   163,   170,   171,   172,     0,   173,
   174,   175,   176,     0,     0,     0,     0,     0,   147,
   148,   149,   150,   151,   152,   153,   155,   157,   158,
   159,   160,   161,   162,   163,   170,   171,   172,     0,
   173,   174,   175,   176,   144,   144,     0,   144,   144,
   144,   144,   144,   144,   144,   144,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   144,   144,
   144,   144,   144,   144,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   144,   144,   145,
   145,     0,   145,   145,   145,   145,   145,   145,   145,
   145,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   145,   145,   145,   145,   145,   145,     0,
     0,   144,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   145,   145,     0,   100,   100,     0,   100,     0,
   100,   100,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   100,   100,   100,   100,   100,   100,
     0,     0,     0,     0,     0,   145,   145,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   100,   100,     0,     0,    98,    98,     0,
    98,     0,    98,    98,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    98,    98,    98,    98,
    98,    98,     0,     0,     0,     0,   100,   100,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    98,    98,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   144,
     0,   144,   144,   144,     0,     0,   144,     0,    98,
    98,   144,   144,   144,     0,   144,   144,   144,     0,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,     0,   144,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,   144,   144,     0,   144,
     0,     0,     0,   145,     0,   145,   145,   145,     0,
     0,   145,     0,     0,     0,   145,   145,   145,     0,
   145,   145,   145,     0,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
     0,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,     0,   145,   100,     0,   100,   100,   100,
     0,     0,   100,     0,     0,     0,   100,   100,   100,
     0,   100,   100,   100,     0,   100,   100,   100,   100,
   100,   100,   100,   100,   100,   100,   100,   100,   100,
   100,     0,   100,   100,   100,   100,   100,   100,   100,
   100,   100,   100,   100,   100,   100,   100,   100,   100,
   100,   100,   100,     0,   100,     0,    98,   100,    98,
    98,    98,     0,     0,    98,     0,     0,     0,    98,
    98,    98,     0,    98,    98,    98,     0,    98,    98,
    98,    98,    98,    98,    98,    98,    98,    98,    98,
    98,    98,    98,     0,    98,    98,    98,    98,    98,
    98,    98,    98,    98,    98,    98,    98,    98,    98,
    98,    98,    98,    98,    98,     0,    98,   142,   142,
    98,   142,   142,   142,   142,   142,   142,   142,   142,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   142,   142,   142,   142,   142,   142,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   142,   142,   143,   143,     0,   143,   143,   143,   143,
   143,   143,   143,   143,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   143,   143,   143,   143,
   143,   143,     0,     0,   142,   142,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   143,   143,    94,    94,     0,
    94,    94,    94,    94,    94,    94,    94,    94,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    94,    94,    94,    94,    94,    94,     0,     0,   143,
   143,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    94,
    94,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    94,    94,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   142,     0,   142,   142,   142,     0,     0,
   142,     0,     0,     0,   142,   142,   142,     0,   142,
   142,   142,     0,   142,   142,   142,   142,   142,   142,
   142,   142,   142,   142,   142,   142,   142,   142,     0,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,   142,   142,   142,   142,   142,   142,   142,   142,
   142,     0,     0,     0,     0,     0,   143,     0,   143,
   143,   143,     0,     0,   143,     0,     0,     0,   143,
   143,   143,     0,   143,   143,   143,     0,   143,   143,
   143,   143,   143,   143,   143,   143,   143,   143,   143,
   143,   143,   143,     0,   143,   143,   143,   143,   143,
   143,   143,   143,   143,   143,   143,   143,   143,   143,
   143,   143,   143,   143,   143,     0,     0,     0,     0,
     0,    94,     0,    94,    94,    94,     0,     0,    94,
     0,     0,     0,    94,    94,    94,     0,    94,    94,
    94,     0,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,     0,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
   113,   113,     0,   113,   113,   113,   113,   113,   113,
   113,   113,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   113,   113,   113,   113,   113,   113,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   113,   113,   107,   107,     0,   107,   107,
   107,   107,   107,   107,   107,   107,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   107,   107,
   107,   107,   107,   107,     0,     0,   113,   113,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   107,   107,    92,
    92,     0,    92,    92,    92,    92,    92,    92,    92,
    92,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    92,    92,    92,    92,    92,    92,     0,
     0,   107,   107,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    92,    92,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    92,    92,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   113,     0,   113,   113,   113,
     0,     0,   113,     0,     0,     0,   113,   113,   113,
     0,   113,   113,   113,     0,   113,   113,   113,   113,
   113,   113,   113,   113,   113,   113,   113,   113,   113,
   113,     0,   113,   113,   113,   113,   113,   113,   113,
   113,   113,   113,   113,   113,   113,   113,   113,   113,
   113,   113,   113,     0,     0,     0,     0,     0,   107,
     0,   107,   107,   107,     0,     0,   107,     0,     0,
     0,   107,   107,   107,     0,   107,   107,   107,     0,
   107,   107,   107,   107,   107,   107,   107,   107,   107,
   107,   107,   107,   107,   107,     0,   107,   107,   107,
   107,   107,   107,   107,   107,   107,   107,   107,   107,
   107,   107,   107,   107,   107,   107,   107,     0,     0,
     0,     0,     0,    92,     0,    92,    92,    92,     0,
     0,    92,     0,     0,     0,    92,    92,    92,     0,
    92,    92,    92,     0,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
     0,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    93,    93,     0,    93,    93,    93,    93,
    93,    93,    93,    93,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    93,    93,    93,    93,
    93,    93,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    93,    93,    96,    96,     0,
    96,    96,    96,    96,    96,    96,    96,    96,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    96,    96,    96,    96,    96,    96,     0,     0,    93,
    93,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    96,
    96,    95,    95,     0,    95,    95,    95,    95,    95,
    95,    95,    95,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    95,    95,    95,    95,    95,
    95,     0,     0,    96,    96,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    95,    95,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    95,    95,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    93,     0,    93,
    93,    93,     0,     0,    93,     0,     0,     0,    93,
    93,    93,     0,    93,    93,    93,     0,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,     0,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,     0,     0,     0,     0,
     0,    96,     0,    96,    96,    96,     0,     0,    96,
     0,     0,     0,    96,    96,    96,     0,    96,    96,
    96,     0,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,     0,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
     0,     0,     0,     0,     0,    95,     0,    95,    95,
    95,     0,     0,    95,     0,     0,     0,    95,    95,
    95,     0,    95,    95,    95,     0,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,     0,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,   138,   138,     0,   138,   138,
   138,   138,   138,   138,   138,   138,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   138,   138,
   138,   138,   138,   138,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   138,   138,   136,
   136,     0,   136,   136,   136,   136,   136,   136,   136,
   136,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   136,   136,   136,   136,   136,   136,     0,
     0,   138,   138,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   136,   136,   137,   137,     0,   137,   137,   137,
   137,   137,   137,   137,   137,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   137,   137,   137,
   137,   137,   137,     0,     0,   136,   136,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   137,   137,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   137,   137,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   138,
     0,   138,   138,   138,     0,     0,   138,     0,     0,
     0,   138,   138,   138,     0,   138,   138,   138,     0,
   138,   138,   138,   138,   138,   138,   138,   138,   138,
   138,   138,   138,   138,   138,     0,   138,   138,   138,
   138,   138,   138,   138,   138,   138,   138,   138,   138,
   138,   138,   138,   138,   138,   138,   138,     0,     0,
     0,     0,     0,   136,     0,   136,   136,   136,     0,
     0,   136,     0,     0,     0,   136,   136,   136,     0,
   136,   136,   136,     0,   136,   136,   136,   136,   136,
   136,   136,   136,   136,   136,   136,   136,   136,   136,
     0,   136,   136,   136,   136,   136,   136,   136,   136,
   136,   136,   136,   136,   136,   136,   136,   136,   136,
     0,     0,     0,     0,     0,     0,     0,   137,     0,
   137,   137,   137,     0,     0,   137,     0,     0,     0,
   137,   137,   137,     0,   137,   137,   137,     0,   137,
   137,   137,   137,   137,   137,   137,   137,   137,   137,
   137,   137,   137,   137,     0,   137,   137,   137,   137,
   137,   137,   137,   137,   137,   137,   137,   137,   137,
   137,   137,   137,   137,   141,   141,     0,   141,   141,
   141,   141,   141,   141,   141,   141,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   141,   141,
   141,   141,   141,   141,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   141,   141,   139,
   139,     0,   139,   139,   139,   139,   139,   139,   139,
   139,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   139,   139,   139,   139,   139,   139,     0,
     0,   141,   141,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   139,   139,   134,     0,   134,   134,     0,   134,
   134,   134,   134,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   134,   134,   134,   134,
   134,   134,     0,     0,     0,   139,   139,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   134,   134,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    42,    42,     0,     0,    42,     0,     0,   134,
   134,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    42,    42,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   141,
    42,   141,   141,   141,     0,     0,   141,     0,     0,
     0,   141,   141,   141,     0,   141,   141,   141,     0,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
   141,   141,   141,   141,   141,    42,   141,   141,   141,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
   141,   141,   141,   141,   141,     0,     0,     0,     0,
     0,     0,     0,   139,     0,   139,   139,   139,     0,
     0,   139,     0,     0,     0,   139,   139,   139,     0,
   139,   139,   139,     0,   139,   139,   139,   139,   139,
   139,   139,   139,   139,   139,   139,   139,   139,   139,
     0,   139,   139,   139,   139,   139,   139,   139,   139,
   139,   139,   139,   139,   139,   139,   139,   139,   139,
     0,     0,     0,     0,     0,     0,   134,     0,   134,
   134,   134,     0,     0,   134,     0,     0,     0,   134,
   134,   134,     0,   134,   134,   134,     0,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,     0,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   135,     0,   135,   135,     0,   135,   135,
   135,   135,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   135,   135,   135,   135,   135,
   135,     0,    42,     0,    42,    42,    42,     0,     0,
    42,     0,     0,     0,    42,    42,    42,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   135,   135,   140,     0,   140,   140,
     0,   140,   140,   140,   140,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   140,   140,
   140,   140,   140,   140,     0,     0,     0,   135,   135,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   109,     0,   109,   109,   140,   140,   109,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   109,   109,   109,   109,   109,
   109,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   140,   140,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   109,   109,   110,     0,   110,   110,
     0,     0,   110,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   110,   110,
   110,   110,   110,   110,     0,     0,     0,   109,   109,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   110,   110,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   135,     0,   135,   135,
   135,   110,   110,   135,     0,     0,     0,   135,   135,
   135,     0,   135,   135,   135,     0,   135,   135,   135,
   135,   135,   135,   135,   135,   135,   135,   135,   135,
   135,   135,     0,   135,   135,   135,   135,   135,   135,
   135,   135,   135,   135,   135,   135,   135,   135,   135,
   135,     0,     0,     0,     0,     0,     0,     0,   140,
     0,   140,   140,   140,     0,     0,   140,     0,     0,
     0,   140,   140,   140,     0,   140,   140,   140,     0,
   140,   140,   140,   140,   140,   140,   140,   140,   140,
   140,   140,   140,   140,   140,     0,   140,   140,   140,
   140,   140,   140,   140,   140,   140,   140,   140,   140,
   140,   140,   140,   140,     0,   109,     0,   109,   109,
   109,     0,     0,   109,     0,     0,     0,   109,   109,
   109,     0,   109,   109,   109,     0,   109,   109,   109,
   109,   109,   109,   109,   109,   109,   109,   109,   109,
   109,   109,     0,   109,   109,   109,   109,   109,   109,
   109,   109,   109,   109,   109,   109,   109,   109,   109,
   109,     0,     0,     0,     0,     0,     0,     0,   110,
     0,   110,   110,   110,     0,     0,   110,     0,     0,
     0,   110,   110,   110,     0,   110,   110,   110,     0,
   110,   110,   110,   110,   110,   110,   110,   110,   110,
   110,   110,   110,   110,   110,     0,   110,   110,   110,
   110,   110,   110,   110,   110,   110,   110,   110,   110,
   110,   110,   110,   110,   121,     0,   121,   121,     0,
     0,   121,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   121,   121,     0,
   121,     0,   121,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   122,     0,   122,   122,     0,     0,   122,     0,
     0,     0,     0,     0,     0,   121,   121,     0,     0,
     0,     0,     0,   122,   122,     0,   122,     0,   122,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   121,   121,   123,     0,   123,   123,     0,     0,   123,
     0,     0,   122,   122,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   123,   123,     0,   123,     0,
   123,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   122,   122,   124,
     0,   124,   124,     0,     0,   124,     0,     0,     0,
     0,     0,     0,   123,   123,     0,     0,     0,     0,
     0,   124,   124,     0,   124,     0,   124,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   123,   123,
     0,     0,     0,    45,    45,     0,     0,    45,     0,
   124,   124,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    45,    45,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   124,   124,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    45,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   121,     0,
   121,   121,   121,     0,     0,   121,     0,     0,     0,
   121,   121,   121,     0,   121,   121,   121,    45,   121,
   121,   121,   121,   121,   121,   121,   121,   121,   121,
   121,   121,   121,   121,     0,   121,   121,   121,   121,
   121,   121,   121,   121,   122,     0,   122,   122,   122,
     0,     0,   122,     0,     0,     0,   122,   122,   122,
     0,   122,   122,   122,     0,   122,   122,   122,   122,
   122,   122,   122,   122,   122,   122,   122,   122,   122,
   122,     0,   122,   122,   122,   122,   122,   122,   122,
   122,     0,     0,     0,     0,   123,     0,   123,   123,
   123,     0,     0,   123,     0,     0,     0,   123,   123,
   123,     0,   123,   123,   123,     0,   123,   123,   123,
   123,   123,   123,   123,   123,   123,   123,   123,   123,
   123,   123,     0,   123,   123,   123,   123,   123,   123,
   123,   123,   124,     0,   124,   124,   124,     0,     0,
   124,     0,     0,     0,   124,   124,   124,     0,   124,
   124,   124,     0,   124,   124,   124,   124,   124,   124,
   124,   124,   124,   124,   124,   124,   124,   124,     0,
   124,   124,   124,   124,   124,   124,   124,   124,   128,
     0,   128,   128,     0,    45,   128,    45,    45,    45,
     0,     0,    45,     0,     0,     0,    45,    45,    45,
     0,   128,   128,     0,   128,     0,   128,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   129,     0,   129,   129,
     0,     0,   129,     0,     0,     0,     0,     0,     0,
   128,   128,     0,     0,     0,     0,     0,   129,   129,
     0,   129,     0,   129,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   128,   128,   130,     0,   130,
   130,     0,     0,   130,     0,     0,   129,   129,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   130,
   130,     0,   130,     0,   130,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   129,   129,   131,     0,   131,   131,     0,     0,
   131,     0,     0,     0,     0,     0,     0,   130,   130,
     0,     0,     0,     0,     0,   131,   131,     0,   131,
     0,   131,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   130,   130,     0,     0,     0,    46,    46,};
}
static void yytable1(){
yytable[1] = new short[] {
     0,     0,    46,     0,   131,   131,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    46,    46,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   131,
   131,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    46,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   128,     0,   128,   128,   128,     0,     0,
   128,     0,     0,     0,   128,   128,   128,     0,   128,
   128,   128,    46,   128,   128,   128,   128,   128,   128,
   128,   128,   128,   128,   128,   128,   128,   128,     0,
   128,   128,   128,   128,   128,   128,   128,   128,   129,
     0,   129,   129,   129,     0,     0,   129,     0,     0,
     0,   129,   129,   129,     0,   129,   129,   129,     0,
   129,   129,   129,   129,   129,   129,   129,   129,   129,
   129,   129,   129,   129,   129,     0,   129,   129,   129,
   129,   129,   129,   129,   129,     0,     0,     0,     0,
   130,     0,   130,   130,   130,     0,     0,   130,     0,
     0,     0,   130,   130,   130,     0,   130,   130,   130,
     0,   130,   130,   130,   130,   130,   130,   130,   130,
   130,   130,   130,   130,   130,   130,     0,   130,   130,
   130,   130,   130,   130,   130,   130,   131,     0,   131,
   131,   131,     0,     0,   131,     0,     0,     0,   131,
   131,   131,     0,   131,   131,   131,     0,   131,   131,
   131,   131,   131,   131,   131,   131,   131,   131,   131,
   131,   131,   131,     0,   131,   131,   131,   131,   131,
   131,   131,   131,   106,     0,   106,   106,     0,    46,
   106,    46,    46,    46,     0,     0,    46,     0,     0,
     0,    46,    46,    46,     0,   106,   106,     0,   106,
     0,   106,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   119,     0,   119,   119,     0,
     0,   119,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   106,   106,   119,   119,     0,
   119,     0,   119,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   120,     0,   120,   120,
     0,     0,   120,     0,     0,     0,     0,     0,   106,
   106,     0,     0,     0,     0,   119,   119,   120,   120,
     0,   120,     0,   120,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   126,     0,   126,
   126,     0,     0,   126,     0,     0,     0,     0,     0,
   119,   119,     0,     0,     0,     0,   120,   120,   126,
   126,     0,   126,     0,   126,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   127,     0,
   127,   127,     0,     0,   127,     0,     0,     0,     0,
     0,   120,   120,     0,     0,     0,     0,   126,   126,
   127,   127,     0,   127,     0,   127,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   126,   126,     0,     0,     0,     0,   127,
   127,     0,     0,   117,   117,     0,     0,   117,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   117,   117,     0,     0,     0,     0,
     0,     0,     0,   127,   127,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   106,     0,   106,
   106,   106,     0,     0,   106,     0,     0,     0,   106,
   106,   106,   117,   106,   106,   106,     0,   106,   106,
   106,   106,   106,   106,   106,   106,   106,   106,   106,
   106,   106,   106,     0,   106,     0,     0,   119,     0,
   119,   119,   119,     0,     0,   119,     0,   117,     0,
   119,   119,   119,     0,   119,   119,   119,     0,   119,
   119,   119,   119,   119,   119,   119,   119,   119,   119,
   119,   119,   119,   119,     0,   119,     0,     0,   120,
     0,   120,   120,   120,     0,     0,   120,     0,     0,
     0,   120,   120,   120,     0,   120,   120,   120,     0,
   120,   120,   120,   120,   120,   120,   120,   120,   120,
   120,   120,   120,   120,   120,     0,   120,     0,     0,
   126,     0,   126,   126,   126,     0,     0,   126,     0,
     0,     0,   126,   126,   126,     0,   126,   126,   126,
     0,   126,   126,   126,   126,   126,   126,   126,   126,
   126,   126,   126,   126,   126,   126,     0,   126,     0,
     0,   127,     0,   127,   127,   127,     0,     0,   127,
     0,     0,     0,   127,   127,   127,     0,   127,   127,
   127,     0,   127,   127,   127,   127,   127,   127,   127,
   127,   127,   127,   127,   127,   127,   127,   132,   127,
   132,   132,     0,     0,   132,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   132,   132,     0,   132,   117,   132,   117,   117,   117,
     0,     0,   117,     0,     0,     0,   117,   117,   125,
     0,   125,   125,     0,     0,   125,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   132,
   132,   125,   125,     0,   125,     0,   125,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   133,     0,   133,   133,     0,     0,   133,     0,     0,
     0,     0,     0,   132,   132,     0,     0,     0,     0,
   125,   125,   133,   133,     0,   133,     0,   133,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   105,   105,     0,     0,   105,     0,
     0,     0,     0,     0,   125,   125,     0,     0,     0,
     0,   133,   133,   105,   105,     0,   105,     0,   105,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   108,   108,     0,     0,   108,
     0,     0,     0,     0,     0,   133,   133,     0,     0,
     0,     0,   105,   105,   108,   108,     0,   108,     0,
   108,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   105,   105,     0,
     0,     0,     0,   108,   108,     0,     0,   112,   112,
     0,     0,   112,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   112,   112,
     0,   112,     0,   112,     0,     0,     0,   108,   108,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   132,     0,   132,   132,   132,     0,     0,   132,
     0,     0,     0,   132,   132,   132,   112,   132,   132,
   132,     0,   132,   132,   132,   132,   132,   132,   132,
   132,   132,   132,   132,   132,   132,   132,     0,   132,
     0,     0,   125,     0,   125,   125,   125,     0,     0,
   125,     0,   112,     0,   125,   125,   125,     0,   125,
   125,   125,     0,   125,   125,   125,   125,   125,   125,
   125,   125,   125,   125,   125,   125,   125,   125,     0,
   125,     0,     0,   133,     0,   133,   133,   133,     0,
     0,   133,     0,     0,     0,   133,   133,   133,     0,
   133,   133,   133,     0,   133,   133,   133,   133,   133,
   133,   133,   133,   133,   133,   133,   133,   133,   133,
     0,   133,     0,     0,   105,     0,   105,   105,   105,
     0,     0,   105,     0,     0,     0,   105,   105,   105,
     0,   105,   105,   105,     0,   105,   105,   105,   105,
   105,   105,   105,   105,   105,   105,   105,   105,   105,
   105,     0,   105,     0,     0,   108,     0,   108,   108,
   108,     0,     0,   108,     0,     0,     0,   108,   108,
   108,     0,   108,   108,   108,     0,   108,   108,   108,
   108,   108,   108,   108,   108,   108,   108,   108,   108,
   108,   108,     0,   108,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   111,   111,     0,     0,   111,
     0,     0,     0,     0,     0,     0,     0,     0,   112,
     0,   112,   112,   112,   111,   111,   112,   111,     0,
   111,   112,   112,   112,     0,   112,   112,   112,     0,
   112,   112,   112,   112,   112,   112,   112,   112,   112,
   112,   112,   112,   112,   112,     0,   112,    85,    85,
     0,     0,    85,   111,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    85,    85,
     0,    85,     0,    85,     0,     0,     0,     0,     0,
     0,     0,   118,   118,     0,     0,   118,     0,   111,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   118,   118,     0,   118,    85,     0,     0,
     0,     0,     0,     0,     0,     0,    47,    47,     0,
     0,    47,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    43,    43,    47,    47,    43,
     0,   118,    85,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    43,    43,     0,    44,    44,
     0,     0,    44,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    47,   118,    44,    44,
    53,    53,     0,     0,    53,     0,     0,     0,     0,
     0,     0,     0,    43,     0,     0,     0,     0,     0,
    53,    53,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,    52,    52,     0,    44,    52,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    43,
     0,     0,     0,    52,    52,     0,     0,     0,    53,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    44,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   111,     0,   111,   111,
   111,     0,    52,   111,    53,     0,     0,   111,   111,
   111,     0,   111,   111,   111,     0,   111,   111,   111,
   111,   111,   111,   111,   111,   111,   111,   111,   111,
   111,   111,     0,     0,    49,    49,     0,    52,    49,
     0,     0,     0,     0,     0,     0,     0,     0,    85,
     0,    85,    85,    85,    49,    49,    85,     0,     0,
     0,    85,    85,    85,     0,    85,    85,    85,     0,
    85,    85,    85,    85,    85,    85,    85,    85,    85,
    85,    85,    85,   118,     0,   118,   118,   118,     0,
     0,   118,     0,    49,     0,   118,   118,   118,     0,
   118,   118,   118,     0,   118,   118,   118,   118,   118,
   118,   118,   118,   118,   118,   118,   118,    47,     0,
    47,    47,    47,     0,     0,    47,     0,     0,    49,
    47,    47,    47,     0,     0,    43,     0,    43,    43,
    43,     0,     0,    43,     0,     0,     0,    43,    43,
    43,     0,     0,     0,     0,     0,     0,     0,    44,
     0,    44,    44,    44,     0,     0,    44,     0,     0,
     0,    44,    44,    44,     0,     0,     0,    50,    50,
     0,    53,    50,    53,    53,    53,     0,     0,    53,
     0,     0,     0,    53,    53,    53,     0,    50,    50,
    51,    51,     0,     0,    51,     0,     0,     0,     0,
     0,     0,     0,     0,    52,     0,    52,    52,    52,
    51,    51,    52,     0,     0,     0,    52,    52,    52,
    48,    48,     0,     0,    48,     0,    50,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    54,    54,
    48,    48,    54,     0,     0,     0,     0,     0,    51,
     0,     0,     0,     0,     0,     0,     0,    54,    54,
    55,    55,    50,     0,    55,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    48,
    55,    55,     0,     0,    51,    57,    57,     0,     0,
    57,     0,     0,     0,     0,    49,    54,    49,    49,
    49,     0,     0,    49,     0,    57,    57,    49,    49,
    49,     0,     0,     0,    48,    56,    56,     0,    55,
    56,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    54,     0,     0,    56,    56,     0,     0,
     0,     0,     0,     0,    57,     0,     0,     0,     0,
     0,     0,     0,     0,    55,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    56,     0,     0,     0,     0,
    57,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    56,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    50,
     0,    50,    50,    50,     0,     0,    50,     0,     0,
     0,    50,    50,    50,     0,     0,     0,     0,     0,
     0,    51,     0,    51,    51,    51,     0,     0,    51,
     0,     0,     0,    51,    51,    51,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    48,     0,    48,    48,    48,     0,     0,    48,
     0,     0,     0,    48,    48,    48,     0,     0,    54,
     0,    54,    54,    54,     0,     0,    54,     0,     0,
     0,    54,    54,    54,     0,     0,     0,     0,     0,
     0,    55,     0,    55,    55,    55,     0,     0,    55,
     0,     0,     0,    55,    55,    55,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    57,     0,    57,
    57,    57,     0,     0,    57,     0,     0,     0,    57,
    57,    57,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    56,     0,    56,
    56,    56,     0,     0,    56,     0,     0,     0,    56,
    56,    56};
}

 
static short yycheck[][] = new short[2][];
static { yycheck0(); yycheck1();}
static void yycheck0(){
yycheck[0] = new short[] {
    91,     0,   284,    91,   286,   287,   288,    59,    59,
   291,    59,   284,    36,    37,   287,    41,    40,    36,
    37,   289,   290,    40,   274,   275,   276,    38,    39,
   266,   266,    59,    59,     7,   123,    59,    33,   123,
   266,    36,    37,    38,    64,    40,   123,   266,    43,
    64,    45,   267,   267,    25,    26,   267,    28,    29,
    30,    31,    32,    33,    34,   267,   267,   267,    38,
    39,    40,    64,   266,    93,    40,   123,    40,    40,
    40,   342,   267,    18,    19,    20,    21,    22,    41,
    93,   125,   267,   267,    59,   274,    41,    41,   342,
    93,   125,    91,    92,    40,   123,    41,    93,   125,
   125,   123,   123,    78,    41,    40,   123,   123,   123,
    40,   123,    91,    59,   123,   123,     0,    59,    41,
    59,    59,    59,    59,    42,    59,    16,   123,    59,
   125,   126,   104,    27,    -1,    -1,   182,    -1,    -1,
    -1,    -1,   113,   114,   115,   116,   117,   123,   119,
   120,   121,   122,    -1,   124,   125,   126,   127,   128,
   129,   130,   131,   132,   133,   134,   135,   136,   137,
   138,   139,   140,   141,   142,   143,   144,   145,   146,
   147,   148,   149,   150,   151,   152,   153,   154,   155,
   156,   157,   158,   159,   160,   161,   162,   163,   164,
   165,   166,   167,   168,   169,   170,    -1,    -1,   173,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   193,    -1,   195,    -1,   197,   198,   199,    -1,
    37,    -1,    -1,    -1,    -1,    42,    43,    -1,    45,
    46,    47,   179,   180,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    -1,    -1,    36,    37,    38,   341,    40,
    -1,    -1,    43,    -1,    45,   256,   284,    -1,   286,
   287,   288,   262,   263,   291,    91,   266,    -1,    -1,
   269,   270,   271,   272,   273,    64,    -1,    -1,    -1,
    91,    -1,   280,   281,   282,   283,   284,   285,   286,
   287,   288,   342,   342,   291,   292,   293,   294,    -1,
    -1,   123,   298,    -1,    -1,    91,    92,    -1,    33,
    -1,    -1,    36,    37,    38,   123,    40,    -1,    -1,
    43,    -1,    45,   294,    -1,    -1,    37,    -1,   299,
    -1,    -1,    42,    43,   304,    45,    46,    47,    -1,
    -1,   123,    -1,    64,   126,    -1,    -1,   339,   340,
    33,    -1,    -1,    36,    37,    38,    -1,    40,   326,
   327,    43,   329,    45,    -1,    -1,   338,   339,   340,
   341,    -1,    -1,    91,    92,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    64,    -1,    91,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
    -1,    -1,   126,    -1,    91,    92,    -1,    -1,    -1,
    33,    -1,   123,    36,    37,    38,    -1,    40,    -1,
    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,    37,    -1,   126,    64,    -1,    42,    -1,    -1,
    -1,    -1,    47,    33,    -1,    -1,    36,    37,    38,
    -1,    40,    -1,    -1,    43,    -1,    45,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    64,    -1,
   266,    41,    -1,   269,   270,   271,   272,   273,    -1,
    -1,    91,    -1,    -1,    -1,   280,    -1,   282,    -1,
   123,    59,   125,   126,    -1,    -1,    -1,    91,    92,
    -1,    -1,    -1,    -1,    -1,   298,   335,   336,    -1,
   338,   339,   340,   341,    -1,    -1,   123,   332,   333,
   334,   335,   336,    -1,   338,   339,   340,   341,    93,
    -1,    -1,    -1,   123,    -1,   125,   126,   266,    -1,
    -1,   269,   270,   271,   272,   273,    -1,    -1,    -1,
    -1,   339,   340,   280,   342,   282,   112,    -1,    -1,
    -1,    -1,    -1,    -1,   125,    -1,    -1,   256,    -1,
    -1,    -1,    -1,   298,   262,   263,    -1,    -1,   266,
    -1,    -1,   269,   270,   271,   272,   273,    -1,    -1,
    -1,    -1,    -1,    -1,   280,   281,   282,   283,   284,
   285,   286,   287,   288,    -1,    -1,   291,   292,   293,
   294,    -1,    -1,    -1,   298,    -1,    -1,    -1,   339,
   340,    -1,   342,    -1,    -1,    -1,   334,   335,   336,
    -1,   338,   339,   340,   341,    -1,    -1,   256,    -1,
    -1,    -1,    -1,    -1,   262,   263,    -1,   194,   266,
    -1,    -1,   269,   270,   271,   272,   273,    -1,    -1,
   339,   340,    -1,    -1,   280,    -1,   282,   283,   284,
   285,   286,   287,   288,    -1,    -1,   291,   292,   293,
   294,   256,    -1,    -1,   298,    -1,    -1,   262,   263,
    -1,    -1,   266,    -1,    -1,   269,   270,   271,   272,
   273,    -1,    -1,    -1,    -1,    -1,    -1,   280,    -1,
   282,   283,   284,   285,   286,   287,   288,    -1,    -1,
   291,   292,   293,   294,    -1,    -1,    -1,   298,    33,
   339,   340,    36,    37,    38,    -1,    40,    -1,    -1,
    43,   284,    45,   286,   287,   288,    -1,    -1,   291,
    -1,   334,   335,   336,    -1,   338,   339,   340,   341,
    -1,    -1,    -1,    64,    -1,   300,    -1,    -1,    -1,
    -1,    -1,    33,   339,   340,    36,    37,    38,    -1,
    40,   314,    -1,    43,   317,    45,    -1,   320,   321,
   322,    -1,    -1,    91,    92,    -1,    -1,    -1,   330,
    -1,    -1,    -1,   334,    -1,    -1,    64,    35,    36,
    37,    -1,    -1,    -1,    -1,    -1,    33,    -1,    -1,
    36,    37,    38,   352,    40,    -1,    -1,    43,   123,
    45,   125,   126,    -1,    -1,    -1,    91,    92,    -1,
    -1,    -1,    66,    -1,    -1,    -1,    -1,    71,    -1,
    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    33,    -1,    -1,    36,    37,    38,    -1,    40,    -1,
    -1,    43,   123,    45,   125,   126,    -1,    -1,    -1,
    -1,    91,    92,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    40,    41,    -1,   123,    44,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,   125,
   126,    -1,    58,    59,    91,    92,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    -1,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
   123,    93,   125,   126,    -1,   177,   178,    -1,    -1,
    60,    -1,    62,    -1,    -1,    -1,   256,    -1,    -1,
    -1,    -1,    -1,   262,   263,   195,    -1,   266,    -1,
    -1,   269,   270,   271,   272,   273,   125,    -1,    -1,
    -1,    -1,    -1,   280,    91,   282,   283,   284,   285,
   286,   287,   288,    -1,    -1,   291,   292,   293,   294,
   256,    -1,    -1,   298,    -1,    -1,   262,   263,    -1,
    -1,   266,    -1,    -1,   269,   270,   271,   272,   273,
   123,    -1,    -1,    -1,    -1,    -1,   280,    -1,   282,
   283,   284,   285,   286,   287,   288,    -1,    -1,   291,
   292,   293,   294,    -1,   256,    -1,   298,    -1,   339,
   340,   262,   263,   274,   275,   266,    -1,    -1,   269,
   270,   271,   272,   273,    -1,    -1,    -1,    -1,   288,
    -1,   280,    -1,   282,   283,   284,   285,   286,   287,
   288,    -1,    -1,   291,   292,   293,   294,   256,    -1,
    -1,   298,   339,   340,   262,   263,    -1,    -1,   266,
    -1,    -1,   269,   270,   271,   272,   273,    -1,    -1,
    -1,    -1,    -1,    -1,   280,    -1,   282,   283,   284,
   285,   286,   287,   288,    -1,    -1,   291,   292,   293,
   294,    -1,    -1,    -1,   298,    33,   339,   340,    36,
    37,    38,    -1,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    33,
   339,   340,    36,    37,    38,    -1,    40,    -1,    -1,
    43,    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    92,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    33,    -1,    -1,    36,    37,    38,
    -1,    40,    -1,    -1,    43,   123,    45,   125,   126,
    -1,    -1,    -1,    91,    92,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    64,   338,
   339,   340,   341,    -1,    -1,    -1,    33,    -1,    -1,
    36,    37,    38,    -1,    40,    -1,    -1,    43,   123,
    45,   125,   126,    -1,    -1,    -1,    -1,    91,    92,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,    40,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,    -1,   125,   126,    -1,    58,
    59,    91,    92,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,    93,   125,
   126,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   256,    -1,    -1,    -1,    -1,    -1,
   262,   263,    -1,    -1,   266,    -1,    -1,   269,   270,
   271,   272,   273,   125,    -1,    -1,    -1,    -1,    -1,
   280,    -1,   282,   283,   284,   285,   286,   287,   288,
    -1,    -1,   291,   292,   293,   294,   256,    -1,    -1,
   298,    -1,    -1,   262,   263,    -1,    -1,   266,    -1,
    -1,   269,   270,   271,   272,   273,    -1,    -1,    -1,
    -1,    -1,    -1,   280,    -1,   282,   283,   284,   285,
   286,   287,   288,    -1,    -1,   291,   292,   293,   294,
    -1,   256,    -1,   298,    -1,   339,   340,   262,   263,
    -1,    -1,   266,    -1,    -1,   269,   270,   271,   272,
   273,    -1,    -1,    -1,    -1,    -1,    -1,   280,    -1,
   282,   283,   284,   285,   286,   287,   288,    -1,    -1,
   291,   292,   293,   294,   256,    -1,    -1,   298,   339,
   340,   262,   263,    -1,    -1,   266,    -1,    -1,   269,
   270,   271,   272,   273,    -1,    -1,    -1,    -1,    -1,
    -1,   280,    -1,   282,   283,   284,   285,   286,   287,
   288,    -1,    -1,   291,   292,   293,   294,    -1,    -1,
    -1,   298,    33,   339,   340,    36,    37,    38,    -1,
    40,    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,
   284,    -1,   286,   287,   288,    -1,    -1,   291,    -1,
    -1,    -1,   295,   296,   297,    -1,    64,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   339,   340,    -1,
    33,    -1,    35,    36,    37,    38,    -1,    40,    -1,
    -1,    43,    -1,    45,    -1,    -1,    91,    92,    -1,
    -1,    -1,    33,    -1,    -1,    36,    37,    38,    -1,
    40,    -1,    -1,    43,    64,    45,    -1,    -1,    40,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    59,   123,    -1,   125,   126,    64,    -1,    58,
    59,    -1,    -1,    -1,    91,    92,    -1,    -1,    -1,
    33,    -1,    -1,    36,    37,    38,    -1,    40,    -1,
    -1,    43,    -1,    45,    -1,    -1,    91,    92,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,
   123,    -1,    -1,   126,    64,    -1,    -1,    -1,    -1,
    33,    -1,    -1,    36,    37,    38,    -1,    40,    41,
    -1,    43,   123,    45,    -1,   126,    -1,    -1,    -1,
    -1,    -1,    -1,   125,    91,    92,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    64,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    33,    -1,
    -1,    36,    37,    38,    -1,    40,    -1,    -1,    43,
   123,    45,    -1,   126,    91,    92,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,
   256,    -1,    -1,    -1,    -1,    -1,   262,   263,    -1,
   123,   266,    -1,   126,   269,   270,   271,   272,   273,
    -1,    -1,    91,    92,    93,    -1,   280,    -1,   282,
   283,   284,   285,   286,   287,   288,    -1,    -1,   291,
   292,   293,   294,    -1,    -1,    -1,   298,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   266,
   267,   126,   269,   270,   271,   272,   273,    -1,    -1,
    -1,    -1,    -1,    -1,   280,    -1,   282,    -1,    -1,
    -1,   266,    -1,    -1,   269,   270,   271,   272,   273,
    -1,    -1,   339,   340,   298,    -1,   280,    -1,   282,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   284,    -1,   286,   287,   288,    -1,   298,   291,    -1,
    -1,    -1,   295,   296,   297,    -1,    -1,    -1,   266,
   267,    -1,   269,   270,   271,   272,   273,    -1,    -1,
   339,   340,    -1,    -1,   280,    -1,   282,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   339,   340,   298,    -1,    -1,    -1,   266,
    -1,    -1,   269,   270,   271,   272,   273,    33,    -1,
    -1,    36,    37,    38,   280,    40,   282,    -1,    43,
    -1,    45,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,    -1,    -1,    -1,    -1,
   339,   340,    64,    -1,    -1,    -1,   266,    -1,    -1,
   269,   270,   271,   272,   273,    33,    -1,    -1,    36,
    37,    38,   280,    40,   282,    -1,    43,    -1,    45,
    -1,    -1,    91,    92,    -1,    -1,    -1,    -1,    -1,
   339,   340,   298,    -1,    -1,    -1,    -1,    -1,    -1,
    64,    -1,    33,    -1,    -1,    36,    37,    38,    -1,
    40,    -1,    -1,    43,    -1,    45,    -1,   123,    -1,
   125,   126,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    92,    -1,    -1,    -1,    -1,    64,   339,   340,
    -1,    -1,    40,    41,    -1,    33,    44,    -1,    36,
    37,    38,    -1,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    58,    59,    -1,   123,    91,    92,   126,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    40,    41,    -1,    -1,
    44,    93,   123,    -1,    -1,   126,    -1,    -1,    -1,
    91,    92,    -1,    37,    38,    58,    59,    -1,    42,
    43,    -1,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,    60,
    -1,    62,    -1,    -1,    -1,   123,    -1,    -1,   126,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    -1,    -1,   266,    -1,    -1,
   269,   270,   271,   272,   273,    -1,    -1,    -1,    -1,
   125,    -1,   280,    -1,   282,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
    -1,    -1,   298,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   266,   267,    -1,   269,   270,
   271,   272,   273,    -1,    -1,    -1,    -1,    -1,    -1,
   280,    -1,   282,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   339,   340,
   298,   266,    -1,    -1,   269,   270,   271,   272,   273,
    -1,    -1,    -1,    -1,    -1,    -1,   280,    -1,   282,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,    -1,    -1,
    -1,    -1,    -1,    -1,   266,   339,   340,   269,   270,
   271,   272,   273,   284,    -1,   286,   287,   288,    -1,
   280,   291,   282,    -1,    -1,   295,   296,    37,    38,
    -1,    40,    41,    42,    43,    44,    45,    46,    47,
   298,    -1,   339,   340,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,    -1,    -1,   339,   340,    91,    -1,
    93,    94,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,   123,   124,   125,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,    -1,   338,   339,
   340,   341,    91,    -1,    93,    94,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,   123,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    91,    -1,    93,
    94,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    -1,    62,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   123,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   284,    -1,   286,   287,   288,    -1,    -1,
   291,   123,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,    -1,   338,   339,   340,   341,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,   338,   339,   340,
   341,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
    -1,   338,   339,   340,   341,    37,    38,    -1,    40,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,    -1,    91,    -1,    93,    94,
    37,    38,    -1,    40,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,   123,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    91,    -1,    93,    94,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,   123,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   284,    -1,   286,   287,   288,    -1,    -1,   291,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,   284,    -1,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,    -1,   338,   339,   340,   341,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    37,    38,    -1,    -1,    -1,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    59,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    37,    38,    94,    -1,    -1,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,   123,
   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    37,    38,    -1,    -1,    91,    42,    43,    94,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    40,
    91,    42,    43,    94,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   295,   296,   297,    -1,   299,   300,   301,    -1,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,    -1,   338,   339,
   340,   341,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,    -1,   338,   339,   340,   341,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,    37,    38,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    -1,
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
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
    -1,   338,   339,   340,   341,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,    -1,   338,   339,   340,   341,    37,    38,    -1,
    -1,    41,    42,    43,    -1,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    91,    37,    38,
    94,    -1,    41,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    37,    38,    -1,    -1,    91,    42,
    43,    94,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    59,    60,
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
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
    -1,   338,   339,   340,   341,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,    -1,   338,   339,   340,   341,    -1,    -1,    -1,
   295,   296,   297,    -1,   299,   300,   301,    -1,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,    -1,   338,   339,
   340,   341,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    37,    38,    -1,    -1,    41,    42,
    43,    -1,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,
    61,    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    37,    38,    94,    -1,    41,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,   123,
   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    37,    38,    -1,    -1,    91,    42,    43,    94,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,    40,
    91,    42,    43,    94,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   295,   296,   297,    -1,   299,   300,   301,    -1,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,    -1,   338,   339,
   340,   341,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,    -1,   338,   339,   340,   341,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,    37,    38,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    37,    38,    -1,    -1,    -1,    42,    43,
    -1,    45,    46,    47,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    37,    38,    -1,    -1,    -1,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    60,    -1,
    62,    63,    91,    37,    38,    94,    -1,    -1,    42,
    43,    -1,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,
    -1,    62,    91,    -1,    -1,    94,    -1,   123,   124,
    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    91,    -1,    -1,    94,   123,   124,
    60,    -1,    62,    -1,    -1,    37,    38,    -1,    -1,
    -1,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,
   124,    60,    -1,    62,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,   338,   339,   340,
   341,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,   338,   339,   340,
   341,    -1,    -1,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,   335,   336,    -1,   338,   339,
   340,   341,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
   339,   340,   341,    -1,    -1,    -1,    -1,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,    -1,
   338,   339,   340,   341,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    41,    42,    -1,    44,    -1,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    41,    42,    -1,
    44,    -1,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,   124,
   125,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,   338,
    -1,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
    -1,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,    -1,   338,   284,    -1,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,    -1,   338,    -1,   284,   341,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,   338,    37,    38,
   341,    40,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   284,    -1,   286,   287,   288,    -1,    -1,
   291,    -1,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
    37,    38,    -1,    40,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   284,    -1,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,    -1,
    -1,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
    -1,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    37,    38,    -1,
    40,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    37,    38,    -1,    40,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
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
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,   287,
   288,    -1,    -1,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    37,    38,    -1,    40,    41,    42,
    43,    44,    45,    46,    47,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    60,
    61,    62,    63,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,    -1,    -1,
    -1,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
    -1,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,
   286,   287,   288,    -1,    -1,   291,    -1,    -1,    -1,
   295,   296,   297,    -1,   299,   300,   301,    -1,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   330,
   331,   332,   333,   334,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    38,    -1,    40,    41,    -1,    43,
    44,    45,    46,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    40,    41,    -1,    -1,    44,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    93,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   125,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
    -1,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,    38,    -1,    40,    41,    -1,    43,    44,
    45,    46,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,   284,    -1,   286,   287,   288,    -1,    -1,
   291,    -1,    -1,    -1,   295,   296,   297,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    38,    -1,    40,    41,
    -1,    43,    44,    45,    46,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    38,    -1,    40,    41,    93,    94,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    38,    -1,    40,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,   287,
   288,   124,   125,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,    -1,   284,    -1,   286,   287,
   288,    -1,    -1,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,   318,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,    38,    -1,    40,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    38,    -1,    40,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    38,    -1,    40,    41,    -1,    -1,    44,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    38,
    -1,    40,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    40,    41,    -1,    -1,    44,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,
   286,   287,   288,    -1,    -1,   291,    -1,    -1,    -1,
   295,   296,   297,    -1,   299,   300,   301,   125,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,   319,   320,   321,
   322,   323,   324,   325,   284,    -1,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,   319,   320,   321,   322,   323,   324,
   325,    -1,    -1,    -1,    -1,   284,    -1,   286,   287,
   288,    -1,    -1,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,   318,   319,   320,   321,   322,   323,
   324,   325,   284,    -1,   286,   287,   288,    -1,    -1,
   291,    -1,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,    38,
    -1,    40,    41,    -1,   284,    44,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    38,    -1,    40,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    38,    -1,    40,
    41,    -1,    -1,    44,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   124,   125,    38,    -1,    40,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    40,    41,};
}
static void yycheck1(){
yycheck[1] = new short[] {
    -1,    -1,    44,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   284,    -1,   286,   287,   288,    -1,    -1,
   291,    -1,    -1,    -1,   295,   296,   297,    -1,   299,
   300,   301,   125,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,   319,   320,   321,   322,   323,   324,   325,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,   319,   320,
   321,   322,   323,   324,   325,    -1,    -1,    -1,    -1,
   284,    -1,   286,   287,   288,    -1,    -1,   291,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,   319,
   320,   321,   322,   323,   324,   325,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,   319,   320,   321,   322,
   323,   324,   325,    38,    -1,    40,    41,    -1,   284,
    44,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    38,    -1,    40,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    38,    -1,    40,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    93,    94,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,    40,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    93,    94,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,
    40,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,   124,   125,    -1,    -1,    -1,    -1,    93,    94,
    58,    59,    -1,    61,    -1,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    40,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    93,   299,   300,   301,    -1,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,    -1,   318,    -1,    -1,   284,    -1,
   286,   287,   288,    -1,    -1,   291,    -1,   125,    -1,
   295,   296,   297,    -1,   299,   300,   301,    -1,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,    -1,   318,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,    -1,    -1,
   284,    -1,   286,   287,   288,    -1,    -1,   291,    -1,
    -1,    -1,   295,   296,   297,    -1,   299,   300,   301,
    -1,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,    -1,   318,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    38,   318,
    40,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,   284,    63,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,    38,
    -1,    40,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    38,    -1,    40,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    93,    94,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    40,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    93,    94,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    40,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    93,    94,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,
    -1,    -1,    -1,    93,    94,    -1,    -1,    40,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,   124,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    93,   299,   300,
   301,    -1,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,    -1,   318,
    -1,    -1,   284,    -1,   286,   287,   288,    -1,    -1,
   291,    -1,   125,    -1,   295,   296,   297,    -1,   299,
   300,   301,    -1,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,    -1,
   318,    -1,    -1,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    -1,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
    -1,   318,    -1,    -1,   284,    -1,   286,   287,   288,
    -1,    -1,   291,    -1,    -1,    -1,   295,   296,   297,
    -1,   299,   300,   301,    -1,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,    -1,   318,    -1,    -1,   284,    -1,   286,   287,
   288,    -1,    -1,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,   318,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    40,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    58,    59,   291,    61,    -1,
    63,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,    -1,   318,    40,    41,
    -1,    -1,    44,    93,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    40,    41,    -1,    -1,    44,    -1,   125,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    61,    93,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    40,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    40,    41,    58,    59,    44,
    -1,    93,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    40,    41,
    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,   125,    58,    59,
    40,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   125,    -1,    40,    41,    -1,    93,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,
    -1,    -1,    -1,    58,    59,    -1,    -1,    -1,    93,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   125,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,   287,
   288,    -1,    93,   291,   125,    -1,    -1,   295,   296,
   297,    -1,   299,   300,   301,    -1,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,    -1,    -1,    40,    41,    -1,   125,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    58,    59,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,   299,   300,   301,    -1,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   284,    -1,   286,   287,   288,    -1,
    -1,   291,    -1,    93,    -1,   295,   296,   297,    -1,
   299,   300,   301,    -1,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   284,    -1,
   286,   287,   288,    -1,    -1,   291,    -1,    -1,   125,
   295,   296,   297,    -1,    -1,   284,    -1,   286,   287,
   288,    -1,    -1,   291,    -1,    -1,    -1,   295,   296,
   297,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,    -1,    -1,    40,    41,
    -1,   284,    44,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,    58,    59,
    40,    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   284,    -1,   286,   287,   288,
    58,    59,   291,    -1,    -1,    -1,   295,   296,   297,
    40,    41,    -1,    -1,    44,    -1,    93,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    40,    41,
    58,    59,    44,    -1,    -1,    -1,    -1,    -1,    93,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    40,    41,   125,    -1,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    58,    59,    -1,    -1,   125,    40,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,   284,    93,   286,   287,
   288,    -1,    -1,   291,    -1,    58,    59,   295,   296,
   297,    -1,    -1,    -1,   125,    40,    41,    -1,    93,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   125,    -1,    -1,    58,    59,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,    -1,   284,
    -1,   286,   287,   288,    -1,    -1,   291,    -1,    -1,
    -1,   295,   296,   297,    -1,    -1,    -1,    -1,    -1,
    -1,   284,    -1,   286,   287,   288,    -1,    -1,   291,
    -1,    -1,    -1,   295,   296,   297,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   284,    -1,   286,
   287,   288,    -1,    -1,   291,    -1,    -1,    -1,   295,
   296,   297};
}

 
final static short YYFINAL=1;
final static short YYMAXTOKEN=342;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'","'&'",null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'","'@'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'['","'\\\\'","']'","'^'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'{'","'|'","'}'","'~'",null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"PD_TIPO","PD_NUM",
"PD_VAR","PD_ARGS","PD_RETURNS","COMENTARIO","DECLARACION_TIPO","IMPORT_JAVA",
"LINEA_JAVA","ID","VAR","SIGIL","ENTERO","DECIMAL","CADENA_SIMPLE",
"CADENA_DOBLE","CADENA_COMANDO","M_REGEX","S_REGEX","Y_REGEX","STDIN","STDOUT",
"STDERR","MY","SUB","OUR","PACKAGE","WHILE","DO","FOR","UNTIL","IF","ELSIF",
"ELSE","UNLESS","LAST","NEXT","RETURN","LLOR","LLXOR","LLAND","LLNOT",
"MULTI_IGUAL","DIV_IGUAL","MOD_IGUAL","SUMA_IGUAL","MAS_IGUAL","MENOS_IGUAL",
"DESP_I_IGUAL","DESP_D_IGUAL","AND_IGUAL","OR_IGUAL","XOR_IGUAL","POW_IGUAL",
"LAND_IGUAL","LOR_IGUAL","CONCAT_IGUAL","X_IGUAL","DOS_PUNTOS","LOR","SUELO",
"LAND","NUM_EQ","NUM_NE","STR_EQ","STR_NE","CMP","CMP_NUM","SMART_EQ","NUM_LE",
"NUM_GE","STR_LT","STR_LE","STR_GT","STR_GE","DESP_I","DESP_D","X","STR_REX",
"STR_NO_REX","UNITARIO","POW","MAS_MAS","MENOS_MENOS","FLECHA","AMBITO",
};
final static String yyrule[] = {
"$accept : raiz",
"raiz : fuente",
"fuente : cuerpo masFuente",
"masFuente :",
"masFuente : funcionDef fuente",
"funcionDef : funcionSub '{' cuerpo '}'",
"funcionSub : SUB ID",
"cuerpo :",
"cuerpo : cuerpo sentencia",
"sentencia : lista modificador ';'",
"sentencia : bloque",
"sentencia : flujo",
"sentencia : PACKAGE paqueteID ID ';'",
"sentencia : PACKAGE ID ';'",
"sentencia : COMENTARIO",
"sentencia : DECLARACION_TIPO",
"sentencia : error ';'",
"expresion : constante",
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
"expresion : regulares",
"lista : expresion ',' lista",
"lista : expresion ','",
"lista : expresion",
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
"asignacion : expresion X_IGUAL expresion",
"asignacion : expresion CONCAT_IGUAL expresion",
"constante : ENTERO",
"constante : DECIMAL",
"constante : CADENA_SIMPLE",
"constante : CADENA_DOBLE",
"constante : CADENA_COMANDO",
"variable : '$' VAR",
"variable : '@' VAR",
"variable : '%' VAR",
"variable : '$' paqueteVar VAR",
"variable : '@' paqueteVar VAR",
"variable : '%' paqueteVar VAR",
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
"coleccion : '(' lista ')'",
"coleccion : '(' ')'",
"coleccion : '[' lista ']'",
"coleccion : '[' ']'",
"coleccion : '{' lista '}'",
"coleccion : '{' '}'",
"coleccion : expresion DOS_PUNTOS expresion",
"coleccion : MY '(' lista ')'",
"coleccion : OUR '(' lista ')'",
"acceso : expresion '{' lista '}'",
"acceso : expresion '[' lista ']'",
"acceso : expresion FLECHA '{' lista '}'",
"acceso : expresion FLECHA '[' lista ']'",
"acceso : '$' expresion",
"acceso : '@' expresion",
"acceso : '%' expresion",
"acceso : '$' '#' expresion",
"acceso : '\\\\' expresion",
"funcion : paqueteID ID expresion",
"funcion : paqueteID ID",
"funcion : ID expresion",
"funcion : ID",
"regulares : expresion STR_NO_REX M_REGEX",
"regulares : expresion STR_REX M_REGEX",
"regulares : expresion STR_REX S_REGEX",
"regulares : expresion STR_REX Y_REGEX",
"binario : expresion '|' expresion",
"binario : expresion '&' expresion",
"binario : '~' expresion",
"binario : expresion '^' expresion",
"binario : expresion DESP_I expresion",
"binario : expresion DESP_D expresion",
"logico : expresion LOR expresion",
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
"abrirBloque :",
"bloque : condicional elsif",
"bloque : WHILE abrirBloque '(' expresion ')' '{' cuerpo '}'",
"bloque : UNTIL abrirBloque '(' expresion ')' '{' cuerpo '}'",
"bloque : DO abrirBloque '{' cuerpo '}' WHILE '(' expresion ')' ';'",
"bloque : DO abrirBloque '{' cuerpo '}' UNTIL '(' expresion ')' ';'",
"bloque : FOR abrirBloque '(' expresion ';' expresion ';' expresion ')' '{' cuerpo '}'",
"bloque : FOR abrirBloque expresion '(' lista ')' '{' cuerpo '}'",
"bloque : FOR abrirBloque '(' lista ')' '{' cuerpo '}'",
"condicional : IF abrirBloque '(' expresion ')' '{' cuerpo '}'",
"condicional : UNLESS abrirBloque '(' expresion ')' '{' cuerpo '}'",
"elsif :",
"elsif : bloqueElsif elsif",
"elsif : ELSE abrirBloque '{' cuerpo '}'",
"bloqueElsif : ELSIF abrirBloque '(' expresion ')' '{' cuerpo '}'",
};

//#line 272 "parser.y"

	private List<Simbolo> simbolos;
	private List<Token> tokens;
	private Iterator<Token> iterator;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	private int errores;
	
	/**
	 * Constructor del analizador sintactico
	 * @param tokens Tokens lexicos
	 * @param opciones Opciones
	 * @param gestorErrores Gestor de errores
	 */
	public Parser(List<Token> tokens, Opciones opciones,GestorErrores gestorErrores) {
		this.tokens = tokens;
		simbolos = new ArrayList<>(tokens.size()*10);
		iterator = tokens.iterator();
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
	 * y luego lo retorna.
	 * @param s Simbolo
	 * @return ParseVal
	 */
	private <T extends Simbolo>  T add(T s){
		simbolos.add(s);
		return s;
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
//#line 2916 "Parser.java"
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
//#line 79 "parser.y"
{yyval=set(new Raiz(s(val_peek(0))));}
break;
case 2:
//#line 81 "parser.y"
{yyval=set(new Fuente(s(val_peek(1)), s(val_peek(0))));}
break;
case 3:
//#line 83 "parser.y"
{yyval=set(new MfNada());}
break;
case 4:
//#line 84 "parser.y"
{yyval=set(new MfFuente(s(val_peek(1)), s(val_peek(0))));}
break;
case 5:
//#line 86 "parser.y"
{yyval=set(new FuncionDef(s(val_peek(3)), s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 6:
//#line 88 "parser.y"
{yyval=set(new FuncionSub(s(val_peek(1)), s(val_peek(0))));}
break;
case 7:
//#line 90 "parser.y"
{yyval=set(new Cuerpo());}
break;
case 8:
//#line 91 "parser.y"
{yyval=set(Cuerpo.add(s(val_peek(1)), s(val_peek(0))));}
break;
case 9:
//#line 93 "parser.y"
{yyval=set(new StcLista(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 10:
//#line 94 "parser.y"
{yyval=set(new StcBloque(s(val_peek(0))));}
break;
case 11:
//#line 95 "parser.y"
{yyval=set(new StcFlujo(s(val_peek(0))));}
break;
case 12:
//#line 96 "parser.y"
{yyval=set(new StcPaquete(s(val_peek(3)), s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 13:
//#line 97 "parser.y"
{yyval=set(new StcPaquete(s(val_peek(2)), new Paquetes(), s(val_peek(1)), s(val_peek(0))));}
break;
case 14:
//#line 98 "parser.y"
{yyval=set(new StcComentario(s(val_peek(0))));}
break;
case 15:
//#line 99 "parser.y"
{yyval=set(new StcDeclaracion(s(val_peek(0))));}
break;
case 16:
//#line 100 "parser.y"
{yyval=set(new StcError());}
break;
case 17:
//#line 102 "parser.y"
{yyval=set(new ExpConstante(s(val_peek(0))));}
break;
case 18:
//#line 103 "parser.y"
{yyval=set(new ExpVariable(s(val_peek(0))));}
break;
case 19:
//#line 104 "parser.y"
{yyval=set(new ExpAsignacion(s(val_peek(0))));}
break;
case 20:
//#line 105 "parser.y"
{yyval=set(new ExpBinario(s(val_peek(0))));}
break;
case 21:
//#line 106 "parser.y"
{yyval=set(new ExpAritmetica(s(val_peek(0))));}
break;
case 22:
//#line 107 "parser.y"
{yyval=set(new ExpLogico(s(val_peek(0))));}
break;
case 23:
//#line 108 "parser.y"
{yyval=set(new ExpComparacion(s(val_peek(0))));}
break;
case 24:
//#line 109 "parser.y"
{yyval=set(new ExpColeccion(s(val_peek(0))));}
break;
case 25:
//#line 110 "parser.y"
{yyval=set(new ExpAcceso(s(val_peek(0))));}
break;
case 26:
//#line 111 "parser.y"
{yyval=set(new ExpFuncion(s(val_peek(0))));}
break;
case 27:
//#line 112 "parser.y"
{yyval=set(new ExpFuncion5(s(val_peek(1)), s(val_peek(0))));}
break;
case 28:
//#line 113 "parser.y"
{yyval=set(new ExpRegulares(s(val_peek(0))));}
break;
case 29:
//#line 115 "parser.y"
{yyval=set(Lista.add(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 30:
//#line 116 "parser.y"
{yyval=set(new Lista(s(val_peek(1)), s(val_peek(0))));}
break;
case 31:
//#line 117 "parser.y"
{yyval=set(new Lista(s(val_peek(0))));}
break;
case 32:
//#line 119 "parser.y"
{yyval=set(new ModNada());}
break;
case 33:
//#line 120 "parser.y"
{yyval=set(new ModIf(s(val_peek(1)), s(val_peek(0))));}
break;
case 34:
//#line 121 "parser.y"
{yyval=set(new ModUnless(s(val_peek(1)), s(val_peek(0))));}
break;
case 35:
//#line 122 "parser.y"
{yyval=set(new ModWhile(s(val_peek(1)), s(val_peek(0))));}
break;
case 36:
//#line 123 "parser.y"
{yyval=set(new ModUntil(s(val_peek(1)), s(val_peek(0))));}
break;
case 37:
//#line 124 "parser.y"
{yyval=set(new ModFor(s(val_peek(1)), s(val_peek(0))));}
break;
case 38:
//#line 126 "parser.y"
{yyval=set(new Next(s(val_peek(1)), s(val_peek(0))));}
break;
case 39:
//#line 127 "parser.y"
{yyval=set(new Last(s(val_peek(1)), s(val_peek(0))));}
break;
case 40:
//#line 128 "parser.y"
{yyval=set(new Return(s(val_peek(1)), null, s(val_peek(0))));}
break;
case 41:
//#line 129 "parser.y"
{yyval=set(new Return(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 42:
//#line 131 "parser.y"
{yyval=set(new Igual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 43:
//#line 132 "parser.y"
{yyval=set(new MasIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 44:
//#line 133 "parser.y"
{yyval=set(new MenosIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 45:
//#line 134 "parser.y"
{yyval=set(new MultiIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 46:
//#line 135 "parser.y"
{yyval=set(new DivIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 47:
//#line 136 "parser.y"
{yyval=set(new ModIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 48:
//#line 137 "parser.y"
{yyval=set(new PowIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 49:
//#line 138 "parser.y"
{yyval=set(new AndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 50:
//#line 139 "parser.y"
{yyval=set(new OrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 51:
//#line 140 "parser.y"
{yyval=set(new XorIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 52:
//#line 141 "parser.y"
{yyval=set(new DespDIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 53:
//#line 142 "parser.y"
{yyval=set(new DespIIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 54:
//#line 143 "parser.y"
{yyval=set(new LAndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 55:
//#line 144 "parser.y"
{yyval=set(new LOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 56:
//#line 145 "parser.y"
{yyval=set(new XIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 57:
//#line 146 "parser.y"
{yyval=set(new ConcatIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 58:
//#line 148 "parser.y"
{yyval=set(new Entero(s(val_peek(0))));}
break;
case 59:
//#line 149 "parser.y"
{yyval=set(new Decimal(s(val_peek(0))));}
break;
case 60:
//#line 150 "parser.y"
{yyval=set(new CadenaSimple(s(val_peek(0))));}
break;
case 61:
//#line 151 "parser.y"
{yyval=set(new CadenaDoble(s(val_peek(0))));}
break;
case 62:
//#line 152 "parser.y"
{yyval=set(new CadenaComando(s(val_peek(0))));}
break;
case 63:
//#line 154 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 64:
//#line 155 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 65:
//#line 156 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 66:
//#line 157 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 67:
//#line 158 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 68:
//#line 159 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 69:
//#line 160 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 70:
//#line 161 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 71:
//#line 162 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 72:
//#line 163 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 73:
//#line 164 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 74:
//#line 165 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 75:
//#line 167 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 76:
//#line 168 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 77:
//#line 170 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 78:
//#line 171 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 79:
//#line 173 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 80:
//#line 174 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(1)),new Lista(),s(val_peek(0))));}
break;
case 81:
//#line 175 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 82:
//#line 176 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(1)),new Lista(),s(val_peek(0))));}
break;
case 83:
//#line 177 "parser.y"
{yyval=set(new ColLlave(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 84:
//#line 178 "parser.y"
{yyval=set(new ColLlave(s(val_peek(1)),new Lista(),s(val_peek(0))));}
break;
case 85:
//#line 179 "parser.y"
{yyval=set(new ColGenerador(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 86:
//#line 180 "parser.y"
{yyval=set(new ColMy(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 87:
//#line 181 "parser.y"
{yyval=set(new ColOur(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 88:
//#line 183 "parser.y"
{yyval=set(new AccesoMap(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 89:
//#line 184 "parser.y"
{yyval=set(new AccesoArray(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 90:
//#line 185 "parser.y"
{yyval=set(new AccesoMapRef(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 91:
//#line 186 "parser.y"
{yyval=set(new AccesoArrayRef(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 92:
//#line 187 "parser.y"
{yyval=set(new AccesoRefEscalar(s(val_peek(1)),s(val_peek(0))));}
break;
case 93:
//#line 188 "parser.y"
{yyval=set(new AccesoRefArray(s(val_peek(1)),s(val_peek(0))));}
break;
case 94:
//#line 189 "parser.y"
{yyval=set(new AccesoRefMap(s(val_peek(1)),s(val_peek(0))));}
break;
case 95:
//#line 190 "parser.y"
{yyval=set(new AccesoSigil(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 96:
//#line 191 "parser.y"
{yyval=set(new AccesoRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 97:
//#line 193 "parser.y"
{yyval=set(new FuncionPaqueteArgs(s(val_peek(2)),s(val_peek(1)),add(new Argumentos(s(val_peek(0))))));}
break;
case 98:
//#line 194 "parser.y"
{yyval=set(new FuncionPaqueteNoArgs(s(val_peek(1)),s(val_peek(0))));}
break;
case 99:
//#line 195 "parser.y"
{yyval=set(new FuncionArgs(s(val_peek(1)),add(new Argumentos(s(val_peek(0))))));}
break;
case 100:
//#line 196 "parser.y"
{yyval=set(new FuncionNoArgs(s(val_peek(0))));}
break;
case 101:
//#line 198 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 102:
//#line 199 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 103:
//#line 200 "parser.y"
{yyval=set(new RegularSubs(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 104:
//#line 201 "parser.y"
{yyval=set(new RegularTrans(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 105:
//#line 203 "parser.y"
{yyval=set(new BinOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 106:
//#line 204 "parser.y"
{yyval=set(new BinAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 107:
//#line 205 "parser.y"
{yyval=set(new BinNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 108:
//#line 206 "parser.y"
{yyval=set(new BinXor(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 109:
//#line 207 "parser.y"
{yyval=set(new BinDespI(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 110:
//#line 208 "parser.y"
{yyval=set(new BinDespD(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 111:
//#line 210 "parser.y"
{yyval=set(new LogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 112:
//#line 211 "parser.y"
{yyval=set(new LogAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 113:
//#line 212 "parser.y"
{yyval=set(new LogNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 114:
//#line 213 "parser.y"
{yyval=set(new LogOrBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 115:
//#line 214 "parser.y"
{yyval=set(new LogAndBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 116:
//#line 215 "parser.y"
{yyval=set(new LogNotBajo(s(val_peek(1)),s(val_peek(0))));}
break;
case 117:
//#line 216 "parser.y"
{yyval=set(new LogXorBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 118:
//#line 217 "parser.y"
{yyval=set(new LogTernario(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 119:
//#line 219 "parser.y"
{yyval=set(new CompNumEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 120:
//#line 220 "parser.y"
{yyval=set(new CompNumNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 121:
//#line 221 "parser.y"
{yyval=set(new CompNumLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 122:
//#line 222 "parser.y"
{yyval=set(new CompNumLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 123:
//#line 223 "parser.y"
{yyval=set(new CompNumGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 124:
//#line 224 "parser.y"
{yyval=set(new CompNumGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 125:
//#line 225 "parser.y"
{yyval=set(new CompNumCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 126:
//#line 226 "parser.y"
{yyval=set(new CompStrEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 127:
//#line 227 "parser.y"
{yyval=set(new CompStrNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 128:
//#line 228 "parser.y"
{yyval=set(new CompStrLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 129:
//#line 229 "parser.y"
{yyval=set(new CompStrLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 130:
//#line 230 "parser.y"
{yyval=set(new CompStrGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 131:
//#line 231 "parser.y"
{yyval=set(new CompStrGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 132:
//#line 232 "parser.y"
{yyval=set(new CompStrCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 133:
//#line 233 "parser.y"
{yyval=set(new CompSmart(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 134:
//#line 235 "parser.y"
{yyval=set(new AritSuma(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 135:
//#line 236 "parser.y"
{yyval=set(new AritResta(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 136:
//#line 237 "parser.y"
{yyval=set(new AritMulti(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 137:
//#line 238 "parser.y"
{yyval=set(new AritDiv(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 138:
//#line 239 "parser.y"
{yyval=set(new AritPow(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 139:
//#line 240 "parser.y"
{yyval=set(new AritX(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 140:
//#line 241 "parser.y"
{yyval=set(new AritConcat(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 141:
//#line 242 "parser.y"
{yyval=set(new AritMod(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 142:
//#line 243 "parser.y"
{yyval=set(new AritPositivo(s(val_peek(1)),s(val_peek(0))));}
break;
case 143:
//#line 244 "parser.y"
{yyval=set(new AritNegativo(s(val_peek(1)),s(val_peek(0))));}
break;
case 144:
//#line 245 "parser.y"
{yyval=set(new AritPreIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 145:
//#line 246 "parser.y"
{yyval=set(new AritPreDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 146:
//#line 247 "parser.y"
{yyval=set(new AritPostIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 147:
//#line 248 "parser.y"
{yyval=set(new AritPostDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 148:
//#line 250 "parser.y"
{yyval=set(new AbrirBloque());}
break;
case 149:
//#line 252 "parser.y"
{yyval=set(new BloqueCondicional(s(val_peek(1)),s(val_peek(0))));}
break;
case 150:
//#line 253 "parser.y"
{yyval=set(new BloqueWhile(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 151:
//#line 254 "parser.y"
{yyval=set(new BloqueUntil(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 152:
//#line 255 "parser.y"
{yyval=set(new BloqueDoWhile(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 153:
//#line 256 "parser.y"
{yyval=set(new BloqueDoUntil(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 154:
//#line 257 "parser.y"
{yyval=set(new BloqueFor(s(val_peek(11)),s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 155:
//#line 258 "parser.y"
{yyval=set(new BloqueForeachVar(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 156:
//#line 259 "parser.y"
{yyval=set(new BloqueForeach(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 157:
//#line 261 "parser.y"
{yyval=set(new CondicionalIf(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 158:
//#line 262 "parser.y"
{yyval=set(new CondicionalUnless(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 159:
//#line 264 "parser.y"
{yyval=set(new ElsIfNada());}
break;
case 160:
//#line 265 "parser.y"
{yyval=set(new ElsIfElsIf(s(val_peek(1)),s(val_peek(0))));}
break;
case 161:
//#line 266 "parser.y"
{yyval=set(new ElsIfElse(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 162:
//#line 268 "parser.y"
{yyval=set(new BloqueElsIf(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
//#line 3713 "Parser.java"
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
