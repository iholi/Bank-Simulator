package MVC;

/*
 * merge 2 branches
 */
import java.util.Vector;

import servicequeue.Cashier;
import servicequeue.CustomerGenerator;
import servicequeue.ServiceQueue;
import servicequeue.ServiceQueueManager;
import servicequeue.UniformCashier;
import servicequeue.UniformCustomerGenerator;

public class SimulationModel 
{
	private ServiceQueueManager myServiceQueueM;
	private SimulationController myController;
	private View myView;
	public CustomerGenerator myCustomer;
	private ServiceQueue myQueue;
	private Vector<Cashier> myCashiersVector;
	private Vector<ServiceQueue> mySQVector;
	private Cashier myCashiers;
	private int myCustomers2;
	private int zero=0;
	private int maxTimeBWCustomer, maxCustomers, numberOfTeller, maxServiceTime;

	public SimulationModel(SimulationController controller, View view) {
		myController = controller;
		myCustomers2 =zero;
		myView = view;
		myCashiersVector = new Vector<Cashier>();
		mySQVector = new Vector<ServiceQueue>();
	}
	
	public void getInput()
	{

		maxTimeBWCustomer = myView.getMaxGenerator();
		maxCustomers = myView.getNoOfCustomers();
		numberOfTeller = myView.getNoOfTellers();
		maxServiceTime = myView.getMaxServiceTime();
		
		myServiceQueueM = new ServiceQueueManager();
		myServiceQueueM.setNumberOfServiceQueue(numberOfTeller);
		myCustomer = new UniformCustomerGenerator(maxTimeBWCustomer, maxCustomers, myServiceQueueM);
			
	}	

	public void generateAndServe() 
			throws InterruptedException
	{
		long start = myCustomer.getStartTime();
 		myCustomer.start(); //generating customers and put them into queues
 		
		
		for (int i =0; i<numberOfTeller;i++)
		{
			this.displayCustomers(i);
			ServiceQueue tempQueue = new ServiceQueue();
			tempQueue = myCustomer.getServiceQueue(i);
			mySQVector.add(tempQueue);
			System.out.print(" queue "+i+1+" started ");
			
			Cashier tempCashier;
			tempCashier = new UniformCashier(maxServiceTime, tempQueue, myCustomer);
			myCashiersVector.add(tempCashier);
			myCashiersVector.get(i).start();
			myCashiersVector.get(i).suspend();
			myCashiersVector.get(i).resume();
			System.out.print(" queue "+i+1+" finished ");
		}
		myQueue = myCustomer.getServiceQueue(1);
	
		myCashiers = new UniformCashier(maxServiceTime, myQueue, myCustomer);
		
		int customers = myQueue.getSize();
		//myCashiers.start();//serve them
		start = myCustomer.getStartTime();
		
//	    for ( int i  = 0 ; i < myServiceQueueM.getServiceQueue(queueNum).getSize(); i ++)
//	    {
//	       ((Cashier) myCashiersVector.get(i)).start();
//	    }    
		
//	    myController.setQueueM(myServiceQueueM);
		myCashiers.start();
		System.out.println("cashier started");
	
		myCashiers.suspend();

		System.out.println("cashier is suspended");
		try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            String message = e.getMessage();
            System.err.println(message);
        }
		myCustomer.resume();
		System.out.println("Resume customers");
		try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            String message = e.getMessage();
            System.err.println(message);
        }
		myCashiers.resume();
		System.out.print("resume cashiers");
		customers = myQueue.getSize();
		
		//myCashiers.resume();
		
		int queueNum=0;
		myCustomers2 = maxCustomers;

	}
	
	/**
	 * Displays the customer images in the appropriate lines.
	 * @param index The queue index for the customers to be printed in.
	 */
	public void displayCustomers(int queue)
	{
		int numInQueue=0;
	
		if (myServiceQueueM!=null)
		{
			numInQueue= myServiceQueueM.getNumCustomers(queue);
		}
	}

	public int getCustomers2()
	{
		return myCustomers2;
	}

	public ServiceQueueManager getMyServiceQueueM() 
	{
		return myServiceQueueM;
	}

	public void setMyServiceQueueM(ServiceQueueManager myServiceQueueM) 
	{
		this.myServiceQueueM = myServiceQueueM;
	}

	public CustomerGenerator getMyCustomer() 
	{
		return myCustomer;
	}

	public void setMyCustomer(CustomerGenerator myCustomer) 
	{
		this.myCustomer = myCustomer;
	}

	public ServiceQueue getServiceQueue(int queueNum)
	{
		return myServiceQueueM.getServiceQueue(queueNum);
	}
	
	public Vector getMyCashiers() 
	{
		return myCashiersVector;
	}

	public void setMyCashiers(Vector myCashiers) 
	{
		this.myCashiersVector = myCashiers;
	}
	
	public int TotalServedSoFar(int queueNum)
	{

		return myServiceQueueM.totalServedSoFar(queueNum);
	}
	
	public long TotalIdleTime(int queueNum)
	{
		return myServiceQueueM.totalIdleTime(queueNum);
	}
	
	public long TotalWaitTime(int queueNum)
	{
		return myServiceQueueM.totalWaitTime(queueNum);
	}
	
	public long TotalServiceTime(int queueNum)
	{
		return myServiceQueueM.totalServiceTime(queueNum);
	}
	
	public long AverageIdleTime(int queueNum)
	{

		return myServiceQueueM.averageIdleTime(queueNum);
	}
	
	public long AverageWaitTime(int queueNum)
	{

		return myServiceQueueM.averageWaitTime(queueNum);
	}
	
	public long AverageServiceTime(int queueNum)
	{
		return myServiceQueueM.averageServiceTime(queueNum);
	}
}
