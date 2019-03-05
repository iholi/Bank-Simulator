package servicequeue;


import java.util.ArrayList;
import java.util.Arrays;
/**
 * 
 * @author Isabel and Tram
 * Real Service Queue Manager
 * 
 * This is the Service Queue Manager class to determine these logistics
 * below for each queue:
 * 
 * ** total Customers served so far
 * ** total Wait Time 
 * ** total Service Time
 * ** Shortest Queue
 * ** average Wait Time
 * ** average Service Time
 * ** average Idle Time
 * 
 */
import java.util.Random;
import java.util.Vector;

public class ServiceQueueManager 
{
	private static final int MAX_NUMBER_OF_QUEUES = 5;
	private int myNumberOfServiceQueues=5;
	private ServiceQueue[] myServiceQueues;
	private long[] myTotalWaitTime;
	private long[] myTotalServiceTime; //Vector<Long>
	private long[] myTotalIdleTime;
	private long[] myAverageWaitTime;
	private long[] myAverageIdleTime;
	private long[] myAverageServiceTime;
	private long[] myPresentTime;
	private long[] myStartTime;
	private int[] myTotalServed;
	private int[] myCustomersInLine;
	private int[] myCustomersInLine2;
	private Random myRandom;

	
	
	/**
	 * create an array of Service Queue
	 */
	public ServiceQueueManager()
	{		//myRandom = new Random(5);

	//System.out.println(myRandom);
		myServiceQueues = new ServiceQueue[myNumberOfServiceQueues];
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myServiceQueues[i] = new ServiceQueue();
	
		}
	}

	//whenever cashier serve a customer, they pull the customer out of the queue (dequeue)
	public void cashierServeCustomer(int queNum)
	{
		myServiceQueues[queNum].serveCustomer();
		//System.out.println(System.currentTimeMillis());
	}
	
	public void customerEnque(int queNum, Customer c)
	{
		myServiceQueues[queNum].enqueue(c);
		//System.out.println(System.currentTimeMillis());
	}
	/**
	 * create an array of Total Customers served so far for each queue
	 * @param queueNum - the number of the queue
	 * @return the total customers served so far in queueNum (position queueNum-1)
	 */
	public int totalServedSoFar(int queueNum)
	{
		
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myTotalServed[i] = myServiceQueues[i].getMyNumberCustomersServedSoFar();
		}
		return myTotalServed[queueNum];
	}
	
	/**
	 * create an array of Total Wait time for each queue
	 * @param queueNum - the number of the queue
	 * @return the total wait time in queueNum (position queueNum-1)
	 */
	public long totalWaitTime(int queueNum)
	{
		
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myTotalWaitTime[i] = myServiceQueues[i].getMyTotalWaitTime();
		}
		return myTotalWaitTime[queueNum];
	}
	
	/**
	 * create an array of Total Service Time for each queue
	 * @param queueNum - the number of the queue
	 * @return the total Service Time in queueNum (position queueNum-1)
	 */
	public long totalServiceTime(int queueNum)
	{
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myTotalServiceTime[i] = myServiceQueues[i].getMyTotalServiceTime();
		}
		return myTotalServiceTime[queueNum];
	}
	
	/**
	 * create an array of Total Idle Time for each queue
	 * @param queueNum - the number of the queue
	 * @return the total Idle Time in queueNum (position queueNum-1)
	 */
	public long totalIdleTime(int queueNum)
	{
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myTotalIdleTime[i] = myServiceQueues[i].getMyTotalIdleTime();
		}
		return myTotalIdleTime[queueNum];
	}
	
	/**
	 * create an array of average wait time for each queue
	 * @param queueNum - the number of the queue
	 * @return the average wait time in queueNum (position queueNum-1)
	 */
	
	public long averageWaitTime(int queueNum)
	{
		
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myAverageWaitTime[i] = myServiceQueues[i].averageWaitTime();
		}
		return myAverageWaitTime[queueNum];
		
	}
	
	/**
	 * create an array of average service Time for each queue
	 * @param queueNum - the number of the queue
	 * @return the average service time in queueNum (position queueNum-1)
	 */
	
	public long averageServiceTime(int queueNum)
	{
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myAverageServiceTime[i] = myServiceQueues[i].averageServiceTime();
		}
		return myAverageServiceTime[queueNum];
		
	}
	
	/**
	 * create an array of average idle time for each queue
	 * @param queueNum - the number of the queue
	 * @return the average idle time in queueNum (position queueNum-1)
	 */
	public long averageIdleTime(int queueNum)
	{
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myAverageIdleTime[i] = myServiceQueues[i].averageIdleTime();
		}
		return myAverageIdleTime[queueNum];
	}
	
	
	/**
	 * 
	 * @param queueNum The Queue Number of the Customers
	 * @return number of customers in line
	 */
	public int getNumCustomers(int queueNum)
	{
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myCustomersInLine[i] = myServiceQueues[i].getMyNumberCustomersInLine();
		}
		return myCustomersInLine[queueNum];
		//return myRandom.nextInt(5) + 1;

	}
	
	//return the index + 1 of the queue
	public int determineShortestQueue()
	{
		int min = 10000000;
		for (int i =0; i<myNumberOfServiceQueues;i++)
		{
			myCustomersInLine[i] = myServiceQueues[i].getMyNumberCustomersInLine();
		}

		for (int i = 0; i < myNumberOfServiceQueues; i++)
		{
			if (min>myCustomersInLine[i])
			{
				min = myCustomersInLine[i];
				
			}
			
		}
		for (int i = 0; i < myNumberOfServiceQueues; i++)
		{
			if (min==myCustomersInLine[i])
			{
				return i;
				
			}
			
		}
				
		return 0;
	}
	
	//return the service at queNum
	public ServiceQueue getServiceQueue(int queNum)
	{
		System.out.print(myServiceQueues.length);
		return myServiceQueues[queNum];
		
	}
	
	public void setNumberOfServiceQueue(int num)
	{
		myNumberOfServiceQueues = num;
		myCustomersInLine = new int[myNumberOfServiceQueues];
		myAverageWaitTime = new long[myNumberOfServiceQueues];
		myTotalServed = new int[myNumberOfServiceQueues];
		myTotalWaitTime = new long[myNumberOfServiceQueues];
		myTotalServiceTime = new long[myNumberOfServiceQueues];
		myTotalIdleTime = new long[myNumberOfServiceQueues];
		myAverageServiceTime = new long[myNumberOfServiceQueues];
		myAverageIdleTime = new long[myNumberOfServiceQueues];
		myCustomersInLine = new int[myNumberOfServiceQueues];
		
	}
	
    public ServiceQueue[] getServiceQueues()
    {
    	return myServiceQueues;
    }
	
	public int getTotalCustomersServed()
	{
		int numOfCustomers=0;
		for (int i=0; i<myNumberOfServiceQueues; i++)
		{
			numOfCustomers = numOfCustomers + myTotalServed[i]; 
		}
		return numOfCustomers;
	}
	
}