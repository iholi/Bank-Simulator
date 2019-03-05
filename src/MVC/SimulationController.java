package MVC;

import servicequeue.ServiceQueueManager;

/**
 * Sample Queue MVC with threading, with this controller handling
 * the threading to pause while running.  Notice that this is the
 * conduit between the model (ServiceQueueManager) and the view 
 * (SimulationView).  You'll need to clean these up and implement
 * them appropriately.
 * 
*/
public class SimulationController implements Runnable
{
	//Data Members
	private SimulationModel myModel;
	private View myView;
	private boolean mySuspended;
	private long myTime;
	private Thread myThread;
	private ServiceQueueManager myServiceQM;
	/**
	 * Basic Constructor
	 * @throws InterruptedException 
	 */
	public SimulationController() throws InterruptedException
	{

		myView = new View(this);
		myServiceQM = new ServiceQueueManager();
		myModel = new SimulationModel(this, myView);
        myThread = new Thread(this);
		mySuspended = false;
		myThread.start();
//		this.startPause();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new SimulationController();
		
	}
	
	
	private void displayCustomers(int queue)
	{
		int numInQueue = myServiceQM.getNumCustomers(queue);

		myView.setCustomersInLine(queue, numInQueue);
	}
			
	/**
	 * Runs the thread that updates the view.
	 */
    public void run()
    {

	    	try
	    	{
	    		synchronized(this)
	    		{
	    			this.updateView();
					Thread.sleep(10);
	    		}
	    	}
	    	catch (InterruptedException e)
	    	{
	    		System.out.println("Thread suspended.");
	    	}
    }
	
	/**
	 * Updates the view.
	 * @throws InterruptedException
	 */
	private void updateView()throws InterruptedException
	{
		//need a variable here
		while(mySuspended==false)
		{
			this.waitWhileSuspended();
			
			try 
			{
				Thread.sleep(100);
				for(int x = 0; x < 5; x++)
				{
					
					myModel.displayCustomers(x);
					
				}
				this.suspend(); 
			} 
			catch (InterruptedException e) 
			{
					e.printStackTrace();
			}
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
    
    public void start()
    {
        try
        {
            myThread.start();
        }
        catch(IllegalThreadStateException e)
        {
            System.out.println("Thread already started");
        }
    }
    
    public synchronized void resume()
    {
	    	mySuspended = false;
	    	this.notify();
    }
    
    public void startPause() throws InterruptedException
    {
    	myView.changeStartPause();
    	//setting the inputs from users
    	myModel.getInput();
		myModel.generateAndServe();
		
		//myView.showResults();
    	myModel.displayCustomers(1);
    	if (mySuspended)
    	{
    		this.resume();
    	}
    	else
    	{
    		this.suspend();
	    	}
    }


	
	public void setQueueM(ServiceQueueManager myServiceQ)
	{
		myServiceQM = myServiceQ;
	}
	public ServiceQueueManager getSQM()
	{
		return myServiceQM;
	}
	
	public SimulationModel getModel()
	{
		return myModel;
	}
	
	
}