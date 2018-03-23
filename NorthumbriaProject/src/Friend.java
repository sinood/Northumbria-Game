
public class Friend extends NPC{ //Friend is an NPC that can join the Player on his quest
	
	
	public Friend(String name, int money, Area area, int combat, int health, NorthumbriaGame game){
		super(name, money, health, combat, area, game);
		this.combat = combat;
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
	
	//new methods
	public void shareMoney(Player p){
		p.addMoney(money);
		money = 0;
	}
	
	
}	
