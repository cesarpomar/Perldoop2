
package perldoop::test::java::asignacion::Igual;

#Asignacion simple
our $l1 = 1;#<integer>
our $l2 = 2;#<box>
our @l3 = (1,);#<array><string>
our @l4 = (1,);#<list><integer>
our %l5 = (1,"a");#<hash><string>

#Inicializacion
our @l6 = ();#<array><10><array><string>
our @l7 = ();#<list><10><list><string>
our %l8 = ();#<hash><10><hash><string>

#Inicializacion Referencias
our $l9;#<ref><array><string>
our $l10;#<ref><list><string>
our $l11;#<ref><hash><string>

$l6[0] = [];#<10>
$l7[0] = [];#<10>
$l8{0} = {};#<10>
$l9 = [];#<10>
$l10 = [];#<10>
$l11 = {};#<10>

#Multiasignacion coleccion
our @l12=(1,);#<list><integer>
(our $l13,$l12[0],our $l14)=(1,2,3);#<integer>

#Multiasignacion variable
my @a=(1,2,3);#<array><integer>
our @l15=(1,);#<list><integer>
(our $l16,$l15[0],our $l17)=@a;#<integer>


###FALTAN LAS PRUEBAS CON FUNCIONES