package cn.mr.liao;

import static org.junit.Assert.assertTrue;

import cn.mr.liao.utils.MyUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static void main(String[] args) throws ParseException {
        List<String> wifiAllDataList = new ArrayList<>();
        wifiAllDataList.add("20200316100000");
        wifiAllDataList.add("20200316100111");
        wifiAllDataList.add("20200316100512");

        wifiAllDataList.add("20200316102020");  //大于
        wifiAllDataList.add("20200316102121");
        wifiAllDataList.add("20200316102525");

        wifiAllDataList.add("20200316103636"); //大于
        wifiAllDataList.add("20200316103737");
        wifiAllDataList.add("20200316103838");
        wifiAllDataList.add("20200316103939");

        wifiAllDataList.add("20200316105555"); //大于
        int jump = 0;
        for(int i = 0; i < wifiAllDataList.size(); i=jump){
            for (int j = i; j < wifiAllDataList.size() - 1; j++){
                String startTime = wifiAllDataList.get(j);
                String endTime =wifiAllDataList.get(j + 1);
                if (MyUtil.getApartMinute(startTime, endTime, "yyyyMMddHHmmss") > 10){
                    //时间相隔十分钟，说明已经离开 i - j为这个时间段
                    jump = j;
                    String startTimeStr = wifiAllDataList.get(i);
                    String endTimeStr = wifiAllDataList.get(j);
                    System.out.println(startTimeStr + "----" + endTimeStr);
                    break;
                }
            }
            jump++;
        }

    }
}
