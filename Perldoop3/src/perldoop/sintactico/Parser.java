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
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.bloque.*;
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
//#line 54 "Parser.java"




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
public final static short COMENTARIO=257;
public final static short DECLARACION_TIPO=258;
public final static short IMPORT_JAVA=259;
public final static short LINEA_JAVA=260;
public final static short VAR=261;
public final static short FILE=262;
public final static short ENTERO=263;
public final static short DECIMAL=264;
public final static short M_REX=265;
public final static short S_REX=266;
public final static short Y_REX=267;
public final static short TEXTO=268;
public final static short EXP_SEP=269;
public final static short REX_MOD=270;
public final static short SEP=271;
public final static short STDIN=272;
public final static short STDOUT=273;
public final static short STDERR=274;
public final static short STDOUT_H=275;
public final static short STDERR_H=276;
public final static short MY=277;
public final static short SUB=278;
public final static short OUR=279;
public final static short PACKAGE=280;
public final static short WHILE=281;
public final static short DO=282;
public final static short FOR=283;
public final static short UNTIL=284;
public final static short USE=285;
public final static short IF=286;
public final static short ELSIF=287;
public final static short ELSE=288;
public final static short UNLESS=289;
public final static short LAST=290;
public final static short NEXT=291;
public final static short RETURN=292;
public final static short Q=293;
public final static short QQ=294;
public final static short QR=295;
public final static short QW=296;
public final static short QX=297;
public final static short LLOR=298;
public final static short LLXOR=299;
public final static short LLAND=300;
public final static short LLNOT=301;
public final static short MULTI_IGUAL=302;
public final static short DIV_IGUAL=303;
public final static short MOD_IGUAL=304;
public final static short MAS_IGUAL=305;
public final static short MENOS_IGUAL=306;
public final static short DESP_I_IGUAL=307;
public final static short DESP_D_IGUAL=308;
public final static short AND_IGUAL=309;
public final static short OR_IGUAL=310;
public final static short XOR_IGUAL=311;
public final static short POW_IGUAL=312;
public final static short LAND_IGUAL=313;
public final static short LOR_IGUAL=314;
public final static short DLOR_IGUAL=315;
public final static short CONCAT_IGUAL=316;
public final static short X_IGUAL=317;
public final static short ID=318;
public final static short DOS_PUNTOS=319;
public final static short LOR=320;
public final static short DLOR=321;
public final static short LAND=322;
public final static short NUM_EQ=323;
public final static short NUM_NE=324;
public final static short STR_EQ=325;
public final static short STR_NE=326;
public final static short CMP=327;
public final static short CMP_NUM=328;
public final static short SMART_EQ=329;
public final static short NUM_LE=330;
public final static short NUM_GE=331;
public final static short STR_LT=332;
public final static short STR_LE=333;
public final static short STR_GT=334;
public final static short STR_GE=335;
public final static short DESP_I=336;
public final static short DESP_D=337;
public final static short X=338;
public final static short STR_REX=339;
public final static short STR_NO_REX=340;
public final static short UNITARIO=341;
public final static short POW=342;
public final static short MAS_MAS=343;
public final static short MENOS_MENOS=344;
public final static short FLECHA=345;
public final static short ID_P=346;
public final static short ID_L=347;
public final static short AMBITO=348;
public final static short CONTEXTO=349;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    4,    5,    5,    6,    6,    8,
    3,    3,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,   13,   13,   13,   13,   13,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   30,    9,    9,   31,   31,   10,
   10,   10,   10,   10,   10,   12,   12,   12,   12,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   17,   17,   15,   15,
   15,   15,   15,   15,   15,   15,   32,   33,   33,   33,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   34,   34,   14,   14,   35,   35,
   36,   36,   36,   36,   37,   37,   24,   24,   24,   25,
   25,   25,   25,   25,   25,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   38,   38,   38,   28,   28,   28,
   27,   27,   20,   20,   20,   20,   20,   20,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   29,   29,   29,   29,
   29,   29,   39,   39,   40,   41,   41,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   42,   42,   42,
};
final static short yylen[] = {                            2,
    1,    2,    0,    2,    4,    2,    2,    1,    2,    1,
    0,    1,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    4,    3,    3,    4,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    2,
    1,    1,    1,    1,    3,    1,    2,    3,    1,    0,
    2,    2,    2,    2,    2,    2,    2,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    1,    1,    3,    3,
    3,    4,    4,    4,    4,    4,    1,    0,    4,    2,
    2,    2,    2,    3,    3,    3,    3,    4,    3,    3,
    3,    3,    3,    3,    3,    2,    3,    2,    3,    2,
    3,    2,    3,    2,    4,    4,    1,    1,    1,    2,
    3,    2,    2,    2,    2,    2,    2,    1,    3,    3,
    2,    3,    5,    5,    1,    1,    2,    1,    1,    1,
    3,    2,    3,    3,    2,    3,    3,    3,    3,    3,
    3,    2,    3,    3,    2,    3,    5,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    2,    2,    2,    2,    7,    6,    7,    6,
    9,    9,    0,    1,    0,    0,    1,    3,    9,    9,
   11,   11,   13,    8,    7,   10,   10,    0,    9,    5,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    4,    0,    0,   20,   21,   18,
   19,   77,   78,  138,  139,  140,    0,    0,    0,  195,
    0,  195,  195,    0,  195,  195,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,   88,   88,   88,    0,    0,    0,    2,    0,    8,
   12,    0,   15,   16,   17,    0,   30,    0,   29,   31,
   32,   33,   34,   35,   36,   37,   38,   39,   41,   42,
   43,   44,    0,  117,  118,  119,    7,    6,    0,   23,
   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   57,   56,    0,   58,    0,   88,   88,   88,   88,   88,
    0,    0,  135,  136,  108,    0,    0,   40,  142,    0,
    0,    0,    0,  124,    0,    0,    0,    0,    0,  110,
    0,  112,    0,  114,    0,    0,    0,  127,    0,    0,
    0,    0,    0,    0,    0,  122,    0,    0,  123,    0,
  125,    9,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  185,  186,    0,  120,    0,
    0,  101,    0,   99,  100,  104,    0,  102,  103,   28,
    0,    0,   26,    0,    0,    0,    0,    0,    0,    0,
    0,  195,    0,   25,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,  137,    0,    0,  106,    0,  109,
  111,  198,  113,    0,    0,   79,   90,    0,   80,   81,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   13,
  107,    0,  130,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   88,    0,
   88,    0,  121,    0,    5,  115,  116,   27,    0,    0,
    0,    0,  195,    0,    0,   24,    0,    0,   82,   84,
   85,   83,   86,  105,    0,    0,    0,    0,    0,   88,
   88,   88,    0,   88,    0,  195,    0,    0,    0,    0,
  195,  195,  195,  133,    0,   89,    0,    0,    0,    0,
    0,    0,    0,    0,  195,  195,  197,    0,    0,    0,
    0,    0,    0,    0,   88,   88,  194,  188,    0,  190,
    0,    0,    0,    0,    0,  205,    0,    0,    0,  187,
    0,    0,  189,    0,    0,    0,    0,  204,    0,    0,
    0,    0,    0,  199,    0,    0,  195,  200,    0,    0,
  191,  192,    0,    0,    0,    0,  195,  206,  207,  201,
  202,    0,    0,    0,    0,    0,    0,  203,  195,    0,
    0,  210,    0,    0,    0,  209,
};
final static short yydgoto[] = {                          1,
    2,    3,   58,    5,    6,   59,   60,   61,   62,  168,
   63,   64,   65,   66,   67,   68,   69,   70,   71,   72,
   73,   74,   75,   76,   77,   78,   79,   80,   81,   82,
   83,  150,  151,  135,   84,   85,   86,  127,  418,  102,
  362,  458,
};
final static short yysindex[] = {                         0,
    0, -237,  972, -289,    0,  -72,  -34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   35,   44, -250,    0,
   11,    0,    0, -230,    0,    0,   41,   50, 1328, -181,
 -160, -159, -151, -149, 2101,  580, -264, 1404, 2101, 2101,
 1620, 2101, 2101, 2101, 2101, 1696, 1736,  896,   83,   10,
    0,    0,    0,    0, 1288, 1813, 2101,    0,  972,    0,
    0, -152,    0,    0,    0, -259,    0, 5673,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   86,    0,    0,    0,    0,    0,  972,    0,
    0, -126, 2101, -125, -123, -122, 2101, -114,  -61,  -45,
 -115,  164,  146,   85,   -9,  166,  -36, -101,  181,  182,
    0,    0, 2024,    0, 3819,    0,    0,    0,    0,    0,
 6124,  -29,    0,    0,    0,    1, 2101,    0,    0, 5815,
  163,  163, -124,    0,  -25,  163,  163,  -54,  -54,    0,
  184,    0,  134,    0,  103,  -46, 1012,    0, 2101,  190,
 -172,  205,  145, -124,  -19,    0,  -16, -124,    0,  -15,
    0,    0, 2101, 2101, 2101, 2101, 2101,  188,  620,  208,
 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101,
 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101,
 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101,
 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101,
 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101, 2101,
 2101, 2101,  -51, -229, 2101,    0,    0,  -56,    0, 2101,
  124,    0,  209,    0,    0,    0,  211,    0,    0,    0,
  -35, 2101,    0,  972,   49,   57,   -8, 1696,   14,   -3,
  208,    0, 2101,    0,  -33, 2101, 2101,  138,    0,   -7,
   -6,   -2,    5,    6,    0,    1, 2137,    0,  -77,    0,
    0,    0,    0, 2101, 3961,    0,    0, 2101,    0,    0,
 -124,   13,  -77,  -77, 5673, 5673, 5673, 5673, 5673,    0,
    0,    1,    0, 5982, 5982, 6124, 6124, 6124, 6124, 6124,
 6124, 6124, 6124, 6124, 6124, 6124, 6124, 6124, 6124, 6124,
 6124, 6124, 6124, 4128, 6151, 6291, 6291, 6460, 6319, 6319,
 4014, 1472, 1472, 1472, 1472, 1472, 1472, 1472,  524,  524,
  524,  524,  524,  524,  524,  524,  485,  485,  176,  176,
  176,  -83,  -83,  -83,  -83,    9,   12,   16,    0,   22,
    0,  163,    0, 5673,    0,    0,    0,    0, 4270,  147,
  184,  219,    0,  159, 4437,    0, 4579, 4746,    0,    0,
    0,    0,    0,    0, 4888, 2101, 5055,  -77, 2101,    0,
    0,    0,   27,    0,   29,    0, -207, 2101,  178,  972,
    0,    0,    0,    0,  -56,    0,    1,   39,   40,   45,
   48,   78,   48,  229,    0,    0,    0,  295,  972,  230,
  234,  236,  237,   48,    0,    0,    0,    0,   48,    0,
  972,  321,  322, 2101,  238,    0,  972,  972,  972,    0,
   93,   95,    0,  242, 2101, 2101,  327,    0,  244,  248,
  249,   48,   48,    0, 5197, 5364,    0,    0, -182, -182,
    0,    0,  316,  317,  255,  342,    0,    0,    0,    0,
    0,  972, 2101,  265,  266, 5506,  972,    0,    0,  267,
  270,    0,  972,  269, -182,    0,
};
final static short yyrindex[] = {                         0,
    0,  390,    7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  275,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 7016,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,    0,
    0,  340,    0,    0,    0,    0,    0,  550,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1218,    0,    0,    0,    0,    0,  276,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  844,    0,    0,    0,    0,11813,    0,    0,    0,    0,
 7162, 7466, 2268,    0,    0, 7538, 7612, 6629, 6714,    0,
    0,    0,    0,    0,    0,  340,    0,    0,    0,    0,
   31,    0,    0, 2416,    0,    0,    0, 2583,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, 7088,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1258,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  276,    0,    0,    0,  341,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,11863, 6544,    0, 2725,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 2892,    0, 3034, 3201,  343,  346,  347,  348,  349,    0,
    0,11913,    0,  715,  935, 2231, 9368, 9728, 9796,10156,
10224,12192,12196,12217,12238,12242,12264,12285,12289,12310,
12343,12556,12560,    0,11763,11481,11710,11427,11319,11373,
10523,10577,10631,10689,10921,10975,11029,11087, 9307, 9375,
 9667, 9735, 9803,10095,10163,10231, 8938, 9238, 8500, 8800,
 8869, 7988, 8058, 8360, 8430,    0,    0,    0,    0,    0,
    0, 7916,    0, 1058,    0,    0,    0,    0,    0,    0,
  350,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 3343,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  341,    0,  276,
    0,    0,    0,    0, 3652,    0,12142,    0,    0,    0,
 3510,    0, 3510,    0,    0,    0,    0,    0,  276,    0,
    0,    0,    0, 3510,    0,    0,    0,    0, 3510,    0,
  276,    0,    0,  369,    0,    0,  276,  276,  276,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, 3510, 3510,    0,    0,    0,    0,    0,  504,  504,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  276,    0,    0,    0,    0,  276,    0,    0,    0,
    0,    0,  276,    0,  504,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -32,    0,    0,    0,  352,  368,  -37,    0,
    0,    0,    0,   79,  396,  -23,    0,  313,    0,    0,
    0,    0,    0,    0,    0,  382,    0,    0,    0,    0,
    0,   -1,    0,  154,   96,  158,    0,  273, -341,  -21,
 -358, -361,
};
final static int YYTABLESIZE=12860;
static short yytable[][] = new short[2][];
static { yytable0(); yytable1();}
static void yytable0(){
yytable[0] = new short[] {
   104,   105,   106,    10,   109,   110,   115,    11,    47,
   141,   143,   146,   121,   126,   240,   130,   131,   132,
   134,   136,   137,   138,   139,   254,   358,    91,   366,
   249,   247,    87,   408,   248,   156,   159,   161,    47,
   350,    47,   221,   198,   113,     4,   351,   219,   216,
    53,   217,   218,   220,   155,    52,    89,   152,   153,
    36,   250,   233,   231,    88,   169,   237,   206,   420,
   208,   191,    87,   437,   113,   100,   113,    87,    94,
    92,   430,   405,    93,   258,   406,   433,   273,    98,
    96,    49,    50,    97,    94,    92,   170,   107,   459,
   116,    90,    47,    98,    96,   197,   277,   278,   101,
    95,   111,   451,   452,   108,   266,   456,   457,    54,
    99,   112,   141,   117,   118,    95,   476,   260,   261,
   262,   263,   264,   119,    99,   120,   147,   113,   196,
   275,    87,    10,   163,   230,   164,   165,   149,   166,
   232,   234,   167,   235,   236,   285,   286,   287,   288,
   289,   148,   292,   238,   294,   295,   296,   297,   298,
   299,   300,   301,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,   318,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,   341,   342,   343,
   344,   345,   239,   252,   352,   241,   242,   243,   253,
   354,   244,   157,   160,   361,   360,   221,   346,   347,
   348,   255,   219,   359,   349,   256,   257,   220,   268,
   270,   229,   271,   272,   276,   365,   364,   265,   367,
   368,   163,   269,   164,   165,   279,   166,   280,   281,
   167,   331,   283,   284,   290,    46,   355,   356,   375,
   357,   133,    47,   377,   223,   224,   158,   225,   226,
   227,   228,   273,   369,   370,   293,    47,   245,   371,
   246,   374,   387,   229,   378,   154,   372,   373,   388,
   229,   380,    10,   390,   381,   229,    11,   113,   382,
   229,   229,   229,   228,   229,   384,   229,   229,   229,
   229,   401,   113,   403,   409,    87,   125,    30,    31,
    32,    33,    34,   282,   414,   415,   125,   291,   229,
   291,   416,   229,   417,   229,   192,   193,   194,   195,
   199,   200,   201,   202,   203,   204,   205,   207,   209,
   210,   211,   212,   213,   214,   215,   222,   223,   224,
   389,   225,   226,   227,   228,   363,   383,   419,   385,
   407,   421,   395,   424,   426,   397,   427,   410,   428,
   429,   435,   436,   438,   442,   404,   443,   444,   447,
   448,   411,   412,   413,   449,   450,   460,   461,   425,
   462,   398,   399,   400,   463,   402,   422,   423,   353,
   407,   467,   434,     1,   468,   472,   473,   475,   439,
   440,   441,   195,    50,   196,    11,    53,   157,   160,
    55,    54,    51,    52,   197,   196,   162,   445,   446,
   431,   432,   145,   103,   251,   128,   274,     0,     0,
     0,   229,     0,   455,     0,     0,     0,   465,     0,
     0,   229,     0,   470,   464,     0,     0,     0,   466,
   474,     0,   229,   229,   229,   229,   229,   471,     0,
   229,     0,   229,   229,   229,   229,   229,   229,   229,
   229,   229,   229,   229,   229,   229,   229,   229,   229,
   229,   229,   229,   229,   229,   229,   229,   229,   229,
   229,   229,   229,   229,   229,   229,   229,   229,   229,
   229,   229,   229,   229,   229,   229,   229,   229,   229,
   229,   229,   229,   229,   229,   229,   229,   229,   229,
   208,   225,   226,   227,   228,     0,   229,     0,   229,
     0,   222,   223,   224,   229,   225,   226,   227,   228,
   221,   229,     0,   229,   229,   219,   216,     0,   217,
   218,   220,   229,     0,   229,     0,   208,   208,     0,
   208,   208,   208,   208,   208,     0,     0,   208,     0,
   208,     0,     0,     0,   229,     0,   229,     0,     0,
     0,     0,     0,   221,     0,   208,   208,     0,   219,
   216,   208,   217,   218,   220,     0,     0,     0,     0,
    47,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    49,     0,     0,
    49,   208,   208,     0,     0,     0,   208,     0,     0,
   229,   229,     0,     0,     0,   113,    49,     0,     0,
     0,    42,    53,    47,    55,    41,    37,    52,    46,
     0,     0,    39,   229,    40,     0,   208,     0,   208,
   208,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    38,     0,     0,    49,    56,     0,     0,   113,
     0,     0,     0,     0,     0,    42,    53,     0,    55,
    41,    37,    52,    46,     0,     0,    39,     0,    40,
     0,     0,     0,     0,     0,    47,    57,     0,     0,
    49,    54,     0,     0,     0,    38,     0,     0,     0,
    56,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,     0,     0,    43,     0,     0,     0,     0,
    47,    57,     0,     0,     0,    54,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   113,     0,     0,    43,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   153,     0,     0,   153,   208,   208,   208,   208,   208,
     0,     0,   208,   208,     0,     0,     0,     0,   153,
   153,     0,   208,   208,   208,     0,     0,   208,   208,
   208,   208,   208,   208,   208,   208,   208,   208,     0,
     0,   208,   208,   208,   208,   208,   208,   208,   208,
   208,     0,     0,     0,   208,     0,     0,   153,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   208,   222,   223,   224,     0,   225,
   226,   227,   228,    49,     0,    49,    49,     0,    49,
     0,     0,    49,   153,     0,   122,    12,    13,     0,
     0,   208,   208,     0,   208,   208,    14,    15,    16,
   123,   124,    17,     0,    18,   214,   215,   222,   223,
   224,     0,   225,   226,   227,   228,     0,     0,     0,
    30,    31,    32,    33,    34,     0,     0,     0,    35,
     0,    12,    13,   155,     0,     0,   155,     0,     0,
     0,    14,    15,    16,     0,     0,    17,    36,    18,
     0,     0,   155,   155,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    30,    31,    32,    33,    34,
     0,     0,     0,    35,     0,    44,    45,     0,    49,
    50,   125,    42,    53,     0,    55,    41,    37,    52,
    46,   155,    36,    39,     0,    40,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    51,    38,     0,     0,     0,    56,     0,     0,
    44,    45,     0,    49,    50,   291,   155,     0,     0,
     0,     0,     0,     0,   156,     0,     0,   156,     0,
     0,     0,     0,   153,     0,     0,    47,    57,     0,
     0,     0,    54,   156,   156,     0,   153,     0,   153,
   153,     0,   153,     0,     0,   153,    42,    53,     0,
    55,    41,    37,    52,    46,   153,   153,    39,     0,
    40,     0,    48,     0,   144,    43,     0,     0,     0,
     0,     0,   156,     0,     0,    51,    38,     0,     0,
     0,    56,     0,     0,     0,     0,     0,     0,     0,
     0,    42,    53,     0,    55,    41,    37,    52,    46,
   140,     0,    39,     0,    40,     0,     0,   156,     0,
     0,    47,    57,     0,     0,     0,    54,     0,     0,
     0,    38,     0,     0,     0,    56,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    48,     0,     0,
    43,    48,     0,     0,    48,    47,    57,     0,     0,
     0,    54,     0,     0,     0,     0,   155,     0,     0,
     0,    48,     0,     0,     0,     0,     0,     0,     0,
   155,     0,   155,   155,     0,   155,     0,     0,   155,
     0,   113,     0,     0,    43,     0,     0,     0,   155,
   155,   155,     0,     0,     0,     0,     0,     0,    48,
     7,     8,     9,    10,    11,     0,     0,    12,    13,
     0,     0,     0,     0,     0,     0,     0,    14,    15,
    16,     0,     0,    17,     0,    18,    19,    20,    21,
    22,    23,    24,    25,    48,     0,    26,    27,    28,
    29,    30,    31,    32,    33,    34,     0,     0,     0,
    35,     0,     0,     0,     0,     0,     0,   156,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    36,
     0,   156,     0,   156,   156,     0,   156,     0,     0,
   156,     0,     0,     0,     7,     8,     9,    10,    11,
   156,   156,    12,    13,     0,     0,    44,    45,     0,
    49,    50,    14,    15,    16,     0,     0,    17,     0,
    18,    19,    20,    21,    22,    23,    24,    25,    46,
     0,    26,    27,    28,    29,    30,    31,    32,    33,
    34,     0,     0,     0,    35,   122,    12,    13,    46,
     0,     0,     0,     0,     0,     0,    14,    15,    16,
   123,   124,    17,    36,    18,     0,     0,     0,     0,
     0,     0,     0,    47,     0,     0,     0,     0,     0,
    30,    31,    32,    33,    34,     0,    46,     0,    35,
     0,    44,    45,    47,    49,    50,     0,    42,    53,
   155,    55,    41,    37,    52,    46,     0,    36,    39,
     0,    40,     0,     0,     0,     0,     0,    48,     0,
    48,    48,    46,    48,     0,     0,    48,    38,     0,
     0,    47,    56,     0,     0,    44,    45,     0,    49,
    50,     0,    42,    53,     0,    55,    41,    37,    52,
    46,     0,     0,    39,     0,    40,     0,     0,     0,
     0,     0,    47,    57,     0,     0,    47,    54,     0,
     0,   114,    38,     0,     0,     0,    56,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   113,     0,
     0,    43,     0,     0,     0,     0,    47,    57,     0,
     0,     0,    54,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    42,    53,     0,
    55,    41,    37,    52,    46,     0,     0,    39,     0,
    40,     0,   113,     0,     0,    43,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    38,     0,   129,
     0,    56,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,    57,     0,     0,    46,    54,    46,    46,
     0,    46,     0,     0,    46,     0,   221,     0,     0,
     0,     0,   219,   216,     0,   217,   218,   220,     0,
     0,     0,     0,     0,     0,     0,   113,     0,     0,
    43,     0,   206,     0,   208,     0,     0,     0,     0,
    47,     0,    47,    47,     0,    47,     0,     0,    47,
     0,   154,     0,    12,    13,     0,     0,     0,     0,
     0,     0,     0,    14,    15,    16,    47,     0,    17,
     0,    18,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    30,    31,    32,
    33,    34,     0,     0,     0,    35,     0,    12,    13,
     0,     0,   113,     0,     0,     0,     0,    14,    15,
    16,     0,     0,    17,    36,    18,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    30,    31,    32,    33,    34,     0,     0,     0,
    35,     0,    44,    45,     0,    49,    50,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    36,
     0,     0,     0,     0,     0,     0,    42,    53,     0,
    55,    41,    37,    52,    46,     0,     0,    39,     0,
    40,     0,    12,    13,     0,     0,    44,    45,     0,
    49,    50,    14,    15,    16,     0,    38,    17,     0,
    18,    56,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    30,    31,    32,    33,
    34,     0,     0,     0,    35,     0,     0,     0,     0,
     0,    47,    57,     0,     0,     0,    54,     0,     0,
     0,     0,     0,    36,     0,     0,     0,     0,     0,
     0,    42,    53,     0,    55,    41,    37,    52,    46,
   140,     0,    39,     0,    40,     0,   113,     0,     0,
    43,    44,    45,     0,    49,    50,     0,     0,     0,
     0,    38,     0,     0,     0,    56,     0,     0,     0,
     0,     0,     0,     0,     0,    42,    53,     0,    55,
    41,    37,    52,    46,     0,     0,    39,     0,    40,
     0,     0,     0,     0,     0,    47,    57,     0,     0,
     0,    54,     0,     0,     0,    38,     0,     0,     0,
    56,     0,   207,   209,   210,   211,   212,   213,   214,
   215,   222,   223,   224,     0,   225,   226,   227,   228,
     0,   113,     0,     0,    43,     0,     0,     0,     0,
    47,    57,   142,     0,     0,    54,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    42,    53,     0,    55,    41,    37,    52,    46,
     0,     0,    39,     0,    40,   113,     0,     0,    43,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    38,     0,     0,     0,    56,     0,     0,     0,
   133,     0,    12,    13,     0,     0,     0,     0,     0,
     0,     0,    14,    15,    16,     0,     0,    17,     0,
    18,     0,     0,     0,     0,    47,    57,     0,     0,
     0,    54,     0,     0,     0,    30,    31,    32,    33,
    34,     0,     0,     0,    35,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,     0,    36,    43,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    12,    13,     0,
     0,    44,    45,     0,    49,    50,    14,    15,    16,
     0,     0,    17,     0,    18,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    30,    31,    32,    33,    34,     0,     0,     0,    35,
     0,    12,    13,     0,     0,     0,     0,     0,     0,
     0,    14,    15,    16,     0,     0,    17,    36,    18,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    30,    31,    32,    33,    34,
     0,     0,     0,    35,     0,    44,    45,     0,    49,
    50,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    36,     0,     0,    42,    53,     0,    55,
    41,    37,    52,    46,     0,     0,    39,     0,    40,
     0,     0,     0,     0,   158,     0,    12,    13,     0,
    44,    45,     0,    49,    50,    38,    14,    15,    16,
    56,     0,    17,     0,    18,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    30,    31,    32,    33,    34,     0,     0,     0,    35,
    47,    57,     0,     0,     0,    54,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    36,     0,
     0,    42,    53,     0,    55,    41,    37,    52,    46,
     0,     0,    39,     0,    40,   113,     0,   144,    43,
     0,     0,     0,     0,     0,    44,    45,     0,    49,
    50,    38,     0,     0,     0,    56,     0,     0,     0,
     0,    42,    53,     0,    55,    41,     0,    52,    46,
     0,     0,    39,     0,    40,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    47,    57,     0,     0,
     0,    54,     0,     0,     0,    56,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,     0,     0,    43,    47,    57,     0,     0,
     0,    54,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,     0,     0,    43,     0,     0,     0,     0,
     0,     0,     0,     0,   154,     0,     0,   154,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    12,    13,   154,   154,     0,     0,     0,     0,
     0,    14,    15,    16,     0,     0,    17,     0,    18,
     0,    93,    93,     0,    93,    93,    93,    93,    93,
    93,    93,    93,     0,    30,    31,    32,    33,    34,
     0,     0,   154,    35,    93,    93,    93,    93,    93,
    93,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    36,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   154,     0,
     0,    93,     0,    93,    93,     0,    12,    13,     0,
    44,    45,     0,    49,    50,     0,    14,    15,    16,
     0,     0,    17,     0,    18,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    93,    93,    93,
    30,    31,    32,    33,    34,     0,    12,    13,    35,
     0,     0,     0,     0,     0,     0,    14,    15,    16,
     0,     0,    17,     0,    18,     0,     0,    36,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    30,    31,    32,    33,    34,     0,     0,     0,    35,
     0,     0,     0,     0,     0,    44,    45,     0,    49,
    50,     0,     0,     0,     0,    91,    91,    36,    91,
    91,    91,    91,    91,    91,    91,    91,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    91,
    91,    91,    91,    91,    91,    44,    45,     0,    49,
    50,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   154,     0,
     0,     0,     0,     0,     0,    91,     0,    91,    91,
     0,   154,     0,   154,   154,     0,   154,     0,     0,
   154,     0,     0,     0,     0,     0,     0,     0,     0,
   154,   154,   154,     0,     0,     0,     0,     0,    93,
     0,    91,    91,    91,     0,     0,     0,     0,     0,
     0,     0,    93,     0,    93,    93,     0,    93,     0,
     0,    93,     0,     0,     0,     0,     0,     0,     0,
     0,    93,    93,    93,     0,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,     0,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,    93,
    93,    93,    93,    93,    93,    93,    93,    93,     0,
    93,    93,    93,    93,     0,     0,     0,     0,     0,
     0,    92,    92,     0,    92,    92,    92,    92,    92,
    92,    92,    92,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    92,    92,    92,    92,    92,
    92,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    92,     0,    92,    92,     0,     0,     0,     0,
     0,     0,     0,    91,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    91,     0,    91,
    91,     0,    91,     0,     0,    91,    92,    92,    92,
     0,     0,     0,     0,     0,    91,    91,    91,     0,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,     0,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,    91,    91,    91,    91,    91,    91,
    91,    91,    91,     0,    91,    91,    91,    91,    96,
    96,     0,    96,    96,    96,    96,    96,    96,    96,
    96,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    96,    96,    96,    96,    96,    96,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    96,
     0,    96,    96,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    96,    96,    96,     0,    92,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    92,     0,    92,    92,     0,    92,     0,
     0,    92,     0,     0,     0,     0,     0,     0,     0,
     0,    92,    92,    92,     0,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,     0,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,    92,
    92,    92,    92,    92,    92,    92,    92,    92,     0,
    92,    92,    92,    92,    97,    97,     0,    97,    97,
    97,    97,    97,    97,    97,    97,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    97,    97,
    97,    97,    97,    97,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    97,     0,    97,    97,     0,
     0,     0,     0,     0,     0,     0,    96,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    96,     0,    96,    96,     0,    96,     0,     0,    96,
    97,    97,    97,     0,     0,     0,     0,     0,    96,
    96,    96,     0,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,     0,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,    96,    96,    96,
    96,    96,    96,    96,    96,    96,     0,    96,    96,
    96,    96,    94,    94,     0,    94,    94,    94,    94,
    94,    94,    94,    94,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    94,    94,    94,    94,
    94,    94,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    94,     0,    94,    94,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    94,    94,
    94,     0,    97,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    97,     0,    97,    97,
     0,    97,     0,     0,    97,     0,     0,     0,     0,
     0,     0,     0,     0,    97,    97,    97,     0,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,     0,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,    97,    97,    97,    97,    97,    97,    97,
    97,    97,     0,    97,    97,    97,    97,    95,    95,
     0,    95,    95,    95,    95,    95,    95,    95,    95,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    95,    95,    95,    95,    95,    95,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    95,     0,
    95,    95,     0,     0,     0,     0,     0,     0,     0,
    94,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    94,     0,    94,    94,     0,    94,
     0,     0,    94,    95,    95,    95,     0,     0,     0,
     0,     0,    94,    94,    94,     0,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,     0,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
    94,    94,    94,    94,    94,    94,    94,    94,    94,
     0,    94,    94,    94,    94,    98,    98,     0,    98,
    98,    98,    98,    98,    98,    98,    98,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    98,
    98,    98,    98,    98,    98,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    98,     0,    98,    98,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    98,    98,    98,     0,    95,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    95,
     0,    95,    95,     0,    95,     0,     0,    95,     0,
     0,     0,     0,     0,     0,     0,     0,    95,    95,
    95,     0,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
     0,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,    95,    95,    95,    95,
    95,    95,    95,    95,    95,     0,    95,    95,    95,
    95,   193,   193,     0,     0,   193,   193,   193,   193,
   193,   193,   193,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   193,   193,   193,   193,   193,
   193,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   193,     0,   193,   193,     0,     0,     0,     0,
     0,     0,     0,    98,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,    98,     0,    98,
    98,     0,    98,     0,     0,    98,   193,   193,   193,
     0,     0,     0,     0,     0,    98,    98,    98,     0,
    98,    98,    98,    98,    98,    98,    98,    98,    98,
    98,    98,    98,    98,    98,    98,    98,     0,    98,
    98,    98,    98,    98,    98,    98,    98,    98,    98,
    98,    98,    98,    98,    98,    98,    98,    98,    98,
    98,    98,    98,     0,    98,    98,    98,    98,   134,
   134,     0,     0,   134,   134,   134,   134,   134,   134,
   134,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   134,   134,   134,   134,   134,   134,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   134,   134,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   134,   134,     0,   193,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   193,     0,   193,   193,     0,   193,     0,
     0,   193,     0,     0,     0,     0,     0,     0,     0,
     0,   193,   193,   193,     0,   193,   193,   193,   193,
   193,   193,   193,   193,   193,   193,   193,   193,   193,
   193,   193,   193,     0,   193,   193,   193,   193,   193,
   193,   193,   193,   193,   193,   193,   193,   193,   193,
   193,   193,   193,   193,   193,   193,   193,   193,     0,
   193,   193,   193,   193,   221,   198,     0,     0,     0,
   219,   216,     0,   217,   218,   220,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   259,
   206,   174,   208,   191,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    47,     0,     0,   197,     0,
     0,     0,     0,     0,     0,     0,   134,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   134,     0,   134,   134,     0,   134,     0,     0,   134,
   113,   196,     0,     0,     0,     0,     0,     0,   134,
   134,   134,     0,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,     0,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,     0,   134,   134,
   134,   134,   221,   198,     0,     0,     0,   219,   216,
     0,   217,   218,   220,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   206,   174,
   208,   191,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   221,    47,     0,     0,   197,   219,   216,     0,
   217,   218,   220,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   206,     0,   208,
     0,     0,     0,     0,     0,     0,     0,   113,   196,
   376,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   171,   172,   173,     0,   175,
   176,   177,   178,   179,   180,   181,   182,   183,   184,
   185,   186,   187,   188,   189,   190,   113,   192,   193,
   194,   195,   199,   200,   201,   202,   203,   204,   205,
   207,   209,   210,   211,   212,   213,   214,   215,   222,
   223,   224,     0,   225,   226,   227,   228,   221,   198,
     0,     0,     0,   219,   216,     0,   217,   218,   220,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   379,     0,   206,   174,   208,   191,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,     0,
     0,   197,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   113,   196,     0,     0,     0,     0,
     0,     0,   171,   172,   173,     0,   175,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   188,   189,   190,     0,   192,   193,   194,   195,
   199,   200,   201,   202,   203,   204,   205,   207,   209,
   210,   211,   212,   213,   214,   215,   222,   223,   224,
     0,   225,   226,   227,   228,   221,   198,     0,     0,
   386,   219,   216,     0,   217,   218,   220,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   206,   174,   208,   191,     0,     0,     0,   199,
   200,   201,   202,   203,   204,   205,   207,   209,   210,
   211,   212,   213,   214,   215,   222,   223,   224,     0,
   225,   226,   227,   228,     0,    47,     0,     0,   197,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,   196,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   171,   172,
   173,     0,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   188,   189,   190,
     0,   192,   193,   194,   195,   199,   200,   201,   202,
   203,   204,   205,   207,   209,   210,   211,   212,   213,
   214,   215,   222,   223,   224,     0,   225,   226,   227,
   228,   221,   198,     0,     0,   391,   219,   216,     0,
   217,   218,   220,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   206,   174,   208,
   191,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,     0,   197,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   113,   196,     0,
     0,     0,     0,     0,     0,   171,   172,   173,     0,
   175,   176,   177,   178,   179,   180,   181,   182,   183,
   184,   185,   186,   187,   188,   189,   190,     0,   192,
   193,   194,   195,   199,   200,   201,   202,   203,   204,
   205,   207,   209,   210,   211,   212,   213,   214,   215,
   222,   223,   224,     0,   225,   226,   227,   228,   221,
   198,     0,     0,   392,   219,   216,     0,   217,   218,
   220,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   206,   174,   208,   191,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    47,
     0,     0,   197,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   113,   196,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   171,   172,   173,     0,   175,   176,   177,   178,
   179,   180,   181,   182,   183,   184,   185,   186,   187,
   188,   189,   190,     0,   192,   193,   194,   195,   199,
   200,   201,   202,   203,   204,   205,   207,   209,   210,
   211,   212,   213,   214,   215,   222,   223,   224,     0,
   225,   226,   227,   228,   221,   198,     0,     0,   393,
   219,   216,     0,   217,   218,   220,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   206,   174,   208,   191,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    47,     0,     0,   197,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   113,   196,     0,     0,     0,     0,     0,     0,   171,
   172,   173,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   188,   189,
   190,     0,   192,   193,   194,   195,   199,   200,   201,
   202,   203,   204,   205,   207,   209,   210,   211,   212,
   213,   214,   215,   222,   223,   224,     0,   225,   226,
   227,   228,   221,   198,     0,     0,   394,   219,   216,
     0,   217,   218,   220,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   206,   174,
   208,   191,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    47,     0,     0,   197,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   113,   196,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   171,   172,   173,     0,   175,
   176,   177,   178,   179,   180,   181,   182,   183,   184,
   185,   186,   187,   188,   189,   190,     0,   192,   193,
   194,   195,   199,   200,   201,   202,   203,   204,   205,
   207,   209,   210,   211,   212,   213,   214,   215,   222,
   223,   224,     0,   225,   226,   227,   228,   221,   198,
     0,     0,     0,   219,   216,     0,   217,   218,   220,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   206,   174,   208,   191,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,     0,
     0,   197,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   113,   196,     0,     0,     0,     0,
     0,     0,   171,   172,   173,     0,   175,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   188,   189,   190,     0,   192,   193,   194,   195,
   199,   200,   201,   202,   203,   204,   205,   207,   209,
   210,   211,   212,   213,   214,   215,   222,   223,   224,
     0,   225,   226,   227,   228,   221,   198,     0,     0,
   453,   219,   216,     0,   217,   218,   220,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   206,   174,   208,   191,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    47,     0,     0,   197,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,   196,     0,     0,   396,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   171,   172,
   173,     0,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   188,   189,   190,
     0,   192,   193,   194,   195,   199,   200,   201,   202,
   203,   204,   205,   207,   209,   210,   211,   212,   213,
   214,   215,   222,   223,   224,     0,   225,   226,   227,
   228,   221,   198,     0,     0,   454,   219,   216,     0,
   217,   218,   220,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   206,   174,   208,
   191,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    47,     0,     0,   197,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   113,   196,     0,
     0,     0,     0,     0,     0,   171,   172,   173,     0,
   175,   176,   177,   178,   179,   180,   181,   182,   183,
   184,   185,   186,   187,   188,   189,   190,     0,   192,
   193,   194,   195,   199,   200,   201,   202,   203,   204,
   205,   207,   209,   210,   211,   212,   213,   214,   215,
   222,   223,   224,     0,   225,   226,   227,   228,   221,
   198,     0,     0,   469,   219,   216,     0,   217,   218,
   220,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   206,   174,   208,   191,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    47,
     0,     0,   197,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   113,   196,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   171,   172,   173,     0,   175,   176,   177,   178,
   179,   180,   181,   182,   183,   184,   185,   186,   187,
   188,   189,   190,     0,   192,   193,   194,   195,   199,
   200,   201,   202,   203,   204,   205,   207,   209,   210,
   211,   212,   213,   214,   215,   222,   223,   224,     0,
   225,   226,   227,   228,   221,   198,     0,     0,     0,
   219,   216,     0,   217,   218,   220,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   206,   174,   208,   191,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    47,     0,     0,   197,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   113,   196,     0,     0,     0,     0,     0,     0,   171,
   172,   173,     0,   175,   176,   177,   178,   179,   180,
   181,   182,   183,   184,   185,   186,   187,   188,   189,
   190,     0,   192,   193,   194,   195,   199,   200,   201,
   202,   203,   204,   205,   207,   209,   210,   211,   212,
   213,   214,   215,   222,   223,   224,     0,   225,   226,
   227,   228,   221,   198,     0,     0,     0,   219,   216,
     0,   217,   218,   220,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   206,   174,
   267,   191,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    47,     0,     0,   197,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   113,   196,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   171,   172,   173,     0,   175,
   176,   177,   178,   179,   180,   181,   182,   183,   184,
   185,   186,   187,   188,   189,   190,     0,   192,   193,
   194,   195,   199,   200,   201,   202,   203,   204,   205,
   207,   209,   210,   211,   212,   213,   214,   215,   222,
   223,   224,     0,   225,   226,   227,   228,   221,   198,
     0,     0,     0,   219,   216,     0,   217,   218,   220,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   206,   174,   208,   191,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    47,     0,
     0,   197,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   113,   196,     0,     0,     0,     0,
     0,     0,   171,   172,   173,     0,   175,   176,   177,
   178,   179,   180,   181,   182,   183,   184,   185,   186,
   187,   188,   189,   190,     0,   192,   193,   194,   195,
   199,   200,   201,   202,   203,   204,   205,   207,   209,
   210,   211,   212,   213,   214,   215,   222,   223,   224,
     0,   225,   226,   227,   228,   221,   198,     0,     0,
     0,   219,   216,     0,   217,   218,   220,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   206,   174,   208,   191,   221,   198,     0,     0,
     0,   219,   216,     0,   217,   218,   220,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   206,     0,   208,     0,    47,     0,     0,   197,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    47,     0,     0,   197,
     0,   113,   196,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   113,   196,     0,     0,     0,     0,     0,     0,
   173,     0,   175,   176,   177,   178,   179,   180,   181,
   182,   183,   184,   185,   186,   187,   188,   189,   190,
     0,   192,   193,   194,   195,   199,   200,   201,   202,
   203,   204,   205,   207,   209,   210,   211,   212,   213,
   214,   215,   222,   223,   224,     0,   225,   226,   227,
   228,   221,   198,     0,     0,     0,   219,   216,     0,
   217,   218,   220,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   206,     0,   208,
     0,     0,   221,   198,     0,     0,     0,   219,   216,
     0,   217,   218,   220,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   206,     0,
   208,    47,     0,     0,   197,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    47,     0,     0,     0,   113,   196,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   175,   176,   177,   178,   179,   180,   181,   182,   183,
   184,   185,   186,   187,   188,   189,   190,   113,   192,
   193,   194,   195,   199,   200,   201,   202,   203,   204,
   205,   207,   209,   210,   211,   212,   213,   214,   215,
   222,   223,   224,     0,   225,   226,   227,   228,     0,
   193,   194,   195,   199,   200,   201,   202,   203,   204,
   205,   207,   209,   210,   211,   212,   213,   214,   215,
   222,   223,   224,     0,   225,   226,   227,   228,   221,
   198,     0,     0,     0,   219,   216,     0,   217,   218,
   220,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   206,     0,   208,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    47,
     0,     0,   197,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   141,   113,   196,   141,   141,     0,
   141,     0,   141,   141,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   141,   141,     0,   141,
   141,   141,     0,     0,     0,     0,     0,   195,   199,
   200,   201,   202,   203,   204,   205,   207,   209,   210,
   211,   212,   213,   214,   215,   222,   223,   224,     0,
   225,   226,   227,   228,   141,   141,     0,     0,     0,
   199,   200,   201,   202,   203,   204,   205,   207,   209,
   210,   211,   212,   213,   214,   215,   222,   223,   224,
     0,   225,   226,   227,   228,     0,   183,   183,   141,
   141,   183,   183,   183,   183,   183,   183,   183,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   183,   183,   183,   183,   183,   183,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   183,
   183,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   184,   184,   183,   183,   184,   184,   184,   184,
   184,   184,   184,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   184,   184,   184,   184,   184,
   184,     0,     0,     0,     0,     0,   199,   200,   201,
   202,   203,   204,   205,   207,   209,   210,   211,   212,
   213,   214,   215,   222,   223,   224,     0,   225,   226,
   227,   228,     0,   184,   184,     0,     0,     0,     0,
   141,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   141,     0,   141,   141,     0,   141,
     0,     0,   141,     0,     0,     0,     0,   184,   184,
     0,     0,   141,   141,   141,     0,   141,   141,   141,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
   141,   141,   141,   141,     0,   141,   141,   141,   141,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
   141,   141,   141,   141,   141,   141,   141,   141,   141,
     0,   141,     0,     0,   141,     0,     0,     0,     0,
     0,     0,     0,     0,   183,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   183,     0,
   183,   183,     0,   183,     0,     0,   183,     0,     0,
     0,     0,     0,     0,     0,     0,   183,   183,   183,
     0,   183,   183,   183,   183,   183,   183,   183,   183,
   183,   183,   183,   183,   183,   183,   183,   183,     0,
   183,   183,   183,   183,   183,   183,   183,   183,   183,
   183,   183,   183,   183,   183,   183,   183,   183,   183,
   183,   183,   183,   183,     0,   183,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   184,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   184,     0,   184,   184,     0,   184,     0,
     0,   184,     0,     0,     0,     0,     0,     0,     0,
     0,   184,   184,   184,     0,   184,   184,   184,   184,
   184,   184,   184,   184,   184,   184,   184,   184,   184,
   184,   184,   184,     0,   184,   184,   184,   184,   184,
   184,   184,   184,   184,   184,   184,   184,   184,   184,
   184,   184,   184,   184,   184,   184,   184,   184,     0,
   184,   128,   128,     0,   128,     0,   128,   128,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   128,   128,     0,   128,   128,   128,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   128,
   128,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   131,   131,     0,   131,     0,   131,   131,     0,
     0,     0,     0,   128,   128,     0,     0,     0,     0,
   131,   131,     0,   131,   131,   131,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   131,
   131,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   181,
   181,     0,     0,   181,   181,   181,   181,   181,   181,
   181,     0,     0,   131,   131,     0,     0,     0,     0,
     0,     0,   181,   181,   181,   181,   181,   181,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   181,   181,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   128,   181,   181,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   128,     0,
   128,   128,     0,   128,     0,     0,   128,     0,     0,
     0,     0,     0,     0,     0,     0,   128,   128,   128,
     0,   128,   128,   128,   128,   128,   128,   128,   128,
   128,   128,   128,   128,   128,   128,   128,   128,     0,
   128,   128,   128,   128,   128,   128,   128,   128,   128,
   128,   128,   128,   128,   128,   128,   128,   128,   128,
   128,   128,   128,   128,   131,   128,     0,     0,   128,
     0,     0,     0,     0,     0,     0,     0,   131,     0,
   131,   131,     0,   131,     0,     0,   131,     0,     0,
     0,     0,     0,     0,     0,     0,   131,   131,   131,
     0,   131,   131,   131,   131,   131,   131,   131,   131,
   131,   131,   131,   131,   131,   131,   131,   131,     0,
   131,   131,   131,   131,   131,   131,   131,   131,   131,
   131,   131,   131,   131,   131,   131,   131,   131,   131,
   131,   131,   131,   131,     0,   131,   181,     0,   131,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   181,     0,   181,   181,     0,   181,     0,     0,   181,
     0,     0,     0,     0,     0,     0,     0,     0,   181,
   181,   181,     0,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,     0,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   181,   181,   181,
   181,   181,   181,   181,   181,   181,   182,   182,     0,
     0,   182,   182,   182,   182,   182,   182,   182,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   182,   182,   182,   182,   182,   182,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   182,
   182,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   152,   152,     0,
     0,   152,   152,   152,   152,   152,   152,   152,     0,
     0,     0,     0,   182,   182,     0,     0,     0,     0,
   152,   152,   152,   152,   152,   152,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   152,
   152,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   145,
   145,     0,     0,   145,   145,   145,   145,   145,   145,
   145,     0,     0,   152,   152,     0,     0,     0,     0,
     0,     0,   145,   145,   145,   145,   145,   145,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   145,   145,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   182,   145,   145,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   182,     0,
   182,   182,     0,   182,     0,     0,   182,     0,     0,
     0,     0,     0,     0,     0,     0,   182,   182,   182,
     0,   182,   182,   182,   182,   182,   182,   182,   182,
   182,   182,   182,   182,   182,   182,   182,   182,     0,
   182,   182,   182,   182,   182,   182,   182,   182,   182,
   182,   182,   182,   182,   182,   182,   182,   182,   182,
   182,   182,   182,   182,   152,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   152,     0,
   152,   152,     0,   152,     0,     0,   152,     0,     0,
     0,     0,     0,     0,     0,     0,   152,   152,   152,
     0,   152,   152,   152,   152,   152,   152,   152,   152,
   152,   152,   152,   152,   152,   152,   152,   152,     0,
   152,   152,   152,   152,   152,   152,   152,   152,   152,
   152,   152,   152,   152,   152,   152,   152,   152,   152,
   152,   152,   152,   152,     0,     0,   145,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   145,     0,   145,   145,     0,   145,     0,     0,   145,
     0,     0,     0,     0,     0,     0,     0,     0,   145,
   145,   145,     0,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,     0,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   145,   145,   145,
   145,   145,   145,   145,   145,   145,   177,   177,     0,
     0,   177,   177,   177,   177,   177,   177,   177,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   177,   177,   177,   177,   177,   177,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   177,
   177,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   175,   175,     0,
     0,   175,   175,   175,   175,   175,   175,   175,     0,
     0,     0,     0,   177,   177,     0,     0,     0,     0,
   175,   175,   175,   175,   175,   175,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   175,
   175,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   176,   176,     0,     0,   176,
   176,   176,   176,   176,   176,   176,     0,     0,     0,
     0,     0,     0,   175,   175,     0,     0,   176,   176,
   176,   176,   176,   176,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   176,   176,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   176,   176,     0,   177,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   177,     0,
   177,   177,     0,   177,     0,     0,   177,     0,     0,
     0,     0,     0,     0,     0,     0,   177,   177,   177,
     0,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,     0,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   177,   177,   177,   177,   177,
   177,   177,   177,   177,   175,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   175,     0,
   175,   175,     0,   175,     0,     0,   175,     0,     0,
     0,     0,     0,     0,     0,     0,   175,   175,   175,
     0,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,     0,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   175,   175,   175,   175,   175,   175,   175,
   175,   175,   176,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   176,     0,   176,   176,
     0,   176,     0,     0,   176,     0,     0,     0,     0,
     0,     0,     0,     0,   176,   176,   176,     0,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,     0,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   176,   176,   176,   176,   176,   176,   176,   176,   176,
   180,   180,     0,     0,   180,   180,   180,   180,   180,
   180,   180,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   180,   180,   180,   180,   180,   180,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   180,   180,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   178,   178,
     0,     0,   178,   178,   178,   178,   178,   178,   178,
     0,     0,     0,     0,     0,     0,   180,   180,     0,
     0,   178,   178,   178,   178,   178,   178,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   178,   178,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   173,     0,     0,
   173,     0,   173,   173,   173,   173,     0,     0,     0,
     0,     0,     0,     0,   178,   178,     0,     0,   173,
   173,   173,   173,   173,   173,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   173,   173,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   173,   173,     0,     0,     0,   180,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   180,     0,   180,   180,     0,   180,     0,     0,
   180,     0,     0,     0,     0,     0,     0,     0,     0,
   180,   180,   180,     0,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,     0,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   180,   180,   180,   180,
   180,   180,   180,   180,   180,   178,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   178,
     0,   178,   178,     0,   178,     0,     0,   178,     0,
     0,     0,     0,     0,     0,     0,     0,   178,   178,
   178,     0,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
     0,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   178,   178,   178,   178,   178,   178,
   178,   178,   178,   173,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   173,     0,   173,
   173,     0,   173,     0,     0,   173,     0,     0,     0,
     0,     0,     0,     0,     0,   173,   173,   173,     0,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,     0,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   173,   173,   173,   173,   173,   173,   173,   173,   173,
   174,     0,     0,   174,     0,   174,   174,   174,   174,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   174,   174,   174,   174,   174,   174,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   174,   174,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   179,     0,     0,
   179,     0,   179,   179,   179,   179,     0,     0,     0,
     0,     0,     0,     0,     0,   174,   174,     0,   179,
   179,   179,   179,   179,   179,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   179,   179,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   147,     0,     0,   147,     0,     0,
   147,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   179,   179,     0,   147,   147,   147,   147,};
}
static void yytable1(){
yytable[1] = new short[] {
   147,   147,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   147,   147,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   147,
   147,     0,     0,     0,     0,     0,   174,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   174,     0,   174,   174,     0,   174,     0,     0,   174,
     0,     0,     0,     0,     0,     0,     0,     0,   174,
   174,   174,     0,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,     0,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   174,   174,   174,   174,   174,   174,
   174,   174,   174,   179,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   179,     0,   179,
   179,     0,   179,     0,     0,   179,     0,     0,     0,
     0,     0,     0,     0,     0,   179,   179,   179,     0,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,     0,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   179,   179,   179,   179,   179,   179,   179,   179,   179,
   147,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   147,     0,   147,   147,     0,   147,
     0,     0,   147,     0,     0,     0,     0,     0,     0,
     0,     0,   147,   147,   147,     0,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   147,   147,   147,
   147,   147,   147,   147,     0,   147,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   147,   147,   147,
   147,   147,   147,   147,   147,   147,   148,     0,     0,
   148,     0,     0,   148,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   148,
   148,   148,   148,   148,   148,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   148,   148,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   160,     0,     0,   160,     0,     0,
   160,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   148,   148,     0,   160,   160,     0,   160,
     0,   160,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   160,   160,     0,     0,     0,
     0,     0,     0,     0,    60,     0,     0,    60,   161,
     0,     0,   161,     0,     0,   161,     0,     0,     0,
     0,     0,     0,    60,    60,     0,     0,     0,   160,
   160,   161,   161,     0,   161,     0,   161,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    60,     0,     0,     0,     0,     0,     0,
   161,   161,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    60,     0,
     0,     0,     0,     0,   161,   161,     0,     0,     0,
     0,     0,     0,   148,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   148,     0,   148,
   148,     0,   148,     0,     0,   148,     0,     0,     0,
     0,     0,     0,     0,     0,   148,   148,   148,     0,
   148,   148,   148,   148,   148,   148,   148,   148,   148,
   148,   148,   148,   148,   148,   148,   148,     0,   148,
   148,   148,   148,   148,   148,   148,   148,   148,   148,
   148,   148,   148,   148,   148,   148,   148,   148,   148,
   160,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   160,     0,   160,   160,     0,   160,
     0,     0,   160,     0,     0,     0,     0,     0,     0,
     0,     0,   160,   160,   160,     0,   160,   160,   160,
   160,   160,   160,   160,   160,   160,   160,   160,   160,
   160,   160,   160,   160,     0,   160,   160,   160,   160,
   160,   160,   160,   160,   160,   160,   160,    60,     0,
     0,     0,     0,     0,     0,   161,     0,     0,     0,
     0,    60,     0,    60,    60,     0,    60,     0,   161,
    60,   161,   161,     0,   161,     0,     0,   161,     0,
    60,    60,    60,     0,     0,     0,     0,   161,   161,
   161,     0,   161,   161,   161,   161,   161,   161,   161,
   161,   161,   161,   161,   161,   161,   161,   161,   161,
     0,   161,   161,   161,   161,   161,   161,   161,   161,
   161,   161,   161,   162,     0,     0,   162,     0,     0,
   162,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   162,   162,     0,   162,
     0,   162,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   162,   162,     0,     0,     0,
     0,     0,     0,     0,    63,     0,     0,    63,   163,
     0,     0,   163,     0,     0,   163,     0,     0,     0,
     0,     0,     0,    63,    63,     0,     0,     0,   162,
   162,   163,   163,     0,   163,     0,   163,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,    63,     0,     0,     0,     0,     0,     0,
   163,   163,     0,     0,     0,     0,     0,     0,     0,
    64,     0,     0,    64,   167,     0,     0,   167,     0,
     0,   167,     0,     0,     0,     0,     0,    63,    64,
    64,     0,     0,     0,   163,   163,   167,   167,     0,
   167,     0,   167,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    64,     0,
     0,     0,     0,     0,     0,   167,   167,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    64,     0,     0,     0,     0,     0,
   167,   167,     0,     0,     0,     0,     0,     0,     0,
   162,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   162,     0,   162,   162,     0,   162,
     0,     0,   162,     0,     0,     0,     0,     0,     0,
     0,     0,   162,   162,   162,     0,   162,   162,   162,
   162,   162,   162,   162,   162,   162,   162,   162,   162,
   162,   162,   162,   162,     0,   162,   162,   162,   162,
   162,   162,   162,   162,   162,   162,   162,    63,     0,
     0,     0,     0,     0,     0,   163,     0,     0,     0,
     0,    63,     0,    63,    63,     0,    63,     0,   163,
    63,   163,   163,     0,   163,     0,     0,   163,     0,
    63,    63,    63,     0,     0,     0,     0,   163,   163,
   163,     0,   163,   163,   163,   163,   163,   163,   163,
   163,   163,   163,   163,   163,   163,   163,   163,   163,
     0,   163,   163,   163,   163,   163,   163,   163,   163,
   163,   163,   163,    64,     0,     0,     0,     0,     0,
     0,   167,     0,     0,     0,     0,    64,     0,    64,
    64,     0,    64,     0,   167,    64,   167,   167,     0,
   167,     0,     0,   167,     0,    64,    64,    64,     0,
     0,     0,     0,   167,   167,   167,     0,   167,   167,
   167,   167,   167,   167,   167,   167,   167,   167,   167,
   167,   167,   167,   167,   167,     0,   167,   167,   167,
   167,   167,   167,   167,   167,   167,   167,   167,   168,
     0,     0,   168,     0,     0,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   168,   168,     0,   168,     0,   168,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   168,   168,     0,     0,     0,     0,     0,     0,     0,
    65,     0,     0,    65,   169,     0,     0,   169,     0,
     0,   169,     0,     0,     0,     0,     0,     0,    65,
    65,     0,     0,     0,   168,   168,   169,   169,     0,
   169,     0,   169,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,    65,     0,
     0,     0,     0,     0,     0,   169,   169,     0,     0,
     0,     0,     0,     0,     0,    61,     0,     0,    61,
   170,     0,     0,   170,     0,     0,   170,     0,     0,
     0,     0,     0,    65,    61,    61,     0,     0,     0,
   169,   169,   170,   170,     0,   170,     0,   170,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,    61,     0,     0,     0,     0,     0,
     0,   170,   170,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    61,
     0,     0,     0,     0,     0,   170,   170,     0,     0,
     0,     0,     0,     0,     0,   168,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   168,
     0,   168,   168,     0,   168,     0,     0,   168,     0,
     0,     0,     0,     0,     0,     0,     0,   168,   168,
   168,     0,   168,   168,   168,   168,   168,   168,   168,
   168,   168,   168,   168,   168,   168,   168,   168,   168,
     0,   168,   168,   168,   168,   168,   168,   168,   168,
   168,   168,   168,    65,     0,     0,     0,     0,     0,
     0,   169,     0,     0,     0,     0,    65,     0,    65,
    65,     0,    65,     0,   169,    65,   169,   169,     0,
   169,     0,     0,   169,     0,    65,    65,    65,     0,
     0,     0,     0,   169,   169,   169,     0,   169,   169,
   169,   169,   169,   169,   169,   169,   169,   169,   169,
   169,   169,   169,   169,   169,     0,   169,   169,   169,
   169,   169,   169,   169,   169,   169,   169,   169,    61,
     0,     0,     0,     0,     0,     0,   170,     0,     0,
     0,     0,    61,     0,    61,    61,     0,    61,     0,
   170,    61,   170,   170,     0,   170,     0,     0,   170,
     0,    61,    61,    61,     0,     0,     0,     0,   170,
   170,   170,     0,   170,   170,   170,   170,   170,   170,
   170,   170,   170,   170,   170,   170,   170,   170,   170,
   170,     0,   170,   170,   170,   170,   170,   170,   170,
   170,   170,   170,   170,   144,     0,     0,   144,     0,
     0,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   144,   144,     0,
   144,     0,   144,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   158,   144,   144,   158,     0,
     0,   158,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   158,   158,     0,
   158,     0,   158,     0,     0,     0,     0,     0,     0,
   144,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   159,   158,   158,   159,     0,
     0,   159,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   159,   159,     0,
   159,     0,   159,     0,     0,     0,     0,     0,     0,
   158,   158,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   159,   159,     0,   165,
     0,     0,   165,     0,     0,   165,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   165,   165,     0,   165,     0,   165,     0,     0,
   159,   159,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   165,   165,     0,     0,     0,     0,     0,     0,     0,
     0,   144,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   144,     0,   144,   144,     0,
   144,     0,     0,   144,   165,   165,     0,     0,     0,
     0,     0,     0,   144,   144,   144,     0,   144,   144,
   144,   144,   144,   144,   144,   144,   144,   144,   144,
   144,   144,   144,   144,   144,     0,   144,   144,   144,
   144,   158,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   158,     0,   158,   158,     0,
   158,     0,     0,   158,     0,     0,     0,     0,     0,
     0,     0,     0,   158,   158,   158,     0,   158,   158,
   158,   158,   158,   158,   158,   158,   158,   158,   158,
   158,   158,   158,   158,   158,     0,   158,   158,   158,
   158,   159,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   159,     0,   159,   159,     0,
   159,     0,     0,   159,     0,     0,     0,     0,     0,
     0,     0,     0,   159,   159,   159,     0,   159,   159,
   159,   159,   159,   159,   159,   159,   159,   159,   159,
   159,   159,   159,   159,   159,     0,   159,   159,   159,
   159,     0,     0,     0,     0,   165,   166,     0,     0,
   166,     0,     0,   166,     0,     0,     0,     0,   165,
     0,   165,   165,     0,   165,     0,     0,   165,   166,
   166,     0,   166,     0,   166,     0,     0,   165,   165,
   165,     0,   165,   165,   165,   165,   165,   165,   165,
   165,   165,   165,   165,   165,   165,   165,   165,   165,
     0,   165,   165,   165,   165,     0,   171,   166,   166,
   171,     0,     0,   171,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   171,
   171,     0,   171,     0,   171,     0,     0,     0,     0,
     0,     0,   166,   166,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   164,   171,   171,
   164,     0,     0,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   164,
   164,     0,   164,     0,   164,     0,     0,     0,     0,
     0,     0,   171,   171,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   164,   164,
     0,   172,     0,     0,   172,     0,     0,   172,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   172,   172,     0,   172,     0,   172,
     0,     0,   164,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   172,   172,     0,     0,     0,     0,     0,
     0,     0,     0,   166,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   166,     0,   166,
   166,     0,   166,     0,     0,   166,   172,   172,     0,
     0,     0,     0,     0,     0,   166,   166,   166,     0,
   166,   166,   166,   166,   166,   166,   166,   166,   166,
   166,   166,   166,   166,   166,   166,   166,     0,   166,
   166,   166,   166,   171,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   171,     0,   171,
   171,     0,   171,     0,     0,   171,     0,     0,     0,
     0,     0,     0,     0,     0,   171,   171,   171,     0,
   171,   171,   171,   171,   171,   171,   171,   171,   171,
   171,   171,   171,   171,   171,   171,   171,     0,   171,
   171,   171,   171,   164,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   164,     0,   164,
   164,     0,   164,     0,     0,   164,     0,     0,     0,
     0,     0,     0,     0,     0,   164,   164,   164,     0,
   164,   164,   164,   164,   164,   164,   164,   164,   164,
   164,   164,   164,   164,   164,   164,   164,     0,   164,
   164,   164,   164,     0,     0,     0,     0,   172,     0,
     0,     0,   143,     0,     0,   143,     0,     0,     0,
     0,   172,     0,   172,   172,     0,   172,     0,     0,
   172,   143,   143,     0,   143,     0,   143,     0,     0,
   172,   172,   172,     0,   172,   172,   172,   172,   172,
   172,   172,   172,   172,   172,   172,   172,   172,   172,
   172,   172,     0,   172,   172,   172,   172,     0,     0,
   143,   143,   146,     0,     0,   146,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   146,   146,     0,   146,     0,   146,     0,     0,
     0,     0,     0,     0,   143,   143,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   146,   146,   151,     0,     0,   151,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   151,   151,     0,   151,     0,   151,     0,     0,
     0,     0,     0,     0,   146,   146,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   151,     0,   149,     0,     0,   149,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   149,   149,     0,   149,     0,   149,     0,     0,
     0,     0,     0,     0,     0,   151,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   149,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   143,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   143,
     0,   143,   143,     0,   143,   149,     0,   143,     0,
     0,     0,     0,     0,     0,     0,     0,   143,   143,
   143,     0,   143,   143,   143,   143,   143,   143,   143,
   143,   143,   143,   143,   143,   143,   143,   143,   143,
     0,   143,   143,   143,   143,   146,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   146,
     0,   146,   146,     0,   146,     0,     0,   146,     0,
     0,     0,     0,     0,     0,     0,     0,   146,   146,
   146,     0,   146,   146,   146,   146,   146,   146,   146,
   146,   146,   146,   146,   146,   146,   146,   146,   146,
     0,   146,   146,   146,   146,   151,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   151,
     0,   151,   151,     0,   151,     0,     0,   151,     0,
     0,     0,     0,     0,     0,     0,     0,   151,   151,
   151,     0,   151,   151,   151,   151,   151,   151,   151,
   151,   151,   151,   151,   151,   151,   151,   151,   151,
     0,   151,   151,   151,   151,   149,   150,     0,     0,
   150,     0,     0,     0,     0,     0,     0,     0,   149,
     0,   149,   149,     0,   149,   150,   150,   149,   150,
     0,   150,     0,     0,     0,     0,     0,   149,   149,
   149,     0,   149,   149,   149,   149,   149,   149,   149,
   149,   149,   149,   149,   149,   149,   149,   149,   149,
     0,   149,   149,   149,   150,    45,     0,     0,    45,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    45,    45,     0,    45,     0,
    45,     0,     0,     0,     0,     0,     0,     0,     0,
   150,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   126,     0,    45,   126,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
   126,   126,     0,   126,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    45,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   132,     0,   126,
   132,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   132,   132,     0,   132,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   126,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   129,     0,   132,   129,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   129,   129,     0,   129,     0,     0,     0,     0,
   150,     0,     0,     0,     0,     0,     0,     0,     0,
   132,     0,     0,   150,     0,   150,   150,     0,   150,
     0,     0,   150,     0,     0,     0,     0,     0,     0,
   129,     0,   150,   150,   150,     0,   150,   150,   150,
   150,   150,   150,   150,   150,   150,   150,   150,   150,
   150,   150,   150,   150,     0,   150,   150,   150,    45,
     0,     0,     0,     0,     0,   129,     0,     0,     0,
     0,     0,    45,     0,    45,    45,     0,    45,     0,
     0,    45,     0,     0,     0,     0,     0,     0,     0,
     0,    45,    45,    45,     0,    45,    45,    45,    45,
    45,    45,    45,    45,    45,    45,    45,    45,    45,
    45,    45,    45,     0,   126,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   126,     0,
   126,   126,     0,   126,     0,     0,   126,     0,     0,
     0,     0,     0,     0,     0,     0,   126,   126,   126,
     0,   126,   126,   126,   126,   126,   126,   126,   126,
   126,   126,   126,   126,   126,   126,   126,   126,     0,
   132,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   132,     0,   132,   132,     0,   132,
     0,     0,   132,     0,     0,     0,     0,     0,     0,
     0,     0,   132,   132,   132,     0,   132,   132,   132,
   132,   132,   132,   132,   132,   132,   132,   132,   132,
   132,   132,   132,   132,     0,   129,   157,     0,     0,
   157,     0,     0,     0,     0,     0,     0,     0,   129,
     0,   129,   129,     0,   129,   157,   157,   129,   157,
     0,     0,     0,     0,     0,     0,     0,   129,   129,
   129,     0,   129,   129,   129,   129,   129,   129,   129,
   129,   129,   129,   129,   129,   129,   129,   129,   129,
     0,     0,    62,     0,   157,    62,    71,     0,     0,
    71,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    62,    62,     0,     0,    71,    71,     0,     0,
    70,     0,     0,    70,     0,     0,     0,     0,     0,
   157,     0,     0,     0,     0,     0,     0,     0,    70,
    70,     0,     0,    67,     0,     0,    67,    68,     0,
    62,    68,     0,     0,    71,     0,     0,     0,     0,
     0,     0,    67,    67,     0,     0,    68,    68,     0,
     0,     0,    69,     0,     0,    69,     0,    70,     0,
     0,     0,     0,     0,     0,    62,     0,     0,     0,
    71,    69,    69,     0,     0,    66,     0,     0,    66,
    72,    67,     0,    72,     0,    68,     0,     0,     0,
     0,     0,     0,    70,    66,    66,     0,     0,    72,
    72,     0,     0,    73,     0,     0,    73,     0,     0,
    69,     0,     0,     0,     0,     0,    67,     0,     0,
     0,    68,    73,    73,     0,     0,     0,     0,     0,
     0,     0,     0,    66,     0,     0,     0,    72,     0,
    74,     0,     0,    74,     0,    69,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,    74,
    74,    73,     0,     0,     0,     0,     0,     0,    66,
   157,     0,     0,    72,     0,     0,     0,     0,     0,
     0,     0,     0,   157,     0,   157,   157,     0,   157,
     0,     0,   157,     0,     0,     0,    73,    74,     0,
     0,     0,   157,   157,   157,     0,   157,   157,   157,
   157,   157,   157,   157,   157,   157,   157,   157,   157,
   157,   157,   157,   157,     0,    62,     0,     0,     0,
    71,     0,     0,    74,     0,     0,     0,     0,    62,
     0,    62,    62,    71,    62,    71,    71,    62,    71,
     0,     0,    71,    70,     0,     0,     0,    62,    62,
    62,     0,    71,    71,    71,     0,    70,     0,    70,
    70,     0,    70,     0,     0,    70,    67,     0,     0,
     0,    68,     0,     0,     0,    70,    70,    70,     0,
    67,     0,    67,    67,    68,    67,    68,    68,    67,
    68,     0,     0,    68,     0,    69,     0,     0,    67,
    67,    67,     0,    68,    68,    68,     0,     0,    69,
     0,    69,    69,     0,    69,     0,     0,    69,    66,
     0,     0,     0,    72,     0,     0,     0,    69,    69,
    69,     0,    66,     0,    66,    66,    72,    66,    72,
    72,    66,    72,     0,     0,    72,    73,     0,     0,
     0,    66,    66,    66,     0,    72,    72,    72,     0,
    73,     0,    73,    73,     0,    73,    76,     0,    73,
    76,    75,     0,     0,    75,     0,     0,     0,    73,
    73,    73,     0,    74,     0,    76,    76,     0,     0,
    75,    75,     0,     0,     0,     0,    74,     0,    74,
    74,     0,    74,     0,     0,    74,     0,     0,     0,
     0,     0,     0,     0,     0,    74,    74,    74,     0,
     0,     0,     0,     0,    76,     0,     0,     0,    75,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    76,     0,     0,     0,    75,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,
    76,     0,     0,     0,    75,     0,     0,     0,     0,
     0,     0,     0,    76,     0,    76,    76,    75,    76,
    75,    75,    76,    75,     0,     0,    75,     0,     0,
     0,     0,    76,    76,    76,     0,    75,    75,    75,};
}

 
static short yycheck[][] = new short[2][];
static { yycheck0(); yycheck1();}
static void yycheck0(){
yycheck[0] = new short[] {
    21,    22,    23,     0,    25,    26,    29,     0,    91,
    46,    47,    48,    35,    36,    59,    38,    39,    40,
    41,    42,    43,    44,    45,    59,    59,    59,    59,
    36,    37,   318,   388,    40,    55,    56,    57,    91,
   265,    91,    37,    38,   123,   278,   271,    42,    43,
    34,    45,    46,    47,    35,    39,   123,    53,    54,
   318,    64,    93,    89,   347,   318,    97,    60,   403,
    62,    63,    34,   424,   123,   318,   123,    39,    36,
    37,   414,   281,    40,   113,   284,   419,   125,    36,
    37,   346,   347,    40,    36,    37,   346,   318,   450,
   271,   125,    91,    36,    37,    94,   268,   269,    19,
    64,    59,   442,   443,    24,   127,   287,   288,    96,
    64,    59,   147,   271,   271,    64,   475,   116,   117,
   118,   119,   120,   271,    64,   271,    40,   123,   124,
   149,    96,   125,   281,    44,   283,   284,   123,   286,
   261,   261,   289,   261,   261,   163,   164,   165,   166,
   167,    49,   169,   261,   171,   172,   173,   174,   175,
   176,   177,   178,   179,   180,   181,   182,   183,   184,
   185,   186,   187,   188,   189,   190,   191,   192,   193,
   194,   195,   196,   197,   198,   199,   200,   201,   202,
   203,   204,   205,   206,   207,   208,   209,   210,   211,
   212,   213,   214,   215,   216,   217,   218,   219,   220,
   221,   222,   261,   105,   225,   318,    40,    59,    40,
   230,   123,    55,    56,   248,   244,    37,   265,   266,
   267,   318,    42,   242,   271,    40,    40,    47,   348,
    41,    68,    93,   125,    39,   253,   252,   261,   256,
   257,   281,   261,   283,   284,    34,   286,    96,   261,
   289,   267,   261,   261,    59,    40,   125,    41,   274,
    41,   261,    91,   278,   339,   340,   261,   342,   343,
   344,   345,   125,   271,   271,   170,    91,   277,   271,
   279,   348,   125,   115,   261,   261,   271,   271,    59,
   121,   271,   278,   123,   271,   126,   278,   123,   271,
   130,   131,   132,   345,   134,   271,   136,   137,   138,
   139,   271,   123,   271,   123,   271,   348,   293,   294,
   295,   296,   297,   155,   271,   271,   348,   348,   156,
   348,   271,   159,   270,   161,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
   363,   342,   343,   344,   345,   251,   349,   271,   351,
   388,   123,   376,    59,   125,   379,   123,   390,   123,
   123,    40,    40,   125,   271,   386,   271,   125,    41,
   125,   391,   392,   393,   125,   125,    59,    59,   409,
   123,   380,   381,   382,    40,   384,   405,   406,   228,
   424,   123,   421,     0,   125,   125,   123,   125,   427,
   428,   429,   123,    59,    59,   125,    59,   249,   250,
    59,    59,    59,    59,    59,    41,    59,   435,   436,
   415,   416,    48,    21,   105,    37,   147,    -1,    -1,
    -1,   266,    -1,   447,    -1,    -1,    -1,   462,    -1,
    -1,   275,    -1,   467,   457,    -1,    -1,    -1,   463,
   473,    -1,   285,   286,   287,   288,   289,   469,    -1,
   292,    -1,   294,   295,   296,   297,   298,   299,   300,
   301,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,   318,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   341,   342,   343,   344,   345,
     0,   342,   343,   344,   345,    -1,   352,    -1,   354,
    -1,   338,   339,   340,   359,   342,   343,   344,   345,
    37,   365,    -1,   367,   368,    42,    43,    -1,    45,
    46,    47,   375,    -1,   377,    -1,    33,    34,    -1,
    36,    37,    38,    39,    40,    -1,    -1,    43,    -1,
    45,    -1,    -1,    -1,   395,    -1,   397,    -1,    -1,
    -1,    -1,    -1,    37,    -1,    59,    60,    -1,    42,
    43,    64,    45,    46,    47,    -1,    -1,    -1,    -1,
    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    41,    -1,    -1,
    44,    91,    92,    -1,    -1,    -1,    96,    -1,    -1,
   445,   446,    -1,    -1,    -1,   123,    59,    -1,    -1,
    -1,    33,    34,    91,    36,    37,    38,    39,    40,
    -1,    -1,    43,   466,    45,    -1,   123,    -1,   125,
   126,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    -1,    -1,    93,    64,    -1,    -1,   123,
    -1,    -1,    -1,    -1,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,
   125,    96,    -1,    -1,    -1,    60,    -1,    -1,    -1,
    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    -1,    -1,   126,    -1,    -1,    -1,    -1,
    91,    92,    -1,    -1,    -1,    96,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,   126,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    41,    -1,    -1,    44,   256,   257,   258,   259,   260,
    -1,    -1,   263,   264,    -1,    -1,    -1,    -1,    58,
    59,    -1,   272,   273,   274,    -1,    -1,   277,   278,
   279,   280,   281,   282,   283,   284,   285,   286,    -1,
    -1,   289,   290,   291,   292,   293,   294,   295,   296,
   297,    -1,    -1,    -1,   301,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   318,   338,   339,   340,    -1,   342,
   343,   344,   345,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,   125,    -1,   262,   263,   264,    -1,
    -1,   343,   344,    -1,   346,   347,   272,   273,   274,
   275,   276,   277,    -1,   279,   336,   337,   338,   339,
   340,    -1,   342,   343,   344,   345,    -1,    -1,    -1,
   293,   294,   295,   296,   297,    -1,    -1,    -1,   301,
    -1,   263,   264,    41,    -1,    -1,    44,    -1,    -1,
    -1,   272,   273,   274,    -1,    -1,   277,   318,   279,
    -1,    -1,    58,    59,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   293,   294,   295,   296,   297,
    -1,    -1,    -1,   301,    -1,   343,   344,    -1,   346,
   347,   348,    33,    34,    -1,    36,    37,    38,    39,
    40,    93,   318,    43,    -1,    45,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    59,    60,    -1,    -1,    -1,    64,    -1,    -1,
   343,   344,    -1,   346,   347,   348,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,   269,    -1,    -1,    91,    92,    -1,
    -1,    -1,    96,    58,    59,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    33,    34,    -1,
    36,    37,    38,    39,    40,   298,   299,    43,    -1,
    45,    -1,   123,    -1,   125,   126,    -1,    -1,    -1,
    -1,    -1,    93,    -1,    -1,    59,    60,    -1,    -1,
    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    41,    -1,    43,    -1,    45,    -1,    -1,   125,    -1,
    -1,    91,    92,    -1,    -1,    -1,    96,    -1,    -1,
    -1,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,
   126,    41,    -1,    -1,    44,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,    -1,   269,    -1,    -1,
    -1,    59,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,   123,    -1,    -1,   126,    -1,    -1,    -1,   298,
   299,   300,    -1,    -1,    -1,    -1,    -1,    -1,    93,
   256,   257,   258,   259,   260,    -1,    -1,   263,   264,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   272,   273,
   274,    -1,    -1,   277,    -1,   279,   280,   281,   282,
   283,   284,   285,   286,   125,    -1,   289,   290,   291,
   292,   293,   294,   295,   296,   297,    -1,    -1,    -1,
   301,    -1,    -1,    -1,    -1,    -1,    -1,   269,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   318,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,    -1,
   289,    -1,    -1,    -1,   256,   257,   258,   259,   260,
   298,   299,   263,   264,    -1,    -1,   343,   344,    -1,
   346,   347,   272,   273,   274,    -1,    -1,   277,    -1,
   279,   280,   281,   282,   283,   284,   285,   286,    41,
    -1,   289,   290,   291,   292,   293,   294,   295,   296,
   297,    -1,    -1,    -1,   301,   262,   263,   264,    59,
    -1,    -1,    -1,    -1,    -1,    -1,   272,   273,   274,
   275,   276,   277,   318,   279,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    41,    -1,    -1,    -1,    -1,    -1,
   293,   294,   295,   296,   297,    -1,    93,    -1,   301,
    -1,   343,   344,    59,   346,   347,    -1,    33,    34,
    35,    36,    37,    38,    39,    40,    -1,   318,    43,
    -1,    45,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,   125,   286,    -1,    -1,   289,    60,    -1,
    -1,    93,    64,    -1,    -1,   343,   344,    -1,   346,
   347,    -1,    33,    34,    -1,    36,    37,    38,    39,
    40,    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,
    -1,    -1,    91,    92,    -1,    -1,   125,    96,    -1,
    -1,    59,    60,    -1,    -1,    -1,    64,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,
    -1,   126,    -1,    -1,    -1,    -1,    91,    92,    -1,
    -1,    -1,    96,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    33,    34,    -1,
    36,    37,    38,    39,    40,    -1,    -1,    43,    -1,
    45,    -1,   123,    -1,    -1,   126,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,
    -1,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    92,    -1,    -1,   281,    96,   283,   284,
    -1,   286,    -1,    -1,   289,    -1,    37,    -1,    -1,
    -1,    -1,    42,    43,    -1,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,    -1,    -1,
   126,    -1,    60,    -1,    62,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,   261,    -1,   263,   264,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   272,   273,   274,    91,    -1,   277,
    -1,   279,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   293,   294,   295,
   296,   297,    -1,    -1,    -1,   301,    -1,   263,   264,
    -1,    -1,   123,    -1,    -1,    -1,    -1,   272,   273,
   274,    -1,    -1,   277,   318,   279,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   293,   294,   295,   296,   297,    -1,    -1,    -1,
   301,    -1,   343,   344,    -1,   346,   347,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   318,
    -1,    -1,    -1,    -1,    -1,    -1,    33,    34,    -1,
    36,    37,    38,    39,    40,    -1,    -1,    43,    -1,
    45,    -1,   263,   264,    -1,    -1,   343,   344,    -1,
   346,   347,   272,   273,   274,    -1,    60,   277,    -1,
   279,    64,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   293,   294,   295,   296,
   297,    -1,    -1,    -1,   301,    -1,    -1,    -1,    -1,
    -1,    91,    92,    -1,    -1,    -1,    96,    -1,    -1,
    -1,    -1,    -1,   318,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    41,    -1,    43,    -1,    45,    -1,   123,    -1,    -1,
   126,   343,   344,    -1,   346,   347,    -1,    -1,    -1,
    -1,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,    60,    -1,    -1,    -1,
    64,    -1,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,    -1,   342,   343,   344,   345,
    -1,   123,    -1,    -1,   126,    -1,    -1,    -1,    -1,
    91,    92,    93,    -1,    -1,    96,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,   123,    -1,    -1,   126,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
   261,    -1,   263,   264,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   272,   273,   274,    -1,    -1,   277,    -1,
   279,    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,   293,   294,   295,   296,
   297,    -1,    -1,    -1,   301,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    -1,   318,   126,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   263,   264,    -1,
    -1,   343,   344,    -1,   346,   347,   272,   273,   274,
    -1,    -1,   277,    -1,   279,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   293,   294,   295,   296,   297,    -1,    -1,    -1,   301,
    -1,   263,   264,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   272,   273,   274,    -1,    -1,   277,   318,   279,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   293,   294,   295,   296,   297,
    -1,    -1,    -1,   301,    -1,   343,   344,    -1,   346,
   347,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   318,    -1,    -1,    33,    34,    -1,    36,
    37,    38,    39,    40,    -1,    -1,    43,    -1,    45,
    -1,    -1,    -1,    -1,   261,    -1,   263,   264,    -1,
   343,   344,    -1,   346,   347,    60,   272,   273,   274,
    64,    -1,   277,    -1,   279,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   293,   294,   295,   296,   297,    -1,    -1,    -1,   301,
    91,    92,    -1,    -1,    -1,    96,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   318,    -1,
    -1,    33,    34,    -1,    36,    37,    38,    39,    40,
    -1,    -1,    43,    -1,    45,   123,    -1,   125,   126,
    -1,    -1,    -1,    -1,    -1,   343,   344,    -1,   346,
   347,    60,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    33,    34,    -1,    36,    37,    -1,    39,    40,
    -1,    -1,    43,    -1,    45,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,    64,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    -1,    -1,   126,    91,    92,    -1,    -1,
    -1,    96,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,    -1,    -1,   126,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   263,   264,    58,    59,    -1,    -1,    -1,    -1,
    -1,   272,   273,   274,    -1,    -1,   277,    -1,   279,
    -1,    37,    38,    -1,    40,    41,    42,    43,    44,
    45,    46,    47,    -1,   293,   294,   295,   296,   297,
    -1,    -1,    93,   301,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   318,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,
    -1,    91,    -1,    93,    94,    -1,   263,   264,    -1,
   343,   344,    -1,   346,   347,    -1,   272,   273,   274,
    -1,    -1,   277,    -1,   279,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,   125,
   293,   294,   295,   296,   297,    -1,   263,   264,   301,
    -1,    -1,    -1,    -1,    -1,    -1,   272,   273,   274,
    -1,    -1,   277,    -1,   279,    -1,    -1,   318,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   293,   294,   295,   296,   297,    -1,    -1,    -1,   301,
    -1,    -1,    -1,    -1,    -1,   343,   344,    -1,   346,
   347,    -1,    -1,    -1,    -1,    37,    38,   318,    40,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,   343,   344,    -1,   346,
   347,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   269,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,    94,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,    -1,
   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   298,   299,   300,    -1,    -1,    -1,    -1,    -1,   269,
    -1,   123,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    -1,    -1,    -1,    -1,    -1,
    -1,    37,    38,    -1,    40,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,   123,   124,   125,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    37,
    38,    -1,    40,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,   125,    -1,   269,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    37,    38,    -1,    40,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
   123,   124,   125,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    -1,   342,   343,
   344,   345,    37,    38,    -1,    40,    41,    42,    43,
    44,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
   125,    -1,   269,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,   284,
    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,    -1,   302,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   317,    -1,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,    -1,   342,   343,   344,   345,    37,    38,
    -1,    40,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,   123,   124,   125,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,   343,   344,   345,    37,    38,    -1,    40,
    41,    42,    43,    44,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,   125,    -1,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,    -1,   342,   343,   344,
   345,    37,    38,    -1,    -1,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    93,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,   123,   124,   125,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,   269,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    37,    38,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    -1,   342,   343,
   344,   345,    37,    38,    -1,    -1,    -1,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    37,    91,    -1,    -1,    94,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,    -1,   302,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   317,   123,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,    -1,   342,   343,   344,   345,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,   343,   344,   345,    37,    38,    -1,    -1,
    41,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    -1,    -1,    -1,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    -1,    91,    -1,    -1,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,    -1,   342,   343,   344,
   345,    37,    38,    -1,    -1,    41,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    37,
    38,    -1,    -1,    41,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    37,    38,    -1,    -1,    41,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    -1,   342,   343,
   344,   345,    37,    38,    -1,    -1,    41,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,    -1,   302,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   317,    -1,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,    -1,   342,   343,   344,   345,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,   343,   344,   345,    37,    38,    -1,    -1,
    41,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,    -1,   342,   343,   344,
   345,    37,    38,    -1,    -1,    41,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    37,
    38,    -1,    -1,    41,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    37,    38,    -1,    -1,    -1,
    42,    43,    -1,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    -1,   342,   343,
   344,   345,    37,    38,    -1,    -1,    -1,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    -1,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   123,   124,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,    -1,   302,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   317,    -1,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
   339,   340,    -1,   342,   343,   344,   345,    37,    38,
    -1,    -1,    -1,    42,    43,    -1,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,    -1,
    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   123,   124,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,   343,   344,   345,    37,    38,    -1,    -1,
    -1,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    61,    62,    63,    37,    38,    -1,    -1,
    -1,    42,    43,    -1,    45,    46,    47,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    60,    -1,    62,    -1,    91,    -1,    -1,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    91,    -1,    -1,    94,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   123,   124,    -1,    -1,    -1,    -1,    -1,    -1,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   339,   340,    -1,   342,   343,   344,
   345,    37,    38,    -1,    -1,    -1,    42,    43,    -1,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,    62,
    -1,    -1,    37,    38,    -1,    -1,    -1,    42,    43,
    -1,    45,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    60,    -1,
    62,    91,    -1,    -1,    94,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    91,    -1,    -1,    -1,   123,   124,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,   123,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    -1,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   338,   339,   340,    -1,   342,   343,   344,   345,    37,
    38,    -1,    -1,    -1,    42,    43,    -1,    45,    46,
    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    60,    -1,    62,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    91,
    -1,    -1,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    38,   123,   124,    41,    42,    -1,
    44,    -1,    46,    47,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    62,    63,    -1,    -1,    -1,    -1,    -1,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,   343,   344,   345,    93,    94,    -1,    -1,    -1,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,   343,   344,   345,    -1,    37,    38,   124,
   125,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    37,    38,   124,   125,    41,    42,    43,    44,
    45,    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    60,    61,    62,
    63,    -1,    -1,    -1,    -1,    -1,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    -1,   342,   343,
   344,   345,    -1,    93,    94,    -1,    -1,    -1,    -1,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,   124,   125,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,   338,   339,   340,
    -1,   342,    -1,    -1,   345,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,    -1,   342,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   269,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   319,   320,   321,   322,   323,
   324,   325,   326,   327,   328,   329,   330,   331,   332,
   333,   334,   335,   336,   337,   338,   339,   340,    -1,
   342,    41,    42,    -1,    44,    -1,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    41,    42,    -1,    44,    -1,    46,    47,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   269,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   269,   342,    -1,    -1,   345,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,    -1,   342,   269,    -1,   345,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,
    38,    -1,    -1,    41,    42,    43,    44,    45,    46,
    47,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   269,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,    -1,    -1,   269,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   338,   339,   340,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,    -1,
    -1,    41,    42,    43,    44,    45,    46,    47,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,
    58,    59,    60,    61,    62,    63,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,
    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    37,    38,    -1,    -1,    41,
    42,    43,    44,    45,    46,    47,    -1,    -1,    -1,
    -1,    -1,    -1,   124,   125,    -1,    -1,    58,    59,
    60,    61,    62,    63,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   124,   125,    -1,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   339,   340,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   319,   320,   321,   322,   323,   324,   325,   326,   327,
   328,   329,   330,   331,   332,   333,   334,   335,   336,
   337,   338,   269,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,   284,
    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   298,   299,   300,    -1,   302,
   303,   304,   305,   306,   307,   308,   309,   310,   311,
   312,   313,   314,   315,   316,   317,    -1,   319,   320,
   321,   322,   323,   324,   325,   326,   327,   328,   329,
   330,   331,   332,   333,   334,   335,   336,   337,   338,
    37,    38,    -1,    -1,    41,    42,    43,    44,    45,
    46,    47,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    60,    61,    62,    63,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    37,    38,
    -1,    -1,    41,    42,    43,    44,    45,    46,    47,
    -1,    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,
    -1,    58,    59,    60,    61,    62,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,    -1,
    41,    -1,    43,    44,    45,    46,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,   269,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,    -1,
   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   298,   299,   300,    -1,   302,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,   317,    -1,   319,   320,   321,   322,   323,   324,
   325,   326,   327,   328,   329,   330,   331,   332,   333,
   334,   335,   336,   337,   338,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   330,   331,   332,   333,   334,   335,
   336,   337,   338,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
    38,    -1,    -1,    41,    -1,    43,    44,    45,    46,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    60,    61,    62,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    -1,    -1,
    41,    -1,    43,    44,    45,    46,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    38,    -1,    -1,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    58,    59,    60,    61,};
}
static void yycheck1(){
yycheck[1] = new short[] {
    62,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   124,
   125,    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   281,    -1,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,   330,   331,   332,   333,   334,
   335,   336,   337,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   330,   331,
   332,   333,   334,   335,   336,   337,    38,    -1,    -1,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    60,    61,    62,    63,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    38,    -1,    -1,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    38,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    -1,    -1,   124,
   125,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   323,   324,   325,   326,   327,   328,
   329,   330,   331,   332,   333,   334,   335,   336,   337,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   269,    -1,
    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,    -1,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,   281,
   289,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
   298,   299,   300,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,    38,    -1,    -1,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,    38,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    -1,    -1,   124,
   125,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    41,    -1,    -1,    44,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,   125,    58,
    59,    -1,    -1,    -1,   124,   125,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   322,
   323,   324,   325,   326,   327,   328,   329,   269,    -1,
    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,    -1,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,   281,
   289,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
   298,   299,   300,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   269,    -1,    -1,    -1,    -1,    -1,
    -1,   269,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,   281,   289,   283,   284,    -1,
   286,    -1,    -1,   289,    -1,   298,   299,   300,    -1,
    -1,    -1,    -1,   298,   299,   300,    -1,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,    -1,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,    38,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    41,    -1,    -1,    44,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    -1,    -1,   124,   125,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    41,    -1,    -1,    44,
    38,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    -1,    -1,    -1,   125,    58,    59,    -1,    -1,    -1,
   124,   125,    58,    59,    -1,    61,    -1,    63,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    -1,    -1,    -1,    -1,    -1,
    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   125,
    -1,    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   323,   324,   325,   326,
   327,   328,   329,   269,    -1,    -1,    -1,    -1,    -1,
    -1,   269,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,   281,   289,   283,   284,    -1,
   286,    -1,    -1,   289,    -1,   298,   299,   300,    -1,
    -1,    -1,    -1,   298,   299,   300,    -1,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,    -1,   319,   320,   321,
   322,   323,   324,   325,   326,   327,   328,   329,   269,
    -1,    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
   281,   289,   283,   284,    -1,   286,    -1,    -1,   289,
    -1,   298,   299,   300,    -1,    -1,    -1,    -1,   298,
   299,   300,    -1,   302,   303,   304,   305,   306,   307,
   308,   309,   310,   311,   312,   313,   314,   315,   316,
   317,    -1,   319,   320,   321,   322,   323,   324,   325,
   326,   327,   328,   329,    38,    -1,    -1,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    38,    93,    94,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    38,    93,    94,    41,    -1,
    -1,    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    58,    59,    -1,
    61,    -1,    63,    -1,    -1,    -1,    -1,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    93,    94,    -1,    38,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
   124,   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   281,    -1,   283,   284,    -1,
   286,    -1,    -1,   289,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,   298,   299,   300,    -1,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,    -1,   319,   320,   321,
   322,   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   281,    -1,   283,   284,    -1,
   286,    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   298,   299,   300,    -1,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,    -1,   319,   320,   321,
   322,   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,   281,    -1,   283,   284,    -1,
   286,    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   298,   299,   300,    -1,   302,   303,
   304,   305,   306,   307,   308,   309,   310,   311,   312,
   313,   314,   315,   316,   317,    -1,   319,   320,   321,
   322,    -1,    -1,    -1,    -1,   269,    38,    -1,    -1,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,    -1,    38,    93,    94,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    38,    93,    94,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    61,    -1,    63,    -1,    -1,    -1,    -1,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    93,    94,
    -1,    38,    -1,    -1,    41,    -1,    -1,    44,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    58,    59,    -1,    61,    -1,    63,
    -1,    -1,   124,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    93,    94,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,   124,   125,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,   269,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
   302,   303,   304,   305,   306,   307,   308,   309,   310,
   311,   312,   313,   314,   315,   316,   317,    -1,   319,
   320,   321,   322,    -1,    -1,    -1,    -1,   269,    -1,
    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,   281,    -1,   283,   284,    -1,   286,    -1,    -1,
   289,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
   298,   299,   300,    -1,   302,   303,   304,   305,   306,
   307,   308,   309,   310,   311,   312,   313,   314,   315,
   316,   317,    -1,   319,   320,   321,   322,    -1,    -1,
    93,    94,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    94,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,   124,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    -1,    41,    -1,    -1,    44,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    61,    -1,    63,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,   125,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   269,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,   322,   269,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    58,    59,   289,    61,
    -1,    63,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,   319,   320,   321,    93,    41,    -1,    -1,    44,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    58,    59,    -1,    61,    -1,
    63,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    41,    -1,    93,    44,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    58,    59,    -1,    61,    -1,    -1,    -1,    -1,    -1,
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
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,
    93,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   319,   320,   321,   269,
    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,
    -1,    -1,   281,    -1,   283,   284,    -1,   286,    -1,
    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,   298,   299,   300,    -1,   302,   303,   304,   305,
   306,   307,   308,   309,   310,   311,   312,   313,   314,
   315,   316,   317,    -1,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,    -1,
   283,   284,    -1,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,   300,
    -1,   302,   303,   304,   305,   306,   307,   308,   309,
   310,   311,   312,   313,   314,   315,   316,   317,    -1,
   269,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   269,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    58,    59,   289,    61,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,   298,   299,
   300,    -1,   302,   303,   304,   305,   306,   307,   308,
   309,   310,   311,   312,   313,   314,   315,   316,   317,
    -1,    -1,    41,    -1,    93,    44,    41,    -1,    -1,
    44,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    58,    59,    -1,    -1,    58,    59,    -1,    -1,
    41,    -1,    -1,    44,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    -1,    -1,    41,    -1,    -1,    44,    41,    -1,
    93,    44,    -1,    -1,    93,    -1,    -1,    -1,    -1,
    -1,    -1,    58,    59,    -1,    -1,    58,    59,    -1,
    -1,    -1,    41,    -1,    -1,    44,    -1,    93,    -1,
    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,    -1,
   125,    58,    59,    -1,    -1,    41,    -1,    -1,    44,
    41,    93,    -1,    44,    -1,    93,    -1,    -1,    -1,
    -1,    -1,    -1,   125,    58,    59,    -1,    -1,    58,
    59,    -1,    -1,    41,    -1,    -1,    44,    -1,    -1,
    93,    -1,    -1,    -1,    -1,    -1,   125,    -1,    -1,
    -1,   125,    58,    59,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    93,    -1,    -1,    -1,    93,    -1,
    41,    -1,    -1,    44,    -1,   125,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    58,
    59,    93,    -1,    -1,    -1,    -1,    -1,    -1,   125,
   269,    -1,    -1,   125,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,    -1,   286,
    -1,    -1,   289,    -1,    -1,    -1,   125,    93,    -1,
    -1,    -1,   298,   299,   300,    -1,   302,   303,   304,
   305,   306,   307,   308,   309,   310,   311,   312,   313,
   314,   315,   316,   317,    -1,   269,    -1,    -1,    -1,
   269,    -1,    -1,   125,    -1,    -1,    -1,    -1,   281,
    -1,   283,   284,   281,   286,   283,   284,   289,   286,
    -1,    -1,   289,   269,    -1,    -1,    -1,   298,   299,
   300,    -1,   298,   299,   300,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,   269,    -1,    -1,
    -1,   269,    -1,    -1,    -1,   298,   299,   300,    -1,
   281,    -1,   283,   284,   281,   286,   283,   284,   289,
   286,    -1,    -1,   289,    -1,   269,    -1,    -1,   298,
   299,   300,    -1,   298,   299,   300,    -1,    -1,   281,
    -1,   283,   284,    -1,   286,    -1,    -1,   289,   269,
    -1,    -1,    -1,   269,    -1,    -1,    -1,   298,   299,
   300,    -1,   281,    -1,   283,   284,   281,   286,   283,
   284,   289,   286,    -1,    -1,   289,   269,    -1,    -1,
    -1,   298,   299,   300,    -1,   298,   299,   300,    -1,
   281,    -1,   283,   284,    -1,   286,    41,    -1,   289,
    44,    41,    -1,    -1,    44,    -1,    -1,    -1,   298,
   299,   300,    -1,   269,    -1,    58,    59,    -1,    -1,
    58,    59,    -1,    -1,    -1,    -1,   281,    -1,   283,
   284,    -1,   286,    -1,    -1,   289,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,   298,   299,   300,    -1,
    -1,    -1,    -1,    -1,    93,    -1,    -1,    -1,    93,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   125,    -1,    -1,    -1,   125,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
   269,    -1,    -1,    -1,   269,    -1,    -1,    -1,    -1,
    -1,    -1,    -1,   281,    -1,   283,   284,   281,   286,
   283,   284,   289,   286,    -1,    -1,   289,    -1,    -1,
    -1,    -1,   298,   299,   300,    -1,   298,   299,   300,};
}

 
final static short YYFINAL=1;
final static short YYMAXTOKEN=349;
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
null,null,null,null,null,null,null,null,null,null,null,null,"COMENTARIO",
"DECLARACION_TIPO","IMPORT_JAVA","LINEA_JAVA","VAR","FILE","ENTERO","DECIMAL",
"M_REX","S_REX","Y_REX","TEXTO","EXP_SEP","REX_MOD","SEP","STDIN","STDOUT",
"STDERR","STDOUT_H","STDERR_H","MY","SUB","OUR","PACKAGE","WHILE","DO","FOR",
"UNTIL","USE","IF","ELSIF","ELSE","UNLESS","LAST","NEXT","RETURN","Q","QQ","QR",
"QW","QX","LLOR","LLXOR","LLAND","LLNOT","MULTI_IGUAL","DIV_IGUAL","MOD_IGUAL",
"MAS_IGUAL","MENOS_IGUAL","DESP_I_IGUAL","DESP_D_IGUAL","AND_IGUAL","OR_IGUAL",
"XOR_IGUAL","POW_IGUAL","LAND_IGUAL","LOR_IGUAL","DLOR_IGUAL","CONCAT_IGUAL",
"X_IGUAL","ID","DOS_PUNTOS","LOR","DLOR","LAND","NUM_EQ","NUM_NE","STR_EQ",
"STR_NE","CMP","CMP_NUM","SMART_EQ","NUM_LE","NUM_GE","STR_LT","STR_LE",
"STR_GT","STR_GE","DESP_I","DESP_D","X","STR_REX","STR_NO_REX","UNITARIO","POW",
"MAS_MAS","MENOS_MENOS","FLECHA","ID_P","ID_L","AMBITO","CONTEXTO",
};
final static String yyrule[] = {
"$accept : raiz",
"raiz : fuente",
"fuente : masFuente cuerpo",
"masFuente :",
"masFuente : fuente funcionDef",
"funcionDef : funcionSub '{' cuerpo '}'",
"funcionSub : SUB ID_L",
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
"modulos : USE paqueteID ID ';'",
"modulos : USE ID ';'",
"modulos : DO cadena ';'",
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
"cadena : '\\'' cadenaTexto '\\''",
"cadena : '\"' cadenaTexto '\"'",
"cadena : '`' cadenaTexto '`'",
"cadena : Q SEP cadenaTexto SEP",
"cadena : QW SEP cadenaTexto SEP",
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
"funcion : ID_L '{' expresion '}' expresion",
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
"bloque : WHILE abrirBloque '(' expresion ')' abrirBloque '{' cuerpo '}'",
"bloque : UNTIL abrirBloque '(' expresion ')' abrirBloque '{' cuerpo '}'",
"bloque : DO abrirBloque '{' cuerpo '}' WHILE abrirBloque '(' expresion ')' ';'",
"bloque : DO abrirBloque '{' cuerpo '}' UNTIL abrirBloque '(' expresion ')' ';'",
"bloque : FOR abrirBloque '(' listaFor ';' listaFor ';' listaFor ')' abrirBloque '{' cuerpo '}'",
"bloque : FOR abrirBloque variable colParen abrirBloque '{' cuerpo '}'",
"bloque : FOR abrirBloque colParen abrirBloque '{' cuerpo '}'",
"bloque : IF abrirBloque '(' expresion ')' abrirBloque '{' cuerpo '}' condicional",
"bloque : UNLESS abrirBloque '(' expresion ')' abrirBloque '{' cuerpo '}' condicional",
"condicional :",
"condicional : ELSIF '(' expresion ')' abrirBloque '{' cuerpo '}' condicional",
"condicional : ELSE abrirBloque '{' cuerpo '}'",
};

