//package servicequeue;
//
//public class mymain {
//	
//	public static void main(String[] args) throws InterruptedException
//	{
//		UniformCustomerGenerator g;
//		ServiceQueue q;
//		int numberOfCustomer = 10;
//		
//		ServiceQueueManager serviceQueueManager = new ServiceQueueManager();
//		g = new UniformCustomerGenerator(4, numberOfCustomer, serviceQueueManager);
//		g.start();
//		
//		g.run();
//		System.out.print("need to suspend");
//		g.suspend();
//		
//		serviceQueueManager.cashierServeCustomer(1);
//		System.out.print(serviceQueueManager.getServiceQueue(1));
//		
//		
//		
////		UniformCashier c;
////		ServiceQueue s = g.getServiceQueue();
////		c = new UniformCashier(3, s);
////		c.serveCustomer(s);
//	}
//	
//
//}
