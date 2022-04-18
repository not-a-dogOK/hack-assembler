import java.io.IOException;

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

        while (parser.hasMoreLines()) 
        {
            if((parser.instactionType() == "A" || parser.instactionType() == "L") && !Table.contains(parser.symbol())) {
                addEntry(parser.symbol());
            } 
        }
    }
}
