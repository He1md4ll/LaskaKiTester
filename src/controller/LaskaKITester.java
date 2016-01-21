package controller;

import entities.Player;

public class LaskaKITester {

	public final static String SWIPL_LOCATION = "D:\\2\\Programme\\swipl\\bin\\swipl.exe";
	public final static String SWIPL_PARAMETERS_AI1 = " -s D:\\2\\Entwicklung\\Prolog\\LaskaKI\\2015\\main.pl";
	public final static int MAX_MATCHES = 5; 		//Change this Variable to increase/decrease amount
	public static final int TIMEOUT = 300*1000;		//of matches running in parallel (best is 1 match/cpu_core)
	
	public static void main(String[] args) {
		TournamentController tc = new TournamentController();
		
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 13, 13, 0, 0, 77));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, -1, -1, 0, 0, 0, 0, 0));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 21, 13, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 21, 21, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 31, 3, 0, 0, 77));
		
		tc.startTournament();
	}
}
