package utils;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Metody pouzivane na pracu s casom/datumom.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class DateUtils {

	public static String getCurrentDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	public static String decodeDate(String dateString){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date;
		try {
			date = dateFormat.parse(dateString);
			dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm.ss");
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String encodeDate(String dateString){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm.ss");
		Date date;
		try {
			date = dateFormat.parse(dateString);
			dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
