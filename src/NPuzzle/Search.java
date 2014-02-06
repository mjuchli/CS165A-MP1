package NPuzzle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

abstract class Search {
	
	public Node goalNode;
	public Node startNode;
	
	public Search(String input) throws IOException {
		String inputFile = readFile(input);
		startNode = new Node(generateStateFromString(inputFile), "");
		goalNode = new Node(getGoalState(startNode.puzzle.length), "goal");
	}
	
	/**
	 * Reads text file an generates string
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected String readFile(String file) throws IOException{
	 	BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        String everything = sb.toString();
	        
	        return everything;
	    } finally {
	        br.close();
	    }
	}
	
	/**
	 * Generates a state out of a string
	 * @param input
	 * @return
	 */
	protected String[][] generateStateFromString(String input) {
		if(input.charAt(input.length()-1) == '\n'){
			generateStateFromString(input.substring(0, input.length()-1));
		}
		
		String[] y = input.split("\n");
		String[][] state = new String[y.length][y.length];
		for(int i = 0; i < y.length; i++){
			state[i] = y[i].split(" ");
		}
		
		return state;
		
	}
	
	/**
	 * Generates goal state according start state 
	 * @param lenght
	 * @return
	 */
	public String[][] getGoalState(int lenght){
		String[][] goal = new String[lenght][lenght];
		int tile = 1;
		for(int i = 0; i < lenght; i++){
			for(int j = 0; j < lenght; j++){
				goal[i][j] = String.valueOf(tile);
				tile++;
			}
		}
		goal[lenght-1][lenght-1] = "X";
		
		return goal;
	}
	
	/**
	 * Compares two puzzles
	 * 
	 * @param p1
	 * @param p2
	 * @return boolean
	 */
	public boolean comparePuzzle(String[][] p1, String[][] p2){
		if(p1.length != p2.length)
			return false;
		
		for(int i = 0; i < p1.length; i++){
			for(int j = 0; j < p1.length; j++){
				if(!p1[i][j].equals(p2[i][j])){
					return false;
				}
			}
		}
		return true;
	}
}
