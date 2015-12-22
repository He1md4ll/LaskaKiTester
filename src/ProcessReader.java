import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for receiving data from process.
 * Data is checked by a thread
 * @author jelto aka. anon6789
 */
public class ProcessReader extends Thread {
	
	private final String KI_ACTION_STRING = "KI -> "; // String to look for when parsing ki Action
	private final int POSITION_KI_ACTION_STRING_STARTS = 6; // Position where the actual action starts. you need to adjust this according to KI_ACTION_STRING
	
	private String aiAction;
	private boolean actionChanged = false;

    private BufferedReader reader = null;
    public ProcessReader(Process process) {
        this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    @Override
    public void run() {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
            	//Uncomment this to see raw process output
                //System.out.println(line);
                if (line.contains(KI_ACTION_STRING)){
                	aiAction = line.substring(POSITION_KI_ACTION_STRING_STARTS);
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
    		Thread.sleep(2000);
    		while(!actionChanged){
    		
				Thread.sleep(100);
    		}
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionChanged = false;
		return this.aiAction;
    }
}