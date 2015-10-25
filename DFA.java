import java.util.*;

public class DFA {
	
	int alphabetLength;
	int statesCount;
	int startState;
	int finalStatesCount;
	List<Integer> finalStates = new LinkedList<Integer>();
	Set<Transition> transitions = new HashSet<Transition>();
	Map<StartStateSymbol, Integer> transitFunction = new HashMap<StartStateSymbol, Integer>();
	
	DFA(int a, int sCount, int s0, int fCount, String[] finishState, List<String> tr) {
		alphabetLength = a;
		statesCount = sCount;
		startState = s0;
		finalStatesCount = fCount;
		for (int i = 1; i <= finalStatesCount; i++) {
			finalStates.add(Integer.parseInt(finishState[i]));
		}
		
		try {
		for (String s : tr) {
			String[] ss = s.split(" ");
			addTransition(Integer.parseInt(ss[0]), ss[1].charAt(0), Integer.parseInt(ss[2]));
		}}
		catch (Exception e){}
	}
	
	void setAlphabetLength(int length) {
		alphabetLength = length;
	}
	
	void setStatesCount(int count) {
		statesCount = count;
	}
	
	void setStartState(int state) {
		startState = state;
	}
	
	void setFinalStatesCount(int count) {
		finalStatesCount = count;
	}
	
	void addFinalState(int state) {
		finalStates.add(state);
	}
	
	void addTransition(int start, char symbol, int target) {
		transitions.add(new Transition(start, symbol, target));
		transitFunction.put(new StartStateSymbol(start, symbol), target);
	}
}
