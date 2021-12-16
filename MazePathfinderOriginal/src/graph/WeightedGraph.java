                
package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {



	HashMap<V,HashMap<V,Integer>> weightedGraph;
	private final int INFINITY = (int)Double.POSITIVE_INFINITY; //used in disktras algo



	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;




	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		weightedGraph = new HashMap<>(); //initialize with empty hashmap
		observerList = new ArrayList<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * If there isn't already a vertex then create a new one with a empty Hash Map that is ready to take in adjacent
	 * vertices information
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if(weightedGraph.containsKey(vertex)) { //make sure has the V first
			throw new IllegalArgumentException();
		}

		HashMap<V,Integer> adjacentVertices = new HashMap<>();
		weightedGraph.put(vertex, adjacentVertices);          //map new vertex to a new empty HashMap
	}

	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		return weightedGraph.containsKey(vertex);
	}



	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if( !(containsVertex(from) && containsVertex(to) && weight > 0) ) {//if all 3 conditions false, throw exception
			throw new IllegalArgumentException();
		}

		// from ------> (.put) { to ---> weight }
		//take weightedGraph HashMap, put the parameter (to, weight) into it's Value
		weightedGraph.get(from).put(to, weight);   

	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if( !(containsVertex(from) && containsVertex(to)) ) { //if both vertices from and to are not in weighted graph
			throw new IllegalArgumentException();
		}
		
		//get value from weightedGraph(HashMap). From HashMap get the value (weight)
		return weightedGraph.get(from).get(to); 
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the          (1)
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will        (2)
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>     notifyVisit(V vertexBeingVisited);
	 * 
	 * <P>After the "end" vertex has been visited, this method will          (3)
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		for(GraphAlgorithmObserver<V> x: observerList) {//                   (1)
			x.notifyBFSHasBegun();
		}

		//Beginning of Breadth-First Search
		HashSet<V> visitedSet = new HashSet<>();
		LinkedList<V> queue = new LinkedList<>();   // **note: add to back and remove from front
		Set<V> adjacentVertices;                    // used to store each successor of nextElem
		boolean endVerticeVisited = false;          // used to track if we had visited the "end" element passed in


		queue.addLast(start);                       // add 1st vertex to queue

		while( !queue.isEmpty() && !endVerticeVisited ) {// while queue isn't empty & "end" has not been visited yet

			V nextElem = queue.removeFirst();              // removes nextElement from front of queue

			if(nextElem.equals(end)) {
				endVerticeVisited = true;                  //we've reached the end vertex
			}

			if( !(visitedSet.contains(nextElem)) ) {       // if queue doesn't contain the nextElement from the front
				
				//VISIT element
				for(GraphAlgorithmObserver<V> x: observerList) {//            (2)	
					x.notifyVisit(nextElem);
				}


				visitedSet.add(nextElem);                    // add nextElement to the visitedSet

				adjacentVertices = weightedGraph.get(nextElem).keySet(); 
				// all successors of nextElem
				for(V successor: adjacentVertices) {         // for each successor of nextElement
					if( !queue.contains(successor) ){       
						queue.addLast(successor);;           // if queue does not have the successor yet add to queue
					}
				}

			} 


		}

		for(GraphAlgorithmObserver<V> x: observerList) {//                   (3)
			x.notifySearchIsOver();
		}


	}

	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		//Beginning of Depth-First Search
		HashSet<V> visitedSet = new HashSet<>();
		LinkedList<V> stack = new LinkedList<>();   						// **note: add to front and remove from front
		Set<V> adjacentVertices;                    						// used to store each successor of nextElem
		boolean endVerticeVisited = false;          						// used to track if we had visited the "end" element passed in


		stack.addFirst(start);                       						// add 1st vertex to Stack
		
		for(GraphAlgorithmObserver<V> x: observerList) {				//(1)
			x.notifyDFSHasBegun();
		}
		
		while( !stack.isEmpty() && !endVerticeVisited ) {					// while stack isn't empty & "end" has not been visited yet
			V nextElem = stack.removeFirst();              					// removes nextElement from front of queue


			if(nextElem.equals(end)) {
				endVerticeVisited = true;                  					//we've reached the end vertex
			}

			
			if( !(visitedSet.contains(nextElem)) ) {       					// if stack doesn't contain the nextElement from the front
				System.out.println(nextElem);
				//VISIT element
				for(GraphAlgorithmObserver<V> x: observerList) {		//(2)	
					x.notifyVisit(nextElem);
				}

				visitedSet.add(nextElem);             						// add nextElement to the visitedSet

				adjacentVertices = weightedGraph.get(nextElem).keySet(); 

				for(V successor: adjacentVertices) {  						// for each successor of nextElement
					if( !visitedSet.contains(successor) ){       
						stack.addFirst(successor);             				// if stack does not have the successor yet add to queue
					}
				}

			} 

		}


		for(GraphAlgorithmObserver<V> x: observerList) {				//(3)
			x.notifySearchIsOver();
		}


	}

	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through           (1)
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this         
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 * 
	 * HashMap<V,Object[]> table:
	 * 
	 * A ---> [lowestCost|Predecessor]
	 * B ---> ...
	 * C ---> [infinity|tbd]
	 * D ---> ...
	 * E ---> [2|H]
	 * F ---> ...
	 * G ---> etc.
	 * H --->
	 * I --->
	 * J --->
	 * K --->
	 * 
	 */
	public void DoDijsktra(V start, V end) {
		for(GraphAlgorithmObserver<V> x: observerList) {//                   (1)
			x.notifyDijkstraHasBegun();
		}
		
		
		

		// INITIALIZING THE TABLE AND MAKING IT EMPTY----------------------
		HashMap<V,Object[]> table = new HashMap<>();  							// A----> emptyArray
		Object[] emptyArr; 														//emptyArray [infinity|tbd]
		
		Set<V> unfinishedVertices = new HashSet(weightedGraph.keySet()); 		//initialize the Dijsktra table with emptyArray ex. C ---> [infinity|tbd]
			
		
		for(V v: unfinishedVertices) {
			emptyArr = new Object[2]; 											//no privacy leak
			emptyArr[0] = INFINITY;  											//initializes with ex C ---> [infinity|tbd]
			emptyArr[1] = null;
			table.put(v, emptyArr); 											//put all unfinished vertices into the Dijsktra's table
		}
		// ----------------------------------------------------------------


		
		//BEGINNING OF DIJSKTRA ALGORITHM*********************
		Set<V> finishedVertices = new HashSet<>();

		table.put(start, new Object[] {0, start});  										//add start vertex

		Set<V> adjacentVertices;
		V predecessor = start; 																//for which one we're looking at
		int minCost = 0;
		
		while(unfinishedVertices.size() != 0) {  											//while we have not explored every vertice
			adjacentVertices = adjacentVerticesNotVisited(finishedVertices, predecessor); 	//adjacent to H is [A,B,I]

			if(adjacentVertices.size() != 0) {
				int pathCost;
				for(V k: adjacentVertices) {
					//go to dijsktras table. change lowestCost to the 
					//(adjacent vertex's weight) + (predecessor's lowest cost) ex. 0 + 14
					pathCost = weightedGraph.get(predecessor).get(k) + (int)table.get(predecessor)[0];
					if(pathCost < (int)table.get(k)[0]) {
						table.get(k)[0] = pathCost;  
						table.get(k)[1] = predecessor; //set adjacent vertex's predecessor
					}
				}

			}
			//lowest cost for adjacent vertices
			unfinishedVertices.remove(predecessor);
			finishedVertices.add(predecessor);

			for(GraphAlgorithmObserver<V> x: observerList) {//               (2)	
				x.notifyDijkstraVertexFinished(predecessor, minCost);
			}

			predecessor = minCostPred(table, unfinishedVertices); 			//determine the lowest unfinished vertex in the table
			minCost = minCost(table, unfinishedVertices); 					// the cost of that vertex
		}

		//shortest path from start to end
		LinkedList<V> path = new LinkedList<>();
		//iterates thru every V inside path
		//start from "end" and stop at "start". Going backwards
		for(V v = end; v != start; v = (V) table.get(v)[1]) { 
			path.addFirst(v);
		}
		path.addFirst(start);

		for(GraphAlgorithmObserver<V> x: observerList) {//                   (3)
			x.notifyDijkstraIsOver(path);
		}

	}

	/**
	 * Used in diktras method for determining which vertices around the one being visited have not been completed yet
	 * 
	 * @param Set<V> finishedVertices, V predecessor from the dikstras method
	 * @return Set<v> of vertices
	 * **/
	private Set<V> adjacentVerticesNotVisited(Set<V> finishedVertices, V predecessor){
		Set<V> adjacentVertices = weightedGraph.get(predecessor).keySet();
		adjacentVertices.removeAll(finishedVertices);
		return adjacentVertices;
	}


	/**
	 * Used to get the minimum cost vertex in the table after calculing the paths. 
	 * 
	 * @param HashMap<V,Object[]> table, Set<V> unfinishedVertices
	 * @return V vertex thats the min cost
	 * **/
	private V minCostPred(HashMap<V,Object[]> table, Set<V> unfinishedVertices) {
		int minCost = INFINITY;
		int cost;
		V minVertex = null;
		
		//look at every vertex in the table and examines their costs too see which one is the lowest
		for(V k: unfinishedVertices) {
			cost = (int)table.get(k)[0]; 	//examines the cost
			if(cost == (INFINITY)) { 		//skips right over it if its unvisited
				continue;
			}

			if(cost < minCost) { 			//for setting the new minCost
				minCost = cost;
				minVertex = k;
			}
		}
		
		//returns the actual V item

		return minVertex;
	}

	/**
	 * Similar to the previos method above this one just returns the minCost as a integer
	 * 
	 * @param HashMap<V,Object[]> table, Set<V> unfinishedVertices
	 * @return V vertex thats the min cost
	 * **/
	private int minCost(HashMap<V,Object[]> table, Set<V> unfinishedVertices) {
		//look at every vertex in the table and examines their costs too see which one is the lowest
		int minCost = INFINITY;
		int cost;
		for(V k: unfinishedVertices) { 
			cost = (int)table.get(k)[0];
			//don't need to examine if it is still infinity 
			if(cost == (INFINITY)) {
				continue;
			}

			if(cost < minCost) { //setting the new lowest cost
				minCost = cost;
			}
		}

		return minCost;
	}





}
     