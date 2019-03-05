package MVC;

import java.awt.*;
import java.lang.reflect.Method;
import javax.swing.*;

import servicequeue.ButtonListener;

@SuppressWarnings("serial")
public class SimulationView extends JFrame
{
	//Constants
	private final int TELLER_WIDTH = 75;
	private final int TELLER_HEIGHT = 85;
	private final String TELLER_IMG = "images/teller.png";
	private final String FACE_IMG = "images/girl.jpg";
	private final int COUNTER_BOX_WIDTH = 50;
	private final int COUNTER_BOX_HEIGHT = 20;
	private final int CUSTOMER_WIDTH = 50;
	private final int CUSTOMER_HEIGHT = 50;
	private final int ROW_1 = 400;
	private final int ROW_2 = 460; 
	 
	private final int MAX_PEOPLE_IN_LINE = 5;
	private final int MAX_NUM_OF_TELLERS = 2;

	//Data Members
	private Image myScaledImage;
	private SimulationController myController;
	private Container myContentPane;
	private JLabel [] myTotalServed;
	private ButtonListener myStartPauseListener;
	private JLabel [][] myCustomer;
	private JButton myStartPauseButton;
	private JLabel [] myTeller;
	private JPanel mySimPanel;
	
	//Constructor
	
	/**
	 * Constructor that creates the view.
	 * 
	 * @param controller the SimulationController that gives function to the buttons.
	 */
	public SimulationView(SimulationController controller)
	{
		Image face = Toolkit.getDefaultToolkit().getImage(FACE_IMG);
		myScaledImage = face.getScaledInstance(CUSTOMER_WIDTH,CUSTOMER_HEIGHT,Image.SCALE_SMOOTH);
		
		myController = controller;
		
		//Start/Pause Button
		myStartPauseButton = new JButton("Start");
		
		this.associateListeners(myController);

		//Frame info
		this.setSize(600,600);
		this.setLocation(100, 100);
		this.setTitle("Sample Queue MVC");
		this.setResizable(false);
		
		myContentPane = getContentPane();
		myContentPane.setLayout(new BorderLayout());
		
		//Sim Panel
		mySimPanel = new JPanel();
		mySimPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mySimPanel.setLayout(null);
		
		//Customer Served Counter
		myTotalServed = new JLabel[MAX_NUM_OF_TELLERS];
		
		for(int i = 0; i < myTotalServed.length; i++)
		{
			myTotalServed[i] = new JLabel("0");
			myTotalServed[i].setSize(COUNTER_BOX_WIDTH, COUNTER_BOX_HEIGHT);
			myTotalServed[i].setLocation(65 + (CUSTOMER_WIDTH*i), ROW_2);
			myTotalServed[i].setBorder
					(BorderFactory.createLineBorder(Color.BLACK));
			mySimPanel.add(myTotalServed[i]);
		}

		//Teller locations
		myTeller = new JLabel[MAX_NUM_OF_TELLERS];
		
		for(int i = 0; i < MAX_NUM_OF_TELLERS; i++)
		{
			myTeller[i] = new JLabel(new ImageIcon(TELLER_IMG));
			myTeller[i].setSize(TELLER_WIDTH, TELLER_HEIGHT);
			myTeller[i].setLocation(50 + (CUSTOMER_WIDTH*i), ROW_1);
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
				myCustomer[i][j].setSize(CUSTOMER_WIDTH, CUSTOMER_HEIGHT);
				myCustomer[i][j].setLocation(65 + (CUSTOMER_WIDTH*i), 325 - (50*j));
				myCustomer[i][j].setVisible(true);
				mySimPanel.add(myCustomer[i][j]);
			}
		}
		
		//Background
		JLabel bg;
		bg = new JLabel(new ImageIcon("images/background.png"));
		bg.setSize(500, 500);
		bg.setLocation(0, 0);
		mySimPanel.add(bg);
		myContentPane.add(mySimPanel, BorderLayout.CENTER);
		myContentPane.add(myStartPauseButton, BorderLayout.EAST);
		
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
			myStartPauseButton.setText("Pause");
		}
		else
		{
			myStartPauseButton.setText("Start");
		}
	}
	
	 public void setCustomersInLine(int queue, int numInLine)
	 {
		System.out.println("Queue: " + queue + "  numInLine = " + numInLine);
		
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
	 
	/**
	 * Associates the button with the appropriate method
	 * @param controller The controller in which the method is included.
	 */
	private void associateListeners(SimulationController controller)
	{
		Class<? extends SimulationController> controllerClass;
		Method startPauseMethod;
		
		controllerClass = myController.getClass();
				
		startPauseMethod = null;
		
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

}