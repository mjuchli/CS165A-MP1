import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class AStarSearch {
	
	public class Node implements Comparable<Node> {
		
		protected LinkedList<Node> successors = new LinkedList<>();
		public Integer costs = 0; //will be cost of parent state + pathcost
		public Integer pathCost = 1; //always 1 for each additional state
	    public Integer heuristicCost = 0; //will be calculated
		public Node parent;
		public String[][] puzzle;
		public int xX;
		public int yX;
		public String path = "";
		
		public Node(String[][] input) {
			this.puzzle = input;
		}
		
		public Node(String[][] input, Node parent, String path) {
			this.puzzle = input;
			this.parent = parent;
			this.path = this.parent.path + path;
		}
		
		/**
		 * Generates successors out of the current state limited by puzzle possibilities
		 * @return
		 */
		public LinkedList<Node> getSuccessors(){			
			int n = puzzle.length;
			
			//find left
			if(xX != 0){
				String[][] leftPuzzle = new String[n][n];
				leftPuzzle = copyPuzzle(leftPuzzle, this.puzzle, n);
				leftPuzzle[yX][xX] = leftPuzzle[yX][xX-1];
				leftPuzzle[yX][xX-1] = "X";
				Node leftNode = new Node(leftPuzzle, this, "l");
				leftNode.findX();
				successors.add(leftNode);
			}
			
			//find up
			if(yX != 0){
				String[][] upPuzzle = new String[n][n];
				upPuzzle = copyPuzzle(upPuzzle, this.puzzle, n);
				upPuzzle[yX][xX] = upPuzzle[yX-1][xX];
				upPuzzle[yX-1][xX] = "X";
				Node upNode = new Node(upPuzzle, this, "u");
				upNode.findX();
				successors.add(upNode);
			}
			
			//find right
			if(xX != n-1){
				String[][] rightPuzzle = new String[n][n];
				rightPuzzle = copyPuzzle(rightPuzzle, this.puzzle, n);
				rightPuzzle[yX][xX] = new String(rightPuzzle[yX][xX+1]);
				rightPuzzle[yX][xX+1] = new String("X");
				Node rightNode = new Node(rightPuzzle, this, "r");
				rightNode.findX();
				successors.add(rightNode);
			}
			
			//find down
			if(yX != n-1){
				String[][] downPuzzle = new String[n][n];
				downPuzzle = copyPuzzle(downPuzzle, this.puzzle, n);
				downPuzzle[yX][xX] = downPuzzle[yX+1][xX];
				downPuzzle[yX+1][xX] = "X";
				Node downNode = new Node(downPuzzle, this, "d");
				downNode.findX();
				successors.add(downNode);
			}
			
			return successors;
		}
		
		/**
		 * Helper method to copy a puzzle without references of String
		 * @param newPuzzle
		 * @param oldPuzzle
		 * @param n
		 * @return
		 */
		private String[][] copyPuzzle(String[][] newPuzzle, String[][] oldPuzzle, int n){
			for(int i = 0; i < n; i++){
				for(int j = 0; j< n; j++){
					newPuzzle[i][j] = oldPuzzle[i][j];
				}
			}
			return newPuzzle;
		}

		@Override
		public int compareTo(Node n) {
			if(this.costs < n.costs){
				return -1;
			} else if(this.costs > n.costs){
				return 1;
			} else {
				return 0;
			}
		}
		
		/**
		 * Helper method to set coordinates of X for the current state
		 */
		public void findX(){
			int n = puzzle.length;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					if(puzzle[i][j].equals("X")){
						this.yX = i;
						this.xX = j;
					}
				}
			}
		}
	}
	
	public LinkedList<Node> openList = new LinkedList<>();
	public LinkedList<Node> closedList = new LinkedList<>();
	public Node goalState;
	public Node startState;
	
	public AStarSearch(String input) throws IOException {
		String inputFile = readFile(input);
		startState = generateStateFromString(inputFile);
		startState.path = "root:";
		startState.findX();
		
		openList.add(startState);
		goalState = getGoalState();
	}
	
	/**
	 * Reads text file an generates string
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String readFile(String file) throws IOException{
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
	 * Lookup a list for containing state 
	 * @param node
	 * @param list
	 * @return
	 */
	protected Node searchList(Node node, LinkedList<Node> list){
		ListIterator<Node> itr = list.listIterator();
		while(itr.hasNext()){
			Node next = itr.next();
			if(comparePuzzle(next.puzzle, node.puzzle)){
				return next;
			}
		}
		return null;
	}
	
	/**
	 * Generates a state out of a string
	 * @param input
	 * @return
	 */
	protected Node generateStateFromString(String input) {
		if(input.charAt(input.length()-1) == '\n'){
			generateStateFromString(input.substring(0, input.length()-1));
		}
		
		String[] y = input.split("\n");
		String[][] x = new String[y.length][y.length];
		for(int i = 0; i < y.length; i++){
			x[i] = y[i].split(" ");
		}
	    
	    return new Node(x);
		
	}
	
	/**
	 * Generates goal state according start state
	 * @return Node
	 */
	public Node getGoalState(){
		int lenght = startState.puzzle.length;
		String[][] goal = new String[lenght][lenght];
		int tile = 1;
		for(int i = 0; i < startState.puzzle.length; i++){
			for(int j = 0; j < startState.puzzle.length; j++){
				goal[i][j] = String.valueOf(tile);
				tile++;
			}
		}
		goal[lenght-1][lenght-1] = "X";
		return new Node(goal);
	}
		
	
	/**
	 * manhattan
	 * 
	 * @param currentNode
	 * @return integer
	 */
	private int heuristics(Node currentNode){
	    return Math.abs(currentNode.xX - goalState.xX) + Math.abs(currentNode.yX - goalState.yX);
	}
	
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
	
	/**
	 * A-star algorithm 
	 * @return Node | null
	 */
	public Node search(){
		while(!openList.isEmpty()){
			
			Node currentNode = openList.getLast();
			
			if (comparePuzzle(currentNode.puzzle, goalState.puzzle)){
				return currentNode;
			}
			
			for(Node successor : currentNode.getSuccessors()){
				successor.heuristicCost = heuristics(successor);
				successor.costs = currentNode.costs + successor.pathCost + successor.heuristicCost;
				
				Node findOpen = searchList(successor, openList);
				Node findClosed = searchList(successor, closedList);
				
				if (findOpen != null && findOpen.compareTo(successor) <= 0) continue;
				if (findClosed != null && findClosed.compareTo(successor) <= 0) continue;
				
				openList.remove(findOpen);
				closedList.remove(findClosed);
				
				openList.add(successor);
			}
			
			closedList.add(currentNode);
			
		}
		
		return null;
	}
	
	public static void main(String[] args){
		
		try {
			AStarSearch astar = new AStarSearch("/Users/Marc/Dropbox/School/UCSB/CS165A-MP1/testPuzzle.txt");
			Node result = astar.search();
			System.out.println(result.path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
   
}