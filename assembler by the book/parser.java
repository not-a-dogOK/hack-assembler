import java.io.*;
import java.util.Scanner;

import javax.sound.sampled.Line;

public class parser {
    public String fileName;
    public File file;

    public parser() throws IOException { // reset?

        System.out.println("name? with '\' at start");
        Scanner reader = new Scanner(System.in);
        fileName = System.getProperty("user.dir") + reader.nextLine() + ".asm";
        this.file = new File(fileName);
        if (file.exists()) {
            System.out.println("file found!");
            System.out.println("File name: " + file.getName());
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("Writeable: " + file.canWrite());
            System.out.println("Readable " + file.canRead());
            System.out.println("File size in bytes " + file.length());
        }

    }

    public boolean isComment(String line) {
        for (int j = 0; j < line.length() - 1; j++) {
            if (line.charAt(j) == '/' && line.charAt(j + 1) == '/') {
                return true;
            }
        }
        return false;
    }

    // returns the istra in one letter A C or L
    public String instactionType(String line) throws IOException {

        for (int j = 0; j < line.length(); j++) {
            if (line.charAt(j) == '(') {
                return "L";
            }
            if (line.charAt(j) == '@') {
                return "A";
            }
            if (line.charAt(j) == '=') {
                return "C";
            }
        }
        return null;

    }

    // removes the '@', '(' and ')' making the line clean for the code translator
    public String symbol(String line) throws IOException {
        
        for (int i = 0; i < line.length(); i++) {

            if ("L".equals(instactionType(line))) {
                if (line.charAt(i) == '(') { // TO DO: cry about L here
                    line = line.substring(i + 1);
                }
            }
            if ("A".equals(instactionType(line))) {
                if (line.charAt(i) == '@') {
                    line = line.substring(i + 1);
                }
            }
        }
        
        return line;
    }

    // return a String with dest Keyword
    public String dest(String line) throws IOException {
        line = symbol(line);
        String newLine = "";
        int j = 0;
        while (line.charAt(j) != '=') {
            j++;
            if (j >= line.length()) {
                return "";
            }
        }
            
        while (j - 1 >= 0 && (line.charAt(j - 1) == 'D' || line.charAt(j - 1) == 'A' || line.charAt(j - 1) == 'M') ) {
            //System.out.println(line.charAt(j - 1));
            newLine = newLine + line.charAt(j - 1);
            j--;    
        }
        return newLine;
    }

    // return a String with comp Keywords
    public String comp(String line) throws IOException {
        line = symbol(line);
        String newLine = "";
        int i = 0;
        while (line.charAt(i) != '=') {
            i++;
            if (i >= line.length()) {
                i = 0;
                while(line.charAt(i) != ';') {
                    i++;
                }
            }
        }
        i++;
        System.out.println(line.charAt(i)); 
        System.out.println(line.length());       
        while ( 
            line.charAt(i) == '0' || line.charAt(i) == '+' || line.charAt(i) == '1' || line.charAt(i) == '-' || 
            line.charAt(i) == 'D' || line.charAt(i) == 'A' || line.charAt(i) == 'M' || line.charAt(i) == '!' ||
            line.charAt(i) == '&' || line.charAt(i) == '|' ) {
            //System.out.println(line.charAt(j - 1));
            newLine = newLine + line.charAt(i);
            i++;
            if (line.length() - 1 <i) { // at end of line
                return newLine;
            }       
        }
        return newLine;
    }

    // return a String with jump Keyword
    public String jump(String line) throws IOException {
        line = symbol(line);

        String newLine = "";
        int i = 0;
        int j = line.length() - 1;
        while (line.charAt(i) != ';') {
            i++;
            if (line.length() - 1 <i) { // at end of line
                return newLine;
            }
        }
        
        while (i < j) {
            newLine = newLine + line.charAt(i);
            i++;
        }
        newLine = newLine + line.charAt(j - 1);
        return newLine;
    }

    public String tobin(String line) {
        String l = Integer.toBinaryString(Integer.parseInt(line));
        //System.out.println(l);
        if (l.equals("0") || l.equals("00") ) {
            for (int i = 0; i < 15; i++) {
                l = "0" + l;
            }
            return l;
        }
        //System.out.println(l.length());
        int num = l.length();
        for (int i = 0; i < 16 - num; i++) {
            l = "0" + l;
        }
        //System.out.println(l);
        return l;
    }

    public void writeline(String line) throws IOException {
        SymbolTable Table = new SymbolTable();

        for (int i = 0; i < 16; i++) {
            Table.addEntry(i, "R" + i);
        }
        int x = 15;
        Table.addEntry(16394, "SCREEN");
        Table.addEntry(24576, "KBD");
        if (!isComment(line)) {
            if ((instactionType(line).equals("A") || instactionType(line).equals("L")) // adds to all symbols to table
                    && !Table.contains(line)) {
                String templine = symbol(line);
                Table.addEntry(x, line);
                x++;
            }

            String lineBin = "";

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.hack", true));
            if (instactionType(line).equals("A")) {
                //System.out.println(tobin(symbol(line)));
                lineBin = tobin(symbol(line));
            }

            if (instactionType(line).equals("C")) {
                lineBin = "111";
                lineBin = lineBin + Code.comp(comp(line));
                lineBin = lineBin + Code.dest(dest(line));
                lineBin = lineBin + Code.jump(jump(line));
                System.out.println(lineBin);
                
            }
            /**TO DO: 
             * L in stra
             * fix the missing bits on translate 
             * 
            **/
            writer.write(lineBin);
            writer.newLine();
            writer.close();

            System.out.println("Successfully wrote to the file.");
        }
    }
}
