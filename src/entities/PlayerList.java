package entities;

import java.util.LinkedHashMap;

public class PlayerList {
	private static LinkedHashMap<String, Player> playerList = new LinkedHashMap<>();
	private static LinkedHashMap<String, Player> playerDoneAllMachtes = new LinkedHashMap<>();
	
	public static void addAi(Player player){
		playerList.put(player.getId(), player);
	}
	
	public static void addPoints(String id){
		playerList.get(id).winScore();
	}
	
	public static boolean hasPlayerDoneAllMatches(String id){
		Player tmpPlayer = playerDoneAllMachtes.get(id);
		
		if(tmpPlayer == null){
			return false;
		} else {
			return true;
		}
	}
	
	public static LinkedHashMap<String, Player> getPlayerList(){
		return playerList;
	}
	
	public static void playerDoneAllMachtes(String id){
		playerDoneAllMachtes.put(id,playerList.get(id));
	}
	
	
	
}
