package br.com.pontualmobile.utils.time;

import java.util.Date;

/**
 * Created by feandrad on 15/06/16.
 */
public class PrettyTime {
	
	public static final long SEC_MILLIS  = 1000;
	public static final long MIN_MILLIS  = 60000;
	public static final long HOUR_MILLIS = 3600000;
	public static final long DAY_MILLIS  = 86400000;
	
	public static final String FORMAT_NOW    = "agora";
	public static final String FORMAT_FUTURE = "daqui a %1$s";
	public static final String FORMAT_TODAY  = "hoje";
	
	public static final String FORMAT_PAST    = "%1$s atrás";
	public static final String SEC_SINGULAR   = "1 segundo";
	public static final String SEC_PLURAL     = " segundos";
	public static final String MIN_SINGULAR   = "1 minuto";
	public static final String MIN_PLURAL     = " minutos";
	public static final String HOUR_SINGULAR  = "1 hora";
	public static final String HOUR_PLURAL    = " horas";
	public static final String DAY_SINGULAR   = "1 dia";
	public static final String DAY_PLURAL     = " dias";
	public static final String MONTH_SINGULAR = "1 mês";
	public static final String MONTH_PLURAL   = " meses";
	public static final String YEAR_SINGULAR  = "1 ano";
	public static final String YEAR_PLURAL    = " anos";
	public static final String FUTURE         = "muito tempo";
	
	public static String format(Date date) {
		return format(date, FORMAT_PAST, FORMAT_FUTURE);
	}
	
	public static String format(Date date, String pastMask, String futureMask) {
		String mask, module;
		long deltaTime = date.getTime() - new Date().getTime();
		
		if (deltaTime < 0) {
			deltaTime = deltaTime * -1;
			mask = pastMask;
		} else {
			mask = futureMask;
		}
		
		if (deltaTime < MIN_MILLIS) {
			return FORMAT_NOW;
		} else if (deltaTime < MIN_MILLIS * 2) {
			module = MIN_SINGULAR;
		} else if (deltaTime < HOUR_MILLIS) {
			module = normalize(deltaTime, MIN_MILLIS) + MIN_PLURAL;
		} else if (deltaTime < HOUR_MILLIS * 2) {
			module = HOUR_SINGULAR;
		} else if (deltaTime < DAY_MILLIS) {
			module = normalize(deltaTime, HOUR_MILLIS) + HOUR_PLURAL;
		} else if (deltaTime < DAY_MILLIS * 2) {
			module = DAY_SINGULAR;
		} else if (deltaTime < DAY_MILLIS * 30) {
			module = normalize(deltaTime, DAY_MILLIS) + DAY_PLURAL;
		} else {
			return prettyDate(new SimpleDate(date), pastMask, futureMask);
		}
		
		return String.format(mask, module);
	}
	
	private static int normalize(long value, long var) {
		return (int) (value / var);
	}
	
	public static String prettyDate(SimpleDate date) {
		return prettyDate(date, FORMAT_PAST, FORMAT_FUTURE);
	}
	
	public static String prettyDate(SimpleDate date, String pastMask, String futureMask) {
		String mask, module;
		SimpleDate greater, smaller;
		SimpleDate now = SimpleDate.today();
		
		if (now.equals(date)) {
			return FORMAT_TODAY;
		} else if (now.isGreaterThan(date)) {
			greater = now;
			smaller = date;
			mask = pastMask;
		} else {
			greater = date;
			smaller = now;
			mask = futureMask;
		}

		if (greater.getYear() == smaller.getYear()) {
			if (greater.getMonth() < smaller.getMonth() + 2) {
				module = MONTH_SINGULAR;
			} else {
				module = new Integer(greater.getMonth() - smaller.getMonth()) + MONTH_PLURAL;
			}
		} else if (greater.getYear() < smaller.getYear() + 100) {
			if (greater.getYear() < smaller.getYear() + 2) {
				module = YEAR_SINGULAR;
			} else {
				module = new Integer(greater.getYear() - smaller.getYear()) + YEAR_PLURAL;
			}
		} else {
			module = FORMAT_FUTURE;
		}

		return String.format(mask, module);
	}
}
