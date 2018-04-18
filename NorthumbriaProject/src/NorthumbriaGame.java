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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Graphics;
//import java.awt.Component;
import java.awt.Color;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
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
	private JScrollPane helpSp;
	private JPanel exitPnl;
	private JLabel helpLbl;
	private JLabel exitLbl;
	public JTextField txt2;
	public JButton areaButton;
	public JButton helpButton;
	public JButton exitButton;
	public JButton exitNoBtn;
	public JLabel nw;
	public JLabel n;
	public JLabel ne;
	public JLabel w;
	public JLabel you;
	public JLabel e;
	public JLabel sw;
	public JLabel s;
	public JLabel se;
	
	
	
	public String userText;
	public String prompt2 = "";
	
	public static int time = 0;
	
	public Player p;
	
	private LocalMap lm;
	private static String currentInput;
	
	public InputCollect ic;
	
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
	 * @throws IOException 
	 */
	public NorthumbriaGame() throws BadLocationException, IOException {
		//calls method that creates game window components
		initialize();
		

		
		/*
		 * 
		 * AREAS
		 * 
		 */
		
		//Town: Finchel
		Area finchel = new Area("Finchel", "You are in a moorish, hillish town with cobble streets "
				+ "and half-timbered houses. While the landscape outside the town contains mostly heath, "
				+ "trees line\nthe streets within.","exterior",  this); //exterior
		finchel.addItem(new Item("Old ring", 20, 1), 1);
		
		
		//interiors
		Area wymarsWares = new Area("Wymar's Wares", "You enter a small general store.","shop",  this);
		
		
		Area rustySwordsman = new Area("The Rusty Swordsman", "You are in a tavern with many elders of Finchel.","tavern",  this);
		
		Area barretsTaphouse = new Area("Barret's Taphouse", "You enter a tavern in which many of your "
				+ "friends like to meet.","tavern",  this);
		
		Area baynardsBreads = new Area("Baynard's Breads", "You enter a bakery run by a familiar face."
				+ " You note the window has always read \"Help Wanted\".","trade",  this);
		
		//Exits from Finchel's exterior to its interiors
		finchel.addExit(wymarsWares, "East");
		finchel.addExit(rustySwordsman, "South");
		finchel.addExit(barretsTaphouse, "West");
		finchel.addExit(baynardsBreads, "SE");
		
		//Exits from Finchel's interiors to its exterior
		wymarsWares.addExit(finchel, "West");
		rustySwordsman.addExit(finchel, "North");
		barretsTaphouse.addExit(finchel, "East");
		baynardsBreads.addExit(finchel, "NW");
		
		//Exits from Finchel's exterior to other exteriors
		
		//Town: Cromfield
		Area cromfield = new Area("Cromfield", "You are in a town surrounded by wheatfields and meadows; "
				+ "and many of the homes have thatched roofs. As you enter, you notice a large horse stable"
				+ " beside the town gate.","exterior", this); //exterior
		//interiors
		Area market = new Area("the market", "You walk along a row of market stands with farmers selling their produce.","shop" , this);
		
		Area hodgesLodge = new Area("Hodge's Lodge", "You enter a lively and cozy inn with many new but inviting faces.","inn",  this);
		
		Area flyingScabbard = new Area("The Flying Scabbard", "You enter an old tavern; you see a warm fire "
				+ "crackling at the stone hearth.","tavern", this);
		
		Area cromfieldStables = new Area("Cromfield Stables", "You walk into a stable and see horse heads "
				+ "peeking out of their stalls.","shop", this);
		
		//Exits from Cromfield's exterior to its interiors
		cromfield.addExit(market, "East");
		cromfield.addExit(hodgesLodge, "North");
		cromfield.addExit(flyingScabbard, "South");
		cromfield.addExit(cromfieldStables, "West");
		
		//Exits from Cromfield's interior to its exteriors
		market.addExit(cromfield, "West");
		hodgesLodge.addExit(cromfield, "South");
		flyingScabbard.addExit(cromfield, "North");
		cromfieldStables.addExit(cromfield, "East");
		
		//Town: Hyne
		Area hyne = new Area("Hyne", "You are in a town nestled in a green valley. You hear the constant "
				+ "sound of a rushing river and wood being sawed. The stone buildings are shaded by large oak "
				+ "trees and sit above paved sidewalks.","exterior",  this); //exterior
		
		//interiors
		Area riverMill = new Area("The River Mill", "You walk onto the platform of a large sawmill. You notice the laborers "
				+ "lifting the heavy logs -- they could use some help.", "trade", this);
		
		Area gerboldsBasket = new Area("Gerbold's Basket", "You enter a grocery shop containing many foreign fruits for "
				+ "sale.","shop",this);
		
		Area badgersDen = new Area("The Badger's Den", "You enter a relatively quiet tavern, and walk past a large group"
				+ " of gamblers arguing with a shifty-looking figure","tavern",this);
		
		Area gerardsCuts = new Area("Gerard's Cuts", "You enter a small butcher shop.","shop",this);
		
		//Exits from Hyne's exterior to its interiors
		hyne.addExit(riverMill, "South");
		hyne.addExit(gerboldsBasket, "SE");
		hyne.addExit(badgersDen, "North");
		hyne.addExit(gerardsCuts, "NW");
		
		//Exits from Hyne's interior to its exteriors
		riverMill.addExit(hyne, "North");
		gerboldsBasket.addExit(hyne, "NW");
		badgersDen.addExit(hyne, "South");
		gerardsCuts.addExit(hyne, "SE");
		
		//Town: Ledbarrow
		Area ledbarrow = new Area("Ledbarrow", "You are in a small town among the foothills of a nearby mountain range."
				+ " A stream runs through lopsided brick buildings along the edge of a sloped forest.","exterior",this);//exterior
		
		//interiors
		Area rockAndStone = new Area("Rock and Stone", "You enter a general store with an odd collection of objects "
				+ "for sale.","shop",this);
		
		Area randallsAnvil = new Area("Randall's Anvil", "You enter a smoky smithy and hear a large man pounding away at "
				+ "a blazing shard of metal. He has no apprentice to help him.","trade",this);
		
		Area tildasTrinkets = new Area("Tilda's Trinkets","You enter a whitewashed store with glistening jewelry on display.",
				"shop",this);
		
		Area creekHouse = new Area("The Creek House","You step into a small but nicely furnished inn overlooking "
				+ "the bank of a creek.","inn",this);
		
		//Exits from Ledbarrow's exterior to its interiors
		ledbarrow.addExit(rockAndStone, "East");
		ledbarrow.addExit(randallsAnvil, "NE");
		ledbarrow.addExit(tildasTrinkets, "NW");
		ledbarrow.addExit(creekHouse, "West");
		
		//Exits from Ledbarrow's interior to its exteriors
		rockAndStone.addExit(ledbarrow, "West");
		randallsAnvil.addExit(ledbarrow, "SW");
		tildasTrinkets.addExit(ledbarrow, "SE");
		creekHouse.addExit(ledbarrow, "East");
		
		//Town: Deephollow
		Area deephollow = new Area("Deephollow", "You are in an shady, isolated town in an oak forest glade. Smoke rises above the "
				+ "treetops from chimneys atop elaborate half-timbered houses.","exterior", this); //exterior
		
		//interiors
		Area forestForages = new Area("Forest Forages","You enter a shop that sells many local items and foods.","shop",this);
		
		Area celinesSilks = new Area("Celine's Silks","You walk through a textile store with many fine fabrics,"
				+ " drapes, and tapestries.","shop",this);
		
		Area sybilsPotions = new Area("Sybil's Potions","You enter a tiny shop and find an apothecary "
				+ "frantically rummaging through shelves of strange fungi and ingredients.","trade",this);
		
		Area shadedHearth = new Area("The Shaded Hearth","You enter a large inn with a massive fireplace where something "
				+ "cooks below a spit.","inn",this);
		
		Area mossyLog = new Area("The Mossy Log","You step into a noisy cellar with a stone foundation and many "
				+ "wooden benches upon which a variety of stragners sit and drink.","shop",this);
		
		
		//Exits from Deephollow's exterior to its interiors
		deephollow.addExit(forestForages, "East");
		deephollow.addExit(celinesSilks, "NE");
		deephollow.addExit(sybilsPotions, "North");
		deephollow.addExit(shadedHearth, "NW");
		deephollow.addExit(mossyLog, "SW");
		
		//Exits from Deephollow's interior to its exteriors
		forestForages.addExit(deephollow, "West");
		celinesSilks.addExit(deephollow, "SW");
		sybilsPotions.addExit(deephollow, "South");
		shadedHearth.addExit(deephollow, "SE");
		mossyLog.addExit(deephollow, "NE");
		
		//Inn: Inn name
		Area innCrossing = new Area("Inn Crossing", "You arrive at a major crossroads with a large inn by a "
				+ "bridge over the river.","exterior",this);//exterior
		
		//interior
		Area fourDaughters = new Area("The Four Daughters Lodge","You enter a lively and strange inn full of all kinds"
				+ " of travellers. It is best to keep a watchful eye here.","inn",this);
		
		//Exits from inn's exterior to its interior
		innCrossing.addExit(fourDaughters, "East");
		//Exits from inn's interior to its exterior
		fourDaughters.addExit(innCrossing, "West");
		
		//Castle
		
		//Exterior
		
		//interiors
		
		//Exits from castle's exterior to its interior
		
		//Exits from castle's interior to its exterior
		
		//Exits between castle's interiors
		
		
		//Northumbria exteriors
		Area darkwoodCrossing = new Area("Darkwood Crossing","You come arrive at a crossing near Darkwood forest. One "
				+ "path leads into the wilderness -- toward a dark and faraway castle.","exterior",this);
		
		//Exits between Northumbria's exteriors
		finchel.addExit(cromfield, "NE");
		cromfield.addExit(finchel, "SW");
		
		finchel.addExit(innCrossing, "North");
		innCrossing.addExit(finchel, "South");
		
		finchel.addExit(ledbarrow, "NW");
		ledbarrow.addExit(finchel, "SE");
		
		innCrossing.addExit(cromfield, "SE");
		cromfield.addExit(innCrossing, "NW");
		
		innCrossing.addExit(hyne, "NE");
		hyne.addExit(innCrossing, "SW");
		
		innCrossing.addExit(deephollow, "North");
		deephollow.addExit(innCrossing, "South");
		
		innCrossing.addExit(darkwoodCrossing, "West");
		darkwoodCrossing.addExit(innCrossing, "East");
		
		ledbarrow.addExit(darkwoodCrossing, "North");
		darkwoodCrossing.addExit(ledbarrow, "South");
		
		
		
		/*
		 * 
		 * PLAYER, NPCs, AND THEIR ITEMS
		 * 
		 * NPC, FRIEND construction:
		 * NPC(String name, int money, int health, int combat, boolean isVendor, Area area, NorthumbriaGame game)
		 * 
		 * Item construction:
		 * Item(String name, int value, int amount)
		 * Food:
		 * Food(String name, int value, int amount, int health)
		 * Horse:
		 * Horse (String name, int value, int amount, int speed, int health)
		 * 
		 */
		
		//Add the player
		p = new Player("Rolf", "Mason", 100, 100, finchel, this);
		
		//NPC vendors
		NPC wymar = new Friend("Wymar", 200, 100, 90, true, wymarsWares, this);
		wymar.addItem(new Item("Coal", 4, 12), 12);
		wymar.addItem(new Food("Carrot", 2, 7, 5), 7);
		wymar.addItem(new Food("Hardegin Mead", 5, 3, 2), 3);
		wymar.addItem(new Item("Shears", 10, 1), 1);
		
		NPC baynard = new NPC("Baynard", 300, 100, 70, true, baynardsBreads, this);
		baynard.addItem(new Food("Bread", 3, 30, 10), 30);
		baynard.addItem(new Food("Baynard's Sourdough", 5, 30, 15), 30);
		baynard.addItem(new Item("Flour", 3, 50), 50);
		baynard.addItem(new Food("Cake Slice", 5, 10, 12), 10);
		baynard.addItem(new Food("Pretzel", 4, 40, 15), 40);
		baynard.addItem(new Food("Berry Tart", 8, 10, 12), 10);
		baynard.addItem(new Food("Apple Pie", 10, 10, 14), 10);
		
		NPC eugene = new NPC("Eugene", 150, 100, 50, true, rustySwordsman, this);
		eugene.addItem(new Food("Hardegin Mead", 5, 10, 5), 10);
		eugene.addItem(new Food("Old Forest Mead", 6, 15, 6), 15);
		eugene.addItem(new Food("Finchel Red Wine", 8, 15, 8), 15);
		eugene.addItem(new Food("Dragonfire Brandy", 10, 10, 12), 10);
		eugene.addItem(new Food("Hyne Vineyard Wine", 9, 15, 10), 15);
		eugene.addItem(new Food("Bread", 3, 20, 10), 20);
		
		NPC barret = new NPC("Barret", 170, 100, 90, true, barretsTaphouse, this);
		barret.addItem(new Food("Hardegin Mead", 5, 20, 5), 20);
		barret.addItem(new Food("Greywolf Beer", 4, 20, 4), 20);
		barret.addItem(new Food("Home-brewed Mead", 3, 10, 3), 10);
		barret.addItem(new Food("Dragonfire Brandy", 10, 8, 12), 8);
		barret.addItem(new Food("Slavic Plum Brandy", 18, 4, 20), 4);
		barret.addItem(new Food("Bread", 3, 15, 10), 15);
		barret.addItem(new Food("Cheddar Cheese",4, 10, 11), 10);
		
		//NPC non-vendors
		NPC jayred = new Friend("Jayred", 30, 100, 100, false, barretsTaphouse, this);
		
		NPC oldManHawkin = new NPC("Old Man Hawkin", 10, 80, 20, false, finchel, this);
		
		//temporary friend with which to text the game
		p.addFriend(new Friend("Fred", 20, 100, 100,false,finchel,this));
		
		/*
		 * 
		 * ITEMS
		 * 
		 */
		//temporary items with which to text the game
		Food bread = new Food("Bread", 3, 5, 10);
		p.addItem(new Food("Steak", 8, 3, 25));
		p.addItem(bread);
		
		
		//initializes the local map
		lm = new LocalMap(this, p);
		//initializes input collection
		ic = new InputCollect(this, p);
		
		
	}

	
	//constructs the game's interface
	private void initialize() throws IOException {
		//Creates the initial application window frame
		
		/*
		 * INITIAL FRAME CONTENTS
		 */
	
		//window frame
		frame = new JFrame();
		frame.setBounds(500, 500, 800, 810);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(200,200,200));
		
		//backdrop for the dashboard
		JPanel pnl1 = new JPanel();
		pnl1.setBounds(0,565,800,75);
		pnl1.setBackground(new Color(100,50,0));
		
		//backdrop for the local map
		JPanel pnl2 = new JPanel();
		pnl2.setBounds(5,570,290,65);
		pnl2.setBackground(new Color(200,200,200));
		

		//text area at the bottom of the window
		txt1 = new JTextArea();
		txt1.setBounds(0,660, 800, 125);
		txt1.setBackground(new Color(255,255,255));
		txt1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txt1.setColumns(10);
		
		//adds scrolling to the text area at the bottom of the window
		sp = new JScrollPane();
		sp.setBounds(0,660, 800, 125);
		sp.setViewportView(txt1); 
		
		//text field which takes in user input
		txt2 = new JTextField();
		txt2.setBounds(0,640, 800, 20);
		txt2.setBackground(new Color(255,255,255));
		txt2.setFont(new Font("Courier", Font.BOLD, 12));
		txt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				ic.defaultAction();
			}
		});
		
		
		/*
		 * MOVEMENT BUTTONS
		 */
		
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
		
		/*
		 * OTHER DASHBOARD BUTTONS
		 */
		
		//displays the map
		JButton mapButton = new JButton("Map");
		mapButton.setBounds(350, 590, 50, 25);
		mapButton.setBackground(new Color(0,0,0));
		mapButton.setFont(new Font("Times New Roman", 10, 12));
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println("Testing Button 2"); //**can use setText method but this prevents deletion
			}
		});
		
		//initiates search function for the current area
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(450, 565, 75, 37);
		searchButton.setBackground(new Color(0,0,0));
		searchButton.setFont(new Font("Times New Roman", 10, 16));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.getCurrentArea().transferItems(p);
				p.getCurrentArea().showNPC();
			}
		});
		
		//allows the player to input an amount of time for which to wait
		areaButton = new JButton("Wait");
		areaButton.setBounds(450, 602, 75, 37);
		areaButton.setBackground(new Color(0,0,0));
		areaButton.setFont(new Font("Times New Roman", 10, 16));
		areaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				promptInput("How many hours? (0-8)");
				ic.waitAction();
				
			}
		});
		
		//prints the current game time
		JButton timeButton = new JButton("Time");
		timeButton.setBounds(525, 565, 75, 37);
		timeButton.setBackground(new Color(0,0,0));
		timeButton.setFont(new Font("Times New Roman", 10, 16));
		timeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				println(getTime()); //**can use setText method but this prevents deletion
			}
		});
		
		//prints the player's inventory
		JButton invButton = new JButton("Inventory");
		invButton.setBounds(525, 602, 75, 37);
		invButton.setBackground(new Color(0,0,0));
		invButton.setFont(new Font("Times New Roman", 10, 16));
		invButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				p.printInventory();
			}
		});
		
		//displays exit menu
		exitButton = new JButton("Exit");
		exitButton.setBounds(700, 565, 95, 75);
		exitButton.setBackground(new Color(0,0,0));
		exitButton.setFont(new Font("Times New Roman", 10, 16));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				addExitMenu();
			}
		});
		
		//displays help menu
		helpButton = new JButton("Help");
		helpButton.setBounds(605, 565, 95, 75);
		helpButton.setBackground(new Color(0,0,0));
		helpButton.setFont(new Font("Times New Roman", 10, 16));
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				addHelp();
			}
		});
		
		
		/*
		 * MENUs
		 */
		
		//Exit menu text
		JLabel confirmLbl = new JLabel();
		confirmLbl.setText("Are you sure you want to exit the game?"
				+ "\nYou may not save your progress.");
		confirmLbl.setForeground(new Color(0,0,0));
		confirmLbl.setBackground(new Color(255,255,255));
		confirmLbl.setOpaque(true);
		confirmLbl.setFont(new Font("Times New Roman", 10, 16));
		confirmLbl.setHorizontalAlignment(n.CENTER);
		
		//exits the game
		JButton exitYesBtn = new JButton("Yes");
		exitYesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitYesBtn.setFont(new Font("Times New Roman", 10, 16));
		
		//hides the exit menu
		exitNoBtn = new JButton("No");
		exitNoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeExitMenu();
			}
		});
		exitNoBtn.setFont(new Font("Times New Roman", 10, 16));
		
		//exit menu label
		exitLbl = new JLabel();
		exitLbl.setText("EXIT");
		exitLbl.setBounds(150, 150, 500, 20);
		exitLbl.setForeground(new Color(255,255,255));
		exitLbl.setBackground(new Color(100,50,0));
		exitLbl.setOpaque(true);
		exitLbl.setFont(new Font("Times New Roman", 10, 16));
		exitLbl.setHorizontalAlignment(n.CENTER);
		
		//exit menu body
		exitPnl = new JPanel();
		exitPnl.setBounds(150, 170, 500, 75);
		exitPnl.setBackground(new Color(255,255,255));
		exitPnl.setFont(new Font("Times New Roman", Font.BOLD, 12));
		
		//adds exit menu components to the body
		exitPnl.add(confirmLbl);
		exitPnl.add(exitYesBtn);
		exitPnl.add(exitNoBtn);
		
		//help menu label
		helpLbl = new JLabel();
		helpLbl.setText("HELP");
		helpLbl.setBounds(400, 345, 400, 20);
		helpLbl.setForeground(new Color(255,255,255));
		helpLbl.setBackground(new Color(100,50,0));
		helpLbl.setOpaque(true);
		helpLbl.setFont(new Font("Times New Roman", 10, 16));
		helpLbl.setHorizontalAlignment(n.CENTER);
		
		//help menu text area
		JTextArea helpTxt = new JTextArea();
		helpTxt.setBounds(400, 365, 400, 200);
		helpTxt.setBackground(new Color(255,255,255));
		helpTxt.setFont(new Font("Times New Roman", Font.BOLD, 12));
		helpTxt.setColumns(10);
		
		//reads the help menu text from the "Help.txt" file into the help menu text area
		JFileChooser fc = new JFileChooser();
		File helpFile = new File("Help.txt");
		BufferedReader bf = new BufferedReader(new FileReader(helpFile));
		String line = bf.readLine();
		while(line != null){
			helpTxt.append(line + "\n");
			line = bf.readLine();
		}
		
		//adds scrolling to help menu text area
		helpSp = new JScrollPane();
		helpSp.setBounds(400, 365, 400, 200);
		helpTxt.setCaretPosition(0);
		helpSp.setViewportView(helpTxt);
		
		
		/*
		 * LOCAL MAP
		 */
		
		//displays the Area in the northwest direction
		nw = new JLabel();
		nw.setBounds(10, 572, 92, 20);
		nw.setForeground(new Color(150,20,0));
		nw.setBackground(new Color(255,255,255));
		nw.setOpaque(true);
		nw.setFont(new Font("Times New Roman", 10, 10));
		nw.setHorizontalAlignment(nw.CENTER);
		
		//displays the Area in the north direction
		n = new JLabel();
		n.setBounds(103, 572, 94, 20);
		n.setForeground(new Color(150,20,0));
		n.setBackground(new Color(255,255,255));
		n.setOpaque(true);
		n.setFont(new Font("Times New Roman", 10, 10));
		n.setHorizontalAlignment(n.CENTER);
		
		//displays the Area in the northeast direction
		ne = new JLabel();
		ne.setBounds(198, 572, 92, 20);
		ne.setForeground(new Color(150,20,0));
		ne.setBackground(new Color(255,255,255));
		ne.setOpaque(true);
		ne.setFont(new Font("Times New Roman", 10, 10));
		ne.setHorizontalAlignment(ne.CENTER);
		
		//displays the Area in the west direction
		w = new JLabel();
		w.setBounds(10, 593, 92, 20);
		w.setForeground(new Color(150,20,0));
		w.setBackground(new Color(255,255,255));
		w.setOpaque(true);
		w.setFont(new Font("Times New Roman", 10, 10));
		w.setHorizontalAlignment(w.CENTER);
		
		//displays the current Area
		you = new JLabel();
		you.setBounds(103, 593, 94, 20);
		you.setBackground(new Color(0,0,0));
		you.setFont(new Font("Times New Roman", 10, 10));
		you.setHorizontalAlignment(you.CENTER);
		
		//displays the Area in the east direction
		e = new JLabel();
		e.setBounds(198, 593, 92, 20);
		e.setForeground(new Color(150,20,0));
		e.setBackground(new Color(255,255,255));
		e.setOpaque(true);
		e.setFont(new Font("Times New Roman", 10, 10));
		e.setHorizontalAlignment(e.CENTER);
		
		//displays the Area in the southwest direction
		sw = new JLabel();
		sw.setBounds(10, 614, 92, 20);
		sw.setForeground(new Color(150,20,0));
		sw.setBackground(new Color(255,255,255));
		sw.setOpaque(true);
		sw.setFont(new Font("Times New Roman", 10, 10));
		sw.setHorizontalAlignment(sw.CENTER);
		
		//displays the Area in the south direction
		s = new JLabel();
		s.setBounds(103, 614, 94, 20);
		s.setForeground(new Color(150,20,0));
		s.setBackground(new Color(255,255,255));
		s.setOpaque(true);
		s.setFont(new Font("Times New Roman", 10, 10));
		s.setHorizontalAlignment(s.CENTER);
		
		//displays the Area in the southeast direction
		se = new JLabel();
		se.setBounds(198, 614, 92, 20);
		se.setForeground(new Color(150,20,0));
		se.setBackground(new Color(255,255,255));
		se.setOpaque(true);
		se.setFont(new Font("Times New Roman", 10, 10));
		se.setHorizontalAlignment(se.CENTER);
		
		
		/*
		 * The following adds all of the above components to window container
		 */
		
		//removes layout
		frame.getContentPane().setLayout(null);
		
		frame.getContentPane().add(exitPnl);
		frame.getContentPane().add(exitLbl);
		frame.getContentPane().remove(exitPnl);
		frame.getContentPane().remove(exitLbl);

		frame.getContentPane().add(helpSp);
		frame.getContentPane().remove(helpSp);
		frame.getContentPane().add(helpLbl);
		frame.getContentPane().remove(helpLbl);
		
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
		
		frame.getContentPane().add(exitButton);
		frame.getContentPane().add(helpButton);
		//frame.getContentPane().add(mapButton);
		frame.getContentPane().add(timeButton);
		frame.getContentPane().add(areaButton);
		frame.getContentPane().add(searchButton);
		frame.getContentPane().add(invButton);
		
		frame.getContentPane().add(sp);
		
		frame.getContentPane().add(txt2);
		
		frame.getContentPane().add(pnl2);
		frame.getContentPane().add(pnl1);
		
		
	}
	
	/*
	 * INTERFACE METHODS
	 */
	
	//sets exit button function to display exit menu
	public void addExitMenu(){
		removeButtonAction(exitButton);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				frame.getContentPane().add(exitPnl);
				frame.getContentPane().add(exitLbl);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				exitNoBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { //generates output upon click of the button
						removeExitMenu();
					}
				});
			}
		});
	}
	
	//sets "no" button function in exit menu to hide exit menu
	public void removeExitMenu(){
		removeButtonAction(exitNoBtn);
		exitNoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				frame.getContentPane().remove(exitPnl);
				frame.getContentPane().remove(exitLbl);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				addExitMenu();
			}
		});
	}
	
	//sets help button function to display help menu
	public void addHelp(){
		removeButtonAction(helpButton);
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				frame.getContentPane().add(helpSp);
				frame.getContentPane().add(helpLbl);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				removeHelp();
			}
		});
	}
	
	//sets help button function to hide help menu
	public void removeHelp(){
		removeButtonAction(helpButton);
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				frame.getContentPane().remove(helpSp);
				frame.getContentPane().remove(helpLbl);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				addHelp();
			}
		});
	}
	
	//prints text to the scrollable text window from the bottom up
	public void println(String text){//prints a line
		txt1.append(text + "\n");
	}
	public void print(String text){//prints on the same line
		txt1.append(text);
	}
	
	//getter method for user input text field
	public JTextField getTextField(){
		return txt2;
	}
	
	//prints a given prompt to both the bottom text area and the top text field
	public void promptInput(String prompt1){
		prompt2 = "Type here > ";
		println(prompt1+ " Type in the box above. ^");
		txt2.setText(prompt2);
	}
	
	//getter method for the input String in the user text field
	public String getCurrentInput(){
		return currentInput;
	}
	
	//processes the value of the time variable into a calendar time
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
		return hour + ":00 "+ month + " "+ day + ", 937 AD";
	}
	
	//returns the hour of the current day
	public int getHour(){
		return time % 24;
	}
	
	//returns the current day
	public int getDay(){
		return (time / 24)+1;
	}
	
	//increments time by the given amount of hours and prints a response
	public void wait(String input){
		boolean isNumber = Pattern.matches("[0-9]+", input);
		if(isNumber){
			int amount = Integer.parseInt(input);
			if(amount <= 8){
				time += amount;
				println("You waited "+ amount + " hours.");
			} else{
				println("***You may only wait between 0 and 8 hours at a time.***");
			}
		} else{
			println("***You must type your answer as a positive number of hours (Ex. '3')! Try again.***");
		}
		ic.defaultAction();//ends wait action; resets text field to default user input
	}
	
	//clears action listeners from a button
	public void removeButtonAction(JButton j){
		for(int i = 0; i < j.getActionListeners().length;i++){
			j.removeActionListener(j.getActionListeners()[i]);
		}
	}
	
	//clears action listeners from a text field
	public void removeTextAction(JTextField t){
		for(int i = 0; i < t.getActionListeners().length;i++){
			t.removeActionListener(t.getActionListeners()[i]);
		}
	}
	
	
	//initial game play method
		public void play(){
			p.getCurrentArea().printDesc();
			lm.reset();
		}
}