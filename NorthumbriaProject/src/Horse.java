
public class Horse extends Item{
	private final int SPEED;
	private int health;
	
	public Horse (String name, int value, int amount, int speed, int health){
		super(name, value, amount);
		SPEED = speed;
		this.health = health;
	}
	
	//inherited
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int amount){
		value = amount;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public String getStats(){
		return "Health: ["+ health + "/100], Speed: ["+ SPEED + "] ";
	}
	
	//new methods
	
	public int getSpeed(){
		return SPEED;
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
