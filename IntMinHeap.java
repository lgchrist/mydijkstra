package mydijkstra;

import digraph.Node;

//what should be in a minHeap? it will store nodes, nodes will have an id, data, and a weight
public class IntMinHeap
{
	private int[] minHeap;
	private int position; //next empty spot in array

	public IntMinHeap(int size)
	{
		//Initialize size of array
		minHeap = new int[size + 1];
		position = 1;
	}

	public int getRoot()
	{
		return minHeap[1];
	}

	//inserting an array of edges
	public void insert(int element)
	{
		//check the size first, resize if needed
		if(position == minHeap.length - 1)
		{
			expandCapacity();
		}
		//position to be inserted = 1
		if(position == 1)
		{
			//insert
			minHeap[position] = element;
			position++;
		}
		else
		{
			minHeap[position] = element;
			position++;
			percolateUp();

		}
	}

	//use when inserting & more than one thing in array
	public void percolateUp()
	{
		int pos = position - 1;
		//do this while the distance is less than its parent and not the root

		while(pos > 0 && minHeap[pos/2] > minHeap[pos])
		{
			//store the last thing in the array
			int swap = minHeap[pos];
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
		int[] larger = new int[minHeap.length * 2];

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
	public int extractMin()
	{
		//store root 
		int min = minHeap[1];
		//put last item in array in front
		if(minHeap.length != 0)
		{
			minHeap[1] = minHeap[position-1];
			//set last position in array to 0
			minHeap[position - 1] = 0;
			position--;
			//percolate down starting at 1
			percolateDown(1);
		}

		return min;
	}

	//replace node with a smaller node 
	public void decreaseKey(int index, int newVal)
	{
		//set the new value in the array
		System.out.println("Index: " + index);
		System.out.println("newVal: " + newVal);
		
		
		//so while still traversing the array and the new value is less than its parent 
		//if less than parent, needs to move up
		while(minHeap.length > 0 && newVal < minHeap[index/2]) 
		{
			//swapping for parent if the element to be added is less than parent
			minHeap[index] = minHeap[index/2];
			index /= 2;
			
			if(index == 1)
			{
				minHeap[index] = newVal;
			}
		}
		minHeap[index] = newVal;
	}

	//percolate down in the heap, 
	//emptySpot is the index at which the percolate begins 
	public void percolateDown(int emptySpot)
	{
		//store the dist where the percolate begins, (usually index 1 root of the array)
		int temp = minHeap[emptySpot];

		int smallest = emptySpot; 

		//checks left child 
		if(2 * emptySpot < position && minHeap[smallest] > minHeap[2 * emptySpot])
		{
			smallest = 2 * emptySpot;
		}

		//checks right child
		if(2 * emptySpot + 1 < position && minHeap[smallest] > minHeap[2 * emptySpot + 1])
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
		int temp = minHeap[a];
		minHeap[a] = minHeap[b];
		minHeap[b] = temp;
	}

	@Override
	public String toString()
	{
		String result = "Min heap " ;

		for(int i = 1; i < minHeap.length; i++)
		{
			result += minHeap[i] + " ";
		}

		return result;
	}

}

