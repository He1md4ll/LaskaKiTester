package controller;
import java.io.IOException;

import entities.Player;
import processIO.ProcessReader;
import processIO.ProcessWriter;

/**
 * Class to control the field of one KI.
 * It has one swipl process with one reader and one writer,
 * to communicate with the process.
 */
public class MoveController {

	private Process process;
	private ProcessReader reader;
	private ProcessWriter writer;
	
	public MoveController(int matchId, Player ai, String color) {
		try {			
			
			if (ai.isStartedWithParas()){
				if(ai.getJjov() == 0 && ai.getJov() == 0){
					
					String[] cmd = {
							"/bin/sh",
							"-c",
							LaskaKITester.SWIPL_LOCATION + ai.getParameters() + " -t 'start(" + color + "," + ai.getSv() + "," + ai.getGv() + "," + ai.getJsv() + "," + ai.getJjsv() + "," + ai.getMv() + "," + ai.getJv() + "," + ai.getDv() +")'"
					};
					
					process = Runtime.getRuntime().exec(cmd);		
				} 
				else {
					
					String[] cmd = {
						"/bin/sh",
						"-c",
						LaskaKITester.SWIPL_LOCATION + ai.getParameters() + " -t 'start(" + color + "," + ai.getSv() + "," + ai.getGv() + "," + ai.getJsv() + "," + ai.getJjsv() + "," + ai.getJov() + "," + ai.getJjov() + "," + ai.getMv() + "," + ai.getJv() + "," + ai.getDv() +")'"
					};
					
					process = Runtime.getRuntime().exec(cmd);
				}
			}
			else {
				process = Runtime.getRuntime().exec(LaskaKITester.SWIPL_LOCATION + ai.getParameters() + " -t 'start(" + color + ")'");
			}
			reader = new ProcessReader(process, matchId, ai.getId(), color);
			writer = new ProcessWriter(process);
			reader.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void move(String position){
		writer.write(position);
	}
	
	public void stopGame(){
		reader.setStop(true);
		process.destroy();
	}	
}