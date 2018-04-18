
public class LocalMap {
	NorthumbriaGame game;
	Player player;
	public LocalMap(NorthumbriaGame game, Player p){
		this.game = game;
		player = p;
	}
	
	public void reset(){
		game.nw.setText("");
		game.n.setText("");
		game.ne.setText("");
		game.w.setText("");
		game.you.setText("");
		game.e.setText("");
		game.sw.setText("");
		game.s.setText("");
		game.se.setText("");
		if(player.getCurrentArea().getExitAmount() > 0){
			game.you.setText(player.getCurrentArea().getName());
			for(int i = 0; i < player.getCurrentArea().getExitAmount(); i++){
				if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("NW")){
					game.nw.setText(player.getCurrentArea().getExit("NW").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("north")){
					game.n.setText(player.getCurrentArea().getExit("north").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("NE")){
					game.ne.setText(player.getCurrentArea().getExit("NE").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("west")){
					game.w.setText(player.getCurrentArea().getExit("west").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("east")){
					game.e.setText(player.getCurrentArea().getExit("east").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("SW")){
					game.sw.setText(player.getCurrentArea().getExit("SW").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("south")){
					game.s.setText(player.getCurrentArea().getExit("south").getTarget().getName());
				}else if(player.getCurrentArea().exits.get(i).getDirection().equalsIgnoreCase("SE")){
					game.se.setText(player.getCurrentArea().getExit("SE").getTarget().getName());
				}
			}
		}
	}
}
