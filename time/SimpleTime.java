package br.com.pontualmobile.utils.time;

import java.util.Date;

/**
 * Created by feandrad on 11/05/16.
 */
public class SimpleTime implements Comparable<SimpleTime> {
	
	public static final int MILLISEC_IN_SEC = 1000;
	public static final int SEC_IN_MIN      = 60;
	public static final int MIN_IN_HOUR     = 60;
	
	public static final String TIME_FORMAT = "HH:mm:ss";
	
	private int hours, minutes, seconds, milliseconds;
	
	public SimpleTime() {
		this(0, 0, 0, 0);
	}
	
	public SimpleTime(int hours, int minutes) {
		this(hours, minutes, 0, 0);
	}
	
	public SimpleTime(int hours, int minutes, int seconds) {
		this(hours, minutes, seconds, 0);
	}
	
	public SimpleTime(int hours, int minutes, int seconds, int milliseconds) {
		this.setHours(hours).setMinutes(minutes).setSeconds(seconds).setMilliseconds(milliseconds);
	}
	
	public SimpleTime(Date date) {
		this(DateTime.getHour(date), DateTime.getMinutes(date), DateTime.getSeconds(date),
				DateTime.getMilliseconds(date));
	}
	
	/**
	 * @return the ISO 8601 date format (YYYY-MM-DD).
	 */
	@Override public String toString() {
		return String.format("%02d", this.hours) + ":" + String.format("%02d", this.minutes) + ":" +
				String.format("%02d", this.seconds);
	}
	
	@Override public boolean equals(Object obj) {
		if (obj instanceof SimpleTime) {
			SimpleTime other = (SimpleTime) obj;
			return this.hours == other.hours && this.minutes == other.minutes && this.seconds == other.seconds &&
					this.milliseconds == other.milliseconds;
		}
		
		return false;
	}
	
	/**
	 * @param another
	 * @return The difference in milliseconds
	 */
	@Override public int compareTo(SimpleTime another) {
		
		if (this.equals(another)) {
			return 0;
		}
		
		if (this.getMilliseconds() > another.getMilliseconds()) {
			return 1;
		} else if (this.getMilliseconds() == another.getMilliseconds()) {
			if (this.getSeconds() > another.getSeconds()) {
				return 1;
			} else if (this.getSeconds() == another.getSeconds()) {
				if (this.getMinutes() > another.getMinutes()) {
					return 1;
				} else if (this.getMinutes() == another.getMinutes()) {
					if (this.getHours() > another.getHours()) {
						return 1;
					}
				}
			}
		}
		
		return -1;
	}
	
	public long getDeltaTime() {
		return DateTime.period(getHours(), getMinutes(), getSeconds(), getMilliseconds());
	}
	
	public int getHours() {
		return hours;
	}
	
	public SimpleTime setHours(int hours) {
		this.hours = hours;
		return this;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public SimpleTime setMinutes(int minutes) {
		this.minutes = minutes;
		if (this.minutes >= MIN_IN_HOUR) {
			int exceed = this.minutes / MIN_IN_HOUR;
			setHours(exceed);
			
			int remainder = this.minutes % MIN_IN_HOUR;
			this.minutes -= exceed * MIN_IN_HOUR;
			this.minutes -= remainder;
		}
		return this;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public SimpleTime setSeconds(int seconds) {
		this.seconds = seconds;
		if (this.seconds >= SEC_IN_MIN) {
			int exceed = this.seconds / SEC_IN_MIN;
			setMinutes(exceed);
			
			int remainder = this.seconds % SEC_IN_MIN;
			this.seconds -= exceed * SEC_IN_MIN;
			this.seconds -= remainder;
		}
		return this;
	}
	
	public int getMilliseconds() {
		return milliseconds;
	}
	
	public SimpleTime setMilliseconds(int milliseconds) {
		this.milliseconds = milliseconds;
		if (this.milliseconds >= MILLISEC_IN_SEC) {
			int exceed = this.milliseconds / MILLISEC_IN_SEC;
			setSeconds(exceed);
			
			int remainder = this.milliseconds % MILLISEC_IN_SEC;
			this.milliseconds -= exceed * MILLISEC_IN_SEC;
			this.milliseconds -= remainder;
		}
		return this;
	}
}
