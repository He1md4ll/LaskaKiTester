package entities;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PlayerList {
	private static HashMap<Integer, Player> playerList = new LinkedHashMap<>();
	private static HashMap<Integer, Player> playerDoneAllMachtes = new LinkedHashMap<>();
	
	public static void addAi(Player player){
		playerList.put(player.getId(), player);
	}
	
	public static boolean hasPlayerDoneAllMatches(int id){
		return playerDoneAllMachtes.get(id) != null;
	}
	
	public static HashMap<Integer, Player> getPlayerList(){
		return playerList;
	}
	
	public static void playerDoneAllMachtes(int id){
		playerDoneAllMachtes.put(id,playerList.get(id));
	}
}