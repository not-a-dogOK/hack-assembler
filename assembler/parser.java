import java.io.*;
import java.util.Scanner;

public class parser {
    BufferedReader bufferedReader = null;
    public int lineindex;
    // constractior 
    // read the file line by line
    public parser() throws FileNotFoundException {

        Scanner reader = new Scanner(System.in);
        String fileName = reader.nextLine();
        FileReader fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);
    }

    public String readline(int num) throws IOException {
        String line = "";
        for (int i = 0; i < num; i++) {
            line = bufferedReader.readLine();

        }
        return line;

    }

    public boolean hasMoreLines() throws IOException {
        if (bufferedReader.readLine() != null) {
            return true;
        }
        return false;
    }

    public void advance() throws IOException {
        if (hasMoreLines()) {
            String line = readline(lineindex);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i + 1) == line.charAt(i) && line.charAt(i) == '/') {
                    lineindex++;
                }
            }
        }
    }

    public String instactionType() throws IOException {
        String line = readline(lineindex);
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

    public String symbol() throws IOException {
        String line = readline(lineindex);
        for (int i = 0; i < line.length(); i++) {

            if (instactionType() == "L") {
                if (line.charAt(i) != '(' || line.charAt(i) != ')') {
                    line = line.substring(0, i) + line.substring(i + 1);
                }
            }
            if (instactionType() == "A") {
                if (line.charAt(i) != '@') {
                    line = line.substring(0, i) + line.substring(i + 1);
                }
            }
        }
        return line;
    }

    public String dest() throws IOException {
        String line = readline(lineindex);
        String newLine = "";
        int i = 0;
        int j = 0;
        while (line.charAt(i) == ' ') {
            i++;
        }
        while (line.charAt(j) == '=') {
            j++;
        }
        while (i > j) {
            newLine = newLine + line.charAt(i);
        }
        return newLine;
    }

    public String comp() throws IOException {
        String line = readline(lineindex);
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
        }
        newLine = newLine + line.charAt(j);
        return newLine;
    }

    public String jump() throws IOException {
        String line = readline(lineindex);
        String newLine = "";
        int i = 0;
        int j = line.length();
        while (line.charAt(i) == ';') {
            i++;
        }
        while (i < j) {
            newLine = newLine + line.charAt(i);
        }
        newLine = newLine + line.charAt(j);
        return newLine;
    }
}
