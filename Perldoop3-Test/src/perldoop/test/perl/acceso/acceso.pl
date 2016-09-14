
package perldoop::test::java::acceso::Acceso;

#Colecciones
our @a = (1,2,3,4);#<array><string>
our @l = (1,2,3,4);#<list><integer>
our %h = (1,"a",2,"n");#<hash><string>

our @aa = ([1,2],[3,4]);#<array><array><string>
our @ll = ([1,2],[3,4]);#<list><list><integer>
our %hh = (1,{1,"a"},2,{2,"n"});#<hash><hash><string>

#Lectura
our $l1 = $a[0];#<string>
our $l2 = $l[0];#<integer>
our $l3 = $h{1};#<string>
our $l4 = $aa[0];#<ref><array><string>
our $l5 = $ll[0];#<ref><list><integer>
our $l6 = $hh{1};#<ref><hash><string>

#Lectura anidada
our $l7 = $aa[0][0];#<string>
our $l8 = $ll[0][0];#<integer>
our $l9 = $hh{1}{1};#<string>

#Lectura multiple
our @l10 = @a[0,1];#<array><string> 
our @l11 = @l[0,1];#<list><integer>
our @l12 = @h{1,2};#<list><string>

#Escritura

our @w1 = (1,2,3,4);#<array><string>
our @w2 = (1,2,3,4);#<list><integer>
our %w3 = (1,"a",2,"n");#<hash><string>
our @w4 = ([1,2],[3,4]);#<array><array><string>
our @w5 = ([1,2],[3,4]);#<list><list><integer>
our %w6 = (1,{1,"a"},2,{2,"n"});#<hash><hash><string>

$w1[0] = 0;
$w2[0] = 0;
$w3{1} = "z";
$w4[0] = $w4[1];
$w5[0] = $w5[1];
$w6{1} = $w6{2};

#Escritura anidada

our @w7 = ([1,2],[3,4]);#<array><array><string>
our @w8 = ([1,2],[3,4]);#<list><list><integer>
our %w9 = (1,{1,"a"},2,{2,"n"});#<hash><hash><string>

$w7[0][0] = 0;
$w8[0][0] = 0;
$w9{1}{1} = "z";

#Escritura multiple

our @w10 = (1,2,3,4);#<array><string>
our @w11 = (1,2,3,4);#<list><integer>
our %w12 = (1,"a",2,"n");#<hash><string>

@w10[1,2] = (7,8);
@w11[1,2] = (7,8);
@w12{1,2} = ("n","o");
