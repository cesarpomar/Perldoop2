
package Acceso;

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

#Lectura acceso referencia
our $l13 = $aa[0]->[0];#<string>
our $l14 = $ll[0]->[0];#<integer>
our $l15 = $hh{1}->{1};#<string>
our $l16 = $l4->[0];#<string>
our $l17 = $l5->[0];#<integer>
our $l18 = $l6->{1};#<string>

#Lectura acceso referencia multiple
our @l19 = @aa[0]->[0,1];#<array><string> 
our @l20 = @ll[0]->[0,1];#<list><integer>
our @l21 = @hh{1}->{1,2};#<list><string>
our @l22 = $l4->[0,1];#<array><string> 
our @l23 = $l5->[0,1];#<list><integer>
our @l24 = $l6->{1,1};#<list><string>

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

#Escritura acceso referencia
our @w13 = ([1,2],[3,4]);#<array><array><string>
our @w14 = ([1,2],[3,4]);#<list><list><integer>
our %w15 = (1,{1,"a"},2,{2,"n"});#<hash><hash><string>
our $w16 = [1,2];#<ref><array><string>
our $w17 = [1,2];#<ref><list><integer>
our $w18 = {1,2};#<ref><hash><string>

$w13[0]->[0] = 0;
$w14[0]->[0] = 0;
$w15{1}->{1} = "z";
$w16->[0] = 0;
$w17->[0] = 0;
$w18->{1} = "z";

#Escritura acceso referencia multiple
our @w19 = ([1,2],[3,4]);#<array><array><string>
our @w20 = ([1,2],[3,4]);#<list><list><integer>
our %w21 = (1,{1,"a",2,"b"},2,{2,"n"});#<hash><hash><string>
our $w22 = [1,2];#<ref><array><string>
our $w23 = [1,2];#<ref><list><integer>
our $w24 = {1,2,3,4};#<ref><hash><string>

@w19[0]->[0,1] = (7,8);
@w20[0]->[0,1] = (7,8);
@w21{1}->{1,2} = ("n","o");
$w22->[0,1] = (7,8);
$w23->[0,1] = (7,8);
$w24->{1,3} = ("n","o");

#Acceso a referencia
our @l28 = @{$aa[0]};#<array><string>
our @l29 = @{$ll[0]};#<list><integer>
our %l30 = %{$hh{1}};#<hash><string>
our @l31 = @{$l4};#<array><string>
our @l32 = @{$l5};#<list><integer>
our %l33 = %{$l6};#<hash><string>
our @l34 = @{\@a};#<array><string>
our @l35 = @{[1,2]};#<array><string>

#Creacion de Referencias
our $l36 = \@a;#<ref><array><string>
our $l37 = \@l;#<ref><list><integer>
our $l38 = \%h;#<ref><hash><string>

#Sigil
our $l39 = $#a;#<integer>
our $l40 = $#l;#<integer>
