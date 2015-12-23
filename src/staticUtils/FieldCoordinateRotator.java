package staticUtils;
import java.util.logging.Logger;

/**
 * Class that gives a static method to rotate the field-coordinates
 * @author jelto aka. anon6789
 *
 */
public class FieldCoordinateRotator {
	
	private static final Logger log = Logger.getLogger( FieldCoordinateRotator.class.getName() );

	/**
	 * Translates field-coordinates between two fields.
	 * Main problem of AI from 2014 is, that you always play one side. So for each action the
	 * coordinates need to be mirrowed for the other field.
	 * Maybe the methode is obsolete when some parameters in Prolog-AI are changed/tweaked.
	 * Translation done: <br>
	 * a -> g <br>
	 * b -> f <br>
	 * c -> e <br>
	 * d -> d <br>
	 * e -> c <br>
	 * b -> f <br>
	 * g -> a <br>
	 * and numbers : <br>
	 * 1 -> 7 <br>
	 * 2 -> 6 and so on... <br>
	 * 
	 * @param coordinates
	 * @return mirrowed coordinates
	 */
	public static String rotateCoordinates(String coordinates){
		
		char[] rotatedCoordinates;
		
		//WATCH OUT HERE HAPPENS SOME BAD ASCII-HACKING!
		
		rotatedCoordinates = coordinates.toCharArray();
		
		int firstNum = rotatedCoordinates[1]-48;
		int secondNum = rotatedCoordinates[3]-48;
		
		char firstChar = rotatedCoordinates[0];
		char secondChar = rotatedCoordinates[2];
		
		firstNum = -firstNum+8;
		secondNum = -secondNum+8;
		
		firstChar = (char) ((-(firstChar-96) + 8)+96);
		secondChar = (char) ((-(secondChar-96) + 8)+96);
		
		if (rotatedCoordinates.length > 4){
			int thirdNum = rotatedCoordinates[5]-48;
			thirdNum = -thirdNum+8;
			char thirdChar = rotatedCoordinates[4];
			thirdChar = (char) ((-(thirdChar-96) + 8)+96);
			log.info("Transforming " + coordinates +" into " + firstChar+""+firstNum+""+secondChar+""+secondNum+""+thirdChar+""+thirdNum);
			return firstChar+""+firstNum+""+secondChar+""+secondNum+""+thirdChar+""+thirdNum;
		} else {
			log.info("Transforming " + coordinates +" into " + firstChar+""+firstNum+""+secondChar+""+secondNum);
			return firstChar+""+firstNum+""+secondChar+""+secondNum;
		}
	}
	
	
	
}
