package controller;

import java.util.ArrayList;
import java.util.Map;

import entities.Game;
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
	private ArrayList<MatchController> matches;
	
	public TournamentController(String swiplLocation){
		this.swiplLocation = swiplLocation;
		matches = new ArrayList<>();
	}
	
	public void addAi(Player ai){
		PlayerList.addAi(ai);
	}
	
	public void startTournament(){
		for (Map.Entry<String,Player> a:PlayerList.getPlayerList().entrySet()){
			for (Map.Entry<String,Player> b:PlayerList.getPlayerList().entrySet()){
				if (!a.getKey().equals(b.getKey()) && !PlayerList.hasPlayerDoneAllMatches(b.getKey())){
					MatchController mc = new MatchController(swiplLocation, a.getValue(), b.getValue());
					matches.add(mc);
					mc.start();
				}
			}
			PlayerList.playerDoneAllMachtes(a.getKey());	
		}
	}
	
	public void printRanking(){
		do{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(MatchController.runningMatches > 0);
		
		System.out.println();
		for (Map.Entry<String,Player> a:PlayerList.getPlayerList().entrySet()){
			Player player = a.getValue();
			ArrayList<Game> games = PlayerList.getAllGamesForAi(player.getId());
			System.out.println("Player " + a.getKey() + " scored " + player.getScore() + " points! " 
				+ "SV: " + player.getSv()
				+ " GV: " + player.getGv()
				+ " JSV: " + player.getJsv()
				+ " JJSV: " + player.getJjsv()
				+ " MV: " + player.getMv()
				+ " JV: " + player.getJv()
				+ " DV: " + player.getDv());
			for (Game game: games){
				int calcTime;
				if (game.getAi1().getId().equals(player.getId())){
					calcTime = game.getCalcTimeAi1();
				} else {
					calcTime = game.getCalcTimeAi2();
				}
				System.out.println("\t first game time: " + calcTime + "ms.\t Winner was player " + game.getWinnerAi().getId());
			}
		}
	}
	
	public void stopGame(){
		for (MatchController mc:matches){
			mc.stopGame();
		}
	}
}
