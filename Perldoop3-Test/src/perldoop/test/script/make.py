
from os import walk
from subprocess import call
import shutil

carpetas=["src/perldoop/test/perl/"]
try:
	shutil.rmtree("src/perldoop/test/java/")
except:
	pass

while len(carpetas) > 0:
	for (path, folders, files) in walk(carpetas.pop(0)):
		carpetas += folders
		files.sort()
		infile=""
		
		for file in files:
			if file.endswith(".pl"):
				infile+=file+" "	

		if len(infile) > 0:
			call(['java','-jar','../Perldoop3/dist/Perldoop3.jar',path+"/"+infile,'-out','src'])
