package NPuzzle;
import java.util.LinkedList;

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
