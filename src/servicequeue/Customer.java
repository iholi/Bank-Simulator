package servicequeue;

/**
 * @author Isabel and Tram
 * Customer class, help to "store" the time of service, entry, and wait. 
 */

public class Customer 
{
	private long myServiceTime;
	private long myEntryTime;
	private long myWaitTime;
	private int zero=0;

	public Customer()
	{
		myServiceTime=zero;
		myEntryTime=zero;
		myWaitTime=zero;
	}
	
	/**
	 * Getters and Setters
	 * @return
	 */
	public long getMyServiceTime() 
	{
		return myServiceTime;
	}

	public void setMyServiceTime(long myServiceTime) 
	{
		this.myServiceTime = myServiceTime;
	}

	public long getMyEntryTime() 
	{
		return myEntryTime;
	}

	public void setMyEntryTime(long myEntryTime) 
	{
		this.myEntryTime = myEntryTime;
	}

	public long getMyWaitTime() 
	{
		return myWaitTime;
	}

	public void setMyWaitTime(long myWaitTime) 
	{
		this.myWaitTime = myWaitTime;
	}
}