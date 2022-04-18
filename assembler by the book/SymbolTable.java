public class SymbolTable {
    int[] value;
    String[] symbol;

    public SymbolTable() {
        // max of symbol ?
        this.value = new int[999];
        this.symbol = new String[999];
        for (int i = 0; i < 999; i++) {
            value[i] = -1;
        }
        for (int i = 0; i < 999; i++) {
            symbol[i] = "null";
        }
        
    }
    // WTF? ?
    public void addEntry(int valuein, String symbolIn) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] == valuein && symbol[i] == "null") {
                symbol[i] = symbolIn;
            }
        }
    }
    //in: symbol 
    //out if its in the table
    public boolean contains(String symbolIn) {
        for (int i = 0; i < symbol.length; i++) {
            if (symbol[i] == symbolIn) {
                return true;
            }
        }
        return false;

    }
    //returns the address of symbol
    public int getAdress(String symbolIn) {
        for (int i = 0; i < symbol.length; i++) {
            if(symbolIn == symbol[i])
            return i;
        }
        return -1;
    }

}
