/**
 * Class CarShop.
 *   CarShop program
 *   
 *   This program is designed to record and report 
 *   on stock in a car sales showroom. The program 
 *   runs via the command prompt only.
 *   
 *   It was designed as part of an end of module
 *   assessment for the Masters in Computer Science
 *   program at the University of York. Part of the
 *   requirements for this were that the whole 
 *   program ones from one file.
 *   
 *   @author Will Irvine
 *   @version 2.3
 * 
 */

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class CarShop {
	//class attributes
	private String make;
	private String model;
	private String condition;
	private int year;
	private int mileage;
	private double price;
	private double engine;
	private char grade;
	private int id=1; //id of first car in system
	private static Scanner sc = new Scanner(System.in);
	private static final DecimalFormat df2 = new DecimalFormat("0.00");//currency format 
	private static final DecimalFormat df1 = new DecimalFormat("0.0");//engine size format

	private static ArrayList<CarShop> carList = new ArrayList<>();	//array list for objects
	
	//constructor
	public CarShop(String makeIn, String modelIn, int yearIn, int mileageIn, double priceIn, double engineIn, char gradeIn, int idIn)
	{		
		//set the values for this object
		setMake(makeIn);
		setModel(modelIn);
		setYear(yearIn);
		setMileage(mileageIn);
		setPrice(priceIn);
		setEngine(engineIn);
		setGrade(gradeIn);
		setId(idIn);
		
		//set condition from gradeIn
		
		switch(gradeIn)
		{
			case 'A':{
				setCondition("Excellent");
				break;
				}
			case 'B':{
				setCondition("good");
				break;
			}
			case 'C':{
				setCondition("average");
				break;
			}
			case 'D':{
				setCondition("poor");
				break;
			}			
			default:{
				setCondition("Absent");
				break;
			}
		}
		
		
	}	
	
	//default constructor
	public CarShop()
	{
		
	}
		
	//geters
	public String getMake()
	{
		return make;
	}
	
	public String getModel()
	{
		return model;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getMileage()
	{
		return mileage;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public double getEngine()
	{
		return engine;
	}
	
	public char getGrade()
	{
		return grade;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getCondition()
	{
		return condition;
	}

	//setters	
	public void setMake(String makeIn)
	{
		make=makeIn;
	}
	
	public void setModel(String modelIn)
	{
		model=modelIn;
	}
	
	public void setYear(int yearIn)
	{
		year=yearIn;
	}
	
	public void setMileage(int mileageIn)
	{
		mileage=mileageIn;	
	}
	
	public void setPrice(double priceIn)
	{
		price=priceIn;
	}
	
	public void setEngine(double engineIn)
	{
		engine=engineIn;
	}
	
	public void setGrade(char gradeIn)
	{
		grade=gradeIn;	
	}
	
	public void setId(int idIn)
	{
		id=idIn;
	}

	public void setCondition(String conditionIn)
	{
		condition=conditionIn;
	}
	
	public String getAllData()
	{
		//returns a string with all standard data
		return Integer.toString(getId())+ "\t"
				+getMake() + "\t"
				+getModel() + "\t"
				+Integer.toString(getYear()) + "\t"
				+Integer.toString(getMileage())+ "\t"
				+Double.toString(getEngine())
				+getGrade()+ "\t"
				+Double.toString(getPrice());
	}
	
	public static void displaySpecificData(int carListPos)
	{
		// Method to display the data for a particular vehicle selected by user
		
		System.out.printf("-------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-5s | %-10s |%n",
				"Car ID", "Manufacturer", "Model", "Year", "Mileage", "Engine Size(L)", "Grade", "Price(£)");
		System.out.printf("-------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-5s | %-10s |%n",
			carList.get(carListPos).getId(), carList.get(carListPos).getMake(), carList.get(carListPos).getModel(),
			carList.get(carListPos).getYear(), carList.get(carListPos).getMileage(),
			df1.format(carList.get(carListPos).getEngine()),
			carList.get(carListPos).getGrade(), df2.format(carList.get(carListPos).getPrice()));						
		System.out.printf("-------------------------------------------------------------------------------------------------------%n\n\n");
	}
	
	public static void displayQuantitiesOfVehicle()
	{
		//method to dispaly quantities and total values of cars
		
		//Array to record if make has already been recorded when counting how many of each
		ArrayList<String> makes = new ArrayList<>();
		
		int rowId=0;
		
		System.out.println("Quantities of different makes of car in stock and total value:\n\n");
		
		System.out.printf("------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-4s | %-15s | %-43s | %-15s | %n",
				"ID", "Manufacturer", "Total number of vehicles available for sale", "Stock Price(£)");
		System.out.printf("------------------------------------------------------------------------------------------%n");	
		for (CarShop item : carList)
		{
			if(!makes.contains(item.make))
			{
				rowId++;
				System.out.printf("| %-4s | %-15s | %-43s | %-15s | %n",
						rowId, item.make, item.countMake(item.make), df2.format(item.countCost(item.make)));
				makes.add(item.make); //tick this make off the list
			}
		}
		System.out.printf("------------------------------------------------------------------------------------------%n\n\n");	
	}
	
	public static void displayAllData()
	{
		System.out.printf("-------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-5s | %-10s |%n",
				"Car ID", "Manufacturer", "Model", "Year", "Mileage", "Engine Size(L)", "Grade", "Price(£)");
		System.out.printf("-------------------------------------------------------------------------------------------------------%n");
		for (CarShop item : carList)
		{
			System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-5s | %-10s |%n",
					item.getId(), item.getMake(), item.getModel(), item.getYear(), item.getMileage(),
					df1.format(item.getEngine()), item.getGrade(), df2.format(item.getPrice()));	
		}
		System.out.printf("-------------------------------------------------------------------------------------------------------%n\n\n");
	}
	
	public void setCarData(){
		//gets car data from the user and constructs an object
		//general error checking on user input is performed by method entryHandle
		//specific error chacks are performed here
		boolean goodEntry=false;
		
		System.out.print("Enter vehicle manufacturer: ");
		make=entryHandle(false, true, false, false);
		System.out.print("Enter Vehicle model: ");
		model=entryHandle(false, true, false, false);
		System.out.print("Enter vehicle year of manufacture: ");
		do{
			//error check the year of manufacture
			year=Integer.parseInt(entryHandle(false, false, true, false));
			if(year<1885)
			{	//If the year entered was before 1885 (when first car was built) get the user to re-enter
				System.out.printf("\nThe first car was built in 1885. You've entered a year before that. Please try again:");	
			}
			else if(year> Calendar.getInstance().get(Calendar.YEAR))
			{
				//Check the year entered is not in the future
				System.out.printf("\nThe year you have entered is in the future. Please try again:");
			}
			else
			{
				goodEntry=true;
			}
		}
		while(!goodEntry);
		//reset the check on entries to be false
		goodEntry=false;
		System.out.print("Enter vehicle mileage: ");		
		do{
			//error check the mileage
			mileage=Integer.parseInt(entryHandle(false, false, true, false));
			if(mileage>1000000)
			{
				//Check if the value entered is over one million miles
				System.out.printf("\nYou've entered a value over one million miles. Please try again:");	
			}
			else
			{
				goodEntry=true;
			}
		}
		while(!goodEntry);
		//reset the check on entries to be false
		goodEntry=false;
				
		System.out.print("Enter vehicle Engine Size in litres. Enter 0 for electric vehicles: ");
		do{
			//error check the engine size
			engine=Double.parseDouble(entryHandle(false, false, false, true));
			if(engine>10)
			{
				//Check if the value entered is over 10 litres
				System.out.printf("\nYou've entered a value over 10 litres, please try again:");	
			}
			else
			{
				goodEntry=true;
			}
		}
		while(!goodEntry);
		//reset the check on entries to be false
		goodEntry=false;
		
		System.out.print("Enter vehicle grade A-D (capital letter): ");
		do{
			//error check the grade
			grade=entryHandle(true, false, false, false).charAt(0);
			if(grade!='A'&&grade!='B'&&grade!='C'&&grade!='D')
			{
				//Check if the value entered is allowed
				System.out.printf("\nOnly grades A-D are allowed, please try again:");	
			}
			else
			{
				goodEntry=true;
			}
		}
		while(!goodEntry);
		//reset the check on entries to be false
		goodEntry=false;
		
		System.out.print("Enter vehicle price: ");
		do{
			//error check the price
			price=Double.parseDouble(entryHandle(false, false, false, true));
			if(price>100000)
			{
				//Check if the value entered is over £100,000
				System.out.printf("\nYou've entered a value over £100,000. Please try again:");	
			}
			else
			{
				goodEntry=true;
			}
		}
		while(!goodEntry);		
		
		//add new car object to the ArrayList carList
		carList.add(new CarShop(make, model, year, mileage, price, engine, grade, id));
		// increase the id number ready for next vehicle to be added
		id++;
	}
		
	public void loadSampleData()
	{
		//Create new objects using the sample data and add them to the carList ArrayList
		carList.add(new CarShop("Honda", "Fit", 2013, 200500, 5550.5, 1.3, 'A', id++));
		carList.add(new CarShop("Toyota", "Prius", 2012, 88000, 8450, 1.8, 'A', id++));
		carList.add(new CarShop("Volkswagen", "Golf", 2016, 74550, 12500, 1.5, 'B', id++));
		carList.add(new CarShop("Toyota", "Yaris", 2011, 110100, 6500.5, 1.0, 'A', id++));
		carList.add(new CarShop("Toyota", "Prius", 2015, 52300, 9999.95, 1.8, 'C', id++));
		carList.add(new CarShop("Volkswagen", "Polo", 2012, 140820, 3050.5, 1.2, 'B', id++));
	}
	
	public int countMake(String makeIn)
	{
		//counts how many vehicles of the make sent as makeIn
		int total=0;
		for (CarShop item : carList)
		{
			if(makeIn.compareToIgnoreCase(item.make)==0)
			{
				total++;
			}
		}
		return total;
	}
	
	public double countCost (String makeIn)
	{
		//Sums the total value of vehicles of the make sent as makeIn
				double total=0;
				for (CarShop item : carList)
				{
					if(makeIn.compareToIgnoreCase(item.make)==0)
					{
						total=total+item.getPrice();
					}
				}
				return total;
	}
	
	public void showCondition(int idIn)
	{
		//displays the condition of a particular car
		String carCondition="";
		
		//counter to work out the position of the car in the array
		int idCounter=0;
		int actualId=-1;
		
		for (CarShop item : carList)
		{
			//get the condition for the chosen car
			if(idIn==item.getId())
			{
				carCondition=item.getCondition();
				actualId=idCounter; //location of the selected car within the array is recorded
				break;
			}
			//increase the id counter to work out what position in the array the car ACTUALLy is
			idCounter++;
		}
		
		if(carCondition.compareTo("")==0||actualId==-1)
		{
			//if no carCondition or ID was assigned give an error
			System.out.println("Error! Car not found!\n");
		}
		else
		{
			//display the results in a table
			System.out.printf("------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-9s | %-10s |%n",
					"Car ID", "Manufacturer", "Model", "Year", "Mileage", "Engine Size(L)", "Condition", "Price(£)");
			System.out.printf("------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %-7s | %-15s | %-15s | %-4s | %-7s | %-15s | %-9s | %-10s |%n",
				carList.get(actualId).getId(), carList.get(actualId).getMake(), carList.get(actualId).getModel(),
				carList.get(actualId).getYear(), carList.get(actualId).getMileage(),df1.format(carList.get(actualId).getEngine()),
				carCondition, df2.format(carList.get(actualId).getPrice()));						
			System.out.printf("------------------------------------------------------------------------------------------------------------%n\n\n");
		}
		
	}
	
	public static void waitForEnterKey()
	
	{	//method to pause until user is ready to move on by pressing enter	
		System.out.println("Press 'Enter' key to return to the main menu.");
		try {
	        System.in.read();
	        sc.nextLine();
	    }
	    catch (IOException e){
	        System.out.println("Error reading from user");
	    }
		
	}
	
	public static String entryHandle(boolean isChar, boolean isString, boolean isInt, boolean isDouble)
	{
		//General user entry error handling method
		String entry="";
		//Scanner sc = new Scanner(System.in);
		int i=-1;
		double d=-1.0;
		boolean goodEntry=false;
		boolean checkIsNumber=false;
						
		do
		{
				entry=sc.nextLine();
				//check it's not an empty String
			if(entry.substring(0).equals(""))
			{
				System.out.printf("\nYou didn't enter anything! Please try again:");
			}	
			else
			{ 
				//if string isn't empty
				if(isChar) {
					//check it's only one character
					if(!entry.substring(1).equals(""))
					{
						System.out.printf("\nMore than one character entered! Please try again:");
					}
					//check it's only an upper case letter
					else if(!Character.isUpperCase(entry.charAt(0)))
					{
						System.out.printf("\nCharacter should be an upper case letter only! Please try again:");
					}
					else
					{					
						return entry;
					}
				
				}
				if(isString)
				{
					//Any strings allowed				
					return entry;
				}
				if(isInt)
				{
					//check it's a positive number with no decimals
					do {
						try {
							//try to convert it to an int
							i = Integer.parseInt(entry);
						} 
						catch (NumberFormatException nfe) {
							//if that didn't work, tell the user
							System.out.printf("\nInvalid number entered! Please enter numbers only (no symbols):");
							break;
						}
						checkIsNumber=true;
					}
					while(!checkIsNumber);
					
					//check it's not a negative number
					if(i>-1&&checkIsNumber)
					{
						return entry;
					}
					else if(checkIsNumber)
					{
						System.out.printf("\nPlease enter positive numbers only. Please try again:");
					}
				
				}
				if(isDouble)
				{
					//check it is a number and is positive
					do {
						try {
							//try to convert it to a double
							d = Double.parseDouble(entry);
						} 
						catch (NumberFormatException nfe) {
							//if that didn't work, tell the user
							System.out.printf("\nInvalid value entered! Please enter a number (no symbols except for a decimal point):");
							break;
						}
						checkIsNumber=true;
					}
					while(!checkIsNumber);
					
					//check it's a positive number
					if(d>=0&&checkIsNumber)
					{
						return entry;
					}
					else if(checkIsNumber)
					{
						System.out.printf("\nPlease enter positive numbers only. Please try again:");
					}
				}
			}
		}	
			while(!goodEntry);
			return entry;
	}
	
	public static boolean stockCheck()
	{
		//Checks if there are cars in stock. Returns true if there are, gives error message if not.
		boolean stock=false;
		
		if (carList.size()==0)
		{
			System.out.println("There are no cars in stock!\n\n");
		}
		else
		{
			stock = true;
		}
		
		return stock;
	}
	
	public void deleteEntry(int idIn)
	{
		//deletes the chosen car from stock
		int remove=-1;
		int count = 0;
		
		for (CarShop item : carList)
		{			
			if(item.getId()==idIn)
			{
				//when the car is found, record its position in the ArrayList
				remove = count;
				break;
			}
			//count the position in the array
			count++;
		}
		if(remove==-1)
		{
			System.out.println("\nNo car with that ID number found!\n");
		}
		else
		{
			//if the car exits, remove it
			carList.remove(remove);			
			System.out.println("\nEntry deleted successfully\n");
		}
		
	}
	
	public static void main(String[] args) {

		//variable to record user's choice
		int selection;
		//Create an object
		CarShop entry = new CarShop();
		
		char choice;		
				
		//run the menu until quit option selected
		do {
			//clear the screen
			System.out.print("\033[H\033[2J");
			System.out.flush();
			//Display the menu
			System.out.printf("Menu Choices:\n\n"				
				+ "1. Load sample data\n"
				+ "2. Enter new data for a car\n"
				+ "3. List cars in the order entered\n"
				+ "4. List cars in alphabetical order by model\n"
				+ "5. List cars sorted by price (ascending)\n"
				+ "6. List cars sorted by mileage (ascending)\n"
				+ "7. Show car with lowest mileage\n"
				+ "8. Show car with lowest price\n"
				+ "9. Show condition of a selected car (by ID)\n"
				+ "10. Show volume of stock grouped by manufacturer\n"
				+ "11. Delete an entry (by ID)\n"
				+ "12. Delete entire stock list\n\n"
				+ "0. Quit\n\n"
				+ "Enter option number:");
			//Get the entry
			selection=Integer.parseInt(entryHandle(false, false, true, false));			
				
			//Clear the screen
			System.out.print("\033[H\033[2J");
			System.out.flush();
			
			//run with the user's choice from the menu
			switch(selection)
			{
				case 1:{					
					//Load the sample data
					entry.loadSampleData();
					System.out.println("\nSample data loaded successfully.\n");
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 2:{
					//User entry for cars
					entry.setCarData();
					System.out.println("\nData loaded successfully.\n");
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 3:{
					//check if there are cars in stock
					if(stockCheck())
					{
						 //sort the cars in the order they came in and display
						carList.sort(Comparator.comparing(CarShop::getId));
						System.out.println("Displaying vehicles in order they were entered:\n\n");
						displayAllData();
					}
					//Wait so the message is seen
					waitForEnterKey();					
					break;
				}
				case 4:{
					//check if there are cars in stock
					if(stockCheck())
					{
						//Sort the cars in Alphabetical order and display
					    carList.sort(Comparator.comparing(CarShop::getMake));
					    System.out.println("Displaying vehicles in alphabetical order of make:\n\n");
					    displayAllData();
					}
					//Wait so the message is seen
					waitForEnterKey();		
					break;
				}
				case 5:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //sort cars by order of price and display
					    carList.sort(Comparator.comparing(CarShop::getPrice));
					    System.out.println("Displaying vehicles in order of price (ascending):\n\n");
					    displayAllData();
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 6:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //show cars sorted by mileage
					    carList.sort(Comparator.comparing(CarShop::getMileage));
					    System.out.println("Displaying vehicles in order of mileage (ascending):\n\n");
					    displayAllData();
					}
					//Wait so the message is seen
					waitForEnterKey();					
					break;
				}
				case 7:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //show car with lowest mileage by sorting and showing the first
					    carList.sort(Comparator.comparing(CarShop::getMileage));
					    System.out.println("Displaying vehicle with the lowest mileage:\n\n");
					    displaySpecificData(0);
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}              
				case 8:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //show car with lowest price by sorting and showing the first
					    System.out.println("Displaying vehicle with the lowest price:\n\n");
					    carList.sort(Comparator.comparing(CarShop::getPrice));
					    displaySpecificData(0);
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
			}
				case 9:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //Show the condition of a car chosen by the user
					    System.out.printf("Please enter ID of car to see its condition:");
					    selection=Integer.parseInt(entryHandle(false, false, true, false));
					    System.out.println("");
					    entry.showCondition(selection);
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 10:{
					//check if there are cars in stock
					if(stockCheck())
					{
					    //show how many of each make we have
					    displayQuantitiesOfVehicle();
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 11:{
					//check if there are cars in stock
					if(stockCheck())
					{
						//delete item given by id
					    System.out.printf("Please enter ID of car to you wish to delete (enter 0 to abort):");
					    selection=Integer.parseInt(entryHandle(false, false, true, false));
					    if(selection==0)
					    {
					    	//if user entered 0 to abort...
					    	//reset selection so it doesn't terminate the program
					    	selection=-1;
					    	break;
					    }
					    else
					    {
					    	entry.deleteEntry(selection);	
					    }
					    
					}
					//Wait so the message is seen
					waitForEnterKey();
					
					break;
				}
				case 12:{
					//check if there are cars in stock
					if(stockCheck())
					{
						//Check if the user is sure they want to delete the entire stock
					    System.out.printf("Are you sure you wish to delete the entire stock Y/N?:");
					    choice=entryHandle(false, true, false, false).charAt(0);
					    if(choice=='Y'||choice=='y')
					    {
					    	//if user entered Y, delete everything
					    	carList.removeAll(carList);
					    	System.out.println("\nAll entries were deleted\n");
					    	
					    }
					    else
					    {
					    	System.out.println("\nOperation cancelled - no entries were deleted\n");
					    }
					    
					}
					//Wait so the message is seen
					waitForEnterKey();
					break;
				}
				case 0:{
					//user has quit
					System.out.println("Program terminated\n");
					break;
				}
				default:{
					System.out.println("\n***Incorrect value entered!***\n");
					//Wait so the message is seen
					waitForEnterKey();
				}
			}
		}
		while (selection!=0);			
	}

}
