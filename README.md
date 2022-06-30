# hack-assembler
an assembler for the hack machine language
knows to handle A type instructions (op code 0), C type instructions (op code 1) and L type instructions (labels for easier usage)
the midterm project of "The Elements of Computing Systems" course aka "from nand to tetris" more at https://www.nand2tetris.org
  

![image](https://user-images.githubusercontent.com/57526797/163774382-5a759c36-fd16-4d23-8d2e-7fa1caf56f4d.png)
![image](https://user-images.githubusercontent.com/57526797/163774626-958ad445-de89-405f-b08e-f82107f19610.png)


# API 
input: program get file via user input
1) First pass on the on the file, removing labels and generating "Nolable.asm" 
2) Second pass on the line, reads "Nolable.asm" and write binary code into “output.hack” 
 <br /> output: “output.hack” 16 bit binary code for the hack computer, ready to run
