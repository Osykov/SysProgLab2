import java.util.*;


public class Minimized extends DFA {
	
	boolean [] used = new boolean[statesCount];
	boolean [][] arcs = new boolean[statesCount][statesCount];
	boolean [][] marked = new boolean[statesCount][statesCount];
	int [] component = new int[statesCount];
	List<StatePair> Q = new LinkedList<StatePair>();
	Map<Integer, Set<Integer>> comp = new HashMap<Integer, Set<Integer>>();
	int componentsCount = 0;
	
	Minimized (int a, int sCount, int s0, int fCount, String[] finishState, List<String> transitions) {
		super(a, sCount, s0, fCount, finishState, transitions);
	}
	
	void minimize() {
		addDeadState();
		initializeArcs();
		step1_dfs(startState);
		step2();
		step3();
		step4();
		step5();
	}
	
	void addDeadState() {
		for (int i = 0; i < statesCount; i++) {
			for (int j = 0; j < alphabetLength; j++) {
				StartStateSymbol s = new StartStateSymbol(i, (char)('a' + j));
				if (!transitFunction.containsKey(s)) {
					transitFunction.put(new StartStateSymbol(i, (char)('a' + j)), 0);
					transitions.add(new Transition(i, (char)('a' + j), 0));
				}
			}
		}
	}
	
	void initializeArcs() {
		for (Transition t : transitions) {
			arcs[t.start][t.target] = true;
		}
	}
	
	void step1_dfs(int state) {
		used[state] = true;
		for (int i = 0; i < used.length; i++) {
			if (!used[i] && arcs[state][i]) {
				step1_dfs(i);
			}
		}
	}
	
	// adding state pair that different by e-symbol
	void step2() {
		for (int i = 0; i < statesCount; i++) {
			for (int j : finalStates) {
				if (!finalStates.contains(i)) {
					Q.add(new StatePair(i, j));
					marked[i][j] = true;
					marked[j][i] = true;
				}
			}
		}
	}
	
	//marked table filling
	void step3() {
		while (!Q.isEmpty()) {
			StatePair pair = Q.remove(0);
			for (int i = 0; i < alphabetLength; i++) {
				for (Transition t : transitions) {
					for (Transition t2 : transitions) {
						if (pair.start == t.target && t.symbol == (char)('a' + i) && 
							pair.target == t2.target && t2.symbol == (char)('a' + i)) {
							int a = t.start;
							int b = t2.start;
							if (!marked[a][b]) {
								marked[a][b] = true;
								marked[b][a] = true;
								Q.add(new StatePair(a, b));
							}
						}
					}
				}
			}
		}
	}
	
	//splitting to components
	void step4() {
		for (int i = 0; i < statesCount; i++) {
			component[i] = -1;
		}
		
		for (int i = 0; i < statesCount; i++) {
			if(!marked[0][i]) {
				component[i] = 0;
			}
		}
		
		for (int i = 1; i < statesCount; i++) {
			if (!used[i]) {
				continue;
			}
			
			if (component[i] == -1) {
				Set<Integer> states = new HashSet<Integer>();
				componentsCount++;
				component[i] = componentsCount;
				for (int j = i + 1; j < statesCount; j++) {
					if (!marked[i][j]) {
						component[j] = componentsCount;
						states.add(j);
					}
				}
				comp.put(componentsCount, states);
			}
		}
	}
	
	// building minimized DFA
	void step5() {
		List<Integer> newFinalStates = new ArrayList<Integer>();
		for (int i = 1; i <= componentsCount; i++) {
			Set<Integer> states = new HashSet<Integer>();
			for (int j = 0; j < component.length; j++) {
				if (component[j] == i) {
					if (startState == j) {
						startState = i;
					}
					if (finalStates.contains(j)) {
						if (!newFinalStates.contains(i)) {
							newFinalStates.add(i);
						}
					}
					states.add(j);
				}
			}
			comp.put(i, states);
		}
		
		statesCount = componentsCount;
		finalStates = newFinalStates;
		finalStatesCount =finalStates.size();
		
		Set<Transition> newTransitions = new HashSet<Transition>();
		
		for (Transition t : transitions) {
			int start = 0;
			int target = 0;
			for (int i = 1; i <= componentsCount; i++) {
				if (comp.get(i).contains(t.start)) {
					start = i;
				}
				
				if (comp.get(i).contains(t.target)) {
					target = i;
				}
			}
			
			if (start != 0 && target != 0) {
				boolean is = false;
				for (Transition tb : newTransitions) {
					if (tb.start == start && tb.symbol == t.symbol && tb.target == target) {
						is = true;
					}
				}
				if (!is)
					newTransitions.add(new Transition(start, t.symbol, target));
			}
		}
		
		transitions = newTransitions;
	}
	
	class StatePair {
		
		int start;
		int target;
		
		StatePair(int start, int target) {
			this.start = start;
			this.target = target;
		}
		
		public boolean equals(StatePair o) {
			return (start == o.start && target == o.target) || (start == o.target && target == o.start);
		}
		
		
		@Override
		public int hashCode() {
			return start * 1000000 + target;
		}
	}
	
}
