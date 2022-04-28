import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class RunMe {

    public static void main(String[] args) throws IOException {
        /**
         * main
         */
        SymbolTable Table = new SymbolTable();
        for (int i = 0; i < 16; i++) {
            Table.addEntry(i, "R" + i);
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
    }
}
