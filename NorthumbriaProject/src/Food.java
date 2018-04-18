
public class Food extends Item{
	protected final int HEALTH;
	public Food(String name, int value, int amount, int health){
		super(name, value, amount);
		HEALTH = health;
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
		return "[" + name + " (x"+amount+") ("+value+"g) (+"+HEALTH+" HP)]";
	}
	
	//new methods
	public int getHealth(){
		return HEALTH;
	}
	
	//add functionality
}
