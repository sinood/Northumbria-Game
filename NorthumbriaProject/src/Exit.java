/*
 * The Path class creates connections or 'exits' between Areas in the game.
 * Paths are defined by the Area being exited and the Area being entered, and the
 * direction of the exit from the current area i.e. "west" or "north" exits
 */
public class Exit {
	private Area previousArea;
	private Area nextArea;
	private String direction;
	
	public Exit(Area previousArea, Area next, String direction){
		this.previousArea = previousArea;
		nextArea = next;
		this.direction = direction;
	}
	
	public void setSource(Area newSource){
		previousArea = newSource;
	}
	
	public Area getSource(){
		return previousArea;
	}
	
	public void setTarget(Area newTarget){
		nextArea = newTarget;
	}
	
	public Area getTarget(){
		return nextArea;
	}
	
	public void setDirection(String direction){
		this.direction = direction;
	}
	
	public String getDirection(){
		return direction;
	}
	
	public String getExitDesc(){
		return nextArea.getName() + " is to the " + direction;
	}
}
