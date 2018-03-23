
public class NPC {
	protected String name;
	protected int money;
	protected int health;
	protected int combat;
	protected final int MAX_HEALTH;
	protected Area thisArea; //represents the current or starting area
	protected NorthumbriaGame game; //represents the current game, mostly to print values from this class
	
	public NPC(String name, int money, int health, int combat, Area area, NorthumbriaGame game){
		this.name = name;
		this.money = money;
		this.health = health;
		MAX_HEALTH = health;
		thisArea = area;
		this.game = game;
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
}
