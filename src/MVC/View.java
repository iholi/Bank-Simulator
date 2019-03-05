package MVC;
import servicequeue.ButtonListener;
import servicequeue.ServiceQueue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Isabel and Tram
 * View for the Queue Class
 * 
 */
public class View extends JFrame{

	//Constants
	private final String TELLER_IMG = "images/teller.png";
//	private final String[] FACE_IMG = {"images/family.png","images/girl.png",
//			"images/grandm.png", "images/guy.jpg"};
	private final String FACE_IMG="images/girl.png";
	private Vector<ImageIcon> myCustoms;
	private final int MAX_PEOPLE_IN_LINE = 10;
	private final int MAX_NUM_OF_TELLERS = 5;

	//Data Members
	private Image myScaledImage, myTellerScaleImage, myBGScale;
	private SimulationController myController;
	private SimulationModel myModel;
	private Container myContentPane;
	private JLabel [] myTotalServed, myTotalNotServed;
	private ButtonListener myStartPauseListener;
	private JLabel [][] myCustomer;
	private JButton myStartPauseButton;
	private JLabel [] myStat,myTeller;
	private JPanel mySimPanel;
	private Random myRandom;
	private int myCustomerFace;
	private ServiceQueue myServiceQueue;
	
	private int maxGenerator, maxServiceTime, noOfCustomers, noOfTellers;
	
	private JLabel myNotServed, myStatTotal;
	private JLabel myGenerateTime, myNoCustomers, myNoTellers, myMaxServiceTime;
	private JTextField myGenerateTimeText, myNoCustomersText, myNoTellersText, myMaxServiceText;
	//Constructor
	
	/**
	 * Constructor that creates the view.
	 * 
	 * @param controller the SimulationController that gives function to the buttons.
	 */
	public View(SimulationController controller)
	{
		
		myController = controller;
		myModel = controller.getModel();
		this.View2();
	}
	
