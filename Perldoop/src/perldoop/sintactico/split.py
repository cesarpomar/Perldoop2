import re
import math

fichero = open('Parser.java', 'r')
parser=fichero.read().replace('\r','')
fichero.close()

ELEMENTOS = 900 * 10
#Cambiamos arrays por matriz
parser=parser.replace('yycheck[yyn]','yycheck[yyn/'+str(ELEMENTOS)+']'+'[yyn%'+str(ELEMENTOS)+']')
parser=parser.replace('yytable[yyn]','yytable[yyn/'+str(ELEMENTOS)+']'+'[yyn%'+str(ELEMENTOS)+']')
#Obtenemos el numero de elementos
total=int(re.search('final static int YYTABLESIZE=([0-9]+);', parser).group(1))
divs = total/ELEMENTOS
#Cambiamos la declaracion de array a matrix
parser=parser.replace('static short yytable[];','static short yytable[][] = new short['+str(math.ceil(divs))+'][];')
parser=parser.replace('static short yycheck[];','static short yycheck[][] = new short['+str(math.ceil(divs))+'][];')
#Obtenemos los elementos de ambas tablas
yytable = re.search('yytable = new short\[\]\{([^\}]*)\}', parser, re.MULTILINE).group(1).replace(' ','').replace('\n','').split(',')
yycheck = re.search('yycheck = new short\[\] \{([^\}]*)\}', parser, re.MULTILINE).group(1).replace(' ','').replace('\n','').split(',')
#Si la ultima coma no tenia elemento lo quitamos
if yytable[-1] == '':
	yytable=yytable[:-1]
	yycheck=yycheck[:-1]
#Borramos la tablas viejas
parser=re.sub('static void yytable[^;]*;\n\}',' ',parser,re.MULTILINE)
parser=re.sub('static void yycheck[^;]*;\n\}',' ',parser,re.MULTILINE)
#Recreamos las tablas
yytableM = []
yycheckM = []
yytableT = "static {"
yycheckT = "static {"
i = 0
#Primero la llamada a la funcion
while i < divs:
	yytableT = yytableT+' yytable'+str(i)+'();'
	yycheckT = yycheckT+' yycheck'+str(i)+'();'
	i = i + 1;

yytableT = yytableT+'}\n'
yycheckT = yycheckT+'}\n'
#Luego creamos las funciones
i = 0
while i < divs:
	p = i * ELEMENTOS	#Posicion en el array
	n = 0				#Posicion en la matrix
	l = 0				#Posicion en la linea
	yytableT = yytableT+'static void yytable'+str(i)+'(){\nyytable['+str(i)+'] = new short[] {\n'
	yycheckT = yycheckT+'static void yycheck'+str(i)+'(){\nyycheck['+str(i)+'] = new short[] {\n'
	while (n < ELEMENTOS and p < len(yytable)):
		yytableT = yytableT+'%6s' % (yytable[p],)+','
		yycheckT = yycheckT+'%6s' % (yycheck[p],)+','
		p = p + 1;
		n = n + 1;
		l = l + 1;
		if l == 9:
			l = 0
			yytableT = yytableT+'\n'
			yycheckT = yycheckT+'\n'
	yytableT = yytableT[:-1]
	yycheckT = yycheckT[:-1]
	yytableT = yytableT+'};\n}\n'
	yycheckT = yycheckT+'};\n}\n'
	i = i + 1

#Cambiamos el codigo
parser=parser.replace('static { yytable();}',yytableT)
parser=parser.replace('static { yycheck(); }',yycheckT)



fichero = open('Parser.java', 'w')
fichero.write(parser)
fichero.close()