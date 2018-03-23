/*
 * This class creates an Area which abstracts Northumbria's Town and Room type areas.
 * Namely, the class takes, sets, and returns an area's name, description, displayed image
 * and available connections to other areas.
 */
import java.io.*;
import java.util.ArrayList;

public class Area {
	protected String name;
	protected String desc;
	public ArrayList<Exit> exits;
	private NorthumbriaGame game;
	
	public Area(String name, String desc, NorthumbriaGame game){
		this.name = name;
		this.desc = desc;
		this.game = game;
		exits = new ArrayList<Exit>();
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
}
