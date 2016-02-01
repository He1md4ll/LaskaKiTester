package controller;

import java.util.ArrayList;

import entities.Player;

public class LaskaKITester {

	public final static String SWIPL_LOCATION = "/usr/lib/swi-prolog/bin/amd64/swipl";
	public final static String SWIPL_PARAMETERS_AI_Main = " -s /home/sir_jelto/LaskaKI/2015/main.pl";
	public final static String SWIPL_PARAMETERS_AI_Main_LZ = " -s /home/sir_jelto/LaskaKI/2015_Main_LZ/main.pl";
	public final static String SWIPL_PARAMETERS_AI_Florian_LZ = " -s /home/sir_jelto/LaskaKI/2015_FlorianLZ/main.pl";
	public final static int MAX_MATCHES = 24; 		// Number of simultaneous matches (1 Match --> 2 Players --> 2 Threads)
	public static final int TIMEOUT = 5 * 60 * 1000;		// Timeout in ms
	public static final boolean LOG_MATCHES = Boolean.TRUE;		
	
	public static void main(String[] args) {
		TournamentController tc = new TournamentController();		

		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 20, 50, 0, 0, 3, 5, 0));	 	//Player0
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 20, 65, 10, 0, 3, 7, 10)); 	//Player1
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 10, 5, 0, 20, 50)); 	//Player2	//FloriansBeste
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 1, 3, 0, 0, 0, 0, 0)); 		//Player3 	//DummeKI
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 10, 5, 0, 0, 0));	//Player4	//Simple1
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 0, 0, 0, 10, 0)); 	//Player5	//Simple2
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 10, 10, 0, 0, 50));	//Player6	//JeltoKI
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 70, 220, 10, 10, 0, 5, 80));	//Player7	//MartinsBeste
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 20, 65, 0, 0, 3, 3, 0));		//Player8   //2014
		tc.addAi(new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 0, 0, 5, 5, 0));		//Player9	//2010

		Player player10 = new Player(SWIPL_PARAMETERS_AI_Main, 30, 100, 10, 5, 0, 20, 50);	
		Player player11 = new Player(SWIPL_PARAMETERS_AI_Main, 30, 55, 10, 5, 0, 5, 50);				
		Player player12 = new Player(SWIPL_PARAMETERS_AI_Main, 70, 220, 10, 10, 0, 5, 80);						
		Player player13 = new Player(SWIPL_PARAMETERS_AI_Main, 70, 220, 10, 10, 0, 5, 20);		
		Player player14 = new Player(SWIPL_PARAMETERS_AI_Main, 20, 40, 10, 10, 0, 10, 20);			
		Player player15 = new Player(SWIPL_PARAMETERS_AI_Main, 30, 70, 10, 10, 0, 10, 10);			
		Player player16 = new Player(SWIPL_PARAMETERS_AI_Main, 25, 55, 7, 2, 0, 9,12);		
		Player player17 = new Player(SWIPL_PARAMETERS_AI_Main, 70, 350, 35, 7, 0, 70, 210);		
		Player player18 = new Player(SWIPL_PARAMETERS_AI_Main, 61, 163, 13, 3, 0, 0, 77);		
		Player player19 = new Player(SWIPL_PARAMETERS_AI_Main, 35, 95, 20, 7, 0, 40, 20 );		
		
		Player player20 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 30, 100, 10, 5, 0, 20, 50);		
		Player player21 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 30, 55, 10, 5, 0, 5, 50);			
		Player player22 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 70, 220, 10, 10, 0, 5, 80);		
		Player player23 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 70, 220, 10, 10, 0, 5, 20);			
		Player player24 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 20, 40, 10, 10, 0, 10, 20);		
		Player player25 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 30, 70, 10, 10, 0, 10, 10);		
		Player player26 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 25, 55, 7, 2, 0, 9,12);		
		Player player27 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 70, 350, 35, 7, 0, 70, 210);			
		Player player28 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 61, 163, 13, 3, 0, 0, 77);		
		Player player29 = new Player(SWIPL_PARAMETERS_AI_Main_LZ, 35, 95, 20, 7, 0, 40, 20);	
		
		Player player30 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 30, 100, 10, 5, 0, 20, 8);		
		Player player31 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 30, 55, 10, 5, 0, 5, 8);			
		Player player32 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 70, 220, 10, 10, 0, 5, 10);		
		Player player33 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 70, 220, 10, 10, 0, 5, 2);			
		Player player34 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 20, 40, 10, 10, 0, 10, 10);		
		Player player35 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 30, 70, 10, 10, 0, 10, 1);		
		Player player36 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 25, 55, 7, 2, 0, 9, 3);		
		Player player37 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 70, 350, 35, 7, 0, 70, 8);			
		Player player38 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 61, 163, 13, 3, 0, 0, 4);		
		Player player39 = new Player(SWIPL_PARAMETERS_AI_Florian_LZ, 35, 95, 20, 7, 0, 40, 20);					
				
		tc.addAi(player10); 
		tc.addAi(player11); 
		tc.addAi(player12); 
		tc.addAi(player13); 
		tc.addAi(player14); 
		tc.addAi(player15); 
		tc.addAi(player16); 
		tc.addAi(player17); 
		tc.addAi(player18); 
		tc.addAi(player19); 
		tc.addAi(player20); 
		tc.addAi(player21); 
		tc.addAi(player22); 
		tc.addAi(player23); 
		tc.addAi(player24); 
		tc.addAi(player25); 
		tc.addAi(player26); 
		tc.addAi(player27); 
		tc.addAi(player28); 
		tc.addAi(player29);
		tc.addAi(player30); 
		tc.addAi(player31); 
		tc.addAi(player32); 
		tc.addAi(player33); 
		tc.addAi(player34); 
		tc.addAi(player35); 
		tc.addAi(player36); 
		tc.addAi(player37); 
		tc.addAi(player38); 
		tc.addAi(player39);  
		
		ArrayList<Integer> kiPool = new ArrayList<>();
		kiPool.add(player10.getId()); 
		kiPool.add(player11.getId()); 
		kiPool.add(player12.getId()); 
		kiPool.add(player13.getId()); 
		kiPool.add(player14.getId()); 
		kiPool.add(player15.getId()); 
		kiPool.add(player16.getId()); 
		kiPool.add(player17.getId()); 
		kiPool.add(player18.getId()); 
		kiPool.add(player19.getId()); 
		kiPool.add(player20.getId()); 
		kiPool.add(player21.getId()); 
		kiPool.add(player22.getId()); 
		kiPool.add(player23.getId()); 
		kiPool.add(player24.getId()); 
		kiPool.add(player25.getId()); 
		kiPool.add(player26.getId()); 
		kiPool.add(player27.getId()); 
		kiPool.add(player28.getId()); 
		kiPool.add(player29.getId()); 
		kiPool.add(player30.getId()); 
		kiPool.add(player31.getId()); 
		kiPool.add(player32.getId()); 
		kiPool.add(player33.getId()); 
		kiPool.add(player34.getId()); 
		kiPool.add(player35.getId()); 
		kiPool.add(player36.getId()); 
		kiPool.add(player37.getId()); 
		kiPool.add(player38.getId()); 
		kiPool.add(player39.getId());  	 		
		
		tc.startTournament(kiPool);
	}
}
