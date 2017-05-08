package mydijkstra;

import digraph.Node;

//what should be in a minHeap? 
//it will store nodes, nodes will have an id, data, and a weight
//this will sort according to the id!!
public class NodeMinHeap<T>
{
	private Node<T>[] minHeap;
	private int position; //next empty spot in array
	
	public NodeMinHeap(int size)
	{
		//Initialize size of array
		minHeap = new Node[size + 1];
		position = 1;
	}

	public Node<T> getRoot()
	{
		return minHeap[1];
	}
	

	public void insert(Node<T> element)
	{
		//check the size of array first, resize if needed
		if(position == minHeap.length)
		{
			expandCapacity();
		}
		
		//if the array is empty
		if(position == 1)
		{
			minHeap[position] = element;
			position++;
		}
		
		//insert at the last position in array
		else
		{
			minHeap[position] = element;
			position++;
			//do I need this??
			percolateUp();
		}
	}

	//use when inserting & there's more than one thing in array, 
	//will percolate the smallest distance up to the top of the array 
	public void percolateUp()
	{
		//this gets the last element in the array
		int pos = position - 1;
		
		//do this while the length of path is less than its parent and not the root
		//while(pos > 0 && (minHeap[pos/2].getLengthOfPath() > minHeap[pos].getLengthOfPath()))
		
		while(pos > 1 && (minHeap[pos/2].compareTo(minHeap[pos]) >= 0))
		{
			//store the last thing in the array
			Node swap = minHeap[pos];
			//swap the parent and child
			//this is the child
			minHeap[pos] = minHeap[pos/2];
			//this is the parent
			minHeap[pos/2] = swap;
			//now the position to check is the parent, keep checking up the tree
			pos = pos/2;
		}
	}
	
	public void expandCapacity()
	{
		Node<T>[] larger = new Node[minHeap.length * 2];

		//repopulate the array
		for(int i = 0; i < position; i++)
		{
			larger[i] = minHeap[i];
		}

		minHeap = larger;
	}

	//remove the root 
	//keep the shape
	//move the last leaf (last number in array to the front of the array)
	//then percolate down to put the right number in right spot
	//this is used for the final distance array
	public Node extractMin()
	{

		//store root 
		Node min = minHeap[1];
		//put last item in array in front
		minHeap[1] = minHeap[position-1];
		//set last position in array to null
		minHeap[position - 1] = null;
		position--;
		percolateDown(1);
		
		return min;
	}

	//replace node with a smaller node 
	//is this just updating the node's distance and then percolating up if needed?
	//not making a new node... 
	public void decreaseKey(int index, Node newVal)
	{
		//set the new value in the array
		minHeap[index] = newVal;

		//percolate up if needed
		//so while still traversing the array and the new value is less than its parent 
		//if less than parent, needs to move up
		while(minHeap.length > 0 && newVal.getLengthOfPath()  < minHeap[index/2].getLengthOfPath()) 
		{
			//swapping for parent if the element to be added is less than parent
			minHeap[index] = minHeap[index/2];
			index /= 2;
		}
	}

	//percolate down in the heap, 
	//emptySpot is the index at which the percolate begins 
	public void percolateDown(int emptySpot)
	{
		//store the dist where the percolate begins, (usually index 1 root of the array)
		int temp = minHeap[emptySpot].getLengthOfPath();
		
		int smallest = emptySpot; 
		
		//checks left child 
		if(2 * emptySpot < position && minHeap[smallest].getLengthOfPath() > minHeap[2 * emptySpot].getLengthOfPath())
		{
			smallest = 2 * emptySpot;
		}
		
		//checks right child
		if(2 * emptySpot + 1 < position && minHeap[smallest].getLengthOfPath() > minHeap[2 * emptySpot + 1].getLengthOfPath())
		{
			smallest = 2 * emptySpot + 1;
		}
		
		if(smallest != emptySpot)
		{
			swap(emptySpot, smallest);
			percolateDown(smallest);
		}
	}
	
	public boolean isEmpty()
	{
		return (position == 0);
	}
	
	//swapping nodes at these 2 given indexes
	public void swap(int a, int b)
	{
		Node temp = minHeap[a];
		minHeap[a] = minHeap[b];
		minHeap[b] = temp;
	}
	
	
	@Override
	public String toString()
	{
		String result = "Min heap: " ;
		
		for(int i = 1; i < minHeap.length; i++)
		{
			result += minHeap[i].getLengthOfPath() + " ";
		}
		
		return result;
	}

}
