package servicequeue;

import MVC.View;

/**
 * abstract
 * @author Isabel Ho and Tram
 * Cashier Class help the action of the cashier (start, stop, resume)
 */

public abstract class Cashier implements Runnable {

	private Thread myCashierThread;
	private boolean myCashierSuspended;
	private int myMaxTimeOfService;
	private int myNumberOfCustomersServed;
	ServiceQueue myQueue;
	CustomerGenerator myCustomers;
	ServiceQueueManager myQueueM;
	private long myServiceTime;
	private View myView;
	
	public Cashier(int maxTimeOfService, ServiceQueue serviceQueue, CustomerGenerator myCustomer)
	{
		myCashierThread = new Thread(this);
		myMaxTimeOfService= maxTimeOfService;
		myNumberOfCustomersServed = 0;
		myQueue = serviceQueue;
		myCustomers = myCustomer;
	}

	public abstract int generateServiceTime();
	
	public int serveCustomer() throws InterruptedException
	{
		String message;
		int served = myCustomers.myServiceQueueManager.getServiceQueue(1).getMyNumberCustomersServedSoFar();
		
		int max = myCustomers.getMaxCustomers();
		int customers = max ;
		while (customers>0)
		{
			System.out.print("serving ");
			this.waitWhileSuspended();
			
			try
			{
				myServiceTime = this.generateServiceTime();
				Thread.sleep(myServiceTime);
				myQueue.setMyServiceTime(myServiceTime);
			}
			catch (InterruptedException e)
			{
	            message = e.getMessage();
	            System.err.println(message);
			}
			
			myQueue = myCustomers.myServiceQueueManager.getServiceQueue(myCustomers.getMyLine());
			int size = myQueue.getMyNumberCustomersInLine();
			System.out.println(" number of customers left "+ size);
			myNumberOfCustomersServed++;
			myQueueM = myCustomers.myServiceQueueManager;
			myQueueM.cashierServeCustomer(myCustomers.getMyLine());
			served = myQueue.getMyNumberCustomersServedSoFar();
			customers = myCustomers.myServiceQueueManager.getNumCustomers(myCustomers.getMyLine());
		}

		System.out.print("done serving");
		return myNumberOfCustomersServed;	
	}

	private void waitWhileSuspended() throws InterruptedException 
	{
		while (myCashierSuspended)
		{
			this.wait(10000);
		}
	}

	public void run()
	{
		try
    	{
    		synchronized(this)
    		{
    			this.serveCustomer();
    		}
    	}
    	catch (InterruptedException e)
    	{
    		System.out.println("Thread suspended.");
    	}
	}
	

	public void start()
	{
		try
		{
		
			myCashierThread.start();
			System.out.print("Cashiers start serving");
		}
        catch(IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
	}
	
    public void suspend()
    {
    	
    	myCashierSuspended = true;
    	
    }
    
    public synchronized void resume()
    {
	    	myCashierSuspended = false;
	    	this.notify();
    }
    
	public void stop()
	{
		myCashierSuspended = false;
	}

	public int getMyMaxTimeOfService() {
		return myMaxTimeOfService;
	}

	public void setMyMaxTimeOfService(int myMaxTimeOfService) 
	{
		this.myMaxTimeOfService = myMaxTimeOfService;
	}
	
	public long getServiceTime()
	{
		return myServiceTime;
	}
}