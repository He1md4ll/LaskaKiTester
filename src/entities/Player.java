package entities;

import java.util.UUID;

public class Player {
	private String parameters;
	private int score = 0;
	private String id;
	
	public Player(String parameters){
		this.parameters = parameters;
		this.id = UUID.randomUUID().toString();
	}
	
	public String getParameters(){
		return this.parameters;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void winScore(){
		score+=10;
	}
	
	public String getId(){
		return this.id;
	}
}
