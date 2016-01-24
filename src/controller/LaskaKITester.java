package controller;

import entities.Player;

public class LaskaKITester {

	public static final String SWIPL_LOCATION = "D:\\2\\Programme\\swipl\\bin\\swipl.exe";
	public static final String SWIPL_PARAMETERS_AI1 = " -s D:\\2\\Entwicklung\\Prolog\\LaskaKI\\2015\\main.pl";
	public static final int MAX_MATCHES = 6; 		// Number of simultaneous matches (1 Match --> 2 Players --> 2 Threads)
	public static final int TIMEOUT = 300*1000;		// Timeout in ms
	public static final boolean LOG_MATCHES = Boolean.TRUE;		// Timeout in ms
	
	public static void main(String[] args) {
		TournamentController tc = new TournamentController();
		
		
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, -1, -1, 0, 0, 0, 0, 0));
        //tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 1, 3, 0, 0, 0, 0, 0));
        //tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 33, 101, 11, 3, 0, 29, 53));
		
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 13, 3, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 131, 13, 3, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 47, 103, 13, 3, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 61, 103, 13, 3, 0, 0, 77));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 43, 131, 13, 3, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 59, 131, 13, 3, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 61, 131, 13, 3, 0, 0, 77));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 61, 163, 13, 3, 0, 0, 77));
		
		// Test 21.01.2016
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 13, 13, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 21, 13, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 21, 21, 0, 0, 77));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 37, 103, 31, 3, 0, 0, 77));
		
		tc.startTournament();
	}
}
