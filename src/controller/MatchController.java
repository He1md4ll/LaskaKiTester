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
	
	public static int runningMatches = 0;
	
	private int round = 0;
	
	private MoveController mc1;
	private MoveController mc2;
	
	public MatchController(String swiplLocation, Player ai1, Player ai2){
		this.swiplLocation = swiplLocation;
		this.ai1 = ai1;
		this.ai2 = ai2;
	}
	
	@Override
    public void run() {
		
		do{
			try {
				//Sleep till a slot is free for another match
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}while(runningMatches >= LaskaKITester.MAX_MATCHES);
		
		//Start matches 
		runningMatches ++;
		Player winner = play();
		PlayerList.addPoints(winner.getId());
		Player winner1 = play();
		PlayerList.addPoints(winner1.getId());
		if (winner.getId().equals(winner1.getId())){
			runningMatches--;
		} else {
			Player winner3 = play();
			PlayerList.addPoints(winner3.getId());
			runningMatches--;
		}
	}
	
	/**
	 * 
	 * @return returns the String to winning AI
	 */
	public Player play(){
		round ++;
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
						stopGame();
						return mc2.getAi();
					} else if (mc1.isWin()){ // Text outputs win
						System.out.println(mc1.getAi().getId() + " wins match");
						PlayerList.saveMatch(ai1, ai2, mc1.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						stopGame();
						return mc1.getAi();
					}
					mc2.move(ai1Action);
					System.out.println("------------------------------------------------");
					ai2Action = mc2.getAiAction();
					if (mc2.getTotalCalcTime() > TIMEOUT  || mc1.isWin()){ // Timeout for AI -> lost
						System.out.println(mc1.getAi().getId() + " wins match.\n");
						PlayerList.saveMatch(ai1, ai2, mc1.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						stopGame();
						return mc1.getAi();
					} else if (mc2.isWin()){ // Text output wins
						System.out.println(mc2.getAi().getId() + " wins match.\n");
						PlayerList.saveMatch(ai1, ai2, mc2.getAi(), mc1.getTotalCalcTime(), mc2.getTotalCalcTime());
						stopGame();
						return mc2.getAi();
					}
					mc1.move(ai2Action);
					System.out.println("------------------------------------------------");
				}
	}
	
	public void stopGame(){
		mc1.stopGame();
		mc2.stopGame();
	}
	
}
