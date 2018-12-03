import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lexer {
	private String[][] dfa;
	private String[] alphabets;
	private int[] states;
	private int[] acceptState;
	private List<Boolean> output;
	
	public List<Boolean> lex() throws IOException{
		output = new ArrayList<Boolean>();
		Files files = new Files();
		String file1Name  = "txt1.txt";
		String file2Name  = "txt2.txt";
		List<String> file1 = files.fileToList(file1Name);
		List<String> file2 = files.fileToList(file2Name);
		init(file1);
		files.makeFileFromList(run(file2));
		return output;
	}
	private void init(List<String> lines) throws IOException{
		this.alphabets = makeAlphabets(lines);
		this.states = makeStates(lines);
		this.dfa = makeDFA(lines,alphabets, states.length);
		this.acceptState = makeAcceptedStates(lines);
	}
	private String[] makeAlphabets(List<String> lines){
        return lines.get(0).split(" ");//zero is first line. where alphabets are there.
	}
	public int[] makeStates(List<String> lines){
        String[] s = lines.get(1).split(" ");//1 is second line. where states are there.
        int [] states = new int[s.length];
		for(int i=0;i<s.length;i++) 
			states[i] = Integer.parseInt(s[i]);
			
		return states;
	}
	public int[] makeAcceptedStates(List<String> lines){
        String[] s = lines.get(2).split(" ");//2 is third line of first input file. it defines acceptedStates.
        int [] acceptedStates = new int[s.length];
		for(int i=0;i<s.length;i++) 
			acceptedStates[i] = Integer.parseInt(s[i]);
			
		return acceptedStates;
	}
	public String[][] makeDFA(List<String> lines, String[] alphabets, int statesLength){
		String[][] dfa = new String[statesLength][alphabets.length];
		for(int i=3;i<lines.size();i++){
        	int start = Integer.parseInt(String.valueOf(lines.get(i).charAt(0)));//this is first state
        	String value = String.valueOf(lines.get(i).charAt(4));//this is second state (the value of DFA array)
        	String alphabet = String.valueOf(lines.get(i).charAt(2));
        	int position = Arrays.asList(alphabets).indexOf(alphabet);
        	dfa[start][position] = value;
        }
		return dfa;
	}
	public List<Boolean> run(List<String> file2){
		char[] word;
		for (int i=0;i<file2.size();i++){
			word = file2.get(i).toCharArray();
			boolean bool = true;
			int curState = 0;
			for (int j=0;j<word.length;j++){
				int position = Arrays.asList(alphabets).indexOf(String.valueOf(word[j]));//position defines column of that char.
				if (dfa[curState][position] != null){
					curState = Integer.parseInt(dfa[curState][position]);
					if (j==word.length-1 && !isAcceptedState(curState)) {
						bool = false;
						break;
					}
				}else {
					bool = false;
					break;
				}
			}
			output.add(bool);
		}
		return output;
	}
	public boolean isAcceptedState(int state){
		boolean b = false;
		for (int i=0;i<acceptState.length;i++){
			if (acceptState[i]==state) {
				b = true;
				break;
			}
		}
		return b;
	}
}
