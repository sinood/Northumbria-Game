import java.util.ArrayList;

public class Player extends NPC{ //Player extends NPC to share the name and money variables, but is NOT an NPC
	private NorthumbriaGame thisGame; //represents the current game, mostly to print values from this class
	private final String name;
	private final String surname;
	private ArrayList<Friend> friends; //tracks the Player's acquired friends
	
	public Player(String name, String surname, NorthumbriaGame game, Area area){
		super(name, 50, area); //passed initial amount for money
		this.name = name;
		this.surname = surname;
		thisGame = game;
	}
	
	//inherited from NPC
	public int getMoney(){
		return money;
	}
	
	public void setMoney(int amount){
		money = amount;
	}
	
	public void addMoney(int amount){
		money += amount;
	}
	
	public void loseMoney(int amount){
		money -= amount;
	}
	
	
	public String getName(){
		return name;
	}
	
	//new methods
	public String getSurname(){
		return surname;
	}
	
	public String getFullName(){
		return name + " " + surname;
	}
	
	public void setCurrentArea(Area area){
		thisArea = area;
	}
	
	public Area getCurrentArea(){
		return thisArea;
	}
	
	public void move(String direction){
		if(thisArea.exit(direction) == null){
			thisGame.println("You can't go there!");
		} else{
			setCurrentArea(thisArea.exit(direction));
			thisArea.printDesc();
			thisArea.showExits();
		}
	}
	
	public void addFriend(Friend f){
		friends.add(f);
		thisGame.print(f.getName() + " is now your friend!");
	}
	
	public Friend getFriend(String name){
		for(int i = 0; i < friends.size();i++){
			if(friends.get(i).getName().equalsIgnoreCase(name)){
				return friends.get(i);
			}
		}
		thisGame.print("Who?");
		return null;
	}
}
