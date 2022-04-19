import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;

/**
 * main
 */
public class main {

    public static void main(String[] args) throws IOException {
        SymbolTable Table = new SymbolTable();
        for (int i = 0; i < 16; i++) {
            System.out.println(i);
            Table.addEntry(i, "R" + i);
            System.out.println("R" + i);
        }
        Table.addEntry(16394, "SCREEN");
        Table.addEntry(24576, "KBD");
        int x = 16;
        String line;
        while (parser.hasMoreLines()) {
            
            if ((parser.instactionType() == "A" || parser.instactionType() == "L") //adds to all symbols to table
                && !Table.contains(line) ) {
                line = parser.symbol();
                Table.addEntry(x, line);
                x++;
                parser.advance();
            }
            

        }
        try { //try?
            System.out.println("name?");
            Scanner reader = new Scanner(System.in);
            String name = reader.nextLine();
            File putputFile = new File(name);
            if (putputFile.createNewFile()) {
                System.out.println("File created: " + putputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(name + ".txt");
            myWriter.write(); //TO DO: write there 
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) { // if eror 
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
