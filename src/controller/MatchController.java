package controller;

import entities.Player;
import entities.PlayerList;

/**
 * Class to one match bewteen two players.
 * Start match with run() command to start thread! (for parallel excecution)
 * @author anon6789 aka jelto
 *
 */

public class MatchController extends Thread{

	private String swiplLocation;
	private Player ai1;
	private Player ai2;
	
	private final int TIMEOUT = 300*10;
	
	public static int currentMatches = 0;
	
	private int round = 0;
	
	public MatchController(String swiplLocation, Player ai1, Player ai2){
		this.swiplLocation = swiplLocation;
		this.ai1 = ai1;
		this.ai2 = ai2;
		currentMatches ++;
	}
	
	@Override
    public void run() {
		Player winner = play();
		PlayerList.addPoints(winner.getId());
		Player winner1 = play();
		PlayerList.addPoints(winner1.getId());
		if (winner.getId().equals(winner1.getId())){
			//System.out.println("Player with parameters " + winner.getParameters() + " won!");
			currentMatches--;
		} else {
			Player winner3 = play();
			PlayerList.addPoints(winner3.getId());
			//System.out.println("Player with parameters " + winner3.getParameters() + " won!");
			currentMatches--;
		}
	}
	
	/**
	 * 
	 * @return returns the String to winning AI
	 */
	public Player play(){
		//Example of running two process with two AIs
		round ++;
		MoveController mc1;
		MoveController mc2;
			if (round % 2 == 1){
				mc1 = new MoveController(swiplLocation, ai1, "black");
				mc2 = new MoveController(swiplLocation, ai2, "white");
			} else {
				mc1 = new MoveController(swiplLocation, ai2, "black");
				mc2 = new MoveController(swiplLocation, ai1, "white");
			}
				
				String ai1Action;
				String ai2Action;
				
				
				while (true){
					ai1Action = mc1.getAiAction();
					if (mc1.getTotalCalcTime() > TIMEOUT || mc2.isWin()){ // Timeout for AI -> lost
						System.out.println(mc2.getAi().getId() + " wins match");
						PlayerList.saveMatch(ai1, ai2, mc2.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						mc1.stopGame();
						mc2.stopGame();
						return mc2.getAi();
					} else if (mc1.isWin()){ // Text outputs win
						System.out.println(mc1.getAi().getId() + " wins match");
						PlayerList.saveMatch(ai1, ai2, mc1.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						mc1.stopGame();
						mc2.stopGame();
						return mc1.getAi();
					}
					mc2.move(ai1Action);
					System.out.println("------------------------------------------------");
					ai2Action = mc2.getAiAction();
					if (mc2.getTotalCalcTime() > TIMEOUT  || mc1.isWin()){ // Timeout for AI -> lost
						System.out.println(mc1.getAi().getId() + " wins match.\n");
						PlayerList.saveMatch(ai1, ai2, mc1.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						mc1.stopGame();
						mc2.stopGame();
						return mc1.getAi();
					} else if (mc2.isWin()){ // Text output wins
						System.out.println(mc2.getAi().getId() + " wins match.\n");
						PlayerList.saveMatch(ai1, ai2, mc2.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						mc1.stopGame();
						mc2.stopGame();
						return mc2.getAi();
					}
					mc1.move(ai2Action);
					System.out.println("------------------------------------------------");
				}
	}
	
}
