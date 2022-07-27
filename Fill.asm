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