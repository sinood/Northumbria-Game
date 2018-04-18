/*
 * This class creates an Area which abstracts Northumbria's Town and Room type areas.
 * Namely, the class takes, sets, and returns an area's name, description, displayed image
 * and available connections to other areas.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Area {
	protected String name;
	protected String desc;
	private String type;
	public ArrayList<Exit> exits;
	private ArrayList<Item> items;
	private ArrayList<NPC> npcs;
	private NorthumbriaGame game;
	
	
	public Area(String name, String desc, String type, NorthumbriaGame game){
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.game = game;
		exits = new ArrayList<Exit>();
		items = new ArrayList<Item>();
		npcs = new ArrayList<NPC>();
	}
	
	public String getName(){
		return name;
	}
	
	public void printDesc(){
		game.println(name.toUpperCase() + ": "+ desc);
	}
	
	public String  setDesc(String desc){
		this.desc = desc;
		return desc;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setFunction(){
		game.removeButtonAction(game.areaButton);
		if (type.equals("exterior")){
			game.areaButton.setText("Wait");
			game.areaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //generates output upon click of the button
					game.promptInput("How many hours? (0-8)");
					game.ic.waitAction();
					
				}
			});
		}else if(type.equals("shop") || type.equals("tavern")){
			game.areaButton.setText("Trade");
			
			game.areaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //generates output upon click of the button
					if(hasVendor()){
						game.println(getVendor().getName().toUpperCase() + ": \"Let's do business!\"");
						getVendor().printItems();
						game.promptInput("Type out a transaction i.e. \"sell 2 breads\"");
						game.ic.tradeAction();
					} else{
						game.println("There's no vendor here.");
						game.ic.defaultAction();
					}
				}
			});
		}else if (type.equals("trade")){
			
		}else if (type.equals("inn")){
			
		}else{
			
		}
	}
	
	public boolean isOpen(){
		if((type.equals("shop") || type.equals("trade")) && (game.getHour() < 8 || game.getHour() > 20 )){
			game.println(name + " is closed. You must wait until 8:00 a.m. tomorrow to enter.");
			return false;
		}
		if(type.equals("tavern") && (game.getHour() > 3 && game.getHour() < 18)){
			game.println(name + " is closed. You must wait until 6:00 p.m. tomorrow to enter.");
			return false;
		}
		return true;
	}
	
	public boolean hasVendor(){
		if(npcs.size() > 0){
			for(NPC npc : npcs){
				if(npc.isVendor){
					return true;
				}
			}
		}
		return false;
	}
	
	public Item getItem(String name){
		for(int i = 0; i < items.size();i++){
			if(items.get(i).getName().equalsIgnoreCase(name)){
				if(items.get(i) instanceof Food){
					return (Food)items.get(i);
				} else if(items.get(i) instanceof Horse){
					return (Horse)items.get(i);
				}else if(items.get(i) instanceof Item){
					return items.get(i);
				}
			}
		}
		return null;
	}
	
	public void addItem(Item item, int amount){
		for(int i = 0; i < items.size();i++){
			if(items.get(i).getName().equals(item.getName())){
				items.get(i).changeAmount(amount);
				return;
			}
		}if(item instanceof Food){
			items.add(new Food(item.getName(),item.getValue(),amount, ((Food) item).getHealth()));
		} else if(item instanceof Horse){
			items.add(new Horse(item.getName(),item.getValue(),amount,((Horse) item).getSpeed(),((Horse) item).getHealth()));
		}else if(item instanceof Item){
			items.add(new Item(item.getName(),item.getValue(),amount));
		}
		
		
	}
	
	public void transferItems(Player p){
		game.print("You find ");
		if(items.size() > 0){
			for(int i = 0;i<items.size();i++){
				game.print(items.get(i).getName() + " ("+ items.get(i).getAmount() + ")");
				p.addItem(items.get(i),items.get(i).getAmount());
				if(items.size()-1==i){
					game.println(".");
					break;
				} else if(items.size() - i == 2){
					game.print(", and ");
				}else{
					game.print(", ");
				}
				
			}
			items.clear();
		} else{
			game.println("nothing of use.");
		}
		
	}
	
	public void addNPC(NPC npc){
		npcs.add(npc);
	}
	
	public void removeNPC(){
		npcs.clear();
	}
	
	public void showNPC(){
		if(npcs.size() > 0){
			for(int i = 0; i < npcs.size();i++){
				if(!(npcs.get(i) instanceof Player)){
					game.println(npcs.get(i).getName() + " is here.");
				}
				
			}
		}
	}
	
	
	public void addExit(Area nextArea, String direction){
		Exit exit = new Exit(this, nextArea, direction);
		exits.add(exit);
	}
	
	public boolean enterArea(){
		printDesc();
		return true;
	}
	
	public boolean exitArea(){
		return false;
	}
	
	public int getExitAmount(){
		return exits.size();
	}
	
	public Exit getExit(String exitToCheck){
		for(Exit e : exits){
			if(e.getDirection().equalsIgnoreCase(exitToCheck)){
				return e;
			}
		}
		return null;
	}
	
	public Area exit(String dir){
		Exit e = getExit(dir);
		if(e != null){
			return e.getTarget();
		}
		return null;
	}
	
	public void showExits(){
		if(exits.size() == 0){
			game.println("You can't go anywhere!");
		} else{
			game.print("You may exit ");
			for(int i =0;i<exits.size();i++){
				game.print(exits.get(i).getDirection() + " toward " + exits.get(i).getTarget().getName());
				if(exits.size()-1==i){
					game.println(".");
					break;
				} else if(exits.size() - i == 2){
					game.print(", or ");
				}else{
					game.print(", ");
				}
			}
		}
	}
	
	public NPC getVendor(){
		for(int i =0;i<npcs.size();i++){
			if(npcs.get(i).isVendor){
				return npcs.get(i);
			}
		}
		return null;
	}
	
	
}
