
package perldoop::test::java::asignacion::Igual;

#Asignacion simple
our $l1 = 1;#<integer>
our $l2 = 2;#<box>
our @l3 = (1,);#<array><string>
our @l4 = (1,);#<list><integer>
our %l5 = (1,"a");#<hash><string>

#Inicializacion
our @w1 = ();#<array><10><array><string>
our @w2 = ();#<list><10><list><string>
our %w3 = ();#<hash><10><hash><string>
$w1[0] = [];#<10>
$w2[0] = [];#<10>
$w3{0} = {};#<10>
