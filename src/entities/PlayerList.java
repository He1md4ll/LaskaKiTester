package entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerList {
	private static LinkedHashMap<String, Player> playerList = new LinkedHashMap<>();
	private static LinkedHashMap<String, Player> playerDoneAllMachtes = new LinkedHashMap<>();
	private static LinkedHashMap<Integer, Game> gameList = new LinkedHashMap();
	
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
	
	public static void saveMatch(Player ai1, Player ai2, Player winnerAi, int calcTimeAi1, int calcTimeAi2){
		Game newGame = new Game(calcTimeAi1, calcTimeAi2, ai1, ai2, winnerAi);
		gameList.put(newGame.getId(), newGame);
	}
	
	public static ArrayList<Game> getAllGamesForAi(String id){
		ArrayList<Game> games = new ArrayList<>();
		for (Map.Entry<Integer,Game> game:gameList.entrySet()){
			if (game.getValue().getAi1().getId().equals(id)){
				games.add(game.getValue());
			}
			if (game.getValue().getAi2().getId().equals(id)){
				games.add(game.getValue());
			}
		}
		return games;
	}
	
	
	
	
}
