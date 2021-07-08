package cn.mr.liao;

import cn.mr.liao.model.WifiData;
import cn.mr.liao.utils.MyUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
     * 求夜间（23:00 ~ 5:00）寝室手机不息屏情况
 *  输入数据格式：
 *  wid,lat,lon,time,mac,range,rssi,ds
 *
 *  此处将符合要求的数据过滤出来，再使用StartEndTimeMr + StayCountAndTimeMr
 */
public class DsStatusMr {

    public static class DsStatusMapper extends Mapper<LongWritable, Text, WifiData, NullWritable> {

        private static final int MAXTIME = 230000;  //最晚23:00:00
        private static final int MINTIME = 50000;      //最早05:00:00
        WifiData wifiData = new WifiData();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            String[] split = value.toString().split(",");
            String time = split[3];
            int t = MyUtil.subStringTime(time);
            if (t >= MAXTIME || t <= MINTIME){
                wifiData.parse(split);
                context.write(wifiData, NullWritable.get());
            }

        }
    }

    public static class DsStatusReducer extends Reducer<Text, Text, Text, NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            context.write(key, NullWritable.get());
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPaths = new String[]{"hdfs://localhost:9000/weblog/wifidata/*"};
        String outPath = "hdfs://localhost:9000/out/widsplit";
        Configuration conf = new Configuration();
        String jobName = "timesort";
        JobInitModel jobInitModel = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobName)
                .jarClass(DsStatusMr.class)
                .inputFormatClass(null)
                .mapper(DsStatusMapper.class)
                .mapOutKeyClass(WifiData.class)
                .mapOutValueClass(NullWritable.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(DsStatusReducer.class)
                .reduceOutKeyClass(WifiData.class)
                .reduceOutValueClass(NullWritable.class)
                .build();
        BaseDriver.initJob(new JobInitModel[]{jobInitModel});
    }
}
