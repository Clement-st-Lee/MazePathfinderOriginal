package graph;
import java.util.Set;

import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {


	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and 😎 which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		int cols = maze.getMazeWidth();
		int rows = maze.getMazeHeight();

		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				addVertex(new Juncture(c,r)); //(cols, rows) b/c (X,Y)
			}
		}

	
		Set<Juncture> junctures = weightedGraph.keySet();

		for(Juncture j: junctures) {
			
			/**
			 * For the four if statements the first condition is to check if the vertex is on the edge of the graph
			 * if it is then there can't be a wall at it's next condition 
			 * **/
			if( j.getY() > 0 && !(maze.isWallAbove(j)) ) { //must be greater than 0
				addEdge(j, new Juncture(j.getX(), j.getY() - 1), maze.getWeightAbove(j));
			}
			
			if( j.getY() <= rows && !(maze.isWallBelow(j)) ) { //looks on above
				addEdge(j, new Juncture(j.getX(), j.getY() + 1), maze.getWeightBelow(j));
			}
			
			if( j.getX() > 0 && !(maze.isWallToLeft(j)) ) { //looks one left
				addEdge(j, new Juncture(j.getX() - 1, j.getY()), maze.getWeightToLeft(j));
			}
			
			if( j.getX() <= cols && !(maze.isWallToRight(j)) ) { //looks one right
				addEdge(j, new Juncture(j.getX() + 1, j.getY()), maze.getWeightToRight(j));
			}

		}




	}



	
}