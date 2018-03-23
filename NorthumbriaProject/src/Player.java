import java.util.ArrayList;

public class Player extends NPC{ //Player extends NPC to share the name and money variables, but is NOT an NPC
	private final String name;
	private final String surname;
	private ArrayList<Friend> friends; //tracks the Player's acquired friends
	public ArrayList<Item> inventory = new ArrayList<Item>();
	private LocalMap lm = new LocalMap(game, this);
	
	public Player(String name, String surname, int health, int combat, Area area, NorthumbriaGame game){
		super(name, 50, health, combat, area, game); //passed initial amount for money
		this.name = name;
		this.surname = surname;
	}
	
	//inherited from NPC
	public String getName(){
		return name;
	}
	
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
	
	public int getCombat(){
		return combat;
	}
	
	public void setCombat(int amount){
		combat += amount;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int amount){
		health = amount;
	}
	
	public void heal(int amount){
		health += amount;
	}
	
	public void harm(int amount){
		health -= amount;
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
			game.println("You can't go there!");
		} else{
			setCurrentArea(thisArea.exit(direction));
			thisArea.printDesc();
			//thisArea.showExits();
			lm.reset();
		}
	}
	
	public void addFriend(Friend f){
		friends.add(f);
		game.print(f.getName() + " is now your friend!");
	}
	
	public Friend getFriend(String name){
		for(int i = 0; i < friends.size();i++){
			if(friends.get(i).getName().equalsIgnoreCase(name)){
				return friends.get(i);
			}
		}
		game.print("Who?");
		return null;
	}
	
	public Item getItem(int i){
		return inventory.get(i);
	}
	
	public void addItem(Item item){
		inventory.add(item);
	}
	
	public void removeItem(int i){
		inventory.remove(inventory.get(i));
	}
	
	public void printInventory(){
		if(inventory.size() > 0){
			for(int i = 0; i< inventory.size();i++){
				game.print(inventory.get(i).getStats());
				if(i % 3 ==0){
					game.println("");
				}
			}
			game.println("");
		}
	}
}
