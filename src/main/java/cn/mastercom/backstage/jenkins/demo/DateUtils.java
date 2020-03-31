package cn.mastercom.backstage.jenkins.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static  SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
	public static String getDateString(Date date){
		return dateFormat.format(date);
	}
}
