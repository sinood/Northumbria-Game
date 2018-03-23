/*
 * This class serves as the main Northumbria game simulator. It runs the game,
 * creates and updates the display and contents of the game window, stores instances of 
 * every Area and Person, and contains the methods for GUI functions.
 */
import java.awt.EventQueue;

//import java.io.File;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Graphics;
//import java.awt.Component;
import java.awt.Color;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class NorthumbriaGame {

	static NorthumbriaGame theGame;
	private Graphics g;
	
	private JFrame frame;
	public JTextArea txt1;
	private JScrollPane sp;
	public JTextField txt2;
	public JLabel nw;
	public JLabel n;
	public JLabel ne;
	public JLabel w;
	public JLabel you;
	public JLabel e;
	public JLabel sw;
	public JLabel s;
	public JLabel se;
	
	
	private InputCollect ic = new InputCollect(this);
	public String userText;
	public String prompt2 = "";
	
	public static int time = 0;
	
	private Player p;
	
	private LocalMap lm;
	private static String currentInput;
	//images
	//private Image img1 = new Image ("testimg2.JPG");
	
	public void paintComponent(Graphics g){
		
	}
	
	/**
	 * Launches the game.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					theGame = new NorthumbriaGame();
					theGame.frame.setVisible(true);
					theGame.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * THE APPLICATION CONSTRUCTOR
	 */
	public NorthumbriaGame() throws BadLocationException {
		initialize(); //Initialize every time a room is generated
		
		
		
		//add areas
		
		//Town: Finchel
		Area finchel = new Area("Finchel", "You are in a moorish, hillish town with cobble streets "
				+ "and half-timbered houses. While the landscape outside the town contains mostly heath, "
				+ "trees line\nthe streets within.", this); //exterior
		
		//interiors
		Area finchelS = new Area("Wymar's Wares", "You have entered a small general store.", this);

		Area finchelT1 = new Area("The Rusty Swordsman", "You are in a tavern with many elders of Finchel.", this);
		
		Area finchelT2 = new Area("Barret's Taphouse", "You have entered a tavern with many revellers of"
				+ " your own age. You find many of your friends here.", this);
		
		Area finchelB = new Area("Baynard's Breads", "You have entered a bakery run by a familiar face."
				+ " You note the window has always read \"Help Wanted\".", this);
		
		//Exits from Finchel's exterior to its interiors
		finchel.addExit(finchelS, "West");
		finchel.addExit(finchelT1, "South");
		finchel.addExit(finchelT2, "East");
		finchel.addExit(finchelB, "SE");
		
		//Exits from Finchel's interiors to its exterior
		finchelS.addExit(finchel, "East");
		finchelT1.addExit(finchel, "North");
		finchelT2.addExit(finchel, "West");
		finchelB.addExit(finchel, "NW");
		
		//Exits from Finchel's exterior to other exteriors
		
		//Town: Cromfield
		Area cromfield = new Area("Cromfield", "You are in a town surrounded by wheatfields and meadows; "
				+ "and many of the homes have thatched roofs. As you enter, you notice a large horse stable"
				+ " beside the town gate.", this); //exterior
		//interiors
		Area cromfieldS = new Area("the market", "You walk along a row of stalls with farmers selling their produce.", this);
		
		Area cromfieldI = new Area("Hodge's Lodge", "You enter a lively and cozy inn with many new but inviting faces.", this);
		//Exits from Cromfield's exterior to its interiors
		cromfield.addExit(cromfieldS, "East");
		cromfield.addExit(cromfieldI, "North");
		
		//Exits from Cromfield's interior to its exteriors
		cromfieldS.addExit(cromfield, "West");
		cromfieldI.addExit(cromfield, "South");
		
		//Town: Hyne
		Area hyne = new Area("Hyne", "You are in a town nestled in a green valley. You hear the constant "
				+ "sound of a rushing river and wood being sawed. The stone buildings are shaded by large oak "
				+ "trees and sit above paved sidewalks.", this); //exterior
		//interiors
		
		//Exits from Hyne's exterior to its interiors
		
		//Exits from Hyne's interior to its exteriors
		
		
		//Town: Ledbarrow
		
		//interiors
		
		//Exits from Ledbarrow's exterior to its interiors
		
		//Exits from Ledbarrow's interior to its exteriors
		
		
		//Town: Town 5
		
		//interiors
		
		//Exits from 5's exterior to its interiors
		
		//Exits from 5's interior to its exteriors
		
		
		//Inn: Inn name
		
		//exterior
		
		//interior
		
		//Exits from inn's exterior to its interior
		
		//Exits from inn's interior to its exterior
		
		
		//Castle
		
		//Exterior
		
		//interiors
		
		//Exits from castle's exterior to its interior
		
		//Exits from castle's interior to its exterior
		
		//Exits between castle's interiors
		
		
		//Northumbria exteriors
		finchel.addExit(cromfield, "North");
		cromfield.addExit(finchel, "South");
		//Exits between Northumbria's exteriors
		
		
		
		//test input gather
		//promptInput("Type in the box above. ^", "Type here > ");
		//test image set method
		
		//add characters
		
		//addPlayer
		p = new Player("Rolf", "Mason", 100, 100, finchel, this);
		
		//add characters
		
		//add items
		Item bread = new Food("Bread", 3, 5, 10);
		p.addItem(new Food("Steak", 8, 3, 25));
		p.addItem(bread);
		
		lm = new LocalMap(this, p);
	}

	/**
	 * INITIAL FRAME CONTENTS
	 */
	private void initialize() { //MAKE SURE TO PASS Area AS A PARAMETER TO MATCH ROOMS WITH THEIR CORRECT DISPLAYS
		//Creates the initial application window frame
		frame = new JFrame();
		frame.setBounds(500, 500, 800, 810);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(new Color(200,200,200));
		
		JPanel pnl1 = new JPanel();
		pnl1.setBounds(0,565,800,75);
		pnl1.setBackground(new Color(100,50,0));
		
		JPanel pnl2 = new JPanel();
		pnl2.setBounds(5,570,290,65);
		pnl2.setBackground(new Color(200,200,200));
		
		// "west" button
		JButton wButton = new JButton("W");
		wButton.setBounds(300, 590, 50, 25);
		wButton.setBackground(new Color(0,0,0));
		wButton.setFont(new Font("Times New Roman", 10, 16));
		wButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("West");
			}
		});
		
		//"north" button
		JButton nButton = new JButton("N");
		nButton.setBounds(350, 565, 50, 25);
		nButton.setBackground(new Color(0,0,0));
		nButton.setFont(new Font("Times New Roman", 10, 16));
		nButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("North");
			}
		});
		
		//"south" button
		JButton sButton = new JButton("S");
		sButton.setBounds(350, 615, 50, 25);
		sButton.setBackground(new Color(0,0,0));
		sButton.setFont(new Font("Times New Roman", 10, 16));
		sButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("South");
			}
		});
		
		//"east" button
		JButton eButton = new JButton("E");
		eButton.setBounds(400, 590, 50, 25);
		eButton.setBackground(new Color(0,0,0));
		eButton.setFont(new Font("Times New Roman", 10, 16));
		eButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("East");
			}
		});
		
		//"SE" button
		JButton seButton = new JButton("SE");
		seButton.setBounds(400, 615, 50, 25);
		seButton.setBackground(new Color(0,0,0));
		seButton.setFont(new Font("Times New Roman", 10, 16));
		seButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("SE");
			}
		});
		
		//"SW" button
		JButton swButton = new JButton("SW");
		swButton.setBounds(300, 615, 50, 25);
		swButton.setBackground(new Color(0,0,0));
		swButton.setFont(new Font("Times New Roman", 10, 16));
		swButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("SW");
			}
		});
		
		//"NW" button
		JButton nwButton = new JButton("NW");
		nwButton.setBounds(300, 565, 50, 25);
		nwButton.setBackground(new Color(0,0,0));
		nwButton.setFont(new Font("Times New Roman", 10, 15));
		nwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("NW");
			}
		});
		
		//"NW" button
		JButton neButton = new JButton("NE");
		neButton.setBounds(400, 565, 50, 25);
		neButton.setBackground(new Color(0,0,0));
		neButton.setFont(new Font("Times New Roman", 10, 16));
		neButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("NE");
			}
		});
		
		JButton mapButton = new JButton("Map");
		mapButton.setBounds(350, 590, 50, 25);
		mapButton.setBackground(new Color(0,0,0));
		mapButton.setFont(new Font("Times New Roman", 10, 12));
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(450, 565, 75, 75);
		searchButton.setBackground(new Color(0,0,0));
		searchButton.setFont(new Font("Times New Roman", 10, 16));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		JButton waitButton = new JButton("Wait");
		waitButton.setBounds(525, 565, 75, 75);
		waitButton.setBackground(new Color(0,0,0));
		waitButton.setFont(new Font("Times New Roman", 10, 16));
		waitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				promptInput("How many hours? (0-8)");
				ic.waitAction();
				
			}
		});
		
		JButton timeButton = new JButton("Time");
		timeButton.setBounds(600, 565, 75, 75);
		timeButton.setBackground(new Color(0,0,0));
		timeButton.setFont(new Font("Times New Roman", 10, 16));
		timeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println(getTime()); //**can use setText method but this prevents deletion
			}
		});
		
		
		
		JButton menuButton = new JButton("Menu");
		menuButton.setBounds(695, 565, 100, 75);
		menuButton.setBackground(new Color(0,0,0));
		menuButton.setFont(new Font("Times New Roman", 10, 16));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		//Local "map"
		nw = new JLabel();
		nw.setBounds(10, 572, 92, 20);
		nw.setBackground(new Color(255,255,255));
		nw.setOpaque(true);
		nw.setFont(new Font("Times New Roman", 10, 10));
		nw.setHorizontalAlignment(nw.CENTER);
		
		n = new JLabel();
		n.setBounds(103, 572, 94, 20);
		n.setBackground(new Color(255,255,255));
		n.setOpaque(true);
		n.setFont(new Font("Times New Roman", 10, 10));
		n.setHorizontalAlignment(n.CENTER);
		
		ne = new JLabel();
		ne.setBounds(198, 572, 92, 20);
		ne.setBackground(new Color(255,255,255));
		ne.setOpaque(true);
		ne.setFont(new Font("Times New Roman", 10, 10));
		ne.setHorizontalAlignment(ne.CENTER);
		
		w = new JLabel();
		w.setBounds(10, 593, 92, 20);
		w.setBackground(new Color(255,255,255));
		w.setOpaque(true);
		w.setFont(new Font("Times New Roman", 10, 10));
		w.setHorizontalAlignment(w.CENTER);
		
		you = new JLabel();
		you.setBounds(103, 593, 94, 20);
		you.setBackground(new Color(0,0,0));
		you.setFont(new Font("Times New Roman", 10, 10));
		you.setHorizontalAlignment(you.CENTER);
		
		e = new JLabel();
		e.setBounds(198, 593, 92, 20);
		e.setBackground(new Color(255,255,255));
		e.setOpaque(true);
		e.setFont(new Font("Times New Roman", 10, 10));
		e.setHorizontalAlignment(e.CENTER);
		
		sw = new JLabel();
		sw.setBounds(10, 614, 92, 20);
		sw.setBackground(new Color(255,255,255));
		sw.setOpaque(true);
		sw.setFont(new Font("Times New Roman", 10, 10));
		sw.setHorizontalAlignment(sw.CENTER);
		
		s = new JLabel();
		s.setBounds(103, 614, 94, 20);
		s.setBackground(new Color(255,255,255));
		s.setOpaque(true);
		s.setFont(new Font("Times New Roman", 10, 10));
		s.setHorizontalAlignment(s.CENTER);
		
		se = new JLabel();
		se.setBounds(198, 614, 92, 20);
		se.setBackground(new Color(255,255,255));
		se.setOpaque(true);
		se.setFont(new Font("Times New Roman", 10, 10));
		se.setHorizontalAlignment(se.CENTER);
		//initial image set
		
		//add(visual);
		
		//creates text area at the bottom of the window
		txt1 = new JTextArea();
		txt1.setBounds(0,660, 800, 125);
		txt1.setBackground(new Color(255,255,255));
		txt1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txt1.setColumns(10);
		
		//sets scrolling function to the text area at the bottom of the window
		sp = new JScrollPane();
		sp.setBounds(0,660, 800, 125);
		sp.setViewportView(txt1); 
		
		txt2 = new JTextField();
		txt2.setBounds(0,640, 800, 20);
		txt2.setBackground(new Color(255,255,255));
		txt2.setFont(new Font("Courier", Font.BOLD, 12));
		txt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				userText = txt2.getText(); //**
				if(!userText.isEmpty()){
					currentInput = userText.substring(prompt2.length());
					println("> "+ currentInput);
					txt2.setText("");
					prompt2 = "";
				}
			}
		});
		
		
		/**
		 * Adds content to window display
		 */
		
		//removes layout
		frame.getContentPane().setLayout(null);
		
		
		
		//frame.getContentPane().add(visual);
		//MAKE SURE TO ADD CONDITIONS HERE SO THAT DIFFERENT DISPLAYS DO NOT SHOW EVERY BUTTON CREATED******
		//adds buttons
		//condition...
		
		frame.getContentPane().add(nw);
		frame.getContentPane().add(n);
		frame.getContentPane().add(ne);
		frame.getContentPane().add(w);
		frame.getContentPane().add(you);
		frame.getContentPane().add(e);
		frame.getContentPane().add(sw);
		frame.getContentPane().add(s);
		frame.getContentPane().add(se);
		
		frame.getContentPane().add(wButton);
		frame.getContentPane().add(nButton);
		frame.getContentPane().add(sButton);
		frame.getContentPane().add(eButton);
		frame.getContentPane().add(swButton);
		frame.getContentPane().add(nwButton);
		frame.getContentPane().add(neButton);
		frame.getContentPane().add(seButton);
		
		frame.getContentPane().add(menuButton);
		frame.getContentPane().add(mapButton);
		frame.getContentPane().add(timeButton);
		frame.getContentPane().add(waitButton);
		frame.getContentPane().add(searchButton);
		
		//adds scroll function to window, along with the text area
		frame.getContentPane().add(sp);
		
		//adds user input text field to window
		frame.getContentPane().add(txt2);
		
		frame.getContentPane().add(pnl2);
		frame.getContentPane().add(pnl1);
		
	}
	
	//game play method
	public void play(){
		p.getCurrentArea().printDesc();
		p.getCurrentArea().showExits();
		lm.reset();
		
		
	}
	
	//GUI methods
	//prints text to the scrollable text window from the bottom up
	public void println(String text){
		txt1.append(text + "\n");
	}
	
	public void print(String text){
		txt1.append(text);
	}
	
	public JTextField getTextField(){
		return txt2;
	}
	
	//prints a given user input prompt to both the bottom text area and the top text field
	public void promptInput(String prompt1){
		prompt2 = "Type here > ";
		println(prompt1+ " Type in the box above. ^");
		txt2.setText(prompt2);
	}
	
	public String getCurrentInput(){
		return currentInput;
	}
	
	public String getTime(){
		String month = "March";
		int day = 1;
		int hour = 12;
		if(time < 744){
			month = "March";
			day  = (time / 24)+1;
			hour = time % 24;
		} else if(time >= 744 && time < 1464){
			month = "April";
			day  = ((time-744) / 24)+1;
			hour = (time-744) % 24;
		}else if(time >= 1464 && time <= 2208){
			month = "May";
			day  = ((time-1464) / 24)+1;
			hour = (time-1464) % 24;
		} else if(time >= 2208){ //MAKE SURE TO ADD END GAME DAY
			month = "June";
			day  = ((time-2208) / 24)+1;
			hour = (time-2208) % 24;
		}
		return hour + ":00 "+ month + " "+ day + ", 937";
	}
	
	public void wait(String input){
		boolean isNumber = Pattern.matches("[0-9]+", input);
		if(isNumber){
			int amount = Integer.parseInt(input);
			if(amount <= 8){
				time += amount;
				println("Waited "+ amount + " hours.");
			} else{
				println("***You may only wait between 0 and 8 hours at a time.***");
			}
		} else{
			println("***You must type your answer as a positive number of hours (Ex. '3')! Try again.***");
		}
		
	}
}