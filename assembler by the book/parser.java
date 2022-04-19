import java.io.*;
import java.util.Scanner;

public class parser {
    public static BufferedReader bufferedReader = null;
    public static int lineindex;
    // constractior 
    // read the file line by line
    public parser() throws FileNotFoundException {
        System.out.println("name?");
        Scanner reader = new Scanner(System.in);
        String fileName = reader.nextLine();
        FileReader fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);
    }

    public static String readline(int num) throws IOException {
        String line = "";
        for (int i = 0; i < num; i++) {
            line = bufferedReader.readLine();

        }
        return line;

    }

    public static boolean hasMoreLines() throws IOException {
        if (bufferedReader.readLine() != null) {
            return true;
        }
        return false;
    }
    // advance the line reading in one line
    public static void advance() throws IOException {
        if (hasMoreLines()) {
            String line = readline(lineindex);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i + 1) == line.charAt(i) && line.charAt(i) == '/') { //remove comment
                    lineindex++;
                }
            }
        }
    }
    // returns the istra in one letter A C or L
    public static String instactionType() throws IOException {
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
    //removes the '@', '(' and  ')' making the line clean for the code translator 
    public static String symbol() throws IOException {
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
    // return a String with dest Keyword 
    public static String dest() throws IOException {
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
    //return a String with comp Keywords
    public static String comp() throws IOException {
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
     //return a String with jump Keyword
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