//#line 334 "parser.y"

	private List<Simbolo> simbolos;
	private PreParser preParser;
	private Opciones opciones;
	private GestorErrores gestorErrores;
	private Lista[] args;
	
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
		args= new Lista[1];//Para usar el valor por referencia
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
	 * @param <T> Tipo del simoblo
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
	 * Función interna auxiliar que añade el simbolo a la lista de analizador
	 * justo antes que otro simbolo y luego lo retorna.
	 * @param next Simbolo que siguiente
	 * @param s Simbolo
	 * @return Simbolo s
	 */
	private <T extends Simbolo>  T addBefore(T s, Simbolo next){
		simbolos.add(simbolos.lastIndexOf(next),s);
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
	
	
//#line 3427 "Parser.java"
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
//#line 78 "parser.y"
{yyval=set(new Raiz(add(s(val_peek(0)))));}
break;
case 2:
//#line 80 "parser.y"
{yyval=set(Fuente.addCuerpo(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 3:
//#line 82 "parser.y"
{yyval=set(new Fuente(), false);}
break;
case 4:
//#line 83 "parser.y"
{yyval=set(Fuente.addFuncion(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 5:
//#line 85 "parser.y"
{yyval=set(new FuncionDef(s(val_peek(3)), s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 6:
//#line 87 "parser.y"
{yyval=set(new FuncionSub(s(val_peek(1)), s(val_peek(0))));}
break;
case 7:
//#line 88 "parser.y"
{yyval=set(new FuncionSub(s(val_peek(1)), s(val_peek(0))));}
break;
case 8:
//#line 90 "parser.y"
{yyval=set(new Cuerpo(s(val_peek(0))),false);}
break;
case 9:
//#line 91 "parser.y"
{yyval=set(Cuerpo.add(s(val_peek(1)), s(val_peek(0))), false);}
break;
case 10:
//#line 93 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 11:
//#line 95 "parser.y"
{yyval=set(new Cuerpo());}
break;
case 12:
//#line 96 "parser.y"
{yyval=val_peek(0);}
break;
case 13:
//#line 98 "parser.y"
{yyval=set(new StcLista(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 14:
//#line 99 "parser.y"
{yyval=set(new StcLista(new Lista(), add(new ModNada()), s(val_peek(0))));}
break;
case 15:
//#line 100 "parser.y"
{yyval=set(new StcBloque(s(val_peek(0))));}
break;
case 16:
//#line 101 "parser.y"
{yyval=set(new StcFlujo(s(val_peek(0))));}
break;
case 17:
//#line 102 "parser.y"
{yyval=set(new StcModulos(s(val_peek(0))));}
break;
case 18:
//#line 103 "parser.y"
{yyval=set(new StcImport(s(val_peek(0))));}
break;
case 19:
//#line 104 "parser.y"
{yyval=set(new StcLineaJava(s(val_peek(0))));}
break;
case 20:
//#line 105 "parser.y"
{yyval=set(new StcComentario(s(val_peek(0))));}
break;
case 21:
//#line 106 "parser.y"
{yyval=set(new StcTipado(s(val_peek(0))));}
break;
case 22:
//#line 107 "parser.y"
{yyval=set(new StcError());}
break;
case 23:
//#line 108 "parser.y"
{yyval=set(new StcError());}
break;
case 24:
//#line 110 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 25:
//#line 111 "parser.y"
{yyval=set(new ModuloUse(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 26:
//#line 112 "parser.y"
{yyval=set(new ModuloDo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 27:
//#line 113 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(3)),Paquetes.addId(s(val_peek(2)),s(val_peek(1))),s(val_peek(0))));}
break;
case 28:
//#line 114 "parser.y"
{yyval=set(new ModuloPackage(s(val_peek(2)),add(new Paquetes().addId(s(val_peek(1)))),s(val_peek(0))));}
break;
case 29:
//#line 116 "parser.y"
{yyval=set(new ExpNumero(s(val_peek(0))));}
break;
case 30:
//#line 117 "parser.y"
{yyval=set(new ExpCadena(s(val_peek(0))));}
break;
case 31:
//#line 118 "parser.y"
{yyval=set(new ExpVariable(s(val_peek(0))));}
break;
case 32:
//#line 119 "parser.y"
{yyval=set(new ExpAsignacion(s(val_peek(0))));}
break;
case 33:
//#line 120 "parser.y"
{yyval=set(new ExpBinario(s(val_peek(0))));}
break;
case 34:
//#line 121 "parser.y"
{yyval=set(new ExpAritmetica(s(val_peek(0))));}
break;
case 35:
//#line 122 "parser.y"
{yyval=set(new ExpLogico(s(val_peek(0))));}
break;
case 36:
//#line 123 "parser.y"
{yyval=set(new ExpComparacion(s(val_peek(0))));}
break;
case 37:
//#line 124 "parser.y"
{yyval=set(new ExpColeccion(s(val_peek(0))));}
break;
case 38:
//#line 125 "parser.y"
{yyval=set(new ExpAcceso(s(val_peek(0))));}
break;
case 39:
//#line 126 "parser.y"
{yyval=set(new ExpFuncion(s(val_peek(0))));}
break;
case 40:
//#line 127 "parser.y"
{yyval=set(new ExpFuncion5(s(val_peek(1)), s(val_peek(0))));}
break;
case 41:
//#line 128 "parser.y"
{yyval=set(new ExpLectura(s(val_peek(0))));}
break;
case 42:
//#line 129 "parser.y"
{yyval=set(new ExpStd(s(val_peek(0))));}
break;
case 43:
//#line 130 "parser.y"
{yyval=set(new ExpRegulares(s(val_peek(0))));}
break;
case 44:
//#line 131 "parser.y"
{yyval=set(new ExpRango(s(val_peek(0))));}
break;
case 45:
//#line 133 "parser.y"
{yyval=set(new Rango(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 46:
//#line 135 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 47:
//#line 136 "parser.y"
{yyval=set(s(ParseValLista.add(val_peek(1), s(val_peek(0)))));}
break;
case 48:
//#line 138 "parser.y"
{yyval=ParseValLista.add(val_peek(2), s(val_peek(1)), s(val_peek(0)), args);args[0]=null;}
break;
case 49:
//#line 139 "parser.y"
{yyval=new ParseValLista(new Lista(), s(val_peek(0)), args, simbolos);args[0]=null;}
break;
case 50:
//#line 141 "parser.y"
{yyval=set(new ModNada());}
break;
case 51:
//#line 142 "parser.y"
{yyval=set(new ModIf(s(val_peek(1)), s(val_peek(0))));}
break;
case 52:
//#line 143 "parser.y"
{yyval=set(new ModUnless(s(val_peek(1)), s(val_peek(0))));}
break;
case 53:
//#line 144 "parser.y"
{yyval=set(new ModWhile(s(val_peek(1)), s(val_peek(0))));}
break;
case 54:
//#line 145 "parser.y"
{yyval=set(new ModUntil(s(val_peek(1)), s(val_peek(0))));}
break;
case 55:
//#line 146 "parser.y"
{yyval=set(new ModFor(s(val_peek(1)), s(val_peek(0))));}
break;
case 56:
//#line 148 "parser.y"
{yyval=set(new Next(s(val_peek(1)), s(val_peek(0))));}
break;
case 57:
//#line 149 "parser.y"
{yyval=set(new Last(s(val_peek(1)), s(val_peek(0))));}
break;
case 58:
//#line 150 "parser.y"
{yyval=set(new Return(s(val_peek(1)), s(val_peek(0))));}
break;
case 59:
//#line 151 "parser.y"
{yyval=set(new Return(s(val_peek(2)), s(val_peek(1)), s(val_peek(0))));}
break;
case 60:
//#line 153 "parser.y"
{yyval=set(new Igual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 61:
//#line 154 "parser.y"
{yyval=set(new MasIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 62:
//#line 155 "parser.y"
{yyval=set(new MenosIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 63:
//#line 156 "parser.y"
{yyval=set(new MultiIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 64:
//#line 157 "parser.y"
{yyval=set(new DivIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 65:
//#line 158 "parser.y"
{yyval=set(new ModIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 66:
//#line 159 "parser.y"
{yyval=set(new PowIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 67:
//#line 160 "parser.y"
{yyval=set(new AndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 68:
//#line 161 "parser.y"
{yyval=set(new OrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 69:
//#line 162 "parser.y"
{yyval=set(new XorIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 70:
//#line 163 "parser.y"
{yyval=set(new DespDIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 71:
//#line 164 "parser.y"
{yyval=set(new DespIIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 72:
//#line 165 "parser.y"
{yyval=set(new LAndIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 73:
//#line 166 "parser.y"
{yyval=set(new LOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 74:
//#line 167 "parser.y"
{yyval=set(new DLOrIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 75:
//#line 168 "parser.y"
{yyval=set(new XIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 76:
//#line 169 "parser.y"
{yyval=set(new ConcatIgual(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 77:
//#line 171 "parser.y"
{yyval=set(new Entero(s(val_peek(0))));}
break;
case 78:
//#line 172 "parser.y"
{yyval=set(new Decimal(s(val_peek(0))));}
break;
case 79:
//#line 174 "parser.y"
{yyval=set(new CadenaSimple(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 80:
//#line 175 "parser.y"
{yyval=set(new CadenaDoble(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 81:
//#line 176 "parser.y"
{yyval=set(new CadenaComando(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 82:
//#line 177 "parser.y"
{yyval=set(new CadenaQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 83:
//#line 178 "parser.y"
{yyval=set(new CadenaQW(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 84:
//#line 179 "parser.y"
{yyval=set(new CadenaQQ(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 85:
//#line 180 "parser.y"
{yyval=set(new CadenaQR(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 86:
//#line 181 "parser.y"
{yyval=set(new CadenaQX(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 87:
//#line 183 "parser.y"
{yyval=set(s(val_peek(0)));}
break;
case 88:
//#line 185 "parser.y"
{yyval=set(new CadenaTexto(),false);}
break;
case 89:
//#line 186 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(3)),s(val_peek(1))),false);}
break;
case 90:
//#line 187 "parser.y"
{yyval=set(CadenaTexto.add(s(val_peek(1)),s(val_peek(0))),false);}
break;
case 91:
//#line 189 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 92:
//#line 190 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 93:
//#line 191 "parser.y"
{yyval=set(new VarExistente(s(val_peek(1)),s(val_peek(0))));}
break;
case 94:
//#line 192 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 95:
//#line 193 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 96:
//#line 194 "parser.y"
{yyval=set(new VarPaquete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 97:
//#line 195 "parser.y"
{yyval=set(new VarSigil(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 98:
//#line 196 "parser.y"
{yyval=set(new VarPaqueteSigil(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 99:
//#line 197 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 100:
//#line 198 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 101:
//#line 199 "parser.y"
{yyval=set(new VarMy(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 102:
//#line 200 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 103:
//#line 201 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 104:
//#line 202 "parser.y"
{yyval=set(new VarOur(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 105:
//#line 204 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 106:
//#line 205 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 107:
//#line 207 "parser.y"
{yyval=set(Paquetes.add(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 108:
//#line 208 "parser.y"
{yyval=set(new Paquetes(s(val_peek(1)),s(val_peek(0))));}
break;
case 109:
//#line 210 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 110:
//#line 211 "parser.y"
{yyval=set(new ColParentesis(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 111:
//#line 213 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 112:
//#line 214 "parser.y"
{yyval=set(new ColCorchete(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 113:
//#line 215 "parser.y"
{yyval=set(new ColLlave(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 114:
//#line 216 "parser.y"
{yyval=set(new ColLlave(s(val_peek(1)),add(new Lista()),s(val_peek(0))));}
break;
case 115:
//#line 218 "parser.y"
{yyval=set(new ColDecMy(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 116:
//#line 219 "parser.y"
{yyval=set(new ColDecOur(s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 117:
//#line 221 "parser.y"
{yyval=val_peek(0);}
break;
case 118:
//#line 222 "parser.y"
{yyval=val_peek(0);}
break;
case 119:
//#line 223 "parser.y"
{yyval=val_peek(0);}
break;
case 120:
//#line 225 "parser.y"
{yyval=set(new AccesoCol(s(val_peek(1)),s(val_peek(0))));}
break;
case 121:
//#line 226 "parser.y"
{yyval=set(new AccesoColRef(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 122:
//#line 227 "parser.y"
{yyval=set(new AccesoDesRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 123:
//#line 228 "parser.y"
{yyval=set(new AccesoDesRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 124:
//#line 229 "parser.y"
{yyval=set(new AccesoDesRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 125:
//#line 230 "parser.y"
{yyval=set(new AccesoRef(s(val_peek(1)),s(val_peek(0))));}
break;
case 126:
//#line 232 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),add(new ColParentesis(ParseValLista.args(add(new Lista(s(val_peek(0)))),args)))));}
break;
case 127:
//#line 233 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(1)),s(val_peek(0))));}
break;
case 128:
//#line 234 "parser.y"
{yyval=set(new FuncionBasica(add(new Paquetes()),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 129:
//#line 235 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(ParseValLista.args(add(new Lista(s(val_peek(0)))),args)))));}
break;
case 130:
//#line 236 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 131:
//#line 237 "parser.y"
{yyval=set(new FuncionBasica(s(val_peek(1)),s(val_peek(0)),add(new ColParentesis(add(new Lista())))));}
break;
case 132:
//#line 238 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(ParseValLista.args(add(new Lista(s(val_peek(0)))),args)))));}
break;
case 133:
//#line 239 "parser.y"
{yyval=set(new FuncionHandle(add(new Paquetes()),s(val_peek(4)),s(val_peek(2)),add(new ColParentesis(s(val_peek(3)),add(new Lista(s(val_peek(1)))),s(val_peek(0))))));}
break;
case 134:
//#line 240 "parser.y"
{yyval=set(new FuncionBloque(add(new Paquetes()),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),add(new ColParentesis(ParseValLista.args(add(new Lista(s(val_peek(0)))),args)))));}
break;
case 135:
//#line 242 "parser.y"
{yyval=set(new HandleOut(s(val_peek(0))));}
break;
case 136:
//#line 243 "parser.y"
{yyval=set(new HandleErr(s(val_peek(0))));}
break;
case 137:
//#line 244 "parser.y"
{yyval=set(new HandleFile(add(new VarExistente(s(val_peek(1)),s(val_peek(0))))));}
break;
case 138:
//#line 246 "parser.y"
{yyval=set(new StdIn(s(val_peek(0))));}
break;
case 139:
//#line 247 "parser.y"
{yyval=set(new StdOut(s(val_peek(0))));}
break;
case 140:
//#line 248 "parser.y"
{yyval=set(new StdErr(s(val_peek(0))));}
break;
case 141:
//#line 250 "parser.y"
{yyval=set(new LecturaFile(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 142:
//#line 251 "parser.y"
{yyval=set(new LecturaIn(s(val_peek(1)),s(val_peek(0))));}
break;
case 143:
//#line 253 "parser.y"
{yyval=set(new BinOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 144:
//#line 254 "parser.y"
{yyval=set(new BinAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 145:
//#line 255 "parser.y"
{yyval=set(new BinNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 146:
//#line 256 "parser.y"
{yyval=set(new BinXor(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 147:
//#line 257 "parser.y"
{yyval=set(new BinDespI(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 148:
//#line 258 "parser.y"
{yyval=set(new BinDespD(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 149:
//#line 260 "parser.y"
{yyval=set(new LogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 150:
//#line 261 "parser.y"
{yyval=set(new DLogOr(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 151:
//#line 262 "parser.y"
{yyval=set(new LogAnd(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 152:
//#line 263 "parser.y"
{yyval=set(new LogNot(s(val_peek(1)),s(val_peek(0))));}
break;
case 153:
//#line 264 "parser.y"
{yyval=set(new LogOrBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 154:
//#line 265 "parser.y"
{yyval=set(new LogAndBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 155:
//#line 266 "parser.y"
{yyval=set(new LogNotBajo(s(val_peek(1)),s(val_peek(0))));}
break;
case 156:
//#line 267 "parser.y"
{yyval=set(new LogXorBajo(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 157:
//#line 268 "parser.y"
{yyval=set(new LogTernario(s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 158:
//#line 270 "parser.y"
{yyval=set(new CompNumEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 159:
//#line 271 "parser.y"
{yyval=set(new CompNumNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 160:
//#line 272 "parser.y"
{yyval=set(new CompNumLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 161:
//#line 273 "parser.y"
{yyval=set(new CompNumLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 162:
//#line 274 "parser.y"
{yyval=set(new CompNumGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 163:
//#line 275 "parser.y"
{yyval=set(new CompNumGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 164:
//#line 276 "parser.y"
{yyval=set(new CompNumCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 165:
//#line 277 "parser.y"
{yyval=set(new CompStrEq(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 166:
//#line 278 "parser.y"
{yyval=set(new CompStrNe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 167:
//#line 279 "parser.y"
{yyval=set(new CompStrLt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 168:
//#line 280 "parser.y"
{yyval=set(new CompStrLe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 169:
//#line 281 "parser.y"
{yyval=set(new CompStrGt(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 170:
//#line 282 "parser.y"
{yyval=set(new CompStrGe(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 171:
//#line 283 "parser.y"
{yyval=set(new CompStrCmp(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 172:
//#line 284 "parser.y"
{yyval=set(new CompSmart(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 173:
//#line 286 "parser.y"
{yyval=set(new AritSuma(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 174:
//#line 287 "parser.y"
{yyval=set(new AritResta(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 175:
//#line 288 "parser.y"
{yyval=set(new AritMulti(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 176:
//#line 289 "parser.y"
{yyval=set(new AritDiv(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 177:
//#line 290 "parser.y"
{yyval=set(new AritPow(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 178:
//#line 291 "parser.y"
{yyval=set(new AritX(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 179:
//#line 292 "parser.y"
{yyval=set(new AritConcat(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 180:
//#line 293 "parser.y"
{yyval=set(new AritMod(s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 181:
//#line 294 "parser.y"
{yyval=set(new AritPositivo(s(val_peek(1)),s(val_peek(0))));}
break;
case 182:
//#line 295 "parser.y"
{yyval=set(new AritNegativo(s(val_peek(1)),s(val_peek(0))));}
break;
case 183:
//#line 296 "parser.y"
{yyval=set(new AritPreIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 184:
//#line 297 "parser.y"
{yyval=set(new AritPreDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 185:
//#line 298 "parser.y"
{yyval=set(new AritPostIncremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 186:
//#line 299 "parser.y"
{yyval=set(new AritPostDecremento(s(val_peek(1)),s(val_peek(0))));}
break;
case 187:
//#line 301 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 188:
//#line 302 "parser.y"
{yyval=set(new RegularMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 189:
//#line 303 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 190:
//#line 304 "parser.y"
{yyval=set(new RegularNoMatch(s(val_peek(5)),s(val_peek(4)),null ,s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 191:
//#line 305 "parser.y"
{yyval=set(new RegularSubs(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 192:
//#line 306 "parser.y"
{yyval=set(new RegularTrans(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 193:
//#line 308 "parser.y"
{yyval=set(null,false);}
break;
case 194:
//#line 309 "parser.y"
{yyval=val_peek(0);}
break;
case 195:
//#line 311 "parser.y"
{yyval=set(new AbrirBloque());}
break;
case 196:
//#line 313 "parser.y"
{yyval=set(new Lista());}
break;
case 197:
//#line 314 "parser.y"
{yyval=val_peek(0);}
break;
case 198:
//#line 316 "parser.y"
{yyval=set(new BloqueSimple(addBefore(new AbrirBloque(),s(val_peek(2))),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 199:
//#line 317 "parser.y"
{yyval=set(new BloqueWhile(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 200:
//#line 318 "parser.y"
{yyval=set(new BloqueUntil(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 201:
//#line 319 "parser.y"
{yyval=set(new BloqueDoWhile(s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 202:
//#line 320 "parser.y"
{yyval=set(new BloqueDoUntil(s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 203:
//#line 321 "parser.y"
{yyval=set(new BloqueFor(s(val_peek(12)),s(val_peek(11)),s(val_peek(10)),s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 204:
//#line 322 "parser.y"
{yyval=set(new BloqueForeachVar(s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 205:
//#line 323 "parser.y"
{yyval=set(new BloqueForeach(s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 206:
//#line 324 "parser.y"
{yyval=set(new BloqueIf(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 207:
//#line 325 "parser.y"
{yyval=set(new BloqueUnless(s(val_peek(9)),s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 208:
//#line 327 "parser.y"
{yyval=set(new SubBloqueVacio());}
break;
case 209:
//#line 328 "parser.y"
{yyval=set(new SubBloqueElsif(s(val_peek(8)),s(val_peek(7)),s(val_peek(6)),s(val_peek(5)),s(val_peek(4)),s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
case 210:
//#line 329 "parser.y"
{yyval=set(new SubBloqueElse(s(val_peek(4)), s(val_peek(3)),s(val_peek(2)),s(val_peek(1)),s(val_peek(0))));}
break;
//#line 4416 "Parser.java"
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
