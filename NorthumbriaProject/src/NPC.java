
public class NPC {
	protected String name;
	protected int money;
	protected Area thisArea; //represents the current or starting area
	
	public NPC(String name, int money, Area area){
		this.name = name;
		this.money = money;
		thisArea = area;
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
}
