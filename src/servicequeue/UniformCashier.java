package servicequeue;

import java.util.Random;

/**
 * concrete
 * @author Isabel and Tram
 * UniformCashier class is to create a uniform random service time
 */

public class UniformCashier extends Cashier 
{
	private Random myRandom;
	private int myMaxServiceTime;
	private int myServiceTime;
	
	
	public UniformCashier(int maxTimeOfService, ServiceQueue serviceQueue, 
			CustomerGenerator myCustomer) 
	{
		
		super(maxTimeOfService, serviceQueue, myCustomer);
		myRandom = new Random();
		myMaxServiceTime = maxTimeOfService;	
	}

	@Override
	public int generateServiceTime() 
	{	
		myServiceTime = myRandom.nextInt(myMaxServiceTime);
		return myServiceTime;
	}
}