
package Igual;

#Asignacion simple
our $l1 = 1;#<integer>
our $l2 = 2;#<box>
our @l3 = (1,);#<array><string>
our @l4 = (1,);#<list><integer>
our %l5 = (1,"a");#<hash><string>

#Inicializacion
our @l6 = ();#<array><8><array><string>
our @l7 = ();#<list><8><list><string>
our %l8 = ();#<hash><8><hash><string>

#Inicializacion Referencias
our $l9;#<ref><array><string>
our $l10;#<ref><list><string>
our $l11;#<ref><hash><string>
our @l7aux=([],);#<list><list><string>Las lista solo se puede añadir

$l6[0] = [];#<10>
$l7aux[0] = [];#<10>
$l8{0} = {};#<10>
$l9 = [];#<10>
$l10 = [];#<10>
$l11 = {};#<10>

#Multiasignacion coleccion
our @l12=(1,);#<list><integer>
(our $l13,$l12[0],our $l14)=(1,2,3);#<integer>
(our $l15)=([1,2]);#<ref><array><integer>
my @aa=([1,2],[3,4]);#<array><array><integer>
(our $l16)=($aa[0]);#<ref><array><integer>
our $l17=1,our $l18=2;#<integer>
($l17,$l18)=($l18,$l17);

#Multiasignacion variable
my @a=(1,2,3);#<array><integer>
our @l19=(1,);#<list><integer>
(our $l20,$l19[0],our $l21)=@a;#<integer>



###FALTAN LAS PRUEBAS CON FUNCIONES