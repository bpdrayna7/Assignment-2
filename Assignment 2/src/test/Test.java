package test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Test {
	public static void main(String[] args) {
		final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime start = DATE_FORMATTER.parseDateTime("2010-12-12");
		DateTime end = DATE_FORMATTER.parseDateTime("2011-12-12");
		long difference = end.getMillis() - start.getMillis();
		System.out.println(difference/(2.628*Math.pow(10, 9)));
	}
}