package processIO;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/** 
 * Class for writing data into process
 * @author jelto aka. anon6789
 */
public class ProcessWriter {

	private BufferedWriter writer;
	
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
