package cn.mr.liao;

import cn.mr.liao.model.WifiData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 对不同wid文件下进行时间排序
 * 输入数据格式：
 * wid,lat,lon,time,mac,range,rssi,ds
 */
public class TimeSortMr {

    public static class TimeSortMapper extends Mapper<LongWritable, Text, WifiData, NullWritable> {
        WifiData wifiData = new WifiData();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);
            wifiData.parse(value.toString().split(","));
            context.write(wifiData, NullWritable.get());
        }
    }

    public static class TimeSortReducer extends Reducer<WifiData, NullWritable, WifiData, NullWritable> {
        @Override
        protected void reduce(WifiData key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            context.write(key,NullWritable.get());
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
                .jarClass(TimeSortMr.class)
                .inputFormatClass(null)
                .mapper(TimeSortMapper.class)
                .mapOutKeyClass(WifiData.class)
                .mapOutValueClass(NullWritable.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(TimeSortReducer.class)
                .reduceOutKeyClass(WifiData.class)
                .reduceOutValueClass(NullWritable.class)
                .build();
        BaseDriver.initJob(new JobInitModel[]{jobInitModel});
    }

}
