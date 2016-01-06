package controller;

public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	final static String SWIPL_PARAMETERS_AI1 = " -s .\\KIs\\1\\laska.pl -t start";
	final static String SWIPL_PARAMETERS_AI2 = " -s .\\KIs\\2\\laska.pl -t start";
	
	public static void main(String[] args) {
		
		
		MatchController matchController = new MatchController(SWIPL_LOCATION, SWIPL_PARAMETERS_AI1, SWIPL_PARAMETERS_AI2);
		matchController.start();
		
		//Test if two matches can run parallel
		//MatchController matchController1 = new MatchController(SWIPL_LOCATION, SWIPL_PARAMETERS_AI1, SWIPL_PARAMETERS_AI2);
		//matchController1.start();
		
	}

}
