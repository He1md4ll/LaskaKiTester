package controller;

public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	final static String SWIPL_PARAMETERS_AI1 = " -s .\\LaskaKI\\2015\\main.pl -t start(black)";
	final static String SWIPL_PARAMETERS_AI2 = " -s .\\LaskaKI\\2015\\main.pl -t start(white)";
	
	public static void main(String[] args) {
		
		
		MatchController matchController = new MatchController(SWIPL_LOCATION, SWIPL_PARAMETERS_AI1, SWIPL_PARAMETERS_AI2);
		matchController.start();
		
		//MoveController mc = new MoveController(SWIPL_LOCATION, SWIPL_PARAMETERS_AI2, "white");
		//mc.move("e2d3");
		
		
		//Test if two matches can run parallel
		//MatchController matchController1 = new MatchController(SWIPL_LOCATION, SWIPL_PARAMETERS_AI1, SWIPL_PARAMETERS_AI2);
		//matchController1.start();
		
	}

}
