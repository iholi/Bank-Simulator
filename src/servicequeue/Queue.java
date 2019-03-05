package servicequeue;
import java.util.LinkedList;

/**
 * Implement a Queue as a wrapper around the double ended Linked
 * List class provided by Oracle.  This Linked List is efficient
 * at both ends, so it works well for a queue.
 * 
 * Recall that:
 * 
 * offer() is same as enqueue()
 * poll() is same as dequeue()
 * 
 *
 * @param <T>
 */

public class Queue<T>
{
	private LinkedList<T> myData;
	
	public Queue()
	{
		myData = new LinkedList<T>();
	}
	
	public boolean empty()
	{
		return myData.isEmpty();
	}
	
	public void enqueue(T o)
	{
		myData.addLast(o);
	}
	
	public T peek()
	{
		return myData.peekFirst();
	}
	
	public T dequeue()
	{
		if (myData.isEmpty())
		{
			return null;
		}
		return myData.removeFirst();
	}
	
	public int getSize() 
	{
		return myData.size();
	}
	
	public LinkedList<T> getQueue()
	{
		return myData;
	}

}
