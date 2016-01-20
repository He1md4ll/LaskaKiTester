package entities;

public class Game {

	private int calcTimeAi1;
	private int calcTimeAi2;
	private Player ai2;
	private Player ai1;
	private Player winnerAi;
	private int id;
	static int gameCounter = 0;
	
	public Game(int calcTimeAi1, int calcTimeAi2, Player ai1, Player ai2, Player winnerAi){
		this.calcTimeAi1 = calcTimeAi1;
		this.calcTimeAi2 = calcTimeAi2;
		this.ai1 = ai1;
		this.ai2 = ai2;
		this.id = gameCounter++;
		this.winnerAi = winnerAi;
	}

	public int getCalcTimeAi1() {
		return calcTimeAi1;
	}
	
	public int getCalcTimeAi2() {
		return calcTimeAi2;
	}

	public Player getOpponentAI() {
		return ai2;
	}

	public Player getAi1(){
		return ai1;
	}
	
	public Player getAi2(){
		return ai2;
	}
	
	public int getId(){
		return this.id;
	}
	
	public Player getWinnerAi(){
		return this.winnerAi;
	}
	
	
}
