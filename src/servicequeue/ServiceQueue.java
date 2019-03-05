package servicequeue;

/**
 * @author Isabel and Tram
 */
public class ServiceQueue extends Queue
{
	private int myNumberCustomersServedSoFar;
	private int myNumberCustomersInLine;
	private long myTotalWaitTime; 
	private long myTotalServiceTime;
	private long myTotalIdleTime;
	private long zero=0;
	private long myTotalElapsedTime;
	private long myEntryTime;
	private long myServedTime;
	private long myWaitTime;
	private long myServiceTime;
	private long myAverageWaitTime;
	private long myAverageServiceTime;
	
	public ServiceQueue()
	{
		
		myNumberCustomersServedSoFar=0;
		myNumberCustomersInLine=0;
		myTotalWaitTime=zero;
		myTotalServiceTime=zero;
		myTotalIdleTime=zero;
		myTotalElapsedTime = zero;
	
	}
	
	public void addToElapsedTime (long elapsedTime)
	{
		myTotalElapsedTime = myTotalElapsedTime + elapsedTime;
	}

	public void addToIdleTime (long idleTime)
	{
		myTotalIdleTime = myTotalIdleTime + idleTime;
	}
	
	public void addToWaitTime (long waitTime)
	{
		myTotalWaitTime = myTotalWaitTime + waitTime;
	}
	
	public void addToServiceTime (long serviceTime)
	{
		myTotalServiceTime = myTotalServiceTime + serviceTime;
	}
	
	public void insertCustomer( Customer customer)
	{		
		myEntryTime = System.currentTimeMillis();
		this.enqueue(customer);
		myNumberCustomersInLine=this.getSize(); 	
	}
	
	public void serveCustomer()
	{
		myServedTime = System.currentTimeMillis();
		myNumberCustomersInLine = this.getSize();
		
		if ( this ==null || this.empty())
		{
			this.addToIdleTime(20);
		}
		else 
		{
			myNumberCustomersServedSoFar++;
        	myNumberCustomersInLine--;
        	
        	if (this.peek() ==null)
        	{
        		System.out.println("null");
        	}
        	else
        	{ 
		    	((Customer) this.peek()).setMyServiceTime(System.currentTimeMillis() - ((Customer) this.peek()).getMyEntryTime());
		  	    ((Customer) this.peek()).setMyWaitTime(((Customer) this.peek()).getMyServiceTime() - ((Customer) this.peek()).getMyEntryTime() +278000000);
		  	   	//myWaitTime = myServedTime - myEntryTime;
        		this.addToServiceTime(((Customer) this.peek()).getMyServiceTime());
		  	    this.addToWaitTime(((Customer) this.peek()).getMyWaitTime()); 
//		  	   	this.addToServiceTime(myServiceTime);
//		  	   	this.addToWaitTime(myWaitTime);
 		  	    
		  	    
		  	    System.out.println("My total wait time "+myWaitTime);
		  	    System.out.println("My total service time "+ myServiceTime/1000);
		  	    this.dequeue();       		
        	}
		}
	}

	
	public long averageWaitTime()
	{
		this.getMyTotalWaitTime();
		if (myNumberCustomersServedSoFar!=0){
			return myTotalWaitTime / myNumberCustomersServedSoFar;
		}
		return 0;
	}
	
	public long averageServiceTime()
	{
		this.getMyTotalServiceTime();
		if (myNumberCustomersServedSoFar!=0){
			return myTotalServiceTime / myNumberCustomersServedSoFar;
		}
		return 0;
	}
	
	public long averageIdleTime ()
	{
		this.getMyTotalIdleTime();
		if (myNumberCustomersServedSoFar!=0){
			return myTotalIdleTime / myNumberCustomersServedSoFar;
		}
		return 0;
	}

	/**
	 * Getters and Setters
	 * @return
	 */
	public int getMyNumberCustomersServedSoFar() 
	{
		return myNumberCustomersServedSoFar;
	}

	public void setMyNumberCustomersServedSoFar(int numberCustomersServedSoFar) 
	{
		myNumberCustomersServedSoFar = numberCustomersServedSoFar;
	}

	public int getMyNumberCustomersInLine() 
	{
		myNumberCustomersInLine = this.getSize();
		return myNumberCustomersInLine;
	}

	public void setMyNumberCustomersInLine(int myNumberCustomersInLine) 
	{
		this.myNumberCustomersInLine = myNumberCustomersInLine;
	}

	public long getMyTotalWaitTime() 
	{

		return myTotalWaitTime;
	}

	public void setMyTotalWaitTime(long myTotalWaitTime) 
	{
		this.myTotalWaitTime = myTotalWaitTime;
	}

	public long getMyTotalServiceTime() 
	{
		return myTotalServiceTime;
	}

	public void setMyTotalServiceTime(long myTotalServiceTime) 
	{
		this.myTotalServiceTime = myTotalServiceTime;
	}

	public long getMyTotalIdleTime() 
	{
		return myTotalIdleTime;
	}

	public void setMyTotalIdleTime(long myTotalIdleTime) 
	{
		this.myTotalIdleTime = myTotalIdleTime;
	}
	
	public long getMyEntryTime()
	{
		return myEntryTime;
	}
	public long getMyServedTime()
	{
		return myServedTime;
	}
	
	public void setmyNumberOfCustomerInLine(int number)
	{
		myNumberCustomersInLine = number;
	}
	
	public void setMyServiceTime(long time)
	{
		myServiceTime = time;
	}

}