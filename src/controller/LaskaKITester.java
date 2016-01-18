package controller;

import entities.Player;

public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	//final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl -t start(black)";
	final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl";
	final static String SWIPL_PARAMETERS_AI2 = " -s .\\LaskaKI\\2015\\main.pl";
	
	public static void main(String[] args) {
		
		TournamentController tc = new TournamentController(SWIPL_LOCATION);
		
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI2));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1));
		
		tc.startTournament();
		
		tc.printRanking();
		
	}

}
