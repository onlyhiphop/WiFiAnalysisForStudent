package cn.mr.liao.utils;
import java.text.ParseException;
import	java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义工具类
 * @author liaobaocai
 * @date 2020/3/16
 */
public class MyUtil {


    /**
     * 计算两个时间相隔多少分钟
     * @param startTime
     * @param endTime
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static int getApartMinute(String startTime, String endTime, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date startDate = sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
        long st = startDate.getTime();
        long et = endDate.getTime();
        return (int)((et - st)/(1000L*60L));
    }

    /**
     * 将时间切分出时分秒
     * @param time 20200316 120000
     * @return
     */
    public static int subStringTime(String time){
        String substring = "0";
        if(time.length() == 14){
            substring = time.substring(8);
        }
        return Integer.parseInt(substring);
    }


    /**
     * 将时间变成整点
     * @param time
     * @return
     */
    public static String timeToHour(String time){

        if(time.length() == 14){
            String h = time.substring(8, 10);
            String m = time.substring(10, 12);
            int m2 = Integer.parseInt(m);
            if (m2 > 30){
                int hour = Integer.parseInt(h) + 1;
                h = hour > 10? String.valueOf(hour) : ("0" + hour);

            }
            m = "00";
            return h + ":" + m;
        }
        return "";
    }

    public static void main(String[] args) {
        String s  = "20200316120000";
        System.out.println(Integer.parseInt("01"));
    }
}
