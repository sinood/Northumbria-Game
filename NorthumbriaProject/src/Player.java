
public class Player {
	private NorthumbriaGame thisGame;
	private Area thisArea;
	private final String name;
	private final String surname;
	
	public Player(String name, String surname, NorthumbriaGame game, Area area){
		this.name = name;
		this.surname = surname;
		thisGame = game;
		thisArea = area;
		
	}
	
	public String getName(){
		return name;
	}
	
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
			thisGame.println("You can't go there!");
		} else{
			setCurrentArea(thisArea.exit(direction));
			thisArea.printDesc();
			thisArea.showExits();
		}
	}
	
}
