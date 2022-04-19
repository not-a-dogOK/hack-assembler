import java.io.*;
import java.util.Scanner;

public class parser {
    public String line;
    public File file;
    public Scanner readFile;

    public parser(String pass) throws IOException { // reset?
        if (pass.equals("first")) {
            System.out.println("name? with '\' at start");
            Scanner reader = new Scanner(System.in);
            String fileName = System.getProperty("user.dir") + reader.nextLine() + ".asm";
            File file = new File(fileName);
            this.readFile = new Scanner(file);
            if (file.exists()) {
                System.out.println("file found!");
                System.out.println("File name: " + file.getName());
                System.out.println("Absolute path: " + file.getAbsolutePath());
                System.out.println("Writeable: " + file.canWrite());
                System.out.println("Readable " + file.canRead());
                System.out.println("File size in bytes " + file.length());
            }
        }
        this.line = readFile.nextLine();
        System.out.println(this.line);

    }

    public String readline() throws IOException {
        String Line = readFile.nextLine();
        if (Line != null) {
            return Line;
        }
        return null;
        
    }

    /** 
     * public boolean hasMoreLines(int num) throws IOException {
        return readline() != null;
    }
    */
    

   

    // returns the istra in one letter A C or L
    public String instactionType() throws IOException {
        String line = readline();
        System.out.println(line);
        for (int j = 0; j < line.length(); j++) {
            if (line.charAt(j) == '/' && line.charAt(j + 1) == '/') {
                instactionType();
            }
            if (line.charAt(j) == '(') {
                return "L";
            }
            if (line.charAt(j) == '@') {
                return "A";
            }
            if (line.charAt(j) == '=') {
                System.out.println("C");
                return "C";
            }
            

        }
        return null;

    }

    // removes the '@', '(' and ')' making the line clean for the code translator
    public String symbol() throws IOException {
        String line = readline();
        for (int i = 0; i < line.length(); i++) {

            if ("L".equals(instactionType())) {
                if (line.charAt(i) != '(' || line.charAt(i) != ')') {
                    line = line.substring(0, i) + line.substring(i + 1);
                }
            }
            if ("A".equals(instactionType())) {
                if (line.charAt(i) != '@') {
                    line = line.substring(0, i) + line.substring(i + 1);
                }
            }
        }
        return line;
    }

    // return a String with dest Keyword
    public String dest() throws IOException {
        String line = symbol();
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

    // return a String with comp Keywords
    public String comp() throws IOException {
        String line = symbol();
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

    // return a String with jump Keyword
    public String jump() throws IOException {
        String line = symbol();

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
