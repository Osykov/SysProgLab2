import java.io.*;

public class DFAWriter {

	void write(FileWriter fw, DFA dfa) {
		try {
			fw.append(dfa.alphabetLength + "\n");
			fw.append(dfa.statesCount + "\n");
			fw.append(dfa.startState + "\n");
			fw.append(dfa.finalStatesCount + " ");
			for (int i : dfa.finalStates) {
				fw.append(i + " ");
			}
			fw.append("\n");
			for (Transition tr : dfa.transitions) {
				fw.append(tr + "\n");
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
