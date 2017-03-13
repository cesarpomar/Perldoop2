
package IgualBox;

#Asignacion box
our $l1 = 1;#<box>
our $l2 = 1.1;#<box>
our $l3 = "1";#<box>
our $l4 = [1,2];#<box>
our $l5 = {1,2};#<box>
our $l6 = [[1,2],[3,4]];#<box>
our $l7 = {1,{1,2},2,{2,3}};#<box>
our $l8 = $l1;#<box>

#accesos box
our @l12 = ([1,2],[3,4],[5,6]);#<array><array><box>
our $l13 = $l12[0];#<ref><array><box>
our $l14 = $l12[0];#<box>
my $e1 = [7,8];#<box>
my $e2 = [9,10];#<ref><array><box>
$l12[1] = $e1;
$l12[2] = $e2;