public class value_symbol {
    public int value;
    public String symbol;
    public static int current = 0;

    public value_symbol(int index,String symbol) {
        // max of symbol ?
        this.value = index;
        this.symbol = symbol;
        current++;
        
        
    }

}
