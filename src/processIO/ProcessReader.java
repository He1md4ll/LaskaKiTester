package processIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Optional;

import controller.GlobalEventBus;
import controller.LaskaKITester;
import controller.TournamentController;
import event.MatchErrorEvent;
import event.NewDraftEvent;
import event.NewTimeoutEvent;
import event.NewWinEvent;


/**
 * Class for receiving data from process.
 * Data is checked by a thread
 */
public class ProcessReader extends Thread {
	
	private final String KI_ACTION_STRING = " am Zug:"; // String to look for when parsing ki Action
	private final String KI_CALC_TIME_STRING = "Zeit in MilliSekunden:";
	private final String KI_WIN_STRING = "wins";
	private final String KI_ERROR_STRING = "Falsche Eingabe";
	
	private int playerId;
	private int matchId;
	private Optional<String> aiAction = Optional.absent();
	private Optional<Integer> totalCalcTime = Optional.absent();
	private String color;
	private boolean stop = false;
	private int lineCounter = 0;

    private BufferedReader reader;
    private PrintWriter writer;
    
    public ProcessReader(Process process, int matchId, int playerId, String color) {
    	if("black".equals(color) && LaskaKITester.LOG_MATCHES) {
    		try {
    			final String dateString = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
    			new File("logs/" + TournamentController.FOLDER_PATH + "Match " + matchId + "/").mkdirs();
    			this.writer = new PrintWriter("logs/" + TournamentController.FOLDER_PATH + "Match " + matchId + "/" + dateString +  "_player" + playerId + ".log");
    		} catch (FileNotFoundException ignored){ignored.printStackTrace();}
    	}
        this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.matchId = matchId;
        this.playerId = playerId;
        this.color = color;
    }

    @Override
    public void run() {
        try {
        	String line = reader.readLine();
            while (line != null && !stop) {
            	//if(playerId == 0) System.out.println(line);
            	lineCounter++;
            	if(writer != null) {
            		writer.println(line);
            	}
                if (line.contains(color.toLowerCase() + KI_ACTION_STRING)){
                	aiAction = Optional.fromNullable(line.substring(line.length()-4));
                	if (aiAction.isPresent() && !aiAction.get().matches("\\w\\d\\w\\d")) {
                		aiAction = Optional.absent();
                	}
                } else if (line.contains(KI_CALC_TIME_STRING)){
                	totalCalcTime = Optional.fromNullable(Integer.valueOf(line.substring(KI_CALC_TIME_STRING.length())));
                } else if (line.contains(KI_WIN_STRING)){
                	if (line.toLowerCase().contains(color + " " + KI_WIN_STRING)){
                		win();
                	}
                } else if (line.contains(KI_ERROR_STRING)){
                	GlobalEventBus.getEventBus().post(new MatchErrorEvent(matchId, playerId));
                }
                checkIfDraftComplete();
                checkForError();
                line = reader.readLine();
            }
            stopReader();
        }
        catch(IOException exception) {
            System.out.println("!!Error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
    
    private void checkForError() {
		if(lineCounter > 200) {
			GlobalEventBus.getEventBus().post(new MatchErrorEvent(matchId, playerId));
		}
		
	}

	public void setStop(boolean stop) {
    	this.stop = stop;
    }
    
    private void checkIfDraftComplete() {
    	if (aiAction.isPresent() && totalCalcTime.isPresent()) {
    		if (totalCalcTime.get() <= LaskaKITester.TIMEOUT) {
    			GlobalEventBus.getEventBus().post(new NewDraftEvent(matchId, playerId, aiAction.get(), totalCalcTime.get()));
    		} else {
    			GlobalEventBus.getEventBus().post(new NewTimeoutEvent(matchId, playerId));
    		}
    		aiAction = Optional.absent();
    		totalCalcTime = Optional.absent();
    		lineCounter = 0;
    	}
    }
    
    private void win() {
		GlobalEventBus.getEventBus().post(new NewWinEvent(matchId, playerId));
    }
    
    private void stopReader() {
    	try {
			reader.close();
			if(writer != null) {
				writer.flush();
				writer.close();
			}
		} catch (IOException ignored) {}
    	this.interrupt();
    }
}