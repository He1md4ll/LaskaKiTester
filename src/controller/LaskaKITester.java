package controller;

import entities.Player;

public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl";
	public static final int MAX_MATCHES = 8; 		//Change this Variable to increase/decrease amount
													//of matches running in parallel (best is 1 match/cpu_core)
	
	public static void main(String[] args) {
		
		TournamentController tc = new TournamentController(SWIPL_LOCATION);
		
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 5, 10, 0, 0, 0, 0, 50));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 10, 2, 0, 0, 50));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 10, 2, 0, 0, 50));
		
		tc.startTournament();
		
		tc.printRanking();
		     
	}

}
