import java.io.*;

public class parser {
    public String fileName;
    public File file;

    public parser() throws IOException {
        // the file receiving system
        // with some information for the user
        fileName = "Nolable.asm";
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
            if (line.charAt(j) == '@') {
                if (Character.isDigit(line.charAt(j + 1))) {
                    return "A";
                }

                return "@L";

            }
            if (line.charAt(j) == '=' || line.charAt(j) == ';') {
                return "C";
            }
            if (line.charAt(j) == '(') {
                return "(L)";
            }
        }
        return null;

    }

    // removes the '@', '(' and ')' making the line clean for the code translator
    public String symbol(String line) throws IOException {
        int start = 0;
        int end;
        for (int i = 0; i < line.length(); i++) {

            if ("(L)".equals(instactionType(line))) {
                if (line.charAt(i) == '(') {
                    start = i + 1;
                    // line = line.substring(i + 1);
                }
                if (line.charAt(i) == ')') {
                    end = i;
                    line = line.substring(start, end);
                }
            }
            if ("A".equals(instactionType(line)) ||
                    ("@L".equals(instactionType(line)))) {
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

    /**
     * TO-DO:
     * write line dosent work anymore.
     * when the (L) is before @L the translation to lines is off by 1 (-1)
     **/

    // the work force of the assmbler called for every line
    public void writeline(String line, int index) throws IOException {
        System.out.println("line:" + line);
        if (!isComment(line)) {

            String lineBin = "";

            BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.hack", true));
            if (instactionType(line).equals("A")) {
                lineBin = tobin(symbol(line));
            }

            if (instactionType(line).equals("C")) { // build the line
                lineBin = "111";
                lineBin = lineBin + Code.comp(comp(line));
                lineBin = lineBin + Code.dest(dest(line));
                lineBin = lineBin + Code.jump(jump(line));
                //

            }

            writer1.write(lineBin);
            writer1.newLine();
            writer1.close();

            System.out.println("Successfully wrote to the file.");
        }
    }

    /**
     * checks for labels and parameters and adds them to a parameter Table
     * (symbolTable type)
     * them pops the line of the labels and parameters for the second pass to work
     */

    public void firstPass(String line, int l, SymbolTable Table, String FileName) throws IOException {

        // System.out.println(instactionType("// goto output_d"));
        // System.out.println("-");

        BufferedWriter writer = new BufferedWriter(new FileWriter("Nolable.asm", true));
        String temp = line;
        int tempN = -1;
        int x = 15;
        String symbol = "";
        if (!isComment(line)) {
            if (instactionType(line).equals("(L)")) {
                temp = "";

            }
            if (instactionType(line).equals("@L")) {
                symbol = symbol(line);

                if (checkLabel(line, Table)) {
                    tempN = FindLabelLine(symbol, l, Table, FileName);
                    tempN++; // move one forward cus assembly
                } else {
                    // System.out.println(symbol);
                    tempN = Table.byName(symbol);
                }
                temp = "@" + Integer.toString(tempN);
            }

            if (temp != null) {
                writer.write(temp); // has to write something
                System.out.println("Successfully wrote to the file.");
                // if not label skip line
                if (!instactionType(line).equals("(L)")) {
                    writer.newLine();

                }

                writer.close();
            }
        }
    }

    // input: name of label needed to find
    // output: line of label
    private int FindLabelLine(String symbol, int l, SymbolTable table, String FileName) throws IOException {
        BufferedReader tempRead;
        int LT = 0;
        tempRead = new BufferedReader(new FileReader(FileName));

        String line = tempRead.readLine();
        System.out.println(symbol);
        while (line != null) {
            System.out.println("line: " + symbol(line));
            if (symbol(line).equals(symbol) && LT != l) {
                return LT;
            }
            line = tempRead.readLine();
            LT++;
        }
        System.out.println("monkey, you code has unused label");
        return -1;
    }

    // if its is in the table
    public boolean checkLabel(String line, SymbolTable Table) throws IOException {
        String symbol = symbol(line);
        int num = Table.byName(symbol);
        if (num == -1) {
            return true;
        }
        return false;
    }
}
