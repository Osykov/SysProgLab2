import java.io.*;
import java.util.*;

public class DFAReader {
	
	int alphabetLength;
	int states;
	int state0;
	int finalState;
	String[] finishStates;
	List<String> strings = new ArrayList<String>();
	List<String> transitions = new ArrayList<String>();
	
	void read(FileReader fr) {
		BufferedReader br = new BufferedReader(fr);	
		try {
			for (; ; ) {
				String s = br.readLine();
				if (s == null) 
					break;
				strings.add(s);
			}
		} catch (IOException e) {
			System.out.println("Error while reading.");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.out.println("Cannot close input file");
				e.printStackTrace();
			}
		}
		
		try {
			alphabetLength = Integer.parseInt(strings.get(0));
			
			states = Integer.parseInt(strings.get(1));
			
			state0 = Integer.parseInt(strings.get(2));
	
			finishStates = strings.get(3).split(" ");
			
			finalState = (Integer.parseInt(finishStates[0]));
			
			for (int i = 4; i < strings.size(); i++) {
				transitions.add(strings.get(i));
			}
		} catch (Exception ex) {
			System.out.println("File format error");
		}
	}
	
}
