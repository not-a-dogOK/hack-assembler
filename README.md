# Hack-assembler
an assembler for the hack machine language
knows to handle A type instructions (op code 0), C type instructions (op code 1) and L type instructions (labels for easier usage)
this assembler is the midterm project of "The Elements of Computing Systems" course also known as "from Nand to Tetris" learn more at https://www.nand2tetris.org.
# API 
The program asks the user for the name of the file starting with “\”, in order for the program to find the file it must be in the same folder as the java files
<br />
1) First pass, reads the file given by the user replacing labels with the number they represent, removing comments and writing it to the file: "Nolable.asm" 
2) Second pass, reads "Nolable.asm" and writes Hack 16 bit machine language code into “output.hack”
<br /> “output.hack” 16 bit binary code for the hack computer, ready to be load into a ROM or run on a hack emulator.
# Running the machine language code
In the release tab there will be a hack emulator and hack assembler, taken from https://www.nand2tetris.org under "Creative Common" license
<br /> This also should run on a physical hack computer but wasn’t tested on one.
