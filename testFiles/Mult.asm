// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.

 @2
    M=0     // Init R2, R2 = 0.
    @i
    M=0     // Init i, i=0.

(LOOP)
    @i
    D=M     // D=i
    @0
    //to pass test unit (I have no idea why this piece is needed)
    D=D-M   // D=i-R0 
    @END
    D;JGE    // if i-R0 >= 0 goto END

    @1
    D=M     // D=R1
    @2
    M=D+M   // R2=R2+R1
    @i
    M=M+1   // incremenet i (i=i+1)
    @LOOP
    0;JMP   // Repeat

(END)
    @END
    0;JMP   // End of program infinite loop.