
package perldoop::test::java::coleciones::Colecciones;

#Inicializacion basica
our @a = (1,2,3,4);#<array><string>
our @l = (1,2,3,4);#<list><integer>
our %h = (1,"a",2,"n");#<hash><string>

#Inicializacion basica anidada
our @aa = ([1,2],[3,4]);#<array><array><string>
our @ll = ([1,2],[3,4]);#<list><list><integer>
our %hh = (1,{1,"a"},2,{2,"n"});#<hash><hash><string>

#Inicializacion con despliegue

my @auxa = (1,2);#<array><integer>
my @auxl = (3,4);#<list><integer>
my %auxh = (5,6);#<hash><integer>

our @a2 = (@auxa,@auxl,%auxh, 7, 8);#<array><integer>
our @l2 = (@auxa,@auxl,%auxh, 7, 8);#<list><integer>
our %h2 = (@auxa,@auxl,%auxh, 7, 8);#<hash><integer>

#Inicializacion con despliegue en acceso

my @auxaa = ([1,2],[0,0]);#<array><array><integer>
my @auxll = ([3,4],[0,0]);#<list><list><integer>
my %auxhl = (0,{5,6},1,{0,0});#<hash><hash><integer>

our @a3 = (@auxa[0],@auxl[0],%auxh{0}, 7, 8);#<array><integer>
our @l3 = (@auxa[0],@auxl[0],%auxh{0}, 7, 8);#<list><integer>
our %h3 = (@auxa[0],@auxl[0],%auxh{0}, 7, 8);#<hash><integer>
