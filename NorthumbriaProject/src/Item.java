
public class Item {
	protected int value;
	protected String name;
	protected int amount;
	
	
	public Item(String name, int value, int amount){
		this.name = name;
		this.amount = amount;
		this.value = value;
	}
	
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
	
	public void changeAmount(int amount){
		this.amount += amount;
	}
	
	public String getStats(){
		return "[" + name + " (x"+amount+") ("+value+"g)]"; //override
	}
}
