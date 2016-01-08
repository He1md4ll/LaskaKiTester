package controller;

import staticUtils.FieldCoordinateRotator;

/**
 * Class to one match bewteen two players.
 * Start match with run() command to start thread! (for parallel excecution)
 * @author anon6789 aka jelto
 *
 */

public class MatchController extends Thread{

	private String swiplLocation;
	private String ai1;
	private String ai2;
	
	public MatchController(String swiplLocation, String ai1, String ai2){
		this.swiplLocation = swiplLocation;
		this.ai1 = ai1;
		this.ai2 = ai2;
		
	}
	
	@Override
    public void run() {
		play();
	}
	
	/**
	 * 
	 * @return returns the String to winning AI
	 */
	public String play(){
		//Example of running two process with two AIs
		
				MoveController mc1 = new MoveController(swiplLocation, ai1, "black");
				MoveController mc2 = new MoveController(swiplLocation, ai2, "white");
				
				String ai1Action;
				String ai2Action;
				
				
				while (true){
					ai1Action = mc1.getAiAction();
					if (mc1.getTotalCalcTime() > 300*1000){ // Timeout for AI -> lost
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
					if (mc2.getTotalCalcTime() > 300*1000){ // Timeout for AI -> lost
						System.out.println("AI1 lost match");
						mc1.stopGame();
						mc2.stopGame();
						return ai1;
					} else if (mc2.isWin()){ // Text output wins
						System.out.println("AI1 wins match");
						mc1.stopGame();
						mc2.stopGame();
						return ai2;
					}
					mc1.move(ai2Action);
					System.out.println("------------------------------------------------");
				}
	}
	
}
