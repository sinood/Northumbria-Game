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
	private JTextField txt2;
	
	public String userText;
	private String prompt2;
	
	private Player p;
	
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
		Area finchel = new Area("Finchel", "FINCHEL DESCRIPTION", this);
		
		Area finchelS = new Area("Wymar's Wares", "WYMAR'S WARES DESCRIPTION", this);

		Area finchelT1 = new Area("The Rusty Swordsman", "THE RUSTY SWORDSMAN DESCRIPTION", this);
		
		Area finchelT2 = new Area("Barret's Taphouse", "BARRET'S TAPHOUSE DESCRIPTION", this);
		
		
		finchel.addExit(finchelS, "West");
		finchel.addExit(finchelT1, "South");
		finchel.addExit(finchelT2, "East");
		
		finchelS.addExit(finchel, "East");
		finchelT1.addExit(finchel, "North");
		finchelT2.addExit(finchel, "West");
		//test input gather
		//promptInput("Type in the box above. ^", "Type here > ");
		//test image set method
		
		
		//add paths
		//add characters
		
		//addPlayer
		p = new Player("Rolf", "Mason", this, finchel);
		//add ...
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
		pnl1.setBounds(0,565,800,50);
		pnl1.setBackground(new Color(100,50,0));
		
		// "west" button
		JButton wButton = new JButton("West");
		wButton.setBounds(0, 590, 100, 25);
		wButton.setBackground(new Color(0,0,0));
		wButton.setFont(new Font("Times New Roman", 10, 16));
		wButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("West");
			}
		});
		
		//"north" button
		JButton nButton = new JButton("North");
		nButton.setBounds(100, 565, 100, 25);
		nButton.setBackground(new Color(0,0,0));
		nButton.setFont(new Font("Times New Roman", 10, 16));
		nButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("North");
			}
		});
		
		//"south" button
		JButton sButton = new JButton("South");
		sButton.setBounds(100, 590, 100, 25);
		sButton.setBackground(new Color(0,0,0));
		sButton.setFont(new Font("Times New Roman", 10, 16));
		sButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("South");
			}
		});
		
		//"east" button
		JButton eButton = new JButton("East");
		eButton.setBounds(200, 590, 100, 25);
		eButton.setBackground(new Color(0,0,0));
		eButton.setFont(new Font("Times New Roman", 10, 16));
		eButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.move("East");
			}
		});
		
		
		JButton menuButton = new JButton("Menu");
		menuButton.setBounds(620, 565, 60, 50);
		menuButton.setBackground(new Color(0,0,0));
		menuButton.setFont(new Font("Times New Roman", 10, 16));
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		JButton mapButton = new JButton("Map");
		mapButton.setBounds(680, 565, 60, 50);
		mapButton.setBackground(new Color(0,0,0));
		mapButton.setFont(new Font("Times New Roman", 10, 16));
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		
		//initial image set
		
		//add(visual);
		
		//creates text area at the bottom of the window
		txt1 = new JTextArea();
		txt1.setBounds(0,635, 800, 150);
		txt1.setBackground(new Color(255,255,255));
		txt1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txt1.setColumns(10);
		
		//sets scrolling function to the text area at the bottom of the window
		sp = new JScrollPane();
		sp.setBounds(0,635, 800, 150);
		sp.setViewportView(txt1); 
		
		txt2 = new JTextField();
		txt2.setBounds(0,615, 800, 20);
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
		frame.getContentPane().add(wButton);
		frame.getContentPane().add(nButton);
		frame.getContentPane().add(sButton);
		frame.getContentPane().add(eButton);
		
		frame.getContentPane().add(menuButton);
		frame.getContentPane().add(mapButton);
		
		//adds scroll function to window, along with the text area
		frame.getContentPane().add(sp);
		
		//adds user input text field to window
		frame.getContentPane().add(txt2);
		
		frame.getContentPane().add(pnl1);
	}
	
	public void play(){
		p.getCurrentArea().printDesc();
		p.getCurrentArea().showExits();
		
	}
	
	//prints text to the scrollable text window from the bottom up
	public void println(String text){
		txt1.append(text + "\n");
	}
	
	public void print(String text){
		txt1.append(text);
	}
	
	//prints a given user input prompt to both the bottom text area and the top text field
	public void promptInput(String prompt1, String prompt2){
		this.prompt2 = prompt2;
		println(prompt1);
		txt2.setText(prompt2);
	}
	
	public String getCurrentInput(){
		return currentInput;
	}
}
