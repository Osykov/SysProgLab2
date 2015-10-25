
public class Transition {
	
	int start;
	char symbol;
	int target;
	
	Transition(int start, char symbol, int target) {
		this.start = start;
		this.symbol = symbol;
		this.target = target;
	}
	
	public boolean equals(Transition t) {
		return start == t.start && symbol == t.symbol && target == t.target;
	}
	
	public String toString() {
		return start + " " + symbol + " " + target;
	}
	
	public int hashCode() {
		return 10000 * start + 100 * target + (int)symbol;
	}
}
