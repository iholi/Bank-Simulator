package servicequeue;
import java.util.Random;

/**
 * concrete
 * @author Isabel and Tram
 * UniformCustomerGenerator class is to create a uniform random generator. (help to generate the time)
 */

public class UniformCustomerGenerator extends CustomerGenerator 
{
	private Random myRandom;
	private int myMaxTimeCustomer;
	private int myTimeBetweenCustomers;
	
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers, int numberOfCustomer2, ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, numberOfCustomer2, serviceQueueManager);
		myRandom = new Random();
		myMaxTimeCustomer = maxTimeBetweenCustomers;
		
	}
	
	/**
	 * Create random time between the customers.
	 */
	public int generateTimeBetweenCustomers()
	{
		myTimeBetweenCustomers = myRandom.nextInt(myMaxTimeCustomer);
		return myTimeBetweenCustomers;
	}

}
