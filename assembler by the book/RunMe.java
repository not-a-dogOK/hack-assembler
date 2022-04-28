import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * main
 */
public class RunMe {

    public static void main(String[] args) throws IOException {
        SymbolTable Table = new SymbolTable();
        for (int i = 0; i < 16; i++) {
            //System.out.println(i);
            Table.addEntry(i, "R" + i);
            //System.out.println("R" + i);
        }
        Table.addEntry(16394, "SCREEN");
        Table.addEntry(24576, "KBD");
        parser parser = new parser();

        
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(parser.fileName));
                String line = reader.readLine();
                while (line != null) {
                    parser.writeline(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        int x = 16;
        //String line = parser1.line;
        String Type = "";
        // first pass
        //System.out.println("first pass");
        /**
        while (Type != "*") {
            Type = parser1.instactionType();
            if ((Type.equals("A") || Type.equals("L")) // adds to all symbols to table
                    && !Table.contains(line)) {
                line = parser1.symbol();
                Table.addEntry(x, line);
                x++;
            }

        }
        parser parser2 = new parser(" ");
        line = parser2.readline();
        String lineBin = "";
        try (FileWriter myWriter = new FileWriter("output.hack")) {
            while (line != "*") {
                line = parser2.symbol();
                lineBin = lineBin + Code.dest(parser2.dest());
                lineBin = lineBin + Code.comp(parser2.comp());
                lineBin = lineBin + Code.jump(parser2.jump());
                myWriter.write(lineBin); // TO DO: write there
            }

            myWriter.close();
        }
        System.out.println("Successfully wrote to the file.");
    }
     */
    } 
}
