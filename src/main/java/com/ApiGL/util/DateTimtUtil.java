package com.ApiGL.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class DateTimtUtil {
	
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Taipei");
	
	//取得現在日期
	public Date getNowDate() {
        Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cSchedStartCal.setTimeZone(TIME_ZONE);
        return cSchedStartCal.getTime();
    }
	
	public Date formatStrToDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = dateFormat.parse(dateStr);
		return date;
	}
	
	public String formatDateToStr(Date dateStr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String date = dateFormat.format(dateStr);
		return date;
	}
	

	public boolean isDate(String date,int index) {
		String rexp = "";
		switch (index) {
		case 1:
			rexp = "^\\d{4}/\\d{2}/\\d{2}$";
			break;
		}
		
		Pattern pattern = Pattern.compile(rexp);
        return pattern.matcher(date).matches();
    }
	
	public boolean checkTimeFormat(String StartTime, String endTime) {
        
		if(!isTime(StartTime) || !isTime(endTime)) {
			return false;
		}
		
		if(Integer.parseInt(StartTime.replace(":", "")) > Integer.parseInt(endTime.replace(":", ""))) {
			return false;
		}
        
        return true;
    }
	
	public boolean isTime(String time) {
        
		String[] times = time.split(":");
		
        if (times.length != 2) {
        	return false;
        }
        try {
        	if (Integer.parseInt(times[0]) > 23 || Integer.parseInt(times[0]) < 0 || 
        		Integer.parseInt(times[1]) > 59 || Integer.parseInt(times[1]) < 0) {
        		return false;
        	}
		} catch (Exception e) {
			return false;
		}
        return true;
    }

}
