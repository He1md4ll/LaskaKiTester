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
	
	private final int TIMEOUT = 300*1000;
	
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
		Player winne1 = play();
		if (winner.getId().equals(winne1.getId())){
			System.out.println("Player with parameters " + winner + " won!");
			PlayerList.addPoints(winner.getId());
			currentMatches--;
		} else {
			Player winner3 = play();
			System.out.println("Player with parameters " + winner3 + " won!");
			PlayerList.addPoints(winner.getId());
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
				mc1 = new MoveController(swiplLocation, ai1.getParameters(), "black");
				mc2 = new MoveController(swiplLocation, ai2.getParameters(), "white");
			} else {
				mc1 = new MoveController(swiplLocation, ai2.getParameters(), "black");
				mc2 = new MoveController(swiplLocation, ai1.getParameters(), "white");
			}
				
				String ai1Action;
				String ai2Action;
				
				
				while (true){
					ai1Action = mc1.getAiAction();
					if (mc1.getTotalCalcTime() > TIMEOUT || mc2.isWin()){ // Timeout for AI -> lost
						System.out.println("AI1 lost match");
						mc1.stopGame();
						mc2.stopGame();
						return ai2;
					} else if (mc1.isWin()){ // Text outputs win
						System.out.println("AI1 wins match");
						mc1.stopGame();
						mc2.stopGame();
						return ai1;
					}
					mc2.move(ai1Action);
					System.out.println("------------------------------------------------");
					ai2Action = mc2.getAiAction();
					if (mc2.getTotalCalcTime() > TIMEOUT  || mc1.isWin()){ // Timeout for AI -> lost
						System.out.println("AI2 lost match");
						mc1.stopGame();
						mc2.stopGame();
						return ai1;
					} else if (mc2.isWin()){ // Text output wins
						System.out.println("AI2 wins match");
						mc1.stopGame();
						mc2.stopGame();
						return ai2;
					}
					mc1.move(ai2Action);
					System.out.println("------------------------------------------------");
				}
	}
	
}
