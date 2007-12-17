package gov.nih.nci.calab.service.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class contains a set of utilities for converting Strings to other
 * formats or converting other formats to String.
 * 
 * @author pansu
 * 
 */
/* CVS $Id: StringUtils.java,v 1.25 2007-12-17 16:22:57 cais Exp $ */

public class StringUtils {
	private static Logger logger = Logger.getLogger(StringUtils.class);

	public static boolean isImgFileExt(String fileName) {
		boolean isImgFileExt = false;
		for (int i = 0; i < CaNanoLabConstants.IMAGE_FILE_EXTENSIONS.length; i++) {
			if (fileName.toUpperCase().endsWith(
					"." + CaNanoLabConstants.IMAGE_FILE_EXTENSIONS[i])) {
				isImgFileExt = true;
				break;
			}
		}

		return isImgFileExt;
	}

	public static Date convertToDate(String dateString, String dateFormat) {
		if (dateString == null || dateString == "") {
			return null;
		}
		Date theDate = null;
		try {
			ParsePosition pos = new ParsePosition(0);
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			theDate = format.parse(dateString, pos);
			// method parse doesn't throw an exception when parsing is partial.
			// e.g. date 5/11/200w will
			// be parsed as 5/11/200 !!!
			if (pos.getIndex() != dateString.length()) {
				throw new RuntimeException(
						"The date String is not completely parsed");
			}
			return theDate;
		} catch (Exception e) {
			logger
					.error(
							"Error parsing the given date String using the given dateFormat",
							e);
			throw new RuntimeException("The date String " + dateString
					+ " can't be parsed against the date format:" + dateFormat);
		}
	}

	public static String join(String[] stringArray, String delimiter) {
		String joinedStr = "";
		if (stringArray == null) {
			return joinedStr;
		}
		for (int i = 0; i < stringArray.length; i++) {
			String str = stringArray[i];
			if (str == null) {
				str = "";
			}
			if ((str.length() > 0)) {
				if (i < stringArray.length - 1) {
					joinedStr += str + delimiter;
				} else {
					joinedStr += str;
				}
			}
		}
		if (joinedStr.endsWith(delimiter)) {
			joinedStr = joinedStr.substring(0, joinedStr.length() - 1);
		}
		return joinedStr;
	}

	public static String join(List<String> stringList, String delimiter) {
		String joinedStr = "";
		if (stringList == null || stringList.isEmpty()) {
			return joinedStr;
		}
		// remove null and empty item from the list
		for (String str : stringList) {
			if (str == null) {
				stringList.remove(str);
			}
			if (str.length() == 0) {
				stringList.remove(str);
			}
		}
		int i = 0;
		for (String str : stringList) {
			if (i < stringList.size() - 1) {
				joinedStr += str + delimiter;
			} else {
				joinedStr += str;
			}
			i++;
		}
		return joinedStr;
	}
	
	/* 
	 * empty string of the collection will be included in the joined string
	 * and null item in the collection will be converted to an empty string
	 */
	public static String joinEmptyItemIncluded(Collection<String> stringCollection, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		if(stringCollection == null || stringCollection.isEmpty()) {
			return buffer.toString();
		}
		
		Iterator iter = stringCollection.iterator();
        while (iter.hasNext()) {
        	String item = (String)iter.next();
        	if(item == null) item = "";
            buffer.append(item);
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
		return buffer.toString();
	}

	public static String convertDateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		String dateStr = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			dateStr = dateFormat.format(date);
		} catch (Exception e) {
			logger
					.error(
							"Error converting the given date using the given dateFormat",
							e);
			throw new RuntimeException("Can't format the given date: " + date);
		}
		return dateStr;
	}

	public static Float convertToFloat(String floatStr) {
		if (floatStr == null || floatStr == "") {
			return null;
		}
		try {
			Float floatNum = Float.parseFloat(floatStr);
			return floatNum;
		} catch (NumberFormatException e) {
			logger.error("Error converting the given string to a float number",
					e);
			throw new RuntimeException(
					"Can't convert the given string to a float number: "
							+ floatStr);
		}
	}

	public static Long convertToLong(String longStr) {
		if (longStr == null || longStr == "") {
			return null;
		}
		try {
			Long longNum = Long.parseLong(longStr);
			return longNum;
		} catch (NumberFormatException e) {
			logger.error("Error converting the given string to a long number",
					e);
			throw new RuntimeException(
					"Can't convert the given string to a long number: "
							+ longStr);
		}
	}

	public static String convertToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	public static boolean isInteger(String theStr) {
		if (theStr == null || theStr.length() == 0) {
			return false;
		} else {
			for (int i = 0; i < theStr.length(); i++) {
				if (!Character.isDigit(theStr.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean isDouble(String theStr) {
		int decimalCount = 0;

		if (theStr == null || theStr.length() == 0) {
			return false;
		} else {
			for (int i = 0; i < theStr.length(); i++) {
				if (!Character.isDigit(theStr.charAt(i))) {
					if (theStr.charAt(i) == ('.')) {
						decimalCount++;
						continue;
					} else {
						return false;
					}
				}
			}

			if (decimalCount == 1)
				return true;
			else
				return false;
		}
	}

	public static boolean contains(String[] array, String aString,
			boolean ignoreCase) {
		boolean containsString = false;

		for (int i = 0; i < array.length; i++) {
			if (ignoreCase) {
				if (array[i].equalsIgnoreCase(aString))
					containsString = true;
			} else {
				if (array[i].equals(aString))
					containsString = true;
			}
		}

		return containsString;
	}

	public static String[] add(String[] x, String aString) {
		String[] result = new String[x.length + 1];
		for (int i = 0; i < x.length; i++) {
			result[i] = x[i];
		}
		result[x.length] = aString;

		return result;
	}

	/**
	 * Convert a string with multiple words separated by space to one word, with
	 * first letter as lower case.
	 * 
	 * @param words
	 * @return
	 */
	public static String getOneWordLowerCaseFirstLetter(String words) {
		// remove space in words and make the first letter lower case.
		String firstLetter = words.substring(0, 1);
		String oneWord = words.replaceFirst(firstLetter,
				firstLetter.toLowerCase()).replace(" ", "");
		return oneWord;
	}

	/**
	 * Convert a string with multiple words separated by space to one word, with
	 * first letter as upper case.
	 * 
	 * @param words
	 * @return
	 */
	public static String getOneWordUpperCaseFirstLetter(String words) {
		// remove space in words and make the first letter lower case.
		String firstLetter = words.substring(0, 1);
		String oneWord = words.replaceFirst(firstLetter,
				firstLetter.toUpperCase()).replace(" ", "");
		return oneWord;
	}

	public static void main(String[] args) {
		try {
			String dateString = StringUtils.convertDateToString(new Date(),
					"yyyyMMdd_HH-mm-ss-SSS");
			System.out.println(dateString);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
