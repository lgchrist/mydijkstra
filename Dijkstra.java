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
	private Node currentNode;

	//there will be a new temp each round, this will be the node with the smallest path 
	//this stores all the target nodes
	ArrayList<Node<Node<T>>> outEdgeNodes;

	//constructor
	public Dijkstra(DiGraph dg, Node s)
	{
		DG = dg;
		source = s;

		//set source node path length to zero, all rest are already set to infinity
		source.setLengthOfPath(0);
		source.setKnown(true);

		//set size of minHeap with size of digraph
		minHeap = new NodeMinHeap(DG.getVertices().size());

		//set size of visited nodes with size of digraph
		visited = new Node[DG.getVertices().size()];

		//insert the nodes into the minHeap from DG (digraph)
		//assuming digraph node id's start at 0
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			minHeap.insert(DG.getNode(i));
		}

		currentNode = minHeap.extractMin();

		//stores all outedges from the currentNode
		outEdgeNodes = null;
		//outEdgeNodes = DG.getOutEdgeNodes(currentNode.getId());
	}
	
	//find distances for all vertices being the source node
	//
	public int[] excecuteAll()
	{
		int[] allNodeDist = null;
		
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			
		}
		
		return allNodeDist;
	}

	public void execute()
	{	
		System.out.println("---------------------------START------------------------------");
		int count = 0;
		
		//do this while there are still nodes in minHeap
		while(!minHeap.isEmpty())
		{	
			visited[count] = currentNode;
			System.out.println("Print Visited Array: " + visited[count].getId() + " \ncount of array: " + count );
			
			count++;
			System.out.println("NODE TO EXECUTE: " + currentNode.getId());

			relax();
			
			//get the next node to evaluate
			currentNode = minHeap.extractMin();
			
			//System.out.println("MinHeap: " + minHeap.toString());

			if(!minHeap.isEmpty())
			{
				System.out.println("execute() After extract min execute node: " + currentNode.getId());
			}
		}
	}

	//relax the nodes (reveal the adjacent nodes & update the path length & set predecessor)
	public void relax()
	{
		System.out.println("--------------------------RELAX NODE-----------------------------");
		for(int i = 0; i < currentNode.outEdges.size(); i++)
		{
			//gets the targetNodes from the source(should be the min)
			outEdgeNodes = DG.getOutEdgeNodes(currentNode.getId());

			//check out edge weights!! it was removed: .getWght(currentNode.getId()
			System.out.println("\nCurrent Node: " + currentNode.getId() + 
					"\nCurrent Node length of path: " + currentNode.getLengthOfPath() + 
					"\nOut Edge: " + currentNode.outEdges.get(i) + ", weight: " + currentNode.getWght(i) + 
					"\nLength of path from " + source + " to " + outEdgeNodes.get(i) + ": " + outEdgeNodes.get(i).getLengthOfPath() + "\n");
			
			
			if((currentNode.getWght(i) + currentNode.getLengthOfPath()) < outEdgeNodes.get(i).getLengthOfPath() && 
					(currentNode.getWght(i) + currentNode.getLengthOfPath()) > 0)
			{
				System.out.println("\nrelax() set length of path: ");
				//the sourceNode is an int NOT a Node... 
				//how can I get the length of path for the node associated with the edge
				outEdgeNodes.get(i).setLengthOfPath((currentNode.getWght(i) + currentNode.getLengthOfPath()));
				System.out.println("Update length of path to Node " + currentNode.outEdges.get(i)  + ": "+ (currentNode.getWght(i) + currentNode.getLengthOfPath()) + "\n");
				
				//Need to percolate up!! or decrease key!
				minHeap.percolateUp();
				
				//sets the predecessor for the target node
				outEdgeNodes.get(i).setPredecessor(currentNode);
			}
		}
		
		System.out.println("----------------------------END RELAX--------------------------");
	}

	public String toString()
	{
		String result = "";

		//result += "MinHeap: " + minHeap.toString();

		//result += "\nOut Edge Nodes: " + outEdgeNodes.toString() + "\n";

		for(int i = 0; i < visited.length; i++)
		{
			result += "Node " + visited[i] + " with distance: " + visited[i].getLengthOfPath() +  " to the source node " + 
		"\nPredecessor: " + visited[i].getPredecessor() + "\n";
		}

		//prints the path of dijkstra 
		return result;
	}

}


