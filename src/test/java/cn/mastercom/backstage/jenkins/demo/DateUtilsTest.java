package cn.mastercom.backstage.jenkins.demo;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

	@Test
	public void getDateString() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(2020,2,22);
		assertEquals("2020/03/22",DateUtils.getDateString(calendar.getTime()));
	}
}