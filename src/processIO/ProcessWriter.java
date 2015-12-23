package processIO;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/** 
 * Class for writing data into process
 * @author jelto aka. anon6789
 */
public class ProcessWriter {

	private BufferedWriter writer;
	
	private static final Logger log = Logger.getLogger( ProcessWriter.class.getName() );

	
	public ProcessWriter(Process process){
		this.writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
	}
	
	/**
	 * Method to write a gameAction to swipl-process.
	 * gameAction needs to be in right format.
	 * for KI2014 examples: 'f2e3', 'd4f2' or 'f6d4b6'
	 * @param gameAction
	 */
	public void write(String gameAction){
		try {
			writer.write(gameAction+".\n");
			writer.flush();
			log.info("Wrote action " + gameAction + " to other process");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
