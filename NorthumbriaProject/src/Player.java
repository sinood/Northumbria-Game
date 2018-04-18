import java.util.ArrayList;

public class Player extends NPC{ //Player extends NPC to share the name and money variables, but is NOT an NPC
	private final String name;
	private final String surname;
	protected int money;
	protected int health;
	protected int combat;
	protected int MAX_HEALTH;
	public boolean weaponEquipped;
	public ArrayList<Friend> friends = new ArrayList<Friend>(); //tracks the Player's acquired friends
	public ArrayList<ArrayList<Item>> inventory = new ArrayList<ArrayList<Item>>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Item> food = new ArrayList<Item>();
	public ArrayList<Item> horses = new ArrayList<Item>();
	private LocalMap lm = new LocalMap(game, this);
	
	public Player(String name, String surname, int health, int combat, Area area, NorthumbriaGame game){
		super(name, 50, health, combat, false, area, game); //passed initial amount for money
		this.name = name;
		this.surname = surname;
		this.money = 50;
		this.health = health;
		MAX_HEALTH = health;
		this.combat = combat;
		weaponEquipped = false;
		inventory.add(items);
		inventory.add(food);
		inventory.add(horses);
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
		if(health > MAX_HEALTH){
			health = MAX_HEALTH;
		}
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
	
	public String showStats(){
		return getFullName() + ": Health: ["+ getHealth() + "/" +MAX_HEALTH+"], Money: ["+money
				+"g], Combat: ["+combat+"], Friends: ["+friends.size()+"]";
	}
	
	public void consume(Food f, int amount){
		if(amount <= f.getAmount()){
			health += f.getHealth() * amount;
			if(health > MAX_HEALTH){
				health = MAX_HEALTH;
			}
			f.setAmount(f.getAmount()-amount);
			if(f.getAmount()==0){
				removeFood(f.getName());
			}
			game.println("You have consumed " +amount + " "+ f.getName() + ".");
		}
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
	
	//move player to a new area
	public void move(String direction){
		if(thisArea.exit(direction) == null){
			game.println("You can't go there!");
		} else if(thisArea.getExit(direction).getTarget().isOpen()){
			setCurrentArea(thisArea.exit(direction));
			thisArea.setFunction();
			thisArea.printDesc();
			lm.reset();
			//add image set method
		}
	}
	
	public int numOfFriends(){
		return friends.size();
	}
	
	public void addFriend(Friend f){
		friends.add(f);
		int added = f.getMoney();
		f.shareMoney(this);
		game.println(f.getName() + " is now your friend! You have gained "+ added +"g.");
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
	
	public Friend getFriend(int x){
		return friends.get(x);
	}
	
	
	public Item getItem(String name){
		for(int i = 0; i<inventory.size();i++){
			for(int j = 0; j<inventory.get(i).size();j++){
				if(inventory.get(i).get(j).getName().equalsIgnoreCase(name)){
					return inventory.get(i).get(j);
				}
			}
		}
		return null;
	}
	
	public void addItem(Item item, int amount){
		if(hasItem(item)){
			getItem(item.getName()).changeAmount(amount);
		} else if(item instanceof Food){
			addItem((Food)item);
			getItem(item.getName()).setAmount(amount);
		} else if(item instanceof Horse){
			addItem((Horse)item);
			getItem(item.getName()).setAmount(amount);
		} else{
			addItem(item);
			getItem(item.getName()).setAmount(amount);
		}
	}
	
	public void addItem(Item item){
		if(item instanceof Food){
			food.add(new Food(item.getName(),item.getValue(),item.getAmount(),((Food) item).getHealth()));
		} else if(item instanceof Horse){
			horses.add(new Horse(item.getName(),item.getValue(),item.getAmount(),((Horse) item).getSpeed(),((Horse) item).getHealth()));
		} else{
			items.add(new Item(item.getName(), item.getValue(),item.getAmount()));
		}
	}
	
	public void removeItem(String name){
		for(int i = 0; i <items.size();i++){
			if(items.get(i).getName().equalsIgnoreCase(name)){
				items.remove(i);
			}
		}
	}
	
	public void removeItem(String name, int amount){
		for(int i = 0;i<items.size();i++){
			if(items.get(i).getName().equals(name)){
				if(amount <= items.get(i).getAmount()){
					items.get(i).changeAmount(-1*amount);
					if(items.get(i).getAmount() == 0){
						items.remove(i);
					}
				}
			}
		}
	}
	
	public Item getFood(String name){
		for(int i = 0; i <food.size();i++){
			if(food.get(i).getName().equalsIgnoreCase(name)){
				return food.get(i);
			}
		}
		return null;
	}
	
	public void giveFood(Food f, int amount, Friend fr){
		//f.setAmount(f.getAmount()-amount);
		remove(f, amount);
		fr.consume(f, amount);
		game.println("You gave "+ fr.getName() + " " + amount + " " + f.getName() +".");
	}
	
	public void removeFood(String name){
		for(int i = 0; i <food.size();i++){
			if(food.get(i).getName().equalsIgnoreCase(name)){
				food.remove(i);
			}
		}
	}
	
	public Item getHorse(String name){
		for(int i = 0; i <horses.size();i++){
			if(horses.get(i).getName().equalsIgnoreCase(name)){
				return horses.get(i);
			}
		}
		return null;
	}
	
	
	public void removeHorse(String name){
		for(int i = 0; i <horses.size();i++){
			if(horses.get(i).getName().equalsIgnoreCase(name)){
				horses.remove(i);
			}
		}
	}
	
	
	public void remove(Item item, int amount){
		item.changeAmount(-1*amount);
		if(item.getAmount()==0){
			for(int i = 0; i<inventory.size();i++){
				for(int j = 0; j<inventory.get(i).size();j++){
					if(inventory.get(i).get(j)==item){
						inventory.get(i).remove(j);
					}
				}
			}
		}
	}
	
	public void dropItem (Item item, int amount){
		remove(item, amount);
		getCurrentArea().addItem(item, amount);
		game.println("You have dropped "+ item.getName() +" (" +amount+ ").");
	}
	
	public boolean hasItem(Item item){
		for(int i = 0; i<inventory.size();i++){
			for(int j = 0; j<inventory.get(i).size();j++){
				if(inventory.get(i).get(j).getName().equals(item.getName())){
					return true;
				}
			}
		}
		return false;
	}
	
	public void printInventory(){
		game.println("Inventory: { ");
		for(int i = 0; i < inventory.size();i++){
			if(inventory.get(i).size() > 0){
				for(int j = 0; j< inventory.get(i).size();j++){
					game.println(inventory.get(i).get(j).getStats());
				}
			}
		}
		game.println(" }");
	}
}
