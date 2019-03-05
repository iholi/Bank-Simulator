package servicequeue;

/**abstract
 * 
 * @author Isabel Ho and Tram
 * CustomerGenerator Class help the action of the customer (start, stop, resume)
 */

public abstract class CustomerGenerator implements Runnable
{
	private Thread myCustomerThread;
	private boolean mySuspended;
	private int myMaxTimeBWCustomers;
	private int myNumberOfCustomers;
	private int custom=0;
	private long myStartTime;
	ServiceQueueManager myServiceQueueManager;
	ServiceQueue serviceQueue;
	private long myServedTime;
	private int myLine;
	
	
	public CustomerGenerator(int maxTimeBetweenCustomers, int numberOfCustomers, ServiceQueueManager serviceQueueManager)
	{
		myCustomerThread = new Thread(this);
		myMaxTimeBWCustomers= maxTimeBetweenCustomers;
		myNumberOfCustomers = numberOfCustomers;
		myServiceQueueManager = serviceQueueManager;
		serviceQueue = new ServiceQueue();
		myStartTime = System.currentTimeMillis();
	}

	public abstract int generateTimeBetweenCustomers();
	
	public void generateCustomer() throws InterruptedException
	{
		String message;
		
		while (custom < myNumberOfCustomers)
		{
			this.waitWhileSuspended();
			Customer customer = new Customer();
			custom++;	
			
		try
		{
			Thread.sleep(this.generateTimeBetweenCustomers());
		}
		catch (InterruptedException e)
		{
            message = e.getMessage();
            System.err.println(message);
		}
		int shortest;
		shortest = myServiceQueueManager.determineShortestQueue();
		myLine = shortest;
		System.out.println(" my time is "+System.currentTimeMillis());
		
		myServiceQueueManager.customerEnque(shortest, customer);
		System.out.print(" Customer"+ myServiceQueueManager.getServiceQueue(shortest).getSize() + "in queue "+ shortest);
		}
	}
	
	public void run()
	{
		try
    	{
    		synchronized(this)
    		{
    			//myStartTime = System.currentTimeMillis();
    			System.out.print("myStart time is " + System.currentTimeMillis());
    			this.generateCustomer();
    		}
    	}
    	catch (InterruptedException e)
    	{
    		System.out.println("Thread suspended.");
    	}
	}
	
	/**
	 * Start thread
	 */
	public void start()
	{
		try
		{
			myCustomerThread.start();
			System.out.print("Generating Customers ");
		}
        catch(IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
	}
	
    private void waitWhileSuspended() throws InterruptedException
    {
	    while (mySuspended)
	    {
	    	this.wait();
	    }
    }
    
    public void suspend()
    {
    	mySuspended = true;
    }
    
    public synchronized void resume()
    {
	    mySuspended = false;
	    this.notify();
    }
    
	public void stop()
	{
		mySuspended = false;
	}

	/**
	 * Getters and Setters
	 * @return myMaxTimeBWCustomers
	 */
	public int getMyMaxTimeBWCustomers() 
	{
		return myMaxTimeBWCustomers;
	}
	
	public int getMaxCustomers()
	{
		return myNumberOfCustomers;
	}

	public void setMyMaxTimeBWCustomers(int myMaxTimeBWCustomers) 
	{
		this.myMaxTimeBWCustomers = myMaxTimeBWCustomers;
	}
	
	//queNum is the number of the queue
	public ServiceQueue getServiceQueue(int queNum)
	{
		return myServiceQueueManager.getServiceQueue(queNum);
		
	}
	public long getStartTime()
	{
		return myStartTime;
	}
	
	public int getMyLine()
	{
		return myLine;
	}
	
}
