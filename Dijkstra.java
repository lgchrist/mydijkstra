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

		//set size of minHeap with size of digraph
		minHeap = new NodeMinHeap(DG.getVertices().size());

		//set size of visited nodes with size of digraph
		visited = new Node[DG.getVertices().size()];

		//set source node path length to zero, all rest are already set to infinity
		source.setLengthOfPath(0);
		source.setKnown(true);

		//insert the nodes into the minHeap from DG (digraph)
		//assuming digraph node id's start at 0
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			minHeap.insert(DG.getNode(i));
		}

		currentNode = minHeap.extractMin();

		//stores all outedges from the currentNode
		outEdgeNodes = null;
	}

	//
	//find distances for all vertices being the source node
	//
	public void excecuteAll()
	{
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			Dijkstra dijkstra2 = new Dijkstra(DG, DG.getNode(i));
			dijkstra2.execute();
			System.out.println(dijkstra2.toString());
			dijkstra2.resetLengthOfPath();
		}
	}

	public void execute()
	{	
		System.out.println("---------------------------START------------------------------");
		System.out.println("---------------------------Start Node: " + currentNode.getId() + "---------------------------");

		int count = 0;

		//do this while there are still nodes in minHeap
		while(!minHeap.isEmpty())
		{	
			visited[count] = currentNode;
			count++;
			System.out.println("\nEVALUATING NODE: " + currentNode.getId());

			relax();

			//get the next node to evaluate
			currentNode = minHeap.extractMin();
		}
	}

	//relax the nodes (reveal the adjacent nodes & update the path length & set predecessor)
	public void relax()
	{
		System.out.println("-------RELAX NODE-------\n");
		if(DG.getOutEdgeNodes(currentNode.getId()).size() == 0)
		{
			System.out.println("No out edges.");

		}
		else
		{
			outEdgeNodes = DG.getOutEdgeNodes(currentNode.getId());
		}

		for(int i = 0; i < currentNode.outEdges.size(); i++)
		{
			//gets the targetNodes from the source(should be the min)
			outEdgeNodes = DG.getOutEdgeNodes(currentNode.getId());

			//check out edge weights!!
			System.out.println("\nCurrent Node length of path: " + currentNode.getLengthOfPath() + 
					"\nOut Edge: " + currentNode.outEdges.get(i) + ", weight: " + currentNode.getWght(i) + 
					"\nLength of path from " + source + " to " + outEdgeNodes.get(i) + ": " + outEdgeNodes.get(i).getLengthOfPath() + "\n");

			if(this.addLengthOfPath(i) < outEdgeNodes.get(i).getLengthOfPath() && this.addLengthOfPath(i) > 0)
			{
				System.out.println("relax() set length of path: ");

				outEdgeNodes.get(i).setLengthOfPath(this.addLengthOfPath(i));
				System.out.println("Update length of path to Node " + currentNode.outEdges.get(i)  + ": "+ (currentNode.getWght(i) + currentNode.getLengthOfPath()) + "\n");

				//The index of the minHeap... 
				minHeap.decreaseKey(minHeap.getIndex(outEdgeNodes.get(i).getId()));

				//sets the predecessor for the target node
				outEdgeNodes.get(i).setPredecessor(currentNode);
			}
		}
	}

	public void resetLengthOfPath()
	{
		for(int i = 0; i < DG.getVertices().size(); i++)
		{
			DG.getNode(i).setLengthOfPath(Integer.MAX_VALUE);
		}
	}

	private int addLengthOfPath(int index)
	{
		return currentNode.getWght(index) + currentNode.getLengthOfPath();
	}

	public String toString()
	{
		String result = "";

		//result += "MinHeap: " + minHeap.toString();

		//result += "\nOut Edge Nodes: " + outEdgeNodes.toString() + "\n";

		for(int i = 0; i < visited.length; i++)
		{
			result += "Node " + visited[i] + " with distance: " + visited[i].getLengthOfPath() +  " to the source node " + 
					"-----Predecessor: " + visited[i].getPredecessor() + "\n";
		}

		//prints the path of dijkstra 
		return result;
	}

}


