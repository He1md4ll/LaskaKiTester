package controller;
import java.io.IOException;

import entities.Player;
import processIO.ProcessReader;
import processIO.ProcessWriter;

/**
 * Class to control the field of one KI.
 * It has one swipl process with one reader and one writer,
 * to communicate with the process.
 * @author jelto aka. anon6789
 *
 */
public class MoveController {

	private Process process;
	private ProcessReader reader;
	private ProcessWriter writer;
	private String color; 	// weiss = zuerst dran
							// schwarz = danach dran
	
	private Player ai;
	
	/**
	 * 
	 * @param swiplLocation 
	 * location of the swipl.exe. for example C:\\Program Files\\swipl\\bin\\swipl.exe
	 * @param swiplParameters
	 * String of the parameters swiple gets.  For example -s .\\KIs\\1\\laska.pl -t start
	 * -s stands for the prolog-entry file
	 * -t stands for the start-method
	 * @param color color of the player, just used for loging
	 */
	public MoveController(String swiplLocation, Player ai, String color){
		this.color = color;
		this.ai = ai;
		try {			
			
			if (ai.isStartedWithParas()){
				process = Runtime.getRuntime().exec(swiplLocation + ai.getParameters() + " -t start(" + color + "," + ai.getSv() + "," + ai.getGv() + "," + ai.getJsv() + "," + ai.getJjsv() + "," + ai.getMv() + "," + ai.getJv() + "," + ai.getDv() +")");
			}else {
				process = Runtime.getRuntime().exec(swiplLocation + ai.getParameters() + " -t start(" + color + ")");
			}
			reader = new ProcessReader(process, color);
			writer = new ProcessWriter(process);
			reader.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gives game input data to the writer stream
	 * @param position for example 'f2e3'
	 */
	public void move(String position){
		writer.write(position);
	}
	
	/**
	 * @return returns gameAction of AI
	 * could take some time according to search level
	 */
	public String getAiAction(){
		
		String aiAction = reader.getAiAction();
		
		System.out.println("Player with color " + color + "moved " + aiAction + ". Total calculation time: " + reader.getTotalCalcTime());
		
		return aiAction;
		
	}
	
	public int getTotalCalcTime(){
		return Integer.parseInt(reader.getTotalCalcTime());
	}
	
	public boolean isWin(){
		if (reader.isWin()){
			//System.out.println("Player with color " + color + " wins!");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLost(){
		if (reader.isLost()){
			//System.out.println("Player with color " + color + " lost!");
			return true;
		} else {
			return false;
		}
	}
	
	public void stopGame(){
		process.destroy();
	}
	
	public Player getAi(){
		return this.ai;
	}
	
	
}
