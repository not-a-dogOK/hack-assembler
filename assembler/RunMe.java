import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class RunMe {

    public static void main(String[] args) throws IOException {
        /**
         * main
         */

        // first pass
        SymbolTable Table = new SymbolTable();
        for (int i = 0; i < 16; i++) {
            Table.add(i, "R" + i);
        }
        
        Table.add(16394, "SCREEN");
        Table.add(24576, "KBD");
        parser parser = new parser();

        BufferedReader reader;
        String fileName;
        System.out.println("name? with '\' at start");
        Scanner scanner = new Scanner(System.in);
        fileName = System.getProperty("user.dir") + scanner.nextLine() + ".asm";
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("opening file....");
            System.out.println("File name: " + file.getName());
            System.out.println("path: " + file.getAbsolutePath());
            System.out.println("Writeable: " + file.canWrite());
            System.out.println("Readable " + file.canRead());
            System.out.println("File size in bytes " + file.length());
        }
        int L = 0; // L of line
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                parser.firstpass(line, L, Table);
                line = reader.readLine();
                L++;
            }
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // second pass
        L = 0; // L of line
        try {
            reader = new BufferedReader(new FileReader(parser.fileName));
            String line2 = reader.readLine();
            while (line2 != null) {
                parser.writeline(line2, L);
                line2 = reader.readLine();
                L++;
            }
            reader.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}