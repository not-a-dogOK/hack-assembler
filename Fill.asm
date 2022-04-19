// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
// the idea is to ran @SCREEN 255 (fore each row of pixels on the screen)
//but rows are boring so lets do it with memory adress o(*°▽°*)o

(Start)
@SCREEN // get memory adress
D=A 
@i
M=D

@KBD
D=M
@pressed
D;JNE

(notpressed)
@i
A=M
M=0
@i
M=M+1
@i
D=M
@24575 // max memory of screen so minus it is when the loop is done 
D=D-A
@Start
D;JGT
@notpressed
D;JLE


(pressed)
@i
A=M
M=-1
@i
M=M+1
@i
D=M

@24575 // max memory of screen so minus it is when the loop is done
D=D-A
@Start
D;JGT

@pressed
D;JLE