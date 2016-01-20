package controller;

import entities.Player;

public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	//final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl -t start(black)";
	final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl";
	final static String SWIPL_PARAMETERS_AI2 = " -s C:\\Users\\anon\\Documents\\GitHub\\LaskaKI\\2015\\main.pl";
	
	public static void main(String[] args) {
		
		TournamentController tc = new TournamentController(SWIPL_LOCATION);
		
		// 					Parameter, 			  SV, GV, JSV,JJSV,MV,JV,DV
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 10, 5, 1, 1, 10));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 10, 10, 0, 0, 50));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 0, 0, 0, 0, 0));
		tc.addAi(new Player(SWIPL_PARAMETERS_AI1, 35, 100, 10, 5, 1, 5, 5));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1));
		//tc.addAi(new Player(SWIPL_PARAMETERS_AI1));
		
		tc.startTournament();
		
		tc.printRanking();
		
		//TODO:
		//Swipl beenden bei shutdown
		//tournament statistik erweitern um parameter/file und timeout loses
		//win/loss erkennen
		
	}

}
