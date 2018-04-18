import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;


public class InputCollect {
	
	private ArrayList<String> keyWords = new ArrayList<String>();
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private ArrayList<ArrayList<Item>> inventory;
	private ArrayList<Item> goods;
	private String userText;
	
	NorthumbriaGame game;
	Player p;
	
	public InputCollect(NorthumbriaGame game, Player p){
		this.game = game;
		this.p = p;
		friends = p.friends;
		inventory = p.inventory;
		addKeyWord(p.getName());//delete later
		addKeyWord("eat drink consume show inspect give drop");
		
	}
	
	public void addKeyWord(String input){
		Scanner sc = new Scanner(input);
		while(sc.hasNext()){
			keyWords.add(sc.next().toLowerCase());
		}
		sc.close();
	}
	
	public String locateKeyWord(String input){ //searches user input for a key word and returns that word
		for(int i =0; i< keyWords.size();i++){
			if(input.contains(keyWords.get(i).toLowerCase())){
				return keyWords.get(i);
			}
		}
		return "";
	}
	
	public Friend locateFriend(String input){
		friends = p.friends;
		for(int i =0; i<friends.size();i++){
			if(input.contains(friends.get(i).getName().toLowerCase())){
				return friends.get(i);
			}
		}
		return null;
	}
	
	public Item locateItem(String input){
		inventory = p.inventory;
		for(int i = 0; i < inventory.size();i++){
			for(int j = 0; j < inventory.get(i).size();j++){
				if(input.contains(inventory.get(i).get(j).getName().toLowerCase())){
					return inventory.get(i).get(j);
				}
			}
		}
		return null;
	}
	
	public Item locateSellerItem(String input){
		goods = p.getCurrentArea().getVendor().getItems();
		for(int i = 0; i < goods.size();i++){
			if(input.contains(goods.get(i).getName().toLowerCase())){
				return goods.get(i);
			}
		}
		return null;
	}
	
	public int locateInt(String input){
		Scanner sc = new Scanner(input);
		while(sc.hasNext()){
			if(sc.hasNextInt()){
				int x = sc.nextInt();
				sc.close();
				return x;
			}
			sc.next();
		}
		sc.close();
		return 0;
	}
	
	//executes the appropriate action based on the located keyword provided by the user
	public void processCommand(String input){
		input = input.toLowerCase();
		String key = locateKeyWord(input);
		Friend f;
		Item item;
		
		if(key != null && key.equalsIgnoreCase(p.getName())){
			game.println(showSelf());
		}else if(key != null && (key.equalsIgnoreCase("eat") || key.equalsIgnoreCase("consume") || key.equalsIgnoreCase("drink"))){
			item = locateItem(input);
			int n = locateInt(input);
			if(item != null && item instanceof Food){
				if(n > 0){
					for(int i = 0; i < inventory.get(1).size();i++){
						if(inventory.get(1).get(i) == item){
							p.consume((Food)item, n);
						}
					}
				} else {
					p.consume((Food)item, 1);
				}
			} else {
				game.println("I cannot consume that.");
			}
			
		}else if (key != null && (key.equalsIgnoreCase("show") || key.equalsIgnoreCase("inspect") )){
			item = locateItem(input);
			if(item != null){
				for(int i = 0; i < inventory.size();i++){
					for(int j = 0; j < inventory.get(i).size();j++){
						if(inventory.get(i).get(j) == item){
							game.println(item.getStats());
						}
					}
				}
			} else {
				game.println("I cannot inspect that.");
			}
			
		}else if (key != null && key.equalsIgnoreCase("give")){
			item = locateItem(input);
			for(int i = 0; i < inventory.get(1).size();i++){
				if(inventory.get(1).get(i) == item){
					item = (Food)item;
				}
			}
			f = locateFriend(input);
			int n = locateInt(input);
			if(item != null && item instanceof Food && f != null){
				if(n > 0){
					p.giveFood((Food)item, n, f);
				} else {
					p.giveFood((Food)item, 1, f);
				}
			} else {
				game.println("I cannot give that item.");
			}
		}else if(key != null && key.equalsIgnoreCase("drop")){
			item = locateItem(input);
			int n = locateInt(input);
			if(item != null){
				if(n > 0){
					p.dropItem(item,n);
				}else{
					p.dropItem(item,1);
				}
			}
		}else if(key != null && locateFriend(input) != null){
			f = locateFriend(input);
				game.println(f.showStats());
		} else {
			game.println("Huh?");
		}
	}
	
	public String showSelf(){
		return p.showStats();
	}
	
	//sets the text field to take in general user commands when not trading, resting, etc.
	public void defaultAction(){
		game.getTextField().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				userText = game.getTextField().getText(); //**
				if(!userText.isEmpty()){
					userText = userText.substring(game.prompt2.length());
					game.println("> "+ userText);//just prints input to text area
					processCommand(userText);
					game.getTextField().setText("");
					game.prompt2 = "";
				}
			}
		});
	}
	
	//sets the user input text field to expect an integer value and returns that value
	public int getInt(){
		int x = 0;
		game.getTextField().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				userText = game.getTextField().getText(); //**
				if(!userText.isEmpty()){
					userText = userText.substring(game.prompt2.length());
					int x = locateInt(userText);
					game.getTextField().setText("");
					game.prompt2 = "";
				}
			}
		});
		return x;
	}
	
	//prompts user to provide an amount of hours to wait and sets user input text field to expect that integer value
	public void waitAction(){
		game.getTextField().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				userText = game.getTextField().getText(); //**
				if(!userText.isEmpty()){
					userText = userText.substring(game.prompt2.length());
					game.wait(userText);
					game.getTextField().setText("");
					game.prompt2 = "";
				}
			}
		});
	}
	
	
	public void tradeAction(){
		game.getTextField().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //generates output upon click of the button
				userText = game.getTextField().getText().substring(game.prompt2.length()); //**
				Item item = locateItem(userText);
				Item good = locateSellerItem(userText);
				int n = locateInt(userText);
				NPC npc = p.getCurrentArea().getVendor();
				if(n > 0){
					if(userText.contains("sell") && item != null){
						if(n > 0){
							sell(item, n ,npc);
						} else{
							game.println(npc.getName().toUpperCase()+ ": \"You haven't said how many!\"");
						}
					} else if((userText.contains("buy")||userText.contains("purchase")) && good != null){
						if(n > 0){
							buy(good, n ,npc);
						} else{
							game.println(npc.getName().toUpperCase()+ ": \"You haven't said how many!\"");
						}
					}else{
						game.println(npc.getName().toUpperCase()+ ": \"I don't have that!\"");
					}
				}
				
				game.getTextField().setText("");
				game.prompt2 = "";
				
			}
		});
	}
	
	public void sell(Item item, int amount, NPC seller){
		int price = ((int)(item.getValue()*0.75)*amount);
		item = p.getItem(item.getName());
		if(price <= seller.getMoney() && item != null){
			seller.addItem(item, amount);
			p.remove(item, amount);
			p.addMoney(price);
			seller.loseMoney(price);
			game.println("You sold "+item.getName() + " (x"+amount+")");
		}
		defaultAction();
	}
	

	
	public void buy(Item item, int amount, NPC seller){
		int price = ((int)(item.getValue()*1.25)*amount);
			if(price <= p.getMoney()){
				seller.removeItem(item.getName(), amount);
				p.addItem(item, amount);
				p.loseMoney(price);
				seller.addMoney(price);
				game.println("You purchased "+item.getName() + " (x"+amount+")");
			}
		defaultAction();
	}
}
