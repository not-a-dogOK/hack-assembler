import java.io.*;
import java.util.Scanner;

public class parser {
    public String fileName;
    public File file;

    public parser() throws IOException {
        // the file receiving system
        // with some information for the user
        System.out.println("name? with '\' at start");
        Scanner reader = new Scanner(System.in);
        fileName = System.getProperty("user.dir") + reader.nextLine() + ".asm";
        this.file = new File(fileName);
        if (file.exists()) {
            System.out.println("opening file....");
            System.out.println("File name: " + file.getName());
            System.out.println("path: " + file.getAbsolutePath());
            System.out.println("Writeable: " + file.canWrite());
            System.out.println("Readable " + file.canRead());
            System.out.println("File size in bytes " + file.length());
        }

    }

    // checks if there are 2 '/' near each other
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
            if (line.charAt(j) == '=' || line.charAt(j) == ';') {
                return "C";
            }
        }
        return null;

    }

    // removes the '@', '(' and ')' making the line clean for the code translator
    public String symbol(String line) throws IOException {

        for (int i = 0; i < line.length(); i++) {

            if ("L".equals(instactionType(line))) {
                if (line.charAt(i) == '(') { // TO DO: L?
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

    // return a String with dest Keyword via finding the first leters in the line
    // before '='
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

        while (j - 1 >= 0 && (line.charAt(j - 1) == 'D' || line.charAt(j - 1) == 'A' || line.charAt(j - 1) == 'M')) {
            newLine = newLine + line.charAt(j - 1);
            j--;
        }
        return newLine;
    }

    // return a String with comp Keywords via Acceptg only lines with symbols of asm
    public String comp(String line) throws IOException {
        line = symbol(line);
        String newLine = "";
        int i = 0;
        while (line.charAt(i) != '=' && line.charAt(i) != ';') {
            i++;
        }

        if (line.charAt(i) == '=') {
            i++;
            while (line.charAt(i) == '0' || line.charAt(i) == '+' || line.charAt(i) == '1' || line.charAt(i) == '-' ||
                    line.charAt(i) == 'D' || line.charAt(i) == 'A' || line.charAt(i) == 'M' || line.charAt(i) == '!' ||
                    line.charAt(i) == '&' || line.charAt(i) == '|') {
                newLine = newLine + line.charAt(i);
                i++;
                if (line.length() - 1 < i) { // at end of line
                    return newLine;
                }
            }
        }
        if (line.charAt(i) == ';') {
            i--;
            while (line.charAt(i) == '0' || line.charAt(i) == '+' || line.charAt(i) == '1' || line.charAt(i) == '-' ||
                    line.charAt(i) == 'D' || line.charAt(i) == 'A' || line.charAt(i) == 'M' || line.charAt(i) == '!' ||
                    line.charAt(i) == '&' || line.charAt(i) == '|') {
                newLine = newLine + line.charAt(i);
                if (i == 0) { // at end of comp
                    return newLine;
                }
                i--;
            }
        }
        return newLine; // default
    }

    // return a String with jump Keyword via going to ';' in the line
    public String jump(String line) throws IOException {
        line = symbol(line);

        String newLine = "";
        int i = 0;
        while (line.charAt(i) != ';') {
            i++;
            if (line.length() - 1 < i) { // at end of line
                return newLine;
            }
        }

        while (line.length() - 1 >= i) { // at end of line
            newLine = newLine + line.charAt(i + 1);
            i++;
            if (line.length() - 1 < i + 1) { // at end of line
                return newLine;
            }
        }
        System.out.println("jump: " + newLine);
        return newLine;
    }

    // return a 16 bin number representing the number received
    public String tobin(String line) {
        String l = Integer.toBinaryString(Integer.parseInt(line));
        if (l.equals("0") || l.equals("00")) {
            for (int i = 0; i < 15; i++) {
                l = "0" + l;
            }
            return l;
        }
        int num = l.length();
        for (int i = 0; i < 16 - num; i++) {
            l = "0" + l;
        }
        return l;
    }

    // the work force of the assmbler called for every line
    public void writeline(String line) throws IOException {
        SymbolTable Table = new SymbolTable();
        for (int i = 0; i < 16; i++) {
            Table.addEntry(i, "R" + i);
        }
        int x = 15;
        Table.addEntry(16394, "SCREEN");
        Table.addEntry(24576, "KBD");
        System.out.println("line:" + line);
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
                lineBin = tobin(symbol(line));
            }

            if (instactionType(line).equals("C")) { // build the line
                lineBin = "111";
                lineBin = lineBin + Code.comp(comp(line));
                lineBin = lineBin + Code.dest(dest(line));
                lineBin = lineBin + Code.jump(jump(line));
                // System.out.println(lineBin);

            }

            // TO DO: add L in stra

            writer.write(lineBin);
            writer.newLine();
            writer.close();

            System.out.println("Successfully wrote to the file.");
        }
    }
}
