import java.util.ArrayList;

public class NPC {
	protected String name;
	protected int money;
	protected int health;
	protected int combat;
	public boolean isVendor;
	protected int MAX_HEALTH;
	protected ArrayList<Item> items;
	protected Area thisArea; //represents the current or starting area
	protected NorthumbriaGame game; //represents the current game, mostly to print values from this class
	
	public NPC(String name, int money, int health, int combat, boolean isVendor, Area area, NorthumbriaGame game){
		this.name = name;
		this.money = money;
		this.health = health;
		MAX_HEALTH = health;
		this.combat = combat;
		this.isVendor = isVendor;
		thisArea = area;
		thisArea.addNPC(this);
		this.game = game;
		items = new ArrayList<Item>();
	}
	
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
	
	public Item getItem(String name){
		for(int i = 0;i<items.size();i++){
			if(items.get(i).getName().equals(name)){
				return items.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Item> getItems(){
		return items;
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
	
	public void printItems(){
		int price;
		game.println(name + ": {");
		for(int i = 0;i<items.size();i++){
			price = ((int)(items.get(i).getValue()*1.25));
			game.print(items.get(i).getName() + ", (Price: "+price+"g)");
			if(items.get(i) instanceof Food){
				game.print(", (Health: +"+((Food)items.get(i)).getHealth()+")");
			} else if(items.get(i) instanceof Horse){
				game.print(", (Speed: +"+((Horse)items.get(i)).getSpeed()+")");
			}
			game.println(".....x"+ items.get(i).getAmount());
		}
		game.println("}");
	}
	
	
	
	public String showStats(){
		return name + ": Health: ["+ getHealth() + "/" +MAX_HEALTH+"], Combat: ["+combat+"]";
	}
	
	public void consume(Food f){
		health += f.getHealth();
		if(health > MAX_HEALTH){
			health = MAX_HEALTH;
		}
	}
}
