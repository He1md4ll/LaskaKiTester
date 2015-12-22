public class LaskaKITester {

	final static String SWIPL_LOCATION = "C:\\Program Files\\swipl\\bin\\swipl.exe";
	final static String SWIPL_PARAMETERS = " -s .\\KIs\\1\\laska.pl -t start";
	final static String SWIPL_PARAMETERS1 = " -s .\\KIs\\2\\laska.pl -t start";
	
	public static void main(String[] args) {
		
		
		//Example of running two process with two AIs
		//TODO: Bug when there is a general (no AI-Action is detected)
		
		MoveController mc1 = new MoveController(SWIPL_LOCATION, SWIPL_PARAMETERS, "weiss");
		MoveController mc2 = new MoveController(SWIPL_LOCATION, SWIPL_PARAMETERS1, "schwarz");
		
		String ai1Action;
		String ai2Action;
		
		
		while (true){ //TODO: not while true better usw while (!won/gewonnen)
			ai1Action = mc1.getAiAction();
			mc2.move(FieldCoordinateRotator.rotateCoordinates(ai1Action));
			System.out.println("------------------------------------------------");
			ai2Action = mc2.getAiAction();
			mc1.move(FieldCoordinateRotator.rotateCoordinates(ai2Action));
			System.out.println("------------------------------------------------");
		}
		
	}

}
