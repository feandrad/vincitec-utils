package br.com.pontualmobile.utils.time;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by feandrad on 11/05/16.
 */
public class SimpleDate implements Comparable<SimpleDate>, Serializable {
	
	private int day, month, year;
	
	public SimpleDate(int day, int month, int year) {
		this.setDay(day).setMonth(month).setYear(year);
	}
	
	public SimpleDate(long timestamp) {
		this(new Date(timestamp));
	}
	
	public SimpleDate(Date date) {
		this(DateTime.getDay(date),
				DateTime.getMonth(date) + 1,
				DateTime.getYear(date)
		);
	}

//	public SimpleDate(Date date, TimeZone timeZone) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.setTimeZone(timeZone);
//		this.setYear(cal.get(Calendar.YEAR))
//				.setMonth(cal.get(Calendar.MONTH))
//				.setDay(cal.get(Calendar.DAY_OF_MONTH));
//	}
	
	/**
	 * @return the ISO 8601 date format (YYYY-MM-DD).
	 */
	@Override public String toString() {
		return String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
	}
	
	@Override public boolean equals(Object obj) {
		if (obj instanceof SimpleDate) {
			SimpleDate other = (SimpleDate) obj;
			return this.day == other.day && this.month == other.month && this.year == other.year;
		} else if (obj instanceof Date) {
			SimpleDate other = new SimpleDate((Date) obj);
			return this.equals(other);
		}
		
		return false;
	}
	
	@Override public int hashCode() {
		int result = 7 * day;
		result = 23 * result + month;
		result = 31 * result + year;
		return result;
	}
	
	/**
	 * @param other
	 * @return The difference in days
	 */
	@Override public int compareTo(SimpleDate other) {
		
		if (this.equals(other)) {
			return 0;
		}

//		return this.toDays() - other.toDays();
		
		if (this.getYear() > other.getYear()) {
			return 1;
		} else if (this.getYear() == other.getYear()) {
			if (this.getMonth() > other.getMonth()) {
				return 1;
			} else if (this.getMonth() == other.getMonth()) {
				if (this.getDay() > other.getDay()) {
					return 1;
				}
			}
		}
		
		return -1;
	}
	
	public boolean isGreaterThan(SimpleDate other) {
		return this.compareTo(other) > 0;
	}
	
	public boolean isGreaterOrEquals(SimpleDate other){
		return compareTo(other) >= 0;
	}
	
	public boolean isSmallerThan(SimpleDate other) {
		return this.compareTo(other) < 0;
	}
	
	// FIXME: Problems before epoch
	public int toDays() {
		Date date = DateTime.parseDate(this.toString(), DateTime.DATE_FORMAT);
		return (int) (date.getTime() / DateTime.DAY_MILLIS);
	}
	
	public int getDay() {
		return day;
	}
	
	public SimpleDate setDay(int day) {
		int maxDay = getMaxDaysInMonth();
		if (day > maxDay) {
			throw new IllegalArgumentException("Day cannot be setted to a value greater than " + maxDay +
					" for the month " + this.month + "/" + this.year + ".");
		} else if (day < 1) {
			throw new IllegalArgumentException("Day cannot be setted to a value lesser than 1.");
		}
		this.day = day;

//		int maxDays = getMaxDaysInMonth();
//		if (this.month >= MONTHS_IN_YEAR) {
//			int exceed = this.month / MONTHS_IN_YEAR;
//			setYear(exceed);
//
//			int remainder = this.month % MONTHS_IN_YEAR;
//			this.month -= exceed * MONTHS_IN_YEAR;
//			this.month -= remainder;
//		}
		
		return this;
	}
	
	public int getMonth() {
		return month;
	}
	
	public SimpleDate setMonth(int month) {
		if (month > 12) {
			throw new IllegalArgumentException("Month cannot be setted to a value greater than 12.");
		} else if (month < 1) {
			throw new IllegalArgumentException("Month cannot be setted to a value lesser than 1.");
		}
		
		this.month = month;

//		if (this.month >= MONTHS_IN_YEAR) {
//			int exceed = this.month / MONTHS_IN_YEAR;
//			setYear(exceed);
//
//			int remainder = this.month % MONTHS_IN_YEAR;
//			this.month -= exceed * MONTHS_IN_YEAR;
//			this.month -= remainder;
//		}
		
		return this;
	}
	
	public int getYear() {
		return year;
	}
	
	public SimpleDate setYear(int year) {
		this.year = year;
		return this;
	}
	
	public int getMaxDaysInMonth() {
		if (this.month == 2) {
			if (DateTime.isLeapYear(this.year)) {
				return 29;
			} else {
				return 28;
			}
		}
		
		if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
			return 30;
		}
		
		return 31;
	}
	
	public static SimpleDate today() {
		return new SimpleDate(new Date());
	}
	
	public String format(String dateFormat) {
		return String.format(dateFormat, getYear(), getMonth(), getDay());
	}
}
