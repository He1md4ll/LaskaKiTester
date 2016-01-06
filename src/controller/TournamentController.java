package controller;

import java.util.ArrayList;

/**
 * Class to play a full tournament with double-knock out (K.O)
 * AIs are to be added with addAi()
 * Tournament is started with startTournament()
 * @author anon
 *
 */
public class TournamentController {
	
	private ArrayList<Object> ais;
	
	public TournamentController(){
		ais = new ArrayList<>();
	}
	
	public void addAi(String ai){
		ais.add(ai);
	}
	
	public void startTournament(){
		
	}
	
	public void printRanking(){
		//System.out ...
		// Position in Tournament
		// Calculation time
		// ...
	}
}
