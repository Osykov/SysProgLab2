import java.io.*;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Main input.txt output.txt");
			return;
		}
		FileReader fr = null;
		try {
			fr = new FileReader(args[0]);
		} catch (FileNotFoundException ex) {
			System.out.println("Input file is not found.");
			return;
		}
		
		DFAReader r = new DFAReader();
		r.read(fr);
		
		Minimized dfa = new Minimized(r.alphabetLength, r.states + 1, r.state0, r.finalState, r.finishStates, r.transitions);
		
		dfa.minimize();
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(args[1]);
		} catch (IOException ex) {
			System.out.println("Output file cannot be opened or created.");
			return;
		}
		
		DFAWriter w = new DFAWriter();
		w.write(fw, dfa);
	}

}
