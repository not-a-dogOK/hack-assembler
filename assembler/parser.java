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

    // returns the instruction in one letter A C or L
    public String instructionType(String line) {
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

            if ("(L)".equals(instructionType(line))) {
                if (line.charAt(i) == '(') {
                    start = i + 1;
                }
                if (line.charAt(i) == ')') {
                    end = i;
                    line = line.substring(start, end);
                }
            }
            if ("A".equals(instructionType(line)) ||
                    ("@L".equals(instructionType(line)))) {
                if (line.charAt(i) == '@') {
                    line = line.substring(i + 1);
                }
            }
        }

        return line;
    }

    // return a String with dest Keyword via finding the first letters in the line
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

    // return a String with comp Keywords via Accepting only lines with symbols of
    // asm
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

    // the work force of the assembler, called for every line
    public void writeline(String line, int index) throws IOException {

        if (!isComment(line)) {

            String lineBin = "";

            BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.hack", true));
            if (instructionType(line).equals("A")) {
                lineBin = tobin(symbol(line));
            }

            if (instructionType(line).equals("C")) { // build the line
                lineBin = "111";
                lineBin = lineBin + Code.comp(comp(line));
                lineBin = lineBin + Code.dest(dest(line));
                lineBin = lineBin + Code.jump(jump(line));
                //

            }

            writer1.write(lineBin);
            writer1.newLine();
            writer1.close();

        }
    }

    /**
     * checks for labels and parameters and adds them to a parameter Table
     * (symbolTable type)
     * them pops the line of the labels and parameters for the second pass to work
     */

    public void firstPass(String line, SymbolTable Table, String FileName) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("Nolable.asm", true)); // no new file ?
        String temp = line;
        int tempN = -1;
        int x = 15;
        String symbol = "";
        System.out.println(line);
        if (!isComment(line)) {
            if (instructionType(line).equals("(L)")) {
                temp = "";

            }
            if (instructionType(line).equals("@L")) {
                symbol = symbol(line);

                if (checkLabel(line, Table)) {
                    tempN = FindLabelLine(symbol, FileName);
                    tempN++; // move one forward cus assembly
                } else {
                    tempN = Table.byName(symbol);
                }
                temp = "@" + Integer.toString(tempN);
            }

            if (temp != null) {
                writer.write(temp); // has to write something
                // if not label skip line
                if (!instructionType(line).equals("(L)")) {
                    writer.newLine();

                }

                writer.close();
            }
        }
    }

    // input: name of label needed to find
    // output: line of label

    private int FindLabelLine(String symbol, String FileName) throws IOException {
        BufferedReader tempRead;
        int LT = 0;
        int downGrade = 0;
        tempRead = new BufferedReader(new FileReader(FileName));

        String line = tempRead.readLine();
        while (line != null) {
            if (symbol(line).equals(symbol) && "(L)".equals(instructionType(line))) {
                tempRead.close();
                return LT - downGrade;
                // problem when there are comments
            }
            if ("(L)".equals(instructionType(line))) {
                downGrade++;
            }
            if (!isComment(line)) {
                LT++;
            }
            line = tempRead.readLine();

        }
        System.out.println("monkey, you code has unused label");
        tempRead.close();
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
