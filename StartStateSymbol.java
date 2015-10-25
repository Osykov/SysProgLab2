

public class StartStateSymbol {
	
	int start;
	char symbol;
	
	StartStateSymbol(int start, char symbol) {
		this.start = start;
		this.symbol = symbol;
	}
	
	@Override 
	public String toString() {
		return start + " " + symbol;
	}

	@Override
	public boolean equals(Object o) {
		
		return start == ((StartStateSymbol)o).start && symbol == ((StartStateSymbol)o).symbol;
	}
	
	@Override
	public int hashCode() {
		return start * 100 + symbol;
	}
	
}
