package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import graph.MazeGraph;
import graph.WeightedGraph;
import maze.Maze;

public class GeneralTests {

	@Test
	public void testAddVertexAndContainsVertex() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		assertTrue(graph.containsVertex("A"));
		assertTrue(graph.containsVertex("B"));
		assertTrue(graph.containsVertex("C"));
		assertTrue(graph.containsVertex("D"));
		assertFalse(graph.containsVertex("E"));
	}
	
	@Test
	public void testAddEdgeAndGetWeight() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "C", 2);
		graph.addEdge("A", "D", 3);
		graph.addEdge("B", "C", 4);
		graph.addEdge("D", "C", 5);
		assertTrue(graph.getWeight("A", "B") == 1);
		assertTrue(graph.getWeight("B", "A") == null);
		assertTrue(graph.getWeight("A", "C") == 2);
		assertTrue(graph.getWeight("A", "D") == 3);
		assertTrue(graph.getWeight("B", "C") == 4);
		assertTrue(graph.getWeight("D", "C") == 5);
		boolean caught = false;
		try {
			graph.getWeight("X",  "A");
		} catch (IllegalArgumentException e) {
			caught = true;
		}
		assertTrue(caught);
		caught = false;
		try {
			graph.getWeight("A", "X");
		} catch (IllegalArgumentException e) {
			caught = true;
		}
		assertTrue(caught);
		assertTrue(graph.getWeight("B", "D") == null);
	}
	
	
	
	//MY TESTs
	
	
	@Test
	public void testBFS() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");
		
		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "C", 2);
		graph.addEdge("A", "D", 3);
		
		graph.addEdge("B", "E", 4);
		graph.addEdge("B", "A", 5);
		
		graph.addEdge("C", "A", 1);
		graph.addEdge("C", "D", 2);
		graph.addEdge("C", "F", 3);
		
		graph.addEdge("D", "A", 4);
		graph.addEdge("D", "C", 5);
		
		graph.addEdge("E", "B", 1);
		graph.addEdge("E", "F", 2);
		
		graph.addEdge("F", "C", 3);
		graph.addEdge("F", "E", 4);
		graph.addEdge("F", "G", 5);
		graph.addEdge("F", "H", 3);
		
		graph.addEdge("G", "F", 4);
		
		graph.addEdge("H", "F", 5);
		
		
		graph.DoBFS("A", "H");
	}
	
	
	@Test
	public void testDFS() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");
		
		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "C", 2);
		graph.addEdge("A", "D", 3);
		
		graph.addEdge("B", "E", 4);
		graph.addEdge("B", "A", 5);
		
		graph.addEdge("C", "A", 1);
		graph.addEdge("C", "D", 2);
		graph.addEdge("C", "F", 3);
		
		graph.addEdge("D", "A", 4);
		graph.addEdge("D", "C", 5);
		
		graph.addEdge("E", "B", 1);
		graph.addEdge("E", "F", 2);
		
		graph.addEdge("F", "C", 3);
		graph.addEdge("F", "E", 4);
		graph.addEdge("F", "G", 5);
		graph.addEdge("F", "H", 3);
		
		graph.addEdge("G", "F", 4);
		
		graph.addEdge("H", "F", 5);
		
		
		graph.DoDFS("A", "B");
	}
	
	@Test
	public void testDijsktra() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");
		graph.addVertex("I");
		graph.addVertex("J");
		graph.addVertex("K");
		
		graph.addEdge("A", "G", 1);
		graph.addEdge("A", "H", 2);
		
		graph.addEdge("B", "G", 10);
		graph.addEdge("B", "H", 14);
		graph.addEdge("B", "I", 6);
		graph.addEdge("B", "F", 2);
		graph.addEdge("B", "D", 1);
		
		graph.addEdge("C", "F", 4);
		graph.addEdge("C", "E", 10);
		
		graph.addEdge("D", "B", 1);
		graph.addEdge("D", "K", 2);
		
		graph.addEdge("E", "F", 1);
		graph.addEdge("E", "G", 3);
		graph.addEdge("E", "K", 8);
		graph.addEdge("E", "J", 7);
		graph.addEdge("E", "C", 10);
		
		graph.addEdge("F", "B", 2);
		graph.addEdge("F", "E", 1);
		graph.addEdge("F", "J", 6);
		graph.addEdge("F", "C", 4);
		
		graph.addEdge("G", "A", 1);
		graph.addEdge("G", "B", 10);
		graph.addEdge("G", "E", 3);
		
		graph.addEdge("H", "A", 2);
		graph.addEdge("H", "B", 14);
		graph.addEdge("H", "I", 4);
		
		graph.addEdge("I", "H", 4);
		graph.addEdge("I", "B", 6);
		graph.addEdge("I", "K", 9);
		
		graph.addEdge("J", "E", 7);
		graph.addEdge("J", "F", 6);
		
		graph.addEdge("K", "I", 9);
		graph.addEdge("K", "E", 8);
		graph.addEdge("K", "D", 2);
		
		graph.DoDijsktra("H", "J");
	}
	
	@Test
	public void testMazeGraph() {
		Maze maze = new Maze(5, 4, 1, 6);
		MazeGraph mazeGraph = new MazeGraph(maze);
		
	
	}
	
	@Test
	public void testDFS2() {
		WeightedGraph<String> graph = new WeightedGraph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");
		graph.addVertex("I");
		graph.addVertex("J");
		graph.addVertex("K");
		
		graph.addEdge("A", "G", 1);
		graph.addEdge("A", "H", 2);
		
		graph.addEdge("B", "G", 10);
		graph.addEdge("B", "H", 14);
		graph.addEdge("B", "I", 6);
		graph.addEdge("B", "F", 2);
		graph.addEdge("B", "D", 1);
		
		graph.addEdge("C", "F", 4);
		graph.addEdge("C", "E", 10);
		
		graph.addEdge("D", "B", 1);
		graph.addEdge("D", "K", 2);
		
		graph.addEdge("E", "F", 1);
		graph.addEdge("E", "G", 3);
		graph.addEdge("E", "K", 8);
		graph.addEdge("E", "J", 7);
		graph.addEdge("E", "C", 10);
		
		graph.addEdge("F", "B", 2);
		graph.addEdge("F", "E", 1);
		graph.addEdge("F", "J", 6);
		graph.addEdge("F", "C", 4);
		
		graph.addEdge("G", "A", 1);
		graph.addEdge("G", "B", 10);
		graph.addEdge("G", "E", 3);
		
		graph.addEdge("H", "A", 2);
		graph.addEdge("H", "B", 14);
		graph.addEdge("H", "I", 4);
		
		graph.addEdge("I", "H", 4);
		graph.addEdge("I", "B", 6);
		graph.addEdge("I", "K", 9);
		
		graph.addEdge("J", "E", 7);
		graph.addEdge("J", "F", 6);
		
		graph.addEdge("K", "I", 9);
		graph.addEdge("K", "E", 8);
		graph.addEdge("K", "D", 2);
		
		graph.DoDFS("H", "J");
	}
	
	
}