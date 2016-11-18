package br.com.pontualmobile.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author feandrad on 14/04/16
 */
public class DateTime {
	
	public static final long SEC_MILLIS  = 1000;
	public static final long MIN_MILLIS  = 60000;
	public static final long HOUR_MILLIS = 60 * MIN_MILLIS;
	public static final long DAY_MILLIS  = 86400000;    // 24 * HOUR_MILLIS
	
	// ISO 8601 date format
	public static final String DATE_FORMAT_ISO     = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT_ISO = "yyyy-MM-dd HH:mm";
	
	public static final String DATE_FORMAT     = "dd/MM/yyyy";
	public static final String TIME_FORMAT     = "HH:mm";
	public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
	
	public static final String YEAR         = "yyyy";
	public static final String MONTH        = "MM";
	public static final String DAY_IN_MONTH = "dd";
	public static final String HOUR_IN_DAY  = "HH";
	public static final String MINUTES      = "mm";
	public static final String SECONDS      = "ss";
	public static final String MILLISECONDS = "SSS";
	
	public static long period(int hour, int minutes) {
		return period(hour, minutes, 0, 0);
	}
	
	public static long period(int hour, int minutes, int seconds) {
		return period(hour, minutes, seconds, 0);
	}
	
	public static long period(int hour, int minutes, int seconds, int miliseconds) {
		return hour * HOUR_MILLIS + minutes * MIN_MILLIS + seconds * SEC_MILLIS + miliseconds;
	}
	
	public static String format(Date date, String format) {
		return format(date, format, TimeZone.getDefault());
	}
	
	public static String format(Date d, String format, TimeZone timeZone) {
		if (d == null) {
			return "";
		}
		
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(timeZone);
		return df.format(d);
	}
	
	public static String formatDate(Date date) {
		return format(date, DATE_FORMAT, TimeZone.getDefault());
	}
	
	public static String formatTime(Date date) {
		return format(date, TIME_FORMAT, TimeZone.getDefault());
	}
	
	public static String formatTime(int hours, int minutes) {
		return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
	}
	
	public static String formatDate(int dayOfMonth, int monthOfYear, int year) {
		return String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear) + "/" + year;
	}
	
	public static int get(Date date, String format, TimeZone timeZone) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getUTCHour(Date date) {
		return get(date, HOUR_IN_DAY, TimeZone.getTimeZone("GMT"));
	}
	
	public static int getUTCMinutes(Date date) {
		return get(date, MINUTES, TimeZone.getTimeZone("GMT"));
	}
	
	public static int getUTCSeconds(Date date) {
		return get(date, SECONDS, TimeZone.getTimeZone("GMT"));
	}
	
	public static int getHour(Date date) {
		return Integer.parseInt(new SimpleDateFormat(HOUR_IN_DAY).format(date));
	}
	
	public static int getMinutes(Date date) {
		return Integer.parseInt(new SimpleDateFormat(MINUTES).format(date));
	}
	
	public static int getSeconds(Date date) {
		return Integer.parseInt(new SimpleDateFormat(SECONDS).format(date));
	}
	
	public static int getMilliseconds(Date date) {
		return Integer.parseInt(new SimpleDateFormat(MILLISECONDS).format(date));
	}
	
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @param date
	 * @return month number based on ZERO (January is 0).
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}
	
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	public static Date parseDate(String dateStr, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			Date result = format.parse(dateStr);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parseDate(String dateString, String pattern, TimeZone timeZone) {
		Calendar cal = Calendar.getInstance(timeZone);
		cal.setTime(parseDate(dateString, pattern));
		return cal.getTime();
	}
	
	/**
	 * @param date1
	 * @param date2
	 * @return Positive int if date1 > date2, 0 if date1 = date2 or negative int if date1 < date2
	 */
	public static int compareDateOnly(Date date1, Date date2) {
		return (int) (getLongDateOnly(date1) - getLongDateOnly(date2));
	}
	
	public static long getLongDateOnly(Date date) {
		return ((int) (date.getTime() / DAY_MILLIS)) * DAY_MILLIS;
	}
	
	public static Date UTCNow() {
		return new Date(System.currentTimeMillis());
	}
	
	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}
	
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}
	
	public static Calendar toCalendar(SimpleDate simpleDate, SimpleTime simpleTime) {
		
		Calendar initialCalendar = Calendar.getInstance();
		
		if (simpleDate != null) {
			initialCalendar.set(Calendar.YEAR, simpleDate.getYear());
			initialCalendar.set(Calendar.MONTH, simpleDate.getMonth());
			initialCalendar.set(Calendar.DAY_OF_MONTH, simpleDate.getDay());
		}
		
		if (simpleTime != null) {
			initialCalendar.set(Calendar.HOUR_OF_DAY, simpleTime.getHours());
			initialCalendar.set(Calendar.MINUTE, simpleTime.getMinutes());
			initialCalendar.set(Calendar.SECOND, simpleTime.getSeconds());
		}
		
		return initialCalendar;
		
	}
}