	public void View2()
	{
		myCustoms = new Vector<ImageIcon>();
		myCustoms.add(new ImageIcon("images/family.png"));
		myCustoms.add(new ImageIcon("images/girl.png"));
		myCustoms.add(new ImageIcon ("images/guy.png"));
		myCustoms.add(new ImageIcon ("images/grandm.png"));
		
//		myRandom = new Random();
//		int randomImages = myRandom.nextInt(myCustoms.size());
		
//		myRandom = new Random();
//		myCustomerFace = myRandom.nextInt(4);
//		Image face = Toolkit.getDefaultToolkit().getImage(FACE_IMG[0]);
		Image customer = Toolkit.getDefaultToolkit().getImage(FACE_IMG);
		myScaledImage = customer.getScaledInstance(200,100,Image.SCALE_SMOOTH);
		
		
		Image teller = Toolkit.getDefaultToolkit().getImage(TELLER_IMG);
		myTellerScaleImage = teller.getScaledInstance(200,100,Image.SCALE_SMOOTH);
		
		//Start/Pause Button
		myStartPauseButton = new JButton("Start");
	
		this.associateListeners(myController);

		//Frame info
		this.setSize(1300,700);
		this.setLocation(50, 50);
		this.setTitle("Queue MVC");
		this.setResizable(true);
		
		myContentPane = getContentPane();
		myContentPane.setLayout(new BorderLayout());
		
		//Sim Panel
		mySimPanel = new JPanel();
		mySimPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mySimPanel.setLayout(null);
		
		
		myStat = new JLabel[MAX_NUM_OF_TELLERS];
		
		
		for(int i =0; i < myStat.length; i++)
		{
			myStat[i] = new JLabel();
			myStat[i].setText("Cashier's Statistics");
			myStat[i].setSize(200-20, 150);
			myStat[i].setLocation(50 + (200*i), 500+10);
			myStat[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			myStat[i].setOpaque(true);
			myStat[i].setBackground(Color.WHITE);
			mySimPanel.add(myStat[i]);
		}
		
		myStatTotal = new JLabel();
		myStatTotal.setOpaque(true);
		myStatTotal.setBackground(Color.WHITE);
		myStatTotal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		myStatTotal.setSize(250, 250);
		myStatTotal.setLocation(1050, 500-100);
		mySimPanel.add(myStatTotal);
		
		
		//Teller locations
		myTeller = new JLabel[MAX_NUM_OF_TELLERS];
		
		for(int i = 0; i < MAX_NUM_OF_TELLERS; i++)
		{
			myTeller[i] = new JLabel(new ImageIcon(TELLER_IMG));
			myTeller[i].setSize(200-20, 100);
			myTeller[i].setLocation(50 + (200*i), 400);
			myTeller[i].setVisible(true);
			mySimPanel.add(myTeller[i]);
		}
	
		//Customer Lines
		myCustomer = new JLabel[MAX_NUM_OF_TELLERS][MAX_PEOPLE_IN_LINE];
		for(int i = 0; i < MAX_NUM_OF_TELLERS; i++)
		{
			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
			{
				myCustomer[i][j] = new JLabel();
				myCustomer[i][j].setSize(200, 100);
				myCustomer[i][j].setLocation(50 + (200*i), 325 - (50*j));
				myCustomer[i][j].setVisible(false); //
				mySimPanel.add(myCustomer[i][j]);
			}
		}
		
		JLabel instruction = new JLabel();
		instruction.setText("<html>Please fill out the values below "
				+ "<br> (integer value only) </html>");
		instruction.setSize(200, 50);
		instruction.setLocation(1070, 50);
		instruction.setVisible(true);
		mySimPanel.add(instruction);
		
		myGenerateTime = new JLabel();
		myGenerateTime.setSize(100, 50);
		myGenerateTime.setText("Generate Time:");
		myGenerateTime.setLocation(1050, 100);
		myGenerateTime.setVisible(true);
		mySimPanel.add(myGenerateTime);
		
		myGenerateTimeText = new JTextField();
		myGenerateTimeText.setSize(100, 50);
		myGenerateTimeText.setLocation(1200,100);
		myGenerateTimeText.setVisible(true);
		mySimPanel.add(myGenerateTimeText);
		
		myNoCustomers = new JLabel();
		myNoCustomers.setSize(150, 50);
		myNoCustomers.setText("Number Of Customers: ");
		myNoCustomers.setLocation(1050, 150);
		myNoCustomers.setVisible(true);
		mySimPanel.add(myNoCustomers);
		
		myNoCustomersText = new JTextField();
		myNoCustomersText.setSize(100, 50);
		myNoCustomersText.setLocation(1200,150);
		myNoCustomersText.setVisible(true);
		mySimPanel.add(myNoCustomersText);
		
		myNoTellers = new JLabel();
		myNoTellers.setSize(150, 50);
		myNoTellers.setText("<html>Number Of Tellers<br>  (max 5):</html>");
		myNoTellers.setLocation(1050, 200);
		myNoTellers.setVisible(true);
		mySimPanel.add(myNoTellers);
		
		myNoTellersText = new JTextField();	
		myNoTellersText.setSize(100, 50);
		myNoTellersText.setLocation(1200,200);
		myNoTellersText.setVisible(true);
		mySimPanel.add(myNoTellersText);
		
		myMaxServiceTime = new JLabel();
		myMaxServiceTime.setSize(150, 50);
		myMaxServiceTime.setText("Max Time Of Service: ");
		myMaxServiceTime.setLocation(1050, 250);
		myMaxServiceTime.setVisible(true);
		mySimPanel.add(myMaxServiceTime);
		
		myMaxServiceText = new JTextField();	
		myMaxServiceText.setSize(100, 50);
		myMaxServiceText.setLocation(1200, 250);
		myMaxServiceText.setVisible(true);
		mySimPanel.add(myMaxServiceText);
		
		myStartPauseButton.setText("Start");
		myStartPauseButton.setSize(100, 50);
		myStartPauseButton.setLocation(1110, 300);
		myStartPauseButton.setText("Start");
		myStartPauseButton.setVisible(true);
		mySimPanel.add(myStartPauseButton);
		
		
		//Background
		JLabel bg;
		
		Image bg1 = Toolkit.getDefaultToolkit().getImage("images/new_floor.png");
		myBGScale = bg1.getScaledInstance(1200,700,Image.SCALE_SMOOTH);
		bg = new JLabel(new ImageIcon(myBGScale));
		bg.setSize(1050, 700);
		bg.setLocation(0, 0);
		mySimPanel.add(bg);
		
		myContentPane.add(mySimPanel, BorderLayout.CENTER);
		
		
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}

	//////////////////////////////////////////
	//            Methods                   //
	//////////////////////////////////////////

	public void changeStartPause() 
	{
		if(myStartPauseButton.getText().equals("Start"))
		{
			this.startButton();
			myStartPauseButton.setText("Pause");
			
		}
		else
		{
			myStartPauseButton.setText("Start");
			for (int i =0; i<noOfTellers;i++)
			{
				this.showResults(i);
			}
			
		}
	}
	
	 public void setCustomersInLine(int queue, int numInLine)
	 {
		System.out.println("Queue : " + queue + "  numInLine = " + numInLine);
		
		myTeller[queue].setIcon(new ImageIcon(TELLER_IMG));
		
		for(int i = 0; i < MAX_PEOPLE_IN_LINE; i++)
		{
			myCustomer[queue][i].setVisible(false);
		}
		try
		{
			for(int i = 0; i < numInLine && i < MAX_PEOPLE_IN_LINE; i++)
			{
				myCustomer[queue][i].setVisible(true);
			    myCustomer[queue][i].setIcon(new ImageIcon(myScaledImage));
			} 
		}
		catch (NullPointerException e)
		{
			
		}
	 }

	 public void remove(int queue)
	 {
		 for(int i = 0; i < myCustomer.length; i++)
		 {
			 myCustomer[queue][i].setVisible(false);
		 }
	 }
	 
	private void associateListeners(SimulationController myController2) {
		Class<? extends SimulationController> controllerClass;
		Method startPauseMethod;
		
		Class<?>[] classArgs;
		controllerClass = myController.getClass();
				
		startPauseMethod = null;
		
		classArgs = new Class[1];
		
		try
        {
           classArgs[0] = Class.forName("java.lang.Integer");
        }
        catch(ClassNotFoundException exception)
        {
        	String error1;
           error1 = exception.toString();
           System.out.println(error1);
        }
		try 
		{
			startPauseMethod = 
				controllerClass.getMethod("startPause", (Class<?>[])null);
			
		} 
		catch (SecurityException e) 
		{	
			String error;

			error = e.toString();
			System.out.println(error);
		} 
		catch (NoSuchMethodException e) 
		{
			String error;

	        error = e.toString();
	        System.out.println(error);
		}
		
		myStartPauseListener = 
			new ButtonListener(myController, startPauseMethod, null);
				
		myStartPauseButton.addMouseListener(myStartPauseListener);
		
	}
	
	//whenever the start button is pushed, then this method is triggered
	public void startButton() 
	{
		try
		{
			String input;
			input = myGenerateTimeText.getText();
			maxGenerator = Integer.parseInt(input);
			
		}
		catch(java.lang.NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(myGenerateTime, "Hmm..Please check your generate time, and make sure they are all integers");
		}
		//check for number of customers
		try
		{
			String input;
			input = myNoCustomersText.getText();
			noOfCustomers = Integer.parseInt(input);
			
		}
		catch(java.lang.NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(myNoCustomersText, "Hmm..Please check your customer numbers, and make sure they are all integers");
		}
		
		//check for max time of service
		try
		{
			String input;
			input = myMaxServiceText.getText();
			maxServiceTime = Integer.parseInt(input);
			
		}
		catch(java.lang.NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(myMaxServiceText, "Hmm..Please check your max service time, and make sure they are all integers");
		}
		//check for number of tellers has to be smaller than 5
		try
		{
			String input;
			input = myNoTellersText.getText();
			
			boolean check = false;
			while (!check)
			{
				try
				{
					noOfTellers = Integer.parseInt(input);
					check = true;
					if(noOfTellers>0 && noOfTellers<6)
					{
						check = true;
					}
					else
					{
						JOptionPane.showMessageDialog(myNoTellersText, "Hmm..Please check your tellers numbers, "
								+ "and make sure they are smaller than 5");

					}
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(myNoTellersText, "Hmm..Please check your tellers numbers, and make sure they are all integers");

				
				}
			}
			
		}
		catch(java.lang.NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(myNoTellersText, "Hmm..Please check your tellers numbers, and make sure they are all integers");
		}
		
		
		catch (SecurityException e) 
		{	
			String error;

			error = e.toString();
			System.out.println(error);
		} 

	}
	
	public void Results(int i)
	{
		int totalServiceTime;
		int totalIdleTime;
		int totalWaitTime;
		int avgSerTime;
		int avgWaitTime;
		int avgIdleTime;
		
	}

	//Show all results
	public void showResults(int queNum)
	{
		System.out.print("printed1");
		int totalServed = myController.getModel().TotalServedSoFar(queNum);
		long totalServiceTime = myController.getModel().TotalServiceTime(queNum)/1000000000;
		long totalIdleTime = myController.getModel().TotalIdleTime(queNum)/1000000000;
		long totalWaitTime = myController.getModel().TotalWaitTime(queNum)/1000000000;
		System.out.print("printed2");
		long avgSerTime = myController.getModel().AverageServiceTime(queNum)/1000000000;
		long avgWaitTime = myController.getModel().AverageWaitTime(queNum)/1000000000;
		long avgIdleTime = myController.getModel().AverageIdleTime(queNum)/1000000000;

		String message1 = "<html>Total Served:"  + totalServed +
				  "<br>Total Service Time: " + totalServiceTime + 
				  "<br> Total Idle Time: " + totalIdleTime + 
				  "<br> Total Waiting Time: " + totalWaitTime + 
				  "<br> Average Service: " + avgSerTime + 
				  "<br> Average Wait: " + avgWaitTime +
				  "<br> Average Idle:" + avgIdleTime;
		
		myStat[queNum].setText(message1);
		System.out.print("printed");
	}


	public int getMaxGenerator() 
	{
		return maxGenerator;
	}

	public void setMaxGenerator(int maxGen) {
		maxGenerator = maxGen;
	}

	public int getMaxServiceTime() 
	{
		return maxServiceTime;
	}

	public void setMaxServiceTime(int maxServiceTi) 
	{
		maxServiceTime = maxServiceTi;
	}

	public int getNoOfCustomers() 
	{
		return noOfCustomers;
	}

	public void setNoOfCustomers(int noOfCus) 
	{
		noOfCustomers = noOfCus;
	}

	public int getNoOfTellers() 
	{
		return noOfTellers;
	}

	public void setNoOfTellers(int noOfTeller) 
	{
		noOfTellers = noOfTeller;
	}

}
