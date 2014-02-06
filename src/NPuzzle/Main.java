package NPuzzle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {

	public static void main(String[] args){
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("solution.txt", "UTF-8");
			
			if(args.length <= 0){
				writer.println("no input file (path) defined.");	
				System.out.println("no input file (path) defined.");
			} else {
			
				try {
					String input;
					input = args[0];
					
					double startTime = System.currentTimeMillis();
					
					AStarSearch astar = new AStarSearch(input);
					Node result = astar.search();
					if(result == null){
						System.out.println("cannot be solved");
						writer.println("cannot be solved");
					} else {
						System.out.println(result.path);
						writer.println(result.path);
					}
					
					double endTime   = System.currentTimeMillis();
					double totalTime = endTime - startTime;
					System.out.println(totalTime/1000+" seconds");
					writer.println(totalTime/1000+" seconds");
				} catch (IOException e) {
					System.out.println("cannot read/write file");
					//e.printStackTrace();
				} catch (Exception e) {
					System.out.println(e.toString());
					//e.printStackTrace();
				}	
			
			}
		
			writer.close();
		
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			System.out.println("File not found.");
			//e1.printStackTrace();
		}
	}
	

}
