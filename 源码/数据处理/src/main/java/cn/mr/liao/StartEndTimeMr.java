package cn.mr.liao;
import	java.util.ArrayList;

import cn.mr.liao.model.WifiData;
import cn.mr.liao.utils.MyUtil;
import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 求在地方待的开始时间startTime和结束时间endTime
 * 数据输入格式：
 * wid,lat,lon,time,mac,range,rssi,ds
 * 数据输出格式：
 * wid,lat,lon,mac,range,rssi,ds,startTime,endTime
 */
public class StartEndTimeMr {

    public static class StartEndTimeMapper extends Mapper<LongWritable, Text, Text, WifiData>{

        Text text = new Text();
        private WifiData wifiData = new WifiData();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            String mac = value.toString().split(",")[4];
            text.set(mac);
            wifiData.parse(value.toString().split(","));
            context.write(text, wifiData);
        }
    }

    public static class StartEndTimeReducer extends Reducer<Text, WifiData, Text, NullWritable>{

        Text text = new Text();
        List<WifiData> wifiAllDataList = new ArrayList<> ();

        @SneakyThrows
        @Override
        protected void reduce(Text key, Iterable<WifiData> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            Iterator<WifiData> i1 = values.iterator();
            while (i1.hasNext()) {
                WifiData wifiData = i1.next();
                wifiAllDataList.add(wifiData);
            }
            int jump = 0;
            for(int i = 0; i < wifiAllDataList.size(); i=jump){
                for (int j = i; j < wifiAllDataList.size() - 1; j++){
                    String startTime = wifiAllDataList.get(j).getTime();
                    String endTime =wifiAllDataList.get(j + 1).getTime();
                    if (MyUtil.getApartMinute(startTime, endTime, "yyyyMMddHHmmss") > 10){
                        //时间相隔十分钟，说明已经离开， i - j为这个地方待的时间段
                        jump = j;
                        String startTimeStr = wifiAllDataList.get(i).getTime();
                        String endTimeStr = wifiAllDataList.get(j).getTime();
                        WifiData wifiData = wifiAllDataList.get(i);
                        String textStr = wifiData.getWid() + "," +
                                wifiData.getLat() + "," +
                                wifiData.getLon() + "," +
                                wifiData.getMac() + "," +
                                wifiData.getRange() + "," +
                                wifiData.getRssi() + "," +
                                wifiData.getDs() + "," +
                                startTimeStr + "," +
                                endTimeStr;
                        text.set(textStr);
                        context.write(text, NullWritable.get());
                        break;
                    }
                }
                jump++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPaths = new String[]{"hdfs://localhost:9000/weblog/wifidata/*"};
        String outPath = "hdfs://localhost:9000/out/startEndTime";
        Configuration conf = new Configuration();
        String jobName = "start-end-time";
        JobInitModel jobInitModel = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobName)
                .jarClass(StartEndTimeMr.class)
                .inputFormatClass(null)
                .mapper(StartEndTimeMapper.class)
                .mapOutKeyClass(Text.class)
                .mapOutValueClass(WifiData.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(StartEndTimeReducer.class)
                .reduceOutKeyClass(Text.class)
                .reduceOutValueClass(NullWritable.class)
                .build();
        BaseDriver.initJob(new JobInitModel[]{jobInitModel});
    }
}
