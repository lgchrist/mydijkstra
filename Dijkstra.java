package mydijkstra;

import java.util.ArrayList;

import digraph.DiGraph;
import digraph.Edge;
import digraph.Node;

public class Dijkstra<T>
{
	private DiGraph<Node<T>> DG;
	private Node<T> source;

	//put all nodes in here to determine which node is the minimum, next in line to check
	private NodeMinHeap minHeap;
	//put the vistied nodes here, keeps track of path
	private Node visited[];

	//constructor
	public Dijkstra(DiGraph dg, Node s)
	{
		DG = dg;
		source = s;

		//set source node path length to zero, all rest are already set to infinity
		source.setLengthOfPath(0);
		source.setKnown(true);

		//fill minHeap with the nodes from DiGraph, with size of digraph
		minHeap = new NodeMinHeap(DG.getVertices().size());

		//insert the nodes into the minHeap from DG (digraph)
		//assuming digraph nodes start at 1
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			//.getNode(int id)
			minHeap.insert(DG.getNode(i));
		}

		//stores the visited nodes
		visited = new Node[DG.getVertices().size()];
	}

	public void execute()
	{	
		//do this while there are still nodes in minHeap
		while(!minHeap.isEmpty())
		{	
			//this gets the target nodes from the minHeap by using extractMin()
			//there will be a new temp each round, this will be the node with the smallest path 
			ArrayList<Node<Node<T>>> temp = DG.getTargetNodes(minHeap.extractMin().getId());

			//after find adjacent nodes put source node in the visited array
			
			//relax the nodes (reveal the adjacent nodes & update the path length & set predecessor)
			for(int i = 0; i < minHeap.extractMin().outEdges.size(); i++)
			{
				//gets the targetNodes from the source(should be the min)
				
				//sets the predecessor for the target node
				temp.get(i).setPredecessor(minHeap.extractMin());
				
				//this edge get(i)'s weight + the length of the path 
				//need to update the length of path for each adjacent node
				if(temp.get(i).getWeight(i) + minHeap.extractMin().getLengthOfPath() < 
						minHeap.extractMin().getLengthOfPath())
				{
					//the sourceNode is an int NOT a Node... 
					//how can I get the length of path for the node associated with the edge
					temp.get(i).setLengthOfPath((temp.get(i).getWeight(i) + minHeap.extractMin().getLengthOfPath()));
				}
				
				//at index i, insert the known node
				visited[i] = minHeap.extractMin();
			}
		}
	}

	public String toString()
	{
		String result = "";

		result += minHeap.toString();
		
//		for(int i = 0; i < visited.length; i++)
//		{
//			result += "Node " + visited[i] + " with distance "  + " to the source node\n";
//		}
		
		//prints the path of dijkstra 
		return result;
	}

}

















