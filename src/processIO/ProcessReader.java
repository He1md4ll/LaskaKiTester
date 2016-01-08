package processIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for receiving data from process.
 * Data is checked by a thread
 * @author jelto aka. anon6789
 */
public class ProcessReader extends Thread {
	
	private final String KI_ACTION_STRING = " am Zug:"; // String to look for when parsing ki Action
	private final String KI_CALC_TIME_STRING = "Zeit in MilliSekunden:";
	private final String KI_WIN_STRING = "wins";
	
	private String aiAction;
	private boolean actionChanged = false;
	private String totalCalcTime = "0";
	private boolean win;
	private String color;

    private BufferedReader reader = null;
    public ProcessReader(Process process, String color) {
        this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.color = color;
    }

    @Override
    public void run() {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
            	//Uncomment this to see raw process output
                System.out.println(line);
                if (line.contains(color.toLowerCase() + KI_ACTION_STRING)){
                	aiAction = line.substring(line.length()-4);
                	
                	if (aiAction.matches("\\w\\d\\w\\d")) {
                		actionChanged = true;
                	}
                } else if (line.contains(KI_CALC_TIME_STRING)){
                	totalCalcTime = line.substring(KI_CALC_TIME_STRING.length());
                } else if (line.contains(KI_WIN_STRING)){
                	//TODO: fixen wenn KI richtig reagiert
                	//im moment rastet die ja mit 'execution aborted' aus
                	win = true;
                	actionChanged = true;
                }                
            }
        }
        catch(IOException exception) {
            System.out.println("!!Error: " + exception.getMessage());
        }
    }
    
    /**
     * Waits for the new ki-action-string (for example 'd4f2') and returns the new String.
     * @return returns the actual ki-action when there is a new one
     */
    public String getAiAction(){
    	try {
    		while(!actionChanged){
				Thread.sleep(1000);
    		}
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionChanged = false;
		return this.aiAction;
    }
    
    public String getTotalCalcTime(){
    	return this.totalCalcTime;
    }
    
    public boolean isWin(){
    	return this.win;
    }
}