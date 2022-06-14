public class SymbolTable {
    public value_symbol[] SymbolTable;

    public SymbolTable() {
        this.SymbolTable = new value_symbol[999];
        // did i ask?
    }

    public void add(int index, String symbol) {
        SymbolTable[value_symbol.current] = new value_symbol(index, symbol);
    }

    public String byIndex(int num) {
        for (int i = 0; i < SymbolTable.length; i++) {
            if (SymbolTable[i].value == num) {
                return SymbolTable[i].symbol;
            }
        }
        return "your mom";
    }

    public int byName(String name) {
        for (int i = 0; i < SymbolTable.length; i++) {
            if (SymbolTable[i] == null) {
                return -1;
            }
            if (SymbolTable[i].symbol.equals(name)) {
                return SymbolTable[i].value;
            }
        }
        return -1;
    }

}
