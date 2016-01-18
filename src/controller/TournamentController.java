package controller;

import java.util.Map;
import entities.Player;
import entities.PlayerList;

/**
 * Class to play a full tournament with round robin
 * AIs are to be added with addAi()
 * Tournament is started with startTournament()
 * @author anon
 *
 */
public class TournamentController {
	
	private String swiplLocation;
	
	public TournamentController(String swiplLocation){
		this.swiplLocation = swiplLocation;
	}
	
	public void addAi(Player ai){
		PlayerList.addAi(ai);
	}
	
	public void startTournament(){
		for (Map.Entry<String,Player> a:PlayerList.getPlayerList().entrySet()){
			for (Map.Entry<String,Player> b:PlayerList.getPlayerList().entrySet()){
				if (!a.getKey().equals(b.getKey()) && !PlayerList.hasPlayerDoneAllMatches(b.getKey())){
					MatchController mc = new MatchController(swiplLocation, a.getValue(), b.getValue());
					mc.start();
				}
			}
			PlayerList.playerDoneAllMachtes(a.getKey());	
		}
	}
	
	public void printRanking(){
		while (MatchController.currentMatches > 0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Map.Entry<String,Player> a:PlayerList.getPlayerList().entrySet()){
			System.out.println("Player " + a.getKey() + " scored " + a.getValue().getScore() + " points!");
		}
	}
}
