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
        //
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
        int i = 0;
        int j = 0;
        while (line.charAt(i) == ' ') {
            i++;
        }
        while (line.charAt(j) == '=') {
            j++;
        }
        while (i < j) {
            newLine = newLine + line.charAt(i);
            i++;
        }
        return newLine;
    }

    // return a String with comp Keywords
    public String comp(String line) throws IOException {
        line = symbol(line);
        String newLine = "";
        int i = 0;
        while (line.charAt(i) == '=') {
            i++;
        }
        int j = i;
        i = 0;
        while (line.charAt(i) == ';') {
            i++;
        }
        while (i < j) {
            newLine = newLine + line.charAt(i);
            i++;
        }
        newLine = newLine + line.charAt(j);
        return newLine;
    }

    // return a String with jump Keyword
    public String jump(String line) throws IOException {
        line = symbol(line);

        String newLine = "";
        int i = 0;
        int j = line.length();
        while (line.charAt(i) == ';') {
            i++;
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
        for (int i = 0; i < 16 - l.length(); i++) {
            l = "0" + l;
        }
        return l;
    }

    public void writeline(String line) throws IOException {
        SymbolTable Table = new SymbolTable();

        for (int i = 0; i < 16; i++) {
            // System.out.println(i);
            Table.addEntry(i, "R" + i);
            // System.out.println("R" + i);
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
                System.out.println(tobin(symbol(line)));
                lineBin = tobin(symbol(line));
            }

            if (instactionType(line).equals("C")) {
                lineBin = lineBin + Code.dest(dest(line));
                lineBin = lineBin + Code.comp(comp(line));
                lineBin = lineBin + Code.jump(jump(line));
            }

            writer.write(lineBin);
            writer.newLine();
            writer.close();

            System.out.println("Successfully wrote to the file.");
        }
    }
}
