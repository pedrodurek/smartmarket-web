package com.smartmarket.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	
	public static boolean isSameDate(Date date1, Date date2) {

	    // Strip out the time part of each date
	    long julianDateNumber1 = date1.getTime();
	    long julianDateNumber2 = date2.getTime();

	    // If they now are equal then it is the same day
	    return julianDateNumber1 == julianDateNumber2;
	    
	}
	public static String getCurrentDateString() {
		
		DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date today = Calendar.getInstance().getTime();
		return df.format(today);
		
	}
	

}
