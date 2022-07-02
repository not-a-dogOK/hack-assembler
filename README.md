# hack-assembler
an assembler for the hack machine language
knows to handle A type instructions (op code 0), C type instructions (op code 1) and L type instructions (labels for easier usage)
the midterm project of "The Elements of Computing Systems" course aka "from nand to tetris" more at https://www.nand2tetris.org
# API 
The program asks the user for the name of the file starting with “\”, in order for the program to find the file it must be in the same folder as the java files
<br />
1) First pass on the on the file, replacing labels with the number they represent, removing comments and writing it to the file: "Nolable.asm" 
2) Second pass on the file, reads "Nolable.asm" and writes hack 16 bit machine language code into “output.hack”
<br /> output: “output.hack” 16 bit binary code for the hack computer, ready to run
# Running the machine language code
In the release tab there will be a hack emulator and hack assembler, taken from https://www.nand2tetris.org under "Creative Common" license
<br /> This also should run on a physical hack computer but wasn’t tested on one 

