
public class Friend extends NPC{ //Friend is an NPC that can join the Player on his quest
	
	protected int combat;
	protected int health;
	private Player p;
	
	public Friend(String name, int money, Area area, int combat, int health){
		super(name, money, area);
		this.combat = combat;
		this.health = health;
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
	
	//new methods
	public void shareMoney(Player p){
		p.addMoney(money);
		money = 0;
	}
	
	public int getCombat(){
		return combat;
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
