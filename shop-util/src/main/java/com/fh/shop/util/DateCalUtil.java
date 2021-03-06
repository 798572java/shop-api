package com.fh.shop.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalUtil {
	
	public static  Date  addMonth(Date date,int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	public static boolean isOneDay(Date date1,Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2= Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		return day1==day2 && year1==year2;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		try {
		
			
			//System.out.println(	isOneDay(new Date(),sim.parse("2021-01-23")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
