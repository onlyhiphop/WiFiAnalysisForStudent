package cn.edu.utils;

public class TimeFormatUtil {
	
	// 20181219165107  long¿‡–Õ
	// time£∫ Web Dec 19 16:51:07 2018
	public static Long getFormatTime(String time){
		String[] splits = time.split(" ");
		String year = splits[4];
		String monthDay = getMonth(splits[1]) + splits[2];
		String hm = splits[3].replace(":","");
		String format_time = year  + monthDay  + hm;
		Long long_time = Long.parseLong(format_time);
		return long_time;
	}
	
	public static int getMinute(String time){
		String[] splits = time.split(" ");
		String[] hm = splits[3].split(":");
		int hour = Integer.parseInt(hm[0]);
		int minute = Integer.parseInt(hm[1]);
		return hour*60 + minute;
	}
	
	public static String getMonth(String month){
		String a;
		switch (month) {
		case "Jan":
			a = "01";
			break;
		case "Feb":
			a = "02";
			break;
		case "Mar":
			a = "03";
			break;
		case "Apr":
			a = "04";
			break;
		case "May":
			a = "05";
			break;
		case "Jun":
			a = "06";
			break;
		case "Jul":
			a = "07";
			break;
		case "Aug":
			a = "08";
			break;
		case "Sept":
			a = "09";
			break;
		case "Oct":
			a = "10";
			break;
		case "Nov":
			a = "11";
			break;
		case "Dec":
			a = "12";
			break;

		default:
			a = "00";
		}
		return a;
	}

	public static String getWeek(String week){
		String a;
		switch (week) {
		case "Mon":
			a = "01";
			break;
		case "Tues":
			a = "02";
			break;
		case "Wed":
			a = "03";
			break;
		case "Thur":
			a = "04";
			break;
		case "Fri":
			a = "05";
			break;
		case "Sat":
			a = "06";
			break;
		case "Sun":
			a = "07";
			break;

		default:
			a = "00";
		}
		return a;
	}
}
