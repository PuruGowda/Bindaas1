package com.wattabyte.bindaasteam.util;
import java.util.regex.*;

public class CheckName {
 

	    private static final Pattern NUMBERS = Pattern.compile("\\d+");
	    private static final Pattern LETTERS = Pattern.compile("\\p{Alpha}+");

	    public static final boolean isNumeric(String text)
	    {
	        return NUMBERS.matcher(text).matches();
	    }

	    public static final boolean isAlpha(String text)
	    {
	        return LETTERS.matcher(text).matches();
	    }


}
